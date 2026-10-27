"""Build MDM against the prepared universal library for every supported target; retain per-target logs."""
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import argparse
import json
import os
import subprocess
import sys
ROOT=Path(__file__).resolve().parents[1]
parser=argparse.ArgumentParser(description=__doc__)
parser.add_argument('--target',action='append')
parser.add_argument('--task',default='build')
parser.add_argument('--workers',type=int,default=2)
parser.add_argument('--offline',action='store_true')
args=parser.parse_args()
def jdk(target):
    loader,mc=target.split('_',1)
    major=25 if mc.startswith('26.') or target=='neoforge_1.21.11' else 17 if loader=='forge' else 21
    candidates=[os.environ.get('JAVA%d_HOME'%major),os.environ.get('JAVA_HOME')]
    for base in [Path.home()/'.jdks',Path.home()/'.gradle/jdks',Path('C:/Program Files/Java')]:
        if base.exists(): candidates.extend(str(p) for p in base.iterdir() if p.is_dir())
    for name in filter(None,candidates):
        release=Path(name)/'release'
        if release.exists() and ('JAVA_VERSION="%d.'%major) in release.read_text():return name
    raise RuntimeError('Set JAVA%d_HOME'%major)
def build(target):
    java=jdk(target.name)
    env=dict(os.environ,JAVA_HOME=java)
    env['PATH']=str(Path(java)/'bin')+os.pathsep+env['PATH']
    log=ROOT/'build/eqlib-matrix'/(target.name+'.log');log.parent.mkdir(parents=True,exist_ok=True)
    command=[str(target/'gradlew.bat'),'-Dorg.gradle.java.home='+java,args.task,'--console=plain']
    if args.offline:command.append('--offline')
    print('Building',target.name,flush=True)
    with log.open('w') as output:r=subprocess.run(command,cwd=target,env=env,stdout=output,stderr=subprocess.STDOUT)
    print('PASS' if r.returncode==0 else 'FAIL',target.name,flush=True)
    return target.name,dict(exit_code=r.returncode,log=str(log))
targets=[p for p in sorted((ROOT/'versions').iterdir()) if (p/'build.gradle').exists() and (not args.target or p.name in args.target)]
report=ROOT/'build/eqlib-matrix/results.json'
results=json.loads(report.read_text()) if report.exists() else {}
selected_results={}
with ThreadPoolExecutor(max_workers=args.workers) as pool:
    for future in as_completed([pool.submit(build,t) for t in targets]):
        name,result=future.result();results[name]=result;selected_results[name]=result
        (ROOT/'build/eqlib-matrix/results.json').write_text(json.dumps(results,indent=2))
sys.exit(any(r['exit_code'] for r in selected_results.values()))

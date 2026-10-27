"""Run isolated server regressions against one unchanged universal eqlib distribution."""
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import argparse
import hashlib
import json
import os
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[1]
parser = argparse.ArgumentParser(description=__doc__)
parser.add_argument('--target', action='append')
parser.add_argument('--workers', type=int, default=2)
parser.add_argument('--offline', action='store_true')
args = parser.parse_args()
LIBRARY = Path(os.environ.get('EQLIB_DIR', ROOT.parent.parent/'Sound Features/versions/opaleqsLibrary')).resolve()
sys.path.insert(0, str(LIBRARY/'tools'))
from java_tools import jdk

versions = {
    line.split('=', 1)[1].strip()
    for properties in (ROOT/'versions').glob('*/gradle.properties')
    for line in properties.read_text().splitlines()
    if line.startswith('eqlib_version=')
}
if len(versions) != 1:
    raise SystemExit('All targets must declare the same eqlib_version')
BUNDLE = LIBRARY/'dist'/('eqlib-'+versions.pop()+'.jar')
CHECKSUM = hashlib.sha256(BUNDLE.read_bytes()).hexdigest()
OUTPUT = ROOT/'build/eqlib-runtime'
OUTPUT.mkdir(parents=True, exist_ok=True)


def run(target):
    loader, game = target.name.split('_', 1)
    major = 25 if game.startswith('26.') or target.name == 'neoforge_1.21.11' else 17 if loader == 'forge' else 21
    java = jdk(major)
    command = [str(target/'gradlew.bat'), '-Dorg.gradle.java.home='+str(java)]
    if target.name == 'forge_1.16.5':
        command.append('-PsmokeTest')
    else:
        fixture = LIBRARY/'tests/universal-fabric-runtime.gradle' if target.name in ['fabric_26.2', 'fabric_26.3'] else ROOT/'shared/tests'/('placement-unload-runtime.gradle' if loader == 'fabric' else 'placement-unload-forge-runtime.gradle')
        command.extend(['-I', str(fixture)])
    command.extend([':runServer', '--no-configuration-cache', '--console=plain'])
    if args.offline:
        command.append('--offline')
    environment = dict(os.environ, JAVA_HOME=str(java), EQLIB_DIR=str(LIBRARY))
    environment['PATH'] = str(java/'bin')+os.pathsep+environment['PATH']
    log = OUTPUT/(target.name+'.log')
    print('Running', target.name, flush=True)
    with log.open('w', encoding='utf-8') as stream:
        stream.write('UNIVERSAL_SHA256 '+CHECKSUM+'\n')
        stream.flush()
        result = subprocess.run(command, cwd=target, env=environment, stdout=stream, stderr=subprocess.STDOUT)
    unchanged = hashlib.sha256(BUNDLE.read_bytes()).hexdigest() == CHECKSUM
    valid = result.returncode == 0 and unchanged
    print('PASS' if valid else 'FAIL', target.name, flush=True)
    return target.name, {'exit_code': result.returncode if unchanged else 1, 'log': str(log), 'sha256': CHECKSUM}


targets = [p for p in sorted((ROOT/'versions').iterdir()) if (p/'build.gradle').exists() and (not args.target or p.name in args.target)]
if not targets:
    raise SystemExit('No matching targets')
report = OUTPUT/'results.json'
results = json.loads(report.read_text()) if report.exists() else {}
selected = {}
with ThreadPoolExecutor(max_workers=max(1, args.workers)) as pool:
    for future in as_completed([pool.submit(run, target) for target in targets]):
        name, result = future.result()
        results[name] = selected[name] = result
        report.write_text(json.dumps(results, indent=2)+'\n')
raise SystemExit(any(result['exit_code'] for result in selected.values()))

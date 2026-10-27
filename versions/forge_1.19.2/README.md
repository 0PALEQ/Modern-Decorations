# Modern Decorations — Forge 1.19.2

Backport of the Forge 1.20.1 module, using Minecraft 1.19.2, Forge 43.5.2, and Java 17. The original module is retained.

Build from this directory:

```powershell
.\gradlew.bat build --no-watch-fs
```

The installable mod is `build/libs/mdm-3.1-forge-1.19.2.jar`. The sources JAR is for development.

Run the isolated server regression fixture:

```powershell
.\gradlew.bat -I runtime-test.gradle runServer --no-watch-fs
```

Run the client resource smoke test (opens and closes a Minecraft window):

```powershell
.\gradlew.bat -I runtime-test.gradle -I client-test.gradle runClient --no-watch-fs
```

Fixtures live under `src/testmod`, are packaged separately, and are never included in the release JAR. Their world and results are under `build/backport-runtime-run`.

The server fixture checks registered furniture, shapes in every horizontal direction, precise placement NBT, inventory persistence, light toggles, bed sleep/wake in all four directions, held-item precise placement matching its preview plan, creative tab coverage, furniture footprints, and clean shutdown with a placement spanning chunk edges. The client fixture loads all block states and visible item models, the water particle mixin, and clock hand meshes.

Version-specific Java is copied into this module so 1.19.2 API adaptations do not change newer versions. Shared textures, models, recipes, translations, clock geometry, and bed migration code remain shared. The backport uses the 1.19.2 creative tab and material APIs, legacy matrices and registries, pack format 9, and a 1.19.2 offset rendering mixin. Pottery uses stone sounds because decorated pot sounds do not exist in 1.19.2. Three curtain blocks also gain placement entities, and the pouf is included in the creative tab.

The 1.19.2 lighting overrides avoid entity and chunk lookups from the lighting worker, including while saving and unloading.

Validation on 2026-10-24:

- `build` passed, including recipes, translations, clock geometry, and legacy bed migration checks.
- Dedicated Forge server: 343 furniture blocks, 18 lights, 48 beds (sleep/wake in each horizontal direction), 130 containers, held-item precise placement, and cross-chunk placement shutdown passed.
- Forge client: 15,384 block states, 341 creative items, water particle transformation, and four clock hands loaded successfully.
- Release JAR metadata, pack format, mixin classes/refmap, and exclusion of test fixtures verified.
- Original Forge 1.20.1 module still passes `build`.

These are automated build and runtime checks; a manual multiplayer playthrough is not included.

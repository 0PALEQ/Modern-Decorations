# Modern Decorations ? Forge 1.16.5

Backported from the Forge 1.19.2 target. Uses Forge 36.2.42, ForgeGradle 6, and official 1.16.5 mappings.

## Build

Run Gradle with JDK 17. The compiler uses Jabel to produce Java 8 bytecode; Jabel is a build dependency and is not required by players. Install a JDK 8 for development launches (Gradle discovers the local installation).

```powershell
.\gradlew.bat clean build
```

The installable, reobfuscated mod is `build/libs/mdm-3.1-forge-1.16.5.jar`. Use Minecraft 1.16.5 with Forge 36.2.42 and Java 8. Do not install a JAR from another Minecraft target alongside it.

## Development and verification

```powershell
.\gradlew.bat runClient
.\gradlew.bat runServer -PsmokeTest
.\gradlew.bat runClient -PsmokeTest
```

Smoke tests use isolated directories under `build/`, close automatically, and write result markers. The server test checks every registered furniture block, tile-entity validity, precise-placement NBT round trips and synchronization, and light toggles. The client test checks item models and loading all four animated clock hand meshes. Test sources are included only with `-PsmokeTest`; build releases without that property.

## Resources and compatibility

Common models, textures, and translations remain shared. This target composes the older block/item rendering resources and overrides `mdm.mixins.json` locally. Pack format is 6. No extra vanilla textures are needed for 1.16.5. Future exclusive textures belong in `src/main/resources/assets/mdm/textures/`, with local model mappings pointing to the `mdm` namespace.

Later deepslate-tile, moss, and copper sound types use the older stone, grass, and metal equivalents. Sculk game events are omitted because 1.16.5 has no sculk system. Tile entities, chunk-load repair, networking, client rendering, and placement hooks use the 1.16.5 APIs.

Verified on Java 8: 343 furniture blocks, 130 storage blocks, 18 switchable lights, all 343 item models, and four clock hand meshes. Interactive playtesting of furniture placement, sleeping, water, and rendering in a normal world remains advisable before publication. Worlds from newer Minecraft versions cannot be opened directly in 1.16.5.

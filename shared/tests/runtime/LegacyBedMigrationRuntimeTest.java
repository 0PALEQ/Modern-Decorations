import net.fabricmc.api.ModInitializer;

public final class LegacyBedMigrationRuntimeTest implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            LegacyBedMigrationTest.main(new String[]{"--runtime"});
            System.out.println("LEGACY_BED_RUNTIME_MIGRATION_PASSED");
            System.exit(0);
        } catch (Throwable failure) {
            failure.printStackTrace();
            System.exit(1);
        }
    }
}

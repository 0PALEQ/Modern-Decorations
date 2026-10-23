import net.fabricmc.api.ModInitializer;

/** Compatibility entry point for the original guitar placement test. */
public final class PixelPlacementRuntimeTest implements ModInitializer {
    @Override public void onInitialize() { new DecorationPlacementRuntimeTest().onInitialize(); }
}

import com.cookiecraftmods.mdm.client.clock.ClockHandMesh;
import com.cookiecraftmods.mdm.client.clock.ClockTime;
import java.nio.file.Files;
import java.nio.file.Path;

/** Standalone checks for time conversion and each editable hand's orientation. */
public class ClockHandsTest {
    public static void main(String[] args) throws Exception {
        long[] times = {0, 250, 500, 999, 1000, 6000, 12000, 18000, 24000,
                -6000, 24000L * 1000000000L + 250, Long.MAX_VALUE};
        for (long ticks : times) {
            double hours = ((Math.floorMod(ticks, 24000L) / 1000.0) + 6) % 12;
            close(ClockTime.hourAngle(ticks), hours * 30);
            close(ClockTime.minuteAngle(ticks), Math.floorMod(ticks, 1000L) * 0.36);
        }
        close(ClockTime.hourAngle(6250), 7.5);
        close(ClockTime.minuteAngle(6250), 90);
        for (String model : new String[]{"black_wall_clock", "wooden_wall_clock"}) {
            for (String hand : new String[]{"pointer_hour", "pointer_minute"}) {
                Path file = Path.of("shared/resources/common/assets/mdm/models/custom/" + model + ".json");
                ClockHandMesh mesh;
                try (var reader = Files.newBufferedReader(file)) { mesh = ClockHandMesh.read(reader, hand); }
                if (mesh.vertices().size() != 24) throw new AssertionError("Expected six hand faces");
                // Every face must wind toward its declared outward normal.
                for (int i = 0; i < mesh.vertices().size(); i += 4) {
                    var a = mesh.vertices().get(i);
                    var b = mesh.vertices().get(i + 1);
                    var c = mesh.vertices().get(i + 2);
                    float ax = b.x() - a.x(), ay = b.y() - a.y(), az = b.z() - a.z();
                    float bx = c.x() - a.x(), by = c.y() - a.y(), bz = c.z() - a.z();
                    float dot = (ay * bz - az * by) * a.nx()
                            + (az * bx - ax * bz) * a.ny() + (ax * by - ay * bx) * a.nz();
                    if (dot <= 0) throw new AssertionError("Inverted hand face: " + model + "/" + hand);
                }
                double x = mesh.vertices().stream().mapToDouble(ClockHandMesh.Vertex::x).average().orElseThrow();
                double y = mesh.vertices().stream().mapToDouble(ClockHandMesh.Vertex::y).average().orElseThrow();
                double length = Math.hypot(x, y);
                for (int angle = 0; angle < 360; angle += 90) {
                    double radians = Math.toRadians(mesh.uprightRotation() + angle);
                    // A north-facing clock is viewed from -Z: clockwise goes toward -X.
                    close(x * Math.cos(radians) - y * Math.sin(radians), -length * Math.sin(Math.toRadians(angle)));
                    close(x * Math.sin(radians) + y * Math.cos(radians), length * Math.cos(Math.toRadians(angle)));
                }
            }
        }
        System.out.println("Clock time, all four hand orientations, and face winding passed.");
    }

    private static void close(double actual, double expected) {
        if (Math.abs(actual - expected) > 0.0001) throw new AssertionError(actual + " != " + expected);
    }
}

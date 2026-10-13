import com.cookiecraftmods.mdm.client.clock.ClockHandMesh;
import com.cookiecraftmods.mdm.client.clock.ClockTime;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ClockTest {
    public static void main(String[] args) throws Exception {
        checkTime(0, 180, 0);
        checkTime(250, 187.5f, 90);
        checkTime(500, 195, 180);
        checkTime(6000, 0, 0);
        checkTime(12000, 180, 0);
        checkTime(18000, 0, 0);
        checkTime(24000, 180, 0);
        checkTime(-6000, 0, 0);
        checkTime(24000L * 1000000000L + 250, 187.5f, 90);
        checkTime(999, 209.97f, 359.64f);
        checkTime(1000, 210, 0);

        String[] models = {"black_wall_clock", "wooden_wall_clock"};
        String[] hands = {"pointer_hour", "pointer_minute"};
        float[][] upright = {{-90, 90}, {0, 180}};
        for (int c = 0; c < models.length; c++) {
            for (int h = 0; h < hands.length; h++) {
                Path model = Path.of(args[0], models[c] + ".json");
                try (var reader = Files.newBufferedReader(model)) {
                    ClockHandMesh mesh = ClockHandMesh.read(reader, hands[h]);
                    near(mesh.uprightRotation(), upright[c][h]);
                    if (mesh.vertices().size() != 24) throw new AssertionError("Missing hand faces");
                    double angle = Math.toRadians(mesh.uprightRotation());
                    double x = 0, y = 0;
                    for (var v : mesh.vertices()) {
                        x += v.x() * Math.cos(angle) - v.y() * Math.sin(angle);
                        y += v.x() * Math.sin(angle) + v.y() * Math.cos(angle);
                    }
                    near((float) x, 0);
                    if (y <= 0) throw new AssertionError("Hand does not point to twelve");
                    for (int i = 0; i < 24; i += 4) {
                        var a = mesh.vertices().get(i);
                        var b = mesh.vertices().get(i + 1);
                        var d = mesh.vertices().get(i + 2);
                        float ax = b.x()-a.x(), ay = b.y()-a.y(), az = b.z()-a.z();
                        float bx = d.x()-a.x(), by = d.y()-a.y(), bz = d.z()-a.z();
                        float dot = (ay*bz-az*by)*a.nx() + (az*bx-ax*bz)*a.ny() + (ax*by-ay*bx)*a.nz();
                        if (dot <= 0) throw new AssertionError("Inverted hand face");
                    }
                }
            }
        }
        System.out.println("Clock time, rollover, hand orientation, and face winding checks passed.");
    }

    private static void checkTime(long ticks, float hour, float minute) {
        near(ClockTime.hourAngle(ticks), hour);
        near(ClockTime.minuteAngle(ticks), minute);
    }

    private static void near(float actual, float expected) {
        if (Math.abs(actual - expected) > 0.001f) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
    }
}

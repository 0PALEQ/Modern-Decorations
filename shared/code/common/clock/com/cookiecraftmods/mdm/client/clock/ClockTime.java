package com.cookiecraftmods.mdm.client.clock;

public final class ClockTime {
    private ClockTime() {}

    public static float hourAngle(long ticks) {
        return (Math.floorMod(ticks, 12000L) + 6000L) % 12000L * (360.0f / 12000.0f);
    }

    public static float minuteAngle(long ticks) {
        return Math.floorMod(ticks, 1000L) * (360.0f / 1000.0f);
    }
}

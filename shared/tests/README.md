# Wall clock checks

From any version directory, run `gradlew.bat verifyClockHands` (also included in
`check`). It verifies Minecraft's 06:00 offset, day wraparound, negative and large
tick values, fractional hours, and the orientation of both hands in both models.

The placed clocks read synchronized world time on each render. The hour hand
moves 360 degrees per 12,000 ticks; the minute hand moves 360 degrees per 1,000
ticks. Minecraft tick 0 represents 06:00.

For an in-game visual check, place both clocks facing each cardinal direction
and use `/time set 0` (06:00), `/time set 6000` (12:00), `/time set 6250` (12:15),
and `/time set 18000` (00:00). Check that sleeping updates the hands, pausing time
freezes them, resource reload preserves them, and inventory models retain hands.

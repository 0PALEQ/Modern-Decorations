package com.cookiecraftmods.mdm.client.clock;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public record ClockHandMesh(List<Vertex> vertices, double pivotZ, float uprightRotation) {
    public record Vertex(float x, float y, float z, float u, float v, float nx, float ny, float nz) {}

    public static ClockHandMesh read(Reader reader, String name) {
        JsonObject model = JsonParser.parseReader(reader).getAsJsonObject();
        for (var entry : model.getAsJsonArray("elements")) {
            JsonObject element = entry.getAsJsonObject();
            if (!element.has("name") || !element.get("name").getAsString().equals(name)) continue;
            float[] from = vector(element.getAsJsonArray("from"));
            float[] to = vector(element.getAsJsonArray("to"));
            float[] pivot = vector(element.getAsJsonObject("rotation").getAsJsonArray("origin"));
            List<Vertex> vertices = new ArrayList<>();
            for (var faceEntry : element.getAsJsonObject("faces").entrySet()) {
                JsonObject face = faceEntry.getValue().getAsJsonObject();
                float[] uv = vector(face.getAsJsonArray("uv"));
                int turn = face.has("rotation") ? face.get("rotation").getAsInt() / 90 : 0;
                int[][] corners;
                float nx = 0, ny = 0, nz = 0;
                switch (faceEntry.getKey()) {
                    case "north" -> { corners = new int[][]{{1,1,0},{1,0,0},{0,0,0},{0,1,0}}; nz = -1; }
                    case "south" -> { corners = new int[][]{{0,1,1},{0,0,1},{1,0,1},{1,1,1}}; nz = 1; }
                    case "west" -> { corners = new int[][]{{0,1,0},{0,0,0},{0,0,1},{0,1,1}}; nx = -1; }
                    case "east" -> { corners = new int[][]{{1,1,1},{1,0,1},{1,0,0},{1,1,0}}; nx = 1; }
                    case "up" -> { corners = new int[][]{{0,1,0},{0,1,1},{1,1,1},{1,1,0}}; ny = 1; }
                    case "down" -> { corners = new int[][]{{0,0,1},{0,0,0},{1,0,0},{1,0,1}}; ny = -1; }
                    default -> throw new IllegalArgumentException("Unknown clock face " + faceEntry.getKey());
                }
                for (int i = 0; i < 4; i++) {
                    int t = (i + turn) % 4;
                    int[] c = corners[i];
                    vertices.add(new Vertex(
                            ((c[0] == 0 ? from[0] : to[0]) - pivot[0]) / 16,
                            ((c[1] == 0 ? from[1] : to[1]) - pivot[1]) / 16,
                            ((c[2] == 0 ? from[2] : to[2]) - pivot[2]) / 16,
                            uv[t < 2 ? 0 : 2] / 16, uv[t == 0 || t == 3 ? 1 : 3] / 16,
                            nx, ny, nz));
                }
            }
            float dx = (from[0] + to[0]) / 2 - pivot[0];
            float dy = (from[1] + to[1]) / 2 - pivot[1];
            return new ClockHandMesh(List.copyOf(vertices), pivot[2] / 16.0,
                    (float) Math.toDegrees(Math.atan2(dx, dy)));
        }
        throw new IllegalArgumentException("Missing clock hand " + name);
    }

    private static float[] vector(JsonArray array) {
        float[] result = new float[array.size()];
        for (int i = 0; i < result.length; i++) result[i] = array.get(i).getAsFloat();
        return result;
    }
}

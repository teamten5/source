package io.github.teamten5;

public enum Orientation {
    NORTH,  // Default
    EAST,
    SOUTH,
    WEST;

    static Orientation fromString(String string) {
        string = string.toUpperCase();
        return switch (string) {
            case "NORTH", "N" -> NORTH;
            case "EAST", "E" -> EAST;
            case "SOUTH", "S" -> SOUTH;
            case "WEST", "W" -> WEST;
            default -> throw new InvalidGameDataException("invalid orientation string");
        };
    }
}
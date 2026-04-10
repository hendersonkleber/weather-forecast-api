package com.hendersonkleber.weatherforecast.entity;

public enum WindDirection {
    NORTH(0),
    NORTH_NORTHEAST(22.5),
    NORTHEAST(45),
    EAST_NORTHEAST(67.5),
    EAST(90),
    EAST_SOUTHEAST(112.5),
    SOUTHEAST(135),
    SOUTH_SOUTHEAST(157.5),
    SOUTH(180),
    SOUTH_SOUTHWEST(202.5),
    SOUTHWEST(225),
    WEST_SOUTHWEST(247.5),
    WEST(270),
    WEST_NORTHWEST(292.5),
    NORTHWEST(315),
    NORTH_NORTHWEST(337.5);

    private final double degrees;

    WindDirection(double degrees) {
        this.degrees = degrees;
    }

    public double getDegrees() {
        return degrees;
    }

    public static WindDirection fromDegrees(double degrees) {
        double normalized = (degrees % 360 + 360) % 360;
        int index = (int) Math.round(normalized / 22.5) % 16;
        return values()[index];
    }
}

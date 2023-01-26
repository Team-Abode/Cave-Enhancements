package com.teamabode.cave_enhancements.common.block.weathering;

public enum WeatherState {
    UNAFFECTED(false),
    EXPOSED(false),
    WEATHERED(false),
    OXIDIZED(false),
    WAXED_UNAFFECTED(true),
    WAXED_EXPOSED(true),
    WAXED_WEATHERED(true),
    WAXED_OXIDIZED(true);

    private final boolean isWaxed;

    WeatherState(boolean waxed) {
        this.isWaxed = waxed;
    }

    public boolean isWaxed() {
        return this.isWaxed;
    }
}

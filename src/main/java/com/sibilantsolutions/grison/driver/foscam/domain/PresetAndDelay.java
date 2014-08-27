package com.sibilantsolutions.grison.driver.foscam.domain;

public class PresetAndDelay
{
    private int preset;
    private long durationMs;

    public PresetAndDelay( int preset, long durationMs )
    {
        this.preset = preset;
        this.durationMs = durationMs;
    }

    public long getDurationMs()
    {
        return durationMs;
    }

    public int getPreset()
    {
        return preset;
    }

}

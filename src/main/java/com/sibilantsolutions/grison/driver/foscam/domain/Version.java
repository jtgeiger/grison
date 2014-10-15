package com.sibilantsolutions.grison.driver.foscam.domain;

public class Version
{

    private final int major;
    private final int minor;
    private final int patch;
    private final int build;

    public Version( int major, int minor, int patch, int build )
    {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.build = build;
    }

    public int getMajor()
    {
        return major;
    }

    public int getMinor()
    {
        return minor;
    }

    public int getPatch()
    {
        return patch;
    }

    public int getBuild()
    {
        return build;
    }

    @Override
    public String toString()
    {
        return "" + major + '.' + minor + '.' + patch + '.' + build;
    }

}

package com.sibilantsolutions.grison.driver.foscam.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return major == version.major &&
                minor == version.minor &&
                patch == version.patch &&
                build == version.build;
    }

    @Override
    public int hashCode() {

        return Objects.hash(major, minor, patch, build);
    }

}

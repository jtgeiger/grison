package com.sibilantsolutions.grison.driver.foscam.entity;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchRespTextEntity implements FoscamTextEntity {

    public abstract String cameraId();

    public abstract String cameraName();

    public abstract InetSocketAddress address();

    public abstract InetAddress mask();

    public abstract InetAddress gateway();

    public abstract InetAddress dns();

    public abstract VersionEntity sysSoftwareVersion();

    public abstract VersionEntity appSoftwareVersion();

    public abstract boolean isDhcpEnabled();

    public static SearchRespTextEntity.Builder builder() {
        return new AutoValue_SearchRespTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder cameraId(String cameraId);

        public abstract Builder cameraName(String cameraName);

        public abstract Builder address(InetSocketAddress address);

        public abstract Builder mask(InetAddress mask);

        public abstract Builder gateway(InetAddress gateway);

        public abstract Builder dns(InetAddress dns);

        public abstract Builder sysSoftwareVersion(VersionEntity sysSoftwareVersion);

        public abstract Builder appSoftwareVersion(VersionEntity appSoftwareVersion);

        public abstract Builder isDhcpEnabled(boolean isDhcpEnabled);

        public abstract SearchRespTextEntity build();
    }
}

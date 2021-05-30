package com.sibilantsolutions.grison.driver.foscam.entity;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class InitReqTextEntity implements FoscamTextEntity {

    public abstract String cameraId();
    public abstract String username();
    public abstract String password();
    public abstract InetSocketAddress address();
    public abstract InetAddress mask();
    public abstract InetAddress gateway();
    public abstract InetAddress dns();

    public static InitReqTextEntity.Builder builder() {
        return new AutoValue_InitReqTextEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        
        public abstract Builder cameraId(String cameraId);

        public abstract Builder username(String user);

        public abstract Builder password(String password);

        public abstract Builder address(InetSocketAddress address);

        public abstract Builder mask(InetAddress mask);

        public abstract Builder gateway(InetAddress gateway);

        public abstract Builder dns(InetAddress dns);

        public abstract InitReqTextEntity build();
    }
}

package com.sibilantsolutions.grison.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(CameraConnectionProperties.CONNECTION_PREFIX)
public class CameraConnectionProperties {

    // Prefix for all property names.
    // Final property names will be like "connection.host", "connection.port"...
    static final String CONNECTION_PREFIX = "connection";

    private String host;
    private int port;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.lamp.decoration.core.spring.plugs;

public class Swagger3Configuration {

    private boolean enabled;

    private String filePath = "/decoration/swagger3.yaml";


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

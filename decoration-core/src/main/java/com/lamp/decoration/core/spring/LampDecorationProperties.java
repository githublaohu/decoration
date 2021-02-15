package com.lamp.decoration.core.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LampDecorationProperties.LAMP_DECORATION_PREFIX)
public class LampDecorationProperties {
    
    public static final String  LAMP_DECORATION_PREFIX = "lamp.decoration";
}

/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.decoration.core.spring.plugs;

import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author laohu
 */
public class DecorationCorsRegistry implements WebMvcConfigurer {

    private final List<DecorationCorsConfiguration> corsConfigurationList;

    private final boolean corsEnable;


    public DecorationCorsRegistry(boolean corsEnable, List<DecorationCorsConfiguration> corsConfigurationList) {
        this.corsConfigurationList = corsConfigurationList;
        this.corsEnable = corsEnable;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {


        if (this.corsEnable && (Objects.isNull(this.corsConfigurationList) || corsConfigurationList.isEmpty())) {
            registry.addMapping("/**").allowedOrigins("*");
            return;
        }

        if(Objects.isNull(this.corsConfigurationList)){
            return;
        }

        for (DecorationCorsConfiguration corsConfiguration : corsConfigurationList) {
            if (Objects.isNull(corsConfiguration.getPathPattern())) {
                throw new RuntimeException(" pathPattern is null. ");
            }

            CorsRegistration corsRegistration =
                    registry.addMapping(corsConfiguration.getPathPattern());
            CorsConfiguration configuration = null;
            try {
                Field configField = CorsRegistration.class.getDeclaredField("config");
                configField.setAccessible(true);
                configuration = (CorsConfiguration) configField.get(corsRegistration);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (Objects.nonNull(corsConfiguration.getMaxAge())) {
                configuration.setMaxAge(corsConfiguration.getMaxAge());
            }
            if (!CollectionUtils.isEmpty(corsConfiguration.getAllowedOrigins())) {
                configuration.setAllowedOrigins(corsConfiguration.getAllowedOrigins());
            }
            if (!CollectionUtils.isEmpty(corsConfiguration.getAllowedHeaders())) {
                configuration.setAllowedHeaders(corsConfiguration.getAllowedHeaders());
            }
            if (!CollectionUtils.isEmpty(corsConfiguration.getExposedHeaders())) {
                configuration.setExposedHeaders(corsConfiguration.getExposedHeaders());
            }
            if (!CollectionUtils.isEmpty(corsConfiguration.getAllowedMethods())) {
                configuration.setAllowedMethods(corsConfiguration.getAllowedMethods());
            }
            configuration.setAllowCredentials(corsConfiguration.getAllowCredentials());

        }
    }
}

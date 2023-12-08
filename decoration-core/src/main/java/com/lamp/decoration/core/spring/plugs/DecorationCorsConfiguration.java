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

import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * @author laohu
 */
public class DecorationCorsConfiguration {

    private String pathPattern;


    private List<String> allowedOrigins;


    private List<String> allowedMethods;


    private List<HttpMethod> resolvedMethods;


    private List<String> allowedHeaders;


    private List<String> exposedHeaders;


    private Boolean allowCredentials;


    private Long maxAge;


    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }


    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins( List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }


    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods( List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }


    public List<HttpMethod> getResolvedMethods() {
        return resolvedMethods;
    }

    public void setResolvedMethods( List<HttpMethod> resolvedMethods) {
        this.resolvedMethods = resolvedMethods;
    }


    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders( List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }


    public List<String> getExposedHeaders() {
        return exposedHeaders;
    }

    public void setExposedHeaders( List<String> exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }


    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials( Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }


    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge( Long maxAge) {
        this.maxAge = maxAge;
    }
}

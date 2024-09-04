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

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.yaml.snakeyaml.Yaml;

import java.util.Objects;

/**
 * @author laohu
 */
public class Swagger3Plugs {

    public static OpenAPI createSwagger(String path) {
        Resource[] resources = null;
        try {
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            resources = resourcePatternResolver.getResources(path);
        }catch (Exception e){
            throw  new RuntimeException("file path read error", e);
        }
        if (Objects.isNull(resources) || resources.length == 0) {
            throw  new RuntimeException(String.format("%s non-existent" , path));
        }
        try {
            Resource resource = resources[0];
            byte[] bytes = new byte[(int) resource.contentLength()];
            resource.getInputStream().read(bytes);
            Yaml yaml = new Yaml();
            return yaml.loadAs(new String(bytes), OpenAPI.class);
        }catch(Exception e){
            throw  new RuntimeException(" data serialize error" , e);
        }
    }
}

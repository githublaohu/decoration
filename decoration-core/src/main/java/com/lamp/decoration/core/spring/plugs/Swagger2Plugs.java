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

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Objects;

/**
 * @author laohu
 */
public class Swagger2Plugs {

    private Swagger2Configuration swagger2Config;

    public static Docket getDocket(Swagger2Configuration swagger2Config) {
        Swagger2Plugs swagger2Plugs = new Swagger2Plugs();
        return swagger2Plugs.petApi(swagger2Config);
    }

    public Docket petApi(Swagger2Configuration swagger2Config) {
        this.swagger2Config = swagger2Config;
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(this.apiInfo());
        this.config(docket);
        return this.apiSelector(docket.select());
    }


    private void config(Docket docket) {
        if (!swagger2Config.isEnabled()) {
            docket.enable(swagger2Config.isEnabled());
        }

        if (Objects.nonNull(swagger2Config.getProduces())) {
            docket.produces(swagger2Config.getProduces());
        }

        if (Objects.nonNull(swagger2Config.getProtocols())) {
            docket.protocols(swagger2Config.getProtocols());
        }

        if (Objects.nonNull(swagger2Config.getTags())) {
            //docket.tags(swagger2Config.getTags());
        }

        if (Objects.nonNull(swagger2Config.getGroupName())) {
            docket.groupName(swagger2Config.getGroupName());
        }
    }

    /**
     * 该套 API 说明，包含作者、简介、版本、host、服务URL
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swagger2Config.getApiInfo().getTitle())
                .version(swagger2Config.getApiInfo().getVersion())
                .description(swagger2Config.getApiInfo().getVersion())
                .contact(swagger2Config.getApiInfo().getContact())
                .license(swagger2Config.getApiInfo().getLicense())
                .licenseUrl(swagger2Config.getApiInfo().getLicenseUrl())
                .termsOfServiceUrl(swagger2Config.getApiInfo().getTermsOfServiceUrl())
                .build();
    }

    private Docket apiSelector(ApiSelectorBuilder apiSelectorBuilder) {
        for (String api : swagger2Config.getApiSelector().getApis()) {
            apiSelectorBuilder.apis(RequestHandlerSelectors.basePackage(api));
        }
        if (Objects.nonNull(swagger2Config.getApiSelector().getPaths()) && !swagger2Config.getApiSelector().getPaths().isEmpty()) {
            for (String path : swagger2Config.getApiSelector().getPaths()) {
                apiSelectorBuilder.paths(PathSelectors.ant(path));
            }
        } else {
            apiSelectorBuilder.paths(PathSelectors.any());
        }
        return apiSelectorBuilder.build();
    }
}

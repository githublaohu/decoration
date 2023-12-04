package com.lamp.decoration.core.spring.plugs;


import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author laohu
 */
public class Swagger2Configuration {


    private Set<String> protocols;
    private Set<String> produces;
    private Set<String> consumes;
    private Set<Tag> tags;

    private String groupName;
    private boolean enabled;

    private ApiInfo apiInfo;

    private ApiSelector apiSelector;


    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public ApiSelector getApiSelector() {
        return apiSelector;
    }

    public void setApiSelector(ApiSelector apiSelector) {
        this.apiSelector = apiSelector;
    }


    public Set<String> getProtocols() {
        return protocols;
    }

    public void setProtocols(Set<String> protocols) {
        this.protocols = protocols;
    }

    public Set<String> getProduces() {
        return produces;
    }

    public void setProduces(Set<String> produces) {
        this.produces = produces;
    }

    public Set<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(Set<String> consumes) {
        this.consumes = consumes;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static class ApiSelector {

        private List<String> paths;

        private List<String> apis;

        public List<String> getPaths() {
            return paths;
        }

        public void setPaths(List<String> paths) {
            this.paths = paths;
        }

        public List<String> getApis() {
            return apis;
        }

        public void setApis(List<String> apis) {
            this.apis = apis;
        }
    }

    public static class ApiInfo {

        private String title;

        private String description;

        private String termsOfServiceUrl;

        private Contact contact;

        private String license;

        private String licenseUrl;

        private String version;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}

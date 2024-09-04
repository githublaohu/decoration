package com.lamp.decoration.core.result.third;

public class WangeditorResultObject {

    private Integer errno;

    private WangeditorResultData data;


    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public WangeditorResultData getData() {
        return data;
    }

    public void setData(WangeditorResultData data) {
        this.data = data;
    }

    public static class WangeditorResultData{

        private String url;

        private String alt;

        private String href;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}

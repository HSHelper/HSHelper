package ru.hsHelper.api.configs;

import java.util.Map;

public class PushNotifyConf {
    private String title;
    private String body;
    private Map<String, String> data;

    public PushNotifyConf(String title, String body, Map<String, String> data) {
        this.title = title;
        this.body = body;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}

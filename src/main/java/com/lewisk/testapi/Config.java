package com.lewisk.testapi;

public class Config {
    private  String apigeeUrl;
    private  String clientId;
    private  String clientSecret;

    public Config()
    {
        super();
    }

    public Config(String apigeeUrl, String clientId, String clientSecret) {
        this.apigeeUrl = apigeeUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getApigeeUrl() {
        return apigeeUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}

package uz.yeoju.yeoju_app.controller;

public interface BaseUrl {
    String VERSION = "/v1";
    String BASE = "/api" + VERSION;
    String TYPE = BASE + "/desktop";
    //String SMS_SERVICE_URL= "https://rest.messagebird.com/messages";
    String BASE_URL = TYPE;

}

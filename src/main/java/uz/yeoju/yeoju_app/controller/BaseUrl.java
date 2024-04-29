package uz.yeoju.yeoju_app.controller;

public interface BaseUrl {
    String VERSION = "/v1";
    String BASE = "/api" + VERSION;
    String TYPE = BASE + "/desktop";
    String TYPE_BOT = BASE + "/bot";
    //String SMS_SERVICE_URL= "https://rest.messagebird.com/messages";
    String BASE_URL = TYPE;
    String BASE_BOT_URL = TYPE_BOT;

}

package uz.yeoju.yeoju_app.bot.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.yeoju.yeoju_app.bot.BotRestConstants;
import uz.yeoju.yeoju_app.bot.payload.ResultTelegram;
import uz.yeoju.yeoju_app.bot.payload.SendPhotoOwn;

@FeignClient(url = BotRestConstants.TELEGRAM_BASE_URL_WITHOUT_BOT,name = "TelegramFeign")
public interface TelegramFeign {

    @PostMapping("{path}/sendMessage")
    ResultTelegram sendMessageToUser(@PathVariable String path, @RequestBody SendMessage sendMessage);

    @PostMapping("{path}/sendPhoto")
    ResultTelegram sendPhotoToUser(@PathVariable String path, @RequestBody SendPhotoOwn sendPhotoOwn);
}

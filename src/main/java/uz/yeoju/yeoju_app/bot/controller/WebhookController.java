package uz.yeoju.yeoju_app.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.yeoju.yeoju_app.bot.service.TgService;
import uz.yeoju.yeoju_app.controller.BaseUrl;

@RestController
@RequestMapping(BaseUrl.BASE_BOT_URL+"/telegram")
@RequiredArgsConstructor
public class WebhookController {
    private final TgService tgService;

    String token = "6334964758:AAFMbGS2Z09IslUf5-yr2yRFFxTN9JXtjDs";

    @PostMapping
    public void ketmon(@RequestBody Update update){
//        System.out.println(update);
        tgService.updateWait(update);
    }

}

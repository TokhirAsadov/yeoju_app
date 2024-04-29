package uz.yeoju.yeoju_app.controller.bot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;

@RestController
@RequestMapping(BaseUrl.BASE_BOT_URL+"/telegram")
public class TelegramBotController {

    String token = "6334964758:AAFMbGS2Z09IslUf5-yr2yRFFxTN9JXtjDs";

}

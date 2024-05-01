package uz.yeoju.yeoju_app.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;

@RestController
@RequestMapping(BaseUrl.BASE_BOT_URL+"/admin")
@RequiredArgsConstructor
public class BotAdminController {

}

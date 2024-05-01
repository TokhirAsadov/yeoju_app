package uz.yeoju.yeoju_app.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.bot.service.BotAdminService;
import uz.yeoju.yeoju_app.controller.BaseUrl;

@RestController
@RequestMapping(BaseUrl.BASE_BOT_URL+"/admin")
@RequiredArgsConstructor
public class BotAdminController {

    private final BotAdminService botAdminService;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam String message) {
         return botAdminService.sendMessage(message);
    }

    @GetMapping("/sendPhoto")
    public String sendPhoto(@RequestParam String fileName, @RequestParam String text) {
        return botAdminService.sendPhoto(fileName,text);
    }
}

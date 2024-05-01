package uz.yeoju.yeoju_app.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.yeoju.yeoju_app.bot.BotRestConstants;
import uz.yeoju.yeoju_app.bot.feign.TelegramFeign;
import uz.yeoju.yeoju_app.bot.payload.ResultTelegram;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BotAdminService {
//    private final RestTemplate getRestTemplate;
    private final TelegramFeign telegramFeign;

    private Set<String> userChatIdList = new HashSet<>(Arrays.asList("645257161"));

    public String sendMessage(String message) {
        for (String userChatId : userChatIdList) {
            SendMessage sendMessage = new SendMessage(userChatId,message);
//            ResultTelegram object = getRestTemplate.postForObject(BotRestConstants.TELEGRAM_BASE_URL + BotRestConstants.BOT_TOKEN + "/sendMessage", sendMessage, ResultTelegram.class);
            ResultTelegram resultTelegram = telegramFeign.sendMessageToUser(BotRestConstants.TELEGRAM_FEIGN_PATH, sendMessage);
            System.out.println(resultTelegram);
        }

        return "Message was sent";
    }
}

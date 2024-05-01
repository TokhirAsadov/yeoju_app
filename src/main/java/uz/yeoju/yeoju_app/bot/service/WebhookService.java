package uz.yeoju.yeoju_app.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.yeoju.yeoju_app.bot.BotRestConstants;
import uz.yeoju.yeoju_app.bot.payload.ResultTelegram;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final RestTemplate getRestTemplate;

    public void whenStart(Update update){
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId(),"Xush kelibsiz!.");
        ResultTelegram object = getRestTemplate.postForObject(BotRestConstants.TELEGRAM_BASE_URL + BotRestConstants.BOT_TOKEN + "/sendMessage", sendMessage, ResultTelegram.class);
        System.out.println(object);
    }
}

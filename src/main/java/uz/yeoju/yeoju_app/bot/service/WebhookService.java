package uz.yeoju.yeoju_app.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.yeoju.yeoju_app.bot.BotRestConstants;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final RestTemplate getRestTemplate;

    public void whenStart(Update update){
        System.out.println("2.ok->  ...");
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId(),"Xush kelibsiz!.");
        Object object = getRestTemplate.postForObject(BotRestConstants.TELEGRAM_BASE_URL + BotRestConstants.BOT_TOKEN + "/sendMessage", sendMessage, Object.class);
        System.out.println(object);
    }
}

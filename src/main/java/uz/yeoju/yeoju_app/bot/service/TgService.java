package uz.yeoju.yeoju_app.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TgService {

    private final WebhookService webhookService;

    public void updateWait(Update update){
        System.out.println("ok-> "+update);
        if (update.hasMessage()){
            String text = update.getMessage().getText();
            switch (text){
                case "/start":
                    webhookService.whenStart(update);
                    break;
                default:
                    System.out.println("no thing");
            }
        }
    }
}

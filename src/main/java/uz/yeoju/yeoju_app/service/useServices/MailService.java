package uz.yeoju.yeoju_app.service.useServices;


import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import freemarker.template.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.UserRepository;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    public final JavaMailSender sender;

    public final UserRepository userRepo;

    public final Configuration configuration;


    public ApiResponse sendHTML(User user) {
        try {
            Map<String,Object> model=new HashMap<>();
            model.put("user", user);
            MimeMessage mimeMessage=sender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//            helper.addAttachment("logo_nav.png", new ClassPathResource("logo_nav.png"));
            Template template=configuration.getTemplate("email-template.ftl");
            String html= FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            helper.setTo(user.getEmail());
            helper.setFrom("asadovtohir2002@gmail.com","YTIT");
            helper.setSubject("YTIT");
            helper.setText(html,true);
            sender.send(mimeMessage);
            return new ApiResponse(true,"Sended");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ApiResponse(false,"Error");
    }
}

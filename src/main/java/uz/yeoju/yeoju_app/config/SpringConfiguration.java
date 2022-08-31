package uz.yeoju.yeoju_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class SpringConfiguration {

    @Value("${app.sms.username}")
    private String gatewayUsername;
    @Value("${app.sms.password}")
    private String gatewayPassword;
    @Value("${app.sms.url}")
    private String gatewayUrl;

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public RestTemplate smsClient(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .basicAuthentication(gatewayUsername, gatewayPassword)
                .rootUri(gatewayUrl)
                .build();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheSeconds((int) TimeUnit.HOURS.toSeconds(1));
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
}

package uz.yeoju.yeoju_app.controller;
 
import lombok.SneakyThrows; 
import org.springframework.web.bind.annotation.*; 
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginRedirectController {

    @SneakyThrows
    @GetMapping("/")
    public void login(  HttpServletResponse response){
        response.sendRedirect("/login");
    }

}

package uz.yeoju.yeoju_app.secret;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.yeoju.yeoju_app.entity.User;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${spring.jjwt.secretKey}")
    private String secretKey;

    @Value("${spring.jjwt.expiredDate}")
    private Long expiredDate;

    public String generateToken(User user){
        Date issueDate = new Date();
        Date expiredDates = new Date(issueDate.getTime()+expiredDate);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(issueDate)
                .setExpiration(expiredDates)
                .signWith(SignatureAlgorithm.ES512,secretKey)
                .claim("roles",user.getAuthorities())
                .compact();
    }


}

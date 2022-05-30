package uz.yeoju.yeoju_app.secret;

import io.jsonwebtoken.*;
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
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .claim("roles", user.getAuthorities())
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            System.err.println("Muddati o`tgan");
        }catch (MalformedJwtException m){
            System.err.println("Buzilgan token");
        }catch (SignatureException s){
            System.err.println("Kalit so`z xato");
        }catch (UnsupportedJwtException u){
            System.err.println("Qo`llanilmagan token");
        }catch (IllegalArgumentException i){
            System.err.println("Bo`sh token");
        }
        return false;
    }

    public String getUserIdFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

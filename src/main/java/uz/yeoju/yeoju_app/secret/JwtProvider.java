package uz.yeoju.yeoju_app.secret;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.yeoju.yeoju_app.entity.User;

import javax.servlet.http.HttpServletResponse;
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

    public boolean validateToken(String token, HttpServletResponse response){
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            System.err.println("Muddati o`tgan");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        } catch (MalformedJwtException m) {
            System.err.println("Buzilgan token");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        } catch (SignatureException s) {
            System.err.println("Kalit so`z xato");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        } catch (UnsupportedJwtException u) {
            System.err.println("Qo`llanilmagan token");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        } catch (IllegalArgumentException i) {
            System.err.println("Bo`sh token");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
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

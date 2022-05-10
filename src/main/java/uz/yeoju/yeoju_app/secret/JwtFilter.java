package uz.yeoju.yeoju_app.secret;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider provider;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token!=null){
            User user = getUserFromToken(token);
            if (
                            user!=null
                    &&
                            user.isAccountNonExpired()
                    &&
                            user.isAccountNonLocked()
                    &&
                            user.isCredentialsNonExpired()
                    &&
                            user.isEnabled()
            ) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request,response);

    }

    private User getUserFromToken(String token) {
        boolean validateToken = provider.validateToken(token);
        if (validateToken){
            String userIdFromToken = provider.getUserIdFromToken(token);
            return userRepository.findById(UUID.fromString(userIdFromToken)).get();
        }
        return null;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header!=null?header.substring(7):null;
    }
}

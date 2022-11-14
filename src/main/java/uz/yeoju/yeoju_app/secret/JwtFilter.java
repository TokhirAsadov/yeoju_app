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

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider provider;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       /* String token = getTokenFromRequest(request);

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

        filterChain.doFilter(request,response);*/

        String tokenFromRequest = getTokenFromRequest(request);
        if (tokenFromRequest != null) {
            User user = getUserFromToken(tokenFromRequest);
            if (user != null) {
                if (user.isAccountNonExpired()) {
                    if (user.isAccountNonLocked()) {
                        if (user.isCredentialsNonExpired()) {
                            if (user.isEnabled()) {
                                UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken(
                                                user,
                                                null,
                                                user.getAuthorities()
                                        );
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            } else {
                                System.err.println("User Disabled");
                            }
                        } else {
                            System.err.println("User Credentials Expired");
                        }
                    } else {
                        System.err.println("User Locked");
                    }
                } else {
                    System.err.println("User Expired");
                }
            }
            else {
//                response.sendRedirect("/login");
                response.sendRedirect("http://localhost:3000/login");
                response.setStatus(200);
            }
        }
        filterChain.doFilter(request, response);

    }

    private User getUserFromToken(String token) {
        System.out.println(token);
        boolean validateToken = provider.validateToken(token);
        if (validateToken){
            String userIdFromToken = provider.getUserIdFromToken(token);
            return userRepository.findById(userIdFromToken/*UUID.fromString(userIdFromToken)*/).get();
        }
        return null;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header!=null?header.substring(7):null;
    }
}

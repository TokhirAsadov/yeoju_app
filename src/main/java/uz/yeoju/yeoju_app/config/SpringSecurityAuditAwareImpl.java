package uz.yeoju.yeoju_app.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.yeoju.yeoju_app.entity.User;

import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null
                &&
            auth.isAuthenticated()
            &&
            !"anonymousUser".equals(""+auth.getPrincipal())
        ){
            return Optional.of(((User)auth.getPrincipal()).getId());
        }

        return Optional.empty();
    }
}

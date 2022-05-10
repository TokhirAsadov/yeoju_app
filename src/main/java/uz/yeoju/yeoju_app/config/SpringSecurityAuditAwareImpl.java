package uz.yeoju.yeoju_app.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.yeoju.yeoju_app.entity.User;

import java.util.Optional;
import java.util.UUID;

public class SpringSecurityAuditAwareImpl implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null
                &&
            auth.isAuthenticated()
            &&
            "anonymousUser".equals(""+auth.getPrincipal())
        ){
            return Optional.of(((User)auth.getPrincipal()).getId());
        }

        return Optional.empty();
    }
}

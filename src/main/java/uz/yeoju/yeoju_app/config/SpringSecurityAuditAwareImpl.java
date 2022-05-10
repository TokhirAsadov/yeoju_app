package uz.yeoju.yeoju_app.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

public class SpringSecurityAuditAwareImpl implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        return Optional.empty();
    }
}

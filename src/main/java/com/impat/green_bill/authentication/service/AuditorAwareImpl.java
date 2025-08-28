package com.impat.green_bill.authentication.service;
import com.impat.green_bill.entities.User;
import com.impat.green_bill.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Long> {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuditorAwareImpl(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            // Get current HTTP request
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return Optional.empty();

            HttpServletRequest request = attributes.getRequest();
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) return Optional.empty();

            String jwt = authHeader.substring(7);
            String phoneNumber = jwtService.extractPhoneNumber(jwt);

            // Fetch user by phoneNumber
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            return userOpt.map(user -> {
                try {
                    return Long.parseLong(user.getId());
                } catch (NumberFormatException e) {
                    return null;
                }
            });
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

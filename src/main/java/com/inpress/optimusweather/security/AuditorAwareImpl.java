package com.inpress.optimusweather.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(getUsernameFromContext());
    }

    @NotNull
    private String getUsernameFromContext() {
        return "oveisTest";
    }
}

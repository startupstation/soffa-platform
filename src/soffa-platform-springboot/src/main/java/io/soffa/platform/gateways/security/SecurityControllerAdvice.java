package io.soffa.platform.gateways.security;

import io.soffa.platform.core.security.model.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ModelAttribute
    public UserPrincipal customPrincipal(Authentication a) {
        if (a == null || a.getPrincipal() == null || (!(a.getPrincipal() instanceof UserPrincipal))) {
            return null;
        }
        return (UserPrincipal) a.getPrincipal();
    }

}

package io.soffa.platform.core.security.model;

import lombok.Getter;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserPrincipal implements Principal {

    private final UserId userId;
    private final String email;
    private final Set<String> roles = new HashSet<>();

    public UserPrincipal(UserId userId, String email, Set<String> roles) {
        this.userId = userId;
        this.email = email;
        if (roles != null) {
            this.roles.addAll(roles.stream().map(String::toLowerCase).collect(Collectors.toSet()));
        }
    }

    @Override
    public String getName() {
        return userId.getValue();
    }

    public boolean hasRole(String role) {
        return this.roles.contains(role.toLowerCase());
    }


}

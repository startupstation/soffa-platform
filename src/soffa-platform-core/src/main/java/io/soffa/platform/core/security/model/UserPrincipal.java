package io.soffa.platform.core.security.model;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements Principal {

    private final String username;
    private final String email;
    private final Set<String> roles = new HashSet<>();

    public UserPrincipal(String username, String email, Set<String> roles) {
        this.username = username;
        this.email = email;
        if (roles != null ) {
            this.roles.addAll(roles.stream().map(String::toLowerCase).collect(Collectors.toSet()));
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return email;
    }

    public boolean hasRole(String role) {
        return this.roles.contains(role.toLowerCase());
    }


}

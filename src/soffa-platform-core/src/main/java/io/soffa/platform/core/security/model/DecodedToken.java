package io.soffa.platform.core.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DecodedToken {

    private String token;
    private String username;
    private String email;
    private Set<String> roles;

    public DecodedToken(String token) {
        this.token = token;
    }

    public static final DecodedToken ANONYMOUS = new DecodedToken("guest");

}

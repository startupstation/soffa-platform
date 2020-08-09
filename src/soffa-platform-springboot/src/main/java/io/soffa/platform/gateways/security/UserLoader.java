package io.soffa.platform.gateways.security;

import io.soffa.platform.core.security.model.UserId;

import java.util.Optional;

public interface UserLoader {

    Optional<UserId> loadUserId(String username);

}

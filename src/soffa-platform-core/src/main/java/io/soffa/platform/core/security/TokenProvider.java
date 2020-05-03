package io.soffa.platform.core.security;

import io.soffa.platform.core.security.model.DecodedToken;
import reactor.core.publisher.Mono;

public interface TokenProvider {

    Mono<DecodedToken> decode(String token);

}

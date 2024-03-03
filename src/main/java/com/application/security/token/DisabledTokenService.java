package com.application.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.stereotype.Service;

@Service
public class DisabledTokenService {

    @Autowired
    BoundSetOperations<String, String> boundSetOperationsForDisabledTokens;

    public void save(DisabledToken disabledToken) {
        boundSetOperationsForDisabledTokens.add(disabledToken.getAccessToken());
    }

    public Boolean isDisabled(String accessToken){
        return boundSetOperationsForDisabledTokens.isMember(accessToken);
    }

}

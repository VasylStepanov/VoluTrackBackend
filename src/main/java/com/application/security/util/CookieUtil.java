package com.application.security.util;

import jakarta.servlet.http.Cookie;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CookieUtil {

    @Value("${security.access-token.token-name}")
    String accessTokenName;

    @Value("${security.access-token.expiration}")
    long duration;

    @Autowired
    SecurityCipher securityCipher;

    public HttpCookie createAccessTokenCookie(String token){
        return ResponseCookie.from(accessTokenName, securityCipher.encrypt(token)).httpOnly(true).secure(true).maxAge(duration).path("/").build();
    }

    public String getAccessTokenCookie(Cookie[] cookies){
        return Stream.of(cookies)
                .filter(cookie -> cookie.getName().equals(accessTokenName))
                .map(cookie -> securityCipher.decrypt(cookie.getValue()))
                .findFirst().orElse(null);
    }

    public HttpCookie deleteAccessTokenCookie(){
        return ResponseCookie.from(accessTokenName, "").maxAge(0).path("/").build();
    }
}

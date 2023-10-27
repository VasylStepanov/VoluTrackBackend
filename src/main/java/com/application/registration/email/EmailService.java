package com.application.registration.email;

import java.util.Map;

public interface EmailService {
    void send(String to, Map<String, Object> models);
}

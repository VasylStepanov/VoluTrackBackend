package com.application.user.cache;

import com.application.user.dto.UserDto;
import com.application.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDataCache {

    @Autowired
    private RedisTemplate<String, UserDto> cache;

    public void setUserToCache(User user){
        if(user != null)
            cache.opsForValue().set(user.getEmail(), UserDto.toUserCache(user));
    }

    public User getUserFromCacheByEmail(String email){
        return UserDto.toUser(cache.opsForValue().get(email));
    }
}

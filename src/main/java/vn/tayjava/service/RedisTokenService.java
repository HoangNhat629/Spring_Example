package vn.tayjava.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tayjava.exception.InvalidDataException;
import vn.tayjava.model.RedisToken;
import vn.tayjava.repository.RedisTokenRepository;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisTokenRepository redisTokenRepository;

    public void save(RedisToken token) {
        redisTokenRepository.save(token);
    }

    public void remove(String id) {
        isExists(id);
        redisTokenRepository.deleteById(id);
    }

    public boolean isExists(String id) {
        if (!redisTokenRepository.existsById(id)) {
            throw new InvalidDataException("Token not exists");
        }
        return true;
    }
}

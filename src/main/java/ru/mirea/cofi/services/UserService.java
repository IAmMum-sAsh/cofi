package ru.mirea.cofi.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.cofi.entitys.User;
import ru.mirea.cofi.repositories.UserRepository;
import ru.mirea.cofi.security.payload.UserDtoPayload;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Класс-сервис пользователей
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;


    /**
     * The B crypt password encoder.
     */
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Давайте это говно мы тестировать не будем
     *
     * @return the b crypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    /**
     * Найти пользователя по id
     *
     * @param userId id пользователя
     * @return optional сущность-пользователь
     */
    public Optional<User> findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent())
            log.info("User " + optionalUser.get().toString() + "found by id " + userId);
        else log.info("User with id '" + userId + "' not found.");
        return userRepository.findById(userId);
    }

    /**
     * Найти пользователя по email
     *
     * @param email email пользователя
     * @return optional сущность-пользователь
     */
    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent())
            log.info("User " + optionalUser.get().toString() + "found by email " + email);
        else log.info("User with email '" + email + "' not found.");
        return userRepository.findByEmail(email);
    }

    /**
     * Зарегистрировать нового пользователя
     *
     * @param userDtoPayload dto нового пользователя
     * @param role           роль нового пользователя
     * @return сущность-пользователь
     */
    public User registerNewUser(UserDtoPayload userDtoPayload, String role) {
        User user = new User();

        user.setEmail(userDtoPayload.getEmail());
        user.setRole(role);

        String encodedPassword = bCryptPasswordEncoder.encode(userDtoPayload.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return user;
    }

}

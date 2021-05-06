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
 * The type User service.
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
     * Password encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    /**
     * Find by id optional.
     *
     * @param userId the user id
     * @return the optional
     */
    public Optional<User> findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent())
            log.info("User " + optionalUser.get().toString() + "found by id " + userId);
        else log.info("User with id '" + userId + "' not found.");
        return userRepository.findById(userId);
    }

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent())
            log.info("User " + optionalUser.get().toString() + "found by email " + email);
        else log.info("User with email '" + email + "' not found.");
        return userRepository.findByEmail(email);
    }

    /**
     * Register new sailor user.
     *
     * @return the user
     */
    public User registerNewUser(UserDtoPayload userDtoPayload, String role) {

        //TODO: проверить мрабатоспособность такого метода.
        User user = new User();

        user.setEmail(userDtoPayload.getEmail());
        user.setRole(role);

        //user.setActivationCode(UUID.randomUUID().toString());
        //user.setCreatedActivationCode(LocalDateTime.now());

        String encodedPassword = bCryptPasswordEncoder.encode(userDtoPayload.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);


        return user;
    }




//    public Curator registerNewCurator(CuratorDto curatorDto) {
//        Curator user = new Curator();
//        user.setUsername(curatorDto.getUsername());
//        user.setEmail(curatorDto.getEmail());
//        user.setFirstName(curatorDto.getFirstName());
//        user.setLastName(curatorDto.getLastName());
//        user.setPhoneNumber(curatorDto.getPhoneNumber());
//        user.setSecondName(curatorDto.getSecondName());
//        user.setRole(roleRepository.findByName("ROLE_CURATOR").orElseThrow(() -> { throw new NoSuchElementException("No such role found.");}));
//
//        user.setActivationCode(UUID.randomUUID().toString());
//        user.setCreatedActivationCode(LocalDateTime.now());
//
//        String encodedPassword = bCryptPasswordEncoder.encode(curatorDto.getPassword());
//        user.setPassword(encodedPassword);
//
//        user.setNameCompany(curatorDto.getNameCompany());
//
//        User registeredUser = curatorRepository.save(user);
//        log.info("Registered new curator: " + registeredUser.toString());
//
//        mailService.send(user.getEmail(), "Активация аккаунта.", mailService.compareRegistrationEmail(user.getSecondName(),
//                user.getLastName(), "куратора", user.getActivationCode()));
//
//        return user;
//    }


//    public void activateUser(String encodedUserActivationCode) {
//
//        User activatedUser = userRepository.findByActivationCode(encodedUserActivationCode).orElseThrow(
//                                    () -> {
//                                        throw new NotFoundException();
//                                    }
//                                );
//
//        activatedUser.setActivationCode(null);
//
//        userRepository.save(activatedUser);
//    }

}

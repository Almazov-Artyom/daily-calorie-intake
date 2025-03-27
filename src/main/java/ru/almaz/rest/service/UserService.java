package ru.almaz.rest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.almaz.rest.entity.User;
import ru.almaz.rest.exception.UserAlreadyExistException;
import ru.almaz.rest.exception.UserNotFoundException;
import ru.almaz.rest.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional
    public User createUser(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService(){
        return this::getUserByUsername;
    }



}

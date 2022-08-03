package com.technest.user.crud.service;


import com.technest.user.crud.model.User;
import com.technest.user.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Spring Service Class containing User CRUD operations' business logic.
 */
@Service
public class UserService {


    /**
     * Constructor Dependency Injection
     */
    final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves list of all Users from database with UserRepository's finaAll() method.
     * @return List of Users
     */
    public List<User> listAll(){
        return (List<User>) repository.findAll();
    }

    /**
     * Saves a User to database with UserRepository's save() method.
     */
    public void save(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    /**
     * Retrieves a User object with specified id from database if exists with UserRepository's findById() method.
     * @param id
     * @return User object
     * @throws UserNotFoundException
     */
    public User get(Integer id) throws UserNotFoundException {
       Optional<User> res = repository.findById(id);

       if(res.isPresent()){
           return res.get();
       }

       throw new UserNotFoundException("User doesn't exist with id: " + id);
    }

    /**
     * Deletes User Object with specified id from database if exists with UserRepository's deleteById() method.
     * @param id
     * @throws UserNotFoundException
     */
    public void delete(Integer id) throws UserNotFoundException {

        Long count = repository.countById(id);

        if(count == null || count == 0) {
         throw new UserNotFoundException("Could not find user with id: " + id);
        }
        repository.deleteById(id);
    }

}

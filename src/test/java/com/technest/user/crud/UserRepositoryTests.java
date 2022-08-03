package com.technest.user.crud;

import com.technest.user.crud.model.User;
import com.technest.user.crud.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTests {



    @Autowired
    private UserRepository repo;


    @Test
    public void testCreateNewUser(){
        User user = new User();

        user.setEmail("test@test.com");
        user.setFullName("Test Case");
        user.setPassword("testcase");

        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getEmail()).isNotNull();
        Assertions.assertThat(savedUser.getFullName()).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }


    @Test
    public void testListAll(){

        createUsers();
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for(User user : users){
            System.out.println(user.toString());
        }
    }

    @Test
    public void testUpdate(){
        createUsers();
        Integer userId = 1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setFullName("John Steven Delaney");

        User updatedUser = repo.findById(userId).get();
        Assertions.assertThat(updatedUser.getFullName()).isEqualTo("John Steven Delaney");
    }

    @Test
    public void testGet(){
        createUsers();
        Integer userId = 2;
        Optional<User> optionalUser = repo.findById(userId);

        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete(){
        createUsers();
        Integer userId = 2;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);

        Assertions.assertThat(optionalUser).isNotPresent();

    }

    public void createUsers(){
        User user = new User();
        User userTwo = new User();

        user.setEmail("JohnD@test.com");
        user.setFullName("John Delaney");
        user.setPassword("johndelaney");

        userTwo.setEmail("TimE@test.com");
        userTwo.setFullName("Tim Elliot");
        userTwo.setPassword("timelliot");


        repo.save(user);
        repo.save(userTwo);
    }


}

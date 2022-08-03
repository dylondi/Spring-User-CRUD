package com.technest.user.crud.repository;

import com.technest.user.crud.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Integer> {
    Long countById(Integer id);
}

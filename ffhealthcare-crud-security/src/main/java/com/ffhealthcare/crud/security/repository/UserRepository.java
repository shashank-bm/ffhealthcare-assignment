package com.ffhealthcare.crud.security.repository;
import org.springframework.data.repository.CrudRepository;

import com.ffhealthcare.crud.security.model.UserDao;
public interface UserRepository extends CrudRepository<UserDao, Integer> {
    UserDao findByUsername(String username);
}
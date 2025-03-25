package com.example.semproject.Repository;

import com.example.semproject.Model.Sign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<Sign, String> {

    boolean existsByEmail(String email); // Check if email exists
    boolean existsByUsername(String username); // Check if username exists
}

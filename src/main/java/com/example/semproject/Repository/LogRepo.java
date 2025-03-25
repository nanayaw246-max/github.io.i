package com.example.semproject.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.semproject.Model.Log;

public interface LogRepo extends MongoRepository<Log, String> {
    Log findByUsername(String username); // Custom query method to find a user by username
}

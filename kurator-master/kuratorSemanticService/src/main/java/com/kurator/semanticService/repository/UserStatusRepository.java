package com.kurator.semanticService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kurator.semanticService.model.UserStatus;


@Repository
public interface UserStatusRepository extends MongoRepository<UserStatus, String> {
    UserStatus findByUserEmail(String userName);
}

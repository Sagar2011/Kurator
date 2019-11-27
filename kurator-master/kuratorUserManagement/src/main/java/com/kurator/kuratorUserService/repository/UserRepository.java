package com.kurator.kuratorUserService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.kurator.kuratorUserService.model.User;
@Repository
public interface UserRepository extends MongoRepository<User,String> {
	User findByEmail(String Email);
	User findByUserName(String userName);
	boolean existsByUserNameAndPlaylists_PlaylistName(String username,String playlistName);
	User findByFirstName(String firstName);

}
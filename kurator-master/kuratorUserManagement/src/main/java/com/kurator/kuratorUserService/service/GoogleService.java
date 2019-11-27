package com.kurator.kuratorUserService.service;


import com.kurator.kuratorUserService.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleService {

	String googlelogin();

	String getGoogleAccessToken(String code);

	User getGoogleUserProfile(String accessToken);

}

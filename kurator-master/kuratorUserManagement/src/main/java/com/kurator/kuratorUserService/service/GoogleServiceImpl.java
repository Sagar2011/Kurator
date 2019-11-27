package com.kurator.kuratorUserService.service;
import com.kurator.kuratorUserService.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GoogleServiceImpl implements GoogleService {
    @Value("${spring.social.google.app-id}")
    private String googleId;
    @Value("${spring.social.google.app-secret}")
    private String googleSecret;
    @Value("${spring.social.google.app-redirect-uri}")
    private String redirectUrl;

    //Creates Google OAuth Connection
    private GoogleConnectionFactory createGoogleConnection() {
        return new GoogleConnectionFactory(googleId, googleSecret);
    }
    //Opens the Google Consent Form
    @Override
    public String googlelogin() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(redirectUrl);
        parameters.setScope("profile email");
        return createGoogleConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
    }
    //For getting Google oauth access token
    @Override
    public String getGoogleAccessToken(String code) {
        return createGoogleConnection().getOAuthOperations()
                .exchangeForAccess(code, redirectUrl, null).getAccessToken();
    }
    //For getting google profile of particular user
    @Override
    public User getGoogleUserProfile(String accessToken) {
        Google google = new GoogleTemplate(accessToken);
        Person person = google.plusOperations().getGoogleProfile();
        User user = new User();
        user.setUserName(person.getAccountEmail());
        user.setEmail(person.getAccountEmail());
        user.setFirstName(person.getGivenName());
        user.setLastName(person.getFamilyName());
        user.setAboutUser(person.getAboutMe());
        user.setUserRole(User.Role.USER);
        user.setAvatarURL(person.getImageUrl());
        user.setCreatedOn(LocalDateTime.now());
        user.setCreatedBy("System");
        user.setUpdatedOn(LocalDateTime.now());
        user.setUpdatedBy("System");
        user.setUserRole(User.Role.USER);
        System.out.println(person);
        return user;
    }
}

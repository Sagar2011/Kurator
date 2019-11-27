package com.kurator.kuratorUserService.service;

import com.kurator.kuratorUserService.controller.AuthController;
import com.kurator.kuratorUserService.model.GitlabGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class GitlabServiceImpl implements GitlabService{

    @Autowired
    IUserService userService ;
    @Autowired
    RestTemplate restTemplate;

    @Value("${gitlab.base.url}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.gitlab.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.gitlab.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.gitlab.redirect-uri}")
    private String redirectUrl;
    @Value("${usergroup}")
    private String usergroup;
    @Value("${groupsurl}")
    private String groupsurl;
    @Value("${userurl}")
    private String userurl;

    public static Logger logger = LogManager.getLogger(AuthController.class);


    //For getting Google oauth access token
    @Override
    public String getGitlabAccessToken(String code) {
        System.out.println("inside gitlab access token");
        String url = "https://gitlab-waves.stackroute.io/oauth/token";
        System.out.println("inside gitlab access token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        System.out.println("inside gitlab access token");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("client_id", this.clientId)
                .queryParam("client_secret", this.clientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", this.redirectUrl);
        HttpEntity<String> request = new HttpEntity<>(headers);
        System.out.println("inside gitlab access token");
        ResponseEntity<JSONObject> result = this.restTemplate.postForEntity(builder.toUriString(), request,
                JSONObject.class);
        System.out.println("Access Token: " + result.getBody());
        // logger.info("Access Token: " + result.getBody().get("access_token"));
        return (String) result.getBody().get("access_token");
    }


    // For getting gitlab user profile
    public JSONObject getGitlabUserProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(userurl).queryParam("access_token",
                accessToken);
        ResponseEntity<JSONObject> result = this.restTemplate.getForEntity(builder.toUriString(), JSONObject.class);
        logger.info("User Profile: " + result);
        System.out.println("User Profile: " + result);
        JSONObject userDetails = result.getBody();
        return userDetails;
    }

    // Get user groups from gitlab
    public boolean getUserGroups(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(groupsurl).queryParam("access_token",
                accessToken);
        GitlabGroup[] result = this.restTemplate.getForObject(builder.toUriString(), GitlabGroup[].class);
        if (result.length == 0) {
            return false;
        } else {
            for (int i = 0; i < result.length; i++) {
                if (result[i].getName().equals(usergroup)) {
                    return true;
                }
            }
            return false;
        }
    }
}

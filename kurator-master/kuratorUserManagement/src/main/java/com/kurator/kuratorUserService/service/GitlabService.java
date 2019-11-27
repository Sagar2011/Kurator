package com.kurator.kuratorUserService.service;

import org.json.simple.JSONObject;

public interface GitlabService {


    String getGitlabAccessToken(String code);

    JSONObject getGitlabUserProfile(String accessToken);

    boolean getUserGroups(String accessToken);
}

package com.kurator.kuratorUserService.service;

import com.kurator.kuratorUserService.exception.NoUserExists;
import com.kurator.kuratorUserService.exception.UsersNotFound;
import com.kurator.kuratorUserService.model.ResultModel;
import com.kurator.kuratorUserService.model.User;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public interface IUserService {

    User loadByUsername(HttpServletRequest request);

    void addNewUser(User user);

    void saveUser(User user);

    User findByUserName(String username);

    User findByFirstName(String firstName);

    User findUserByEmail(String email) throws UsersNotFound;

    Collection<User> findAllUser() throws NoUserExists;

    void updateUserFavourites(UUID documentId, String email);

    HashSet<UUID> getFavourites(String email);

    HashSet<UUID> getHistory(String email);

    void updateUserView(UUID docId, String userName);

    void updateUserFollowing(String userName, String followingPeople);

    void updateUserFollower(String followerName, String userName);

    void addUserData(User user);

    boolean playlistExistsByUsername(String username, String playlistName);

    String findUserName(HttpServletRequest request);

    void addToPlaylist(String username, ArrayList<String> playlistName, UUID documentId);

    ArrayList<ResultModel> getPlaylistDocumentByUsername(String userName, String playlistName);
}

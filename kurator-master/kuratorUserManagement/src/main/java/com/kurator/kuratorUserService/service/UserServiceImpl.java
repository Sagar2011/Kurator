package com.kurator.kuratorUserService.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurator.kuratorUserService.exception.NoUserExists;
import com.kurator.kuratorUserService.exception.UsersNotFound;
import com.kurator.kuratorUserService.model.Playlist;
import com.kurator.kuratorUserService.model.ResponseModel;
import com.kurator.kuratorUserService.model.ResultModel;
import io.jsonwebtoken.ExpiredJwtException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;

import com.kurator.kuratorUserService.model.User;
import com.kurator.kuratorUserService.repository.UserRepository;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements IUserService {

    @Value("${SIGNINGKEY}")
    private String SIGNINGKEY;

    @Autowired
    private UserRepository repo;

    @Autowired
    DocumentServiceProxy documentServiceProxy;

    @Autowired
    SemanticServiceProxy semanticServiceProxy;


    @Override
    public void saveUser(User user) {
        repo.save(user);
    }


    public void addUserInfo(Person person) throws DuplicateKeyException {
        User user = new User();
        if (repo.findByEmail(person.getAccountEmail()) == null) {
            //user.setUserId(UUID.randomUUID());
            user.setUserName(person.getAccountEmail());
            user.setEmail(person.getAccountEmail());
            user.setFirstName(person.getGivenName());
            user.setLastName(person.getFamilyName());
            user.setAboutUser(person.getAboutMe());
            user.setAvatarURL(person.getImageUrl());
            user.setCreatedOn(LocalDateTime.now());
            user.setCreatedBy("System");
            user.setUpdatedOn(LocalDateTime.now());
            user.setUpdatedBy("System");
            user.setStatus(false);
            repo.save(user);
        } else {
            throw new DuplicateKeyException("Client Already exists with email" + person.getAccountEmail());
        }
    }

    @Override
    public User findByUserName(String username) {
        User user = repo.findByUserName(username);

        return user;
    }

    @Override
    public User findByFirstName(String firstName) {
        return repo.findByFirstName(firstName);
    }

    public User loadByUsername(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("JWT-TOKEN")) {
                String user = null;
                try {
                    user = Jwts.parser().setSigningKey(SIGNINGKEY).parseClaimsJws(cookie.getValue()).getBody()
                            .get("un", String.class);
                } catch (ExpiredJwtException exception) {
                    return null;
                }
                if (user != null) {
                    return this.repo.findByUserName(user);
                }
            }
        }
        return null;
    }


    public User findUserByEmail(String email) throws UsersNotFound {
        User user = repo.findByEmail(email);
        if (user == null) {
            throw new UsersNotFound("User with the emailId" + email + "doesn't exist");
        } else {
            return user;
        }
    }

    @Override
    public Collection<User> findAllUser() throws NoUserExists {
        Collection<User> listOfUser = repo.findAll();


        if (listOfUser.size() == 0) {
            throw new NoUserExists("Currently, No user exists");
        } else {
            return listOfUser;
        }
    }


    @Override
    public void updateUserFavourites(UUID documentId, String username) {
        User user = repo.findByUserName(username);
        HashSet<UUID> favourites = new HashSet<>();
        if (user.getFavourites() == null) {
            favourites.add(documentId);
        } else {
            favourites.addAll(user.getFavourites());
            favourites.add(documentId);
        }
        user.setFavourites(favourites);
        repo.save(user);
    }

    @Override
    public void updateUserView(UUID docId, String userName) {
        User user = repo.findByUserName(userName);
        HashSet<UUID> recents = new HashSet<>();
        if (user.getRecentViews() == null) {
            recents.add(docId);
        } else {
            recents.addAll(user.getRecentViews());
            recents.add(docId);
        }
        user.setRecentViews(recents);
        repo.save(user);
    }


    @Override
    public HashSet<UUID> getFavourites(String username) {
        HashSet<UUID> favouriteIds = new HashSet<UUID>();
        User user = repo.findByUserName(username);
        favouriteIds.addAll(user.getFavourites());
        return favouriteIds;
    }

    @Override
    public HashSet<UUID> getHistory(String username) {
        HashSet<UUID> recentsIds = new HashSet<UUID>();
        User user = repo.findByUserName(username);
        recentsIds.addAll(user.getRecentViews());
        return recentsIds;
    }


    public void addNewUser(User user) {
        repo.save(user);

    }

    @Override
    public void updateUserFollowing(String userName, String followingPeople) {
        User user = repo.findByUserName(userName);
        HashSet<String> listPeople = new HashSet<>();
        if (user.getFollowingPeople() == null) {
            listPeople.add(followingPeople);
        } else {
            listPeople.addAll(user.getFollowingPeople());
            listPeople.add(followingPeople);
        }

        user.setFollowingPeople(listPeople);
        repo.save(user);
    }

    @Override
    public void updateUserFollower(String followerName,  String userName){
        User user =  repo.findByUserName(followerName);
        HashSet<String> followerList = new HashSet<>();
        if(user.getFollower() == null){
            followerList.add(userName);
        } else {
            followerList.addAll(user.getFollower());
            followerList.add(userName);
        }

        user.setFollower(followerList);
        repo.save(user);
    }

    public void addUserData(User user) {
        repo.save(user);
    }

    @Override
    public boolean playlistExistsByUsername(String username, String playlistName) {
        return repo.existsByUserNameAndPlaylists_PlaylistName(username, playlistName);
    }

    @Override
    public String findUserName(HttpServletRequest request) {
        String user = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("JWT-TOKEN")) {
                try {
                    user = Jwts.parser().setSigningKey(SIGNINGKEY).parseClaimsJws(cookie.getValue()).getBody()
                            .get("un", String.class);
                } catch (ExpiredJwtException exception) {
                    return null;
                }
            }
        }
        return user;
    }

    @Override
    public void addToPlaylist(String username, ArrayList<String> playlists, UUID documentId) {
        User user = repo.findByUserName(username);
        for (Playlist playlist : user.getPlaylists()) {
            if(playlists.contains(playlist.getPlaylistName())) {
                LinkedHashSet<UUID> documentIds = playlist.getDocumentID();
                documentIds.add(documentId);
                playlist.setDocumentID(documentIds);

            }
        }
        repo.save(user);
    }

    @Override
    public ArrayList<ResultModel> getPlaylistDocumentByUsername(String userName, String playlistName) {
        User user = repo.findByUserName(userName);
        ArrayList<String> arrList=new ArrayList<>();
        ArrayList<ResultModel> documents = new ArrayList<>();
         for(Playlist playlist : user.getPlaylists()){
             if(playlist.getPlaylistName().equals(playlistName)){
                 for(UUID documentId : playlist.getDocumentID()) {
                     arrList.add(documentId.toString());
                 }
             }
         }
        return semanticServiceProxy.getDocuments(arrList);
    }
}
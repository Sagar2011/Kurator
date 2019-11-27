package com.kurator.kuratorUserService.controller;

import com.kurator.kuratorUserService.exception.NoUserExists;
import com.kurator.kuratorUserService.exception.UsersNotFound;
import com.kurator.kuratorUserService.model.User;
import com.kurator.kuratorUserService.model.ResponseModel;
import com.kurator.kuratorUserService.model.Playlist;
import com.kurator.kuratorUserService.model.ResultModel;

import com.kurator.kuratorUserService.service.SemanticServiceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kurator.kuratorUserService.service.IUserService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;
//import java.util.HashMap;


@RestController
public class UserController {
    @Autowired
    private IUserService userService;
    public static Logger logger = LogManager.getLogger(UserController.class);


    @Autowired
    private ResponseModel responseModel;

    @Autowired
    private SemanticServiceProxy semanticServiceProxy;

    // For providing user google profile
    @GetMapping("/userprofile")
    public User getUserProfile(HttpServletRequest request) {
        logger.info("in /userprofile");
        User user = userService.loadByUsername(request);
        logger.info("user profile is: " + user.toString());
        System.out.println(user);
        return user;
    }

    @GetMapping("/user/{email}")
    public void markUserStatus(@PathVariable String email) throws UsersNotFound {
        User user = userService.findUserByEmail(email);
        user.setUserId(user.getUserId());
//        user.setEmail(user.getEmail());
//        user.setFirstName(user.getFirstName());
//        user.setLastName(user.getLastName());
//        user.setAvatarURL(user.getAvatarURL());
//        user.setCreatedOn(LocalDateTime.now());
//        user.setCreatedBy("System");
//        user.setUpdatedOn(LocalDateTime.now());
//        user.setUpdatedBy("System");
        user.setStatus(true);
        userService.saveUser(user);
    }


    @RequestMapping(value = "updateprofile/{userName:.+}/", method = RequestMethod.PATCH)
    public User updateProfile(@PathVariable("userName") String userName, @RequestBody User user) throws UsersNotFound {
        User retrievedUser = userService.findUserByEmail(userName);

        retrievedUser.setFirstName(user.getFirstName());

        retrievedUser.setLastName(user.getLastName());

        retrievedUser.setAboutUser(user.getAboutUser());

        userService.saveUser(retrievedUser);
        logger.info("edit profile is: " + retrievedUser);
        return retrievedUser;
    }

    @GetMapping("/available-topics")
    public ArrayList<String> retriveTopics() throws UsersNotFound {
        ArrayList<String> topicList = semanticServiceProxy.getReviewedConcepts();
        System.out.println(topicList);
        return topicList;
    }

    @PatchMapping("/interested-topics")
    public void addRetrievedTopics(@RequestBody ArrayList<String> topics, HttpServletRequest request) {
        String userName = userService.findUserName(request);
        User user = userService.findByUserName(userName);
        ArrayList<String> topic = new ArrayList<>();
        if (user.getTopicsOfInterest() == null) {
            topic.addAll(topics);
            HashSet<String> topicSet = new HashSet<>(topic);
            user.setTopicsOfInterest(topicSet);
        } else {
            topic.addAll(user.getTopicsOfInterest());
            for (String list : topics) {
                topic.add(list);
            }
            HashSet<String> topicSet = new HashSet<>(topic);
            user.setTopicsOfInterest(topicSet);
        }
        userService.saveUser(user);
    }


    @GetMapping("/userTopics")
    public ResponseEntity<?> getUserTopics(HttpServletRequest request) {
        String userName = userService.findUserName(request);
        User user = userService.findByUserName(userName);
        HashSet<String> topics = user.getTopicsOfInterest();
        ArrayList<String> topicList = new ArrayList<>(topics);
        if (topics == null) {
            responseModel.setStatusCode(404);
            responseModel.setResult(null);
            responseModel.setMessage("No Topics found");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } else {
            responseModel.setStatusCode(200);
            responseModel.setResult(topicList);
            responseModel.setMessage("Topics found");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        }


    }

    @DeleteMapping("/interested-topics")
    public void removeTopic(HttpServletRequest request, @RequestParam String topicName) throws UnsupportedEncodingException {
        topicName = URLDecoder.decode(topicName,"UTF-8");
        String userName = userService.findUserName(request);
        User user = userService.findByUserName(userName);
        HashSet<String> topics = user.getTopicsOfInterest();
        System.out.println("Before Removing Playlist" + topics);
        for (String list : topics) {
            if (list.equals(topicName)) {
                System.out.println("InsideIF" + topicName);
                topics.remove(list);

                user.setTopicsOfInterest(topics);
                System.out.println("After Removing" + topics);
            } else {
                responseModel.setStatusCode(404);
                responseModel.setResult(null);
                responseModel.setMessage("topic does not exist");
            }
            userService.saveUser(user);
        }
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            Collection<User> listOfUser = userService.findAllUser();
            if (listOfUser.size() != 0)
                responseModel.setStatusCode(200);
            responseModel.setMessage("Users founded");
            responseModel.setResult((ArrayList<?>) listOfUser);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        } catch (NoUserExists message) {

            responseModel.setStatusCode(404);
            responseModel.setMessage("No User Found ");
            responseModel.setResult(null);
            logger.error("Exception occured with message" + message);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getByUserName(@RequestParam String username) {


        try {

            User user = userService.findByUserName(username);
            if (user != null)
                responseModel.setStatusCode(200);
            responseModel.setMessage("User added Successfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);


        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }

    }


    // for adding the favorite of the user
    @PatchMapping("/favourites")
    public ResponseEntity<?> updateUserFavourites(HttpServletRequest request, @RequestBody String documentId) throws UsersNotFound {
        try {
            String userName = userService.findUserName(request);
            UUID docId = UUID.fromString(documentId);
            System.out.println(docId);
            User findUser = userService.findByUserName(userName);
            System.out.println(findUser);
            if (findUser != null) {
                System.out.println("insside if");
                userService.updateUserFavourites(docId, userName);
            }
            responseModel.setStatusCode(200);
            responseModel.setMessage("Favourite added succesfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);


        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    //	For fetch the favourite documents of a particular logged in user
    @GetMapping("/favourites")
    public ArrayList<ResultModel> getUserFavourites(HttpServletRequest request) {
        String userName = userService.findUserName(request);
        HashSet<UUID> favouritesList = userService.getFavourites(userName);
        ArrayList<String> favListForSemanticService = new ArrayList<>();
        for (UUID list : favouritesList) {
            favListForSemanticService.add(list.toString());
        }
        System.out.println(favListForSemanticService.toString() + "id fromm users");
        return semanticServiceProxy.getDocuments(favListForSemanticService);
    }

    //for unfavourite the documents of a particular logged in user
    @DeleteMapping("/favourites")
    public void deleteUserFavourites(HttpServletRequest request, @RequestParam String documentId) {
        String userName = userService.findUserName(request);
        HashSet<UUID> favouritesList = userService.getFavourites(userName);
        System.out.println("Before deleting" + favouritesList.toString());
        UUID uuid = UUID.fromString(documentId);

        //exists
        if (favouritesList.contains(uuid)) {
            System.out.println("Inside If");
            favouritesList.remove(uuid); //remove

        }
        User user = userService.findByUserName(userName);
        user.setFavourites(favouritesList);
        userService.saveUser(user);
        System.out.println("Successfully Unfavourited" + favouritesList.toString());
    }


    @PatchMapping("/history/view")
    public ResponseEntity<?> updateUserRecentView(HttpServletRequest request, @RequestBody String documentId) throws UsersNotFound {
        try {
            String userName = userService.findUserName(request);
            UUID docId = UUID.fromString(documentId);
            User findUser = userService.findByUserName(userName);
            if (findUser != null) {
                userService.updateUserView(docId, userName);
                System.out.println("recents view");
            }
            responseModel.setStatusCode(200);
            responseModel.setMessage("Favourite added succesfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);


        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    //	For fetching recently viewed documents of a particular logged in user
    @GetMapping("/history/view")
    public ArrayList<ResultModel> getUserHistory(HttpServletRequest request) {
        String userName = userService.findUserName(request);
        HashSet<UUID> viewList = userService.getHistory(userName);
        ArrayList<String> viewListForSemanticService = new ArrayList<>();
        for (UUID list : viewList) {
            viewListForSemanticService.add(list.toString());
        }
        System.out.println(viewListForSemanticService.toString() + "id fromm users");
        return semanticServiceProxy.getDocuments(viewListForSemanticService);
    }


    //for following people
    @PatchMapping("/people")
    public ResponseEntity<?> followPeople(HttpServletRequest request, @RequestBody String followingPeople) throws UsersNotFound {
        try {
            String userName = userService.findUserName(request);
            User user = userService.findByFirstName(followingPeople);
            System.out.println("people following" + user.toString());
            System.out.printf("people following");
//           userService.updateUserFollowing(user.getUserName(), user.getFollowingPeople().get(0));
            userService.updateUserFollowing(userName, user.getEmail());

            //method to call update followers
//            User user = userService.findByUserName(followingPeople);

//            userService.updateUserFollower(followingPeople, userName);
//            User userFollwer = userService.findByUserName(userName);
//            if (user.getFollower() == null) {
//                listFollowers.add(followingPeople);
//                user.setFollower(listFollowers);
//            } else {
//                listFollowers = user.getFollower();
//                listFollowers.add(followingPeople);
//                user.setFollower(listFollowers);
//            }
            responseModel.setStatusCode(200);
            responseModel.setMessage("Person followed successfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception error) {
            logger.error("Error occured " + error);
            error.printStackTrace();
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    //for getting following people
    @GetMapping("/people")
    public ResponseEntity<?> getAllFollowing(HttpServletRequest request) {
        try {
            String userName = userService.findUserName(request);
            System.out.println("in get");
            User user = userService.findByUserName(userName);
            System.out.println(user.getFirstName() + "username");
            HashSet<String> followList = user.getFollowingPeople();
            System.out.println(followList + "followlist");
            ArrayList<User> userLists = new ArrayList<>();
            for (String list : followList) {
                System.out.println(userLists.toString() + "for");

                userLists.add(userService.findByUserName(list));

            }
            System.out.println(userLists.toString());


            responseModel.setStatusCode(200);
            responseModel.setMessage("People Found in DB");
            responseModel.setResult(userLists);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    @DeleteMapping("/people")
    public ResponseEntity<?> deleteFollowers(HttpServletRequest request, @RequestParam String followerName) {
        try {
            String userName = userService.findUserName(request);
            User user = userService.findByUserName(userName);
            HashSet<String> followList = user.getFollowingPeople();
            if (followList.contains(followerName)) {
                followList.remove(followerName);
                user.setFollowingPeople(followList);
                userService.addNewUser(user);
            }
            responseModel.setStatusCode(200);
            responseModel.setMessage("follower deleted");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    @PatchMapping("/follower")
    public ResponseEntity<?> followerPeople(@RequestParam String followerName, @RequestBody String userName) throws UsersNotFound {
        try {
            System.out.printf("people following");
//            HashSet<String> listFollowers = new HashSet<>();
            userService.updateUserFollowing(followerName, userName);

            //method to call update followers
//            User user = userService.findByUserName(followerName);

            responseModel.setStatusCode(200);
            responseModel.setMessage("follower successfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception error) {
            logger.error("Error occured " + error);
            error.printStackTrace();
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }


    @GetMapping("/follower")
    public ResponseEntity<?> getAllFollower(@RequestParam String userName) {
        try {
            User user = userService.findByUserName(userName);
            HashSet<String> followerList = user.getFollower();
            ArrayList<User> userLists = new ArrayList<>();

            for (String list : followerList) {
                userLists.add(userService.findByUserName(list));
            }
            responseModel.setStatusCode(200);
            responseModel.setMessage("display follower");
            responseModel.setResult(userLists);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    @DeleteMapping("/follower")
    public ResponseEntity<?> removeFollower(@RequestParam String userName, @RequestParam String followerName) {
        try {
            User user = userService.findByUserName(userName);
            HashSet<String> followerList = user.getFollower();
            if (followerList.contains(followerName)) {
                followerList.remove(followerName);
                user.setFollower(followerList);
                userService.addNewUser(user);
            }
            responseModel.setStatusCode(200);
            responseModel.setMessage("removed follower");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }
//    @PatchMapping("/people")
//    public ResponseEntity<?> followPeople(@RequestBody User user) throws  UsersNotFound{
//        try{
//            System.out.printf("people following");
//            userService.updateUserFollowing(user.getUserName(), user.getFollowingPeople().get(0));
//            responseModel.setStatusCode((200));
//            responseModel.setMessage("Person followed successfully");
//            responseModel.setResult(null);
//            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
//        } catch (Exception error){
//            logger.error("Error occured "+ error);
//            error.printStackTrace();
//            responseModel.setStatusCode(500);
//            responseModel.setMessage("INTERNAL SERVER ERROR");
//            responseModel.setResult(null);
//            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
//        }
//    }


    //for getting people following list
//     @GetMapping("/followingPeople")


    //for getting the status of user
    @GetMapping("/status/{userName:.+}/")
    public boolean userStatus(@PathVariable String userName) {
        try {
            User user = userService.findByUserName(userName);
            if (!user.isStatus()) {
                System.out.println("User is active");
                return false;
            } else {
                System.out.println("User is blocked");
                return true;
            }
        } catch (Exception e) {
            logger.error("User Status unable to catch");
            return false;
        }
    }

    //for getting the playlist of the user
    @GetMapping("/playlist")
    public ResponseEntity<?> getPlaylist(HttpServletRequest request) {
        String userName = userService.findUserName(request);
        User user = userService.findByUserName(userName);
        ArrayList<Playlist> playlists = user.getPlaylists();
        playlists.sort(((playlist1, playlist2) -> playlist2.getCreatedOn().compareTo(playlist1.getCreatedOn())));
        if (playlists == null) {
            responseModel.setStatusCode(404);
            responseModel.setResult(null);
            responseModel.setMessage("No Playlist found");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } else {
            responseModel.setStatusCode(200);
            responseModel.setResult(playlists);
            responseModel.setMessage("Playlist found");
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        }


    }

    //for adding the playlist

    @PatchMapping("/playlist")
    public void addPlaylist(@RequestBody Playlist playlists, HttpServletRequest request) {
        String userName = userService.findUserName(request);
        User user = userService.findByUserName(userName);
        ArrayList<Playlist> playList = new ArrayList<>();
        playlists.setCreatedOn(LocalDateTime.now());
        if (user.getPlaylists() == null) {
            playList.add(playlists);
            user.setPlaylists(playList);
        } else {
            System.out.println("PLAYLIST EXISTS:: " + userService.playlistExistsByUsername(userName, playlists.getPlaylistName()));

            if (userService.playlistExistsByUsername(userName, playlists.getPlaylistName())) {
                responseModel.setStatusCode(200);
                responseModel.setResult(null);
                responseModel.setMessage("Playlist already exists");
            } else {
                playList.addAll(user.getPlaylists());
                playList.add(playlists);
                user.setPlaylists(playList);
            }
        }
        userService.saveUser(user);
    }

    //for removing the playlist of a particular logged in user
    @DeleteMapping("/playlist")
    public void removePlaylist(HttpServletRequest request, @RequestParam String playlistName) {
        String userName = userService.findUserName(request);
        User user = userService.findByUserName(userName);
        ArrayList<Playlist> playlists = user.getPlaylists();
        System.out.println("Before Removing Playlist" + playlists);
        for (Playlist list : playlists) {

            if (list.getPlaylistName().equals(playlistName)) {
                System.out.println("InsideIF" + playlistName);
                playlists.remove(list);

                user.setPlaylists(playlists);
                System.out.println("After Removing" + playlists);
            } else {
                responseModel.setStatusCode(404);
                responseModel.setResult(null);
                responseModel.setMessage("playlist does not exist");
            }
            userService.saveUser(user);
        }
    }

    @PatchMapping("/userplaylist")
    public void addToPlaylist(@RequestParam ArrayList<String> playlists, @RequestParam UUID documentID, HttpServletRequest request) {
    //    UUID uuid = UUID.fromString(documentID);
        String userName = userService.findUserName(request);
        userService.addToPlaylist(userName, playlists, documentID);
    }

    @GetMapping("/userplaylist/{playlistName}/documents")
    public ArrayList<ResultModel> getPlaylistDocuments(@PathVariable String playlistName, HttpServletRequest request) {
        String userName = userService.findUserName(request);
        ArrayList<ResultModel> documents = userService.getPlaylistDocumentByUsername(userName, playlistName);
        return documents;
    }

    @GetMapping("/topics/documents")
    ArrayList<ResultModel> getDocumentByUsers(HttpServletRequest request) {
        String username = userService.findUserName(request);
        User user = userService.findByUserName(username);
        HashSet<String> topics = user.getTopicsOfInterest();
        System.out.println("topic from semantic"+ topics.toString());
        ArrayList<ResultModel> documents = new ArrayList<>();
        for (String list : topics) {
            documents.addAll(semanticServiceProxy.getDocumentByConcept(list));
        }
        HashSet<ResultModel> resultModels = new HashSet<>();
        resultModels.addAll(documents);
        documents.clear();
        documents.addAll(resultModels);
        return documents;
    }


    @PatchMapping("/terms")
    public ResponseEntity<?> termsAndConditionsAccepted (HttpServletRequest request) {
        String userName = userService.findUserName(request);
        User user = userService.findByUserName(userName);
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("result", user);
        response.put("message", "Successfully updated User entity");
        response.put("errror", false);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
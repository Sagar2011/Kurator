package com.kurator.kuratorUserService.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.kurator.kuratorUserService.exception.UsersNotFound;
import com.kurator.kuratorUserService.model.User;
import com.kurator.kuratorUserService.service.GitlabService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.kurator.kuratorUserService.service.GoogleService;
import com.kurator.kuratorUserService.service.IUserService;
import com.kurator.kuratorUserService.util.CookieUtil;
import com.kurator.kuratorUserService.util.JwtUtil;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private GoogleService googleService;
    @Autowired
    private GitlabService gitlabService;
    @Autowired
    private IUserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${gitlab.base.url}")
    private String baseUrl;
    @Value("${spring.security.oauth2.client.registration.gitlab.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.gitlab.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.gitlab.redirect-uri}")
    private String redirectUrl;
    @Value("${domain}")
    private String domain;
    @Value("${userDashboardUrl}")
    private String userDashboardUrl;
    @Value("${adminDashboardUrl}")
    private String adminDashboardUrl;

    private static final String jwtTokenCookieName = "JWT-TOKEN";
    public static Logger logger = LogManager.getLogger(AuthController.class);

    // Redirect to login/consent form on Google's authent= "http://localhost:8765/#/dashboard";ication page
    @GetMapping(value = "/googlelogin")
    public RedirectView googlelogin() {
        logger.info("In google login");
        RedirectView redirectview = new RedirectView();
        String url = googleService.googlelogin();
        logger.info(url);
        redirectview.setUrl(url);
        return redirectview;
    }

    // Google calls back on user's successful authentication and consent
    @GetMapping(value = "/userredirect")
    public RedirectView google(@RequestParam("code") String code, HttpServletResponse res) throws UsersNotFound {
        String accessToken = googleService.getGoogleAccessToken(code);
        User user = googleService.getGoogleUserProfile(accessToken);
        String jwtToken = jwtUtil.addToken(res, user);
        logger.info(jwtToken);
        Cookie cookie = CookieUtil.create(res, jwtTokenCookieName, jwtToken, false, -1, domain);
        logger.info(cookie.toString());
        

        User retrievedUser = userService.findByUserName(user.getUserName());
        																																																																																																																																																																																																	
        if (retrievedUser != null) {
            retrievedUser.setFirstName(user.getFirstName());
            retrievedUser.setLastName(user.getLastName());
            retrievedUser.setAboutUser(retrievedUser.getAboutUser());
            retrievedUser.setTerms(false);
            user = retrievedUser;
        }
        userService.saveUser(user);

        RedirectView redirectview = new RedirectView();
        redirectview.setUrl("http://localhost:8080/#/dashboard");
        return redirectview;
    }

    @GetMapping(value = "/userlogout")
    public void googleLogout(HttpServletResponse response) {
        String cookiename = jwtTokenCookieName;
        CookieUtil.clearCookie(response, cookiename, domain);
    }

    @GetMapping(value = "/gitlablogin")
    public RedirectView gitlablogin() {
        //System.out.println("inside gitlab login");
        String url = baseUrl + "oauth/authorize?client_id=" + this.clientId + "&redirect_uri=" + this.redirectUrl
                + "&response_type=code&scope=read_user+api";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        //System.out.println("inside gitlab login");
        return redirectView;
    }

    @GetMapping("/admindashboard")
    public RedirectView getAdminDetails(@RequestParam("code") String code, HttpServletResponse response)
            throws IOException {
        System.out.println("in /admindashboard");
        String accessToken = gitlabService.getGitlabAccessToken(code);
        JSONObject userDetails = gitlabService.getGitlabUserProfile(accessToken);

        System.out.println("USER_DETAILS:::" + userDetails);
        User user = userService.findByUserName((String) userDetails.get("username"));
        if(user == null) {
            user = new User();
            user.setUserName((String) userDetails.get("username"));
            user.setFirstName((String) userDetails.get("name"));
            user.setAvatarURL((String) userDetails.get("avatar_url"));
            user.setEmail((String)userDetails.get("email"));
            user.setName((String) userDetails.get("name"));
            user.setCreatedOn(LocalDateTime.now());
            user.setCreatedBy("System");
            user.setUpdatedOn(LocalDateTime.now());
            user.setUpdatedBy("System");
            user.setTerms(false);
        }
        RedirectView redirectview = new RedirectView();

        if (gitlabService.getUserGroups(accessToken)) {
            user.setUserRole(User.Role.ADMIN);
            String jwtToken = jwtUtil.addToken(response, user);
            CookieUtil.create(response, jwtTokenCookieName, jwtToken, false, -1, domain);
            userService.addUserData(user);
            redirectview.setUrl(adminDashboardUrl);
            return redirectview;
        } else {
            user.setUserRole(User.Role.USER);
            String jwtToken = jwtUtil.addToken(response, user);
            CookieUtil.create(response, jwtTokenCookieName, jwtToken, false, -1, domain);
            userService.addUserData(user);
            redirectview.setUrl(userDashboardUrl);
            return redirectview;
        }
    }
}
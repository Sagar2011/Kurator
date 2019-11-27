package com.kurator.kuratorDocumentService.library.service;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import com.kurator.kuratorDocumentService.library.model.Document;
import com.kurator.kuratorDocumentService.library.model.Flag;
import com.kurator.kuratorDocumentService.library.repository.DocumentRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    MessageSenderImpl messageSender;

    public static Logger logger = LogManager.getLogger(DocumentServiceImpl.class);

    public ArrayList<Document> findAllDocuments() {
        try {
            return (ArrayList<Document>) documentRepository.findAll();
        } catch (Exception e) {
            logger.error(
                    "Error occured during finding all documents from Document collection in DocumentServiceImpl class with message:"
                            + e.getMessage());
            return null;
        }
    }

    public void saveDocument(Document document) {
        try {
            documentRepository.save(document);
        } catch (Exception e) {
            logger.error("Error occured during saving a Document collection in DocumentServiceImpl class with message:"
                    + e.getMessage());
        }
    }

    public Document findById(UUID id) {
        return documentRepository.findByDocumentId(id);
    }


    public boolean checkURL(Document document, String username) {//parameter should be document object
        HttpURLConnection httpUrlConn;
        String url = document.getUrl();

        try {
            System.out.println(url);
            System.out.println(encode(url));
            System.out.println(document.getVisibility());
            Document.Visibility visibility = document.getVisibility();
            ArrayList<String> conceptTags = document.getConceptTags();
            httpUrlConn = (HttpURLConnection) new URL(url).openConnection();
            httpUrlConn.setRequestMethod("HEAD");
            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);
            logger.info("Response Code: " + httpUrlConn.getResponseCode());
            logger.info("Response Message: " + httpUrlConn.getResponseMessage());
            if (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                UUID id = UUID.randomUUID();
                System.out.println("in doc service");
                logger.info("in document service");
//                float rating = (float) 0.0;
//					LocalDateTime addedOn = LocalDateTime.now();
//					LocalDateTime updatedOn = LocalDateTime.now();//add email,termTags,visibility here
//					Visibility visibility = Visibility.PRIVATE;
//					String addBy = addedBy;
//					if(visibility)
//					{
//						visible = Visibility.PRIVATE;
//					}
//					else
//					{
//						visible = Visibility.PUBLIC;
//					}
                ArrayList<Flag> flagList = new ArrayList<Flag>();
                Flag docFlag = new Flag(null, null, null, null);
                flagList.add(docFlag);
                Document doc = new Document(id, null, url, null, null, null, null, null, username, null, null, null,
                        null, null, conceptTags, visibility, flagList, null, "in document service");
                messageSender.sendToFirewall(doc);
                if (existsByUrl(url)) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error happened with message" + e.getMessage());
            return false;
        }
    }

    public ArrayList<Document> findByUrl(String url) {
        return documentRepository.findByUrl(url);
    }

    public boolean existsByUrl(String url) {
        return documentRepository.existsByUrl(url);
    }

    public static String encode(String url) {
        try {
            String encodeURL = URLEncoder.encode(url, "UTF-8");
            return encodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while encoding" + e.getMessage();
        }
    }

    @Override
    public String findUserName(HttpServletRequest request) {
        String user = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("JWT-TOKEN")) {
                    try {
                        user = Jwts.parser().setSigningKey("IWPCRQO").parseClaimsJws(cookie.getValue()).getBody()
                                .get("n", String.class);
                        System.out.println(user + "user::");
                    } catch (ExpiredJwtException exception) {
                        System.out.println("exception" + exception);
                        return null;
                    }
                }
            }
        }
        return user;
    }

    @Override
    public ArrayList<Document> findByDate(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = startDate.plusHours(24);
        ArrayList<Document> document = this.documentRepository.findByAddedOnBetween(startDate, endDate);
        System.out.println("djjwd is " + document);
        return document;
    }

    public ArrayList<Document> findByAddedBy(HttpServletRequest request) {
        String userName = findUserName(request);
        System.out.println("findByAddedBy: " + userName + "..." + documentRepository.findByAddedBy(userName));
        return documentRepository.findByAddedBy(userName);
    }
//	@Override
//	  public String findUserName(HttpServletRequest request) {
//	      String user = null;
//	      for (Cookie cookie : request.getCookies()) {
//	          if (cookie.getName().equals("JWT-TOKEN")) {
//	              try {
//	                  user = Jwts.parser().setSigningKey(SIGNINGKEY).parseClaimsJws(cookie.getValue()).getBody()
//	                          .get("un", String.class);
//	              } catch (ExpiredJwtException exception) {
//	                  return null;
//	              }
//	          }
//	      }
//	      return user;
//	  }


}
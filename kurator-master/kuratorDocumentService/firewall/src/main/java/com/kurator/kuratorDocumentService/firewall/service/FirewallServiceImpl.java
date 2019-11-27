package com.kurator.kuratorDocumentService.firewall.service;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.kurator.kuratorDocumentService.library.model.Document;
import com.kurator.kuratorDocumentService.library.model.Status;
import com.kurator.kuratorDocumentService.library.service.IDocumentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FirewallServiceImpl implements IFirewallService {

	@Autowired
	IDocumentService documentService;

	@Value("${admin.config.blaclistedDomains}")
	private String blacklist;

	public static Logger logger = LogManager.getLogger(FirewallServiceImpl.class);

	public boolean checkURL(Document doc) {
		String url = doc.getUrl();
		HttpURLConnection httpUrlConn;
		try {
			httpUrlConn = (HttpURLConnection) new URL(url).openConnection();
			httpUrlConn.setRequestMethod("HEAD");
			// Set timeouts in milliseconds
			httpUrlConn.setConnectTimeout(30000);
			httpUrlConn.setReadTimeout(30000);
			// Print HTTP status code/message for your information.
			logger.info("Response Code in Firewall: " + httpUrlConn.getResponseCode());
			logger.info("Response Message in Firewall: " + httpUrlConn.getResponseMessage());
			if (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				URL domainCheck = new URL(url);
				System.out.println("Host is.........." + domainCheck.getHost());
				JSONParser parser;
				try {
					parser = new JSONParser();
					JSONObject blackList = (JSONObject) parser. parse(blacklist);
					System.out.println(blackList);			
					System.out.println(blackList.get("domains"));
					ArrayList<String> blacklistedDomains = (ArrayList<String>)blackList.get("domains");
					for (int j=0;j<blacklistedDomains.size();j++)
				    {
							if (domainCheck.getHost().equals(blacklistedDomains.get(j))) {
								Status.Validity validity = Status.Validity.Invalid;
								Status.Reason reason = Status.Reason.Blacklisted;
								Status status = new Status(validity, reason);
								doc.setStatus(status);
								System.out.println("in firewall service impl...............");
								System.out.println(doc);
								documentService.saveDocument(doc);
								return false;
							}
				    }
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return true;
			} else {
				if (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
					Status.Validity validity = Status.Validity.Invalid;
					Status.Reason reason = Status.Reason.Timedout;
					Status status = new Status(validity, reason);
					doc.setStatus(status);
					documentService.saveDocument(doc);//
				} else if (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN) {
					Status.Validity validity = Status.Validity.Invalid;
					Status.Reason reason = Status.Reason.AuthenticationRequired;
					Status status = new Status(validity, reason);
					doc.setStatus(status);
					documentService.saveDocument(doc);
				} else {
					if (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
						Status.Validity validity = Status.Validity.Invalid;
						Status.Reason reason = Status.Reason.NotFound;
						Status status = new Status(validity, reason);
						doc.setStatus(status);
						documentService.saveDocument(doc);
					}
				}
//				else 
//				{
//					URL domainCheck=new URL(url);
//					if(domainCheck.getHost()=="facebook") //array list of domains from admin properties(to be implemented)
//					{//domain check(depends on which domains the admin blacklists)
//						Validity validity=Validity.Invalid;
//						Reason reason=Reason.Blacklisted;
//						Status status = new Status(validity, reason);
//						doc.setStatus(status);
//						documentService.saveDocument(doc);
//					}
//				}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occured in FirewallServiceImpl class with message" + e.getMessage());
			return false;
		}
	}

	public boolean toBeCrawledOrNot(Document docById) {
		LocalDateTime lastupdate;
		if (docById.getUpdatedOn() == docById.getAddedOn()) {
			lastupdate = docById.getAddedOn();
		} else {
			lastupdate = docById.getUpdatedOn();
		}
		LocalDateTime to = LocalDateTime.now();
		LocalDateTime from = lastupdate;
		LocalDateTime fromTemp = LocalDateTime.from(from);
		long years = fromTemp.until(to, ChronoUnit.YEARS);
		fromTemp = fromTemp.plusYears(years);

		long months = fromTemp.until(to, ChronoUnit.MONTHS);
		fromTemp = fromTemp.plusMonths(months);

		long days = fromTemp.until(to, ChronoUnit.DAYS);
		fromTemp = fromTemp.plusDays(days);

		long hours = fromTemp.until(to, ChronoUnit.HOURS);
		fromTemp = fromTemp.plusHours(hours);

		long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
		fromTemp = fromTemp.plusMinutes(minutes);

		long seconds = fromTemp.until(to, ChronoUnit.SECONDS);
		fromTemp = fromTemp.plusSeconds(seconds);

//		long millis = fromTemp.until(to, ChronoUnit.MILLIS);

		if (minutes >= 5) {
			return true;
		} else {
			return false;
		}

//		if ((to.compareTo(lastupdate)) >= 6) {
//			return true;
//		} else {
//			return false;
//		}
	}

	// checks if the document is existing or not
	public boolean documentExistsOrNot(String url) {
		if (documentService.existsByUrl(url)) {
			return true;
		} else {
			return false;
		}
	}

	public static String encode(String url) {
		try {
			String encodeURL = URLEncoder.encode(url, "UTF-8");
			return encodeURL;
		} catch (UnsupportedEncodingException e) {
			return "Issue while encoding" + e.getMessage();
		}
	}
}

package com.kurator.kuratorDocumentService.www.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
// import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;

import com.kurator.kuratorDocumentService.library.model.Document;
import com.kurator.kuratorDocumentService.library.model.Flag;
import com.kurator.kuratorDocumentService.library.model.ResponseModel;
import com.kurator.kuratorDocumentService.library.service.IDocumentService;
import com.kurator.kuratorDocumentService.www.service.FileStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;

import io.swagger.annotations.ApiOperation;

@RestController
public class DocumentController {

	@Autowired
	private IDocumentService documentService;

	@Autowired
	ResponseModel response;

	@Autowired
	private FileStorageService fileStorageService;

	public static Logger logger = LogManager.getLogger(DocumentController.class);

	//	For getting all documents
	@GetMapping("/documents")
	public ResponseEntity<?> getAllDocs() {
		ArrayList<Document> documentList = new ArrayList<Document>();
		try {
			documentList = documentService.findAllDocuments();
			Collections.sort(documentList, (document1, document2) -> document2.getUpdatedOn().compareTo(document1.getUpdatedOn()));
//			documentList = documentList.sort((document1, document2)->document2.getAddedOn().compareTo(document1.getAddedOn()));
			if (documentList.size() == 0) {
				response.setStatus(200);
				response.setMessage("no documents found");
				response.setCount(0);
				response.setResultList(null);
			}
			response.setStatus(200);
			response.setMessage("Documents retrieved successfully");
			response.setCount(documentList.size());
			response.setResultList(documentList);
			return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
		} catch (Exception exception) {
			response.setStatus(200);
			response.setMessage("Some error occured");
			response.setCount(0);
			response.setResultList(null);
			return new ResponseEntity<ResponseModel>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @GetMapping("/documents/recentAdded")
    public ResponseEntity<?> recentDocumentList(HttpServletRequest request) {
        try {
            ArrayList<Document> listDocuments = documentService.findByAddedBy(request);
            if (listDocuments.size() == 0) {
                response.setStatus(404);
                response.setMessage("no documents found");
                response.setCount(0);
                response.setResultList(null);
            }
            response.setStatus(200);
            response.setMessage("Documents retrieved successfully");
            response.setCount(listDocuments.size());
            response.setResultList(listDocuments);
            return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setStatus(500);
            response.setMessage("Some error occured");
            response.setCount(0);
            response.setResultList(null);
            return new ResponseEntity<ResponseModel>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //	For getting a document by Id
    @GetMapping("/documents/{documentId}")
    public ResponseEntity<?> getById(@PathVariable("documentId") UUID documentId) {
        ArrayList<Document> documentList = new ArrayList<Document>();
        try {
            documentList.add(documentService.findById(documentId));
            if (documentList.size() == 0) {
                response.setStatus(200);
                response.setMessage("no documents found");
                response.setCount(0);
                response.setResultList(null);
            }
            response.setStatus(200);
            response.setMessage("Document retrieved successfully");
            response.setCount(documentList.size());
            response.setResultList(documentList);
            return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setStatus(200);
            response.setMessage("Some error occured");
            response.setCount(0);
            response.setResultList(null);
            return new ResponseEntity<ResponseModel>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//	For adding a document to the database
//	@PostMapping("/documents")
//	public void addDocs(@RequestBody Document document) {
//		try {
//			UUID docId=UUID.randomUUID();
//			document.setDocumentId(docId);
//			documentService.saveDocument(document);
//		} catch (Exception e) {
//			
//			logger.error("Error occured during saving a Document collection in DocumentController class with message:" + e.getMessage());
//		}
//	}

//	Checks if the url entered by the user is valid or not, accordingly saves the document
//	@ApiOperation(value = "Add document to database if it is valid")
//	@PostMapping("/documents")
//	public boolean checkIfURLExists(@RequestBody String url, String email, boolean visibility, ArrayList<String> termTags) {
//		System.out.println(url);
//		System.out.println(email);
//		return documentService.checkURL(url,email,visibility,termTags);// this directly gets saved and added in the firewall queue
//	}

    @ApiOperation(value = "Add document to database if it is valid")
    @PostMapping("/documents")
    public boolean checkIfURLExists(@RequestBody Document document, HttpServletRequest request) {
        System.out.println("cookie..." + request.getCookies());
        String username = documentService.findUserName(request);
        System.out.println("username.........." + username);
        return documentService.checkURL(document, username);// this directly gets saved and added in the firewall queue
    }

    @PostMapping("/bulkUpload")
    public boolean bulkUpload(@RequestBody String bulkUpload, HttpServletRequest request) throws IOException {
        System.out.println("bulkUpload......."+bulkUpload);
        System.out.println("cookie..." + request.getCookies());
        String username = documentService.findUserName(request);
        System.out.println("username.........." + username);
        Document document=new Document();
        ArrayList<String> list=new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new StringReader(bulkUpload));
        String line=reader.readLine();
        while(line!=null)
        {
            System.out.println("line....."+line);
            list.add(line);
            line=reader.readLine();
        }
        System.out.println("list......"+list);
//        String eachUrl="";
//        for(int i=0;i<bulkUpload.length();i++)
//        {
//            while(bulkUpload.charAt(i)!='\n')
//            {
//                eachUrl=eachUrl+bulkUpload.charAt(i);
//            }
//            list.add(eachUrl);
//            System.out.println(eachUrl);
//            eachUrl="";
//        }
        for(String url: list) {
            document.setUrl(url);
            document.setVisibility(Document.Visibility.PUBLIC);
            document.setAddedBy(username);
            documentService.checkURL(document, username);
        }
        return true;
    }

    @PostMapping("/uploadFile")
    public boolean uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String username = documentService.findUserName(request);
        String fileName = fileStorageService.storeFile(file);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
//                .path(fileName).toUriString();

        System.out.println("hello");
        System.out.println(file);

        try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader("classpath:assets/checkUploads/" + fileName);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String url : nextRecord) {
                    System.out.print(url + "\t");
                    Document.Visibility visibility= Document.Visibility.PUBLIC;
                    Document document = new Document(null, null, url, null, null, null,
                            null, null, null, null, null, null, null, null, null, visibility, null, null, null);
                    documentService.checkURL(document, username);
                }
                System.out.println();
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());

        return true;
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //	Patches details to the document being saved when called by other services
    @ApiOperation(value = "Saves the document details, has optional parameters ")
    @PatchMapping("/documents")
    public void updateDocument(@RequestBody Document document) {
        try {
            String id = document.getDocumentId().toString();
            UUID docId = UUID.fromString(id);
            Document doc = documentService.findById(docId);
            doc.setUserRating(document.getUserRating());
            doc.setDescription(document.getDescription());
            doc.setConcepts(document.getConcepts());
            doc.setIntent(document.getIntent());
            doc.setFavouriteOf(document.getFavouriteOf());
            doc.setNumberOfViews(document.getNumberOfViews());
            doc.setTitle(document.getTitle());
            doc.setAddedBy(document.getAddedBy());
            doc.setAddedOn(document.getAddedOn());
            doc.setVisibility(document.getVisibility());
            System.out.println("inside patchmapping");
            System.out.println(doc.getDescription());
            documentService.saveDocument(doc);
//			return doc;
        } catch (Exception e) {
            e.printStackTrace();

            logger.error("Error occured during saving a Document collection in DocumentController class with message:");
//			return null;
        }
    }

    //	Patches details to the document being saved when called by other services
    @ApiOperation(value = "Saves the document details, has optional parameters ")
    @PatchMapping("/documents/flag")
    public void updateDocument(@RequestParam String reason,@RequestParam String comment, @RequestParam String documentID, HttpServletRequest request) {
        try {
//            String id = document.getDocumentId().toString();
            UUID docId = UUID.fromString(documentID);
            Document doc = documentService.findById(docId);
            String username = documentService.findUserName(request);
            LocalDateTime flaggedOn=LocalDateTime.now();
            Flag flag=new Flag(username, flaggedOn, comment, reason);
            ArrayList<Flag> flagList=new ArrayList<>();
            flagList=doc.getDocumentFlag();
            flagList.add(flag);
            doc.setDocumentFlag(flagList);
            System.out.println("inside flagging patchmapping");
            System.out.println(doc.getDescription());
            documentService.saveDocument(doc);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error occured during saving a Document collection in DocumentController class with message:");
        }
    }

    @PatchMapping("/documentfeed")
    public ResponseEntity<?> updateDocumentView(@RequestParam String addedBy, @RequestBody String documentId) {
        try {
//            UUID docId = UUID.fromString(documentId)	;
//            documentService.updateDocumentView(docId, addedBy);
            response.setStatus(200);
            response.setMessage("trending document found");
            response.setResultList(null);
            return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);


        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            response.setStatus(500);
            response.setMessage("INTERNAL SERVER ERROR");
            response.setResultList(null);
            return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/addedDocuments")
    public ResponseEntity<?> getAddedDocuments(@RequestParam("date") String formatDate){
        LocalDate date = LocalDate.parse(formatDate, DateTimeFormatter.ISO_DATE);
        System.out.println("Date received is: " + date);
        ArrayList<Document> documents = this.documentService.findByDate(date);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCount(documents.size());
        responseModel.setStatus(200);
        responseModel.setMessage("Documents retrieved Successfully");
        responseModel.setResultList(documents);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
    @PatchMapping("/claps")
    public void clapForTheDocument(@RequestParam String docId, HttpServletRequest request) {
        try {
            HashSet<String> likedusers = new HashSet<>();
            String userName = documentService.findUserName(request);
            UUID documentId = UUID.fromString(docId);
            Document document = documentService.findById(documentId);
            if (document.getUserRating() == null) {
                likedusers.add(userName);
                document.setUserRating(likedusers);
                documentService.saveDocument(document);
            } else {
                likedusers.addAll(document.getUserRating());
                likedusers.add(userName);
                document.setUserRating(likedusers);
                documentService.saveDocument(document);
            }
        } catch (Exception error) {
            logger.error("Error occured at clapping for document" + error);
        }
    }

    @GetMapping("/claps")
    public int getNoOfClaps(@RequestParam String docId) {
        UUID documentId = UUID.fromString(docId);
        HashSet<String> userList = new HashSet<>();
        try {
            Document document = documentService.findById(documentId);
            if (document.getUserRating() == null) {
                return 0;
            } else {
                userList = document.getUserRating();
                return userList.size();
            }

        } catch (Exception exception) {
            logger.error("exception at get mapping" + exception);
            return 0;
        }
    }
}

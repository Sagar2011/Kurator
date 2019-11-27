package com.kurator.kuratorDocumentService.library;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.*;

import com.kurator.kuratorDocumentService.library.LibraryApplication;
import com.kurator.kuratorDocumentService.library.model.Document;
import com.kurator.kuratorDocumentService.library.model.Flag;
import com.kurator.kuratorDocumentService.library.model.Status;
import com.kurator.kuratorDocumentService.library.service.IDocumentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LibraryApplication.class)
@TestPropertySource("/bootstrap-test.properties")
public class DocumentServiceImplTest {

	@Autowired
	private IDocumentService documentservice;

	private static Document createDocument;
	private static UUID id = UUID.fromString("09588e95-b069-436b-8c07-8ac5e3b10b9a");

//	for creating dummy document object before running test cases
	@BeforeAll
	public static void documentSetup() {
		Float rating=(float) 4.5;
		List<String> anotherList = Arrays.asList("101", "102");
		ArrayList<String> favouriteOf = new ArrayList<String>();
		favouriteOf.addAll(anotherList);
//		HashMap<String,Integer> intentList = Arrays.asList("learn", "know");
//		ArrayList<String> intent = new ArrayList<String>();
//		intent.addAll(intentList);
		List<String> topicList = Arrays.asList("Java", "Angular");
		ArrayList<String> topicTags = new ArrayList<String>();
		topicTags.addAll(topicList);
		LocalDateTime addedOn = LocalDateTime.now();
		LocalDateTime updatedOn = LocalDateTime.now();
		LocalDateTime flaggedOn=LocalDateTime.now();
		Document.Visibility visibility = Document.Visibility.PRIVATE;
		ArrayList<Flag> flagList=new ArrayList<Flag>();
		Flag docFlag=new Flag("Alice.Cooper@outlook.com",flaggedOn,"Irrelevant Content","Bad content");
		flagList.add(docFlag);
		Status.Validity validity= Status.Validity.Valid;
		Status.Reason reason= Status.Reason.NotDuplicate;
		Document.Type type= Document.Type.Website;
		Status status=new Status(validity,reason);
		createDocument = new Document(id, "java", "java.com", "introduction", null, null,type, null,
				"string", addedOn, "string", updatedOn, null, null, topicTags, visibility, flagList,status,null);
	}

//	tests if the document is getting saved
	@Test
	void saveThisDocumentTest() {
		documentservice.saveDocument(createDocument);
		Document savedDocument = documentservice.findById(createDocument.getDocumentId());
		assertEquals(createDocument, savedDocument);
	}

//	tests if the document is getting fetched by id
	@Test
	void findByDocumentIdTest() {
		documentservice.saveDocument(createDocument);
		Document doc = documentservice.findById(id);
		UUID docId = doc.getDocumentId();
		String expected = docId.toString();
		String actual = id.toString();
		assertEquals(expected, actual);
	}
}

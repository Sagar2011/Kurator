package com.kurator.crawler.crawlerProcessor;

import com.kurator.library.model.DocumentContent;
import com.kurator.library.model.Message;
import com.kurator.library.service.DocContentServiceImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class Crawler implements ICrawler {
//	@Autowired
//    Processor pipeline;

    @Autowired
    DocContentServiceImpl docContentServiceImpl;

    Document htmlDocument;
    final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    public void crawl(Message msg){
         ArrayList<String> links = new ArrayList<String>();
//        ArrayList<String> titles= new ArrayList<String>();
         String documentTitle;
         ArrayList<String> content= new ArrayList<String>();
         ArrayList<String> headList= new ArrayList<>();
        //   private ArrayList<String> meta= new ArrayList<String>();
         ArrayList<String> h1list= new ArrayList<String>();
         ArrayList<String> h2list= new ArrayList<String>();
         ArrayList<String> h3list= new ArrayList<String>();
         ArrayList<String> h4list= new ArrayList<String>();
         ArrayList<String> h5list= new ArrayList<String>();
         ArrayList<String> h6list= new ArrayList<String>();
         String description = "No Description found for this document!";

//		/ We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
        UUID docId=msg.getDocumentId();
        String url= msg.getUrl();
        DocumentContent documentContent = new DocumentContent();
        documentContent.setUrl(url);
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
            // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
//                return null;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            Elements titlesOnPage= htmlDocument.select("h1");
            Elements titles2OnPage= htmlDocument.select("h2");
            Elements titles3OnPage= htmlDocument.select("h3");
            Elements titles4OnPage= htmlDocument.select("h4");
            Elements titles5OnPage= htmlDocument.select("h5");
            Elements titles6OnPage= htmlDocument.select("h6");
            Elements contentOnPage= htmlDocument.select("p");
            Elements headOnPage= htmlDocument.select("head");
            Elements metaOnPage= htmlDocument.select("meta");
            documentTitle=htmlDocument.title();
            documentContent.setTitle(documentTitle);

            for(Element title : titlesOnPage)
            {
                h1list.add(title.text().toString());
            }
            documentContent.setH1TagList(h1list);
            for(Element title : titles2OnPage)
            {
                h2list.add(title.text().toString());
            }
            documentContent.setH2TagList(h2list);
            for(Element title : titles3OnPage)
            {
                h3list.add(title.text().toString());
            }
            documentContent.setH3TagList(h3list);
            for(Element title : titles4OnPage)
            {
                h4list.add(title.text().toString());
            }
            documentContent.setH4TagList(h4list);
            for(Element title : titles5OnPage)
            {
                h5list.add(title.text().toString());
            }
            documentContent.setH5TagList(h5list);
            for(Element title : titles6OnPage)
            {
                h6list.add(title.text().toString());
            }
            documentContent.setH6TagList(h6list);
            for(Element contents : contentOnPage)
            {
                content.add(contents.text().toString());
            }
            documentContent.setContentList(content);
            for(Element head: headOnPage)
            {
                headList.add(head.text().toString());
            }

            for(Element meta : metaOnPage)
            {
                if(meta.attr("name").toString().toLowerCase().contains("description")) {
                    description= meta.attr("content").toString();
                }

                headList.add(meta.attr("content").toString());
            }
            documentContent.setDescription(description);
            documentContent.setHeadList(headList);
//            System.out.println(meta);
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                links.add(link.absUrl("href"));
            }
//             UUID hardcodedID=UUID.fromString("a2405a78-e44d-11e9-81b4-2a2ae2dbcce4");
            documentContent.setLinksList(links);
//            documentContent.setDocumentId(UUID.randomUUID());
            documentContent.setDocumentId(docId);
            documentContent.setIndexingStatus(DocumentContent.IndexingStatus.CRAWLED);
            docContentServiceImpl.saveAllCrawlingDetails(documentContent);
//            documentContent.setDescription("Description not available");
//            LinkedHashMap<String, Object> content = new LinkedHashMap<>();
//            content.put("documentId", documentContent.getDocumentId());
//            content.put("url", doc)
//            return content;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
//            return null;
            System.out.println("Unsuccessful HTTP request" );
        }

    }
//	public List<String> getLinks()
//    {
//        return this.links;
//    }
//    public List<String> geth1lList()
//    {
//        return this.h1list;
//    }
//    public List<String> geth2lList()
//    {
//        return this.h2list;
//    }
//    public List<String> geth3lList()
//    {
//        return this.h3list;
//    }
//    public List<String> geth4lList()
//    {
//        return this.h4list;
//    }
//    public List<String> geth5lList()
//    {
//        return this.h5list;
//    }
//    public List<String> geth6lList()
//    {
//        return this.h6list;
//    }
//
//    public List<String> getContent()
//    {
//        return this.content;
//    }
//    public List<String> getHead()
//    {
//    	head.addAll(meta);
//        return head;
//    }

}

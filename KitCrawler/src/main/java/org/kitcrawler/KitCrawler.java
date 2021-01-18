package org.kitcrawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class KitCrawler extends WebCrawler{

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");

    private int docNum = 0;

    /**
     * Specify whether the given url should be crawled or not based on
     * the crawling logic. Here URLs with extensions css, js etc will not be visited
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        System.out.println("shouldVisit: " + url.getURL().toLowerCase());

        String href = url.getURL().toLowerCase();
        boolean result = !FILTERS.matcher(href).matches();

        if(result)
            System.out.println("URL Should Visit");
        else
            System.out.println("URL Should not Visit");

        return result;
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by the program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

//        List<org.kitcrawler.Page> pageList = new ArrayList<>();

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String title = htmlParseData.getTitle(); //extract text from page
            String text = htmlParseData.getText(); //extract text from page
            String html = htmlParseData.getHtml(); //extract html from page
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("---------------------------------------------------------");
            System.out.println("Page URL: " + url);
            System.out.println("Title: " + title);
            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
            System.out.println("---------------------------------------------------------");

            org.kitcrawler.Page readPage = new org.kitcrawler.Page();
            readPage.setPageURL(url);
            readPage.setTitle(title);
            readPage.setTextData(text);
            readPage.setHtmlData(html);
            readPage.setLinks(links);
            readPage.setTextLength(text.length());
            readPage.setHtmlLength(html.length());
            readPage.setOutgoingLinks(links.size());

            WriteToXML(readPage);

        }
    }

    private void WriteToXML(org.kitcrawler.Page page) {
        try {

            final String xmlFilePath = "C:\\data\\crawl\\root\\xmlfile.xml";

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            File xmlFile;
            Document document;
            try {
                xmlFile= new File(xmlFilePath);
                if (xmlFile != null) {
                    document = documentBuilder.parse(xmlFile);
                } else {
                    document = documentBuilder.newDocument();
                }
            } catch (Exception e) {
                System.out.println(e);
                document = documentBuilder.newDocument();
            }

            Element root = document.getDocumentElement();
            if (root == null) {
                root = document.createElement("TREC");
                document.appendChild(root);
            }

            Element doc = document.createElement("DOC");
            doc.appendChild(document.createTextNode(page.getTitle()));


            Element docNo = document.createElement("DOCNO");
            docNo.appendChild(document.createTextNode(String.valueOf(docNum++)));
            doc.appendChild(docNo);

            root.appendChild(doc);


//            Element url = document.createElement("URL");
//            url.appendChild(document.createTextNode(page.getPageURL()));
//            doc.appendChild(url);
//
//            Element textLength = document.createElement("TextLength");
//            textLength.appendChild(document.createTextNode(String.valueOf(page.getTextLength())));
//            doc.appendChild(textLength);
//
//            Element htmlLength = document.createElement("HTMLLength");
//            htmlLength.appendChild(document.createTextNode(String.valueOf(page.getHtmlLength())));
//            doc.appendChild(htmlLength);

//            Element text = document.createElement("TEXT");
//            text.appendChild(document.createTextNode(page.getTextData().substring(0,100)));
//            doc.appendChild(text);

//            Element html = document.createElement("HTML");
//            html.appendChild(document.createTextNode(page.getHtmlData().substring(0,100)));
//            doc.appendChild(html);


//            Element outgoingLinks = document.createElement("OutgoingLinks");
//            outgoingLinks.appendChild(document.createTextNode(String.valueOf(page.getOutgoingLinks())));
//            doc.appendChild(outgoingLinks);

//            root.appendChild(doc);

//            Element docNo = document.createElement("DOCNO");
//            docNo.setNodeValue(String.valueOf(docNum++));
//            doc.appendChild(docNo);

            // set attributes to doc element
//            Attr url = document.createAttribute("URL");
//            url.setValue(page.getPageURL());
//            doc.setAttributeNode(url);

//            Attr text = document.createAttribute("Text");
//            text.setValue(page.getTextData());
//            doc.setAttributeNode(text);
//
//            Attr html = document.createAttribute("Html");
//            html.setValue(page.getHtmlData());
//            doc.setAttributeNode(html);
//
//            Attr links = document.createAttribute("Links");
//            links.setValue(page.getLinks().toString());
//            doc.setAttributeNode(links);
//
//            Attr textLength = document.createAttribute("TextLength");
//            textLength.setValue(String.valueOf(page.getTextLength()));
//            doc.setAttributeNode(textLength);
//
//            Attr htmlLength = document.createAttribute("HTMLLength");
//            htmlLength.setValue(String.valueOf(page.getHtmlLength()));
//            doc.setAttributeNode(htmlLength);
//
//            Attr linkLength = document.createAttribute("NoOfLinks");
//            linkLength.setValue(String.valueOf(page.getOutgoingLinks()));
//            doc.setAttributeNode(linkLength);
//            }

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

            System.out.println("Done appending Page");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
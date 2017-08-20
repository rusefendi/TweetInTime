package com.rusefendi.posting.rss;

import com.rusefendi.posting.model.Post;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RSSFeedParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String ITEM = "item";
    static final String LINK = "link";

    final URL url;

    public RSSFeedParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Post> readFeed() {
        List<Post> entries = new ArrayList<Post>();
        Post post = null;
        try {
            boolean isFeedHeader = true;
            String description = "";
            String title = "";
            String link = "";

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();
                    switch (localPart) {
                        case ITEM:
                            if (isFeedHeader) {
                                isFeedHeader = false;
                            }
                            break;
                        case TITLE:
                            title = getCharacterData(eventReader);
                            break;
                        case DESCRIPTION:
                            description = getCharacterData(eventReader);
                            break;
                        case LINK:
                            link = getCharacterData(eventReader);
                            break;
                    }
                } else if (event.isEndElement()) {
                    if ((event.asEndElement().getName().getLocalPart() == (ITEM)) && (!(getURL(description).equals("")))) {
                        post = new Post(title, description, link);
                        post.setTitle(correct(title));
                        post.setImageUrl(getURL(description));
                        post.setLink(link);
                        entries.add(post);
                        continue;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }

    private String getCharacterData(XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        XMLEvent event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getURL(String data){
        Pattern pattern = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()){
            return matcher.group();
        }
        return "";
    }

    private String correct(String data){
        return data.replaceAll("&quot;", "\"");
    }
}
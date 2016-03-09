package com.example.map;

import java.io.IOException;
import java.net.ContentHandler;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.bean.Place;
/**
 * ½âÎö
 * @author ly12974
 *
 */
public class GpxFileContentHandler extends DefaultHandler {
	
	private String currentValue;
    private Place location;
    private List<Place> locationList;
	
	
    public GpxFileContentHandler() {
        locationList = new ArrayList<Place>();
    }

    public List<Place> getLocationList() {
        return locationList;
    }
    
    
    @Override
    public void startElement(String uri, String localName, String qName,
    		Attributes attributes) throws SAXException {
    	if (localName.equalsIgnoreCase("trkpt")) {
            location = new Place("gpxImport");
            location.setLat(Double.parseDouble(attributes.getValue("lat").trim()));
            location.setLon(Double.parseDouble(attributes.getValue("lon").trim()));
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
    		throws SAXException {
    	if (localName.equalsIgnoreCase("trkpt")) {
            locationList.add(location);
        }
    }
    
    
    @Override
    public void characters(char[] ch, int start, int length)
    		throws SAXException {
    	currentValue = new String(ch,start,length);
    }
   


}

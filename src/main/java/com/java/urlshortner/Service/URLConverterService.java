package com.java.urlshortner.Service;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.urlshortner.Common.IDConverter;
import com.java.urlshortner.Repo.URLRepository;

@Service
public class URLConverterService {
    private static final Logger LOGGER=LoggerFactory.getLogger(URLConverterService.class);
    private final URLRepository urlRepository;

    @Autowired
    public URLConverterService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenURL(String localURL,String longUrl){
        LOGGER.info("Shortening URL: " + longUrl);
        Long id=urlRepository.incrementID();
        String uniqueID=IDConverter.INSTANCE.createUniqueID(id);
        urlRepository.saveUrl("url:"+id, longUrl);
        String baseString =formatLocalURLFromShortener(localURL);
        String shortenedURL=baseString+uniqueID;
        return shortenedURL;
    }

    public String getLongURLFromID(String uniqueID) throws Exception{
        Long dictionaryKey= IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
        String longUrl=urlRepository.getUrl(dictionaryKey);
        LOGGER.info("Converting shortened URL back to "+longUrl);
        return longUrl;
    }

    private String formatLocalURLFromShortener(String localURL){
        String[] addressComponents=localURL.split("/");
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<addressComponents.length-1;i++){
            sb.append(addressComponents[i]);
        }
        sb.append("/");
        return sb.toString();
    }
}

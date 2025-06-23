package com.java.urlshortner.Controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.urlshortner.Common.URLValidator;
import com.java.urlshortner.Service.URLConverterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);
    private final URLConverterService urlConverterService;
    public URLController(URLConverterService urlConverterService) {
        this.urlConverterService = urlConverterService;
    }

    @RequestMapping(value="/shortener", method=RequestMethod.POST,consumes={"application/json"})
    public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, HttpServletRequest request) throws Exception{
        LOGGER.info("Received url to shorten:"+shortenRequest.getUrl());
        String longUrl=shortenRequest.getUrl();
        if(URLValidator.INSTANCE.validateURL(longUrl)){
            String localURL=request.getRequestURL().toString();
            String shortenedURL=urlConverterService.shortenURL(localURL,longUrl);
            LOGGER.info("Shortened URL: " + shortenedURL);
            return shortenedURL;
        } else {
            throw new Exception("Invalid URL provided");
        }
        
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String id,HttpServletRequest request, HttpServletResponse response) throws IOException,URISyntaxException,Exception{
        LOGGER.debug("Received request to redirect for id: " + id);
        String redirectUrlString=urlConverterService.getLongURLFromID(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://"+redirectUrlString);
        return redirectView;
    }
}

class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest(){
    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url){
        this.url=url;
    }
    public String getUrl(){
        return url;
    }
}

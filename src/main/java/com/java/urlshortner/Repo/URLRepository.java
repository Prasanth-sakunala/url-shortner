package com.java.urlshortner.Repo;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import org.springframework.stereotype.Repository;

@Repository
public class URLRepository {

    private final Jedis jedis;
    private final String idKey;
    private final String urlKey;
    private static final  Logger LOGGER= (Logger) LoggerFactory.getLogger(URLRepository.class);

    public URLRepository(Jedis jedis) {
        this.jedis = jedis;
        this.idKey = "id";
        this.urlKey = "url:";
    }

    public URLRepository(Jedis jedis, String idKey, String urlKey){
        this.jedis = jedis;
        this.idKey = idKey;
        this.urlKey = urlKey;
    }
    public Long incrementID(){
        Long id=jedis.incr(idKey);
        LOGGER.info("Incrementing ID: " + (id - 1));
        return id-1; 
    }

    public void saveUrl(String key,String longUrl){
        LOGGER.info("Saving: "+longUrl+" at "+ key);
        jedis.hset(urlKey, key, longUrl);
    }

    public String getUrl(Long id) throws Exception{
        LOGGER.info("Retrieving at "+id);
        String url=jedis.hget(urlKey, "url:"+id);
        if(url == null){
            throw new Exception("URL at key: " + id + " does not exist");
        }

        return jedis.hget(urlKey, "url:" + id);
    }
}

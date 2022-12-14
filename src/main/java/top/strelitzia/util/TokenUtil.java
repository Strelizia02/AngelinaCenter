package top.strelitzia.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtil {
  
    @Autowired
    private StringRedisTemplate redisTemplate;


    public String createToken(Integer id) {
      JSONObject obj = new JSONObject();
      obj.append("id", id);
      obj.append("sat", new Random().nextInt(9999));
      String token = Base64.encodeBase64String(obj.toString().getBytes());
      redisTemplate.opsForValue().set(token, String.valueOf(id), 30, TimeUnit.MINUTES);
      return token;
    }

    public Integer getTokenId(String token) {
      JSONObject obj = new JSONObject(Base64.decodeBase64(token));
      return obj.getInt("id");
    }
}

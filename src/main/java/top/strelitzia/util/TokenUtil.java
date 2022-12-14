package top.strelitzia.util;

@Component
public class TokenUtil {
  
  @Autowired
  Jedis jedis;
  
  public String createToken(String id) {
    JSONObject obj = new JSONObject();
    obj.append("id", id);
    obj.append("sat", new Random().nextInt(9999)));
    String token = Base64.encodeBase64String(obj.toString.getBytes());
    jedis.put(id, token);
    return token;
  }
  
  public String getTokenId(String token) {
    JSONObject obj = new JSONObject(Base64.decodeBase64(token));
    return obj.getString("id");
  }
}

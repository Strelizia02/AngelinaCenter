package top.strelitzia.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.strelitzia.dao.UserMapper;
import top.strelitzia.model.Info;
import top.strelitzia.model.OpenAiModel;
import top.strelitzia.model.UserInfo;
import top.strelitzia.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenUtil tokenUtil;

    public Info sendChatGPT(OpenAiModel openAiModel, String botId) {
        //TODO 验证apikey的余额
        Integer num = userMapper.selectTokenByBotId(botId);
        if (num <= 0) {
            return new Info(false, "您的token余额小于0");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer ");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openAiModel.getModel());
        requestBody.put("prompt", openAiModel.getPrompt());
        requestBody.put("temperature", openAiModel.getTemperature());
        requestBody.put("max_tokens", openAiModel.getMax_tokens());

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        String body = restTemplate.postForEntity("https://api.openai.com/v1/completions", httpEntity, String.class).getBody();

        if (body == null) {
            return new Info(false, "接口错误");
        }
        Long totalToken = new JSONObject(body).getJSONObject("token").getLong("totalToken");
        
        userMapper.updateTokenByBotId(botId, totalToken);
        return new Info(true,  "剩余token总数" + totalToken);
    }

    public Boolean buyOpenAiToken(String token, String id, Long num) {
        Integer adminId = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(adminId);
        if (userInfo.getIsAdmin() == 0) {
            return false;
        }
        userMapper.updateTokenByBotId(id, num);
        return true;
    }
}

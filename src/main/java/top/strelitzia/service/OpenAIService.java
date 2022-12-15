package top.strelitzia.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.strelitzia.models.OpenAiModel;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIService {

    @Autowired
    private RestTemplate restTemplate;

    public String getApiKey(String id) {
        return null;
    }

    public String buyOpenAiToken(String id) {
        return null;
    }

    public String sendChatGPT(OpenAiModel openAiModel, String botId) {
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
        
        Integer totalToken = new JSONObject(body).getJSONObject("token").getInt("totalToken");
        
        userMapper.updateTokenByBotId(id, totalToken);
        return new Info(true, nody);
    }

    public Boolean buyOpenAiToken(String token, String id, Long num) {
        String adminId = tokenUtil.getTokenId(token);
        UserInfo userInfo = userMapper.selectUserInfo(adminId);
        if (!userinfo.getIsadmin) {
            return false;
        }
        userMapper.updateToken(id, num);
        return true;
    }
}

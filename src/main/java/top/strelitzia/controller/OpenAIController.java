package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.annotation.Token;
import top.strelitzia.models.OpenAiModel;
import top.strelitzia.service.OpenAIService;
import top.strelitzia.vo.JsonResult;

/**
 * @author strelitzia
 * @Date 2022/12/12
 * 下载文件专用接口
 **/
@RequestMapping("openai")
@RestController
@Slf4j
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    /**
     * 生成一个新的apiKey
     * @param id 登录人id
     * @return 新的api key
     */
    @Token
    @PostMapping("getApiKey")
    public JsonResult<String> getApiKey(@RequestParam String id) {
        return JsonResult.success(openAIService.getApiKey(id));
    }

    /**
     * 转发OpenAI的接口
     * @param body OpenAI的参数体
     * @param apiKey 购买的apikey
     * @return 返回结果
     */
    @PostMapping("getApiKey")
    public JsonResult<String> openAiApi(@RequestBody OpenAiModel body, @RequestHeader(value = "apiKey", required = false) String apiKey) {
        String s = openAIService.sendChatGPT(body, apiKey);
        return JsonResult.success(s);
    }

    /**
     * 调用付款接口，购买OpenAI的apikey的token个数
     * @param id
     * @return
     */
    @Token
    @PostMapping("getApiKey")
    public JsonResult<String> buyOpenAiToken(@RequestParam String id) {
        String s = openAIService.buyOpenAiToken(id);
        return JsonResult.success(s);
    }
}

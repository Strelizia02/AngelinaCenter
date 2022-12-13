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
     * 转发OpenAI的接口
     * @param body OpenAI的参数体
     * @param apiKey 购买的apikey
     * @return 返回结果
     */
    @PostMapping("getApiKey")
    public JsonResult<String> openAiApi(@RequestBody OpenAiModel body, @RequestHeader(value = "apiKey", required = false) String apiKey) {
        //TODO Header里携带id参数就可以了，直接用id校验
        
        //TODO 每次调用完了，要给用户统计使用量，最好也做个图表
        String s = openAIService.sendChatGPT(body, apiKey);
        return JsonResult.success(s);
    }

    /**
     * 分发token，由超级管理员给用户分配token
     * @param id
     * @return
     */
    @Token
    @PostMapping("addOpenAiToken")
    public JsonResult<String> addOpenAiToken(@RequestParam String id) {
        String s = openAIService.buyOpenAiToken(id);
        return JsonResult.success(s);
    }
}

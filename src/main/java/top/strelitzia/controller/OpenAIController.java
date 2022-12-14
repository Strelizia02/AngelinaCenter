package top.strelitzia.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.annotation.Token;
import top.strelitzia.model.Info;
import top.strelitzia.model.OpenAiModel;
import top.strelitzia.service.OpenAIService;
import top.strelitzia.vo.JsonResult;

/**
 * @author strelitzia
 * @Date 2022/12/12
 * openAI转发相关接口
 **/
@RequestMapping("openai")
@RestController
@Slf4j
@Api("OpenAI的转发相关接口")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    /**
     * 转发OpenAI的接口
     * @param body OpenAI的参数体
     * @return 返回结果
     */
    @PostMapping("getApiKey")
    @ApiOperation("转发OpenAI，需要传过来BotId")
    public JsonResult<Info> openAiApi(@RequestBody OpenAiModel body, @RequestHeader(value = "botId", required = false) String botId) {
        //QQ号容易伪造，Botid不好伪造
        return JsonResult.success(openAIService.sendChatGPT(body, botId));
    }

    /**
     * 分发token，由超级管理员给用户分配token
     * @param id
     * @return
     */
    @Token
    @PostMapping("addOpenAiToken")
    @ApiOperation("管理员给某个账号添加token余额")
    public JsonResult<Boolean> addOpenAiToken(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam String id, @RequestParam Long num) {
        return JsonResult.success(openAIService.buyOpenAiToken(token,id, num));
    }
}

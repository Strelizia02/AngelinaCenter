package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.model.*;
import top.strelitzia.service.DataService;
import top.strelitzia.vo.JsonResult;

import java.util.List;


/**
 * @author strelitzia
 * @Date 2022/12/12
 * Bot同步数据用接口
 **/
@RequestMapping("bot")
@RestController
@Slf4j
@Api("Bot用机机接口及相关信息查询接口")
public class BotController {

    @Autowired
    private DataService dataService;

    /**
     * 心跳接口
     * @param bot bot信息，包含了一个botid和其下账号的在线信息
     */
    @PostMapping("heartBeats")
    @ApiOperation("Bot使用的机机接口，用于心跳保持在线状态")
    public JsonResult<String> heartBeats(@RequestBody Bot bot) {
        log.info(bot.toString());
        return JsonResult.success(dataService.heartBeats(bot));
    }
  
    /**
     * 同步功能调用数据
     * @return true/false
     */
    @PostMapping("pushData")
    @ApiOperation("Bot使用的机机接口，用于定时同步Bot的运行数据")
    public JsonResult<Boolean> pushData(@RequestBody PushData list) {
        return JsonResult.success(dataService.pushData(list));
    }
}

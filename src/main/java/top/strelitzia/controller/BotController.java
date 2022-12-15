package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.annotation.Token;
import top.strelitzia.models.*;
import top.strelitzia.service.UserService;
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
public class BotController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private NoticeService noticeService;

    /**
     * 心跳接口
     * @param bot bot信息，包含了一个botid和其下账号的在线信息
     */
    @PostMapping("heartBeats")
    public JsonResult<String> heartBeats(@RequestBody Bot bot) {
      //TODO 心跳接口，要传过来Bot的id，所有账号的在线情况，返回Botid（第一次心跳需要生成id）
        return JsonResult.success(dataService.heartBeats(bot));
    }
  
    /**
     * 同步功能调用数据
     * @return true/false
     */
    @PostMapping("pushData")
    public JsonResult<Boolean> pushData(@RequestHeader String token, @RequestBody List<Function> list) {
        return JsonResult.success(dataService.pushData(token, list));
    }
}

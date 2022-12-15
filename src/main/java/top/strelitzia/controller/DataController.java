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
 * 查询数据接口
 **/
@RequestMapping("data")
@RestController
@Slf4j
public class DataController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private NoticeService noticeService;
  
    /**
     * 获取某个账号下所有Bot的列表
     * @return 全部信息
     */
    @Token
    @PostMapping("getBotList")
    public JsonResult<List<Bot>> getBotList(@RequestHeader String token) {
        //TODO 数据库里记录的是每个账号的最后一次心跳时间，超过五分钟没有心跳就算离线
        return JsonResult.success(dataService.getBotList(token));
    }
  
    /**
     * 获取Bot图表数据
     * @return 在线几个、离线几个、一共几个
     */
    @GetMapping("getBotBoard")
    public JsonResult<BotData> getBotBoard() {
        //TODO 统计一下，返回一个数据量少一点的
        return JsonResult.success(dataService.getBotBoard());
    }
  
    /**
     * 获取全部功能的列表
     * @return 每个功能的详细调用次数
     */
    @PostMapping("getFuncList")
    public JsonResult<List<Function>> getFuncList() {
        return JsonResult.success(dataService.getFuncList());
    }
    
    /**
     * 获取某个人的功能的列表
     * @return 每个功能的详细调用次数
     */
    @Token
    @PostMapping("getSomeOneFuncList")
    public JsonResult<List<Function>> getSomeOneFuncList(@RequestHeader String token) {
        return JsonResult.success(dataService.getSomeOneFuncList(token));
    }
}

package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.annotation.Token;
import top.strelitzia.model.*;
import top.strelitzia.service.DataService;
import top.strelitzia.vo.JsonResult;

import java.util.HashMap;
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
    private DataService dataService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
  
    /**
     * 获取某个账号下所有Bot的列表
     * @return 全部信息
     */
    @Token
    @GetMapping("getBotList")
    public JsonResult<List<Bot>> getBotList(@RequestHeader(value = "Authorization", required = false) String token) {
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
    @GetMapping("getFuncList")
    public JsonResult<List<Function>> getFuncList() {
        return JsonResult.success(dataService.getFuncList());
    }
    
    /**
     * 获取某个人的功能的列表
     * @return 每个功能的详细调用次数
     */
    @Token
    @GetMapping("getSomeOneFuncList")
    public JsonResult<List<Function>> getSomeOneFuncList(@RequestHeader(value = "Authorization", required = false) String token) {
        return JsonResult.success(dataService.getSomeOneFuncList(token));
    }

    @GetMapping("test")
    public JsonResult<Boolean> test() {
        //定时检测然后git clone，成功后发送更新消息
        rabbitTemplate.convertAndSend("DataVersion","", 1);
        
        return JsonResult.success(true);
    }
    
    /**
     * 根据用户传回来的版本号，推送最新的卡池信息
     */
    @GetMapping("getPoolData")
    public JsonResult<List<PoolData>> getPoolData(@RequestParam String botId, @RequestParam String version) {
        return JsonResult.success(dataService.getPoolData(String botId, String version));
    }
    
    /**
     * 分页查询某一页的卡池数据（一页10条写死）
     */
    @Token
    @GetMapping("getAllPoolData")
    public JsonResult<List<PoolData>> getAllPoolData(@RequestParam Integer current) {
        return JsonResult.success(dataService.getAllPoolData(current, dataService.getPoolCount());
    }
    
    /**
     * 写入卡池数据
     */
    @Token
    @GetMapping("setPoolData")
    public JsonResult<List<PoolData>> setPoolData(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody List<PoolData> poolData) {
        return JsonResult.success(dataService.setPoolData(token, poolData);
    }
}

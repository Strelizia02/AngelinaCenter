package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.annotation.Token;
import top.strelitzia.models.LoginInfo;
import top.strelitzia.models.UserProperty;
import top.strelitzia.service.UserPropertyService;
import top.strelitzia.vo.JsonResult;


/**
 * @author strelitzia
 * @Date 2022/12/12
 * 查询用户个人资产接口
 **/
@RequestMapping("data")
@RestController
@Slf4j
public class DataController {

    @Autowired
    private UserPropertyService userPropertyService;

    /**
     * 获取全部公告信息
     * @return 公告信息
     */
    @GetMapping("getNotice")
    public JsonResult<List<Notice>> getNotice() {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 编辑某条公告信息
     * @return 公告信息
     */
    @Token
    @PostMapping("editNotice")
    public JsonResult<Notice> editNotice(@RequestBody Notice notice) {
      //TODO 这里要做权限校验，看看这个人有没有权限编辑
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }

    /**
     * 删除某条公告信息
     * @return true/false
     */
    @Token
    @DeletetMapping("deleteNotice")
    public JsonResult<Boolean> deleteNotice(@RequestParam Integer noticeId) {
      //TODO 这里要做权限校验，看看这个人有没有权限删除
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 创建一条公告
     * @return 公告
     */
    @Token
    @PostMapping("createNotice")
    public JsonResult<Notice> createNotice(@RequestBody Notice notice) {
      //TODO 这里要做权限校验，看看这个人有没有权限创建
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 心跳接口
     * @param Bot bot信息，包含了一个botid和其下账号的在线信息
     * @return true/false
     */
    @PostMapping("heartBeats")
    public JsonResult<Boolean> heartBeats(@RequestBody Bot bot) {
      //TODO 这里要做权限校验，看看这个人有没有权限创建
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 获取全部Bot的列表
     * @return 全部信息
     */
    @Token
    @PostMapping("getBotList")
    public JsonResult<List<Bot>> getBotList() {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 获取Bot图表数据
     * @return 在线几个、离线几个、一共几个
     */
    @PostMapping("getBotBoard")
    public JsonResult<BotData> getBotBoard() {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 同步功能调用数据
     * @return true/false
     */
    @PostMapping("getBotBoard")
    public JsonResult<BotData> pushData(@RequestBody BotData botData) {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 获取全部功能的列表
     * @return 每个功能的详细调用次数(按小时统计)
     */
    @Token
    @PostMapping("getFuncList")
    public JsonResult<List<Function>> getFuncList() {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
  
    /**
     * 获取最近12小时的调用次数数据
     * @return 调用次数
     */
    @PostMapping("getFuncBoard")
    public JsonResult<List<Long>> getFuncBoard() {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
}

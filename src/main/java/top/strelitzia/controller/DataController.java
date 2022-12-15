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
 * 查询用户个人资产接口
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
     * 获取全部公告信息
     * @return 公告信息
     */
    @GetMapping("getNotice")
    public JsonResult<List<Notice>> getNotice() {
        return JsonResult.success(noticeService.getNotice());
    }
  
    /**
     * 编辑某条公告信息
     * @return 公告信息
     */
    @Token
    @PostMapping("editNotice")
    public JsonResult<Notice> editNotice(@RequestHeader String token, @ApiParam(value = "img") @RequestParam MultipartFile img, @ApiParam(value = "id") @RequestParam Integer id, @ApiParam(value = "text") @RequestParam String text) {
        return JsonResult.success(noticeService.editNotice(token, id, text, img));
    }

    /**
     * 删除某条公告信息
     * @return true/false
     */
    @Token
    @DeleteMapping("deleteNotice")
    public JsonResult<Boolean> deleteNotice(@RequestParam Integer noticeId) {
      //TODO 这里要做权限校验，看看这个人有没有权限删除
        return JsonResult.success();
    }
  
    /**
     * 创建一条公告
     * @return 公告
     */
    @Token
    @PostMapping("createNotice")
    public JsonResult<Notice> createNotice(@RequestBody Notice notice) {
      //TODO 这里要做权限校验，看看这个人有没有权限创建
        return JsonResult.success();
    }
  
    /**
     * 心跳接口
     * @param bot bot信息，包含了一个botid和其下账号的在线信息
     * @return true/false
     */
    @PostMapping("heartBeats")
    public JsonResult<Boolean> heartBeats(@RequestBody Bot bot) {
      //TODO 心跳接口，要传过来Bot的id，所有账号的在线情况，返回Botid（第一次心跳需要生成id）
        return JsonResult.success();
    }
  
    /**
     * 获取全部Bot的列表
     * @return 全部信息
     */
    @Token
    @PostMapping("getBotList")
    public JsonResult<List<Bot>> getBotList() {
        //TODO 数据库里记录的是每个账号的最后一次心跳时间，超过五分钟没有心跳就算离线
        return JsonResult.success();
    }
  
    /**
     * 获取Bot图表数据
     * @return 在线几个、离线几个、一共几个
     */
    @PostMapping("getBotBoard")
    public JsonResult<BotData> getBotBoard() {
        //TODO 统计一下，返回一个数据量少一点的
        return JsonResult.success();
    }
  
    /**
     * 同步功能调用数据
     * @return true/false
     */
    @PostMapping("pushData")
    public JsonResult<Boolean> pushData(@RequestBody List<Function> list) {
        return JsonResult.success();
    }
  
    /**
     * 获取全部功能的列表
     * @return 每个功能的详细调用次数
     */
    @Token
    @PostMapping("getFuncList")
    public JsonResult<List<Function>> getFuncList() {
        return JsonResult.success();
    }
}

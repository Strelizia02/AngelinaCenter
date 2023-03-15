package top.strelitzia.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.annotation.Token;
import top.strelitzia.dao.VisitMapper;
import top.strelitzia.model.*;
import top.strelitzia.service.DataService;
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
@Api("数据信息查询接口")
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private VisitMapper visitMapper;

    /**
     * 获取某个账号下所有Bot的列表
     * @return 全部信息
     */
    @Token
    @GetMapping("getBotList")
    @ApiOperation("查询某个账号拥有的所有Bot列表，需要token")
    public JsonResult<List<Bot>> getBotList(@RequestHeader(value = "Authorization", required = false) String token) {
        return JsonResult.success(dataService.getBotList(token));
    }

    /**
     * 获取某个账号下所有Bot的列表
     * @return 全部信息
     */
    @GetMapping("getAllBotData")
    @ApiOperation("查询全部运行数据的合集")
    public JsonResult<Bot> getAllBotData() {
        return JsonResult.success(dataService.getAllBotData());
    }

    /**
     * 获取Bot图表数据
     * @return 在线几个、离线几个、一共几个
     */
    @GetMapping("getBotBoard")
    @ApiOperation("Bot图表用接口，只返回在线和离线数量")
    public JsonResult<BotData> getBotBoard() {
        return JsonResult.success(dataService.getBotBoard());
    }

    /**
     * 获取全部功能的列表
     * @return 每个功能的详细调用次数
     */
    @GetMapping("getFuncList")
    @ApiOperation("获取全部功能的调用次数")
    public JsonResult<List<Function>> getFuncList() {
        return JsonResult.success(dataService.getFuncList());
    }

    /**
     * 获取某个人的功能的列表
     * @return 每个功能的详细调用次数
     */
    @Token
    @GetMapping("getSomeOneFuncList")
    @ApiOperation("根据token获取某个人的功能调用次数，需要token")
    public JsonResult<List<Function>> getSomeOneFuncList(@RequestHeader(value = "Authorization", required = false) String token) {
        return JsonResult.success(dataService.getSomeOneFuncList(token));
    }

    /**
     * 根据用户传回来的版本号，推送最新的卡池信息
     */
    @GetMapping("getPoolData")
    @ApiOperation("Bot用机机接口，根据用户传回来的版本号，推送他缺失的卡池信息")
    public JsonResult<List<PoolData>> getPoolData(@RequestParam String botId, @RequestParam Integer version) {
        return JsonResult.success(dataService.getPoolData(botId, version));
    }

    /**
     * 分页查询某一页的卡池数据（一页10条写死）
     */
    @GetMapping("getAllPoolData")
    @ApiOperation("分页查询某一页的卡池数据（一页10条写死）")
    public JsonResult<List<PoolData>> getAllPoolData(@RequestParam Integer current) {
        return JsonResult.success(dataService.getAllPoolData(current), dataService.getPoolCount());
    }

    /**
     * 写入卡池数据
     */
    @Token
    @PostMapping("setPoolData")
    @ApiOperation("写入一批卡池数据，需要token")
    public JsonResult<Boolean> setPoolData(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody List<PoolData> poolData) {
        if (dataService.setPoolData(token, poolData)) {
            return JsonResult.success(true);
        } else {
            return JsonResult.failure();
        }
    }

    /**
     * 删除卡池数据
     */
    @Token
    @GetMapping("removePoolData")
    @ApiOperation("删除一批卡池数据，需要token")
    public JsonResult<Boolean> removePoolData(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody List<PoolData> poolData) {
        if (dataService.removePoolData(token, poolData)) {
            return JsonResult.success(true);
        } else {
            return JsonResult.failure();
        }
    }

    /**
     * 获取全部昵称数据
     */
    @GetMapping("getAllNickName")
    @ApiOperation("查询全部的昵称数据")
    public JsonResult<List<NickName>> getAllNickName() {
        return JsonResult.success(dataService.getAllNickName());
    }

    /**
     * 写入几条新的昵称
     */
    @Token
    @PostMapping("setNickName")
    @ApiOperation("写入一批新的昵称信息，需要token")
    public JsonResult<Boolean> setNickName(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody List<NickName> nickName) {
        if (dataService.setNickName(token, nickName)) {
            return JsonResult.success(true);
        } else {
            return JsonResult.failure();
        }
    }

    /**
     * 删除卡池数据
     */
    @Token
    @GetMapping("removeNickName")
    @ApiOperation("删除一批昵称信息，需要token")
    public JsonResult<Boolean> removeNickName(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody List<NickName> nickName) {
        if (dataService.removeNickName(token, nickName)) {
            return JsonResult.success(true);
        } else {
            return JsonResult.failure();
        }
    }

    /**
     * 根据用户版本号获取新的昵称数据
     */
    @GetMapping("getNickName")
    @ApiOperation("Bot用机机接口，根据用户版本号获取新的昵称数据")
    public JsonResult<List<NickName>> getNickName(@RequestParam String botId, @RequestParam Integer version) {
        return JsonResult.success(dataService.getNickName(botId, version));
    }

    /**
     * 用户访问某个页面时用的数据埋点
     */
    @GetMapping("visitPage")
    @ApiOperation("用户访问某个页面时用的数据埋点")
    public JsonResult<Integer> visitPage(@RequestParam String url) {
        return JsonResult.success(visitMapper.insertVisit(url, System.currentTimeMillis()));
    }

    @GetMapping("getVisitPageDataByTime")
    @ApiOperation("页面访问数据统计，统计最近24小时")
    public JsonResult<List<Integer>> getVisitPageDataByTime() {
        return JsonResult.success(visitMapper.selectVisitByTime());
    }

    @GetMapping("getVisitPageDataByPage")
    @ApiOperation("页面访问数据统计，按页面统计全量")
    public JsonResult<List<Integer>> getVisitPageDataByPage() {
        return JsonResult.success(visitMapper.selectVisitByUrl());
    }
}

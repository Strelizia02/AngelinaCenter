package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.annotation.Token;
import top.strelitzia.model.NewPwd;
import top.strelitzia.model.UserInfo;
import top.strelitzia.service.UserService;
import top.strelitzia.vo.JsonResult;


/**
 * @author strelitzia
 * @Date 2022/12/12
 * 查询用户个人资产接口
 **/
@RequestMapping("user")
@RestController
@Slf4j
@Api("用户信息相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询某人的详细资产数据
     * @return 登录人的资产信息
     */
    @Token
    @GetMapping("getUserProperty")
    @ApiOperation("查询用户的资产数据，包括token余额，调用次数之类的")
    public JsonResult<UserInfo> getUserProperty(@RequestHeader(value = "Authorization", required = false) String token) {
        //详细资产包括：token余额，token已使用额度，调用次数
        return JsonResult.success(userService.getUserProperty(token));
    }

    /**
     * 查询某人的个人信息
     * @return 登录人的个人信息
     */
    @Token
    @GetMapping("getUserInfo")
    @ApiOperation("查询用户的基础信息")
    public JsonResult<UserInfo> getUserInfo(@RequestHeader(value = "Authorization", required = false) String token) {
        return JsonResult.success(userService.getUserInfo(token));
    }
    
    /**
     * 账号修改名字
     * @param name 新的名字
     * @return 登录人的个人信息
     */
    @Token
    @GetMapping("editUserName")
    @ApiOperation("修改用户名")
    public JsonResult<Boolean> editUserName(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam String name) {
        return JsonResult.success(userService.editUserName(token, name));
    }
    
    /**
     * 账号绑定Bot
     * @param qq 待绑定账号
     */
    @Token
    @GetMapping("addUserBot")
    @ApiOperation("绑定一个Bot")
    public JsonResult<Boolean> addUserBot(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam String qq) {
        return JsonResult.success(userService.addUserBot(token, qq));
    }

    /**
     * 账号解绑Bot
     * @param botId 待解绑账号
     */
    @Token
    @GetMapping("removeUserBot")
    @ApiOperation("解绑一个Bot")
    public JsonResult<Boolean> removeUserBot(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam String botId) {
        return JsonResult.success(userService.removeUserBot(token, botId));
    }
    
    /**
     * 账号修改密码
     * @param pwd 密码加密字符串
     */
    @Token
    @PostMapping("editUserPwd")
    @ApiOperation("修改密码")
    public JsonResult<Boolean> editUserPwd(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody NewPwd pwd) {
        return JsonResult.success(userService.editUserPwd(token, pwd));
    }
}

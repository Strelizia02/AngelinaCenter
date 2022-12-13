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
@RequestMapping("user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserPropertyService userPropertyService;

    /**
     * 查询某人的详细资产数据
     * @param id 登录人id
     * @return 登录人的资产信息
     */
    @Token
    @GetMapping("getUserProperty")
    public JsonResult<UserProperty> getUserProperty(@RequestParam String id) {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }

    /**
     * 查询某人的详细数据
     * @param id 登录人id
     * @return 登录人的个人信息
     */
    @Token
    @GetMapping("getUserInfo")
    public JsonResult<UserProperty> getUserInfo(@RequestParam String id) {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
    
    /**
     * 修改某人的详细数据
     * @param userInfo 登录人个人信息
     * @return 登录人的个人信息
     */
    @Token
    @PostMapping("editUserInfo")
    public JsonResult<UserProperty> editUserInfo(@Requestbody UserInfo userInfo) {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }
}

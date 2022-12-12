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
@RequestMapping("userProperty")
@RestController
@Slf4j
public class UserPropertyController {

    @Autowired
    private UserPropertyService userPropertyService;

    /**
     * 查询某人的详细资产数据
     * @param id 登录人id
     * @return 登录人信息
     */
    @Token
    @PostMapping("getUserProperty")
    public JsonResult<UserProperty> getUserProperty(@RequestParam String id) {
        return JsonResult.success(userPropertyService.getUserProperty(id));
    }

}

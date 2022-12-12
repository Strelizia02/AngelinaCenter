package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.strelitzia.models.LoginInfo;
import top.strelitzia.service.LoginService;
import top.strelitzia.vo.JsonResult;


/**
 * @author strelitzia
 * @Date 2022/12/12
 * Web前端主页登录用接口
 **/
@RequestMapping("login")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 使用密码登录的方法
     * @param loginInfo 登录信息
     * @return 登录人信息
     */
    @PostMapping("pwd")
    public JsonResult<LoginInfo> pwdLogin(@RequestBody LoginInfo loginInfo) {
        return JsonResult.success(loginService.pwdLogin(loginInfo));
    }

    /**
     * 使用验证码登录的方法
     * @param loginInfo 登录信息
     * @return 登录人信息
     */
    @PostMapping("captcha")
    public JsonResult<LoginInfo> captchaLogin(@RequestBody LoginInfo loginInfo) {
        return JsonResult.success(loginService.captchaLogin(loginInfo));
    }

    /**
     * 创建一个随机验证码
     * @param loginInfo 登录信息
     * @return 验证码字符串
     */
    @PostMapping("creatCaptcha")
    public JsonResult<String> creatCaptcha(@RequestBody LoginInfo loginInfo) {
        return JsonResult.success(loginService.creatCaptcha(loginInfo));
    }

    /**
     * 注册信息
     * @param loginInfo 注册信息
     * @return 注册成功的个人信息
     */
    @PostMapping("creatCaptcha")
    public JsonResult<LoginInfo> register(@RequestBody LoginInfo loginInfo) {
        return JsonResult.success(loginService.register(loginInfo));
    }
}

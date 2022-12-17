package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.strelitzia.model.CaptchaReceive;
import top.strelitzia.model.LoginInfo;
import top.strelitzia.model.UserInfo;
import top.strelitzia.service.LoginService;
import top.strelitzia.util.RSAUtil;
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
    private RSAUtil rSAUtil;

    @Autowired
    private LoginService loginService;

    /**
     * 使用密码登录的方法，用账户name和pwd登录
     * @param userInfo 登录信息
     * @return 登录人信息
     */
    @PostMapping("pwd")
    public JsonResult<LoginInfo> pwdLogin(@RequestBody UserInfo userInfo) {
        return JsonResult.success(loginService.pwdLogin(userInfo));
    }

    /**
     * 检查验证码是否通过，用Bot的qq号和验证码登录
     * @param qq 待验证qq
     * @return 登录人信息
     */
    @GetMapping("captcha")
    public JsonResult<LoginInfo> captchaLogin(@RequestParam String qq) {
        return JsonResult.success(loginService.captchaLogin(qq));
    }

    /**
     * 创建一个随机验证码
     * @param qq 登录信息
     * @return 验证码字符串
     */
    @GetMapping("creatCaptcha")
    public JsonResult<String> creatCaptcha(@RequestParam String qq) {
        return JsonResult.success(loginService.creatCaptcha(qq));
    }
    
    /**
     * 接受Bot验证
     * @param captchaReceive 登录信息
     * @return 验证码字符串
     */
    @GetMapping("reciveCaptcha")
    public JsonResult<Boolean> reciveCaptcha(@RequestBody CaptchaReceive captchaReceive) {
        return JsonResult.success(loginService.receivedCaptcha(captchaReceive));
    }

    /**
     * 注册信息
     * @param userInfo 注册信息
     * @return 注册成功的个人信息
     */
    @PostMapping("register")
    public JsonResult<LoginInfo> register(@RequestBody UserInfo userInfo) {
        return JsonResult.success(loginService.register(userInfo));
    }
    
    /**
     * 获取一个RSA公钥
     * @return 公钥字符串，公钥编号
     */
    @GetMapping("getRSAPublicKey")
    public JsonResult<String> getRSAPublicKey() {
        return JsonResult.success(rSAUtil.getPublicKey());
    }
}

package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.strelitzia.annotation.Token;
import top.strelitzia.models.UserProperty;
import top.strelitzia.service.UserPropertyService;
import top.strelitzia.vo.JsonResult;


/**
 * @author strelitzia
 * @Date 2022/12/12
 * 下载文件专用接口
 **/
@RequestMapping("file")
@RestController
@Slf4j
public class FileController {

    @Autowired
    private UserPropertyService userPropertyService;

    @Token
    @PostMapping("download")
    public JsonResult<UserProperty> downloadFile(@RequestParam String fileId) {
        return JsonResult.success(userPropertyService.getUserProperty(fileId));
    }

}

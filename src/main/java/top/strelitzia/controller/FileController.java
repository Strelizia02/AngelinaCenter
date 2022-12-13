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
        //TODO 首先要有定时任务，检查git上数据版本，然后使用JGit拉取数据。
        
        //TODO 分文件记录每个人的下载次数（文件名、user、下载次数），每次数据更新后，把下载次数清空
        
        //TODO 搞到本地文件里就行了，省钱
        return JsonResult.success(userPropertyService.getUserProperty(fileId));
    }

}

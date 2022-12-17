package top.strelitzia.controller;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.strelitzia.annotation.Token;
import top.strelitzia.model.*;
import top.strelitzia.service.NoticeService;
import top.strelitzia.service.UserService;
import top.strelitzia.vo.JsonResult;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * @author strelitzia
 * @Date 2022/12/12
 * 公告操作接口
 **/
@RequestMapping("notice")
@RestController
@Slf4j
public class NoticeController {
    
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
     * 获取公告的图片
     */
    @GetMapping(value = "getNoticeImg", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getNoticeImg(@RequestParam Integer id) {
        return noticeService.getNoticeImg(id);
    }


        /**
         * 编辑某条公告信息
         * @return 公告信息
         */
    @Token
    @PostMapping("editNotice")
    public JsonResult<Info> editNotice(@RequestHeader(value = "Authorization", required = false) String token, @ApiParam(value = "img") @RequestParam(required = false) MultipartFile img, @ApiParam(value = "id") @RequestParam Integer id, @ApiParam(value = "text") @RequestParam String text) {
        return JsonResult.success(noticeService.editNotice(token, id, text, img));
    }

    /**
     * 删除某条公告信息
     * @return true/false
     */
    @Token
    @DeleteMapping("deleteNotice")
    public JsonResult<Info> deleteNotice(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam Integer noticeId) {
      //TODO 这里要做权限校验，看看这个人有没有权限删除
        return JsonResult.success(noticeService.deleteNotice(token, noticeId));
    }
  
    /**
     * 创建一条公告
     * @return 公告
     */
    @Token
    @PostMapping("createNotice")
    public JsonResult<Info> createNotice(@RequestHeader(value = "Authorization", required = false) String token, @ApiParam(value = "img") @RequestParam MultipartFile img, @ApiParam(value = "text") @RequestParam String text) {
        return JsonResult.success(noticeService.createNotice(token, text, img));
    }
  }

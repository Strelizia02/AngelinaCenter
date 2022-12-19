package top.strelitzia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.strelitzia.annotation.Token;
import top.strelitzia.model.UserInfo;
import top.strelitzia.service.UserService;
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
    private UserService userPropertyService;
  
    private final String[] fileNameList = new String[]{"character_table.json", "gacha_table.json", "skill_table.json", "building_data.json", "handbook_info_table.json", "charword_table.json",
                                                  "char_patch_table.json", "item_table.json", "skin_table.json", "battle_equip_table.json", "uniequip_table.json", "enemy_database.json", "data_version.txt"};

    private final String[] pathList = new String[]{"gamedata/excel/character_table.json", "gamedata/excel/gacha_table.json", "gamedata/excel/skill_table.json", "gamedata/excel/building_data.json", "gamedata/excel/handbook_info_table.json", "gamedata/excel/charword_table.json",
                                                  "gamedata/excel/char_patch_table.json", "gamedata/excel/item_table.json", "gamedata/excel/skin_table.json", "gamedata/excel/battle_equip_table.json", "gamedata/excel/uniequip_table.json", "gamedata/levels/enemydata/enemy_database.json", "gamedata/excel/data_version.txt"};


   @PostMapping("download")
   public ResponseEntity<FileSystemResource> downloadFile(@RequestParam String fileName, @RequestParam String botId) {
        List<String> botIds = botMapper.selectAllBotId();
    if (botIds.contains(botId) && fileNameList.contains(fileName)) {
        File file = new File(fileNameList.get(fileName));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
 
    return ResponseEntity
            .ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(new FileSystemResource(file));
   }


}

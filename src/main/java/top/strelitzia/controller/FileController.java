package top.strelitzia.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.strelitzia.dao.BotMapper;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private BotMapper botMapper;

    private final Map<String, String> fileMap = new HashMap<String, String>() {{
        put("character_table.json", "runFile/git/gamedata/excel/character_table.json");
        put("gacha_table.json", "runFile/git/gamedata/excel/gacha_table.json");
        put("skill_table.json", "runFile/git/gamedata/excel/skill_table.json");
        put("building_data.json", "runFile/git/gamedata/excel/building_data.json");
        put("handbook_info_table.json", "runFile/git/gamedata/excel/handbook_info_table.json");
        put("charword_table.json", "runFile/git/gamedata/excel/charword_table.json");
        put("char_patch_table.json", "runFile/git/gamedata/excel/char_patch_table.json");
        put("item_table.json", "runFile/git/gamedata/excel/item_table.json");
        put("skin_table.json", "runFile/git/gamedata/excel/skin_table.json");
        put("battle_equip_table.json", "runFile/git/gamedata/excel/battle_equip_table.json");
        put("uniequip_table.json", "runFile/git/gamedata/excel/uniequip_table.json");
        put("enemy_database.json", "runFile/git/gamedata/levels/enemydata/enemy_database.json");
        put("data_version.txt", "runFile/git/gamedata/excel/data_version.txt");
    }};

    @PostMapping("download")
    @ApiOperation("对外提供下载文件接口")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam String fileName, @RequestParam String botId) {
        List<String> botIds = botMapper.selectAllBotId();
        if (botIds.contains(botId) && fileMap.containsKey(fileName)) {
            File file = new File(fileMap.get(fileName));
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
        } else {
            return null;
        }
    }
}

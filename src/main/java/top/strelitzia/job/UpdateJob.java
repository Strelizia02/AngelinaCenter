package top.strelitzia.job;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author strelitzia
 * @Date 2022/12/19 15:53
 **/

@Component
@Slf4j
public class UpdateJob {

    @Value("${git-path}")
    private String path;
    private static int updateStatus = 0;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;
  
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    @Async
    public void updateGameDataJob() throws GitAPIException, IOException {
        String versionUrl = "https://raw.fastgit.org/yuanyan3060/Arknights-Bot-Resource/master/gamedata/excel/data_version.txt";
        String version = getStringFromUrl(versionUrl);
        String localVersion = getStringFromFile("runFile/git/gamedata/excel/data_version.txt");

        if (updateStatus == 0 && !version.equals(localVersion)) {
            log.info("正在尝试git pull");
            updateStatus = 1;
            Runtime.getRuntime().exec("cd " + path);
            Runtime.getRuntime().exec("git pull");
            rabbitTemplate.convertAndSend("DataVersion","", 1);
            updateStatus = 0;
            log.info("git成功");
        } else {
            log.warn("当前正在git操作中，请稍后再试");
        }
    }
  
    /**
     * 读取文件的内容字符串
     * @param fileName url
     * @return 返回结果String
     */
    public String getStringFromFile(String fileName) {
        File file = new File(fileName);
        StringBuilder laststr = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            // System.out.println("以行为单位读取文件内容，一次读一整行：");
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                laststr.append(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return laststr.toString();
    }

    /**
     * 发送url的get请求获取结果json字符串
     * @param url url
     * @return 返回结果String
     */
    public String getStringFromUrl(String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("User-Agent", "PostmanRuntime/7.26.8");
        httpHeaders.set("Authorization", "2");
        httpHeaders.set("Host", "andata.somedata.top");
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        String s = null;
        try {
            s = restTemplate
                    .exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        } catch (Exception ignored) {

        }
        return s;
    }
}

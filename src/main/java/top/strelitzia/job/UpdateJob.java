package top.strelitzia.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author strelitzia
 * @Date 2022/12/19 15:53
 **/

@Component
@Slf4j
public class UpdateJob {
  
    private final String url = "";

    private static int updateStatus = 0;
  
    @Scheduled(cron = "15 */3 * * * ?")
    @Async
    public void updateGameDataJob() {
        String versionUrl = "https://raw.fastgit.org/yuanyan3060/Arknights-Bot-Resource/master/gamedata/excel/data_version.txt";
        String version = getJsonStringFromUrl(versionUrl);

        File dataVersionFile = new File("runFile/download/data_version.txt");

        if (updateStatus == 0 && version.equals()) {
            updateStatus == 1;\
            Repository repo = new FileRepositoryBuilder()
              .setGitDir(Paths.get(dir, ".git").toFile())
              .build();
            Git git = new Git(repo);
            CredentialsProvider provider = new UsernamePasswordCredentialsProvider(name, pwd);
            git.pull()
              .setRemoteBranchName("master")
              .setCredentialsProvider(provider)
              .call();
                  rabbitTemplate.convertAndSend("DataVersion","", 1);
        } else {
            log.warning("当前正在git操作中，请稍后再试");
        }
    }
  
    /**
     * 读取文件的内容字符串
     * @param fileName url
     * @return 返回结果String
     */
    public String getJsonStringFromFile(String fileName) {
        File file = new File("runFile/download/" + fileName);
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
}

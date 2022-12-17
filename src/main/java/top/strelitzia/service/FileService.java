package top.strelitzia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileService {

    public String saveFile(MultipartFile img) {
        String path = "runFile/cache/" + UUID.randomUUID();
        try {
            File file = new File(path);
            String absolutePath = file.getCanonicalPath();

            /*判断路径中的文件夹是否存在，如果不存在，先创建文件夹*/
            String dirPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            InputStream ins = img.getInputStream();

            inputStreamToFile(ins, file);
            ins.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = Files.newOutputStream(file.toPath());
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

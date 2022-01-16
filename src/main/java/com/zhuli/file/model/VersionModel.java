package com.zhuli.file.model;

import com.zhuli.file.request.FileController;
import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @Description 加载配置文件
 * @Author zhuli
 * @Date 2021/6/7/3:55 PM
 */
@Data
public class VersionModel {

    private String version;
    private int type;
    private String url;
    private String content;

    public VersionModel() {
        //加载项目同级目录下的配置文件
        if (FileController.filePath == null) return;
        Properties prop = new Properties();
        try {
            String path = FileController.filePath + "version.properties";
            System.err.println(path);
            InputStreamReader in = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            prop.load(in);
            version = prop.getProperty("app.version");
            type = Integer.parseInt(prop.getProperty("app.type"));
            url = prop.getProperty("app.url");
            content = prop.getProperty("app.content");

        } catch (IOException e) {
            System.err.println("加载项目同级目录下的version.properties文件失败，请检查是否存在文件，文件内容为：\n{" +
                    "# 版本号\n" +
                    "app.version=1.0.0\n" +
                    "# 类型\n" +
                    "app.type=3\n" +
                    "# 下载地址\n" +
                    "app.url=http:127.0.0.1:8088/api/file/download\n" +
                    "# 更新说明\n" +
                    "app.content=apk版本更新}");
            e.printStackTrace();
        }
    }

}

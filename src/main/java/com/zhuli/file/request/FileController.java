package com.zhuli.file.request;

import com.zhuli.file.result.RequestResult;
import com.zhuli.file.result.ResultCode;
import com.zhuli.file.unit.FileType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author zhuli
 * @description 文件上传
 * @date 2021/6/30 23:52
 */
@RequestMapping("api/file")
@Controller
public class FileController {

    //文件路径
//    @Value("${filepath}")
    public static String filePath;

    public FileController(){
        filePath = getPath();
    }

    @RequestMapping("/fileUpload")
    public String getFilePath() {
        return "uploadFile";
    }

    @RequestMapping("/fileDownload")
    public String FileDownload() {
        return "downloadFile";
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return
     */
    @PostMapping(value = "/upload")
    public @ResponseBody
    RequestResult upload(
            @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            System.out.println("文件为空");
            return RequestResult.error(ResultCode.PARAM_IS_INVALID);
        }
        // 获取原始文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 新文件名
        if(suffixName.contains(".png") || suffixName.contains(".jpg")){
            fileName = UUID.randomUUID() + suffixName;

        }else {
            fileName = suffixName;
        }

        // 创建新文件
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            String urlPath = request.getRequestURL().toString().replace("api/file/upload", "files/");
            return RequestResult.success(urlPath + fileName);

        } catch (IOException e) {
            System.out.println("文件上传失败" + e.getMessage());
            return RequestResult.error(402, e.getMessage());

        }

    }


    /**
     * 文件下载
     *
     * @param filename 文件名
     * @return 正确返回类型FileSystemResource
     */
    @PostMapping(value = "/download")
    public @ResponseBody
    ResponseEntity download(
            @RequestParam(value = "filename") String filename) {

        if (filename == null || filename.isEmpty()) {
            System.out.println("文件为空");
            return null;
        }

        File file = Paths.get(filePath).resolve(filename).toFile();
        System.out.println("文件请求路径" + file.getPath());

        if (file.exists() && file.canRead()) {
            System.out.println("文件请求成功");
            return ResponseEntity.ok()
                    .contentType(FileType.getFileType(filename))
                    .body(new FileSystemResource(file));
        }
        System.out.println("文件不存在");
        return ResponseEntity.badRequest().body(RequestResult.error(ResultCode.PARAM_IS_INVALID));
    }


    private String getPath() {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (System.getProperty("os.name").contains("dows")) {
            path = path.substring(1);
        }
        if (path.contains("jar")) {
            path = path.substring(0, path.lastIndexOf("."));
            return path.substring(0, path.lastIndexOf("/"));
        }
        return path.replace("target/classes/", "");
    }

}

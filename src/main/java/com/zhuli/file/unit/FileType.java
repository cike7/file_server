package com.zhuli.file.unit;

import org.springframework.http.MediaType;

public class FileType {

    public static MediaType getFileType(String filename){

        String names = filename.substring(filename.lastIndexOf("."));

        System.out.println("文件类型" + names);

        switch (names){
            case ".png":
                return MediaType.IMAGE_PNG;
            case ".jpg":
                return MediaType.IMAGE_JPEG;
            case ".pdf":
                return MediaType.APPLICATION_PDF;
            case ".txt":
                return MediaType.TEXT_HTML;
            case ".xml":
                return MediaType.TEXT_XML;
            case ".dex":
            default:
                return MediaType.MULTIPART_FORM_DATA;
        }

    }

}

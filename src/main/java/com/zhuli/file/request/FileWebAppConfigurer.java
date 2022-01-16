package com.zhuli.file.request;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhuli
 * @description 资源映射路径
 * @date 2021/7/1 0:05
 */
@Configuration
public class FileWebAppConfigurer implements WebMvcConfigurer {

    /**
     * 添加资源处理程序
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将Url路径映射到物理路径下
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + FileController.filePath);
    }

}

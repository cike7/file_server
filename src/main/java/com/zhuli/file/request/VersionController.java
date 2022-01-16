package com.zhuli.file.request;

import com.zhuli.file.model.VersionModel;
import com.zhuli.file.result.RequestResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author zhuli
 * @description 版本检测
 * @date 2021/7/1 0:05
 */
@RequestMapping("api/app/")
@Controller
public class VersionController {

    private VersionModel data;

    /**
     * 版本检测
     *
     * @return
     */
    @GetMapping(value = "version")
    public @ResponseBody
    RequestResult version(HttpServletRequest request) {
        if (data == null) {
            data = new VersionModel();
        }
        return RequestResult.success(data);
    }


}

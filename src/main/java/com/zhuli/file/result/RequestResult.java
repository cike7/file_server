package com.zhuli.file.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zhuli
 * @description 请求返回结果
 * @date 2021/6/30 14:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestResult<T> {

    /**
     * code 状态值：代表本次请求response的状态结果。
     */
    private Integer code;

    /**
     * response描述：对本次状态码的描述。
     */
    private String message;

    /**
     * data数据：本次返回的数据。
     */

    private T data;

    /**
     * 成功，创建ResResult：没data数据
     */
    public static RequestResult success() {
        RequestResult result = new RequestResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public static RequestResult success(Object data) {
        RequestResult result = new RequestResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }


    /**
     * 失败，指定status、desc
     */
    public static RequestResult error(Integer status, String message) {
        RequestResult result = new RequestResult();
        result.setCode(status);
        result.setMessage(message);
        return result;
    }


    /**
     * 失败，指定ResultCode枚举
     */
    public static RequestResult error(ResultCode resultCode) {
        RequestResult result = new RequestResult();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 把ResultCode枚举转换为ResResult
     */
    private void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    private void setCode(Integer code) {
        this.code = code;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setData(T data) {
        this.data = data;
    }

}
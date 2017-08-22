package com.bjsxt.utils;

import com.bjsxt.study.model.RespCode;
import com.bjsxt.study.model.RespMsg;

/**
 * @author: 欣元
 * @date: 2017/8/20 上午11:18
 */
public class RespUtil {

    public static RespMsg buildResp(RespCode respCode, String info) {
        RespMsg respMsg = new RespMsg();
        respMsg.setCode(respCode);
        respMsg.setInfo(info);
        return respMsg;
    }

    public static RespMsg success(String info) {
        return buildResp(RespCode.Success, info);
    }

    public static RespMsg failed(String info) {
        return buildResp(RespCode.Failed, info);
    }

    public static RespMsg error(String info) {
        return buildResp(RespCode.Error, info);
    }
}

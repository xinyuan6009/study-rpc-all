package com.bjsxt.study.impl;

import com.bjsxt.study.HelloRPCService;
import com.bjsxt.study.exceptions.HRException;
import com.bjsxt.study.model.RespMsg;
import com.bjsxt.study.model.User;
import com.bjsxt.utils.RespUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 欣元
 * @date: 2017/8/20 上午11:10
 */
public class HelloRpcServiceImpl implements HelloRPCService.Iface {

    /**
     * LOG
     */
    private Logger logger = LoggerFactory.getLogger(HelloRpcServiceImpl.class);

    /**
     *
     * @param user
     * @return
     * @throws HRException
     * @throws TException
     */
    public RespMsg sayHello(User user) throws HRException, TException {
        logger.info("A visiter is visited userName:{} userAge:{}"
                ,new Object[]{user.getName(),user.getAge()});
        return RespUtil.success("a msg recevied...");
    }

    public void ping() throws TException {
        //Todo
    }
}

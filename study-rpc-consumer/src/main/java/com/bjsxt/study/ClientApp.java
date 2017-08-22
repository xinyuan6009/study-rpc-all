package com.bjsxt.study;

import com.bjsxt.study.model.RespMsg;
import com.bjsxt.study.model.User;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 欣元
 * @date: 2017/8/20 上午11:44
 */
public class ClientApp {


    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 7009;
    private static final int TIMEOUT = 500;

    /**
     * LOG
     */
    private Logger logger = LoggerFactory.getLogger(ClientApp.class);

    public void sayHello() {
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
//            TProtocol protocol = new TCompactProtocol(transport);
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            HelloRPCService.Client client = new HelloRPCService.Client(
                    protocol);
            User user = new User();
            user.setName("Xinyuan");
            user.setAge(11);
            //需要打开
            transport.open();
            RespMsg respMsg = client.sayHello(user);
            logger.info("a msg received {}", respMsg);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }


    public HelloRPCService.Client getServerClient() {
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
//            TProtocol protocol = new TCompactProtocol(transport);
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            HelloRPCService.Client client = new HelloRPCService.Client(
                    protocol);
            transport.close();
            return client;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        new ClientApp().sayHello();
    }
}

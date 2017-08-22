package com.bjsxt.study;

import com.bjsxt.study.impl.HelloRpcServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: 欣元
 * @date: 2017/8/20 上午11:24
 */
public class ServerBootstrap {

    /**
     * 服务端口
     */
    private  int serv_port ;

    /**
     * LOG
     */
    private Logger logger = LoggerFactory.getLogger(ServerBootstrap.class);

    private HelloRPCService.Iface helloRpcServiceImpl;
    /**
     *
     */
    public void startServer() {
        try {
            logger.info("server is starting...");
            //构建一个processor
            TProcessor tProcessor = new HelloRPCService.Processor<HelloRPCService.Iface>(
                    helloRpcServiceImpl);
            //构建 transport
            //TNonblockingServerSocket transport = new TNonblockingServerSocket(SERVER_PORT);
            //BIO
            TServerSocket serverTransport = new TServerSocket(serv_port);
            //通过协议工程构建 TProtocolFactory
            //new TBinaryProtocol.Factory()
            //new TFramedTransport.Factory()
            TProtocolFactory protocol = new TBinaryProtocol.Factory();
            //Simple
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(protocol);
            //构建Server
            TServer server = new TSimpleServer(tArgs);
            //开启
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
            logger.error("server Error e:{}", e);
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-provider.xml");
        ServerBootstrap app = (ServerBootstrap) context.getBean("app");
        app.startServer();
    }

    public int getServ_port() {
        return serv_port;
    }

    public void setServ_port(int serv_port) {
        this.serv_port = serv_port;
    }

    public HelloRPCService.Iface getHelloRpcServiceImpl() {
        return helloRpcServiceImpl;
    }

    public void setHelloRpcServiceImpl(HelloRPCService.Iface helloRpcServiceImpl) {
        this.helloRpcServiceImpl = helloRpcServiceImpl;
    }
}

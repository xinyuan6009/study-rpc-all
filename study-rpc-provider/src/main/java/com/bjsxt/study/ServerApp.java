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

/**
 * @author: 欣元
 * @date: 2017/8/20 上午11:24
 */
public class ServerApp {

    private static final int SERVER_PORT = 7001;

    /**
     * LOG
     */
    private Logger logger = LoggerFactory.getLogger(ServerApp.class);

    /**
     *
     */
    public void startServer() {
        try {
            logger.info("server is starting...");
            //构建一个processor
            TProcessor tProcessor = new HelloRPCService.Processor<HelloRPCService.Iface>(
                    new HelloRpcServiceImpl());
            //构建 transport
            //TNonblockingServerSocket transport = new TNonblockingServerSocket(SERVER_PORT);
            //BIO
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
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
        new ServerApp().startServer();
    }
}

## Thrift入门开发

### 开发步骤
1.编写IDL文件
1. 服务端编码基本步骤：
实现服务处理接口impl
创建TProcessor

```java
TProcessor tprocessor = new RpcService.Processor<RpcService.Iface>(
                    new RpcServiceImpl());
```
创建TServerTransport

```java
//TNonblockingServerSocket transport = new TNonblockingServerSocket(SERVER_PORT);
//BIO
TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
```
创建TProtocol

```java
//通过协议工程构建 TProtocolFactory
//new TBinaryProtocol.Factory()
//new TFramedTransport.Factory()
TProtocolFactory protocol = new TCompactProtocol.Factory();
```
创建TServer

```java
//1.服务绑定
//多线程BIO
//TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
                    serverTransport);
//Simple
TServer.Args tArgs = new TServer.Args(serverTransport);
tArgs.processor(tprocessor);
tArgs.protocolFactory(protocol);
//构造Server
//TServer server = new TThreadPoolServer(ttpsArgs);
//TServer server = new TThreadedSelectorServer(args);
TServer server = new TSimpleServer(tArgs);
```
启动Server

```java
server.serve();
```

2.客户端编码基本步骤：
创建Transport
创建TProtocol
基于TTransport和TProtocol创建 Client
调用Client的相应方法

```java
    /**
     */
    public void startClient(User user) {
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
//            TProtocol protocol = new TCompactProtocol(transport);
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            RpcService.Client client = new RpcService.Client(
                    protocol);
            transport.open();
            client.sayHello(user);
            System.out.println("Thrift client result =: ");
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
```

3.数据传输协议
TBinaryProtocol : 二进制格式.
TCompactProtocol : 压缩格式
TJSONProtocol : JSON格式
TSimpleJSONProtocol : 提供JSON只写协议, 生成的文件很容易通过脚本语言解析

### 环境信息
管理工具：maven
版本控制：git
thrift版本: 0.10.0
java版本：1.8/8 

### 项目结构：
主项目：study-rpc-all 控制项目版本，属性等信息
服务interface：study-rpc-interface Thrift生成代码，接口定义等文件管理
服务provider：study-rpc-provider   Thrift服务端代码实现
服务consumer：study-rpc-consumer   Thrift消费端代码实现

.ignore

```shell
# IDEA
.idea/
*.iml
out/
gen/
idea-gitignore.jar
resources/templates.list
resources/gitignore/*

#Build
build/
build.properties
junit*.properties
IgnoreLexer.java~
.gradle

# Package Files #
*.jar
*.war
*.ear
*.zip
*.tar.gz
*.rar
```

### 定义.thrift文件

```c

/**
* RPC
* 1. 定义一个测试方法 RepsMsg sayHello(User user)
* 2. 定义一个心跳检查方法 void ping()
*/
include "Persion.thrift"

//类似于JAVA的包
namespace java com.xinyuan.study

/**
*   异常
*/
exception HRException {
  1: i32 code,
  2: string msg
}


struct RepsMsg{
    1: required i32 code;
    2: required string info;
}

/**
*   HelloMsg sayHello(Persion.Persion persion) throws (1: HRException hrexception)
*   void ping()
*/
service HelloRpcServiec{
    RepsMsg sayHello(1: Persion.Persion persion);
    void ping();
}
```
执行编译命令

```shell
    thrift -out ../java -gen java HelloRpc.thrift 
```

### Thrift与Spring 整合
导入依赖
spring-core spring-content

log依赖
slf4j-api
log-classic

### 上传git:  http://blog.csdn.net/laozitianxia/article/details/50682100

参考文章：[入门](https://segmentfault.com/a/1190000008606491)


# Thrift基础
Thrift 是一个跨语言的序列化/RPC框架，它含有三个主要的组件：protocol，transport和server，其中，protocol定义了消息是怎样序列化的，transport定义了消息是怎样在客户端和服务器端之间通信的，server用于从transport接收序列化的消息，根据protocol反序列化之，调用用户定义的消息处理器，并序列化消息处理器的响应，然后再将它们写回transport。

### 服务类型
· TSimpleServer
> TSimplerServer接受一个连接，处理连接请求，直到客户端关闭了连接，它才回去接受一个新的连接。正因为它只在一个单独的线程中以阻塞I/O的方式完成这些工作，所以它只能服务一个客户端连接，其他所有客户端在被服务器端接受之前都只能等待。TSimpleServer主要用于测试目的，不要在生产环境中使用它！

· TNonblockingServer vs. THsHaServer
> TNonblockingServer使用非阻塞的I/O解决了TSimpleServer一个客户端阻塞其他所有客户端的问题。它使用了java.nio.channels.Selector，通过调用select()，它使得你阻塞在多个连接上，而不是阻塞在单一的连接上。当一或多个连接准备好被接受/读/写时，select()调用便会返回。TNonblockingServer处理这些连接的时候，要么接受它，要么从它那读数据，要么把数据写到它那里，然后再次调用select()来等待下一个可用的连接。通用这种方式，server可同时服务多个客户端，而不会出现一个客户端把其他客户端全部“饿死”的情况。

· THsHaServer
> THsHaServer（半同步/半异步的server）,它使用一个单独的线程来处理网络I/O，一个独立的worker线程池来处理消息。这样，只要有空闲的worker线程，消息就会被立即处理，因此多条消息能被并行处理。用上面的例子来说，现在的latency就是100毫秒，而吞吐量就是100个请求/秒。

· TThreadedSelectorServer
> Thrift 0.8引入了另一种server实现，即TThreadedSelectorServer。它与THsHaServer的主要区别在于，TThreadedSelectorServer允许你用多个线程来处理网络I/O。它维护了两个线程池，一个用来处理网络I/O，另一个用来进行请求的处理。当网络I/O是瓶颈的时候，TThreadedSelectorServer比THsHaServer的表现要好。为了展现它们的区别，我进行了一个测试，令其消息处理器在不做任何工作的情况下立即返回，以衡量在不同客户端数量的情况下的平均latency和吞吐量。对THsHaServer，我使用32个worker线程；对TThreadedSelectorServer，我使用16个worker线程和16个selector线程。

· TThreadPoolServer
> 与单线程对照，线程池模型是一个请求建立一个线程，优点是可以较少阻塞，缺点是消耗系统资源
 选择：如果并发量不是特别大，而且并发数总体可控，选择TThreadPoolServer比较理想
如相册服务，并发量相对较小，单次链接比较长
如果并发很大，单次链接时间较短，可以选择TNonblockingServer
剩下几种，可以根据具体生产环境中遇到的瓶颈，进行类型更换

### Transport
为了使用上的方便, Thrift 提供了如下几个常用 Transport:

1. TSocket: 这个 transport 使用阻塞 socket 来收发数据.
2. TFramedTransport: 以帧的形式发送数据, 每帧前面是一个长度. 当服务方使用 non-blocking IO 时(即服务器端使用的是 TNonblockingServerSocket), 那么就必须使用 TFramedTransport.
3. TMemoryTransport: 使用内存 I/O. Java 实现中在内部使用了 ByteArrayOutputStream
4. TZlibTransport: 使用 Zlib 压缩传输的数据. 在 Java 中未实现.

### protocal
1. TBinaryProtocol, 二进制格式
2. TCompactProtocol, 压缩格式
3. TJSONProtocol, JSON 格式
4. TSimpleJSONProtocol, 提供 JSON 只写协议, 生成的文件很容易通过脚本语言解析.
5. TDebugProtocoal, 使用人类可读的 Text 格式, 帮助调试

**注意, 客户端和服务器的协议要一样.**

### 数据类型
namespace java com.bjsxt.study  Java包
1. 基本类型：
bool：布尔值，true 或 false，对应 Java 的 boolean
byte：8 位有符号整数，对应 Java 的 byte
i16：16 位有符号整数，对应 Java 的 short
i32：32 位有符号整数，对应 Java 的 int
i64：64 位有符号整数，对应 Java 的 long
double：64 位浮点数，对应 Java 的 double
string：utf-8编码的字符串，对应 Java 的 String

2. 结构体类型：
struct：定义公共的对象，类似于 C 语言中的结构体定义，在 Java 中是一个 JavaBean
容器类型：
list：对应 Java 的 ArrayList
set：对应 Java 的 HashSet
map：对应 Java 的 HashMap
3. 异常类型：
exception：对应 Java 的 Exception
4. 服务类型：
service：对应服务的类


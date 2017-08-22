/**
*   注释类型
*   1.  JAVA风格注释
*   2. SHELL风格
*/

include "Model.thrift"
include "Exception.thrift"
//namepace 语言 包
namespace java com.bjsxt.study

/**
* 1. consumer -> provider: RespMsg sayHello(User user);
* 2. consumer -> provider: void ping();
*/
service HelloRPCService{
    Model.RespMsg sayHello(1: Model.User user) throws (1: Exception.HRException hrexception);
    void ping();
}



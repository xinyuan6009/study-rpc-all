
namespace java com.bjsxt.study.model

/**
*   结构体属性定义规范
*  1. 属性定义格式  编码: 类型 名称 分号
*  2. required标识符 传输前必须设置值
*/
struct User{
    1: required string name;
    2: required i32 age;
}

/**
*
*/
enum RespCode{
    Success,
    Failed = 400,
    Error = 500
}

struct RespMsg{
    1: required RespCode code;
    2: required string info;
}
#官网步骤
官网started：https://thrift.apache.org/

#Client 和 Server
是一对，写的简单的helloworld

1. src\main\resources\thrift\helloService.thrift 是idl语法写的源文件，质只包含简单的类型。详细见如下：
    > idl语法类型：https://thrift.apache.org/docs/types.html  
      idl语法规则和样例：https://thrift.apache.org/docs/idl
2. HelloService.Iface 用源文件helloService.thrift 通过 thrift生成  
3. HelloServiceImpl 服务端的具体实现
4. 启动Server和Client

---
#JavaClient 和 JavaServer 
是一对，类似计算器，包含复杂idl内容，官网例子。

---
入门例子：https://segmentfault.com/a/1190000008606491  
入门例子：http://www.micmiu.com/soa/rpc/thrift-sample/#respond  
idea配置thrift编译：https://blog.csdn.net/u010900754/article/details/80172671  
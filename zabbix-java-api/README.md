#  JAVA调用Zabbix例子
最近折腾了一下zabbix接口调用，zabbix提供了丰富的操作接口，以实现通过接口添加监控、查询监控详情等功能。    

### 此项目主要演示：  
- 如何调用zabbix接口
- 如何查询信息
- 演示zabbix模糊查询

### 相关文档：
zabbix官方文档  
[https://www.zabbix.com/documentation/3.0/manual/api/reference](#) 
 
zabbix官方链接的Java访问Zabbix API的库  
[http://zabbix.org/wiki/Docs/api/libraries#Java](#)  
[https://github.com/hengyunabc/zabbix-api](#)

***
### maven dependency摘自上面的链接 

```java

<dependency>
    <groupId>io.github.hengyunabc</groupId>
    <artifactId>zabbix-api</artifactId>
    <version>0.0.2</version>  
</dependency>

```
***


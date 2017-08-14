### kbase-codescan 条形码扫描入库应用
	这是一个条形码扫描入库的应用，比如一些超市里纳入库存的商品，这些商品的条形码都是根据样本码通过某些运算规则计算出来的，
	入库的时候需要校验条形码是否符合规范。
	校验规则写在 CodeUtil.java 中
	
### 技术架构
	|-后端（版本详见pom.xml）
	   |-jdk8
	   |-spring-boot
	   |-spring-security
	   |-spring-mongo
	   |-thymeleaf
	|-前端
	   |-art-template-4.12.1
	   |-bootstrap-3.3.7
	   |-bootstrap-datepicker-1.7.1
	   
### 操作说明
* 管理员可录入和查看样本码，普通用户只能扫描条形码和查看已录入的条形码
* 选中待扫描的料号名称，单击【开始扫描】，用扫描枪开始录入条形码，扫描到不符合规范的条形码会报警，所有条形码都会记录

![扫描验证条形码](https://github.com/ekoz/kbase-codescan/blob/master/src/main/resources/static/DATAS/code_index.png?raw=true "扫描验证条形码样例图")

### 待完善的功能点
1. 目前 spring-security 的登录账号和用户名是在文件里配置写死的，需要实现成注册开放式
2. 登录/登出界面，无操作权限界面提示等页面
3. 样本码列表/条形码列表的分页功能
4. 条形码列表的查询和导出功能
5. 历史数据，如果这个应用使用了三年以上，势必会产生很多数据，在校验流水号是否重复的时候会越来越慢，请思考如何处理历史数据的问题

### 注意事项
* POJO 对象都是采用 [lombok](https://projectlombok.org/) 自动生成 
* spring.data.mongodb.uri= 如果配置了host等属性，就不能配置这个属性，要禁掉而不是设置为空，否则会抛出异常
```
Invalid mongo configuration, either uri or host/port/credentials must be specified
```

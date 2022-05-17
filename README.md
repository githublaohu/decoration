## 前言
> decoration主要是解决一些重复性行为与开发过程需要的一些基础能力

1. 统一 spring mvc result
2. 统一 spring mvc 异常
3. 隐式分页功能
4. 防止重复提交
5. 单元测试


## 使用

#### maven依赖
```xml
<dependency>
	<groupId>cn.lampup</groupId>
	<artifactId>decoration-core</artifactId>
	<version>${version}</version>
</dependency>
```

#### spring boot 启动
```yaml

decoration: {class}

```


## 统一 spring mvc result
> 声明式result object处理十分麻烦。因为申明式分页工作感觉有以下问题

1. 不优雅
2. 代码大量重复
3. 有不少开发工作量

#### 方法返回类型与结果决定了行为

| 类型       |  行为 |
| ---------- | ------- |
| void       | 默认请求成功 |
| Integer    | 添加与修改方法，0是失败，大于0是成功 |
| Long       | 返回的结果 |
| Throwable  | 结果异常，返回异常|
| Enum       | 返回枚举，主要是适于复杂业务的方法|
| ResultObject| 直接返回发对象，不做任何处理|
| 其他结果与类型 | 直接当做结果返回 |

#### 行为接口
> 行为接口（com.lamp.decoration.core.result.ResultAction）

## 统一 spring mvc 异常
> 声明式异常处理造成大量的重复代码,而不对异常进行动态处理会造成异常结果不读。因为申明式分页工作感觉有以下问题

1. 不优雅
2. 代码大量重复
3. 有不少开发工作量

#### 对比
|      |   声明式   |   隐士式  |  decoration  |
| --   |   -----   |   -----  |  -----       |
|      |           |          |              |
|      |           |          |              |
|      |           |          |              |
|      |           |          |              |

#### 使用方式



##### ExceptionResult
> 异常返回类，定义异常类型

```java

public class ExceptionResult {

	private String className;
	
	/**
	 * 异常类
	 */
	private Class<?> clazz;
	
	/**
	 * 返回视图类型
	 */
	private ExceptionResultTypeEnum resultType;
	
	/**
	 * 返回code
	 */
	private Integer code;
	
	/**
	 * 消息内容
	 */
	private String message;
}

```

##### decoration默认方式
```java
public class DecorationCustomExceptionResult implements CustomExceptionResult {
	ExceptionResult exceptionResult;

    {
        exceptionResult = new ExceptionResult();
        exceptionResult.setCode(400);
        exceptionResult.setMessage("系统出现异常");
        exceptionResult.setResultType(ExceptionResultTypeEnum.JSON);

    }

    @Override
    public ExceptionResult getDefaultExceptionResult() {
        return exceptionResult;
    }

    @Override
    public List<ExceptionResult> getExceptionResultList() {
        return null;
    }
}

```



##### 自定义
```java
public class DecorationCustomExceptionResult implements CustomExceptionResult {
	ExceptionResult exceptionResult;

    {
        exceptionResult = new ExceptionResult();
        exceptionResult.setCode(400);
        exceptionResult.setMessage("系统出现异常");
        exceptionResult.setResultType(ExceptionResultTypeEnum.JSON);

    }

    @Override
    public ExceptionResult getDefaultExceptionResult() {
        return exceptionResult;
    }

    @Override
    public List<ExceptionResult> getExceptionResultList() {
        return null;
    }
}

``` 


## 隐式分页功能
> 隐士分页工作目前只支持mybatis,基于pagehelper组件实现，主要是解决简单业务的分页查询。因为申明式分页工作感觉有以下问题

1. 不优雅
2. 代码大量重复
3. 有不少开发工作量

#### 使用方法:
> 把分页参数作为请求头queryClause的vlaue．queryClause是一个json。

##### 传输参数:
|    字段名     |   说明   |         |         | 
|--------      |-------- |-------- |-------- |
| limitPageNum | 第几页   |         |         | 
| limitStart   | 第几条   |         |         |
| limitSize    | 每页大小  |         |         |
| orderBy      | 排序字段 |         |         |

##### 目前支持的框架有
1. springmvc
2. dubbo
3. spring cloud openfeign(规划中)

## 防止重复提交
> 同样的数据重复请求某个接口，造成数据问题的。可以使用重复提交

```java
@DuplicateSubmission
public void overbooking(Map<String,String> parameter){
}

```


#### lock对象
1. 基于请求端的网络地址
2. 基于传递参数

#### lock支持方法
1. LocadDuplicateCheck基于ConcurrentHashMap实现。已完成
2. RedisDuplicateCheck基于redis实现。未完成

#### lock方式
1. 时间锁
2. 上锁与解锁

#### DuplicateSubmission参数解释


 * <ol>ValidationUtils </ol>
 

## 单元测试
1. mvc单元测试
2. 持久层单元测试

mvn clean deploy javadoc:javadoc -P release
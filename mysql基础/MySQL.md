尚硅谷

基础查询

```mysql
#指定数据库
USE mhwz;
#1.查询一个字段
#SELECT AdminID FROM admin
#2.查询多个字段
#SELECT AdminID,AdminName FROM admin;
#3.查询所有字段
#SELECT * FROM admin
#手动查询所有字段，可以自定顺序
#SELECT AdminID,AdminPwd,AdminName,AdminType,LastLoginTime FROM admin
#使用``区分关键词和字段名，如果字段名和关键词重复，建议加``
#查询常量值
#SELECT 1;
#SELECT 'mrsoft';
#SELECT 100%99;
#查询函数
#SELECT VERSION(); #查询版本号
#起别名 AS关键字
#方式一
#便于理解
#如果要查询的字段有重名的情况，使用别名进行区分
#SELECT 100%98 AS 取余
#SELECT AdminID AS id值,AdminName AS 用户名 FROM admin;
#方式二 简写方式
#SELECT AdminID id值,AdminName 用户名 FROM admin
#如果有特殊符号，将别名加上双引号
#不去重
#SELECT AdminType FROM admin 
#去重
#SELECT DISTINCT AdminType FROM admin #检测管理员类型有几种
# +号的作用
#例子 ： 查询员工名和姓连接成一个字段，并显示为姓名
#mysql中的加号只有一个作用  运算符
#SELECT 100+90;
#SELECT "123"+90 #字符型会试图转换为数值型，如果转换成功，则做加法运算
#如果转换失败，字符型转换为0
#如果一个为null那么结果也为null
#SELECT NULL+0
#SELECT AdminID+AdminName AS 个人信息 FROM admin
#SELECT CONCAT('a','b','c') AS 结果
#连接符CONCAT
#SELECT CONCAT(AdminID,AdminName) AS 个人信息 FROM admin
#表名字段名和属性
#DESC admin
#判断是否为空  IFNULL(expr1,expr2) exper1为null的字段名，如果为null则取值为expr2
#SELECT IFNULL(AdminName,0) AS 名字,AdminName FROM admin#将为null的替换成0
#SELECT CONCAT(AdminID,",",IFNULL(AdminName,0)) AS 个人信息 FROM admin
```

条件查询

```mysql
/*
条件查询
语法
SELECT 查询列表 FROM 表名 WHERE 筛选条件
分类
一、按条件表达式筛选
条件运算符：>大于  <小于  =等于  != 不等于 <> 不等于  >=大于等于  <=小于等于
二、按逻辑表达式筛选
逻辑运算符： && || !
						and or not 
三、模糊查询 
LIKE 
BETWEEN AND 
in	
is null
*/
#查询价格超过1000的手机
#SELECT title,price FROM tb_sku WHERE price>100000
#查询indexes不为1_0_0的手机
#SELECT title,price,indexes FROM tb_sku WHERE indexes!='1_0_0'
#SELECT title,price,indexes FROM tb_sku WHERE indexes<>'1_0_0'
#按逻辑表达式筛选 作用主要是进行条件表达式的连接，满足多个条件
#筛选即不为1_0_0,并且价格大于1000元
#SELECT title,price,indexes FROM tb_sku WHERE price>100000 AND indexes!='1_0_0'
#筛选tb_sku的价格为1000到1500之间的手机
#SELECT title,price,indexes FROM tb_sku WHERE price>100000 AND price<150000
#查询价格不为1500到100之间 或者 indexes 为1_0_0 的手机
SELECT title,price,indexes FROM tb_sku WHERE NOT(price<=150000 AND price>=100000) or indexes='1_0_0'
#NOT取反
```

条件查询

```mysql
/*
条件查询
语法
SELECT 查询列表 FROM 表名 WHERE 筛选条件
分类
一、按条件表达式筛选
条件运算符：>大于  <小于  =等于  != 不等于 <> 不等于  >=大于等于  <=小于等于
二、按逻辑表达式筛选
逻辑运算符： && || !
						and or not 
三、模糊查询 
LIKE 
BETWEEN AND 
in	
is null
*/
#查询价格超过1000的手机
#SELECT title,price FROM tb_sku WHERE price>100000
#查询indexes不为1_0_0的手机
#SELECT title,price,indexes FROM tb_sku WHERE indexes!='1_0_0'
#SELECT title,price,indexes FROM tb_sku WHERE indexes<>'1_0_0'
#按逻辑表达式筛选 作用主要是进行条件表达式的连接，满足多个条件
#筛选即不为1_0_0,并且价格大于1000元
#SELECT title,price,indexes FROM tb_sku WHERE price>100000 AND indexes!='1_0_0'
#筛选tb_sku的价格为1000到1500之间的手机
#SELECT title,price,indexes FROM tb_sku WHERE price>100000 AND price<150000
#查询价格不为1500到100之间 或者 indexes 为1_0_0 的手机
#SELECT title,price,indexes FROM tb_sku WHERE NOT(price<=150000 AND price>=100000) or indexes='1_0_0'
#NOT取反
#三、模糊查询
#LIKE 
#BETWEEN AND 
#in	
#is null || is not NULL

#1.LIKE

#情况1.LIKE 模糊查询  %通配符  任意多个字符包括0个字符
#SELECT title,price,indexes FROM tb_sku WHERE title LIKE '%华为%'
#情况2：查询手机名第二个字符为'米'的手机
#SELECT title,price,indexes FROM tb_sku WHERE title LIKE "_米%"
#情况3：查询员工名中第三个字符为_的手机名   \转义字符
#SELECT title FROM tb_sku WHERE title LIKE '__\_%';
#或者指定转义字符$
#SELECT title FROM tb_sku WHERE title LIKE '__$_%' ESCAPE '$';

#2.BETWEEN AND 
#情况一：查询价格在800到1000之间的手机 包含临界值 不可颠倒
#SELECT title,price FROM tb_sku WHERE price BETWEEN 80000 AND 100000

#3.IN
#含义：去判断某字段的值满足属于in的某一项
#特点 1、使用In提高语句简洁度
#2.IN 列表的值类型必须一致或兼容'123' 123
#
#案例：查询手机的品牌是华为 小米 魅族的手机信息
#SELECT * FROM tb_brand WHERE `name`="华为（HUAWEI）" OR `name`='小米（MI）' OR `name`='魅族（MEIZU）'
#SELECT * FROM tb_brand WHERE `name` IN("华为（HUAWEI）",'小米（MI）','魅族（MEIZU）')


#4.is NULL
/*
= 和 !=不能判断null值
*/
#mhwz admin 表
#案例1 为null
#SELECT * FROM admin WHERE AdminName IS NULL
#案例2  不为null
#SELECT * FROM admin WHERE AdminName is NOT NULL

#5.安全等于 <=>  能够判断null值
#SELECT * FROM admin WHERE AdminName <=> NULL;
#也可以判断普通值
#SELECT * FROM  admin WHERE AdminType <=> 2;
# is NULL 只能判断null值 <=>能判断null值和普通值

#查询价格小于1500并且indexes为1_0_0的手机
#SELECT title,indexes,price FROM tb_sku WHERE indexes='1_0_0' AND price <= 150000
 
#查询品牌为小米 或者 letter为S的手机
#SELECT * FROM tb_brand WHERE `name`="小米（MI）" OR letter="S"
 
#查询表结构
#DESC tb_sku

#查询表中title涉及的那些title名
#SELECT DISTINCT title FROM tb_sku

#SELECT * FROM tb_sku 和 SELECT * FROM tb_sku WHERE title LIKE "%%"
#结果是否一样？并说明原因
#不一样，出现null的话不一样
```

排序查询

```mysql
/*
排序查询
引入
SELECT * FROM tb_sku
语法
SELECT 查询列表
FROM 表
【WHERE 筛选条件】
ORDER BY 排序列表  【ASC|DESC】
特点
1.ASC代表的是升序，DESC是降序
如果不写，默认是升序
2.ORDER BY 子句中可以支持单个字段，多个字段，表达式，函数，别名
3.ORDER BY 子句执行顺序，一般放在查询语句的最后面，LIMIT子句除外
*/
#情况1：查询手机信息，要求手机价格默认排序从低到高
#SELECT title,price FROM tb_sku ORDER BY price
#情况2：查询手机信息，要求手机从高到低排序
#SELECT title,price FROM tb_sku ORDER BY price DESC
#情况3：查询手机信息，要求要求手机价格超过1000元，按创建时间先后来排序
#SELECT title,price,create_time FROM tb_sku WHERE price>=100000 ORDER BY create_time 
#情况4: 按价格的高低进行手机的排序和信息【使用表达式排序】
#SELECT title,price*0.01 AS 价格 FROM tb_sku 
#情况5:按价格的高低进行手机的排序和信息【使用别名排序】
#SELECT title,price*0.01  AS 价格 FROM tb_sku ORDER BY 价格 DESC
#情况6：按手机名长度排序
#SELECT LENGTH(title) 字节长度,title,price FROM tb_sku	 ORDER BY LENGTH(title) DESC 
#案例6：查询手机信息，要求先价格排序，在按spu_id排序【按多个字段排序】
#SELECT title,price,spu_id  FROM tb_sku ORDER BY price ASC,spu_id DESC

```

常用函数介绍

```mysql
#字符函数
#数学函数
#日期函数
#其他函数[补充]
#流程控制函数[补充]
/*
概念 类似于java的方法，将一组逻辑语句封装在方法体中，对外暴露方法名
好处：1、隐藏了实现细节 2、提高代码的重用性
调用：SELECT 函数名 （实参列表） [FROM 表]
特点
1、叫什么（函数名）
2、干什么 （函数功能）
分类
1、单行函数
如 CONCAT(str1,str2,...) LENGTH(str),IFNULL(expr1,expr2)等
2、分组函数
功能：做统计使用，又称统计函数、聚合函数、组函数
*/

#字符函数
#LENGTH(str) 获取字节个数
#SELECT LENGTH('join')

#UTF-8 一个汉字占三个字节 
#SELECT LENGTH('张三丰')

#显示字符集
#SHOW VARIABLES LIKE '%char%'

#CONCAT(str1,str2,...) 拼接字符串
#SELECT CONCAT(id,"---",title) AS 手机信息 FROM tb_sku 

#3.UPPER(str),LOWER(str)
#SELECT Upper('join')
#SELECT LOWER('JOIN')
#SELECT CONCAT(Lower(title),UPPER(images)) 手机信息 FROM tb_sku

#substr、substring
#注意索引从一开始
#SELECT SUBSTR('李莫愁爱上了陆展元',6) out_put
#截取从指定索引处到指定字符长度的字符
#SELECT SUBSTR('李莫愁爱上了陆展元',1,3) out_put

#title的首字符 和 images 字符大写 用_拼接，显示出来
#SELECT CONCAT(SUBSTR(title,1,2),"__",UPPER(images)) AS out_put FROM tb_sku

#INSERT 返回子串第一次出现的索引，如果找不到返回0
#SELECT INSTR('杨不悔爱上了殷六侠','殷六侠') AS out_put

#trim 去掉前后空格
#SELECT TRIM('    张翠山     ') AS out_put

#去掉前后字符
#SELECT TRIM('a' FROM 'aaaaaa张翠山aaaaaaa') AS out_put

#lpad 左填充 总长度10
#SELECT LPAD("殷素素",10,'*') AS out_put
#lpad 左填充 总长度10
#SELECT LPAD("殷素素",2,'*') AS out_put

#RPAD 用指定的字符实现右填充指定长度
#SELECT RPAD("殷素素",12,'ab') AS out_put

#REPLACE 替换   
#SELECT REPLACE("张无忌爱上了周芷若周芷若周芷若","周芷若","赵敏") AS out_put

#数学函数

#ROUND四舍五入
#SELECT ROUND(-1.45)

#ROUND保留多少位
#SELECT ROUND(1.578,2)

#ceil 向上取整，返回>=该参数的最小证书
#SELECT CEIL(1.00)
#SELECT CEIL(-1.02)

#FLOOR 向下取整  返回>=该参数的最大整数
#SELECT FLOOR(-9.99)

#TRUNCATE 截断
#SELECT TRUNCATE(1.79999,1)

#mod取余  被除数为负 得到的就是负数   被除数为正 得到的就是正数
#MOD(a,b) a-a/b*b
#SELECT MOD(-10,3);
#SELECT MOD(10,3);
#SELECT MOD(-10,-3)

#日期函数
#now 返回当前系统日期+时间
#SELECT NOW()

#curdate 返回当前系统日期，不包含时间
#SELECT CURDATE();

#curtime 返回当前时间，不包含日期
#SELECT CURTIME()

#可以获取指定的部分 年 月  日 小时 分钟 秒
#SELECT YEAR(NOW()) 年
#SELECT YEAR('1998-1-1') 年;
#SELECT YEAR(last_update_time) 年 FROM tb_sku

#SELECT MONTH(last_update_time) 年 FROM tb_sku
#SELECT MONTHNAME(last_update_time) 年 FROM tb_sku

#STR_TO_DATE(str,format) 将日期格式的字符转换为指定格式的日期
#SELECT STR_TO_DATE('9-13-1999',"%m-%d-%Y")

#查询创建时间为2018-04-21时间的手机信息
#SELECT * FROM tb_sku WHERE create_time="2018-04-21 15:55:14"

#DATE_FORMAT(date,format) 将日期转换为字符串
#SELECT DATE_FORMAT(NOW(),"%y年%m月%d日") AS out_put

#查询手机名和创建日期(xx月/xx日 xx年)
#SELECT title,DATE_FORMAT(create_time,"%m月/%d日 %y年") 入职日期 FROM tb_sku

#其他函数
#SELECT VERSION()
#SELECT DATABASE()
#SELECT USER()

#流程控制函数
#1.if函数   类似于if else   IF(expr1,expr2,expr3)
#SELECT IF(10<5,'大','小')
#2.case 函数的使用一  类似于switch case
#mysql中
#case 要判断的字段和表达式
#WHEN 常量1 THEN 要显示的值1或语句1；
#WHEN 常量2 THEN 要显示的值2或者语句2;
#...
#else
#查询手机价格，要求 title里面带华为的价格为1.3倍
#title里面带小米的价格为0.9倍
#title里面APPLE的价格为1.0倍
#title里面三星的价格为1.4倍
#SELECT price AS 原始价格,title,
#CASE title
#	WHEN  title LIKE "%华为%" THEN
#		price*1.3
#	WHEN  title LIKE "%小米%" THEN
#		price*0.9
#	WHEN title LIKE "%APPLE%" THEN
#		price*1.0
#	WHEN title LIKE "%三星%" THEN
#		price*1.4
#	ELSE
#		price
#	END  AS 新价格
#	FROM tb_sku

#情况2：查询手机价格的情况
#如果工资>2000,显示A级别
#如果工资<2000且>1000显示B级别
#如果工资<1000显示C级别
SELECT title,price,
CASE
WHEN price>200000 THEN 'A'
WHEN price<200000 AND price>100000 THEN 'B'
WHEN price<100000 THEN 'C'
ELSE 'D'
END AS 工资
FROM tb_sku

```

```
/*常见函数
LENGTH(str)
CONCAT(str1,str2,...)
SUBSTR(str FROM pos FOR len)
INSTR(str,substr)
trim
upper
LOWER(str)
lpad
rpad
replace
数学函数
ROUND(X)
ceil
floor
TRUNCATE
mod

日期函数
now
curdate
curtime
year
MONTH
MONTHNAME(date)
DAY
HOUR
MINUTE
SECOND
STR_TO_DATE(str,format)
DATE_FORMAT(date,format)
其他函数
VERSION()
datebase
USER
控制函数	
if
case

*/

```

```mysql
-- #1.简单使用
-- SELECT SUM(salary) FROM employees;
-- SELECT AVG(salary) FROM employees;
-- SELECT MAX(salary) FROM employees;
-- SELECT MIN(salary) FROM employees;
-- SELECT COUNT(salary) FROM employees;
-- 
-- SELECT SUM(salary) 和,ROUND(AVG(salary),2) 平均,MAX(salary) 最高,MIN(salary) 最低,COUNT(salary) 个数
-- FROM employees;
-- 
-- #2.参数支持哪些数据类型
-- 
-- SELECT SUM(last_name),AVG(last_name) FROM employees;
-- SELECT SUM(hiredate),AVG(hiredate) FROM employees;
-- 
-- SELECT MAX(last_name),MIN(last_name) FROM employees;
-- SELECT MAX(hiredate),MIN(hiredate) FROM employees;
-- 
-- SELECT COUNT(commission_pct) FROM employees;
-- SELECT COUNT(last_name) FROM employees;
-- 
-- #3.是否忽略null
-- 
-- SELECT SUM(commission_pct),AVG(commission_pct) FROM employees;
-- 
-- SELECT commission_pct FROM employees;
-- 
-- SELECT SUM(commission_pct),AVG(commission_pct),SUM(commission_pct)/35,AVG(commission_pct)/107 FROM employees;
-- 
-- SELECT MAX(commission_pct),MIN(commission_pct) FROM employees;
-- 
-- SELECT COUNT(commission_pct) FROM employees;
-- 
-- #4.和distinct搭配
-- 
-- SELECT SUM(DISTINCT salary),SUM(salary) FROM employees;
-- 
-- SELECT COUNT(DISTINCT salary),COUNT(salary) FROM employees;
-- 
-- #5.count函数详解
-- 
-- SELECT COUNT(salary) FROM employees;
-- SELECT COUNT(*) FROM employees;
-- SELECT COUNT(1) FROM employees;
-- /*
-- 效率上：
-- MyISAM存储引擎，count(*)最高
-- InnoDB存储引擎，count(*)和count(1)效率>count(字段)
-- */
-- 
-- #6.和分组函数一同查询的字段有限制
-- 
-- SELECT AVG(salary),employee_id FROM employees;

#分组函数

#语法
/*
SELECT 分组函数，列(要求出现在group by 的后面)
FROM 表
【WHERE 筛选条件】
GROUP BY 分组的列表
【ORDER BY】
*/
#SELECT AVG(salary) FROM employees

#简单的分组查询
#案例1：查询每个工种的最高工资
#SELECT MAX(salary),job_id
#FROM employees
#GROUP BY job_id;

#案例2、查询每个位置上的部门个数
SELECT COUNT(*),location_id
FROM departments
GROUP BY location_id






```

分组函数

```mysql
#分组函数

#语法
/*
SELECT 分组函数，列(要求出现在group by 的后面)
FROM 表
【WHERE 筛选条件】
GROUP BY 分组的列表
【ORDER BY】
注意：查询列表必须特殊，要求是分组函数和group by 后出现的字段
特点：1、分组查询中的筛选条件分为两类
												数据源            位置            关键字
分组前筛选              原始表  				GROUP BY前面			WHERE
分组后筛选							分组后的结果集  GROUP BY后面			HAVING

①分组函数做条件肯定是放在having子句中
②能够分组前筛选，优先考虑分组前筛选

2.GROUP BY子句支持单个字段分组，也支持多个字段分组，（多个字段隔开，没有顺序限制）
3、也可以添加排序
*/

#sql的执行顺序一般为from,WHERE,GROUP BY,HAVING,ORDER BY,LIMIT

#添加筛选条件
#案例1：查询邮箱中包含a字符的，每个部门的平均工资
-- SELECT AVG(salary),department_id,email
-- FROM employees
-- WHERE email LIKE "%a%"
-- GROUP BY department_id

#案例2：查询有奖金的每个领导手下员工的最高工资
-- SELECT MAX(salary),last_name,manager_id
-- FROM employees
-- WHERE commission_pct IS NOT NULL
-- GROUP BY manager_id

#添加复杂的筛选条件
#案例一，查询那个部门的员工个数大于2
#①查询每个部门的员工个数
#②根据①的结果进行筛选，查询那个部门的员工个数大于2

#计算个数，按照department_id进行分组
-- SELECT COUNT(*),department_id
-- FROM employees
-- GROUP BY department_id
-- #条件个数大于2的department_id
-- HAVING count(*)>2

#查询每个工种有奖金的员工的最高工资>12000的工种编号和最高工资

#查询每个工种有奖金的最高工资
-- SELECT MAX(salary) AS 最高工资,job_id
-- FROM employees
-- WHERE commission_pct is not NULL
-- GROUP BY job_id
-- #条件最高工资大于12000的
-- HAVING 最高工资>12000

#查询领导编号大于102的每个领导下的员工的最低工资大于5000的

#查询大于102的领导的员工的最低工资
-- SELECT MIN(salary),manager_id
-- FROM employees
-- WHERE manager_id>102
-- GROUP BY manager_id
-- #最低工资大于5000的
-- HAVING MIN(salary)>5000

#按表达式或函数分组

#案例一、按员工姓名长度分组，查询每一组员工个数，筛选员工个数大于5的有哪些

#查询每个姓名长度的个数
#count(*)会根据条件来进行统计
-- SELECT COUNT(*),LENGTH(last_name)
-- FROM employees
-- GROUP BY LENGTH(last_name)
-- HAVING COUNT(*)>5

#按多个字段分组
#案例每个部门每个工种的员工的平均工资
-- SELECT AVG(salary),department_id,job_id
-- FROM employees
-- GROUP BY department_id,job_id

#添加排序
#查询每个部门每个工种的员工的平均工资，并且按平均工资的从高到低显示
-- SELECT AVG(salary),department_id,job_id
-- FROM employees
-- GROUP BY department_id,job_id
-- ORDER BY AVG(salary) DESC

#查询每个部门每个工种的员工的平均工资大于12000，并且按平均工资的从高到低显示
-- SELECT AVG(salary),department_id,job_id
-- FROM employees
-- GROUP BY department_id,job_id
-- HAVING AVG(salary)>12000
-- ORDER BY AVG(salary) DESC
```

分组排序练习

```mysql
#查询各job_id的员工工资的最大值，最小值，平均值，总和，并按job_id升序
-- SELECT MAX(salary),MIN(salary),AVG(salary),SUM(salary),job_id
-- FROM employees
-- GROUP BY job_id
-- ORDER BY job_id

#查询员工最高工资和最低工资的差距
-- SELECT MAX(salary),MIN(salary),MAX(salary)-MIN(salary) AS difference
-- FROM employees

#查询各个管理者手下的员工的最低工资，其中最低的工资不能低于6000，没有管理者的员工不计算在内
-- SELECT MIN(salary),manager_id 
-- FROM employees
-- WHERE manager_id is not NULL
-- GROUP BY manager_id
-- HAVING MIN(salary)>6000


#查询所有部门的编号，员工数量和工资平均值，并按平均工资降序
-- SELECT COUNT(*),department_id,AVG(salary)
-- FROM employees
-- WHERE department_id is not NULL
-- GROUP BY department_id
-- ORDER BY AVG(salary) DESC

#查询各个job_id的员工人数
SELECT job_id,COUNT(*)
FROM employees
GROUP BY job_id
```

```
#连接查询
/*
多表查询，当查询的字段来自多个表时，就会用到连接查询
分类：
按年代分类：
sql92分类  只支持内连接
sql99标准【推荐】：支持内连接，外连接（左外和右外）+ 交叉连接
内连接
		等值连接
		非等值连接
		自连接
外连接
		左外连接
		右外连接
		全外连接
交叉连接
		
*/ 
#笛卡尔乘积现象 表一：有n行 ,表二,有m行 结果m*n行
#发生原因：没有有效的连接条件
#如何避免：添加有效的连接条件
-- SELECT *
-- FROM beauty,boys

#消除笛卡尔积的现象
-- SELECT `name`,boyName
-- FROM beauty,boys
-- WHERE beauty.boyfriend_id=boys.id

#一、sql92标准
#1、等值连接
#案例一、查询女生名和对应的男生名
-- SELECT `name`,boyName
-- FROM beauty,boys
-- WHERE beauty.boyfriend_id=boys.id

#2、查询员工名和对应的部门名
-- SELECT last_name,job_title,e.job_id 
-- FROM employees e,jobs j
-- WHERE e.job_id=j.job_id

#案例：查询有奖金的员工名、部门名
SELECT last_name,department_name
FROM employees e,departments d
WHERE e.department_id=d.department_id AND e.commission_pct is not NULL
```


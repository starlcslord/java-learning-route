### 分组查询练习

```mysql
#1.查询出job_id 的员工工资的最大值，最小值，平均值，总和，并按job_id升序
-- SELECT MAX(salary),MIN(salary),AVG(salary),SUM(salary)
-- FROM employees
-- GROUP BY job_id 
-- ORDER BY job_id；
#默认升序

#2.查询员工最高工资和最低工资的差距(DIFFERENCE)
-- SELECT MAX(salary)-MIN(salary) DIFFERENCE
-- FROM employees
-- 
#3.查询各个管理者手下员工的最低工资，最低工资不能低于6000，没有管理者的不计算在内
#设计到分组的查询的时候使用HAVING
-- SELECT MIN(salary),manager_id
-- FROM employees
-- WHERE manager_id is not NULL
-- GROUP BY manager_id
-- HAVING MIN(salary)>=6000  
#4.查询所有部门的编号，员工数量和工资平均值，并按平均工资降序
-- SELECT department_id,COUNT(*),AVG(salary) a
-- FROM employees
-- GROUP BY department_id 
-- ORDER BY a DESC
#5.选择具有各个job_id的员工人数
SELECT job_id,COUNT(*)
FROM employees
GROUP BY job_id
```





```mysql
/*
连接查询
含义：又称多表查询，当查询的字段来自多个表时，就会用到连接查询
笛卡尔乘积 表一 有m行  表二有 n行   结果 m*n 行
发生原因 ： 没有有效的连接条件
解决原因：添加有效的连接条件

分类 
		 按年代分类
		 sql92标准：仅仅支持内连接
		 sql99标准【推荐】 支持内连接+外连接（左外和右外）+交叉连接
		 
		 
		 
		 按功能分类
			内连接：
						等值连接
						非等值连接
			外连接：
						左外连接
						右外连接
						全外连接
			交叉连接
			
*/
-- SELECT `name` ,boyname
-- FROM beauty,boys
-- WHERE boyfriend_id = boys.id
-- 

#一、sql92标准
#1、等值连接
#案例1 ： 查询女神名 和1 对应的男神名
-- SELECT `name`,boyname
-- FROM boys,beauty
-- WHERE beauty.boyfriend_id = boys.id

#案例2：查询员工名和对应的部门名
-- SELECT last_name,department_name
-- FROM employees,departments
-- WHERE employees.department_id = departments.department_id

#2、
#为表起别名，区分多个重名的字段
#查询员工名、工种号，工种名
-- SELECT e.last_name,e.job_id,j.job_title
-- FROM employees AS e,jobs AS j
-- WHERE e.job_id = j.job_id

#4、是否可以添加筛选
#案例：查询有奖金的员工名、部门名
-- SELECT last_name,d.department_name
-- FROM employees as e,departments as d
-- WHERE e.commission_pct is not NULL and e.department_id = d.department_id

#案例2：查询城市名第二个为o的部门名
-- SELECT department_name,city
-- FROM departments d,locations l
-- WHERE  d.location_id = l.location_id AND city LIKE '_o%';

#5、可以加分组吗？
#查询查询每个城市的部门个数
-- SELECT city,COUNT(*) 个数
-- FROM locations l,departments d
-- WHERE l.location_id = d.location_id
-- GROUP BY l.city

#6.查询有奖金的每个部门的部门名和部门领导编号和该部门的最低工资
-- SELECT e.department_id,department_name,MIN(salary),d.manager_id
-- FROM employees e,departments d
-- WHERE e.department_id = d.department_id AND e.commission_pct is not null
-- GROUP BY department_name,d.manager_id

#案例：查询每个工种的工种名和员工个数，并且按员工个数降序
-- SELECT j.job_title 工种名,COUNT(*) 个数
-- FROM employees e,jobs j
-- WHERE e.job_id = j.job_id 
-- GROUP BY j.job_id
-- ORDER BY COUNT(*) desc

#7.实现三表连接
#案例：查询员工的员工名，部门名和所在城市
-- SELECT last_name,department_name,city
-- FROM employees,departments,locations
-- WHERE employees.department_id = departments.department_id and departments.location_id = locations.location_id





#2、非等值连接
#案例1 ：查询员工的工资和工资级别
-- SELECT e.last_name,jb.grade_level
-- FROM employees e,job_grades jb
-- WHERE salary BETWEEN jb.lowest_level  and jb.highest_level





#3.自连接 查询员工名和上级的名称
-- SELECT e1.employee_id,e1.last_name,e2.employee_id,e2.last_name
-- FROM employees e1,employees e2
-- WHERE e1.manager_id = e2.employee_id


#练习
#一、显示员工表
```

```mysql
/*
连接查询
含义：又称多表查询，当查询的字段来自多个表时，就会用到连接查询
笛卡尔乘积 表一 有m行  表二有 n行   结果 m*n 行
发生原因 ： 没有有效的连接条件
解决原因：添加有效的连接条件

分类 
		 按年代分类
		 sql92标准：仅仅支持内连接
		 sql99标准【推荐】 支持内连接+外连接（左外和右外）+交叉连接
		 
		 
		 
		 按功能分类
			内连接：
						等值连接
						非等值连接
			外连接：
						左外连接
						右外连接
						全外连接
			交叉连接
			
*/
-- SELECT `name` ,boyname
-- FROM beauty,boys
-- WHERE boyfriend_id = boys.id
-- 

#一、sql92标准
#1、等值连接
#案例1 ： 查询女神名 和1 对应的男神名
-- SELECT `name`,boyname
-- FROM boys,beauty
-- WHERE beauty.boyfriend_id = boys.id

#案例2：查询员工名和对应的部门名
-- SELECT last_name,department_name
-- FROM employees,departments
-- WHERE employees.department_id = departments.department_id

#2、
#为表起别名，区分多个重名的字段
#查询员工名、工种号，工种名
-- SELECT e.last_name,e.job_id,j.job_title
-- FROM employees AS e,jobs AS j
-- WHERE e.job_id = j.job_id

#4、是否可以添加筛选
#案例：查询有奖金的员工名、部门名
-- SELECT last_name,d.department_name
-- FROM employees as e,departments as d
-- WHERE e.commission_pct is not NULL and e.department_id = d.department_id

#案例2：查询城市名第二个为o的部门名
-- SELECT department_name,city
-- FROM departments d,locations l
-- WHERE  d.location_id = l.location_id AND city LIKE '_o%';

#5、可以加分组吗？
#查询查询每个城市的部门个数
-- SELECT city,COUNT(*) 个数
-- FROM locations l,departments d
-- WHERE l.location_id = d.location_id
-- GROUP BY l.city

#6.查询有奖金的每个部门的部门名和部门领导编号和该部门的最低工资
-- SELECT e.department_id,department_name,MIN(salary),d.manager_id
-- FROM employees e,departments d
-- WHERE e.department_id = d.department_id AND e.commission_pct is not null
-- GROUP BY department_name,d.manager_id

#案例：查询每个工种的工种名和员工个数，并且按员工个数降序
-- SELECT j.job_title 工种名,COUNT(*) 个数
-- FROM employees e,jobs j
-- WHERE e.job_id = j.job_id 
-- GROUP BY j.job_id
-- ORDER BY COUNT(*) desc

#7.实现三表连接
#案例：查询员工的员工名，部门名和所在城市
-- SELECT last_name,department_name,city
-- FROM employees,departments,locations
-- WHERE employees.department_id = departments.department_id and departments.location_id = locations.location_id





#2、非等值连接
#案例1 ：查询员工的工资和工资级别
-- SELECT e.last_name,jb.grade_level
-- FROM employees e,job_grades jb
-- WHERE salary BETWEEN jb.lowest_level  and jb.highest_level





#3.自连接 查询员工名和上级的名称
-- SELECT e1.employee_id,e1.last_name,e2.employee_id,e2.last_name
-- FROM employees e1,employees e2
-- WHERE e1.manager_id = e2.employee_id


#练习
#一、显示员工表的最大工资，工资平均值
-- SELECT MAX(salary),AVG(salary)
-- FROM employees
#二、查询员工表employee_id,job_id,last_name,按department_id降序，salary升序
-- SELECT employee_id,job_id,last_name,salary
-- FROM employees
-- ORDER BY salary ASC,department_id DESC


#三、显示当前日期、以及去前后空格，截取子字符串的函数
-- SELECT now()
#SELECT TRIM('             字符                  ')
-- SELECT SUBSTR(str,startIndex);
-- SELECT SUBSTR(str,startIndex,length)

#作业
#1、显示所有员工的姓名、部门号和部门名称
-- SELECT last_name,d.department_id,d.department_name
-- FROM employees e,departments d
-- WHERE e.department_id = d.department_id

#2.查询90号部门员工的Job_id和90号部门的location_id
-- SELECT last_name,employees.department_id,location_id
-- FROM employees,departments
-- WHERE employees.department_id = 90 AND employees.department_id = departments.department_id

#3.选择所有有奖金的员工的last_name,department_name,location_id,city

-- SELECT last_name,department_name,locations.location_id,city
-- FROM employees,departments,locations
-- WHERE employees.department_id  =  departments.department_id and departments.location_id = locations.location_id AND employees.commission_pct is not NULL

#4、查询每个工种，每个部门的部门名，工种名和最低工资
-- SELECT jobs.job_id,jobs.job_title,departments.department_id,departments.department_name,MIN(salary)
-- FROM employees,departments,jobs
-- WHERE employees.job_id = jobs.job_id and employees.department_id  = departments.department_id 	
-- GROUP BY department_id,job_title

#查询city在Toronto工作的员工
-- SELECT last_name
-- FROM employees,locations,departments
-- WHERE employees.department_id = departments.department_id AND departments.location_id = locations.location_id AND locations.city = 'Toronto'
#5、查询每个国家下的部门个数大于2的国家编号
-- SELECT COUNT(*),locations.country_id
-- FROM locations,departments
-- WHERE departments.location_id = locations.location_id
-- GROUP BY country_id
-- HAVING COUNT(*)>=2
#6.从员工表中查找管理者的员工号和名
-- SELECT e.last_name,e.employee_id "Emp#",m.last_name,m.employee_id "Mgr#"
-- FROM employees e,employees m
-- WHERE e.manager_id = m.employee_id


#二 SQL99语法
/*
语法
			SELECT 查询列表
			FROM 表1 别名  [连接类型]
			JOIN 表2 别名   on  连接条件
			【WHERE 筛选条件】
			【GROUP BY 分组】
			【HAVING 筛选条件】
			【ORDER BY 排序条件】
			
内连接（★） INNER
外连接 
			左外（★） LEFT OUTER
			右外（★） RIGHT OUTER
			全外： FULL OUTER
交叉连接:CROSS
*/


#一、内连接
/*
语法：
SELECT 查询列表
from 表1 别名
INNER JOIN 表2 别名
on 连接条件

分类:
等值连接
非等值连接
自连接
特点
1、添加排序、分组、筛选
2、inner可以省略
3、筛选条件放在where后面，连接条件放在on后面
4、INNER JOIN 连接和 sql92语法的等值连接一样，都是查询多表的交集
*/
#1.等值连接
#案例1：查询员工名、部门名
-- SELECT last_name,department_name
-- FROM employees
-- INNER JOIN departments
-- ON employees.department_id = departments.department_id

#案例2:查询名字中包含e的员工名和工种名（添加筛选）
-- SELECT last_name,job_title
-- FROM employees
-- INNER JOIN jobs
-- ON employees.job_id = jobs.job_id
-- WHERE last_name LIKE "%e%"

#案例3、查询部门个数>=3的城市名(添加分组和筛选)
-- SELECT COUNT(*),city
-- FROM departments
-- INNER JOIN locations
-- ON departments.location_id = locations.location_id
-- GROUP BY locations.location_id
-- HAVING COUNT(*)>=3

#案例4.查询哪个部门员工个数>3的部门名和员工个数，并按个数降序(排序)
-- SELECT COUNT(*),department_name
-- FROM employees
-- INNER JOIN departments
-- ON departments.department_id = employees.department_id
-- GROUP BY departments.department_id
-- ORDER BY COUNT(*) DESC

#案例五：查询员工名、部门名、工种名并且按部门名排序
-- SELECT last_name,department_name,job_title
-- FROM employees
-- INNER JOIN  departments
-- ON employees.department_id = departments.department_id 
-- INNER JOIN jobs
-- ON employees.job_id = jobs.job_id
-- ORDER BY departments.department_name DESC

#二、非等值连接
#查询员工的工资级别
-- SELECT last_name,salary,job_grades.grade_level
-- FROM employees
-- INNER JOIN job_grades
-- ON employees.salary BETWEEN lowest_level AND highest_level

#查询每个工资级别的个数，并且排序
-- SELECT job_grades.grade_level,COUNT(*)
-- FROM employees
-- INNER JOIN job_grades
-- ON employees.salary BETWEEN lowest_level AND highest_level
-- GROUP BY job_grades.grade_level
-- 
#查询工资级别的个数大于20的级别，并且按照工资级别排序
-- SELECT job_grades.grade_level,COUNT(*)
-- FROM employees
-- INNER JOIN job_grades
-- ON employees.salary BETWEEN lowest_level AND highest_level
-- GROUP BY job_grades.grade_level
-- HAVING COUNT(*)>20
-- ORDER BY grade_level

#自连接
-- SELECT e.last_name,m.last_name
-- FROM employees e
-- INNER JOIN employees m
-- ON e.manager_id = m.employee_id
-- WHERE e.last_name LIKE "%K%"

#外连接
/*
应用场景：用户查询一个表中有，另一个表没有的记录
如果从表中有和它匹配的，则显示匹配的值

*/
#查询没有男朋友的女生名

```

![1](C:\Users\lcs\Desktop\数据库查询\asset\1.bmp)

![Snipaste_2022-01-06_15-13-08](C:\Users\lcs\Desktop\数据库查询\asset\Snipaste_2022-01-06_15-13-08.bmp)

```mysql
-- -- sql99连接语法
-- /*
-- 语法：
-- select 查询列表
-- from 表1 别名【连接类型】
-- join 表2 别名
-- on 连接条件
-- 【where 筛选条件】
-- 【group by 分组】
-- 【having 筛选条件】
-- 【order by 排序列表】
-- */
-- /*
-- 连接类型：
-- 内连接：inner
-- 左外：    left 【outer】
-- 右外：    right 【outer】
-- 全外：    full 【outer】
-- 交叉连接：cross
-- */
--
-- -- 一、内连接
-- /*
-- 语法：
-- select 查询列表
-- from 表1 别名
-- inner join 表2 别名
-- on 连接条件
-- */
-- -- 1、等值连接
-- -- 案例1：查询员工名、部门名
-- SELECT last_name, department_name
-- FROM employees e
-- INNER JOIN departments d
-- ON e.`department_id`=d.`department_id`;
--
-- -- 案例2：查询名字中包含e的员工名和他们的工种名
-- SELECT last_name, job_title
-- FROM employees e
-- INNER JOIN jobs j
-- ON e.job_id=j.`job_id`
-- WHERE e.last_name LIKE '%e%';
--
-- -- 案例3：查询部门个数>3的城市名和部门个数
-- SELECT city, COUNT(*) 部门个数
-- FROM departments d
-- INNER JOIN locations l
-- ON d.`location_id`=l.`location_id`
-- GROUP BY city
-- HAVING COUNT(*)>3;
--
-- -- 案例4：查询哪个部门的员工个数>3的部门名和员工个数，并按个数降序
-- SELECT COUNT(*) 员工个数, d.`department_name`
-- FROM employees e
-- INNER JOIN departments d
-- ON d.`department_id`=e.`department_id`
-- GROUP BY d.department_id
-- HAVING COUNT(*)>3
-- ORDER BY COUNT(*) DESC;
--
-- -- 案例5：查询员工名、部门名、工种名，并按照部门名降序
-- SELECT e.last_name, d.department_name, j.job_title
-- FROM employees e
-- INNER JOIN departments d
-- ON e.`department_id`=d.`department_id`
-- INNER JOIN jobs j
-- ON e.job_id=j.`job_id`
-- ORDER BY d.department_name DESC;
--
-- -- 2.非等值连接
-- --  案例：查询员工工资级别
-- SELECT salary,grade_level
-- FROM employees e
-- JOIN job_grades g
-- ON e.salary BETWEEN g.`lowest_sal`AND g.`highest_sal`;
--
-- -- 案例：查询每个工资级别个数>20的个数，并且按工资级别降序
-- SELECT COUNT(*), grade_level 工资级别
-- FROM employees e
-- JOIN job_grades jg
-- ON e.`salary` BETWEEN jg.`lowest_sal`AND jg.`highest_sal`
-- GROUP BY grade_level
-- HAVING COUNT(*) > 20
-- ORDER BY grade_level DESC ;
--
-- -- 3.自连接
-- -- 查询员工名字、上级的名字
-- SELECT e.last_name AS 员工名字, m.last_name 他领导的名字
-- FROM employees e
-- JOIN employees m
-- ON e.`manager_id`=m.`employee_id`;
--
--
-- -- 二、外连接
-- /*
-- 用于查询一个表中有，另一个表中没有的记录
-- 特点：
-- 1、外连接的查询结果=内连接结果+主表中有而从表中没有的记录，从表中没有的记录部分填充null
-- 2、左外连接，left join左面的是主表
--      右外连接，right join 右面的是主表
-- 3、左外和右外交换两表顺序，效果相同
-- 4、全外连接是=内连接结果+表1中的独有记录+表2中的独有记录。另一个表没有的部分填充null
-- */
-- -- 引例：找出没有男票的女神
-- SELECT b.name, bo.*
-- FROM beauty b
-- LEFT OUTER JOIN boys bo
-- ON b.boyfriend_id=bo.id
-- WHERE bo.id IS NULL;
--
-- -- 三、交叉连接：笛卡尔乘积而已，用cross join
--
--
-- -- 进阶 7：子查询
-- /*
-- 含义：出现在其他语句中的select语句，称为子查询或内查询
-- 内部嵌套其他select语句的查询语句称为主查询或外查询。
-- 分类：
-- 按照子查询出现的位置：select后面：仅仅支持标量子查询
-- 			            from后面：支持表子查询
-- 			            where或having后面：支持标量子查询或者单行或者单列
-- 			            exists后面（相关子查询）：支持表子查询
-- 按结果集的行列数不同： 标量子查询（结果集只有一行一列）
-- 				     列子查询（结果集只有一列多行）
-- 				     行子查询（结果集有多行多列一般为一行多列）
-- 				     表子查询（以上三种统称。通常多行多列）
-- */
--
-- -- where或having 后面的子查询：
-- -- 1、标量子查询（单行单列）
-- -- 2、列子查询（多行）
-- -- 3、行子查询（多行多列）
-- --  特点：子查询放在小括号内，子查询一般在条件的右侧，标量子查询一般搭配单行操作符使用
-- -- < > <= = <>  ， 而列子查询一般搭配多行操作符使用in ,   any/some , all
-- -- 1、标量子查询
-- -- 案例1：查询谁的工资比Abel高？
-- -- 查询Abel的工资
-- SELECT salary
-- FROM employees
-- WHERE last_name='Abel';
-- -- 查询员工信息，满足salary>上述结果
-- SELECT *
-- FROM employees
-- WHERE salary>(
-- 	SELECT salary
-- 	FROM employees
-- 	WHERE last_name='Abel'
-- );
-- -- 案例2：返回job_id与141号员工相同，salary比143号员工多的员工姓名，job_id和工资
-- -- 查询141号员工的job_id
-- SELECT job_id
-- FROM employees
-- WHERE employee_id = 141;
-- -- 查询143号员工的工资
-- SELECT `salary`
-- FROM employees
-- WHERE employee_id = 143;
-- -- 主查询：
-- SELECT last_name, job_id, `salary`
-- FROM employees
-- WHERE `salary` > (
-- 	SELECT `salary`
-- 	FROM employees
-- 	WHERE employee_id = 143
-- )
-- AND job_id = (
-- 	SELECT job_id
-- 	FROM employees
-- 	WHERE employee_id = 141
-- );
--
-- -- 案例3：返回公司工资最少的员工的last_name,job_id 和salary
-- SELECT last_name, job_id, salary
-- FROM employees
-- WHERE salary = (
-- 	SELECT MIN(salary)
-- 	FROM employees
-- );
-- -- 案例4：查询最低工资大于50号部门的最低工资的部门id和其最低工资
-- SELECT employee_id, MIN(salary) mi
-- FROM employees
-- GROUP BY department_id
-- HAVING mi > (
-- 	SELECT MIN(salary)
-- 	FROM employees
-- 	WHERE department_id = 50
-- );
-- -- 2. 多行子查询
-- -- IN/NOT IN            ANY/ SOME           ALL
-- -- 案例1：返回location_id是1400或者1700的部门中的所有员工的姓名
-- SELECT last_name
-- FROM employees
-- WHERE department_id IN (
-- 	SELECT department_id
-- 	FROM departments
-- 	WHERE location_id IN(1400, 1700)
-- );
-- -- 案例2：返回中比job_id为"IT_PROG'部门中的任一工资低的员工的员工号、姓名、job_id以及salary
-- SELECT last_name, job_id, employee_id, salary
-- FROM employees
-- WHERE salary < ANY(
-- 	SELECT salary
-- 	FROM employees
-- 	WHERE job_id = 'IT_PROG'
-- );
--
-- -- 3. 行子查询
-- -- 案例：查询员工编号最小而且工资最高的员工信息
-- SELECT *
-- FROM employees
-- WHERE employee_id = (
-- 	SELECT MIN(employee_id)
-- 	FROM employees
-- ) AND salary = (
-- 	SELECT MAX(salary)
-- 	FROM employees
-- );
-- -- 上述是标量子查询，下面用行子查询：
-- SELECT *
-- FROM employees
-- WHERE (employee_id, salary) = (
-- 	SELECT MIN(employee_id), MAX(salary)
-- 	FROM employees
-- );
--
-- -- 二、select 后面仅仅支持标量子查询
-- -- 案例： 查询每个部门的员工的个数
--  SELECT d.*, (
-- 	SELECT COUNT(*)
-- 	FROM employees e
-- 	WHERE e.department_id = d.department_id
-- ) 员工个数
-- FROM departments d ;
-- -- 用连接做：
-- SELECT d.*, COUNT(e.manager_id)
-- FROM employees e
-- RIGHT OUTER JOIN departments d
-- ON e.`department_id`=d.`department_id`
-- GROUP BY d.`department_id`;
--
-- -- 案例2：查询员工号为102的部门名
-- SELECT department_name
-- FROM departments d
-- INNER JOIN employees e
-- ON d.department_id = e.department_id
-- WHERE e.employee_id = 102;
--
-- -- from后面
-- -- 案例：查询每个部门的平均工资的工资等级
-- SELECT temp.*, g.`grade_level`
-- FROM (
-- 	SELECT AVG(salary) av, department_id
-- 	FROM employees
-- 	GROUP BY department_id
-- ) temp
-- INNER JOIN job_grades g
-- ON temp.av BETWEEN g.`lowest_sal` AND g.`highest_sal`;
--
-- -- 四 exists后面（相关子查询）
-- SELECT EXISTS(SELECT employee_id FROM employees);就是用来看子查询结果是否有值

#查询有员工的部门名
-- SELECT department_name
-- FROM departments d
-- WHERE EXISTS(
-- 	SELECT *
-- 	FROM employees e
-- 	WHERE d.department_id = e.department_id
-- ) = 1


#例题
#1、查询和Zlotkey相同部门的员工姓名和工资
# SELECT last_name,salary
# FROM employees
# WHERE department_id = (select  department_id from employees where  last_name = 'Zlotkey')

#2、查询工资比平均工资高的员工的员工号，姓名和工资
# select last_name,employee_id,salary
# from    employees
# where  salary > (select  AVG(salary) from employees)

#内连接
#3、查询各部门中工资比本部门平均工资高的员工号，姓名，工资
select employee_id,last_name,salary
from employees e
INNER join (
    select AVG(salary) ag,department_id
    from    employees
    GROUP BY department_id
    ) ag_dep
ON e.department_id = ag_dep.department_id
where salary >ag_dep.ag;
#4、查询和姓名中包含u的员工在相同部门的员工号和员工名
select last_name,employee_id
from  employees
where  department_id in (select  department_id from  employees where  last_name like '%u%');

#5、查询在部门的的location_id为1700的部门工作的员工的员工号
select last_name,employee_id,department_id
from  employees
where  department_id in (select  department_id from departments where location_id = 1700);

#6、查询管理员是king的员工姓名和工资
select   last_name,salary,manager_id,employee_id
from employees
where  manager_id  in  (select employee_id from  employees where last_name='K_ing');

#7、查询工资最高的员工的姓名，要求first_name和last_name为同一列
select  CONCAT(first_name,' ',last_name)  姓名,salary
from employees
where  salary = (select  max(salary) from employees )
```





子查询

```mysql
-- -- sql99连接语法
-- /*
-- 语法：
-- select 查询列表
-- from 表1 别名【连接类型】
-- join 表2 别名
-- on 连接条件
-- 【where 筛选条件】
-- 【group by 分组】
-- 【having 筛选条件】
-- 【order by 排序列表】
-- */
-- /*
-- 连接类型：
-- 内连接：inner
-- 左外：    left 【outer】
-- 右外：    right 【outer】
-- 全外：    full 【outer】
-- 交叉连接：cross
-- */
--
-- -- 一、内连接
-- /*
-- 语法：
-- select 查询列表
-- from 表1 别名
-- inner join 表2 别名
-- on 连接条件
-- */
-- -- 1、等值连接
-- -- 案例1：查询员工名、部门名
-- SELECT last_name, department_name
-- FROM employees e
-- INNER JOIN departments d
-- ON e.`department_id`=d.`department_id`;
--
-- -- 案例2：查询名字中包含e的员工名和他们的工种名
-- SELECT last_name, job_title
-- FROM employees e
-- INNER JOIN jobs j
-- ON e.job_id=j.`job_id`
-- WHERE e.last_name LIKE '%e%';
--
-- -- 案例3：查询部门个数>3的城市名和部门个数
-- SELECT city, COUNT(*) 部门个数
-- FROM departments d
-- INNER JOIN locations l
-- ON d.`location_id`=l.`location_id`
-- GROUP BY city
-- HAVING COUNT(*)>3;
--
-- -- 案例4：查询哪个部门的员工个数>3的部门名和员工个数，并按个数降序
-- SELECT COUNT(*) 员工个数, d.`department_name`
-- FROM employees e
-- INNER JOIN departments d
-- ON d.`department_id`=e.`department_id`
-- GROUP BY d.department_id
-- HAVING COUNT(*)>3
-- ORDER BY COUNT(*) DESC;
--
-- -- 案例5：查询员工名、部门名、工种名，并按照部门名降序
-- SELECT e.last_name, d.department_name, j.job_title
-- FROM employees e
-- INNER JOIN departments d
-- ON e.`department_id`=d.`department_id`
-- INNER JOIN jobs j
-- ON e.job_id=j.`job_id`
-- ORDER BY d.department_name DESC;
--
-- -- 2.非等值连接
-- --  案例：查询员工工资级别
-- SELECT salary,grade_level
-- FROM employees e
-- JOIN job_grades g
-- ON e.salary BETWEEN g.`lowest_sal`AND g.`highest_sal`;
--
-- -- 案例：查询每个工资级别个数>20的个数，并且按工资级别降序
-- SELECT COUNT(*), grade_level 工资级别
-- FROM employees e
-- JOIN job_grades jg
-- ON e.`salary` BETWEEN jg.`lowest_sal`AND jg.`highest_sal`
-- GROUP BY grade_level
-- HAVING COUNT(*) > 20
-- ORDER BY grade_level DESC ;
--
-- -- 3.自连接
-- -- 查询员工名字、上级的名字
-- SELECT e.last_name AS 员工名字, m.last_name 他领导的名字
-- FROM employees e
-- JOIN employees m
-- ON e.`manager_id`=m.`employee_id`;
--
--
-- -- 二、外连接
-- /*
-- 用于查询一个表中有，另一个表中没有的记录
-- 特点：
-- 1、外连接的查询结果=内连接结果+主表中有而从表中没有的记录，从表中没有的记录部分填充null
-- 2、左外连接，left join左面的是主表
--      右外连接，right join 右面的是主表
-- 3、左外和右外交换两表顺序，效果相同
-- 4、全外连接是=内连接结果+表1中的独有记录+表2中的独有记录。另一个表没有的部分填充null
-- */
-- -- 引例：找出没有男票的女神
-- SELECT b.name, bo.*
-- FROM beauty b
-- LEFT OUTER JOIN boys bo
-- ON b.boyfriend_id=bo.id
-- WHERE bo.id IS NULL;
--
-- -- 三、交叉连接：笛卡尔乘积而已，用cross join
--
--
-- -- 进阶 7：子查询
-- /*
-- 含义：出现在其他语句中的select语句，称为子查询或内查询
-- 内部嵌套其他select语句的查询语句称为主查询或外查询。
-- 分类：
-- 按照子查询出现的位置：select后面：仅仅支持标量子查询
-- 			            from后面：支持表子查询
-- 			            where或having后面：支持标量子查询或者单行或者单列
-- 			            exists后面（相关子查询）：支持表子查询
-- 按结果集的行列数不同： 标量子查询（结果集只有一行一列）
-- 				     列子查询（结果集只有一列多行）
-- 				     行子查询（结果集有多行多列一般为一行多列）
-- 				     表子查询（以上三种统称。通常多行多列）
-- */
--
-- -- where或having 后面的子查询：
-- -- 1、标量子查询（单行单列）
-- -- 2、列子查询（多行）
-- -- 3、行子查询（多行多列）
-- --  特点：子查询放在小括号内，子查询一般在条件的右侧，标量子查询一般搭配单行操作符使用
-- -- < > <= = <>  ， 而列子查询一般搭配多行操作符使用in ,   any/some , all
-- -- 1、标量子查询
-- -- 案例1：查询谁的工资比Abel高？
-- -- 查询Abel的工资
-- SELECT salary
-- FROM employees
-- WHERE last_name='Abel';
-- -- 查询员工信息，满足salary>上述结果
-- SELECT *
-- FROM employees
-- WHERE salary>(
-- 	SELECT salary
-- 	FROM employees
-- 	WHERE last_name='Abel'
-- );
-- -- 案例2：返回job_id与141号员工相同，salary比143号员工多的员工姓名，job_id和工资
-- -- 查询141号员工的job_id
-- SELECT job_id
-- FROM employees
-- WHERE employee_id = 141;
-- -- 查询143号员工的工资
-- SELECT `salary`
-- FROM employees
-- WHERE employee_id = 143;
-- -- 主查询：
-- SELECT last_name, job_id, `salary`
-- FROM employees
-- WHERE `salary` > (
-- 	SELECT `salary`
-- 	FROM employees
-- 	WHERE employee_id = 143
-- )
-- AND job_id = (
-- 	SELECT job_id
-- 	FROM employees
-- 	WHERE employee_id = 141
-- );
--
-- -- 案例3：返回公司工资最少的员工的last_name,job_id 和salary
-- SELECT last_name, job_id, salary
-- FROM employees
-- WHERE salary = (
-- 	SELECT MIN(salary)
-- 	FROM employees
-- );
-- -- 案例4：查询最低工资大于50号部门的最低工资的部门id和其最低工资
-- SELECT employee_id, MIN(salary) mi
-- FROM employees
-- GROUP BY department_id
-- HAVING mi > (
-- 	SELECT MIN(salary)
-- 	FROM employees
-- 	WHERE department_id = 50
-- );
-- -- 2. 多行子查询
-- -- IN/NOT IN            ANY/ SOME           ALL
-- -- 案例1：返回location_id是1400或者1700的部门中的所有员工的姓名
-- SELECT last_name
-- FROM employees
-- WHERE department_id IN (
-- 	SELECT department_id
-- 	FROM departments
-- 	WHERE location_id IN(1400, 1700)
-- );
-- -- 案例2：返回中比job_id为"IT_PROG'部门中的任一工资低的员工的员工号、姓名、job_id以及salary
-- SELECT last_name, job_id, employee_id, salary
-- FROM employees
-- WHERE salary < ANY(
-- 	SELECT salary
-- 	FROM employees
-- 	WHERE job_id = 'IT_PROG'
-- );
--
-- -- 3. 行子查询
-- -- 案例：查询员工编号最小而且工资最高的员工信息
-- SELECT *
-- FROM employees
-- WHERE employee_id = (
-- 	SELECT MIN(employee_id)
-- 	FROM employees
-- ) AND salary = (
-- 	SELECT MAX(salary)
-- 	FROM employees
-- );
-- -- 上述是标量子查询，下面用行子查询：
-- SELECT *
-- FROM employees
-- WHERE (employee_id, salary) = (
-- 	SELECT MIN(employee_id), MAX(salary)
-- 	FROM employees
-- );
--
-- -- 二、select 后面仅仅支持标量子查询
-- -- 案例： 查询每个部门的员工的个数
--  SELECT d.*, (
-- 	SELECT COUNT(*)
-- 	FROM employees e
-- 	WHERE e.department_id = d.department_id
-- ) 员工个数
-- FROM departments d ;
-- -- 用连接做：
-- SELECT d.*, COUNT(e.manager_id)
-- FROM employees e
-- RIGHT OUTER JOIN departments d
-- ON e.`department_id`=d.`department_id`
-- GROUP BY d.`department_id`;
--
-- -- 案例2：查询员工号为102的部门名
-- SELECT department_name
-- FROM departments d
-- INNER JOIN employees e
-- ON d.department_id = e.department_id
-- WHERE e.employee_id = 102;
--
-- -- from后面
-- -- 案例：查询每个部门的平均工资的工资等级
-- SELECT temp.*, g.`grade_level`
-- FROM (
-- 	SELECT AVG(salary) av, department_id
-- 	FROM employees
-- 	GROUP BY department_id
-- ) temp
-- INNER JOIN job_grades g
-- ON temp.av BETWEEN g.`lowest_sal` AND g.`highest_sal`;
--
-- -- 四 exists后面（相关子查询）
-- SELECT EXISTS(SELECT employee_id FROM employees);就是用来看子查询结果是否有值

#查询有员工的部门名
-- SELECT department_name
-- FROM departments d
-- WHERE EXISTS(
-- 	SELECT *
-- 	FROM employees e
-- 	WHERE d.department_id = e.department_id
-- ) = 1


#例题
#1、查询和Zlotkey相同部门的员工姓名和工资
# SELECT last_name,salary
# FROM employees
# WHERE department_id = (select  department_id from employees where  last_name = 'Zlotkey')

#2、查询工资比平均工资高的员工的员工号，姓名和工资
# select last_name,employee_id,salary
# from    employees
# where  salary > (select  AVG(salary) from employees)

#内连接
#3、查询各部门中工资比本部门平均工资高的员工号，姓名，工资
select employee_id,last_name,salary
from employees e
INNER join (
    select AVG(salary) ag,department_id
    from    employees
    GROUP BY department_id
    ) ag_dep
ON e.department_id = ag_dep.department_id
where salary >ag_dep.ag;
#4、查询和姓名中包含u的员工在相同部门的员工号和员工名
select last_name,employee_id
from  employees
where  department_id in (select  department_id from  employees where  last_name like '%u%');

#5、查询在部门的的location_id为1700的部门工作的员工的员工号
select last_name,employee_id,department_id
from  employees
where  department_id in (select  department_id from departments where location_id = 1700);

#6、查询管理员是king的员工姓名和工资
select   last_name,salary,manager_id,employee_id
from employees
where  manager_id  in  (select employee_id from  employees where last_name='K_ing');

#7、查询工资最高的员工的姓名，要求first_name和last_name为同一列
select  CONCAT(first_name,' ',last_name)  姓名,salary
from employees
where  salary = (select  max(salary) from employees );

#经典案例
#1、查询最低工资的员工信息last_name,salary
select last_name,salary
from employees
where salary = (select min(salary) from employees);

#2、查询平均工资最低的部门
select department_id
from employees
group by department_id
HAVING avg(salary)<=ALL(select AVG(salary) from employees group by department_id);

#3、查询平均工资最低的部门信息和该部门的平均工资
select d.*,avg(salary)
from employees e,departments d
where e.department_id = d.department_id
group by d.department_id
HAVING avg(salary) <= ALL (select AVG(salary) from  employees group by  department_id);

#4、查询平均工资最高的job信息
select j.*,avg(salary)
from   jobs j,employees e
where j.job_id = e.job_id
group by j.job_id
HAVING avg(salary) >= ALL (select  AVG(salary) from employees group by job_id);

#5、查询平均工资高于公司的平均工资的部门有哪些
select departments.department_id,department_name,avg(salary)
from employees,departments
where employees.department_id  = departments.department_id
group by departments.department_id
having avg(salary) > (select avg(salary) from employees);

#6、查询出公司中所有manager的详细信息
select *
from employees
where employee_id in (select  manager_id from employees);

#7、各个部门中最高工资最低的那个部门最低工资是多少
select min(salary),max(salary)
from  employees
group by department_id
HAVING max(salary) <= ALL(select max(salary) from employees group by department_id);

#8、查询平均工资最高的部门的manager的详细信息:last_name,department,email,salary
select department_id
from employees
group by department_id
having avg(salary)>=ALL(select avg(salary) from employees group by  department_id);

#一、参加每个专业的人数
select  major.majorname 专业,count(*) 人数
from student,major
where student.student.majorid = major.majorid
group by student.majorid;

#二、查询参加考试的学生中，每个学生的平均分，最高分
select studentno,AVG(score),max(score)
from result
group by studentno;

#三、查询姓张的每个学生的最低分大于60的学号，姓名
select result.studentno,studentname,min(score)
from student,result
where studentname  in (select studentname from student where studentname like '张%') and result.studentno = student.studentno
group by studentno
HAVING min(score)>60;

#四、查询每个专业生日在'1988-1-1'后的学生姓名，专业姓名
select studentname,majorname
from student,major
where DATEDIFF(borndate,'1988-1-1')>0 and   student.majorid = major.majorid;

select (now()-borndate)
from student;

select now()-str_to_date('1988-1-1','%Y-%m-%d');

SELECT STR_TO_DATE('9-13-1999','%m-%d-%Y');

#查询每个专业的男生和女生人数
select  count(*),sex,majorid
from student
group by sex,majorid
order by majorid;

select majorid,
       (select count(*) from student where sex = '男' and majorid = s.majorid) 男,
       (select count(*) from student where sex = '女' and majorid = s.majorid) 女
from student s
group by majorid;


#查询专业和张翠山一样的学生的最低分
select student.studentno,min(score)
from student,result
where result.studentno = student.studentno and majorid= (select  majorid from student where studentname = '张翠山')

#查询大于60分的学生的姓名，密码，专业名
select studentname,loginpwd,majorname,score
from student,major,result
where score>60 and result.studentno = student.studentno and major.majorid = student.majorid


#按邮箱位数分组，查询每组的学生个数
select length(email),count(*)
from student
group by length(email);

#查询哪个专业没有学生，分别用左连接和右连接实现
select majorname,count(m.majorid)
from student s
right join major m on m.majorid = s.majorid
group by  m.majorname
HAVING  count(m.majorid)  = 0 or count(m.majorid) is null

#查询没有成绩的学生人数
select studentname,score
from    student
left join result r on student.studentno = r.studentno
where score is null


# SELECT d.*, COUNT(e.manager_id)
# FROM employees e
# RIGHT OUTER JOIN departments d
# ON e.`department_id`=d.`department_id`
# GROUP BY d.`department_id`;
```



分页查询

```mysql
/*
进阶8：分页查询
应用场景：当要显示的数据，一页显示不全，需要分页提交sql请求
语法：
    select 查询列表
    from   表
    【join type join 表2
    on 连接条件
    where 筛选条件
    group by 分组字段
    having  分组后的筛选
    order by 排序的字段】
    limit offset,size

offset要显示条目的起始索引（起始索引从0开始）
size要显示的条目个数

特点：
    ① limit放在查询语句的最后
    ②公式
            要显示的页数page,每页的条目数size
            page从1开始
    select 查询列表
    from   表
    limit  (page-1)*size,size;
*/
#案例1：查询第一条到第五条数据
select *
from  employees
limit 0,5;
#等价于
select *
from employees
limit  5;
#案例2：查询emp_id = 110到emp_id = 125 条数据
select *
from employees
limit 10,16;

#案例3：有奖金的员工信息，并且工资较高的前10名显示出来
select *
from employees
where commission_pct is not null
order by salary DESC
limit  10;

```



测试题

```mysql
#1、查询所有学生邮箱的用户名（@前面的字符）
select substr(email,1,instr(email,'@')-1)
from student;
#2、查询男生和女生的个数
select count(*) 人数,sex
from student
group by sex;
#3、查询年龄>25的所有学生的姓名和年纪名称
select studentname,majorname,(year(now())-year(borndate))
from student,major
where (year(now())-year(borndate))>25 and student.majorid = major.majorid
order by major.majorid;
#4.查询哪个年纪的学生最小年龄大于25岁
select min(year(now())-year(borndate)),studentname
from student
group by majorid
HAVING min(year(now())-year(borndate))<25;
# select  year(now())

```


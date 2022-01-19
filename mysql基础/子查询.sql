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
#视图
/*
含义：虚拟表和普通表一样使用
mysql5.1出现的新的特性，是通过表动态生成的数据

语法
create view 视图名
as
查询语句;

    比如：舞蹈班级和普通班级的对比
                    创建语法的关键字                是否实际占用物理空间          使用
视图                  create view                 只是保存了sql逻辑         增删改查，只是一般不能增删改
表                   create table                 保存了数据               增删改查

*/
select  stuname,majorname
from stuinfo s
inner join stumajor m on s.majorid = m.id
where s.stuname like '张%';

create view v1
as
select  stuname,majorname
from stuinfo s
inner join stumajor m on s.majorid = m.id;

select * from v1 where v1.stuname like '张%';

create view v2
as
    select *
    from account;
#二、视图的修改
#方式一
/*
create or replace view 视图名
as
查询语句;
*/


create or replace  view v1
as
    select AVG(salary)
    from employees
    inner join departments d on d.department_id = employees.department_id;

select *
from v1;

#方式二
/*
语法
alter view 视图名
as
查询语句;
*/
alter  view v1
as
    select * from  employees;

select * from v1;

#三、删除视图
/*
语法 drop view 视图名，视图名，、、、；
*/

drop view  v1,v2;

#查看视图
desc v1;
show create view v1;

#视图的更新
create or replace view myv1
as
    select last_name,email,salary*12*(1+ifnull(commission_pct,0)) 'annual salary' from employees;

select *
from myv1;

insert into myv1 values ('张飞','zf@qq.com',100000);

#3.删除
DROP view myv1;

#具备以下特点的视图是不允许更新的
/*
包含以下关键词的sql语句：分组函数，distinct,group by,having,union或者union all
常量视图
select 中包含子查询
join
from 一个不能更新的视图
where 子句的子查询引用了 from 子句中的表
*/
#更新
update myv1 set m = 9000 where department_id = 10;
#常量视图
create or replace view myv2
as
select 'john' name;

select * from myv2;



create or replace view myv2
as
    select 'john' name;
#更新
update myv2 set name = 'lucy';


#③select 中包含子查询
create or replace view  myv3
as
    select (select max(salary) from employees) 最高工资;

#更新
select * from myv3;
update myv3 set 最高工资 = 10000;

#④join
create or replace view myv4
as
    select last_name,department_name
    from employees e join departments d on d.department_id = e.department_id;

#更新
select * from myv4;
update myv4 set last_name='张飞' where last_name = 'whalen';
insert into myv4 values ('陈真','xxxx');

#⑤from 一个不能更新的视图
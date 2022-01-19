#DDL
/*
数据定义语言

库和表的管理

一、库的管理
创建、修改、删除
二、表的管理
创建、修改、删除

创建：create
修改：alter
删除：drop

*/

/*
一、库的管理
1、库的创建
create database 库名;
*/
#创建books库
#库的创建
create database if not exists dd;
#库的修改
#更改字符集
alter database dd character set  gbk;
#库的删除
drop database if exists dd;

/*
表的管理
1、表的创建 ★

create table 表名(
    列名  列的类型[(长度) 约束],
    列名  列的类型[(长度) 约束],
    ...
    列名  列的类型[(长度) 约束],
)
*/
create table if not exists dd(
    id int ,
    bname varchar(20),
    price double,
    authorid int,
    publishDate datetime
);
DESC dd;
create table if not exists author(
    id int,
    au_name varchar(20),
    nation varchar(10)
);
desc author;

#2.表的修改
/*
alter table 表面
add | drop | modify | change column 列名 [列类型 约束]
*/
#修改列名
alter table dd change column  publishDate pubDate DATETIME;
#修改列的类型或约束
alter  table  dd modify  column pubDate timestamp;
#添加新列
alter table  author add column annual double;
#删除列
alter table author drop column  annual;
#修改表名
alter table  author RENAME to dd_author;

#3、表的删除
drop table if exists dd_author;

show tables;

#通用的写法
drop database  if exists 旧库名;
create database  新库名;

drop table if exists 旧表名;
create table 表面();

#4、表的复制
INSERT into author values
                        (1,'村上春树','日本'),
                        (2,'莫言','中国'),
                        (3,'冯唐','中国'),
                        (4,'金庸','中国'),
                        (5,'刘慈欣','中国');

select *
from author;
#1、仅仅复制表的结果,没有数据
create  table copy_author like author;

#2、复制表的结构和数据
create table copy_author1
select * from author;

#只复制部分数据
create  table copy_author2
select id,au_name
from author
where nation = '中国';

#仅仅复制某些字段，不要数据
create table  copy_author3
select id,au_name from author where 0;


#例题1
#建表  id int(7)  name varchar(25)
create table dept1(
    id int(7),
    name varchar(25)
);
#将departments中的数据插入新表dep2中
create table dept2
select * from departments;

#将employees的数据插入新表emp1中
create  table emp1
select * from   employees;
#将last_name的长度增加到50
alter  table  emp1  modify  column  last_name varchar(50);
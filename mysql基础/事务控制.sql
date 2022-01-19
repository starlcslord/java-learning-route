#TCL
/*
transaction control language 事务控制语言
事务：
一个或一组sql语句组成一个执行单元，执行单元要么全部执行，要么
全部不执行。

张三丰  1000
郭襄 1000
update 表 set 张三丰余额 = 500 where name = '张三丰'
意外
update 表 set 郭襄的余额 = 1500 where name = '郭襄'

事务acid
atom原子性：原子性是指事务是一个不可分割的工作单位，事务中的操作
要么都发生，要么都不发生
consistency一致性：事务必须使数据库从一个一致性状态变化到另一个一致性状态
Isolation隔离性:事务的隔离性是指一个事务的执行不能被其他事务干扰，即一个
事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务不能相互
干扰。
Durability持久性:持久性是指一个事务一旦提交，它对数据库中数据的改变就是永久的
，接下来的其他操作和数据库故障不应该对其他任何影响。

事务的创建
隐式事务：事务没有明显的开始或结束的标记
比如insert,update,delete语句自动开启
delete from 表 where id = 1;事务

显式事务：事务具有明显的开启的结束标记
前提：必须先设置自动的提交功能为禁用
set autocommit = 0;
步骤一、
update 表 set 张三丰余额 = 500 where name = '张三丰'
意外
update 表 set 郭襄的余额 = 1500 where name = '郭襄'

步骤一、开启事务
set autocommit = 0;
start transaction;
步骤二、编写事务中的sql语句(select insert update delete)
语句1;
语句2；
。。。
步骤3：结束事务
commit:提交事务
rollback:回滚事务
*/
show engines;

show variables like  'autocommit';

#演示事务的使用步骤
create  table  account(
    id int primary key auto_increment,
    username varchar(20),
    balance double
);
insert into account(username, balance)
values ('张无忌',1000),('赵敏',1000);

select * from account;

#开启事务
set autocommit  = 0;
start transaction ;
#编写一组事务语句
update account set  balance = 1000 where username = '张无忌';
update account set  balance = 1000 where username = '赵敏';

#结束事务
commit ;
# ROLLBACK ;

select * from account;

select @@tx_isolation;
#设置最低的等级的隔离才会出现幻读，不可重复读，等
set session  transaction isolation level  read uncommitted ;

#rollback
#savepoint 回档点 演示savepoint的使用
set autocommit  = 0;
start transaction ;
delete  from    account where  id=25;
savepoint a;
delete  from account    where   id=28;
rollback to a;

#2、delete 和 truncate 在事务使用时的区别
#演示delete，支持回滚
set autocommit = 0;
start transaction ;
delete from account;
rollback ;

#不支持回滚
set autocommit =0;
start transaction ;
truncate table account;
rollback ;
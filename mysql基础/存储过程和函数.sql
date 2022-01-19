#存储过程和函数
/*
存储过程和函数：类似于Java中的方法
好处
1、提高代码的重用性
2、简化操作
3、减少了编译次数并且减少了和数据库服务器的连接次数，提高效率
*/
#存储过程
/*
含义：一组预编译好的sql语句的集合，理解成批处理语句
*/


#一、创建语法
create procedure 存储过程名(参数列表)
begin
    方法体（一组合法的sql语句）
end
#注意
/**
  1、参数列表包含三部分
  参数模式     参数名   参数类型
  in stuname varchar(20)
  参数模式 in,  该参数可以作为输入，有传入值
  out,         该参数可以作为输出，有返回值
  in out        该参数要传入值，并且也有返回值
  2、如果存储过程体仅仅只有一句话，begin end可以省略
  存储过程体的每个sql语句的结尾要求必须加分号。
  存储过程的结尾可以使用delimiter重新设置
  语法：
  delimiter 结束符号
  案例:
  delimiter $
 */
#二、调用语法
/*
call 存储过程名（）；

*/
drop  table admin;
create table admin(
    id int primary key auto_increment,
    username varchar(10),
    password varchar(10)
);
#1、空参列表
#案例：admin表中的插入五条记录
select * from  admin;
DELIMITER $
create procedure myp1()
begin
    insert into  admin(username,password) values ('john1','00000'),
   ('john1','00000'),
   ('john1','00000'),
    ('john1','00000');
end $

#调用
call myp1()$;

select * from admin;

#2、创建带in模式参数的存储过程
#案例1、创建存储过程实现，根据女生名、查询对应的男生信息
create  procedure myp2(in beautyName varchar(20))
begin
    select bo.*
    from boys bo
    right join beauty b on bo.id = b.boyfriend_id
    where b.name = beautyName;
end $
# #调用
# call myp2('小昭')$;

# DELIMITER $
drop procedure myp3;
#案例二 创建存储过程实现，用户是否登录成功
create procedure myp3(in username varchar(20),in passowrd varchar(20))
begin
    declare result int  default 0;#声明并初始

    select count(*) into result#赋值
    from admin
    where admin.username = username
    and admin.password = password;

    select if(result > 0,'成功','失败');#使用
end $

#调用
call myp3('张飞','8888') $;

#3、创建带out模式的存储过程
#案例1：根据女神名，返回对应的男生名
create procedure myp5(in beautyName varchar(20),out boyName varchar(20))
begin
    select bo.boyName into boyName
        from boys bo
    inner   join beauty b on bo.id = b.boyfriend_id
    where b.name = beautyName;
end $

#调用
set @bName$
call myp5('小昭',@bName)$
select @bName$
#案例二：根据女神名
drop procedure myp6;

create procedure myp6(in beautyName varchar(20),out boyName varchar(20),out usercp int) #in传入值为小昭，查询出返回值 boyname 和 userco
begin
    select bo.boyName,bo.userCP into boyName,usercp  #将查询出来的值通过Into传给返回值boyname 和 usercp
    from boys bo
    inner join beauty b on bo.id = b.boyfriend_id
    where b.name = beautyName;   #使用传入值进行查询
end $

set @bName1$
set @Cpnum$
call myp6('小昭',@bName1,@Cpnum);
select @bName1,@Cpnum$;

#4.带 in out 模式参数的存储过程
#1、传入a和b两个值，然后将a和b都翻倍并返回

create procedure myp7(inout a int ,inout  b  int)
begin
    set a = a*2;
    set b = b*2;
end $

set @ain = 10$;
set @bin = 20$;
select @ain;
select @bin;

call myp7(@ain,@bin);

#存储过程的删除，一次只能删除一个

#三、查看

show  create procedure myp7;

#函数
/*
预先编译好的sql的集合，理解成批处理
好处
1、提高代码的重用性
2、简化操作
3、减少了编译次数并且减少了和数据库服务器的连接次数，提高效率
函数：有且仅有一个返回
存储过程:不限定
*/
create function 函数名（参数列表） returns 返回类型
begin
    函数体
end
/*
注意
参数列表 包含两部分
参数名 参数类型

函数体 ：肯定会有return 语句，如果没有会报错
如果return 语句没有放在函数体的最后也不报错，但不建议
return 值；

函数体中仅有一句话，可以省略begin end

使用delimiter语句设置结束标记
*/

#二、调用语法
select 函数名（参数列表）

#案例
#1、无参又返回  返回公司的员工个数
create function myf1 returns 
select *
from beauty;
select *
from boys;

#增
#方式一
#1、插入的值类型要与列的类型一致或兼容
INSERT into beauty(id, name, sex, borndate, phone, photo, boyfriend_id)
values(13,'唐艺昕','女','1990-4-23','189888888',null,2);

#方式二
INSERT INTO beauty
set id = 19,name = '刘涛',phone = '999' ,boyfriend_id = '11';

#方式一支持插入多行，方式二不支持
insert into beauty
values (23,'唐艺昕1','女','1990-4-23','18988',NULL,2),
       (24,'唐艺昕2','女','1991-4-23','18777',NULL,2);

#方式一支持子查询，方式二不支持
INSERT into beauty(id,name,phone)
select 26,'宋茜','11809866';

#改
/*
1、修改单表的记录 ☆
语法:
update 表名   第一步
set 列 = 新值,列 = 新值,列 = 新值   第三步
where 筛选条件;  第二步


2、修改多表的记录【补充】

语法：
sql92语法：
update 表1 别名 ,表2 别名
set 列 = 值,....
where 连接条件
and 筛选条件;

sql99语法：
update 表1 别名
inner | left | right join 表2 别名
on 连接条件
set 列 = 值,.....
where 筛选条件;
*/


#1、修改beauty表中的姓唐的女生的电话为123456789
update beauty
set  phone = '123456789'
where name like '唐%';

#2.修改boys表中的id号为2的名称改为张飞，魅力值10
update boys
set boyname = '张飞' ,usercp = '10'
where id = 2;

#修改多表的记录
#案例1：修改张无忌的女朋友的电话号为987654321
update boys
left join beauty
on boys.id  =  beauty.boyfriend_id
set phone = 987654321
where boyname = '张无忌';

#案例2：修改没有男朋友的女生的男朋友为2号
update beauty
left join boys
on boyfriend_id = boys.id
set  boyfriend_id = 2
where boys.id is null;

#三、删除
/*
1、单标的删除【☆】
delete from  表名 where 筛选条件
2、多表的删除【补充】
sql92语法:
delete 表1的别名,表2的别名    #删哪个表的，就写哪个表
from 表1 别名，表2 别名
where 连接条件
and 筛选条件

sql99语法:
delete 表1的别名,表2的别名 #删哪个表的，就写哪个表
from 表1 别名
left | right | inner join 表2 别名 on 连接条件
where 筛选条件


方式二：truncate
语法： truncate table 表名;
*/
#方式一 、delete
#1、单表的删除
#案例1、删除手机尾号为9的女生信息
delete from beauty where phone like  '%9';

#2、多表的删除
#案例2、删除张无忌的女朋友信息
delete beauty
from boys
left join beauty on beauty.boyfriend_id = boys.id
where boyname = '张无忌';

#方式二、truncate语句
#不允许加where
truncate table boys;

#delete pk truncate【面试题】
/*
1、delete可以加where条件，truncate不能加
2、truncate删除，效率高一丢丢
3、假如要删除的表中的有自增加列，如果用delete删除，在插入数据，则从断点开始
truncate从1开始
4、truncate没有返回值，delete删除有返回值
5、truncate删除不能回滚,delete可以回滚
*/

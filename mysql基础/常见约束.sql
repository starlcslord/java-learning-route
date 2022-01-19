#常见约束
/*
含义：一种限制，用于限制表中的数据，为了保证表中的数据的准确和可靠
分类：六大约束
    not null : 非空，用于保证该字段的值不能为空，比如姓名，学号等
    DEFAULT:默认，用于保证该字段有的默认值，比如性别
    PRIMARY KEY:主键，用于保证该字段的值具有唯一性，并且非空
    比如学号、员工编号等
    UNIQUE:唯一、用于保证该字段的值具有唯一值，可以为空，比如座位号
    CHECK:检查约束【mysql中不支持】
    FOREIGN KEY:外键，用于限制两个表的关系，用于保证该字段的值必须来自于主表的关联列表
    的值

CREATE TABLE 表名(
    字段名  字段类型  列级约束,
    字段名  字段类型,
    表级约束
)
*/
#一、创建表时添加约束
#1、添加列级约束
/*
语法
直接在字段名和类型后面追加约束类型即可
支持：默认，非空，主键。唯一

主键和唯一的对比：
                保证唯一性       是否允许为空      一个表中可以有多少个  是否允许组合
        主键      √                  ×               至多有1个           允许，但不推荐
        唯一      √                  √               可以有多个          允许，但不推荐

primary key(id,stuname) id和stuname都作为主键
unique  key(id,stuname) id和stuname都作为唯一值

外键
    1、要求在从表设置外键关系
    2、从表的外键列的类型和主表的关联列的类型要求一致或兼容
    3、主表的关联列必须是一个Key,一般是主键或唯一
    4、插入数据时，先插入主表，再插入从表
    删除数据时，先删除从表，再删除主表
*/
create table stuinfo(
    id int primary key ,#主键
    stuname varchar(20) not null ,#非空
    gender char(1) check ( gender = '男' or gender = '女'),#检查
    seat int unique,#唯一
    age int default 18,#默认约束
    majorid INT  REFERENCES stumajor(id)
);
create table  stumajor(
    id int primary key,
    majorname varchar(20)
);
#查看索引
show index  from stuinfo;

#添加表级约束
/*
语法：在各个字段的最下面
【constraint 约束名】 约束类型(字段名)
*/
DROP  table  if exists stuinfo;

create table stuinfo(
    id int,
    stuname varchar(20),
    gender char(1),
    seat int,
    age int,
    majorid int,
    #列级约束，约束名变成别名
    constraint pk primary key(id),#主键，pk作为别名
    constraint uq unique (seat),
    constraint ck check ( gender = '男' or gender = '女'),
    constraint rf foreign key(majorid) references stumajor(id)
    #unique(seat)
);




#二、修改表时添加约束
/*
1、添加列级约束
alter table 表名 modify column 字段名 字段类型 新约束;
2、添加表级约束
alter table 表名 add column   字段名 字段类型    新约束
*/
DROP table if exists  stuinfo;
create table stuinfo(
    id int,
    stuname varchar(20),
    gender char(1),
    age int,
    seat int,
    majorid int
);
#列级约束
#1、添加唯一约束
alter table stuinfo modify column stuname varchar(30) unique ;
#2、添加默认约束
alter table stuinfo modify column age int default 18;
#3、添加外键约束
alter table stuinfo modify column majorid int references stumajor(id);
#4、添加主键约束
alter table stuinfo modify column  id int primary key ;
#表级约束
alter table stuinfo add primary key (id);

#删除约束
#1、删除主键约束
alter table  stuinfo drop primary key ;
#2、删除默认约束
alter table stuinfo modify  column  age int;
#3、删除外键
alter table stuinfo drop  foreign key majorid;

#标识列
/*
又称自增长列
含义：可以不要手动的插入值，系统提供默认的序列值

*/

#一、创建表时设置标识列
DROP table if exists  tab_identity;
create table tab_identity(
    id int primary key  auto_increment,
    name varchar(20)
);
truncate tab_identity;
insert into tab_identity values (null,'john');
select * from tab_identity;
#常见数据类型
/*
数值型:
        整型
        小数：
                定点数
                浮点数
字符型:
        较短的文本:char,varchar
        较长的文本:text,blob(较长的二进制数据)
日期型
*/


#1、整型
/*
分类：
tinyint、smallint,mediumint,int/integer,bigint
    1byte       2       3         4      8
    8bit       16bit   24bit     32bit  64bit
特点：
①如果不设置无符号还是有符号，默认是有符号，如果想要设置无符号，需要
添加unsigned
②out of range超过范围，自动填入临界值，当前版本不支持自动填入
③如果不设置长度，会有默认的长度

*/
#1、如何设置无符号和有符号
drop table tab_int;

create table tab_int(
    t1 int,
    t2 int unsigned
);
#表结构
DESC  tab_int;
#报错
insert  into  tab_int values (-123456,-123456);
#因为unsigned最小到0

select *
from tab_int;

insert into tab_int values (111111111111111111,0);

#二、小数
/*
浮点型
        单浮点 float(m,d)
        双浮点 double(m,d)
        定点  dec(m,d) 最大取值范围与double相同，但是有效取值有m和d决定
特点：
1、m,d
m代表外加小数部位
d代表的小数部位
2、m和d可以省略
dec默认m为10，D默认为0
如果是float和double,则会根据插入的数值的精度来决定精度
3、定点型精度较高，如果要使用插入数值的精度较高如货币运算等考虑使用
*/
DROP table tab_float;
create  table  tab_float(
    f1 float(5,2),
    f2 double(5,2),
    f3 decimal(5,2)
);
desc tab_float;
select *    from tab_float;
insert into tab_float values (123.45,123.45,123.45);
insert into tab_float values (123.456,123.456,123.456);
insert into tab_float values (123.4,123.4,123.4);
insert into tab_float values (1223.45,1123.45,1423.45);

#原则
/*
所选择的尽量越简单越好，越实用越好
*/

#三、字符型
/*
较短文本
char
varchar

较长文本
text
blob(较大的二进制)

特点
        写法        M的意思                      特点              空间耗费        效率
char  char(M)    最大的字符数，可以省略默认为1     固定长度的字符     比较耗费        高
varchar varchar(M)  最大的字符数，不能省略        可变长度的字符     比较节省        低

其他
binary和varbinary
enum
set
*/
select * from tab_char;
create table tab_char(
    c1 enum('a','b','c')
);
INSERT into tab_char values('a');
INSERT into tab_char values('b');
INSERT into tab_char values('c');
#枚举中不存在m，插入不进
INSERT into tab_char values('m');
INSERT into tab_char values('A');

create table tab_set(
    s1 set('a','b','c','d')
);
#集合中找不到f,插入不了
INSERT into tab_set values ('a','b','f');

#四、日期型
/*
分类
date    只保存日期
time    只保存时间
year    只保存年
datetime 保存日期+时间
timestamp保存日期+时间
日期类型    字节      最小值                 最大值
date        4      1000-01-01           9999-12-31
datetime    8      1000-01-01 00:00:00  9999-12-31 23:59:59
timestamp   4      19700101080001       2038年的某个时刻
time        3      -838:59:59           838:59:59
year        1      1901                 2155
*/

create  table  tab_date(
    t1 datetime,
    t2 timestamp
);
select * from tab_date;
INSERT INTO tab_date values (now(),now());
show variables  like  'time_zone';
set time_zone='+9:00' #改成东九区



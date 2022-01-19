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

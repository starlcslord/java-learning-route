#进阶9
/*
  union 联合 合并 ： 将多条查询语句的结果合并一个结果
  语法：
  查询语句1
  union
  查询语句2
  union
  ...

  应用场景：
  要查询的结果来自多个表，且多个表没有直接的连接关系，但查询的信息一致时

  特点：
  列数一致
  列名按照第一个的列名，所以要求多条查询语句的查询的每一列的类型和顺序最好一致
  默认去重，不需要去重则 union all
 */
#引入的案例：查询部门编号>90或邮箱包含a的员工信息
select * from employees where email like '%a%' or department_id>90;
#等价于
select * from employees where email like '%a%'
union
select * from employees where department_id>90;

#案例：查询中国用户中性别男性的信息以及外国用户中男性的信息
select *
from t_ca
where t_ca.csex = '男'
union
select *
from t_ua
where tGender = 'male'
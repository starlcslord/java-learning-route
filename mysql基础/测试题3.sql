#1、查询所有学生邮箱的用户名（@前面的字符）
select substr(email,1,instr(email,'@')-1)
from student;
#2、查询男生和女生的个数
select count(*) 人数,sex
from student
group by sex;
#3、查询年龄>25的所有学生的姓名和年纪名称
select studentname,majorname,(year(now())-year(borndate))
from student,major
where (year(now())-year(borndate))>25 and student.majorid = major.majorid
order by major.majorid;
#4.查询哪个年纪的学生最小年龄大于25岁
select min(year(now())-year(borndate)),studentname
from student
group by majorid
HAVING min(year(now())-year(borndate))<25;
# select  year(now())

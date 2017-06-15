# �ļ�SQL


## һ�����
��Ҫ�����ճ���ʽ���ı������������ָ����ļ���JSON�ļ����Լ��ָ���JSON�����ļ�����Ŀû�ж���Jar�������������࣬����ǿ�󡣿���Ϊ�ճ�����С����ʹ�á�֧�ֶ�����ʽ�����ݲɼ���ʽ����JSON���ָ���������ָ����Զ�JavaScript�ָ����Զ�Format.class�ָ��ȡ�

![��ʾ][1]

## ��������ṹ
![����ṹ][2]

## ����Ŀ¼����
![Ŀ¼�ṹ][3]

## �ġ�ʹ��˵��
�����ļ��������£�

![ʹ��˵��][4]

## �塢������
```sql
## �ָ���
create table log.txt (id,name,ip,segment,num) fmt |;
## JSON��ʽ
create table log.json (id,name,ip,segment,num) fmt json;
```
![������][5]

## �������±�
```sql
## Java����ȡ
update table log.txt (id,name,ip,segment,num) fmt format.class;
desc log.txt;
select * from log.txt;
```
![���ı�][6]

```sql
## JavaScript�ű���ȡ
update table log.txt (id,name,ip,segment,num) fmt format.js;
desc log.txt;
select * from log.txt;
```
![���ı�][7]

```sql
## ������ȡ
update table log.txt (id,name,ip,segment,num) fmt ~(.*?)|(.*?)|(.*?)|(.*?)|(.*);
desc log.txt more;
select * from log.txt;
```
![���ı�][8]

## �ߡ��򵥲�ѯ
```sql
# �򵥲�ѯ
select name,ip from log.txt;
select name,ip from log.json;
select name,ip from log.{txt,json}; 
```
![�򵥲�ѯ][9]
```sql
# JSON��ȡ
select name,json_path(segment,$.service) from log.txt;
select name,json_path(segment,$.service) from log.json;
select name,json_path(segment,$.service) from log.{json,txt};
```
![�򵥲�ѯ][10]

## �ˡ�������ѯ
```sql
select * from log.txt  where name='taobao' or name='ctrip';
select * from log.json where name='taobao' or name='ctrip';
select * from log.{txt,json} where name='taobao' or name='ctrip';
```
![������ѯ][12]

## �š��ۺϲ�ѯ
```sql
select name,sum(num) as total,avg(num),max(num),min(num),count(num) from log.txt  group by name;
select name,sum(num) as total,avg(num),max(num),min(num),count(num) from log.json group by name;
select name,sum(num) as total,avg(num),max(num),min(num),count(num) from log.{txt,json} group by name;
```
![�ۺϲ�ѯ][13]

## ʮ��������
```sql
select name,ip,num from log.txt into tmp.tb;
select name,ip,num from tmp.tb where name='taobao';
```
![����ṹ][14]

## ʮһ��ɾ����
```sql
drop table log.txt;
drop table log.json;
```


[1]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/3.gif
[2]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/1.png
[3]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/2.png
[4]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/4.png
[5]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/5.png
[6]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/6.png
[7]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/7.png
[8]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/8.png
[9]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/9.png
[10]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/10.png
[11]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/11.png
[12]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/12.png
[13]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/13.png
[14]: https://raw.githubusercontent.com/mircode/file-sql/master/dist/doc/imgs/14.png
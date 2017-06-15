# �ļ�SQL


## ���
��Ҫ�����ճ���ʽ���ı������������ָ����ļ���JSON�ļ����Լ��ָ���JSON�����ļ�����Ŀû�ж���Jar�������������࣬����ǿ�󡣿���Ϊ�ճ�����С����ʹ�á�֧�ֶ�����ʽ�����ݲɼ���ʽ����JSON���ָ���������ָ����Զ�JavaScript�ָ����Զ�Format.class�ָ��ȡ�

## ����ṹ


## Ŀ¼����

## ʹ��˵��

## ������
```sql
## �ָ���
create table log.txt (id,name,ip,segment,num) fmt |;
## JSON��ʽ
create table log.json (id,name,ip,segment,num) fmt json;
```

## ���±�
```sql
## Java����ȡ
update table log.txt (id,name,ip,segment,num) fmt format.class;
desc log.txt;
select * from log.txt;
```

```sql
## JavaScript�ű���ȡ
update table log.txt (id,name,ip,segment,num) fmt format.js;
desc log.txt;
select * from log.txt;
```

```sql
## ������ȡ
update table log.txt (id,name,ip,segment,num) fmt ~(.*?)|(.*?)|(.*?)|(.*?)|(.*);
desc log.txt more;
select * from log.txt;
```
## �򵥲�ѯ
```sql
# �򵥲�ѯ
select name,ip from log.txt;
select name,ip from log.json;
select name,ip from log.{txt,json}; 

# JSON��ȡ
select name,json_path(segment,$.service) from log.txt;
select name,json_path(segment,$.service) from log.json;
select name,json_path(segment,$.service) from log.{json,txt};

```
## ������ѯ
```sql
select * from log.txt  where name='taobao' or name='ctrip';
select * from log.json where name='taobao' or name='ctrip';
select * from log.{txt,json} where name='taobao' or name='ctrip';
```
## �ۺϲ�ѯ
```sql
select name,sum(num) as total,avg(num),max(num),min(num),count(num) from log.txt  group by name;
select name,sum(num) as total,avg(num),max(num),min(num),count(num) from log.json group by name;
select name,sum(num) as total,avg(num),max(num),min(num),count(num) from log.{txt,json} group by name;
```
## ������
```sql
select name,ip,num from log.txt into tmp.tb;
select name,ip,num from tmp.tb where name='taobao';
```

## ɾ����
```sql
drop table log.txt;
drop table log.json;
```


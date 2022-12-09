delimiter //

create procedure sp_Staffs_insert(in name varchar(200),in image varchar(500),in staffusername varchar(10), in password varchar(50),in role bit,in email varchar(50),
									in birthday date, in address varchar(500), in phone varchar(10),in salary double, in departid varchar(10),in username varchar(10))
begin
	declare id int(11);
    declare staffid varchar(15);
	declare sfrole varchar(20);
    set sfrole = (  select case
					when role = 1 then 'Trưởng phòng'
                    when role = 0 then 'Nhân Viên'
					end);
	insert into staffs(staffname,image,username,password,role,email,birthday,address,phone,salary,departid)
    values(name,image,staffusername,password,role,email,birthday,address,phone,salary,departid);
    set id = (select ordernumber from staffs t3 order by ordernumber desc limit 1);
    set staffid = concat('Staff-0',id);
    update staffs t3 
    set staffid = staffid
    where t3.ordernumber=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Staffs',curdate(),
				concat('ID: ',staffid,' Name: ',name,' Username: ',staffusername,' Password: ',password,' Role: ',sfrole,' Email: ',email,' Birthday: ',birthday,' Address: ',address,
								' Phone: ',phone,' Salary: ',salary,' DepartID: ',departid),
                username);
end;//

delimiter //
create procedure sp_Staffs_update(in id int(11),in name varchar(200),in image varchar(500),in staffusername varchar(10), in password varchar(50),in role bit,
								in email varchar(50),in birthday date, in address varchar(500), in phone varchar(10),in salary double, in departid varchar(10),in username varchar(10))
begin
	declare sfname varchar(200);
    declare sfimage varchar(500);
    declare sfpassword varchar(50);
    declare sfroleb varchar(20);
    declare sfrolea varchar(20);
    declare sfemail varchar(50);
    declare sfbirthday date;
    declare sfaddress varchar(500);
    declare sfphone varchar(10);
    declare sfsalary double;
    declare sfdepartid varchar(10);
    set sfname = (select staffs.staffname from staffs where staffs.staffid= id);
    set sfimage = (select staffs.Image from staffs where staffs.staffid= id);
    set sfpassword = (select staffs.Password from staffs where staffs.staffid= id);
    set sfroleb = (select case
					when staffs.role = 1 then 'Trưởng phòng'
                    when staffs.role = 0 then 'Nhân Viên'
					end
				from staffs where staffs.staffid= id);
	set sfrolea = (  select case
					when role = 1 then 'Trưởng phòng'
                    when role = 0 then 'Nhân Viên'
					end);
	set sfemail = (select staffs.email from staffs where staffs.staffid= id);
    set sfbirthday = (select staffs.birthday from staffs where staffs.staffid= id);
    set sfaddress = (select staffs.address from staffs where staffs.staffid= id);
    set sfphone = (select staffs.phone from staffs where staffs.staffid= id);
    set sfsalary = (select staffs.salary from staffs where staffs.staffid= id);
    set sfdepartid = (select staffs.departid from staffs where staffs.staffid= id);
    update staffs a
    set a.staffname = name, a.Image=image, a.password = password, a.role = role, a.email=email, 
		a.birthday= birthday, a.address = address, a.phone=phone, a.salary=salary, a.departid=departid
	where a.staffid= id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Staffs',curdate(),
				concat('Update from Name: ',sfname,' Username: ',staffusername,' Password: ',sfpassword,' Role: ',sfroleb,' Email: ',sfemail,' Birthday: ',sfbirthday,
						' Address: ',sfaddress,' Phone: ',sfphone,' Salary: ',sfsalary,' DepartID: ',sfdepartid,' To ',
                        'Name: ',name,' Username: ',staffusername,' Password: ',password,' Role: ',sfrolea,' Email: ',email,' Birthday: ',birthday,
						' Address: ',address,' Phone: ',phone,' Salary: ',salary,' DepartID: ',departid),
                username);
end;//


delimiter //
create procedure sp_Staffs_delLogic(in id int(11),in username varchar(10))
begin
	update staffs
    set isDelete =0
    where staffs.staffid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete Staffs',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Staffs_restore(in id int(11),in username varchar(10))

begin
	update staffs
    set isDelete =1
    where staffs.staffid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore Staffs',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Staffs_delPhysic(in id int(11), in username varchar(10))
begin
	declare sfname varchar(200);
    declare sfimage varchar(500);
    declare sfusername varchar(20);
    declare sfpassword varchar(50);
    declare sfrole varchar(20);
    declare sfemail varchar(50);
    declare sfbirthday date;
    declare sfaddress varchar(500);
    declare sfphone varchar(10);
    declare sfsalary double;
    declare sfdepartid varchar(10);
    set sfname = (select staffs.staffname from staffs where staffs.staffid= id);
    set sfimage = (select staffs.Image from staffs where staffs.staffid= id);
    set sfusername = (select staffs.username from staffs where staffs.staffid=id);
    set sfpassword = (select staffs.Password from staffs where staffs.staffid= id);
    set sfrole = (select case
					when staffs.role = 1 then 'Trưởng phòng'
                    when staffs.role = 0 then 'Nhân Viên'
					end
				from staffs where staffs.staffid= id);
	set sfemail = (select staffs.email from staffs where staffs.staffid= id);
    set sfbirthday = (select staffs.birthday from staffs where staffs.staffid= id);
    set sfaddress = (select staffs.address from staffs where staffs.staffid= id);
    set sfphone = (select staffs.phone from staffs where staffs.staffid= id);
    set sfsalary = (select staffs.salary from staffs where staffs.staffid= id);
    set sfdepartid = (select staffs.departid from staffs where staffs.staffid= id);
	delete from staffs
    where staffs.staffid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Staffs',curdate(),
				concat('ID: ',id,' Name: ',sfname,' Username: ',sfusername,' Password: ',sfpassword,
						' Role: ',sfrole,' Email:',sfemail,' Birthday: ',sfbirthday,' Address: ',sfaddress,' Phone: ',sfphone,
						' Salary: ',sfsalary,' DepartID: ',sfdepartid),
                username);
end;//

DELIMITER // 
DROP PROCEDURE IF EXISTS `sp_Staffs_login` //
CREATE PROCEDURE `sp_Staffs_login` (in username varchar(20))
begin
	select	
		t1.username,
        t1.password,
		case
        when t1.role = 0 then 'USER'
        when t1.role = 1 then 'ADMIN'
        END as role,
        t2.departid as depart_id
    FROM staffs t1
    INNER JOIN departs t2 on t1.departid=t2.departid
    where t1.username=username;
end;

delimiter //
create procedure sp_Staffs_get1()
begin
	select staffid,staffname,image,username,password,role,email,Birthday,address,phone,salary,departid from staffs where isDelete =1;
end;//

delimiter //
create procedure sp_Staffs_get0()
begin
	select staffid,staffname,image,username,password,role,email,Birthday,address,phone,salary,departid from staffs where isDelete =0;
end;//

delimiter //
create procedure sp_Staffs_findID(in id int(10))
begin
	select staffid,staffname,image,username,password,role,email,Birthday,address,phone,salary,departid from staffs where staffid like concat('%',id,'%') and  isDelete =1;
end;
//
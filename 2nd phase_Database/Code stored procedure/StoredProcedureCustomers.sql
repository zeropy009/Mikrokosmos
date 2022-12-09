delimiter //

create procedure sp_Customers_insert(in name varchar(200),in image varchar(500),in email varchar(50),
										in address varchar(500),in phone varchar(10),in password varchar(30),in username varchar(10))
begin
	declare id int(11);
    declare customerid varchar(15);
	insert into Customers(customername,image,email,address,phone,password)
	values(name,image,email,address,phone,password);
    set id = (select ordernumber from customers t3 order by ordernumber desc limit 1);
    set customerid = concat('Customer-0',id);
    update customers t3 
    set t3.customerid = customerid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Customers',curdate(),
			concat('ID:',customerid,' Name: ',name,' Address: ',address,' Phone: ',phone,' Email: ',email,' Password:',password,' Image: ',image),
			username);
end;//

delimiter //
create procedure sp_Customers_update(in id int(11),in name varchar(200),in image varchar(500),in email varchar(50),
										in address varchar(500),in phone varchar(10),in password varchar(30),in username varchar(10))
begin
	declare csname varchar(200);
    declare csimage varchar(500);
    declare csemail varchar(50);
    declare csaddress varchar(500);
    declare csphone varchar(10);
    declare cspassword varchar(30);
    set csname = ( select customers.customername from customers where customers.customerid = id);
    set csimage = ( select customers.image from customers where customers.customerid = id);
    set csemail = (select customers.email from customers where customers.customerid=id);
    set csaddress = ( select customers.address from customers where customers.customerid = id);
    set csphone = ( select customers.phone from customers where customers.customerid = id);
    set cspassword = ( select customers.password from customers where customers.customerid=id);
    update customers a
    set a.customername= name,a.email=email, a.image=image, a.address=address, a.phone=phone, a.password=password
    where a.customerid = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Customers',curdate(),
				concat('Update from ID:',id,' Name: ',csname,' Address: ',csaddress,' Phone: ',csphone,' Email: ',csemail,' Password:',cspassword,' Image: ',csimage,' To ',
							'Name: ',name,' Address: ',address,' Phone: ',phone,' Email: ',email,' Password:',password,' Image: ',image),
				username);
end;//

delimiter //
create procedure sp_Customers_delLogic(in id int(11),in username varchar(10))
begin
	update Customers
    set isDelete = 0
    where customers.customerid = id;
    insert into logs(logname,Date,note,referenceID)
	values ('Logical Delete Customers',curdate(),
			concat('ID:',id),
			username);
end;//

delimiter //
create procedure sp_Customers_restore(in id int(11),in username varchar(10))
begin
	update Customers
    set isDelete = 1
    where customers.customerid = id;
    insert into logs(logname,Date,note,referenceID)
	values ('Restore Customers',curdate(),
			concat('ID:',id),
			username);
end;//

delimiter //
create procedure sp_Customers_delPhysic(in id int(11),in username varchar(10))
begin
	declare csname varchar(200);
    declare csimage varchar(500);
    declare csemail varchar(50);
    declare csaddress varchar(500);
    declare csphone varchar(10);
    declare cspassword varchar(30);
    set csname = ( select customers.customername from customers where customers.customerid = id);
    set csimage = ( select customers.image from customers where customers.customerid = id);
    set csemail = (select customers.email from customers where customers.customerid=id);
    set csaddress = ( select customers.address from customers where customers.customerid = id);
    set csphone = ( select customers.phone from customers where customers.customerid = id);
    set cspassword = ( select customers.password from customers where customers.customerid=id);
	delete from Customers
    where customers.customerid = id;
    insert into logs(logname,Date,note,referenceID)
	values ('Physical Delete Customers',curdate(),
			concat('ID:',id,'Name: ',csname,' Address: ',csaddress,' Phone: ',csphone,' Email: ',csemail,' Password:',cspassword,' Image: ',csimage),
			username);
end;//

delimiter //
create procedure sp_Customers_get0()
begin
	select customerid,customername,image,email,address,phone,password from customers where isDelete =0;
end;//

delimiter //
create procedure sp_Customers_get1()
begin
	select customerid,customername,image,email,address,phone,password from customers where isDelete =1;
end;//

delimiter //
create procedure sp_Customers_findID(in id int(11)) 
begin
	select customerid,customername,image,email,address,phone,password from customers where customerid =id and isDelete =1;
end;
//
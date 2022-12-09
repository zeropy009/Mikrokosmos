	/*        Suppliers          */

	-- Procedure insert
	drop procedure if exists sp_Suppliers_insert;
	delimiter //
	create procedure sp_Suppliers_insert(in name varchar(200), in address varchar(500), 
										in phone varchar(10), in email varchar(50),
										in username varchar(10))
	begin
		declare id varchar(15);
		declare spid varchar(15);
		insert into suppliers(suppliername,address,phone,email) values (name,address,phone,email);
		set id = (select ordernumber from suppliers t3 order by ordernumber desc limit 1);
		set spid = concat('Supplier-0',id);
		update suppliers t3 
		set supplierid = spid
		where t3.ordernumber=id;
		insert into logs(logname,Date,note,referenceID)
		values ('Insert Suppliers',curdate(),
				concat('ID: ',spid,' Name: ',name,' Address: ',address,' Phone: ',phone,' Email: ',email),
				username);
	end;//

	delimiter //
	-- Procedure Update
	create  procedure sp_Suppliers_update(in id varchar(10),in name varchar(200),
										in address varchar(500), in phone varchar(10), in email varchar(50),
										in username varchar(10))
	begin
		declare spname varchar(200);
		declare spaddress varchar(500);
		declare spphone varchar(10);
		declare spemail varchar(50);
		set spname = (select suppliername from suppliers where supplierid = id);
		set spaddress = ( select address from suppliers where supplierid = id);
		set spphone = (select phone from suppliers where supplierid = id);
		set spemail = (select email from suppliers where supplierid = id);
		update Suppliers a
		set a.suppliername = name, a.address= address, a.phone = phone, a.email=email
		where a.supplierid = id;
		insert into logs(logname,Date,note,referenceID)
		values ('Update Suppliers',curdate(),
				concat('Update From ID: ',id,' Name: ',spname,' Address: ',spaddress,' Phone: ',spphone,' Email: ',spemail,' To ',
				' Name: ',name,' Address: ',address,' Phone: ',phone,' Email: ',email),
				username);
	end; //

	delimiter //
	create  procedure sp_Suppliers_delLogic(in id varchar(10),in username varchar(10))
	begin
		update suppliers
		set isDelete=0
		where supplierid = id;
		insert into logs(logname,date,note,referenceID)
			values ('Delete Logic Supplier',curdate(),
			concat('ID: ',id),
			username);
	end; //

	delimiter //
	create  procedure sp_Suppliers_Restore(in id varchar(10),in username varchar(10))
	begin
		update suppliers
		set isDelete=1
		where supplierid = id;
		insert into logs(logname,date,note,referenceID)
			values ('Restore Supplier',curdate(),
			concat('ID: ',id),
			username);
	end;//

	delimiter //
	create  procedure sp_Suppliers_delPhysic(in id varchar(10),in username varchar(10))
	begin
		declare spname varchar(200);
		declare spaddress varchar(500);
		declare spphone varchar(10);
		declare spemail varchar(50);
		set spname = (select suppliername from suppliers where supplierid = id);
		set spaddress = ( select address from suppliers where supplierid = id);
		set spphone = (select phone from suppliers where supplierid = id);
		set spemail = (select email from suppliers where supplierid = id);
		delete from suppliers where supplierid = id;
		insert into logs(logname,date,note,referenceID)
			values ('Delete Physic Supplier',curdate(),
					concat('ID: ',id,' Name: ',spname,' Address: ',spaddress,' Phone: ',spphone,' Email: ',spemail),
					username);
	end; //

	delimiter //
	create procedure sp_Suppliers_get1()
	begin
		select supplierid,SupplierName,address,phone,email from suppliers  where isDelete=1;
	end; //

	delimiter //
	create procedure sp_Suppliers_get0()
	begin
		select supplierid,SupplierName,address,phone,email from suppliers where isDelete=0;
	end;//

	delimiter //
	create procedure sp_Suppliers_findID(in id varchar(10))
	begin
		select supplierid,SupplierName,address,phone,email from suppliers where supplierid like concat('%',id,'%')  and isDelete=1;
	end;
	//




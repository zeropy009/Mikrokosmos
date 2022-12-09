delimiter //
create procedure sp_Authors_insert(in name varchar(200),in username varchar(10))
begin
	declare id int(11);
    declare atid varchar(15);
	insert into authors (authorname)
    values (name);
    set id = (select ordernumber from authors t3 order by ordernumber desc limit 1);
    set atid = concat('Author-0',id);
    update authors t3 
    set authorid = atid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Producers',curdate(),
			concat('ID: ',atid,' Name: ',name),
				username);
end;//

delimiter //
create procedure sp_Authors_update(in id varchar(20),in name varchar(200),in username varchar(10))
begin
    declare atname varchar(200);
    set atname = (select authors.AuthorName from authors where authors.authorid=id);
    update authors a
    set a.authorname = name
    where a.AuthorID = id;
	insert into logs(logname,Date,note,referenceID)
	values ('Update Authors',curdate(),
		concat('Update from ID: ',id,' Name: ',atname,' To',
				' Name: ',name,' Nation: '),
		username);
end;//

delimiter //
create procedure sp_Authors_delLogic(in id varchar(20),in username varchar(10))
begin
    update authors
    set isDelete=0
    where authors.AuthorID = id;
    insert into logs(logname,date,note,referenceID)
        values ('Logical Delete Author',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Authors_Restore(in id varchar(20),in username varchar(10))
begin
    update authors
    set isDelete=1
    where authors.AuthorID = id;
    insert into logs(logname,date,note,referenceID)
        values ('Restore Author',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Authors_delPhysic(in id varchar(20),in username varchar(10))
begin
	declare atname varchar(200);
    set atname = (select authors.AuthorName from authors where authors.authorid=id);
    delete from authors
    where authorid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Physical Delete Author',curdate(),
        concat('ID: ',id,' Name: ',atname),
        username);
end; //

delimiter //
create procedure sp_Authors_get1()
begin
	select authorid,authorname from Authors where isDelete=1;
end;//

delimiter //
create procedure sp_Authors_get0()
begin
	select authorid,authorname from Authors where isDelete=0;
end;//

delimiter //
create procedure sp_Authors_findID(in id varchar(20))
begin
	select authorid,authorname from Authors where authorid like concat('%',id,'%')  and isDelete=1;
end;

//

select * from books;

	/*        Suppliers          */

	-- Procedure insert
	drop procedure if exists sp_Suppliers_insert;
	delimiter //
	create procedure sp_Suppliers_insert(in name varchar(200), in address varchar(500), 
										in phone varchar(12), in email varchar(50),
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
	create  procedure sp_Suppliers_update(in id varchar(20),in name varchar(200),
										in address varchar(500), in phone varchar(12), in email varchar(50),
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
	create  procedure sp_Suppliers_Restore(in id varchar(20),in username varchar(10))
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
	create  procedure sp_Suppliers_delPhysic(in id varchar(20),in username varchar(10))
	begin
		declare spname varchar(200);
		declare spaddress varchar(500);
		declare spphone varchar(12);
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
	create procedure sp_Suppliers_findID(in id varchar(20))
	begin
		select supplierid,SupplierName,address,phone,email from suppliers where supplierid like concat('%',id,'%')  and isDelete=1;
	end;
	//



delimiter //
create procedure sp_Books_insert(in title varchar(200),in image varchar(500),
									in description varchar(500),in categoryid varchar(20),in authorid varchar(15),in username varchar(10))
begin
	declare id int(11);
    declare bkid varchar(15);
	insert into books(booktitle,amount,image,description,categoryid,authorid)
    values(title,0,image,description,categoryid,authorid);
    set id = (select ordernumber from books t3 order by ordernumber desc limit 1);
    set bkid = concat('Book-0',id);
    update books t3 
    set bookid = bkid
    where t3.ordernumber=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Books',curdate(),
				concat('ID: ',bkid,' Title: ',title,' Amount: ',0,' Image: ',Image,' Description: ',description,
						' CategoryID: ',categoryid,' AuthorID: ',authorid),
                username);
end;//

delimiter //
create procedure sp_Books_update(in id varchar(20),in title varchar(200),in amount int(11),in image varchar(500),
									in description varchar(500),in categoryid varchar(20),in authorid varchar(15),in username varchar(10))
begin
	declare bname varchar(200);
    declare bimage varchar(500);
    declare bdescription varchar(500);
    declare bcategoryid varchar(10);
    declare bauthorid varchar(10);
    set bname = (select books.booktitle from books where books.bookid=id);
    set bimage = (select books.image from books where books.bookid=id);
    set bdescription = (select books.description from books where books.bookid=id);
    set bcategoryid = (select books.categoryid from books where books.bookid=id);
    set bauthorid = (select books.authorid from books where books.bookid=id);
	update books a
    set a.booktitle = title, a.image = image, a.description= description,
		a.categoryid=categoryid, a.authorid=authorid
	where a.bookid=id ;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Books',curdate(),
				concat('Update from ID: ',id,' Name: ',bname,' Amount: ',amount,' Image: ',bimage,' Description: ',bdescription,
						' CategoryID: ',bcategoryid,' AuthorID: ',bauthorid,' To ',
                        'ID: ',id,' Name: ',title,' Amount: ',amount,' Image: ',Image,' Description: ',description,
						' CategoryID: ',categoryid,' AuthorID: ',authorid),
                username);
end;//

delimiter //
create procedure sp_Books_delLogic(in id varchar(20),in username varchar(10))
begin
	update books
    set isDelete=0
    where books.bookid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete Books',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Books_restore(in id varchaR(20),in username varchar(10))
begin
	update books
    set isDelete=1
    where books.bookid = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore Books',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Books_delPhysic(in id varchar(20),in username varchar(10))
begin
	declare bname varchar(200);
    declare bimage varchar(500);
    declare bamount int(11);
    declare bdescription varchar(500);
    declare bcategoryid varchar(20);
    declare bauthorid varchar(15);
    set bname = (select books.booktitle from books where books.bookid=id);
    set bimage = (select books.image from books where books.bookid=id);
    set bamount = (select books.amount from books where books.BookID=id);
    set bdescription = (select books.description from books where books.bookid=id);
    set bcategoryid = (select books.categoryid from books where books.bookid=id);
    set bauthorid = (select books.authorid from books where books.bookid=id);
	delete from books
    where books.BookID = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Book',curdate(),
				concat('ID: ',id,' Name: ',bname,' Amount: ',bamount,' Image: ',bimage,' Description: ',bdescription,
						' CategoryID: ',bcategoryid,' AuthorID: ',bauthorid),
                username);
end;//

delimiter //
create procedure sp_Books_get1()
begin
	select `bookid`,
		booktitle,
        amount,
        image,
        description,categoryid,authorid from Books where isDelete=1;
end;//

delimiter //
create procedure sp_Books_get0()
begin
	select bookid,booktitle,amount,image,description,categoryid,authorid from Books where isDelete=0;
end;//

delimiter //
create procedure sp_Books_findID(in id varchar(20))
begin
	select bookid,booktitle,amount,image,description,categoryid,authorid from Books where Bookid like concat('%',id,'%') and isDelete=1;
end;
//

delimiter //
create procedure sp_Categories_insert(in pname varchar(200),in pusername varchar(10))
begin
	declare id int(11);
    declare cgid varchar(15);
	insert into categories(categoryname)
    values (pname);
    set id = (select ordernumber from categories t3 order by ordernumber desc limit 1);
    set cgid = concat('Category-0',id);
    update categories t3 
    set categoryid = cgid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Category',curdate(),concat('ID: ',cgid,' Name: ',pname),pusername);
end;//

delimiter //
create procedure sp_Categories_update(in pid varchar(20),in pname varchar(200),in pusername varchar(10))
begin
    declare vcagename varchar(200);
    set vcagename = (select categoryname from categories where categoryid = pid);
    update categories a
    set a.categoryname = pname
    where a.categoryid = pid;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Category',curdate(),
				concat('Update from ID: ',pid,' Name: ',vcagename,' To',
											'ID: ',pid,' Name: ',pname)
				,pusername);
end;//

delimiter //
create procedure sp_Categories_delLogic(in id varchar(20),in username varchar(10))
begin
	update categories
    set isDelete=0
    where categoryid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Delete Logic Category',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Categories_restore(in id varchar(20),in username varchar(10))
begin
	update categories
    set isDelete=1
    where categoryid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Restore Category',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Categories_delPhysic(in id varchar(20),in username varchar(10))
begin
	declare vcagename varchar(200);
    set vcagename = (select categoryname from categories where categoryid = id);
	delete from categories
    where categoryid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Physical Delete Category',curdate(),
        concat('ID: ',id,' Name: ',vcagename),
        username);
end;//

delimiter //
create procedure sp_Categories_get1()
begin
	select categoryid,categoryname from categories  where isDelete=1;
end;//

delimiter //
create procedure sp_Categories_get0()
begin
	select categoryid,categoryname from categories  where isDelete=0;
end;//

delimiter //
create procedure sp_Categories_findID(in id varchar(20))
begin
	select categoryid,categoryname from categories  where categoryid like concat('%',id,'%') and isDelete=1;
end;

//

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

/*        Depart            */
delimiter //
-- Procedure insert
create procedure sp_Departs_insert(in name varchar(200),in username varchar(10))
begin
	declare id varchar(15);
    declare dpid varchar(15);
	insert into departs (departname)
    values (name);
    set id = (select ordernumber from departs t3 order by ordernumber desc limit 1);
    set dpid = concat('Depart-',LPAD(id,3,'0'));
    update departs t3 
    set departid = dpid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Depart',curdate(),concat('ID: ',dpid,', Name: ',name),username);
end;//

delimiter //
-- Procedure Update
create procedure sp_Departs_update(in id varchar(20),in name varchar(200),in username varchar(10))
begin
	declare dpname varchar(200);
    set dpname = (select departname from departs where departid = id);
	update departs
    set departname=name
    where departid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Update Depart',curdate(),
        concat('Update Name from ',dpname,' to ',name),
        username);
end;//

delimiter //
create procedure sp_Departs_delLogic(in id varchar(20),in username varchar(10))
begin
	update departs
    set isDelete=0
    where departid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Delete Logic Depart',curdate(),
        concat('ID: ',id),
        username);
end; //

delimiter //
create procedure sp_Departs_restore(in id varchar(20),in username varchar(10))
begin
	update departs
    set isDelete=1
    where departid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Restore Logic Depart',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Departs_delPhysic(in id varchar(20),in username varchar(10))
begin
declare dpname varchar(200);
    set dpname = (select departname from departs where departid = id);
	delete from departs where departid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Delete Physical Depart',curdate(),
        concat('ID: ',id,' Name: ',dpname),
        username);
end; //

delimiter //
create procedure sp_Departs_get1()
begin
	select departid,departname from departs where isDelete=1;
end; //

delimiter //
create procedure sp_Departs_get0()
begin
	select departid,departname from departs where isDelete=0;
end;//

delimiter //
create procedure sp_Departs_findID(in id varchar(20))
begin
	select departid,departname from departs where departid like concat('%',id,'%') and isDelete=1;
end;
//



delimiter //
create procedure sp_HistoryPoint_insert(in customerid int(11),in point int(11),in username varchar(10))
begin
	insert into historypoints(customerid,date,point)
    values(customerid,curdate(),point);
    insert into logs(logname,Date,note,referenceID)
		values ('Insert History Point',curdate(),
				concat('Customer ID: ',customerid,' Date: ',curdate()),
                username);
end;//

delimiter //
create procedure sp_HistoryPoint_update(in id int(11),in customerid int(11),in date datetime,in point int(11),in username varchar(10))
begin
	declare hpcustomerid int(11);
    declare hpdate datetime;
    declare hppoint int(11);
    set hpcustomerid = ( select customerid from historypoints where historypoints.historypointid=id);
    set hpdate= ( select historypoints.date from historypoints where historypoints.historypointid=id);
    set hppoint = ( select historypoints.point from historypoints where historypoints.historypointid=id);
    update historypoints a
    set a.customerid=customerid, a.date = date, a.point=point
    where a.historypointid = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update History Point',curdate(),
				concat('Update from ID: ',id,' Customer ID: ',hpcustomerid,' Date: ',hpdate,' Point: ',hppoint,' To ',
						'ID: ',id,' Customer ID: ',customerid,' Date: ',date,' Point: ',point),
                username);
end;//

delimiter //
create procedure sp_HistoryPoint_delLogic(in id int(11),in username varchar(10))
begin
	update historypoints 
    set isDelete =0
    where historypoints.historypointid = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete History Point',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_HistoryPoint_restore(in id int(11),in username varchar(10))
begin
	update historypoints 
    set isDelete =1
    where historypoints.historypointid = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore History Point',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_HistoryPoint_delPhysic(in id int(11),in username varchar(10))
begin
	declare hpcustomerid int(11);
    declare hpdate datetime;
    declare hppoint int(11);
    set hpcustomerid = ( select customerid from historypoints where historypoints.historypointid=id);
    set hpdate= ( select historypoints.date from historypoints where historypoints.historypointid=id);
    set hppoint = ( select historypoints.point from historypoints where historypoints.historypointid=id);
	delete from historypoints
    where historypoints.historypointid = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete History Point',curdate(),
				concat('ID: ',id,' Customer ID: ',hpcustomerid,' Date: ',hpdate,' Point: ',hppoint),
                username);
end;//

delimiter //
create procedure sp_Historypoint_get1()
begin
	select historypointid,customerid,date,point from historypoints where isDelete =1;
end;//

delimiter //
create procedure sp_Historypoint_get0()
begin
	select historypointid,customerid,date,point  from historypoints where isDelete =0;
end;//

delimiter //
create procedure sp_Historypoint_findID(in id int(11))
begin
	select historypointid,customerid,date,point  from historypoints where historypoints.historypointid like concat('%',id,'%') and isDelete =1;
end;
//

delimiter //
create procedure sp_HistoryPrices_insert(in bookid varchar(10),in price double,in username varchar(10))
begin
	insert into historyprices(bookid,startdate,price)
    values(bookid,curdate(),price);
    insert into logs(logname,date,note,referenceID)
        values ('Insert History Price',curdate(),
				concat('BookID: ',bookid,' Date: ',curdate(),' Price:',price),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_delLogic(in id int(11),in username varchar(10))
begin
	update historyprices
    set isDelete=0
    where historyprices.historypriceid=id;
    insert into logs(logname,date,note,referenceID)
        values ('Logical Delete History Price',curdate(),
				concat('ID: ',id),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_restore(in id int(11),in username varchar(10))
begin
	update historyprices
    set isDelete=1
    where historyprices.historypriceid=id;
    insert into logs(logname,date,note,referenceID)
        values ('Restore History Price',curdate(),
				concat('ID: ',id),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_delPhysic(in id int(11),in username varchar(10))
begin
	declare hpbookid varchar(10);
    declare hpstartdate date;
    declare hpprice double;
    set hpbookid = ( select historyprices.historypriceid from historyprices where historyprices.historypriceid = id);
    set hpstartdate = ( select historyprices.StartDate from historyprices where historyprices.historypriceid = id);
    set hpprice = (select historyprices.Price from historyprices where historyprices.historypriceid = id);
	delete from historyprices
    where historyprices.historypriceid=id;
    insert into logs(logname,date,note,referenceID)
        values ('Physical Delete History Price',curdate(),
				concat('ID: ',id,' BookID: ',hpbookid,' Date: ',hpstartdate,' Price:',hpprice),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_get1()
begin
	select HistoryPriceID,bookid,startdate,price from historyprices where isDelete=1;
end;//

delimiter //
create procedure sp_HistoryPrices_get0()
begin
	select HistoryPriceID,bookid,startdate,price from historyprices where isDelete=0;
end;//

delimiter //
create procedure sp_HistoryPrices_findID(in id int(11))
begin
	select HistoryPriceID,bookid,startdate,price from historyprices where historypriceid like concat('%',id,'%') and isDelete=1;
end;
//

delimiter //

create procedure sp_InvoiceDetails_insert(in invoiceid varchar(20),in bookid varchar(20),in quantity int(11),
											in discount float,in username varchar(10))
begin
	insert into invoicedetails(invoiceid,bookid,quantity,discount)
    values(invoiceid,bookid,quantity,discount);
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Invoice Details',curdate(),
				concat('InvoiceID: ',invoiceid,' BookID: ',bookid,' Quantity: ',Quantity,' Discount: ',discount),
                username);
end;//

delimiter //
create procedure sp_InvoiceDetails_update(in invoiceid varchar(20),in bookid varchar(20),in quantity int(11),
											in discount float,in username varchar(10))
begin
	declare idquantity int(11);
    declare iddiscount float;
    set idquantity = (select invoicedetails.quantity from invoicedetails where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid);
    set iddiscount = (select invoicedetails.discount from invoicedetails where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid);
    update invoicedetails a
    set a.quantity=quantity, a.discount=discount
    where a.invoiceid=invoiceid and a.bookid=bookid;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Invoice Details',curdate(),
				concat('Update from InvoiceID: ',invoiceid,' BookID: ',bookid,' Quantity: ',idquantity,' Discount: ',iddiscount,' To ',
						'InvoiceID: ',invoiceid,' BookID: ',bookid,' Quantity: ',Quantity,' Discount: ',discount),
                username);
end;//

delimiter //
create procedure sp_InvoiceDetails_delLogic(in invoiceid varchar(20),in bookid varchar(20),in username varchar(10))
begin
	update invoicedetails
    set isDelete=0
    where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete Invoice Details',curdate(),
				concat('InvoiceID: ',invoiceid,' ProductID: ',bookid),
                username);
end;//

delimiter //
create procedure sp_InvoiceDetails_restore(in invoiceid varchar(20),in bookid varchar(20),in username varchar(10))
begin
	update invoicedetails
    set isDelete=1
    where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore Invoice Details',curdate(),
				concat('InvoiceID: ',invoiceid,' ProductID: ',bookid),
                username);
end;//

delimiter //
create procedure sp_InvoiceDetails_delPhysic(in invoiceid varchar(20),in bookid varchar(20),in username varchar(10))
begin
	declare idquantity int(11);
    declare iddiscount float;
    set idquantity = (select invoicedetails.quantity from invoicedetails where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid);
    set iddiscount = (select invoicedetails.discount from invoicedetails where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid);
	delete from Invoicedetails
    where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Invoice Details',curdate(),
				concat('InvoiceID: ',invoiceid,' BookID: ',bookid,' Quantity: ',idQuantity,' Discount: ',iddiscount),
                username);
end;//

delimiter //
create procedure sp_InvoiceDetails_get1()
begin
	select invoiceid,bookid,quantity,discount from invoicedetails where isDelete=1;
end;//

delimiter //
create procedure sp_InvoiceDetails_get0()
begin
	select invoiceid,bookid,quantity,discount from invoicedetails where isDelete=0;
end;//

delimiter //
create procedure sp_InvoiceDetails_findInvoiceID(in invoiceid varchar(20))
begin
	select invoicedetails.invoiceid,bookid,quantity,discount from invoicedetails where invoicedetails.invoiceid like concat('%',invoiceid,'%')
								and isDelete=1;
end;
//

delimiter //
create procedure sp_InvoiceDetails_findBookID(in bookid varchar(20))
begin
	select invoiceid,invoicedetails.bookid,quantity,discount from invoicedetails where invoicedetails.bookid  like concat('%',bookid,'%')
								and isDelete=1;
end;
//


delimiter //

create procedure sp_Invoices_insert(in shipdate date,in payStatus varchar(200),in shipstatus varchar(200),
									in methodID int(11),in customerID int(11),in staffID int(11),in username varchar(10))
begin
	declare id int(11);
    declare invid varchar(15);
    insert into invoices(solddate,shipdate,shipstatus,paystatus,methodid,staffid,customerid)
    values(curdate(),shipdate,shipstatus,paystatus,methodID,staffID,customerID);
    set id = (select ordernumber from invoices t3 order by ordernumber desc limit 1);
    set invid = concat('Invoice-0',id);
    update Invoices t3 
    set invoiceid = invid
    where t3.ordernumber=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Invoice',curdate(),
				concat('ID: ',invid,' Sold Date: ',curdate(),' ShipDate: ',shipdate,' ShipStatus: ','Chưa giao',' PayStatus: ',paystatus,' MethodID: ',methodID,
						' StaffID: ',staffID,' CustomerID: ',customerID),
                username);
end;//

delimiter //
create procedure sp_Invoices_update(in id varchar(20),in solddate date,in shipdate date,in payStatus varchar(200),in shipstatus varchar(200),
									in methodID int(11),in customerID int(11),in staffID int(11),in username varchar(10))
begin
	declare icsolddate date;
    declare icshipdate date;
    declare icshipstatus varchar(200);
    declare icpaystatus varchar(200);
    declare icmethodid int(11);
    declare icstaffid int(11);
    declare iccustomerid int(11);
    set icsolddate= (select invoices.solddate from invoices where invoices.invoiceid = id);
    set icshipdate = (select invoices.shipdate from invoices where invoices.invoiceid = id);
    set icshipstatus = (select invoices.shipstatus from invoices where invoices.invoiceid = id);
    set icpaystatus = (select invoices.paystatus from invoices where invoices.invoiceid = id);
    set icmethodid = (select invoices.methodid from invoices where invoices.invoiceid = id);
    set icstaffid = (select invoices.staffid from invoices where invoices.invoiceid = id);
    set iccustomerid = (select invoices.customerid from invoices where invoices.invoiceid = id);
    update invoices a
    set a.solddate= solddate,a.shipdate=shipdate, a.shipstatus=shipstatus, a.paystatus=paystatus,
		a.methodid=methodID, a.staffid=staffID, a.customerid=customerID
	where a.invoiceid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Invoice',curdate(),
				concat('Update from ID: ',id,' SoldDate: ',icsolddate,' ShipDate: ',icshipdate,' ShipStatus: ',icshipstatus,' PayStatus: ',icpaystatus,
						' MethodID: ',icmethodid,' StaffID: ',icstaffid,' CustomerID: ',iccustomerid,' To ',
                        'ID: ',id,' SoldDate: ',solddate,' ShipDate: ',shipdate,' ShipStatus: ',shipstatus,' PayStatus: ',paystatus,
						' MethodID: ',methodID,' StaffID: ',staffID,' CustomerID: ',customerID),
                username);
end;//

delimiter //
create procedure sp_Invoices_delLogic(in id varchar(20),in username varchar(10))
begin
	update Invoices 
    set isDelete=0
    where Invoices.invoiceid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete Invoice',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Invoices_restore(in id varchar(20),in username varchar(10))
begin
	update Invoices 
    set isDelete=1
    where Invoices.invoiceid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore Invoice',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Invoices_delPhysic(in id varchar(20),in username varchar(10))
begin
	declare icsolddate date;
    declare icshipdate date;
    declare icshipstatus varchar(200);
    declare icpaystatus varchar(200);
    declare icmethodid int(11);
    declare icstaffid int(11);
    declare iccustomerid int(11);
    set icsolddate= (select invoices.solddate from invoices where invoices.invoiceid = id);
    set icshipdate = (select invoices.shipdate from invoices where invoices.invoiceid = id);
    set icshipstatus = (select invoices.shipstatus from invoices where invoices.invoiceid = id);
    set icpaystatus = (select invoices.paystatus from invoices where invoices.invoiceid = id);
    set icmethodid = (select invoices.methodid from invoices where invoices.invoiceid = id);
    set icstaffid = (select invoices.staffid from invoices where invoices.invoiceid = id);
    set iccustomerid = (select invoices.customerid from invoices where invoices.invoiceid = id);
	delete from Invoices
    where Invoices.invoiceid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Invoice',curdate(),
				concat('ID: ',id,' Sold Date: ',icsolddate,' ShipDate: ',icshipdate,' ShipStatus: ',icshipstatus,' PayStatus: ',icpaystatus,' MethodID: ',icmethodID,
						' StaffID: ',icstaffID,' CustomerID: ',iccustomerID),
                username);
end;//

delimiter //
create procedure sp_Invoices_get1()
begin
	select invoiceid,solddate,shipdate,shipstatus,paystatus,methodid,staffid,customerid from Invoices where IsDelete =1;
end;//

delimiter //
create procedure sp_Invoices_get0()
begin
	select invoiceid,solddate,shipdate,shipstatus,paystatus,methodid,staffid,customerid from Invoices where IsDelete =0;
end;//

delimiter //
create procedure sp_Invoices_findID(in id varchar(20))
begin
	select invoiceid,solddate,shipdate,shipstatus,paystatus,methodid,staffid,customerid from Invoices where invoiceID like concat('%',id,'%') and IsDelete =1;
end;

//

delimiter //

create procedure sp_levels_insert(in name varchar(200),in point int(11),in username varchar(10))
begin
	insert into levels(point,name)
	values(point,name);
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Levels',curdate(),
				concat(' Name: ',name,' Point: ',point),
				username);
end;

delimiter //
create procedure sp_levels_update(in id int(11),in name varchar(200),in point int(11),in username varchar(10))
begin
	declare lvlpoint int(11);
    declare lvlname varchar(200);
    set lvlpoint = (select levels.point from levels where levels.levelid=id);
    set lvlname = (select levels.name from levels where levels.levelid=id);
    update levels a
    set a.point = point
    where a.levelid=id;
    insert into logs(logname,Date,note,referenceID)
	values ('Update Levels',curdate(),
				concat('Update from ID: ',id,' Name: ',lvlname,' Point: ',lvlpoint,' To: '
						'ID: ',id,'Name: ',name,' Point: ',point),
				username);
end;//

delimiter //
create procedure sp_levels_delLogic(in id int(11),in username varchar(10))
begin
	update levels
    set isDelete =0
    where levels.levelid=id;
    insert into logs(logname,Date,note,referenceID)
	values ('Logical Delete Levels',curdate(),
				concat('ID: ',id),
				username);
end;//

delimiter //
create procedure sp_levels_restore(in id int(11),in username varchar(10))
begin
	update levels
    set isDelete =1
    where levels.levelid=id;
    insert into logs(logname,Date,note,referenceID)
	values ('Restore Levels',curdate(),
				concat('ID: ',id),
				username);
end;//

delimiter //
create procedure sp_levels_delPhysic(in id int(11),in username varchar(10))
begin
	declare lvlpoint int(11);
    declare lvlname varchar(200);
    set lvlpoint = (select levels.point from levels where levels.levelid=id);
    set lvlname = (select levels.name from levels where levels.levelid=id);
	delete from levels
    where levels.levelID=ID;
    insert into logs(logname,Date,note,referenceID)
	values ('Physical Delete Levels',curdate(),
				concat('ID: ',id,' Name: ',lvlname,' Point: ',lvlpoint),
				username);
end;//

delimiter //
create procedure sp_levels_get1()
begin
	select levelid,point,name from levels where isDelete =1;
end;//

delimiter //
create procedure sp_levels_get0()
begin
	select levelid,point,name from levels where isDelete =0;
end;//

delimiter //
create procedure sp_levels_findID(in id int(11))
begin
	select levelid,point,name from levels where levels.levelid like concat('%',id,'%') and isDelete =1;
end;
//

delimiter //

create procedure sp_Logs_insert(in name varchar(50),in time datetime, in note varchar(1000), in username varchar(10))
begin
	insert into logs(logname,Date,note,referenceID)
		values (name,time,note,username);
end;//

delimiter //
create procedure sp_Logs_update(in id int(11),in name varchar(50),in date datetime,
									in note varchar(1000),in referenceID varchar(20), in username varchar(10))
begin
	declare lglogname varchar(50);
    declare lgdate datetime;
    declare lgnote varchar(3000);
    declare lgreferenceid varchar(20);
    set lglogname =(select logs.logid from logs where logs.logid = id);
    set lgdate =(select logs.date from logs where logs.logid = id);
    set lgnote =(select logs.note from logs where logs.logid = id);
    set lgreferenceid =(select logs.referenceid from logs where logs.logid = id);
    update logs x
    set x.logname=name, x.date= date, x.note= note, x.referenceid = referenceid
    where x.logid=logid;
	insert into logs(logname,Date,note,referenceID)
	values ('Update Logs',curdate(),
			concat('Update from ID: ',id,' Name: ',lglogname,' Date : ',lgdate,' ReferenceID: ',lgreferenceid,' To ',
						'ID: ',id,' Name: ',name,' Date : ',date,' ReferenceID: ',referenceid),
            username);
end;//

delimiter //
create procedure sp_Logs_delLogic(in id int(11), in username varchar(10))
begin
	update logs
    set isdelete = 0
    where logs.logid=id;
    insert into logs(logname,Date,note,referenceID)
	values ('Logical Delete Logs',curdate(),
			concat('ID: ',id),
            username);
end;//

delimiter //
create procedure sp_Logs_restore(in id int(11), in username varchar(10))
begin
	update logs
    set isdelete = 1
    where logs.logid=id;
    insert into logs(logname,Date,note,referenceID)
	values ('Restore Logs',curdate(),
			concat('ID: ',id),
            username);
end;//

delimiter //
create procedure sp_Logs_delPhysic(in id int(11),in name varchar(50),in date datetime,
									in note varchar(1000),in referenceID varchar(20), in username varchar(10))
begin
	delete from logs
    where logs.logid =id;
    insert into logs(logname,Date,note,referenceID)
	values ('Physical Delete Logs',curdate(),
			concat('ID: ',id,' Name: ',name,' Date : ',date,' ReferenceID: ',referenceid),
            username);
end;//

delimiter //
create procedure sp_Logs_get1()
begin
	select logid,logname,date,note,ReferenceID from logs where isDelete=1;
end;//

delimiter //
create procedure sp_Logs_get0()
begin
	select logid,logname,date,note,ReferenceID  from logs where isDelete=0;
end;//

delimiter //
create procedure sp_Logs_findID(in id int(11))
begin
	select logid,logname,date,note,ReferenceID  from logs where logid = id and isDelete=1;
end;

//

delimiter //
create procedure sp_Methods_insert(in name varchar(200),in note varchar(500),in username varchar(10))
begin
	insert into Methods(methodname,note)
    values(name,note);
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Method',curdate(),
				concat(' Name: ',name,' Note: ',note),
                username);
end;//

delimiter //
create procedure sp_Methods_update(in id int(11),in name varchar(200),in note varchar(500),in username varchar(10))
begin
	declare methodsnote varchar(500);
    declare methodsname varchar(200);
    set methodsnote = (select methods.note from methods where methods.methodid=id);
    set methodsname = (select methods.methodname from methods where methods.methodid=id);
	update methods a
    set a.note=note
    where a.methodid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Method',curdate(),
				concat('Update from ID:',id,' Name: ',methodsname,' Note: ',methodsnote,' To ',
								'ID: ',id,'Name: ',name,' Note: ',note),
                username);
end;//

delimiter //
create procedure sp_Methods_delLogic(in id int(11),in username varchar(10))
begin
	update methods a
    set isDelete=0
    where a.methodid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete Method',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Methods_restore(in id int(11),in username varchar(10))
begin
	update methods a
    set isDelete=1
    where a.methodid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore Method',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Methods_delPhysic(in id int(11),in username varchar(10))
begin
	declare methodsnote varchar(500);
    declare methodsname varchar(200);
    set methodsnote = (select methods.note from methods where methods.methodid=id);
    set methodsname = (select methods.methodname from methods where methods.methodid=id);
	delete from methods
    where methods.methodid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Method',curdate(),
				concat('ID: ',id,' Name: ',methodsname,' Note: ',methodsnote),
                username);
end;//

delimiter //
create procedure sp_Methods_get0()
begin
	select methodid,methodname,note from methods where isDelete=0;
end;//

delimiter //
create procedure sp_Methods_get1()
begin
	select methodid,methodname,note from methods where isDelete=1;
end;//

delimiter //
create procedure sp_Methods_findID(in id int(11))
begin
	select methodid,methodname,note from methods where methodid like concat('%',id,'%') and isDelete=1;
end;
//

delimiter //

create procedure sp_ReceiptDetails_insert(in receiptid varchar(20),in bookid varchar(20),
											in amount int(11),in price double,in username varchar(10))
begin
	insert into receiptdetails(receiptid,bookid,amount,price)
    values(receiptid,bookid,amount,price);
    insert into logs(logname,date,note,referenceID)
        values ('Insert Receipt Details',curdate(),
				concat('ReceiptID: ',receiptid,' BookID: ',bookid,' Amount: ',amount,' Price: ',price),
				username);
end;//

delimiter //
create procedure sp_ReceiptDetails_update(in receiptid varchar(20),in bookid varchar(20),
											in amount int(11),in price double,in username varchar(10))
begin
	declare rdamount int(11);
    declare rdprice double;
    set rdamount = (select receiptdetails.amount from receiptdetails where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid);
    set rdprice = (select receiptdetails.price from receiptdetails where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid);
    update receiptdetails a
    set a.amount=amount, a.price=price
    where a.receiptid=receiptid and a.bookid=bookid;
    insert into logs(logname,date,note,referenceID)
        values ('Update Receipt Details',curdate(),
				concat('Update from ReceiptID: ',receiptid,' BookID: ',bookid,' Amount: ',rdamount,' Price: ',rdprice,' To ',
						'ReceiptID: ',receiptid,' bookID: ',bookid,' Amount: ',amount,' Price: ',price),
				username);
end;//

delimiter //
create procedure sp_ReceiptDetails_delLogic(in receiptid varchar(20),in bookid varchar(20),in username varchar(10))
begin
	update receiptdetails
    set isDelete=0
	where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid;
    insert into logs(logname,date,note,referenceID)
        values ('Logical Delete Receipt Details',curdate(),
				concat('ReceiptID: ',receiptid,' BookID: ',Bookid),
				username);
end;//

delimiter //
create procedure sp_ReceiptDetails_restore(in receiptid varchar(20),in bookid varchar(20),in username varchar(10))
begin
	update receiptdetails
    set isDelete=1
	where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid;
    insert into logs(logname,date,note,referenceID)
        values ('Restore Receipt Details',curdate(),
				concat('ReceiptID: ',receiptid,' BookID: ',bookid),
				username);
end;//

delimiter //
create procedure sp_ReceiptDetails_delPhysic(in receiptid varchar(20),in bookid varchar(20),in username varchar(10))
begin
	declare rdamount int(11);
    declare rdprice double;
    set rdamount = (select receiptdetails.amount from receiptdetails where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid);
    set rdprice = (select receiptdetails.price from receiptdetails where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid);
	update receiptdetails
    set isDelete=0
	where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid;
    insert into logs(logname,date,note,referenceID)
        values ('Physical Delete Receipt Details',curdate(),
				concat('ReceiptID: ',receiptid,' BookID: ',bookid,' Amount: ',rdamount,' Price: ',rdprice),
				username);
end;//

delimiter //
create procedure sp_ReceiptDetails_get1()
begin
	select ReceiptID,BookID,Amount,Price from ReceiptDetails where isDelete=1;
end;//

delimiter //
create procedure sp_ReceiptDetails_get0()
begin
	select ReceiptID,BookID,Amount,Price from ReceiptDetails where isDelete=0;
end;//

delimiter //
create procedure sp_ReceiptDetails_findReceiptID(in receiptid varchar(20))
begin
	select receiptdetails.ReceiptID,BookID,Amount,Price from ReceiptDetails where receiptdetails.receiptid like concat('%',receiptid,'%')
									and isDelete=1;
end;

delimiter //
create procedure sp_ReceiptDetails_findBookID(in bookid varchar(20))
begin
	select receiptdetails.ReceiptID,receiptdetails.BookID,Amount,Price from ReceiptDetails where receiptdetails.bookid like concat('%',bookid,'%')
									and isDelete=1;
end;
//

delimiter //
create procedure sp_Receipts_insert(in supplierid varchar(20),in username varchar(10))
begin
	declare id int(11);
    declare rcid varchar(15);
	insert into receipts(supplierid,date)
    values(supplierid,curdate());
    set id = (select ordernumber from receipts t3 order by ordernumber desc limit 1);
    set rcid = concat('Receipt-0',id);
    update receipts t3 
    set receiptid = rcid
    where t3.ordernumber=id;
    insert into logs(logname,Date,note,referenceID) 	
		values ('Insert Receipts',curdate(),
				concat('ID: ',rcid,' supplierID: ',supplierid,' Date: ',curdate()),
                username);
end;//

delimiter //
create procedure sp_Receipts_update(in id varchar(20),in supplierid varchar(20),in date date,in username varchar(10))
begin
	declare rpspid varchar(10);
    declare rpdate date;
    set rpspid = (select receipts.supplierid from receipts where receipts.receiptid=id);
    set rpdate = (select receipts.date from receipts where receipts.receiptid=id);
    update receipts a
    set a.supplierid = supplierid, a.date=date
    where a.receiptid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Receipts',curdate(),
				concat('Update from ID: ',id,' supplierID: ',rpspid,' Date: ',rpdate,' To ',
								'ID: ',id,' supplierID: ',supplierid,' Date: ',date),
                username);
end;//

delimiter //
create procedure sp_Receipts_delLogic(in id varchar(20),in username varchar(20))
begin
	update receipts
    set isDelete =0
    where receipts.receiptid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete Receipts',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Receipts_restore(in id varchar(20),in username varchar(10))
begin
	update receipts
    set isDelete =1
    where receipts.receiptid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore Receipts',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Receipts_delPhysic(in id varchar(20),in username varchar(10))
begin
	declare rpspid varchar(10);
    declare rpdate date;
    set rpspid = (select receipts.supplierid from receipts where receipts.receiptid=id);
    set rpdate = (select receipts.date from receipts where receipts.receiptid=id);
	delete from receipts
    where receipts.receiptid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Receipts',curdate(),
				concat('ID: ',id,' supplierID: ',rpspid,' Date: ',rpdate),
                username);
end;//

delimiter //
create procedure sp_Receipts_get1()
begin
	select receiptid,supplierid,date from Receipts where isDelete=1;
end;//

delimiter //
create procedure sp_Receipts_get0()
begin
	select receiptid,supplierid,date from Receipts where isDelete=0;
end;//

delimiter //
create procedure sp_Receipts_findID(in id varchar(20))
begin
	select receiptid,supplierid,date from Receipts where receiptID like concat('%',id,'%') and isDelete=1;
end;
//

delimiter //

create procedure sp_ShipInfo_insert(in invoiceid varchar(20),in fullname varchar(200),in address varchar(500),
									in phone varchar(10), in username varchar(100))
begin
	insert into Shipinfors(invoiceid,fullname,address,phone)
    values(invoiceid,fullname,address,phone);
    insert into logs(logname,Date,note,referenceID)
		values ('Insert ShipInfo',curdate(),
				concat('Invoice ID: ',invoiceid,' FullName: ',fullname,' Address: ',address,' Phone: ',phone),
                username);
end;//

delimiter //
create procedure sp_ShipInfo_update(in id int(11),in invoiceid varchar(10),in fullname varchar(200),in address varchar(500),
									in phone varchar(10), in username varchar(100))
begin
	declare siinvoiceid varchar(10);
	declare sifullname varchar(200);
    declare siaddress varchar(500);
    declare siphone varchar(10);
    set siinvoiceid = ( select shipinfors.invoiceid from shipinfors where shipinfors.shipinforid=id);
    set sifullname = ( select shipinfors.fullname from shipinfors where shipinfors.shipinforid=id);
	set siaddress = ( select shipinfors.address from shipinfors where shipinfors.shipinforid=id);
    set siphone = ( select shipinfors.phone from shipinfors where shipinfors.shipinforid=id);
    update shipinfors a
    set a.fullname=fullname, a.address=address, a.phone=phone
    where a.invoiceid=invoiceid;
    insert into logs(logname,Date,note,referenceID)
		values ('Update ShipInfo',curdate(),
				concat('Update from ID',id,' Invoice ID: ',siinvoiceid,' FullName: ',sifullname,' Address: ',siaddress,' Phone: ',siphone,' To ',
						'ID: ',id,' Invoice ID: ',invoiceid,' FullName: ',fullname,' Address: ',address,' Phone: ',phone),
                username);
end;//

delimiter //
create procedure sp_ShipInfo_delLogic(in id int(11),in username varchar(100))
begin
	update shipinfors 
    set isDelete=0
    where shipinfors.ShipInforID=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete ShipInfo',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_ShipInfo_restore(in id int(11), in username varchar(100))
begin
	update shipinfors 
    set isDelete=1
    where shipinfors.ShipInforID=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore ShipInfo',curdate(),
				concat('Invoice ID: ',id),
                username);
end;//

delimiter //
create procedure sp_ShipInfo_delPhysic(in id int(11), in username varchar(10))
begin
	declare siinvoiceid varchar(10);
	declare sifullname varchar(200);
    declare siaddress varchar(500);
    declare siphone varchar(10);
    set siinvoiceid = ( select shipinfors.invoiceid from shipinfors where shipinfors.shipinforid=id);
    set sifullname = ( select shipinfors.fullname from shipinfors where shipinfors.shipinforid=id);
	set siaddress = ( select shipinfors.address from shipinfors where shipinfors.shipinforid=id);
    set siphone = ( select shipinfors.phone from shipinfors where shipinfors.shipinforid=id);
	delete from shipinfors
    where shipinfors.ShipInforID=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete ShipInfo',curdate(),
				concat('ID: ',id,' Invoice ID: ',siinvoiceid,' FullName: ',sifullname,' Address: ',siaddress,' Phone: ',siphone),
                username);
end;//

delimiter //
create procedure sp_ShipInfo_get1()
begin
	select shipinforID,invoiceid,fullname,address,phone from shipinfors where isDelete =1;
end;//

delimiter //
create procedure sp_ShipInfo_get0()
begin
	select shipinforID,invoiceid,fullname,address,phone from shipinfors where isDelete =0;
end;//

delimiter //
create procedure sp_ShipInfo_findID(in id int(11))
begin
	select shipinforID,invoiceid,fullname,address,phone from shipinfors where shipinfors.shipinforid like concat('%',id,'%') and isDelete =1;
end;
//

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
drop database if exists mikrokosmos ;

create database Mikrokosmos charset utf8mb4 COLLATE utf8mb4_unicode_ci;

use Mikrokosmos;


/* Table Logs */
create table Logs
(
	LogID int(11) auto_increment primary key,
	LogName varchar(50) not null,
	Date datetime not null,
	Note varchar(3000) not null,
	ReferenceID varchar(20) not null,
	IsDelete bit default 1
);

/* Table Levels */
create table Levels(
	LevelID int(11) auto_increment primary key,
	Point int,
	Name varchar(200) not null,
	IsDelete bit default 1
);

/* Table Categories */
create table Categories
(
	OrderNumber int(11) auto_increment primary key,
	CategoryID varchar(15) unique,
	CategoryName varchar(200) not null,
	IsDelete bit default 1
);

/* Table Suppliers */
create table Suppliers(
	OrderNumber int(11) auto_increment primary key,
	SupplierID varchar(15) unique,
	SupplierName varchar(200) not null,
	Address varchar(500),
	Phone varchar(12),
	Email varchar(50),
	IsDelete bit default 1
);

/* Table Authors */
create table Authors
(
	OrderNumber int(11) auto_increment primary key,
	AuthorID varchar(15) unique,
	AuthorName varchar(200) not null,
	IsDelete bit default 1
);

/* Table Departs */
create table Departs
(
	OrderNumber int(11) auto_increment primary key,
	DepartID varchar(15) unique,
	DepartName varchar(200) not null,
	IsDelete bit default 1
);

/* Table Products */
create table Books(
	OrderNumber int(11) auto_increment primary key,
	BookID varchar(15) unique,
	BookTitle varchar(200) not null,
    Amount int default 0,
    Image varchar(500),
	Description varchar(3000) not null,
    CategoryID varchar(15) not null references Categories(CategoryID) on delete cascade,
    AuthorID varchar(15) not null references Authors(AuthorID) on delete cascade,
	IsDelete bit default 1
);


/* Table HistoryPrices */
create table HistoryPrices
(
	HistoryPriceID int auto_increment primary key,
	BookID varchar(10) not null references Books(BookID) on delete cascade,
	StartDate date not null,
	Price double not null check (Price > 0),
	IsDelete bit default 1
);


/* Table Staffs */
create table Staffs(
	OrderNumber int auto_increment primary key,
	StaffID varchar(15) unique,
	StaffName varchar(200) not null,
	Image varchar(500),
	Username varchar(20) unique,
	Password varchar(500) not null,
	Role bit not null,
    Email varchar(50) not null,
	Birthday date not null,
	Address varchar(500) not null,
	Phone varchar(10) not null,
    Salary real not null,
	DepartID varchar(15) not null,
	foreign key (DepartID) references Departs(DepartID) on delete cascade,
	IsDelete bit default 1
);

/* Table Customers */
create table Customers(
	OrderNumber int auto_increment primary key,
	CustomerID varchar(15) unique,
	CustomerName varchar(200) not null,
    Image varchar(500),
	Email varchar(50) not null,
	Address varchar(500) not null,
	Phone varchar(10) not null,
    Password varchar(30) not null,
	IsDelete bit default 1
);

/* Table HistoryPoints */
create table HistoryPoints(
	HistoryPointID int auto_increment primary key,
	CustomerID varchar(15) not null,
	foreign key (CustomerID) references Customers(CustomerID) on delete cascade,
	Date datetime not null,
    Point int not null check (Point > -1),
	IsDelete bit default 1
);


/* Table Receipts */
create table Receipts(
	OrderNumber int(11) auto_increment primary key,
	ReceiptID varchar(15) unique,
	SupplierID varchar(15) not null references Suppliers(SupplierID) on delete cascade,
	Date date not null,
	IsDelete bit default 1
);


/* Table ReceiptDetails */
create table ReceiptDetails
(
	ReceiptID varchar(15) not null references Receipts(ReceiptID) on delete cascade,	
	BookID varchar(15) not null references Books(BookID) on delete cascade,
	primary key (ReceiptID, BookID),	
	Amount int not null check (Amount > 0),
	Price double not null check (Price > 0),
	IsDelete bit default 1
);

/* Table Methods */
create table Methods
(
	MethodID int auto_increment primary key,
	MethodName varchar(200) not null,
	Note nvarchar(500),
	IsDelete bit default 1
);

/* Table Invoices */
Create table Invoices
(
	OrderNumber int(11) auto_increment primary key,
	InvoiceID varchar(15) unique,
	SoldDate date not null,
	ShipStatus varchar(200) not null,
	PayStatus varchar(200) not null,
	MethodID int not null references Methods(MethodID) on delete cascade,
	StaffID varchar(15) not null references Staffs(StaffID) on delete cascade,	
	CustomerID varchar(15) null references Customers(CustomerID) on delete cascade,
    IsDelete bit default 1
);

/* Table ShipInfo */
create table ShipInfors(
	ShipInforID int auto_increment primary key,
	InvoiceID varchar(15) not null references Invoices(InvoiceID) on delete cascade,
	FullName varchar(200) not null,
    ShipDate date not null,
	Address varchar(500) not null,
	Phone varchar(10) not null,
	IsDelete bit default 1
);

/* Table InvoiceDetails */
create table InvoiceDetails(
	InvoiceID varchar(15) not null references Invoices(InvoiceID) on delete cascade,
	BookID varchar(15) not null references Books(BookID) on delete cascade,
	primary key (InvoiceID, BookID),
	Quantity int not null check (Quantity > 0) ,
	Discount float not null check (Discount >= 0),
	IsDelete bit default 1
);

create table image(
	id int(11) primary key auto_increment,
	refID varchar(50),
    file_name varchar(500),
    file_type varchar(50),
    content longblob
);

-- SP
delimiter //
create procedure sp_Authors_insert(in name varchar(200),in username varchar(10))
begin
	declare id int(11);
    declare atid varchar(15);
	insert into authors (authorname)
    values (name);
    set id = (select ordernumber from authors t3 order by ordernumber desc limit 1);
    set atid = concat('Author-',LPAD(id,3,'0'));
    update authors t3 
    set authorid = atid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Producers',curdate(),
			concat('ID: ',atid,' Name: ',name),
				username);
end;//

delimiter //
create procedure sp_Authors_update(in id varchar(10),in name varchar(200),in username varchar(10))
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
create procedure sp_Authors_delLogic(in id varchar(10),in username varchar(10))
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
create procedure sp_Authors_Restore(in id varchar(10),in username varchar(10))
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
create procedure sp_Authors_delPhysic(in id varchar(10),in username varchar(10))
begin
	declare atname varchar(200);
    set atname = (select authors.AuthorName from authors where authors.authorid=id);
    IF( exists (select * from books where authorid = id)) then
		begin
			select * from books where authorid = id;
		end;
	else
		begin
			delete from authors
			where authorid = id;
			insert into logs(logname,date,note,referenceID)
				values ('Physical Delete Author',curdate(),
				concat('ID: ',id,' Name: ',atname),
				username);
        end;
	end if;
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
create procedure sp_Authors_findID(in id varchar(10))
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
		set spid = concat('Supplier-',LPAD(id,3,'0'));
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
	create  procedure sp_Suppliers_update(in id varchar(15),in name varchar(200),
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
	create  procedure sp_Suppliers_delLogic(in id varchar(15),in username varchar(10))
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
	create  procedure sp_Suppliers_Restore(in id varchar(15),in username varchar(10))
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
	create  procedure sp_Suppliers_delPhysic(in id varchar(15),in username varchar(10))
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
	create procedure sp_Suppliers_findID(in id varchar(15))
	begin
		select supplierid,SupplierName,address,phone,email from suppliers where supplierid like concat('%',id,'%')  and isDelete=1;
	end;
	//



delimiter //
create procedure sp_Books_insert(in title varchar(200), in price double,
									in description varchar(500),in categoryid varchar(20),in authorid varchar(15),in username varchar(10))
begin
	declare id int(11);
    declare bkid varchar(15);
	insert into books(booktitle,amount,description,categoryid,authorid)
    values(title,0,description,categoryid,authorid);
    set id = (select ordernumber from books t3 order by ordernumber desc limit 1);
    set bkid = concat('Book-',LPAD(id,3,'0'));
    update books t3 
    set bookid = bkid
    where t3.ordernumber=id;
    call sp_HistoryPrices_insert(bkid,price,username);
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Books',curdate(),
				concat('ID: ',bkid,' Title: ',title,' Amount: ',0,' Description: ',description,
						' CategoryID: ',categoryid,' AuthorID: ',authorid),
                username);
end;//

delimiter //
create procedure sp_Books_update(in id varchar(20),in title varchar(200),in amount int(11),in price double,
									in description varchar(500),in categoryid varchar(20),in authorid varchar(15),in username varchar(10))
begin
	declare bname varchar(200);
    declare bdescription varchar(500);
    declare bcategoryid varchar(20);
    declare bauthorid varchar(20);
    set bname = (select books.booktitle from books where books.bookid=id);
    set bdescription = (select books.description from books where books.bookid=id);
    set bcategoryid = (select books.categoryid from books where books.bookid=id);
    set bauthorid = (select books.authorid from books where books.bookid=id);
    call sp_HistoryPrices_insert(id,price,username);
	update books a
    set a.booktitle = title, a.description= description,
		a.categoryid=categoryid, a.authorid=authorid,
        a.amount=amount
	where a.bookid=id ;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Books',curdate(),
				concat('Update from ID: ',id,' Name: ',bname,' Amount: ',amount,' Description: ',bdescription,
						' CategoryID: ',bcategoryid,' AuthorID: ',bauthorid,' To ',
                        'ID: ',id,' Name: ',title,' Amount: ',amount,' Description: ',description,
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
    declare bamount int(11);
    declare bdescription varchar(500);
    declare bcategoryid varchar(20);
    declare bauthorid varchar(15);
    set bname = (select books.booktitle from books where books.bookid=id);
    set bamount = (select books.amount from books where books.BookID=id);
    set bdescription = (select books.description from books where books.bookid=id);
    set bcategoryid = (select books.categoryid from books where books.bookid=id);
    set bauthorid = (select books.authorid from books where books.bookid=id);
	delete from books
    where books.BookID = id;
    delete from receiptdetails
    where receiptdetails.bookid=id;
    delete from invoicedetails
    where invoicedetails.bookid=id;
    insert into logs(logname,Date,Note,referenceID)
		values ('Physical Delete Book',curdate(),
				concat('ID: ',id,' Name: ',bname,' Amount: ',bamount,' Description: ',bdescription,
						' CategoryID: ',bcategoryid,' AuthorID: ',bauthorid),
                username);
end;//


delimiter //
create procedure sp_Books_get1()
begin
	select 
	t1.`bookid`,
	booktitle,
	t4.price,
	t1.amount from Books t1
	left join (
		select  t2.bookID,t2.price,max(t2.HistoryPriceID) from historyprices t2
        group by t2.bookID
	) as t4 on t4.bookid =t1.bookid
	where isDelete=1;
end;//

delimiter //
create procedure sp_Books_getListInsert()
begin
	select 
	t1.`bookid`,
	booktitle
    from Books t1
	where isDelete=1;
end;//

delimiter //
create procedure sp_Books_get0()
begin
	select bookid,booktitle,amount,image,description,categoryid,authorid from Books where isDelete=0;
end;//

delimiter //
create procedure `sp_Books_findID`(in id varchar(20))
begin
	select IFNULL(file_name,'') into @image
    from image t1 where t1.refID = id
    order by id desc
    limit 1;
	select ifnull(price,0) into @price
	from historyprices where bookid = id
	order by historypriceid desc
	limit 1;
	select 
		t1.`bookid`,
		t1.booktitle,
        t1.amount,
        @image image,
		@price price,
        t1.description,
		t2.categoryid,
		t3.authorid
		from Books t1
		inner join categories t2 on t1.categoryid=t2.categoryid
		inner join authors t3 on t1.authorid=t3.authorid
		where t1.bookid = id and t1.isDelete=1;
end;
//

delimiter //
create procedure sp_Categories_insert(in pname varchar(200),in pusername varchar(20))
begin
	declare id int(11);
    declare cgid varchar(15);
	insert into categories(categoryname)
    values (pname);
    set id = (select ordernumber from categories t3 order by ordernumber desc limit 1);
    set cgid = concat('Category-',LPAD(id,3,'0'));
    update categories t3 
    set categoryid = cgid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Category',curdate(),concat('ID: ',cgid,' Name: ',pname),pusername);
end;//

delimiter //
create procedure sp_Categories_update(in pid varchar(20),in pname varchar(200),in pusername varchar(20))sp_Receipts_findID
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
create procedure sp_Categories_delLogic(in id varchar(10),in username varchar(10))
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
create procedure sp_Categories_restore(in id varchar(10),in username varchar(10))
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
create procedure sp_Categories_delPhysic(in id varchar(15),in username varchar(10))
begin
	declare vcagename varchar(200);
    set vcagename = (select categoryname from categories where categoryid = id);
    IF( exists (select * from books where categoryid = id)) then
		begin
			select * from books where categoryid = id;
		end;
	else
		begin
			delete from categories
			where categoryid = id;
			insert into logs(logname,date,note,referenceID)
				values ('Physical Delete Category',curdate(),
				concat('ID: ',id,' Name: ',vcagename),
				username);
        end;
	end if;
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
create procedure sp_Categories_findID(in id varchar(10))
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
    set customerid = concat('Customer-',LPAD(id,3,'0'));
    update customers t3 
    set t3.customerid = customerid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Customers',curdate(),
			concat('ID:',customerid,' Name: ',name,' Address: ',address,' Phone: ',phone,' Email: ',email,' Password:',password,' Image: ',image),
			username);
end;//


DELIMITER //
CREATE PROCEDURE `sp_Customers_update`(in customerid varchar(20),in name varchar(200),in email varchar(50),in pPoint int(11),
										in address varchar(500),in phone varchar(10),in username varchar(20))
begin
	declare csname varchar(200);
    declare csemail varchar(50);
    declare csaddress varchar(500);
    declare csphone varchar(10);
    set csname = ( select customers.customername from customers where customers.customerid = customerid);
    set csemail = (select customers.email from customers where customers.customerid=customerid);
    set csaddress = ( select customers.address from customers where customers.customerid = customerid);
    set csphone = ( select customers.phone from customers where customers.customerid = customerid);
    update customers a
    set a.customername= name,a.email=email, a.address=address, a.phone=phone
    where a.customerid = customerid;
    call sp_HistoryPoints_insert(customerid, pPoint,username);
    insert into logs(logname,Date,note,referenceID)
		values ('Update Customers',curdate(),
				concat('Update from ID:',customerid,' Name: ',csname,' Address: ',csaddress,' Phone: ',csphone,' Email: ',csemail,' To ',
							'Name: ',name,' Address: ',address,' Phone: ',phone,' Email: ',email),
				username);
end;

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
create procedure `sp_Customers_delPhysic`(in id varchar(20),in username varchar(10))
begin
	declare csname varchar(200);
    declare csemail varchar(50);
    declare csaddress varchar(500);
    declare csphone varchar(10);
    declare cspassword varchar(30);
    set csname = ( select customers.customername from customers where customers.customerid = id);
    set csemail = (select customers.email from customers where customers.customerid=id);
    set csaddress = ( select customers.address from customers where customers.customerid = id);
    set csphone = ( select customers.phone from customers where customers.customerid = id);
    set cspassword = ( select customers.password from customers where customers.customerid=id);
    IF( exists (select * from invoices where customerid = id)) then
		begin
			select * from invoices where customerid = id;
		end;
	else
		begin
			delete from Customers
			where customers.customerid = id;
            delete from historypoints
            where historypoints.customerid = id;
			insert into logs(logname,Date,note,referenceID)
			values ('Physical Delete Customers',curdate(),
					concat('ID:',id,'Name: ',csname,' Address: ',csaddress,' Phone: ',csphone,' Email: ',csemail,' Password:',cspassword),
					username);
        end;
	end if;
end;//

delimiter //
create procedure sp_Customers_get0()
begin
	select customerid,customername,image,email,address,phone,password from customers where isDelete =0;
end;//

delimiter //
create procedure sp_Customers_get1()
begin
	select customerid,customername,email from customers where isDelete =1;
end;//

delimiter //
create procedure sp_Customers_getDetail(in customerid varchar(20))
begin
	select customerid,customername,email from customers where isDelete =1;
end;//

delimiter //
create procedure sp_Customers_findID(in id varchar(20))
begin
	select IFNULL(file_name,'') into @image
    from image t1 where t1.refID = id
    order by id desc
    limit 1;
	select t2.point into @vpoint from historypoints t2 where t2.customerid = id order by t2.historypointid desc limit 1;
	select customerid,customername,email,address,phone,@vpoint customerpoint,password,@image customerimage
    from customers t1 
    where t1.customerid =id and isDelete =1;
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
create procedure sp_Departs_update(in id varchar(10),in name varchar(200),in username varchar(10))
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
create procedure sp_Departs_delLogic(in id varchar(10),in username varchar(10))
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
create procedure sp_Departs_restore(in id varchar(10),in username varchar(10))
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
create procedure sp_Departs_delPhysic(in id varchar(10),in username varchar(10))
begin
	declare dpname varchar(200);
    set dpname = (select departname from departs where departid = id);
    IF( exists (select * from staffs where departid = id)) then
		begin
			select * from staffs where departid = id;
		end;
	else
		begin
			delete from departs where departid = id;
			insert into logs(logname,date,note,referenceID)
				values ('Delete Physical Depart',curdate(),
				concat('ID: ',id,' Name: ',dpname),
				username);
        end;
	end if;
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
create procedure sp_Departs_findID(in id varchar(10))
begin
	select departid,departname from departs where departid like concat('%',id,'%') and isDelete=1;
end;
//



delimiter //
create procedure sp_HistoryPoints_insert(in customerid varchar(20),in point int(11),in username varchar(20))
begin
	insert into historypoints(customerid,date,point)
    values(customerid,curdate(),point);
    insert into logs(logname,Date,note,referenceID)
		values ('Insert History Point',curdate(),
				concat('Customer ID: ',customerid,' Date: ',curdate()),
                username);
end;//

delimiter //
create procedure sp_HistoryPoints_update(in id int(11),in customerid int(11),in date datetime,in point int(11),in username varchar(20))
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
create procedure sp_HistoryPoints_delLogic(in id int(11),in username varchar(20))
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
create procedure sp_HistoryPoints_restore(in id int(11),in username varchar(20))
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
create procedure sp_HistoryPoints_delPhysic(in id int(11),in username varchar(20))
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
create procedure sp_Historypoints_get1(in customerid varchar(20))
begin
	select 
    t1.historypointid,
    t1.customerid,
    t1.date,
    t1.point 
    from historypoints t1
    where 
	t1.customerid = customerid and isDelete =1
    order by date desc
    ;
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
create procedure sp_HistoryPrices_insert(in bookid varchar(20),in price double,in username varchar(10))
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
create procedure sp_HistoryPrices_findID(in id varchar(20))
begin
	select 
    HistoryPriceID,
    bookid,
    startdate,
    price 
    from historyprices where bookid= id and isDelete=1
    order by historypriceid desc
    ;
end;
//

delimiter //

create procedure sp_InvoiceDetails_insert(in invoiceid varchar(20),in bookid varchar(20),in quantity int(11),
											in discount float,in username varchar(10))
begin
	IF(EXISTS (SELECT bookid from invoicedetails t1 where t1.invoiceID = invoiceid and t1.bookid = bookid)) THEN
		BEGIN
			UPDATE invoicedetails t1 
			set t1.quantity=t1.quantity+quantity,t1.discount=discount
			where t1.invoiceID = invoiceid and t1.bookid = bookid;
		END;
	ELSE
		BEGIN
			insert into invoicedetails(invoiceid,bookid,quantity,discount)
			values(invoiceid,bookid,quantity,discount);
			update invoicedetails t1
			set t1.discount=discount where t1.invoiceID=invoiceid;
		END;
	END IF;
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Invoice Details',curdate(),
				concat('InvoiceID: ',invoiceid,' BookID: ',bookid,' Quantity: ',Quantity,' Discount: ',discount),
                username);
end;//

delimiter //
create procedure sp_InvoiceDetails_update(in invoiceid varchar(10),in bookid varchar(10),in quantity int(11),
											in discount float,in username varchar(10))
begin
	declare idquantity int(11);
    declare iddiscount float;
    set idquantity = (select invoicedetails.quantity from invoicedetails where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid);
    set iddiscount = (select invoicedetails.discount from invoicedetails where invoicedetails.invoiceid=invoiceid and invoicedetails.bookid=bookid);
    update invoicedetails a
    set a.quantity=quantity
    where a.invoiceid=invoiceid and a.bookid=bookid;
	update invoicedetails t1
	set t1.discount=discount where t1.invoiceID=invoiceid;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Invoice Details',curdate(),
				concat('Update from InvoiceID: ',invoiceid,' BookID: ',bookid,' Quantity: ',idquantity,' Discount: ',iddiscount,' To ',
						'InvoiceID: ',invoiceid,' BookID: ',bookid,' Quantity: ',Quantity,' Discount: ',discount),
                username);
end;//

delimiter //
create procedure sp_InvoiceDetails_delLogic(in invoiceid varchar(10),in bookid varchar(10),in username varchar(10))
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
create procedure sp_InvoiceDetails_restore(in invoiceid varchar(10),in bookid varchar(10),in username varchar(10))
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
	select 
    invoiceid,bookid,quantity,discount from invoicedetails where isDelete=1;
end;//

delimiter //
create procedure sp_InvoiceDetails_get0()
begin
	select invoiceid,bookid,quantity,discount from invoicedetails where isDelete=0;
end;//

delimiter //
create procedure sp_InvoiceDetails_findInvoiceID(in pinvoiceid varchar(20))
begin
	select 
    t1.bookid, 
    t1.booktitle,
    t3.quantity quantity,
    t2.price*t3.quantity - t2.price*t3.quantity*(t3.discount/100) as total
	from books t1 
	inner join historyprices t2 on t1.bookid= t2.BookID 
	inner join invoicedetails t3 on t1.bookid=t3.BookID 
	inner join invoices t4 on t3.InvoiceID=t4.InvoiceID 
	where t2.HistoryPriceID = ( 
	select t5.historypriceid 
	from 
	historyprices t5 
	WHERE t5.BookID=t1.BookID and t5.startdate < t4.solddate
	order by t5.startdate desc 
	limit 1 ) and t3.invoiceID = pinvoiceid;
end;
//

delimiter //
create procedure sp_InvoiceDetails_findBookID(in bookid varchar(10))
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
    insert into invoices(solddate,shipstatus,paystatus,methodid,staffid,customerid)
    values(curdate(),shipstatus,paystatus,methodID,staffID,customerID);
    set id = (select ordernumber from invoices t3 order by ordernumber desc limit 1);
    set invid = concat('Invoice-',LPAD(id,3,'0'));
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
create procedure `sp_Invoices_update`(in id varchar(20),in solddate date,in payStatus varchar(200),in shipstatus varchar(200),
									in methodID int(11),in customerID varchar(20),in staffID varchar(20),in discount double,in username varchar(10))
begin
	declare icsolddate date;
    declare icshipstatus varchar(200);
    declare icpaystatus varchar(200);
    declare icmethodid int(11);
    declare icstaffid int(11);
    declare iccustomerid int(11);
    set icsolddate= (select invoices.solddate from invoices where invoices.invoiceid = id);
    set icshipstatus = (select invoices.shipstatus from invoices where invoices.invoiceid = id);
    set icpaystatus = (select invoices.paystatus from invoices where invoices.invoiceid = id);
    set icmethodid = (select invoices.methodid from invoices where invoices.invoiceid = id);
    set icstaffid = (select invoices.staffid from invoices where invoices.invoiceid = id);
    set iccustomerid = (select invoices.customerid from invoices where invoices.invoiceid = id);
    update invoices a
    set a.solddate= solddate,a.shipstatus=shipstatus, a.paystatus=paystatus,
		a.methodid=methodID, a.staffid=staffID, a.customerid=customerID
	where a.invoiceid=id;
    update invoicedetails b
    set b.discount=discount
    where b.invoiceid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Invoice',curdate(),
				concat('Update from ID: ',id,' SoldDate: ',icsolddate,' ShipStatus: ',icshipstatus,' PayStatus: ',icpaystatus,
						' MethodID: ',icmethodid,' StaffID: ',icstaffid,' CustomerID: ',iccustomerid,' To ',
                        'ID: ',id,' SoldDate: ',solddate,' ShipStatus: ',shipstatus,' PayStatus: ',paystatus,
						' MethodID: ',methodID,' StaffID: ',staffID,' CustomerID: ',customerID),
                username);
end;//

delimiter //
create procedure sp_Invoices_delLogic(in id varchar(10),in username varchar(10))
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
create procedure sp_Invoices_restore(in id varchar(10),in username varchar(10))
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
create procedure `sp_Invoices_delPhysic`(in id varchar(20),in username varchar(10))
begin
	declare icsolddate date;
    declare icshipstatus varchar(200);
    declare icpaystatus varchar(200);
    declare icmethodid int(11);
    declare icstaffid int(11);
    declare iccustomerid int(11);
    set icsolddate= (select invoices.solddate from invoices where invoices.invoiceid = id);
    set icshipstatus = (select invoices.shipstatus from invoices where invoices.invoiceid = id);
    set icpaystatus = (select invoices.paystatus from invoices where invoices.invoiceid = id);
    set icmethodid = (select invoices.methodid from invoices where invoices.invoiceid = id);
    set icstaffid = (select invoices.staffid from invoices where invoices.invoiceid = id);
    set iccustomerid = (select invoices.customerid from invoices where invoices.invoiceid = id);
	delete from Invoices 
    where Invoices.invoiceid=id;
    delete from invoicedetails 
    where invoicedetails.invoiceid=id;
    delete from shipinfors
    where shipinfors.invoiceID=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Invoice',curdate(),
				concat('ID: ',id,' Sold Date: ',icsolddate,' ShipStatus: ',icshipstatus,' PayStatus: ',icpaystatus,' MethodID: ',icmethodID,
						' StaffID: ',icstaffID,' CustomerID: ',iccustomerID),
                username);
end//

delimiter //
create procedure sp_Invoices_get1()
begin
	select 
    t1.invoiceid,
    t1.solddate,
    concat(t3.staffname,' - ',t3.staffid) staffname,
    concat(t2.customername,' - ',t2.customerid) customername
    from Invoices t1
    inner join customers t2 on t1.customerid=t2.customerid
	inner join staffs t3 on t1.staffid=t3.staffid
    where t1.IsDelete =1;
end;//

delimiter //
create procedure sp_Invoices_get0()
begin
	select invoiceid,solddate,shipdate,shipstatus,paystatus,methodid,staffid,customerid from Invoices where IsDelete =0;
end;//

delimiter //
create procedure sp_Invoices_findID(in id varchar(20))
begin
	select
	t1.InvoiceID,
	t1.solddate soldate,
	t1.customerid,
	t1.staffid,
	t4.discount,
	t5.methodid,
    t1.shipstatus,
	t1.paystatus
	from invoices t1
	inner join customers t2 on t1.customerid=t2.customerid
	inner join staffs t3 on t1.staffid=t3.staffid
	inner join invoicedetails t4 on t1.invoiceid=t4.invoiceid
	inner join methods t5 on t1.methodid=t5.methodid
	where t1.invoiceid=id;
end;

//

delimiter //
create procedure sp_levels_insert(in point int(11),in name varchar(200),in username varchar(10))
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
create PROCEDURE `sp_Logs_delPhysic`(in id int(11),in username varchar(10))
begin
	delete from logs
    where logs.logid =id;
end;//

delimiter //
create procedure sp_Logs_get1()
begin
	select logid,logname,date,note,ReferenceID from logs where isDelete=1 order by logid desc;
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
create procedure sp_ReceiptDetails_insert(in receiptid varchar(15),in bookid varchar(15),
											in amount int(11),in price double,in username varchar(10))
begin
	if(exists (select * from receiptdetails t1 where t1.receiptid = receiptid and t1.bookid = bookid)) then
		begin
			update receiptdetails t1
			set t1.amount=t1.amount+amount,
			t1.price=price
			where t1.receiptid = receiptid and t1.bookid = bookid;
		end;
    ELSE
		begin
			insert into receiptdetails(receiptid,bookid,amount,price)
		values(receiptid,bookid,amount,price);
		end;
	end if;
    insert into logs(logname,date,note,referenceID)
        values ('Insert Receipt Details',curdate(),
				concat('ReceiptID: ',receiptid,' BookID: ',bookid,' Amount: ',amount,' Price: ',price),
				username);
end;//

delimiter //
create procedure sp_ReceiptDetails_update(in receiptid varchar(15),in bookid varchar(15),
											in amount int(11),in price double,in username varchar(15))
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
create procedure sp_ReceiptDetails_delLogic(in receiptid varchar(15),in bookid varchar(15),in username varchar(15))
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
create procedure sp_ReceiptDetails_restore(in receiptid varchar(15),in bookid varchar(15),in username varchar(15))
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
create procedure sp_ReceiptDetails_delPhysic(in receiptid varchar(15),in bookid varchar(15),in username varchar(15))
begin
	declare rdamount int(11);
    declare rdprice double;
    set rdamount = (select receiptdetails.amount from receiptdetails where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid);
    set rdprice = (select receiptdetails.price from receiptdetails where receiptdetails.receiptid=receiptid and receiptdetails.bookid=bookid);
	delete from receiptdetails 
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
create procedure sp_ReceiptDetails_findReceiptID(in receiptid varchar(10))
begin
	select receiptdetails.ReceiptID,BookID,Amount,Price from ReceiptDetails where receiptdetails.receiptid like concat('%',receiptid,'%')
									and isDelete=1;
end;

delimiter //
create procedure sp_ReceiptDetails_findBookID(in bookid varchar(10))
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
    set rcid = concat('Receipt-',LPAD(id,3,'0'));
    update receipts t3 
    set receiptid = rcid
    where t3.ordernumber=id;
    insert into logs(logname,Date,note,referenceID) 	
		values ('Insert Receipts',curdate(),
				concat('ID: ',rcid,' supplierID: ',supplierid,' Date: ',curdate()),
                username);
end;//

delimiter //
create procedure sp_Receipts_update(in id varchar(20),in supplierid varchar(10),in date date,in username varchar(10))
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
create procedure sp_Receipts_delLogic(in id varchar(20),in username varchar(10))
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
     IF( exists (select * from receiptdetails where receiptid = id)) then
		begin
			select * from receiptdetails where receiptid = id;
		end;
	else
		begin
			delete from receipts
			where receipts.receiptid=id;
			insert into logs(logname,Date,note,referenceID)
				values ('Physical Delete Receipts',curdate(),
						concat('ID: ',id,' supplierID: ',rpspid,' Date: ',rpdate),
						username);
        end;
	end if;
end;//

delimiter //
create procedure sp_Receipts_get1()
begin
	select 
    t1.receiptid,
    t2.supplierid,
    t2.suppliername,
    t1.date 
    from Receipts t1
	INNER JOIN suppliers t2 on t1.supplierid=t2.supplierid
    where t1.isDelete=1;
end;//

delimiter //
create procedure sp_Receipts_get0()
begin
	select receiptid,supplierid,date from Receipts where isDelete=0;
end;//

delimiter //
create procedure sp_Receipts_findID(in id varchar(20))
begin
	select 
    t4.SupplierName,
    IFNULL('',t3.bookid) bookid,
    ifnull('',t3.BookTitle) BookTitle,
    ifnull(0,t2.amount) amount,
    ifnull(0,t2.price) price,
    t1.date 
    from Receipts t1
    left join receiptdetails t2 on t1.receiptid=t2.receiptid
    left join books t3 on t2.bookid=t3.bookid
    left join suppliers t4 on t4.supplierid=t1.supplierid
    where t1.receiptID = id and t1.isDelete=1;
end;
//

delimiter //
create procedure `sp_ShipInfors_insert`(in invoiceid varchar(15),in fullname varchar(200),in address varchar(500),in shipdate varchar(20),
									in phone varchar(10), in username varchar(100))
begin
	SELECT STR_TO_DATE(shipdate,'%Y-%m-%d') into @shipDate;
	insert into Shipinfors(invoiceid,fullname,address,phone,shipDate)
    values(invoiceid,fullname,address,phone,@shipDate);
    insert into logs(logname,Date,note,referenceID)
		values ('Insert ShipInfo',curdate(),
				concat('Invoice ID: ',invoiceid,' FullName: ',fullname,' Address: ',address,' Phone: ',phone),
                username);
end//

delimiter //
create procedure sp_ShipInfors_update(in id int(11),in invoiceid varchar(10),in fullname varchar(200),in address varchar(500),
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
create procedure sp_ShipInfors_delLogic(in id int(11),in username varchar(100))
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
create procedure sp_ShipInfors_restore(in id int(11), in username varchar(100))
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
create procedure sp_ShipInfors_delPhysic(in id int(11), in username varchar(10))
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
create procedure sp_ShipInfors_get1()
begin
	select shipinforID,invoiceid,fullname,address,phone from shipinfors where isDelete =1;
end;//

delimiter //
create procedure sp_ShipInfors_get0()
begin
	select shipinforID,invoiceid,fullname,address,phone from shipinfors where isDelete =0;
end;//

delimiter //
create procedure sp_ShipInfors_findID(in id varchar(20))
begin
	SELECT 
	`ShipInforID`,
		`InvoiceID`,
		`FullName`,
		`ShipDate`,
		`Address`,
		`Phone`
	FROM `shipinfors`
	where invoiceId=id and isDelete=1
	order by shipinforID desc limit 1;
end;
//

delimiter //

create procedure `sp_Staffs_insert`(in name varchar(200),in staffusername varchar(10), in password varchar(500),in role bit,in email varchar(50),
									in birthday date, in address varchar(500), in phone varchar(10),in salary double, in departid varchar(20),in username varchar(10))
begin
	declare id int(11);
    declare staffid varchar(15);
	declare sfrole varchar(20);
    set sfrole = (  select case
					when role = 1 then 'Trưởng phòng'
                    when role = 0 then 'Nhân Viên'
					end);
	insert into staffs(staffname,username,password,role,email,birthday,address,phone,salary,departid)
    values(name,staffusername,password,role,email,birthday,address,phone,salary,departid);
    set id = (select ordernumber from staffs t3 order by ordernumber desc limit 1);
    set staffid = concat('Staff-',LPAD(id,3,'0'));
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
create procedure `sp_Staffs_update`(in id varchar(20),in name varchar(200),in staffusername varchar(10),in role bit,
								in email varchar(50),in birthday date, in address varchar(500), in phone varchar(10),in salary double, in departid varchar(10),in username varchar(10))
begin
	declare sfname varchar(200);
    declare sfroleb varchar(20);
    declare sfrolea varchar(20);
    declare sfemail varchar(50);
    declare sfbirthday date;
    declare sfaddress varchar(500);
    declare sfphone varchar(10);
    declare sfsalary double;
    declare sfdepartid varchar(10	);
    set sfname = (select staffs.staffname from staffs where staffs.staffid= id);
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
    set a.staffname = name, a.role = role, a.email=email, 
		a.birthday= birthday, a.address = address, a.phone=phone, a.salary=salary, a.departid=departid
	where a.staffid= id;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Staffs',curdate(),
				concat('Update from Name: ',sfname,' Username: ',staffusername,' Role: ',sfroleb,' Email: ',sfemail,' Birthday: ',sfbirthday,
						' Address: ',sfaddress,' Phone: ',sfphone,' Salary: ',sfsalary,' DepartID: ',sfdepartid,' To ',
                        'Name: ',name,' Username: ',staffusername,' Role: ',sfrolea,' Email: ',email,' Birthday: ',birthday,
						' Address: ',address,' Phone: ',phone,' Salary: ',salary,' DepartID: ',departid),
                username);
end;//

DELIMITER //
CREATE PROCEDURE sp_Staffs_getUsername()
BEGIN
	select username from staffs;
END
//

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
create procedure sp_Staffs_delPhysic(in id varchar(20), in username varchar(10))
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
	IF( exists (select * from invoices where staffid = id)) then
		begin
			select * from invoices where staffid = id;
		end;
	else
		begin
			delete from staffs
			where staffs.staffid=id;
			insert into logs(logname,Date,note,referenceID)
				values ('Physical Delete Staffs',curdate(),
						concat('ID: ',id,' Name: ',sfname,' Username: ',sfusername,' Password: ',sfpassword,
								' Role: ',sfrole,' Email:',sfemail,' Birthday: ',sfbirthday,' Address: ',sfaddress,' Phone: ',sfphone,
								' Salary: ',sfsalary,' DepartID: ',sfdepartid),
						username);
        end;
	end if;
end;//


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
create procedure sp_Staffs_findID(in id varchar(20))
begin
	select username into @id from staffs t1 where t1.staffid=id;
	select IFNULL(file_name,'') into @image
    from image t1 where t1.refID = @id
    order by id desc
    limit 1;
	select 
	staffid,
	staffname,
	t1.birthday,
	@image image,
	email,
	address,
	phone,
	username,
	password,
	t2.departid ,
	t1.role,
    t1.salary
	from staffs t1
	inner join departs t2 on t1.departID=t2.departID
 	where staffid =id and  t1.isDelete =1;
end;
//

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

DELIMITER //
DROP PROCEDURE IF EXISTS `sp_Image_save` //
CREATE PROCEDURE `sp_Image_save`(pRefID varchar(50),pFileName varchar(500),pFileType varchar(50),pData longblob)
begin
    INSERT INTO `image`
	(`refID`,
	`file_name`,
	`file_type`,
	`content`)
	VALUES
		(pRefID,
		pFileName,
		pFileType,
		pData);
end;//

DELIMITER //
DROP PROCEDURE IF EXISTS `sp_Image_get` //
CREATE PROCEDURE `sp_Image_get`(pRefID varchar(50))
BEGIN
	select id,refID,file_name,file_type,content
    from image t1 where t1.refID = pRefID 
    order by id desc
    limit 1;
END;

DELIMITER //
DROP PROCEDURE IF EXISTS `sp_Image_download` //
CREATE PROCEDURE `sp_Image_download`(pRefID varchar(50))
BEGIN
	select id,refID,file_name,file_type,content
    from image t1 where t1.refID = pRefID 
    order by id desc
    limit 1;
END; //

DELIMITER //
CREATE PROCEDURE sp_Staffs_change_password(in id varchar(20),in newPassword varchar(100))
BEGIN
	update staffs
    SET password = newPassword
    where staffID = id;
END
//



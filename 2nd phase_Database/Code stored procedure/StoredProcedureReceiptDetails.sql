delimiter //

create procedure sp_ReceiptDetails_insert(in receiptid varchar(10),in bookid varchar(10),
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
create procedure sp_ReceiptDetails_update(in receiptid varchar(10),in bookid varchar(10),
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
create procedure sp_ReceiptDetails_delLogic(in receiptid varchar(10),in bookid varchar(10),in username varchar(10))
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
create procedure sp_ReceiptDetails_restore(in receiptid varchar(10),in bookid varchar(10),in username varchar(10))
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
create procedure sp_ReceiptDetails_delPhysic(in receiptid varchar(10),in bookid varchar(10),in username varchar(10))
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
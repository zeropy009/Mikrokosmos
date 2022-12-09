delimiter //

create procedure sp_InvoiceDetails_insert(in invoiceid varchar(10),in bookid varchar(10),in quantity int(11),
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
create procedure sp_InvoiceDetails_update(in invoiceid varchar(10),in bookid varchar(10),in quantity int(11),
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
create procedure sp_InvoiceDetails_delPhysic(in invoiceid varchar(10),in bookid varchar(10),in username varchar(10))
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
create procedure sp_InvoiceDetails_findInvoiceID(in invoiceid varchar(10))
begin
	select invoicedetails.invoiceid,bookid,quantity,discount from invoicedetails where invoicedetails.invoiceid like concat('%',invoiceid,'%')
								and isDelete=1;
end;
//

delimiter //
create procedure sp_InvoiceDetails_findBookID(in bookid varchar(10))
begin
	select invoiceid,invoicedetails.bookid,quantity,discount from invoicedetails where invoicedetails.bookid  like concat('%',bookid,'%')
								and isDelete=1;
end;
//
delimiter //
create procedure sp_Receipts_insert(in supplierid varchar(15),in username varchar(10))
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
create procedure sp_Receipts_update(in id varchar(10),in supplierid varchar(10),in date date,in username varchar(10))
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
create procedure sp_Receipts_delLogic(in id varchar(10),in username varchar(10))
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
create procedure sp_Receipts_restore(in id varchar(10),in username varchar(10))
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
create procedure sp_Receipts_delPhysic(in id varchar(10),in username varchar(10))
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
create procedure sp_Receipts_findID(in id varchar(10))
begin
	select receiptid,supplierid,date from Receipts where receiptID like concat('%',id,'%') and isDelete=1;
end;
//
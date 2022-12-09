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
create procedure sp_Invoices_update(in id varchar(10),in solddate date,in shipdate date,in payStatus varchar(200),in shipstatus varchar(200),
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
create procedure sp_Invoices_delPhysic(in id varchar(10),in username varchar(10))
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
create procedure sp_Invoices_findID(in id varchar(10))
begin
	select invoiceid,solddate,shipdate,shipstatus,paystatus,methodid,staffid,customerid from Invoices where invoiceID like concat('%',id,'%') and IsDelete =1;
end;

//
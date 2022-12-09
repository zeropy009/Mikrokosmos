delimiter //

create procedure sp_ShipInfo_insert(in invoiceid varchar(10),in fullname varchar(200),in address varchar(500),
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
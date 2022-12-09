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
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
create procedure sp_Logs_delPhysic(in id int(11), in username varchar(10))
begin
	delete from logs
    where logs.logid =id;
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
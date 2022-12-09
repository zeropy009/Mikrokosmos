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
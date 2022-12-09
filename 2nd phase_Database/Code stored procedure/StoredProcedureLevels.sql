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
select * from logs;
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
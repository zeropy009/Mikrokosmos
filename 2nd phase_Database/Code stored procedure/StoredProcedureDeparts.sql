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
	delete from departs where departid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Delete Physical Depart',curdate(),
        concat('ID: ',id,' Name: ',dpname),
        username);
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




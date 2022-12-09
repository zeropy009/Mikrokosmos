delimiter //
create procedure sp_Categories_insert(in pname varchar(200),in pusername varchar(10))
begin
	declare id int(11);
    declare cgid varchar(15);
	insert into categories(categoryname)
    values (pname);
    set id = (select ordernumber from categories t3 order by ordernumber desc limit 1);
    set cgid = concat('Category-0',id);
    update categories t3 
    set categoryid = cgid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Category',curdate(),concat('ID: ',cgid,' Name: ',pname),pusername);
end;//

delimiter //
create procedure sp_Categories_update(in pid varchar(10),in pname varchar(200),in pusername varchar(10))
begin
    declare vcagename varchar(200);
    set vcagename = (select categoryname from categories where categoryid = pid);
    update categories a
    set a.categoryname = pname
    where a.categoryid = pid;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Category',curdate(),
				concat('Update from ID: ',pid,' Name: ',vcagename,' To',
											'ID: ',pid,' Name: ',pname)
				,pusername);
end;//

delimiter //
create procedure sp_Categories_delLogic(in id varchar(10),in username varchar(10))
begin
	update categories
    set isDelete=0
    where categoryid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Delete Logic Category',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Categories_restore(in id varchar(10),in username varchar(10))
begin
	update categories
    set isDelete=1
    where categoryid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Restore Category',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Categories_delPhysic(in id varchar(10),in username varchar(10))
begin
	declare vcagename varchar(200);
    set vcagename = (select categoryname from categories where categoryid = id);
	delete from categories
    where categoryid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Physical Delete Category',curdate(),
        concat('ID: ',id,' Name: ',vcagename),
        username);
end;//

delimiter //
create procedure sp_Categories_get1()
begin
	select categoryid,categoryname from categories  where isDelete=1;
end;//

delimiter //
create procedure sp_Categories_get0()
begin
	select categoryid,categoryname from categories  where isDelete=0;
end;//

delimiter //
create procedure sp_Categories_findID(in id varchar(10))
begin
	select categoryid,categoryname from categories  where categoryid like concat('%',id,'%') and isDelete=1;
end;

//


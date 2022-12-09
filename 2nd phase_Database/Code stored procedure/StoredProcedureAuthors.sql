delimiter //
create procedure sp_Authors_insert(in name varchar(200),in username varchar(10))
begin
	declare id int(11);
    declare atid varchar(15);
	insert into authors (authorname)
    values (name);
    set id = (select ordernumber from authors t3 order by ordernumber desc limit 1);
    set atid = concat('Author-0',id);
    update authors t3 
    set authorid = atid
    where t3.ordernumber=id;
	insert into logs(logname,Date,note,referenceID)
	values ('Insert Producers',curdate(),
			concat('ID: ',atid,' Name: ',name),
				username);
end;//

delimiter //
create procedure sp_Authors_update(in id varchar(10),in name varchar(200),in username varchar(10))
begin
    declare atname varchar(200);
    set atname = (select authors.AuthorName from authors where authors.authorid=id);
    update authors a
    set a.authorname = name
    where a.AuthorID = id;
	insert into logs(logname,Date,note,referenceID)
	values ('Update Authors',curdate(),
		concat('Update from ID: ',id,' Name: ',atname,' To',
				' Name: ',name,' Nation: '),
		username);
end;//

delimiter //
create procedure sp_Authors_delLogic(in id varchar(10),in username varchar(10))
begin
    update authors
    set isDelete=0
    where authors.AuthorID = id;
    insert into logs(logname,date,note,referenceID)
        values ('Logical Delete Author',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Authors_Restore(in id varchar(10),in username varchar(10))
begin
    update authors
    set isDelete=1
    where authors.AuthorID = id;
    insert into logs(logname,date,note,referenceID)
        values ('Restore Author',curdate(),
        concat('ID: ',id),
        username);
end;//

delimiter //
create procedure sp_Authors_delPhysic(in id varchar(10),in username varchar(10))
begin
	declare atname varchar(200);
    set atname = (select authors.AuthorName from authors where authors.authorid=id);
    delete from authors
    where authorid = id;
    insert into logs(logname,date,note,referenceID)
        values ('Physical Delete Author',curdate(),
        concat('ID: ',id,' Name: ',atname),
        username);
end; //

delimiter //
create procedure sp_Authors_get1()
begin
	select authorid,authorname from Authors where isDelete=1;
end;//

delimiter //
create procedure sp_Authors_get0()
begin
	select authorid,authorname from Authors where isDelete=0;
end;//

delimiter //
create procedure sp_Authors_findID(in id varchar(10))
begin
	select authorid,authorname from Authors where authorid like concat('%',id,'%')  and isDelete=1;
end;

//
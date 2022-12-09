delimiter //
create procedure sp_Books_insert(in title varchar(200),in image varchar(500),
									in description varchar(500),in categoryid varchar(15),in authorid varchar(15),in username varchar(10))
begin
	declare id int(11);
    declare bkid varchar(15);
	insert into books(booktitle,amount,image,description,categoryid,authorid)
    values(title,0,image,description,categoryid,authorid);
    set id = (select ordernumber from books t3 order by ordernumber desc limit 1);
    set bkid = concat('Book-0',id);
    update books t3 
    set bookid = bkid
    where t3.ordernumber=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Insert Books',curdate(),
				concat('ID: ',bkid,' Title: ',title,' Amount: ',0,' Image: ',Image,' Description: ',description,
						' CategoryID: ',categoryid,' AuthorID: ',authorid),
                username);
end;//

delimiter //
create procedure sp_Books_update(in id varchar(10),in title varchar(200),in amount int(11),in image varchar(500),
									in description varchar(500),in categoryid varchar(10),in authorid varchar(10),in username varchar(10))
begin
	declare bname varchar(200);
    declare bimage varchar(500);
    declare bdescription varchar(500);
    declare bcategoryid varchar(10);
    declare bauthorid varchar(10);
    set bname = (select books.booktitle from books where books.bookid=id);
    set bimage = (select books.image from books where books.bookid=id);
    set bdescription = (select books.description from books where books.bookid=id);
    set bcategoryid = (select books.categoryid from books where books.bookid=id);
    set bauthorid = (select books.authorid from books where books.bookid=id);
	update books a
    set a.booktitle = title, a.image = image, a.description= description,
		a.categoryid=categoryid, a.authorid=authorid
	where a.bookid=id ;
    insert into logs(logname,Date,note,referenceID)
		values ('Update Books',curdate(),
				concat('Update from ID: ',id,' Name: ',bname,' Amount: ',amount,' Image: ',bimage,' Description: ',bdescription,
						' CategoryID: ',bcategoryid,' AuthorID: ',bauthorid,' To ',
                        'ID: ',id,' Name: ',title,' Amount: ',amount,' Image: ',Image,' Description: ',description,
						' CategoryID: ',categoryid,' AuthorID: ',authorid),
                username);
end;//

delimiter //
create procedure sp_Books_delLogic(in id varchar(10),in username varchar(10))
begin
	update books
    set isDelete=0
    where books.bookid=id;
    insert into logs(logname,Date,note,referenceID)
		values ('Logical Delete Books',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Books_restore(in id varchaR(10),in username varchar(10))
begin
	update books
    set isDelete=1
    where books.bookid = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Restore Books',curdate(),
				concat('ID: ',id),
                username);
end;//

delimiter //
create procedure sp_Books_delPhysic(in id varchar(10),in username varchar(10))
begin
	declare bname varchar(200);
    declare bimage varchar(500);
    declare bamount int(11);
    declare bdescription varchar(500);
    declare bcategoryid varchar(10);
    declare bauthorid varchar(10);
    set bname = (select books.booktitle from books where books.bookid=id);
    set bimage = (select books.image from books where books.bookid=id);
    set bamount = (select books.amount from books where books.BookID=id);
    set bdescription = (select books.description from books where books.bookid=id);
    set bcategoryid = (select books.categoryid from books where books.bookid=id);
    set bauthorid = (select books.authorid from books where books.bookid=id);
	delete from books
    where books.BookID = id;
    insert into logs(logname,Date,note,referenceID)
		values ('Physical Delete Book',curdate(),
				concat('ID: ',id,' Name: ',bname,' Amount: ',bamount,' Image: ',bimage,' Description: ',bdescription,
						' CategoryID: ',bcategoryid,' AuthorID: ',bauthorid),
                username);
end;//

delimiter //
create procedure sp_Books_get1()
begin
	select `bookid`,
		booktitle,
        amount,
        image,
        description,categoryid,authorid from Books where isDelete=1;
end;//

delimiter //
create procedure sp_Books_get0()
begin
	select bookid,booktitle,amount,image,description,categoryid,authorid from Books where isDelete=0;
end;//

delimiter //
create procedure sp_Books_findID(in id varchar(10))
begin
	select IFNULL(file_name,'') into @image
    from image t1 where t1.refID = id
    order by id desc
    limit 1;
	select ifnull(price,0) into @price
	from historyprices where bookid = id
	select 
		t1.`bookid`,
		t1.booktitle,
        t1.amount,
        @image image,
		@price price,
        t1.description,
		t2.categoryname,
		t3.AuthorName
		from Books t1
		inner join categories t2 on t1.categoryid=t2.categoryid
		inner join authors t3 on t1.authorid=t3.authorid
		where isDelete=1;
end;
//
delimiter //
create procedure sp_HistoryPrices_insert(in bookid varchar(10),in price double,in username varchar(10))
begin
	insert into historyprices(bookid,startdate,price)
    values(bookid,curdate(),price);
    insert into logs(logname,date,note,referenceID)
        values ('Insert History Price',curdate(),
				concat('BookID: ',bookid,' Date: ',curdate(),' Price:',price),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_delLogic(in id int(11),in username varchar(10))
begin
	update historyprices
    set isDelete=0
    where historyprices.historypriceid=id;
    insert into logs(logname,date,note,referenceID)
        values ('Logical Delete History Price',curdate(),
				concat('ID: ',id),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_restore(in id int(11),in username varchar(10))
begin
	update historyprices
    set isDelete=1
    where historyprices.historypriceid=id;
    insert into logs(logname,date,note,referenceID)
        values ('Restore History Price',curdate(),
				concat('ID: ',id),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_delPhysic(in id int(11),in username varchar(10))
begin
	declare hpbookid varchar(10);
    declare hpstartdate date;
    declare hpprice double;
    set hpbookid = ( select historyprices.historypriceid from historyprices where historyprices.historypriceid = id);
    set hpstartdate = ( select historyprices.StartDate from historyprices where historyprices.historypriceid = id);
    set hpprice = (select historyprices.Price from historyprices where historyprices.historypriceid = id);
	delete from historyprices
    where historyprices.historypriceid=id;
    insert into logs(logname,date,note,referenceID)
        values ('Physical Delete History Price',curdate(),
				concat('ID: ',id,' BookID: ',hpbookid,' Date: ',hpstartdate,' Price:',hpprice),
				username);
end;//

delimiter //
create procedure sp_HistoryPrices_get1()
begin
	select HistoryPriceID,bookid,startdate,price from historyprices where isDelete=1;
end;//

delimiter //
create procedure sp_HistoryPrices_get0()
begin
	select HistoryPriceID,bookid,startdate,price from historyprices where isDelete=0;
end;//

delimiter //
create procedure sp_HistoryPrices_findID(in id int(11))
begin
	select HistoryPriceID,bookid,startdate,price from historyprices where historypriceid like concat('%',id,'%') and isDelete=1;
end;
//
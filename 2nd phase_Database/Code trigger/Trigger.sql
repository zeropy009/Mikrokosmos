/*
	Author @HoangTong
    Database_Trigger
*/

/* 
	Check Receipts.Date <= Current Date 
*/
delimiter $$
create trigger trg_Receipts_Date before insert on Receipts
for each row
begin
	if (New.Date > curdate()) then
		signal sqlstate '45000' 
		set message_text = 'Date must be earlier than or equal to current date';
	end if;
end $$

/* Check HistoryPrices.StartDate <= Current Date */
delimiter $$
create trigger trg_HistoryPrices_Date before insert on HistoryPrices
for each row
begin
	if (New.StartDate > curdate()) then
		signal sqlstate '45000' 
		set message_text = 'Start date must be earlier than or equal to current date';
	end if;
end $$

/* 
	Check Logs.Date <= Current Date 
*/
delimiter $$
create trigger trg_Logs_Date before insert on Logs
for each row
begin
	if (New.Date > curdate()) then
		signal sqlstate '45000' 
		set message_text = 'Date must be earlier than or equal to current date';
	end if;
end $$

/* 
	Check Invoices.SoldDate <= Current Date 
    Check Invoices.ShipDate >= Current Date
*/
delimiter $$
create trigger trg_Invoices_Date before insert on Invoices
for each row
begin
	if (New.SoldDate > curdate()) then
		signal sqlstate '45000' 
		set message_text = 'Sold date must be earlier than or equal to current date';
	end if;
    
    if (New.ShipDate < curdate()) then
		signal sqlstate '45000' 
		set message_text = 'Ship date must be greater than or equal to current date';
	end if;
end $$

/* 
	Check Age >= 22 
*/
delimiter $$
create trigger trg_Staffs_Age before insert on Staffs
for each row
begin
	if (year(New.Birthday) > year(curdate()) - 22) then
		signal sqlstate '45000'
		set message_text = 'Age must be over 21';
	end if;
end $$

/* 
	Check HistoryPoints.Date <= Current Date 
*/
delimiter $$
create trigger trg_HistoryPoints_Date before insert on HistoryPoints
for each row
begin
	if (New.Date > curdate()) then
		signal sqlstate '45000' 
		set message_text = 'Date must be earlier than or equal to current date';
	end if;
end $$

/* 
	Check InvoiceDetails.Quantity < Books.Amount 
*/
delimiter $$
create trigger trg_InvoiceDetails_Quantity before insert on InvoiceDetails
for each row
begin
	if (New.Quantity > (select Amount from Books where BookID = New.BookID)) then
		signal sqlstate '45000' 
		set message_text = 'Quantity must be less than quantity of books';
	end if;
end $$

/* 
	Book.Amount += ReceiptsDetails.Amount 
*/
delimiter $$
create trigger trg_Books_PlusAmount after insert on ReceiptDetails
for each row
begin
	update Books, ReceiptDetails
    set Books.Amount = Books.Amount + ReceiptDetails.Amount
    where Books.BookID = ReceiptDetails.BookID;
end $$

/* 
	Books.Amount -= InvoiceDetails.Quantity 
*/
delimiter $$
create trigger trg_Books_MinusAmount after insert on InvoiceDetails
for each row
begin
	update Books, InvoiceDetails
    set Books.Amount = Books.Amount - InvoiceDetails.Quantity
    where Books.BookID = InvoiceDetails.BookID;
end $$
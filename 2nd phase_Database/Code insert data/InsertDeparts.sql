/*
	Author: @ThanhHang
*/
-- ===================================================================================

 -- USE Mikrokosmos;

-- ===================================================================================

Call sp_Departs_insert ('Kho', 'abc');
Call sp_Departs_insert ('Nhân sự', 'abc');
Call sp_Departs_insert ('Kinh doanh', 'abc');
Call sp_Departs_insert ('IT', 'abc');

select * from departs;
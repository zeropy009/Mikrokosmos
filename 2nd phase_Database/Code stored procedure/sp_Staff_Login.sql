DELIMITER // 
DROP PROCEDURE IF EXISTS `sp_Staffs_login` //
CREATE PROCEDURE `sp_Staffs_login` (in username varchar(20))
begin
	select	
		t1.username,
        t1.password,
		case
        when t1.role = 0 then 'USER'
        when t1.role = 1 then 'ADMIN'
        END as role,
        t2.departid as depart_id
    FROM staffs t1
    INNER JOIN departs t2 on t1.departid=t2.departid
    where t1.username=username;
end;
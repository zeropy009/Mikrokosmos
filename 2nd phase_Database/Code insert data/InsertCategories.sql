USE Mikrokosmos;

--
Call sp_Categories_insert ('Tâm lý', 'abc');
Call sp_Categories_insert ('Kỹ năng', 'abc');
Call sp_Categories_insert ('Văn học', 'abc');
Call sp_Categories_insert ('Kinh tế', 'abc');
Call sp_Categories_insert ('Sách thiếu nhi', 'abc');
Call sp_Categories_insert ('Giáo trình', 'abc');
Call sp_Categories_insert ('Văn hóa - Xã hội', 'abc');
Call sp_Categories_insert ('Khoa học - Công nghệ', 'abc');

select * from categories;
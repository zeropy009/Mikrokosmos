/*
	Author: @ThanhHang
*/
-- ===================================================================================

-- USE Mikrokosmos;

-- ===================================================================================

Call sp_Suppliers_insert ('NXB Hồng Đức', '65, Tràng Thi, Hà Nội ', '02439260024', 'hongduc@gmail.com', 'abc');
Call sp_Suppliers_insert ('NXB Trẻ', '161B Lý Chính Thắng, Phường 7, Quận 3 , TP. Hồ Chí Minh', '038437450', 'hopthubandoc@nxbtre.com.vn', 'abc');
Call sp_Suppliers_insert ('NXB Tri Thức', 'Tầng 1 - Tòa nhà VUSTA - 53 Nguyễn Du - Quận Hai Bà Trưng - Hà Nội - Việt Nam', '039447280 ', 'lienhe@nxbtrithuc.com.vn', 'abc');
Call sp_Suppliers_insert ('NXB Kim Đồng', '55 Quang Trung, Nguyễn Du, Hai Bà Trưng, Hà Nội', '02439434490', 'info@nxbkimdong.com.vn', 'abc');
Call sp_Suppliers_insert ('NXB Thế Giới', '46 Trần Hưng Đạo, Hoàn Kiếm Hà Nội', '02438253841', 'thegioi@thegioipublishers.vn', 'abc');
Call sp_Suppliers_insert ('NXB Giáo Dục', 'Số 81 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '02438220554', 'tchc.nxbgdhn@gmail.com', 'abc');
Call sp_Suppliers_insert ('NXB Tổng Hợp', '62 Nguyễn Thị Minh Khai, phường Đa Kao, quận 1, TPHCM', '038256804', 'tonghop@nxbhcm.com.vn', 'abc');
Call sp_Suppliers_insert ('NXB Văn Học', '18 Nguyễn Trường Tộ - Ba Đình - Hà Nội', '02437163409', 'info@nxbvanhoc.com.vn', 'abc');
Call sp_Suppliers_insert ('NXB Đà Nẵng', 'Số 3 đường 30 tháng 4, P.Hòa Cường Bắc, Q.Hải Châu, TP.Đà Nẵng', '02363797814', 'xuatban@nxbdanang.vn', 'abc');
Call sp_Suppliers_insert ('NXB Văn hóa - Văn nghệ', 'Số 88-90 đường Ký Con, phường Nguyễn Thái Bình, Quận 1, Tp.HCM', '02838216009', 'nxbvhvn@nxbvanhoavannghe.org.vn', 'abc');
Call sp_Suppliers_insert ('NXB Dân Trí', 'Số 347 Đội Cấn, quận Ba Đình, Hà Nội', '0466860751', 'nxbdantri@gmail.com', 'abc');
Call sp_Suppliers_insert ('NXB Thanh Niên', '64 Bà Triệu, Hoàn Kiếm, Hà Nội', ' 0462631720', 'thanhnien@gmail.com', 'abc');
Call sp_Suppliers_insert ('NXB Hội Nhà Văn', 'Số 65 Nguyễn Du, Quận Hai Bà Trưng, thành phố Hà Nội', '024382 22135', 'nxbhoinhavan65@gmail.com', 'abc');
Call sp_Suppliers_insert ('NXB Lao Động', '175 Giảng Võ, Q. Đống Đa, Hà Nội', '0438515380', 'nxblaodong@vnn.vn', 'abc');
Call sp_Suppliers_insert ('NXB Phụ Nữ Việt Nam', '39 Hàng Chuối, Q. Hai Bà Trưng, Hà Nội', '02439710717', 'truyenthongvaprnxbpn@gmail.com', 'abc');

select * from suppliers;
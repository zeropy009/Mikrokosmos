drop database if exists mikrokosmos ;

create database Mikrokosmos charset utf8mb4 COLLATE utf8mb4_unicode_ci;

use Mikrokosmos;


/* Table Logs */
create table Logs
(
	LogID int(11) auto_increment primary key,
	LogName varchar(50) not null,
	Date datetime not null,
	Note varchar(3000) not null,
	ReferenceID varchar(20) not null,
	IsDelete bit default 1
);

/* Table Levels */
create table Levels(
	LevelID int(11) auto_increment primary key,
	Point int,
	Name varchar(200) not null,
	IsDelete bit default 1
);

/* Table Categories */
create table Categories
(
	OrderNumber int(11) auto_increment primary key,
	CategoryID varchar(15) unique,
	CategoryName varchar(200) not null,
	IsDelete bit default 1
);

/* Table Suppliers */
create table Suppliers(
	OrderNumber int(11) auto_increment primary key,
	SupplierID varchar(15) unique,
	SupplierName varchar(200) not null,
	Address varchar(500),
	Phone varchar(12),
	Email varchar(50),
	IsDelete bit default 1
);

/* Table Authors */
create table Authors
(
	OrderNumber int(11) auto_increment primary key,
	AuthorID varchar(15) unique,
	AuthorName varchar(200) not null,
	IsDelete bit default 1
);

/* Table Departs */
create table Departs
(
	OrderNumber int(11) auto_increment primary key,
	DepartID varchar(15) unique,
	DepartName varchar(200) not null,
	IsDelete bit default 1
);

/* Table Products */
create table Books(
	OrderNumber int(11) auto_increment primary key,
	BookID varchar(15) unique,
	BookTitle varchar(200) not null,
    Amount int default 0,
    Image varchar(500),
	Description varchar(3000) not null,
    CategoryID varchar(15) not null references Categories(CategoryID) on delete cascade,
    AuthorID varchar(15) not null references Authors(AuthorID) on delete cascade,
	IsDelete bit default 1
);


/* Table HistoryPrices */
create table HistoryPrices
(
	HistoryPriceID int auto_increment primary key,
	BookID varchar(10) not null references Books(BookID) on delete cascade,
	StartDate date not null,
	Price double not null check (Price > 0),
	IsDelete bit default 1
);


/* Table Staffs */
create table Staffs(
	OrderNumber int auto_increment primary key,
	StaffID varchar(15) unique,
	StaffName varchar(200) not null,
	Image varchar(500),
	Username varchar(20) unique,
	Password varchar(50) not null,
	Role bit not null,
    Email varchar(50) not null,
	Birthday date not null,
	Address varchar(500) not null,
	Phone varchar(10) not null,
    Salary real not null,
	DepartID varchar(15) not null,
	foreign key (DepartID) references Departs(DepartID) on delete cascade,
	IsDelete bit default 1
);

/* Table Customers */
create table Customers(
	OrderNumber int auto_increment primary key,
	CustomerID varchar(15) unique,
	CustomerName varchar(200) not null,
    Image varchar(500),
	Email varchar(50) not null,
	Address varchar(500) not null,
	Phone varchar(10) not null,
    Password varchar(30) not null,
	IsDelete bit default 1
);

/* Table HistoryPoints */
create table HistoryPoints(
	HistoryPointID int auto_increment primary key,
	CustomerID varchar(15) not null,
	foreign key (CustomerID) references Customers(CustomerID) on delete cascade,
	Date datetime not null,
    Point int not null check (Point > -1),
	IsDelete bit default 1
);


/* Table Receipts */
create table Receipts(
	OrderNumber int(11) auto_increment primary key,
	ReceiptID varchar(15) unique,
	SupplierID varchar(15) not null references Suppliers(SupplierID) on delete cascade,
	Date date not null,
	IsDelete bit default 1
);


/* Table ReceiptDetails */
create table ReceiptDetails
(
	ReceiptID varchar(15) not null references Receipts(ReceiptID) on delete cascade,	
	BookID varchar(15) not null references Books(BookID) on delete cascade,
	primary key (ReceiptID, BookID),	
	Amount int not null check (Amount > 0),
	Price double not null check (Price > 0),
	IsDelete bit default 1
);

/* Table Methods */
create table Methods
(
	MethodID int auto_increment primary key,
	MethodName varchar(200) not null,
	Note nvarchar(500),
	IsDelete bit default 1
);

/* Table Invoices */
Create table Invoices
(
	OrderNumber int(11) auto_increment primary key,
	InvoiceID varchar(15) unique,
	SoldDate date not null,
	ShipDate date not null,
	ShipStatus varchar(200) not null,
	PayStatus varchar(200) not null,
	MethodID int not null references Methods(MethodID) on delete cascade,
	StaffID varchar(15) not null references Staffs(StaffID) on delete cascade,	
	CustomerID varchar(15) null references Customers(CustomerID) on delete cascade,
    IsDelete bit default 1
);

/* Table ShipInfo */
create table ShipInfors(
	ShipInforID int auto_increment primary key,
	InvoiceID varchar(15) not null references Invoices(InvoiceID) on delete cascade,
	FullName varchar(200) not null,
	Address varchar(500) not null,
	Phone varchar(10) not null,
	IsDelete bit default 1
);

/* Table InvoiceDetails */
create table InvoiceDetails(
	InvoiceID varchar(15) not null references Invoices(InvoiceID) on delete cascade,
	BookID varchar(15) not null references Books(BookID) on delete cascade,
	primary key (InvoiceID, BookID),
	Quantity int not null check (Quantity > 0) ,
	Discount float not null check (Discount >= 0),
	IsDelete bit default 1
);

/* Table for Image */
create table image(
	id int(11) primary key auto_increment,
	refID varchar(50),
    file_name varchar(500),
    file_type varchar(50),
    content longblob
);
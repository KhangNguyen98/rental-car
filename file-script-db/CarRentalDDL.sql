CREATE DATABASE CarRental
COLLATE Latin1_General_CI_AS
GO

USE CarRental
GO

CREATE TABLE Role
(
	id int PRIMARY KEY,
	name varchar(30) NOT NULL
)
GO


CREATE TABLE Account
(
	email varchar(320) PRIMARY KEY,
	password varchar(50) NOT NULL,
	roleID int FOREIGN KEY REFERENCES Role(id) NOT NULL,
	phoneNumber char(10) NOT NULL,
	fullName varchar(50) NOT NULL,
	address varchar(150) NOT NULL,
	createDate bigint NOT NULL,
	status int NOT NULL
)
GO

CREATE TABLE Token
(
	id int IDENTITY PRIMARY KEY,
	tokenString char(5) NOT NULL,
	email varchar(320) FOREIGN KEY REFERENCES Account(email) NOT NULL,
	activate int NOT NULL DEFAULT 0
)
GO

CREATE TABLE Category
(
	id int PRIMARY KEY,
	name nvarchar(50) NOT NULL
)
GO

CREATE TABLE Car
(
	id int PRIMARY KEY ,
	name nvarchar(50) NOT NULL,
	color varchar(50) NOT NULL,
	year int NOT NULL,
	categoryID int FOREIGN KEY REFERENCES Category(id) NOT NULL,
	price float NOT NULL,
	CONSTRAINT chkprice CHECK(price > 0),
	quantity int NOT NULL,
	CONSTRAINT chkQuantity CHECK(quantity > 0),
	status int  NOT NULL
)
GO

CREATE TABLE Discount
(
	id varchar(10) PRIMARY KEY,
	status int NOT NULL,
	rate int NOT NULL,
	CONSTRAINT chkDiscount CHECK(rate > 0 AND  rate <= 100),
	expiredDate bigint NOT NULL
)
GO

CREATE TABLE Rental
(
	id int PRIMARY KEY IDENTITY,
	accountID varchar(320) FOREIGN KEY REFERENCES Account(email) NOT NULL,
	rentalDate bigint NOT NULL,
	returnDate bigint NOT NULL,
	confirmReturnDate bigint,
	total float NOT NULL,
	discountID varchar(10) FOREIGN KEY REFERENCES Discount(id) ,
	status int  NOT NULL
)
GO

CREATE TABLE RentalDetail
(
	id int PRIMARY KEY IDENTITY,
	rentalID int FOREIGN KEY REFERENCES Rental(id),
	carID int FOREIGN KEY REFERENCES Car(id),
	quantity int NOT NULL,
	CONSTRAINT chkQuantityInRental CHECK(quantity > 0),
	price float NOT NULL,
	CONSTRAINT chkPriceInRental CHECK(price > 0),
	name nvarchar(50) NOT NULL,
	color varchar(50) NOT NULL,
	year int NOT NULL, 
	category nvarchar(50) NOT NULL
)
GO

CREATE TABLE Feedback
(
	id int PRIMARY KEY IDENTITY,
	accountID varchar(320) FOREIGN KEY REFERENCES Account(email) NOT NULL,
	carID int FOREIGN KEY REFERENCES Car(id) NOT NULL,
	rentalDetailID int FOREIGN KEY REFERENCES RentalDetail(id) NOT NULL,
	rate int NOT NULL,
	CONSTRAINT chkRate check (rate >= 0 AND rate <=10),
	content nvarchar(500) NOT NULL
)
GO

CREATE TABLE CartDetail
(
	id int PRIMARY KEY IDENTITY,
	accountID varchar(320) FOREIGN KEY REFERENCES Account(email) NOT NULL,
	carID int FOREIGN KEY REFERENCES Car(id),
	quantity int NOT NULL,
	CONSTRAINT chkQuantityInCart CHECK(quantity > 0),
	price float NOT NULL,
	CONSTRAINT chkPriceInCart CHECK(price > 0)
)
GO

CREATE TRIGGER trg_UpdateQuantity ON RentalDetail AFTER INSERT  AS
BEGIN
	UPDATE Car SET Car.quantity = Car.quantity - ( SELECT inserted.quantity FROM inserted WHERE carID = Car.id) FROM Car JOIN inserted ON Car.id = inserted.carID;
	DELETE CartDetail WHERE CartDetail.carID = (SELECT inserted.carID FROM inserted) AND CartDetail.accountID = 
	(SELECT Rental.accountID FROM Rental JOIN inserted ON Rental.id = inserted.rentalID) ;
	UPDATE Discount SET status = 0 WHERE Discount.id = (SELECT Rental.discountID FROM Rental WHERE Rental.id = (SELECT inserted.rentalID FROM inserted))
END
GO

CREATE TRIGGER trg_CancelOrder ON Rental AFTER UPDATE AS
BEGIN 
	SELECT carID INTO TempTable FROM RentalDetail JOIN Car ON RentalDetail.carID = Car.id WHERE RentalDetail.rentalID = (SELECT inserted.id FROM inserted);
	DECLARE @id int;
	WHILE(EXISTS(SELECT carID FROM TempTable))
	BEGIN
		SELECT @id = carID FROM TempTable;
		UPDATE Car SET Car.Quantity = Car.Quantity + (SELECT RentalDetail.quantity FROM RentalDetail WHERE RentalDetail.rentalID = (SELECT inserted.id FROM inserted)
		AND RentalDetail.carID = @id) 
		WHERE Car.id = @id;
		DELETE TempTable WHERE carID = @id;
	END
	DROP TABLE TempTable;
	UPDATE Discount SET status = 1 WHERE Discount.id = (SELECT inserted.discountID FROM inserted);
END 
GO



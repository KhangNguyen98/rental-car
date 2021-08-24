USE CarRental
GO 

INSERT INTO Role(id, name)
VALUES
(
	1,
	'Customer'
),
(
	2,
	'Admin'
)
INSERT INTO Account(email, password, roleID, phoneNumber, fullName, address, createDate, status)
VALUES
(
	'tienttse@gmail.com',
	'123',
	1,
	'1234567890',
	'TienTT',
	'Phu Nhuan',
	'1614934165020',
	1
),
(
	'bangnbse@gmail.com',
	'123',
	2,
	'2393456789',
	'BangNB',
	'Nha Trang',
	'1614934165020',
	1
),
(
	'sonpcse@gmail.com',
	'123',
	2,
	'1111111111',
	'SonPC',
	'Quan 2',
	'1614934165020',
	1
)
GO

INSERT INTO Token(tokenString, email, activate)
VALUES
(
	'abc12',
	'tienttse@gmail.com',
	1
),
(
	'bed12',
	'bangnbse@gmail.com',
	1
),
(
	'asf12',
	'sonpcse@gmail.com',
	1
)
GO



INSERT INTO Category(id, name)
VALUES
(
	1,
	'2-4 peoples'
),
(
	2,
	'6-15 peoples'
),
(
	3,
	'15-20 peoples'
),
(
	4,
	'>20 peoples'
)
GO

INSERT INTO Car(id, name, color,year, categoryID,price,quantity, status)
VALUES
(
	1,
	'Mercede',
	'Yellow',
	2021,
	'1',
	250000,
	4,
	1
),
(
	2,
	'Dragon',
	'Blue',
	2019,
	'3',
	548320,
	10,
	1
),
(
	3,
	'Ambulance',
	'White',
	2000,
	'4',
	2350000,
	8,
	1
),
(
	4,
	'Toy',
	'Black',
	2019,
	'1',
	5000,
	3,
	1
),
(
	5,
	'Electric',
	'Yellow',
	2021,
	'1',
	24000,
	3,
	1
),
(
	6,
	'Wheel',
	'Black',
	2021,
	'1',
	80000,
	1,
	1
),
(
	7,
	'Police',
	'Black',
	2003,
	'2',
	129345,
	5,
	1
),
(
	8,
	'Garbage',
	'Orange',
	2000,
	'3',
	2394021,
	12,
	1
),
(
	9,
	'Transformer',
	'Purple',
	2002,
	'2',
	90000,
	2,
	1
),
(
	10,
	'Time Travel',
	'Blue',
	2002,
	'3',
	90000,
	2,
	1
)
GO

INSERT INTO DISCOUNT(id,status,rate,expiredDate)
VALUES
(
	'ABC398H901',
	1,
	5,
	'1633366800000'
),
(
	'398H901ABC',
	1,
	15,
	'1632466800000'
),
(
	'398H901ACC',
	1,
	40,
	'1583082000000'
),
(
	'398H901ABB',
	0,
	25,
	'1632466800000'
),
(
	'398J901ABB',
	0,
	40,
	'1674934511088'
),
(
	'358H901ABB',
	1,
	12,
	'1614954511088'
)
GO


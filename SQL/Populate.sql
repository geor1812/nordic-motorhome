-- Populating the tables
INSERT INTO model(brand, modelType, fuelType, noBeds, pricePerDay)
VALUES
('Jayco', '2020 REDHAWK 22A', 'Gasoline', 2, 45),
('Swift', 'Bolero 724', 'Diesel', 4, 70),
('Rimon', 'Sailor 697', 'Diesel', 4, 80),
('Jayco', '2020 MELBOURNE PRESTIGE 24TP', 'Diesel', 4, 90),
('Jayco', '2020 REDHAWK 27N', 'Gasoline', 4, 110),
('Bessacarr', 'E435', 'Diesel', 5, 120),
('Roller', 'T-Line 785', 'Diesel', 5, 150),
('Jayco', '2020 REDHAWK 31F', 'Gasoline', 6, 190);

INSERT INTO vehicle(regNo, regDate, odometer, repairStatus, idModel)
VALUES
('EG94816', '2019-03-25', 12000, 0, 1),
('GF38520', '2019-03-01', 24021, 0, 1),
('NB09946', '2019-02-25', 30012, 0, 1),
('OR38446', '2019-03-22', 12000, 0, 1),
('YD98454', '2019-03-01', 24000, 0, 1),
('XC70608', '2019-02-25', 30002, 0, 1),
('JB53814', '2019-03-25', 12040, 0, 1),
('YI82716', '2019-03-01', 24000, 0, 2),
('ZU45178', '2019-02-25', 30050, 0, 2),
('TM92517', '2019-03-25', 12003, 0, 2),
('XP01060', '2019-03-01', 24060, 0, 2),
('AJ61034', '2019-02-25', 30011, 0, 3),
('AP54421', '2019-03-25', 12099, 0, 3),
('JL77969', '2019-03-01', 24000, 0, 3),
('NV57257', '2019-02-25', 30990, 0, 3),
('DW36966', '2019-03-25', 12000, 0, 4),
('HL53757', '2019-03-01', 24000, 0, 4),
('US84270', '2019-02-25', 30230, 0, 4),
('SX55629', '2019-03-25', 12000, 0, 4),
('NO85829', '2019-03-01', 24099, 0, 5),
('WR06359', '2019-02-25', 33300, 0, 5),
('KI96133', '2019-03-25', 12040, 0, 5),
('HG21935', '2019-03-01', 24000, 0, 5),
('TZ21775', '2019-02-25', 30050, 0, 6),
('XK37892', '2019-03-25', 12003, 0, 6),
('GT76875', '2019-03-01', 24060, 0, 6),
('GS53526', '2019-02-25', 30011, 0, 7),
('HS89766', '2019-03-25', 12099, 0, 7),
('OX90426', '2019-03-01', 24000, 0, 7),
('SA31655', '2019-02-25', 30990, 0, 8),
('TW28956', '2019-03-25', 12000, 0, 8),
('AV33128', '2019-03-01', 24000, 0, 8);

INSERT INTO address
(addressDetails, city, state, country, zip)
VALUES
('Kastrup 9','København','','Denmark','2300'),
('Strandgade 61','København','','Denmark','1401'),
('Øster Søgade 45','København','','Denmark','2100'),
('Oldermandsvej 5','København','','Denmark','2400'),
('Regementsgatan 15','Malmö','','Sweden','21748'),
('Poststrasse 18','Hamburg','','Germany','20354');

INSERT INTO customer
(firstName, lastName, phoneNo, email, idAddress)
VALUES
('Johan', 'Mortensen', '34567788', 'jmort@gmail.com', 1),
('Hannah', 'Lund', '45778834', 'hannalund@hotmail.com', 2),
('Christoffer', 'Johnson', '67345566', 'chris_johnson@gmail.com', 3),
('Kriszta', 'Kovacs', '45109566', 'krikov@yahoo.com', 4),
('Bjørn', 'Holbeck', '98776655', 'bjhol@gmail.com', 5),
('Hans', 'Fischer', '45667810', 'hans.fischer@gmail.de', 6);

INSERT INTO contract
(startDate, endDate, endOdometer, fuelCharge, pickUpKm, idVehicle, idCustomer)
VALUES
('2020-03-29', '2020-04-01', '12700', 0, 0, 1, 1),
('2020-04-02', '2020-04-10', '30650', 1, 0, 8, 2),
('2020-04-01', '2020-04-05', '31550', 0, 0, 30, 3),
('2020-04-15', '2020-04-20', '12888', 1, 0, 12, 4),
('2020-05-03', '2020-06-01', '26000', 1, 100, 4, 5),
('2020-05-17', '2020-06-25', '40000', 0, 200, 20, 6);

INSERT INTO licence
(licenceNo, firstName, lastName, birthDate, country, issueDate, expiryDate, originator, cpr, idContract)
VALUES
('7645991088', 'Johan', 'Mortensen', '1998-03-16', 'Denmark', '2017-12-02', '2027-12-02', 'Københavns Politi', '1603981645', 1),
('6753441290', 'Hannah', 'Lund', '1990-07-05', 'Denmark', '2010-05-22', '2020-05-22', 'Københavns Politi', '0507908977', 2),
('1029456732', 'Christoffer', 'Johnson', '1983-12-12', 'Denmark', '2014-07-14', '2024-07-14', 'Københavns Politi', '1212839065', 3),
('1243512633', 'Kriszta', 'Kovacs', '2000-02-17', 'Denmark', '2019-03-01', '2029-03-01', 'Københavns Politi', '1702008907', 4),
('8975678243', 'Bjørn', 'Holbeck', '1980-10-07', 'Sweden', '2000-07-14', '2022-07-14', 'Malmö Polisen', '0710804566', 5),
('7865436712', 'Hans', 'Fischer', '1997-04-30', 'Germany', '2017-11-09', '2027-11-09', 'Hamburg Polizei', '3004976548', 6);

INSERT INTO accessories
(idContract, bedLinen, bikeRack, childSeat, grill, chair, tble)
VALUES
(1, 2, 1, 0, 1, 2, 1),
(2, 3, 0, 1, 1, 3, 1),
(3, 3, 1, 0, 1, 3, 1),
(4, 0, 0, 0, 1, 0, 0),
(5, 2, 2, 0, 1, 4, 1),
(6, 0, 0, 0, 0, 0, 0);
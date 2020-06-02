-- Creating and using the database
CREATE DATABASE nordic;
USE nordic;

-- Creating the tables
CREATE TABLE model
(
	idModel INT NOT NULL UNIQUE AUTO_INCREMENT,
    brand VARCHAR(45) NOT NULL,
    modelType VARCHAR(45) NOT NULL,
    fuelType VARCHAR(45) NOT NULL,
    noBeds INT NOT NULL,
    pricePerDay INT NOT NULL,
    
    PRIMARY KEY(idModel)
);

CREATE TABLE vehicle
(
	idVehicle INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    regNo VARCHAR(7) NOT NULL,
    regDate DATE NOT NULL,
    odometer INT NOT NULL,
    repairStatus TINYINT(1) NOT NULL,
    idModel INT NOT NULL,
    
    FOREIGN KEY (idModel) REFERENCES model(idModel)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE address
(
	idAddress INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    addressDetails VARCHAR(45) NOT NULL,
    city VARCHAR(45) NOT NULL,
    state VARCHAR(45),
    country VARCHAR(45) NOT NULL,
    zip VARCHAR(10) NOT NULL
);

CREATE TABLE customer
(
	idCustomer INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(45) NOT NULL,
    lastName VARCHAR(45) NOT NULL,
    phoneNo VARCHAR(16) NOT NULL,
    email VARCHAR(45) NOT NULL,
    idAddress INT NOT NULL,
    
    FOREIGN KEY (idAddress) REFERENCES address(idAddress)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE contract
(
	idContract INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    endOdometer INT NOT NULL DEFAULT "0",
    fuelCharge TINYINT(1) NOT NULL DEFAULT "0",
    pickUpKm INT DEFAULT "0",
    idVehicle INT NOT NULL,
    idCustomer INT NOT NULL,
    
    FOREIGN KEY (idVehicle) REFERENCES vehicle(idVehicle)
    ON DELETE CASCADE ON UPDATE CASCADE,
    
    FOREIGN KEY (idCustomer) REFERENCES customer(idCustomer)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE licence
(
	idLicence INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    licenceNo VARCHAR(10) NOT NULL,
    firstName VARCHAR(45) NOT NULL,
    lastName VARCHAR(45) NOT NULL,
    birthDate DATE NOT NULL,
    country VARCHAR(45) NOT NULL,
    issueDate DATE NOT NULL,
    expiryDate DATE NOT NULL,
    originator VARCHAR(45) NOT NULL,
    cpr VARCHAR(11) NOT NULL,
    idContract INT NOT NULL,
    
    FOREIGN KEY (idContract) REFERENCES contract(idContract)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE accessories
(
	idAccessories INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    idContract INT NOT NULL,
    bedLinen INT NOT NULL,
    bikeRack INT NOT NULL,
    childSeat INT NOT NULL,
    grill INT NOT NULL,
    chair INT NOT NULL,
    tble INT NOT NULL,
    
    FOREIGN KEY (idContract) REFERENCES contract(idContract)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE archive
(
	idArchive INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    fuelCharge TINYINT(1) NOT NULL,
    idVehicle INT NOT NULL,
    idCustomer INT,
    odometerCharge DOUBLE,
    pickUpCharge DOUBLE,
    totalCost DOUBLE NOT NULL,
    
    FOREIGN KEY (idVehicle) REFERENCES vehicle(idVehicle)
    ON DELETE SET NULL ON UPDATE CASCADE,
    
    FOREIGN KEY (idCustomer) REFERENCES customer(idCustomer)
    ON DELETE SET NULL ON UPDATE CASCADE
);

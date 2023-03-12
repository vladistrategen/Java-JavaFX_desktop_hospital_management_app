--@block
CREATE TABLE doctors(
    ID INT PRIMARY KEY NOT NULL,
    name VARCHAR(20) NOT NULL,
    specialization VARCHAR(20) NOT NULL,
    salary FLOAT NOT NULL
)

--@block
CREATE TABLE patients(
    ID INT PRIMARY KEY NOT NULL,
    name VARCHAR(20) NOT NULL,
    age INT NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

--@block
CREATE TABLE appointments(
    ID INT PRIMARY KEY NOT NULL,
    doctor_ID INT NOT NULL,
    patient_ID INT NOT NULL,
    date VARCHAR(20) NOT NULL,
    FOREIGN KEY (doctor_ID) REFERENCES doctors(ID),
    FOREIGN KEY (patient_ID) REFERENCES patients(ID)
);
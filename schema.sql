\c weather_db;

CREATE TABLE users (
    ID SERIAL PRIMARY KEY,
    email VARCHAR UNIQUE,
    password VARCHAR
);

CREATE TABLE Locations (
    ID SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INT REFERENCES users(ID),
    latitude DECIMAL,
    longitude DECIMAL
);

-- DELETE FROM Sessions;
-- DELETE FROM Locations;
-- DELETE FROM User_Accounts;
--
-- DROP TABLE IF EXISTS Sessions;
-- DROP TABLE IF EXISTS Locations;
-- DROP TABLE IF EXISTS User_Accounts;



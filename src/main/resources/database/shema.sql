CREATE TABLE User_Accounts (
    ID SERIAL PRIMARY KEY,
    login VARCHAR UNIQUE,
    password VARCHAR
);

CREATE TABLE Locations (
    ID SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INT REFERENCES User_Accounts(ID),
    latitude DECIMAL,
    longitude DECIMAL
);

CREATE TABLE Sessions (
    ID UUID PRIMARY KEY,
    user_id INT REFERENCES User_Accounts(ID),
    expires_at TIMESTAMP
);


-- DELETE FROM Sessions;
-- DELETE FROM Locations;
-- DELETE FROM User_Accounts;
--
-- DROP TABLE IF EXISTS Sessions;
-- DROP TABLE IF EXISTS Locations;
-- DROP TABLE IF EXISTS User_Accounts;



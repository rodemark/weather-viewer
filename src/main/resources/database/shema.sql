CREATE TABLE Users (
    ID SERIAL PRIMARY KEY,
    login VARCHAR UNIQUE,
    password VARCHAR
);

CREATE TABLE Locations (
    ID SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INT REFERENCES Users(ID),
    latitude DECIMAL,
    longitude DECIMAL
);

CREATE TABLE Sessions (
    ID UUID PRIMARY KEY,
    user_id INT REFERENCES Users(ID),
    expires_at TIMESTAMP
);

-- DELETE FROM Sessions;
-- DELETE FROM Locations;
-- DELETE FROM Users;
--
-- DROP TABLE IF EXISTS Sessions;
-- DROP TABLE IF EXISTS Locations;
-- DROP TABLE IF EXISTS Users;



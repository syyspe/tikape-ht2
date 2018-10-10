DROP TABLE IF EXISTS Vastaus;
DROP TABLE IF EXISTS Kysymys;
DROP TABLE IF EXISTS Kurssi;

CREATE TABLE IF NOT EXISTS Kurssi (id SERIAL PRIMARY KEY,nimi varchar(256) NOT NULL);
CREATE TABLE IF NOT EXISTS Kysymys (id SERIAL PRIMARY KEY,aihe varchar(256),teksti varchar(1024) NOT NULL,kurssi_id integer NOT NULL,FOREIGN KEY(kurssi_id) REFERENCES Kurssi(id));
CREATE TABLE IF NOT EXISTS Vastaus (id SERIAL PRIMARY KEY,teksti varchar(1024) NOT NULL,oikein boolean NOT NULL,kysymys_id integer NOT NULL,FOREIGN KEY(kysymys_id) REFERENCES Kysymys(id));


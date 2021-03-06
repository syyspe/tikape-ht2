DROP TABLE IF EXISTS Vastaus;
DROP TABLE IF EXISTS Kysymys;
DROP TABLE IF EXISTS Kurssi;
CREATE TABLE IF NOT EXISTS Kurssi (id SERIAL PRIMARY KEY,nimi varchar(256) NOT NULL);
CREATE TABLE IF NOT EXISTS Kysymys (id SERIAL PRIMARY KEY,aihe varchar(256),teksti varchar(1024) NOT NULL,kurssi_id integer NOT NULL,FOREIGN KEY(kurssi_id) REFERENCES Kurssi(id));
CREATE TABLE IF NOT EXISTS Vastaus (id SERIAL PRIMARY KEY,teksti varchar(1024) NOT NULL,oikein boolean NOT NULL,kysymys_id integer NOT NULL,FOREIGN KEY(kysymys_id) REFERENCES Kysymys(id));
INSERT INTO Kurssi (nimi) Values ('Johdatus tietojenkäsittelytieteeseen');
INSERT INTO Kurssi (nimi) Values ('Ohjelmoinnin perusteet');
INSERT INTO Kurssi (nimi) Values ('Ohjelmoinnin jatkokurssi');
INSERT INTO Kurssi (nimi) Values ('Tietokantojen perusteet');
INSERT INTO Kurssi (nimi) Values ('Full stack -websovelluskehitys');
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Kuuluisat henkilöt', 'Mistä Alan Turing tunnetaan?',1);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 1', 'Minkä niminen metodi käynnstää Java-ohjelman?',2);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 8', 'Miksi Java osaa kutsua toString() metodia mille tahansa oliolle?',3);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Osa 3', 'Mihin SQL avainsanaa AS käytetään?',4);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Osa 1', 'Miten React komponentit voivat välittää toisilleen dataa?',5);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Hän kehitti World Wide Webin',false,1);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Hän perusti Microsoftin',false,1);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Hän kehitti Turingin koneen',true,1);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('String()',false,2);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Pää()',false,2);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('main()',true,2);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Ei se osaakaan',false,3);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Koska kaikki luokat perivät automaattisesti Object -luokan, joka toteuttaa kyseisen metodin',true,3);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Koska kääntäjä osaa lisätä sen kaikille luokille',false,3);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Nimeämään sarake kyselyn tuloksissa',true,4);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Rajaamaan haettavia sarakkeita',false,4);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Järjestämään haun tulokset aakkosjärjestykseen',false,4);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('ns. propsien avulla',true,5);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('HTML form elementtien kautta',false,5);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Postikortilla',false,5);
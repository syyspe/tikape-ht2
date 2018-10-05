CREATE TABLE IF NOT EXISTS Kurssi (
	id integer PRIMARY KEY,
	nimi varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS Kysymys (
	id integer PRIMARY KEY,
	aihe varchar(256),
	teksti varchar(1024) NOT NULL,
	kurssi_id integer NOT NULL,
	FOREIGN KEY(kurssi_id) REFERENCES Kurssi(id)
);

CREATE TABLE IF NOT EXISTS Vastaus (
	id integer PRIMARY KEY,
	teksti varchar(1024) NOT NULL,
	oikein integer NOT NULL,
	kysymys_id integer NOT NULL,
	FOREIGN KEY(kysymys_id) REFERENCES Kysymys(id)
);

INSERT INTO Kurssi (nimi) Values ('Johdatus tietojenkäsittelytieteeseen');
INSERT INTO Kurssi (nimi) Values ('Ohjelmoinnin perusteet');
INSERT INTO Kurssi (nimi) Values ('Ohjelmoinnin jatkokurssi');
INSERT INTO Kurssi (nimi) Values ('Tietokantojen perusteet');
INSERT INTO Kurssi (nimi) Values ('Full stack -websovelluskehitys');

INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Kuuluisat henkilöt', 'Mistä Alan Turing tunnetaan?',1);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 1', 'Minkä niminen metodi käynnstää Java-ohjelman?',2);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Viikko 8', 'Miksi Java osaa kutsua toString() metodia mille tahansa oliolle?',3);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Osa 3', 'Mihin SQL termiä AS voidaan käyttää?',4);
INSERT INTO Kysymys (aihe,teksti,kurssi_id) VALUES ('Osa 1', 'Miten React komponentit voivat välittää toisilleen dataa?',5);

INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Hän kehitti World Wide Webin',0,1);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Hän perusti Microsoftin',0,1);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Hän kehitti Turingin koneen',1,1);

INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('String()',0,2);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Pää()',0,2);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('main()',1,2);

INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Ei se osaakaan',0,3);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Koska kaikki luokat perivät automaattisesti Object -luokan, joka toteuttaa kyseisen metodin',1,3);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Koska kääntäjä osaa lisätä sen kaikille luokille',0,3);

INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Nimeämään sarake kyselyn tuloksissa',1,4);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Rajaamaan haettavia sarakkeita',0,4);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Järjestämään haun tulokset aakkosjärjestykseen',1,4);

INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('ns. propsien avulla',1,5);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('HTML form elementtien kautta',0,5);
INSERT INTO Vastaus (teksti,oikein,kysymys_id) VALUES ('Postikortilla',0,5);
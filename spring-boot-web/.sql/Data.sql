
INSERT into prodotti(nome, descrizione, prezzo, netto)
values ('Maglia Lacoste', 'Maglia in cotone', 100,80),
        ('Maglia Diesel', 'Maglia in cotone', 100,80),
        ('Maglione Timberland', 'Msglionr lana', 120,100),
        ('Jeans Levi''s', 'Jeans cotone', 80,60);
        
INSERT into varianti (attributo, valore, prodotto_id)
values('colore','bianco',1),
('colore','nero',1),
('taglia','m',1),
('taglia','l',1),
('colore','bianco',2),
('colore','nero',2),
('taglia','m',2),
('taglia','l',2),
('colore','giallo',3),
('colore','verde',3),
('taglia','l',3),
('taglia','xl',3),
('taglia','40',4),
('taglia','42',4),
('taglia','44',4);

INSERT into fornitori (nome)
values ('I miei maglioni srl'),
('Tutto abbigliamento spa'),
('Solo maglie srl');

INSERT into fornitori_prodotti (fornitore_id, prodotto_id)
values(1,3),
(2,1),
(2,2),
(2,3),
(2,4),
(3,1),
(3,2);
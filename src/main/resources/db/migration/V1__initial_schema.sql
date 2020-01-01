
CREATE TABLE high_score (
    name               VARCHAR(40) NOT NULL,
    score              INTEGER NOT NULL
);


INSERT INTO high_score (name, score) VALUES ('', 0);


CREATE TABLE pieces (
    piece              VARCHAR(40) NOT NULL
);


INSERT INTO pieces (piece) VALUES
-- horizontal
 ('OO'),
 ('OOO'),
 ('OOOO'),
 ('OOOOO'),
-- vertical
 ('O:O'),
 ('O:O:O'),
 ('O:O:O:O'),
 ('O:O:O:O:O'),
-- L shapes
 ('O :OO'), -- L2
 ('O  :O  :OOO'), -- L3
 ('OO:O '), -- upside down L2
 ('OOO:O  :O  '), -- upside down L3
 (' O:OO'), -- backwards L2
 ('  O:  O:OOO'), -- backwards L3
 ('OO: O'), -- upside down and backwards L2
 ('OOO:  O:  O'), -- upside down and backwards L3
-- squares
 ('O'),
 ('OO:OO'),
 ('OOO:OOO:OOO');


CREATE TABLE games (
    id                 VARCHAR(40) NOT NULL,
    score              INTEGER NOT NULL,
    board              VARCHAR(400) NOT NULL,
    piece1             VARCHAR(40),
    piece2             VARCHAR(40),
    piece3             VARCHAR(40),

    CONSTRAINT games_pk PRIMARY KEY (id)
);



CREATE TABLE mazes (
    level              INTEGER NOT NULL,
    width              INTEGER NOT NULL,
    height             INTEGER NOT NULL,
    serialized         TEXT NOT NULL,

    PRIMARY KEY (level, width, height)
);


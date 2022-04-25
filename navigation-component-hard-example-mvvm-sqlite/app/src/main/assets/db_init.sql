PRAGMA foreign_keys = ON;

CREATE TABLE "accounts" (
  "id"		    INTEGER PRIMARY KEY,
  "email"	    TEXT NOT NULL UNIQUE COLLATE NOCASE,
  "username" 	TEXT NOT NULL,
  "password" 	TEXT NOT NULL,
  "created_at"  TEXT NOT NULL
);

CREATE TABLE "boxes" (
  "id"		    INTEGER PRIMARY KEY,
  "color_name" 	TEXT NOT NULL,
  "color_value" TEXT NOT NULL
);

CREATE TABLE "accounts_boxes_settings" (
  "account_id"	INTEGER NOT NULL,
  "box_id"	    INTEGER NOT NULL,
  "is_active"	INTEGER NOT NULL,
  FOREIGN KEY("account_id") REFERENCES "accounts"("id")
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY("box_id") REFERENCES "boxes"("id")
    ON UPDATE CASCADE ON DELETE CASCADE,
  UNIQUE("account_id","box_id")
);

INSERT INTO "accounts" ("email", "username", "password", "created_at")
VALUES
    ("admin", "admin", "123", "10.02.2012"),
    ("tester", "tester", "321", "12.05.2017"),
    ("admin123", "admin", "456", "13.01.2019");

INSERT INTO "boxes" ("color_name", "color_value")
VALUES
    ("Red", "#880000"),
    ("Green", "#008800"),
    ("Blue", "#000088"),
    ("Yellow", "#888800"),
    ("Violet", "#8800FF"),
    ("Black", "#000000");

INSERT INTO "accounts_boxes_settings" ("account_id", "box_id", "is_active")
VALUES
    (1, 1, 1),
    (1, 2, 0),
    (1, 3, 1),
    (1, 4, 0),
    (2, 1, 0)
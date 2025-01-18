CREATE TABLE teacher
(
    nip      VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NULL,
    classes  VARCHAR(255) NOT NULL,
    dob      DATE NULL,
    phoneNo  VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    subjects VARCHAR(255) NOT NULL,
    CONSTRAINT pk_teacher PRIMARY KEY (nip)
);
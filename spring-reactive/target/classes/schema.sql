CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS Student (
    id		 SERIAL,
	name		 TEXT,
	birth_date	 DATE,
	completed_credits INTEGER,
	average_grade	 FLOAT(8),
	PRIMARY KEY(id)

);

CREATE TABLE IF NOT EXISTS professor (
	id	 SERIAL,
	name TEXT,
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS student_professor (
	id		 SERIAL,
	student_id	 INTEGER,
	professor_id INTEGER,
	PRIMARY KEY(student_id, professor_id),
    CONSTRAINT student_professor_fk1 FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT student_professor_fk2 FOREIGN KEY (professor_id) REFERENCES professor(id)
);



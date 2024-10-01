CREATE TABLE users (
    id SERIAL4 NOT NULL,
    firstname VARCHAR (50) NOT NULL,
    lastname VARCHAR (80) NOT NULL,
    username VARCHAR (100) NOT NULL,
    password VARCHAR (10) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)

);



CREATE TABLE trainee (
    id SERIAL4 NOT NULL,
    users_id INT4 NOT NULL,
    date_of_birth DATE NOT NULL,
    address VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (users_id) REFERENCES users (id)

);

CREATE TABLE training_type (
    id SERIAL4 NOT NULL,
    training_type_name VARCHAR (50)  NOT NULL,
    PRIMARY KEY (id)

);

CREATE TABLE trainer (
    id SERIAL4 NOT NULL,
    users_id INT4 NOT NULL,
    specialization INT4 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (specialization) REFERENCES training_type (id)

);

CREATE TABLE trainer_trainee (
    trainee_id INT4 NOT NULL,
    trainer_id INT4 NOT NULL,
    PRIMARY KEY (trainee_id, trainer_id),
    FOREIGN KEY (trainee_id) REFERENCES trainee (id),
    FOREIGN KEY (trainer_id) REFERENCES  trainer (id)

);

CREATE TABLE training (
    id SERIAL4 NOT NULL,
    trainee_id INT4 NOT NULL,
    trainer_id INT4 NOT NULL,
    training_type_id INT4 NOT NULL,
    training_name VARCHAR(50) UNIQUE NOT NULL,
    training_date DATE NOT NULL,
    training_duration INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (trainee_id) REFERENCES trainee (id),
    FOREIGN KEY (trainer_id) REFERENCES trainer (id),
    FOREIGN KEY (training_type_id) REFERENCES training_type (id)

);




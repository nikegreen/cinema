CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE movies (
  id SERIAL PRIMARY KEY,
  name text NOT NULL UNIQUE,
  filename text NOT NULL
);

CREATE TABLE rooms (
  id SERIAL PRIMARY KEY,
  name text
);

CREATE TABLE sessions (
  id SERIAL PRIMARY KEY,
  name text,
  movie_id int NOT NULL REFERENCES movies(id),
  room_id int NOT NULL REFERENCES rooms(id),
  start timestamp without time zone
);

CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
);

CREATE TABLE seats (
  id SERIAL PRIMARY KEY,
  room_id int NOT NULL REFERENCES rooms(id),
  row int,
  cell int
);

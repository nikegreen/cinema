DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS movies CASCADE;
DROP TABLE IF EXISTS rooms CASCADE;
DROP TABLE IF EXISTS seats CASCADE;
DROP TABLE IF EXISTS sessions CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;

CREATE TABLE IF NOT EXISTS users(
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS movies(
  id SERIAL PRIMARY KEY,
  name text NOT NULL UNIQUE,
  filename text NOT NULL
);

CREATE TABLE IF NOT EXISTS rooms(
  id SERIAL PRIMARY KEY,
  name text NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS sessions(
  id SERIAL PRIMARY KEY,
  name text,
  movie_id int NOT NULL REFERENCES movies(id),
  room_id int NOT NULL REFERENCES rooms(id),
  start timestamp without time zone
);

CREATE TABLE IF NOT EXISTS tickets(
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    CONSTRAINT constraint_session_pos_row UNIQUE (session_id, pos_row, cell)
);

CREATE TABLE IF NOT EXISTS seats(
  id SERIAL PRIMARY KEY,
  room_id int NOT NULL REFERENCES rooms(id),
  pos_row int,
  cell int
);

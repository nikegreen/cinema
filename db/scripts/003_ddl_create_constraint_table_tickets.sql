TRUNCATE tickets;
ALTER TABLE tickets
ADD CONSTRAINT constraint_session_pos_row UNIQUE (session_id, pos_row, cell);

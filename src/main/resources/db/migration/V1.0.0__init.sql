CREATE TABLE TASK (id UUID, title VARCHAR(MAX), text VARCHAR(MAX), status VARCHAR(20), created_at TIMESTAMP, previous_version_id UUID, newest_version BOOLEAN);
CREATE TABLE NOTE (id UUID, title VARCHAR(MAX), text VARCHAR(MAX), created_at TIMESTAMP, previous_version_id UUID, newest_version BOOLEAN);
CREATE TABLE TASKS_NOTES (task_id UUID, note_id UUID)
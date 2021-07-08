CREATE TABLE TASK(id UUID, title VARCHAR(MAX), text VARCHAR(MAX), status VARCHAR(20), created_at TIMESTAMP, modified_at TIMESTAMP, previous_version_id UUID, newest_version BOOLEAN);
CREATE TABLE NOTE(id UUID, title VARCHAR(MAX), text VARCHAR(MAX), status VARCHAR(20), created_at TIMESTAMP, modified_at TIMESTAMP, previous_version_id UUID, newest_version BOOLEAN);
CREATE TABLE TASKS_NOTES(task_id UUID, note_id UUID);
CREATE TABLE TAG(id UUID, text VARCHAR(MAX));
CREATE TABLE NOTES_TAGS(note_id UUID, tag_id UUID);
CREATE TABLE TASKS_TAGS(task_id UUID, tag_id UUID);

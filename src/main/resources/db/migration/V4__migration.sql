-- V4__update_tables.sql
ALTER TABLE customers
DROP COLUMN experience,
DROP COLUMN documents,
ADD COLUMN profile_picture VARCHAR(50);

ALTER TABLE professionals
ADD COLUMN profile_picture VARCHAR(50);

-- V5__update_Tables.sql

ALTER TABLE services
ADD COLUMN image_url VARCHAR(50);

ALTER TABLE sub_services
ADD COLUMN image_url VARCHAR(50);
-- V6__updateTables.sql

-- 1. Drop the UNIQUE constraint if it exists
ALTER TABLE customers
DROP CONSTRAINT IF EXISTS customers_username_key;

-- 2. Change the column length to 100
ALTER TABLE customers
ALTER COLUMN username TYPE VARCHAR(100);

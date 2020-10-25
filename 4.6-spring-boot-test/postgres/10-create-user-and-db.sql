-- file: 10-create-user-and-db.sql
CREATE DATABASE simple_web;
CREATE ROLE program WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE simple_web TO program;
ALTER ROLE program WITH LOGIN;
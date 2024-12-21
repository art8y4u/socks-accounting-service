CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS socks_service;

CREATE TABLE IF NOT EXISTS socks_service.socks (
    id                uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    cotton_percentage INTEGER     NOT NULL,
    color             VARCHAR(30) NOT NULL,
    quantity          INTEGER     NOT NULL,
    UNIQUE (cotton_percentage, color)
);
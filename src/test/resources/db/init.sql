CREATE EXTENSION pgcrypto;

CREATE SCHEMA IF NOT EXISTS socks_service;

CREATE TABLE IF NOT EXISTS socks_service.colors (
    id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS socks_service.socks (
    id                uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    quantity          INTEGER NOT NULL,
    cotton_percentage INTEGER NOT NULL,
    color_id          uuid REFERENCES socks_service.colors (id) ON DELETE RESTRICT
);
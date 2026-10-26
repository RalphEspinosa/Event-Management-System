-- Run once on an existing event_management database before using check-in support.
USE event_management;

ALTER TABLE attendees
    ADD COLUMN checked_in BOOLEAN NOT NULL DEFAULT FALSE;

# Hytale Msg Plugin

A simple private message plugin for Hytale with configurable message formats.

## Commands

- `/msg <player> <message>` — send a private message
- `/reply <message>` — reply to your last private message

## Configuration

A `config.json` is created in the plugin data directory on first run.

Two message formats are supported:

- `MessageFormatSent` — what the sender sees
- `MessageFormatReceived` — what the receiver sees

Placeholders:

- `{sender}` — sender display name
- `{receiver}` — receiver username
- `{message}` — message text

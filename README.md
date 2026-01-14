# Kotlin Hytale Plugin Template

A minimal template for writing **Hytale Server** plugins in Kotlin.

It includes:
- Kotlin + Gradle wrapper
- A `manifest.json` plugin manifest
- A sample command + message formatting example
- A **fat JAR** build (so Kotlin stdlib is included)

## Requirements

- **Java 21**
- A local **Hytale Server** install (you need `HytaleServer.jar`)

## Build

This template produces a fat JAR (includes Kotlin runtime).

```bash
./gradlew build
```

Output:
- `build/libs/<project>-<version>.jar`

## Install into a server

Copy the built JAR into the server `mods/` directory:

```bash
cp build/libs/*.jar <PATH_TO_SERVER>/mods/
```

## Plugin manifest

Edit the manifest at `src/main/resources/manifest.json` to match your plugin.

### Allow everyone to use your command (no OP required)

By default, **all players are placed into the `Adventure` permission group** when they join a Hytale server. 

From the **server console** or as an **OP**, run:

```text
/perm group Adventure add com.github.iipanda.hytaleplugin
```

This grants the plugin permissions to all non-OP players.

### Lifecycle

Override these in your plugin main class:
- `setup()` — register commands / listeners
- `start()` — start runtime tasks
- `shutdown()` — cleanup resources
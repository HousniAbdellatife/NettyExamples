# NettyExamples

This project's aim is to understand the Netty Java networking framework by creating examples and playing with it.

## Basic Netty Example

This repository now includes a minimal TCP echo-style Netty setup:

- `BasicNettyServer` listens on a port (default `8080`) and responds with `Server received: <message>`.
- `BasicNettyClient` connects to the server, sends a message, prints the response, and exits.

### Build

```bash
mvn compile
```

### Run the server

```bash
mvn exec:java -Dexec.mainClass=com.example.nettyexamples.BasicNettyServer
```

(Optional custom port)

```bash
mvn exec:java -Dexec.mainClass=com.example.nettyexamples.BasicNettyServer -Dexec.args="9090"
```

### Run the client

```bash
mvn exec:java -Dexec.mainClass=com.example.nettyexamples.BasicNettyClient
```

(Optional host/port/message)

```bash
mvn exec:java -Dexec.mainClass=com.example.nettyexamples.BasicNettyClient -Dexec.args="127.0.0.1 9090 hello-from-client"
```

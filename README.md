# 🌐 HTTP Proxy Cache Server (Java)

This project implements a basic multithreaded HTTP proxy server in Java. It listens on a user-defined port, accepts incoming HTTP requests from clients, forwards them to the appropriate web server, and caches the responses for repeated use.

---

## 🧱 Project Structure

### `ProxyCache.java`
- The main class that starts the proxy server
- Listens for TCP connections on a specified port
- Launches a new thread for each client request

### `HttpRequest.java`
- Parses the incoming HTTP request from the client
- Extracts method, host, path, and headers
- Connects to the target server and forwards the request

### `HttpResponse.java`
- Reads the response from the server
- Handles caching: saves the response locally
- Returns cached content if the same resource is requested again

---

## 🧠 Features

- 📦 **File-based caching:** saves previously requested resources to disk
- 🌐 **Supports basic HTTP GET requests**
- 🧵 **Multithreaded:** handles multiple clients simultaneously
- 🔐 **No HTTPS support (HTTP only)**

---

Run the server on port 8080 (or another port of your choice):
java ProxyCache 8080


Then configure your browser or curl to use the proxy:
curl -x localhost:8080 http://example.com


## ▶️ How to Run

Compile the Java source files:

```bash
javac ProxyCache.java HttpRequest.java HttpResponse.java





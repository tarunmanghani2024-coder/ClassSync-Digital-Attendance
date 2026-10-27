import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * ClassSync backend (basic level, no external libraries, Java 11+).
 *
 *  - Serves the web page from the "public" folder
 *  - GET  /api/data  -> returns the saved app data (204 if nothing saved yet)
 *  - PUT  /api/data  -> saves the app data to data/classsync.json
 *
 * Run:  java Server.java      then open http://localhost:8080
 */
public class Server {
    static final int PORT = 8080;
    static final int MAX_BYTES = 5 * 1024 * 1024;           // 5 MB limit per save
    static final Path PUBLIC_DIR = Paths.get("public").toAbsolutePath().normalize();
    static final Path DATA_FILE = Paths.get("data", "classsync.json");
    static final Object LOCK = new Object();                 // one writer at a time

    public static void main(String[] args) throws IOException {
        Files.createDirectories(DATA_FILE.getParent());
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/data", Server::handleData);
        server.createContext("/", Server::handleFile);
        server.start();
        System.out.println("ClassSync is running at http://localhost:" + PORT);
        System.out.println("Press Ctrl+C to stop.");
    }

    /** Read or save the app data. The server treats it as plain JSON text. */
    static void handleData(HttpExchange ex) throws IOException {
        try {
            String method = ex.getRequestMethod();

            if (method.equals("GET")) {
                synchronized (LOCK) {
                    if (!Files.exists(DATA_FILE)) {
                        send(ex, 204, "text/plain", new byte[0]);
                    } else {
                        send(ex, 200, "application/json", Files.readAllBytes(DATA_FILE));
                    }
                }

            } else if (method.equals("PUT") || method.equals("POST")) {
                byte[] body = ex.getRequestBody().readNBytes(MAX_BYTES + 1);
                if (body.length > MAX_BYTES) {
                    send(ex, 413, "text/plain", "Data too large".getBytes());
                    return;
                }
                String text = new String(body, StandardCharsets.UTF_8).trim();
                if (!text.startsWith("{") || !text.endsWith("}")) {
                    send(ex, 400, "text/plain", "Body must be a JSON object".getBytes());
                    return;
                }
                synchronized (LOCK) {
                    Path tmp = DATA_FILE.resolveSibling("classsync.tmp");
                    Files.write(tmp, text.getBytes(StandardCharsets.UTF_8));
                    Files.move(tmp, DATA_FILE, StandardCopyOption.REPLACE_EXISTING);
                }
                send(ex, 200, "application/json", "{\"ok\":true}".getBytes());

            } else {
                send(ex, 405, "text/plain", "Method not allowed".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            send(ex, 500, "text/plain", "Server error".getBytes());
        }
    }

    /** Serve files from the public folder (index.html by default). */
    static void handleFile(HttpExchange ex) throws IOException {
        try {
            String path = ex.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";

            Path file = PUBLIC_DIR.resolve(path.substring(1)).normalize();
            // block paths like /../secret
            if (!file.startsWith(PUBLIC_DIR) || !Files.isRegularFile(file)) {
                send(ex, 404, "text/plain", "Not found".getBytes());
                return;
            }
            String name = file.getFileName().toString();
            String type = name.endsWith(".html") ? "text/html; charset=utf-8"
                        : name.endsWith(".css") ? "text/css"
                        : name.endsWith(".js") ? "text/javascript"
                        : "application/octet-stream";
            send(ex, 200, type, Files.readAllBytes(file));
        } catch (Exception e) {
            send(ex, 404, "text/plain", "Not found".getBytes());
        }
    }

    static void send(HttpExchange ex, int code, String type, byte[] data) throws IOException {
        ex.getResponseHeaders().set("Content-Type", type);
        ex.getResponseHeaders().set("Cache-Control", "no-store");
        if (code == 204) {                                   // 204 must have no body
            ex.sendResponseHeaders(204, -1);
            ex.close();
            return;
        }
        ex.sendResponseHeaders(code, data.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(data);
        }
    }
}

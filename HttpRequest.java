import java.io.BufferedReader;
import java.io.IOException;

class HttpRequest {
    private String method;
    private String URI;
    private String version;
    private String headers = "";
    private String host;
    private int port;

    public HttpRequest(BufferedReader fromClient) {
        try {
            String requestLine = fromClient.readLine();
            String[] requestParts = requestLine.split(" ");
            method = requestParts[0];
            URI = requestParts[1];
            version = requestParts[2];

            String line = fromClient.readLine();
            while (!line.isEmpty()) {
                headers += line + "\r\n";
                if (line.startsWith("Host:")) {
                    String[] hostParts = line.split(" ")[1].split(":");
                    host = hostParts[0];
                    port = (hostParts.length > 1) ? Integer.parseInt(hostParts[1]) : 80;
                }
                line = fromClient.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading request from client: " + e);
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String generateRequestString() {
        StringBuilder request = new StringBuilder();
        request.append(method).append(" ").append(URI).append(" ").append(version).append("\r\n");
        request.append(headers);
        request.append("Connection: close\r\n\r\n");
        return request.toString();
    }
}

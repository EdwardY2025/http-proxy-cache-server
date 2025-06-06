import java.io.DataInputStream;
import java.io.IOException;

class HttpResponse {
    private String version;
    private int status;
    private String statusLine = "";
    private String headers = "";
    private byte[] body = new byte[ProxyCache.getMaxObjectSize()];

    @SuppressWarnings("deprecation")
	public HttpResponse(DataInputStream fromServer) {
        try {
            String line;
            line = fromServer.readLine();
            while (!line.isEmpty()) {
                if (statusLine.isEmpty()) {
                    statusLine = line;
                } else {
                    headers += line + "\r\n";
                }
                if (line.startsWith("Content-Length:")) {
                    int length = Integer.parseInt(line.split(" ")[1]);
                    int bytesRead = 0;
                    while (bytesRead < length) {
                        int bytesToRead = Math.min(ProxyCache.getBufSize(), length - bytesRead);
                        int bytesReadThisTime = fromServer.read(body, bytesRead, bytesToRead);
                        if (bytesReadThisTime == -1) {
                            break;
                        }
                        bytesRead += bytesReadThisTime;
                    }
                }
                line = fromServer.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading response from server: " + e);
        }
    }

    public String generateResponseString() {
        StringBuilder response = new StringBuilder();
        response.append(statusLine).append("\r\n");
        response.append(headers).append("\r\n");
        return response.toString();
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
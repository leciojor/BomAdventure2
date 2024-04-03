package server.json;

public class ResetResponse {
    private String message;
    private int level;

    public ResetResponse(String message, int level) {
        this.message = message;
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public int getLevel() {
        return level;
    }
}

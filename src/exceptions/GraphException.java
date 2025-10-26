package exceptions;

public class GraphException extends Exception {
    private final ErrorCode errorCode;

    public enum ErrorCode {
        NULL_VERTEX("Vertex cannot be null"),
        INVALID_EDGE("Invalid edge configuration"),
        NEGATIVE_WEIGHT("Edge weight cannot be negative"),
        DISCONNECTED_GRAPH("Graph is disconnected"),
        EMPTY_GRAPH("Graph has no vertices");

        private final String message;

        ErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public GraphException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GraphException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage() + ": " + details);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

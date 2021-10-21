package AirPlane.model.Exception;

public class AirplaneNotInAirException extends AirplaneException {
    public AirplaneNotInAirException(String message) {
        super(message);
    }

    public AirplaneNotInAirException() {
        super();
    }
}

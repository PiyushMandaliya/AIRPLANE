package AirPlane.model.Exception;

public class AirplaneAlreadyInAirException extends AirplaneException {
    public AirplaneAlreadyInAirException(String message) {
        super(message);
    }

    public AirplaneAlreadyInAirException() {
        super();
    }
}

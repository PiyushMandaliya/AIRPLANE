package AirPlane.view;

import AirPlane.model.AirPlane;


public interface AirplaneView
{
    int getAltitude();
    void setAltitude(int altitude);
    boolean isLanded();

    boolean turnMotorOn() throws AirplaneException;
    boolean takeOffAirPlane() throws AirplaneException;
    boolean turnMotorOff() throws AirplaneException;
    void increaseAltitude() throws AirplaneException;
    void checkAltitude() throws AirplaneException;
    void decreaseAltitude() throws AirplaneException;
}
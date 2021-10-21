//Author: Piyushkumar Govindbhai Mandaliya

package AirPlane.model;

import AirPlane.model.Exception.*;
import AirPlane.view.AirPlaneGUI;
import AirPlane.view.AirplaneView;



import java.util.ArrayList;

public class AirPlane implements AirplaneView {

    final private int DANGER_ALTITUDE_LEVEL = 10000;
    final private int EXPLODE_ALTITUDE_LEVEL = 12000;
    final private int CHANGE_ALTITUDE_LEVEL = 1000;

    private int altitude = 0;
    private AirplaneState airplaneState = AirplaneState.ENGINE_OFF;

    private ArrayList<AirPlaneGUI> observerList = new ArrayList<>();

    public void setObserver(AirPlaneGUI airPlaneGUI) { observerList.add(airPlaneGUI); }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public boolean isLanded()
    {
        if (airplaneState != AirplaneState.EXPLODED && airplaneState != AirplaneState.FLYING)
        {
            return true;
        }
        return false;
    }

    public boolean turnMotorOn() throws AirplaneException {
        switch (airplaneState){
            case EXPLODED:
                throw new AirplaneExplodedException("ERROR: Airplane already Exploded");
            case ENGINE_ON:
            case FLYING:
                throw new AirplaneAlreadyStartedException("ERROR: Airplane already started");
            case ENGINE_OFF:
                airplaneState = AirplaneState.ENGINE_ON;
                return true;
            default:
                return false;
        }
    }

    public boolean takeOffAirPlane() throws AirplaneException {
        switch (airplaneState){
            case EXPLODED:
                throw new AirplaneExplodedException("ERROR: Airplane already Exploded");
            case ENGINE_OFF:
                throw new AirplaneAlreadyStoppedException("ERROR: Airplane not started.");
            case FLYING:
                throw new AirplaneAlreadyInAirException("ERROR: Airplane is in the air");
            case ENGINE_ON:

                airplaneState = airplaneState.FLYING;
                increaseAltitude();
            default:
                return false;
        }
    }

    public boolean turnMotorOff() throws AirplaneException {
        switch (airplaneState){
            case EXPLODED:
                throw new AirplaneExplodedException("ERROR:Can't perform any action on airplane because it's exploded.");
            case FLYING:
                throw new AirplaneCannotStopInAirException("DANGER: Cannot turn off motor when flying !");
            case ENGINE_OFF:
                throw new AirplaneAlreadyStoppedException("ERROR: Motor is already off.");
            case ENGINE_ON:
                    airplaneState = AirplaneState.ENGINE_OFF;
                    return true;
            default:
                return false;
        }
    }

    public void increaseAltitude() throws AirplaneException
    {
        switch (airplaneState) {
            case EXPLODED:
                throw new AirplaneExplodedException("ERROR: Airplane exploded");
            case ENGINE_OFF:
                throw new AirplaneAlreadyStoppedException("ERROR: Cannot increase altitude without engine turned on!");
            case ENGINE_ON:
                throw new AirplaneNotInAirException("ERROR: Cannot increase altitude without taking off");
            case FLYING:
                for (AirPlaneGUI observer: observerList)
                    observer.altitudeChanged(altitude,altitude + CHANGE_ALTITUDE_LEVEL);
                altitude += CHANGE_ALTITUDE_LEVEL;
                checkAltitude();
            default:
                break;
        }
    }

    @Override
    public void checkAltitude() throws AirplaneException {
        switch (altitude) {
            case EXPLODE_ALTITUDE_LEVEL:
                airplaneState = airplaneState.EXPLODED;
                throw new AirplaneExplodedException("Aiplane: BOOM!");
            case DANGER_ALTITUDE_LEVEL:
            case DANGER_ALTITUDE_LEVEL + 1000:
                throw new AirplaneWarningException("WARNING: The plane cannot support pressure at 12000 altitude.\nCurrent altitude: " + altitude);
            case 0:
                airplaneState = airplaneState.ENGINE_ON;
            default:
                break;
        }
    }

    public void decreaseAltitude() throws AirplaneException
    {
        switch (airplaneState){
            case EXPLODED:
                throw new AirplaneExplodedException("ERROR: Airplane is exploded !");
            case ENGINE_OFF:
                throw new AirplaneAlreadyStoppedException("ERROR: Cannot decrease altitude without engine turned on!");
            case ENGINE_ON:
                throw new AirplaneIsLandedException("ERROR: Cannot decrease altitude when landed");
            case FLYING:

                for (AirPlaneGUI observer: observerList)
                    observer.altitudeChanged(altitude,altitude - CHANGE_ALTITUDE_LEVEL);

                altitude -= CHANGE_ALTITUDE_LEVEL;
                checkAltitude();
                break;
        }
    }
}
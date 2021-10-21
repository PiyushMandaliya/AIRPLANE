//Author: Piyushkumar Govindbhai Mandaliya

package AirPlane.controller;

import AirPlane.model.AirPlane;
import AirPlane.model.Exception.*;
import AirPlane.view.AirPlaneGUI;


public class Main {
    public static void main(String[] args) {
        AirPlane airPlane = new AirPlane();
        new AirPlaneGUI(airPlane);
    }

    private static void consoleStuff() {
        AirPlane airPlane = new AirPlane();
        int choice = 0;
        while (true) {
            displayMenu();
            System.out.print("\n\nEnter your choice: ");
            choice = IO.getInt();

            try {
                switch (choice) {
                    case 1:
                        if (airPlane.turnMotorOn())
                            IO.println("\tSuccess: Motor started.");
                        break;
                    case 2:
                        if (airPlane.takeOffAirPlane())
                            IO.println("\tSuccess: AirPlane landed.");
                        break;
                    case 3:
                        if (airPlane.turnMotorOff())
                            IO.print("\tSuccess: Stopping motor.");
                        break;
                    case 4:
                        airPlane.increaseAltitude();
                        break;
                    case 5:
                        airPlane.decreaseAltitude();
                        break;
                    case 6:
                        IO.print("Exit!");
                        return;
                    default:
                        IO.println("Invalid choice! Your choice must beetween 1 - 6");
                }
            } catch (AirplaneException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n\n-------------");
        System.out.println("1 – Start motor" +
                "\n2 – Take off" +
                "\n3 – Stop motor" +
                "\n4 – Increase altitude" +
                "\n5 – Decrease altitude" +
                "\n6 – Exit");
    }
}

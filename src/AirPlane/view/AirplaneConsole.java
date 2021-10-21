package AirPlane.view;

public class AirplaneConsole
{
    AirplaneView airplane;

    public AirplaneConsole(AirplaneView airplane){
        this.airplane = airplane;
        start();
    }

    private void displayMenu() {
        System.out.println("\n\n-------------");
        System.out.println("1 – Start motor" +
                "\n2 – Take off" +
                "\n3 – Stop motor" +
                "\n4 – Increase altitude" +
                "\n5 – Decrease altitude" +
                "\n6 – Exit");
    }

    public void start(){
        int choice = 0;
        while (true){
            displayMenu();
            System.out.print("\n\nEnter your choice: ");
            choice = IO.getInt();

            try {
                switch (choice) {
                    case 1:
                        if (airplane.turnMotorOn())
                            IO.println("\tSuccess: Motor started.");
                        break;
                    case 2:
                        if (airplane.takeOffAirPlane())
                            IO.println("\tSuccess: AirPlane landed.");
                        break;
                    case 3:
                        if (airplane.turnMotorOff())
                            IO.print("\tSuccess: Stopping motor.");
                        break;
                    case 4:
                        airplane.increaseAltitude();
                        break;
                    case 5:
                        airplane.decreaseAltitude();
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
}

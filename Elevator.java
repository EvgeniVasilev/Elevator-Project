/**
 * @author Evgeni Vasilev
 * Run the program from cmd and follow the instructions
 * written in the console
 * If use IDE - NetBeans
 * press F6 key and follow the instructions
 * written in the console
 *
 */
package elevator;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Elevator {

    static int floor;
    static int seconds;
    static int resqueFloor;

    /**
     *
     * @param pickElevator
     * @throws InterruptedException
     */
    private static void procesMoveUpMoveDownEventsRegular(String pickElevator)
            throws InterruptedException {
        if (pickElevator.toUpperCase().equals("A")
                || pickElevator.toUpperCase().equals("B")) {
            System.out.println("Please select a floor: ");
            Scanner sc = new Scanner(System.in);
            int choosenFloor = sc.nextInt();
            if (choosenFloor >= -1 && choosenFloor <= 10) {
                Timer t = new Timer();
                Instant start = Instant.now();
                setTimer(choosenFloor);
                System.out.println(pickElevator.toUpperCase() + " moves to: " + choosenFloor);
                System.out.println(pickElevator.toUpperCase() + " doors open.");
                if (seconds == 1) {
                    System.out.println("-");
                } else if (seconds != 1) {
                    setDisplayofElevator(t);
                }

                TimeUnit.SECONDS.sleep(seconds);
                t.cancel();
                Instant end = Instant.now();
                Duration interval = Duration.between(start, end);
                System.out.println(pickElevator.toUpperCase() + " arives on "
                        + choosenFloor);
                System.out.println(pickElevator.toUpperCase() + " close doors.");
                System.out.println("Seconds spent in elevator: "
                        + interval.getSeconds());
            } else {
                System.out.println("No such a floor!");
            }
        }
    }

    /**
     * Sets elevator's display to show moving up and moving down
     *
     * @param t
     */
    private static void setDisplayofElevator(Timer t) {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                printLine();
            }
        }, 1, 1000);
    }

    /**
     * Prints on elevator's display
     */
    protected static void printLine() {
        System.out.println("-");
    }

    /**
     * Evaluates time spend in elevator based on time
     *
     * @param choosenFloor
     */
    private static void setTimer(int choosenFloor) {
        if (floor > choosenFloor) {
            seconds = floor - choosenFloor;
        } else if (floor < choosenFloor) {
            seconds = choosenFloor - floor;
        } else if (choosenFloor == 0) {
            seconds = floor;
        } else if (floor - choosenFloor == 1 || floor + choosenFloor == 1) {
            seconds = 1;
        }
    }

    /**
     * Choose what elevator's mode to emulate 'R' for Regular 'E' for Emergency
     *
     * @param mode
     * @return
     */
    protected static String setMode(String mode) {
        if (mode.equals("E")) {
            return "Emergency";
        }

        if (mode.equals("R")) {
            return "Regular";
        }

        if (!mode.equals("E") || !mode.equals("R")) {
            return "Wrong";
        }
        return null;
    }

    /**
     * Emulate and process rescue mode
     *
     * @param pickElevator
     */
    private static void ProcessRescueMode(String pickElevator) {
        // A elevator
        if (floor >= (-1) && floor <= 10) {
            System.out.println("Emergency button pushed in elevator " + pickElevator.toUpperCase());
            if (floor == (-1)) {
                resqueFloor = floor + 1;
                System.out.println(pickElevator.toUpperCase() + " moves to: " + resqueFloor);
            } else {
                resqueFloor = floor - 1;
                System.out.println(pickElevator.toUpperCase() + " moves to: " + resqueFloor);
            }
        }
        // B elevator
        if (floor >= 0 && floor <= 9) {
            System.out.println("Emergency button pushed in elevator " + pickElevator.toUpperCase());
            if (floor == 0) {
                resqueFloor = floor + 1;
                System.out.println(pickElevator.toUpperCase() + " moves to: " + resqueFloor);
            } else {
                resqueFloor = floor - 1;
            }
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        floor = (int) rand.nextInt((10 - (0 - 1)) + (0 - 1)) + (0 - 1);

        System.out.println("Select elevtor's mode. Type 'R' for regular and"
                + " 'E' for emergency mode:");
        Scanner chooseModeScanner = new Scanner(System.in);
        String choosenMode = chooseModeScanner.next();
        switch (setMode(choosenMode.toUpperCase())) {
            case "Regular": {
                // REGULAR MODE PROCESSING
                System.out.println("Please pick an elevator. Elevator \"A\" "
                        + "goes from 0 to 9th floor and -1 level. Elevator \"B\" "
                        + "goes from 0 to 10th floor (elevator is on "
                        + floor + " floor)");
                Scanner pickElevatorScanner = new Scanner(System.in);
                String pickElevator = pickElevatorScanner.nextLine();
                procesMoveUpMoveDownEventsRegular(pickElevator);
                break;
            }
            case "Emergency": {
                // EMERGENCY MODE PROCESSING
                System.out.println("Please pick an elevator. Elevator \"A\" "
                        + "goes from 0 to 9th floor and -1 level. Elevator \"B\" "
                        + "goes from 0 to 10th floor (elevator is on "
                        + floor + " floor)");
                Scanner pickElevatorScanner = new Scanner(System.in);
                String pickElevator = pickElevatorScanner.nextLine();
                // Type ER to emulate Reset button pushed
                System.out.println("Emulate push \"Emergeny Button\" by typing ER");

                Scanner pushEmrBtnScanner = new Scanner(System.in);
                String pushErmBtn = pushEmrBtnScanner.nextLine();

                if (pushErmBtn.toUpperCase().equals("ER")) {
                    Instant start = Instant.now();
                    ProcessRescueMode(pickElevator.toUpperCase());
                    System.out.println(pickElevator.toUpperCase() + " doors open.");
                    System.out.println("-");
                    TimeUnit.SECONDS.sleep(1);

                    Instant end = Instant.now();
                    Duration interval = Duration.between(start, end);
                    System.out.println(pickElevator.toUpperCase() + " arives on "
                            + resqueFloor);
                    System.out.println(pickElevator.toUpperCase() + " doors open.");
                    System.out.println("Seconds spent in elevator: "
                            + interval.getSeconds());
                    System.out.println("Type \"RST\" to close the door, please!");
                    // Type RST to emulate Reset button pushed
                    Scanner pushResetBtnScanner = new Scanner(System.in);
                    String pushResetBtn = pushResetBtnScanner.nextLine();
                    ProcessReset(pushResetBtn, pickElevator);
                }
                break;
            }
            case "Wrong":
                // IN CASE WRONG INPUT WAS GIVEN
                System.out.println("No such an elevator!");
                break;
            default:
                System.out.println("Oops!");
                break;
        }
    }

    private static void ProcessReset(String pushResetBtn, String pickElevator) {
        if (pushResetBtn.toUpperCase().equals("RST")) {
            System.out.println(pickElevator.toUpperCase() + " dooor close.");
        } else {
            System.out.println(pickElevator.toUpperCase() + " is still opened.");
        }
    }
}

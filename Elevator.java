/**
 * @author Evgeni Vasilev
 *
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
        if (String.valueOf(pickElevator).equals("A")
                || String.valueOf(pickElevator).equals("B")) {
            System.out.println("Please select a floor: ");
            Scanner sc = new Scanner(System.in);
            int choosenFloor = sc.nextInt();
            if (choosenFloor >= -1 && choosenFloor <= 10) {
                Timer t = new Timer();
                Instant start = Instant.now();
                setTimer(choosenFloor);
                System.out.println(pickElevator + " moves to: " + choosenFloor);
                System.out.println(pickElevator + " doors open.");
                if (seconds == 1) {
                    System.out.println("-");
                } else if (seconds != 1) {
                    setDisplayofElevator(t);
                }

                TimeUnit.SECONDS.sleep(seconds);
                t.cancel();
                Instant end = Instant.now();
                Duration interval = Duration.between(start, end);
                System.out.println(pickElevator + " arives on "
                        + choosenFloor);
                System.out.println(pickElevator + " close doors.");
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
        switch (setMode(choosenMode)) {
            case "Regular": {
                // REGULAR MODE PROCESSINF
                System.out.println("Please pick an elevator. Elevator \"A\" "
                        + "goes from 0 to 9th floor. Elevator \"B\" "
                        + "goes from 0 to 10th floor (elevator is on "
                        + floor + " floor)");
                Scanner pickElevatorScanner = new Scanner(System.in);
                String pickElevator = pickElevatorScanner.nextLine();
                procesMoveUpMoveDownEventsRegular(pickElevator);
                break;
            }
            case "Emergency": {
                System.out.println("Please pick an elevator. Elevator \"A\" "
                        + "goes from 0 to 9th floor. Elevator \"B\" "
                        + "goes from 0 to 10th floor (elevator is on "
                        + floor + " floor)");
                Scanner pickElevatorScanner = new Scanner(System.in);
                String pickElevator = pickElevatorScanner.nextLine();
                System.out.println("Emulate push \"Emergeny Button\" by typing ER");

                Scanner pushEmrBtnScanner = new Scanner(System.in);
                String pushErmBtn = pushEmrBtnScanner.nextLine();

                if (String.valueOf(pushErmBtn).equals("ER")) {
                    Instant start = Instant.now();
                    // A elevator
                    if (floor > (-1) && floor <= 9) {
                        System.out.println("Passenger is in elevator \"A\"");
                        if (floor == (-1)) {
                            resqueFloor = floor + 1;
                            System.out.println(pickElevator + " moves to: " + resqueFloor);
                        } else {
                            resqueFloor = floor - 1;
                            System.out.println(pickElevator + " moves to: " + resqueFloor);
                        }
                    }
                    System.out.println(pickElevator + " doors open.");
                    System.out.println("-");
                    TimeUnit.SECONDS.sleep(1);

                    Instant end = Instant.now();
                    Duration interval = Duration.between(start, end);
                    System.out.println(pickElevator + " arives on "
                            + resqueFloor);
                    System.out.println(pickElevator + " doors open.");
                    System.out.println("Seconds spent in elevator: "
                            + interval.getSeconds());
                    Scanner pushResetBtnScanner = new Scanner(System.in);
                    String pushResetBtn = pushResetBtnScanner.nextLine();

                }
                break;
            }
            case "Wrong":
                System.out.println("No such an elevator!");
                break;
            default:
                System.out.println("Opps!");
                break;
        }

    }
}

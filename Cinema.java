package cinema;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Cinema {

    static int rows;
    static int seats;
    static char[][] arrangement;
    static int currentIncome = 0;
    static int numberOfPurchasedTickets = 0;

    public static void main(String[] args) {
        arrangement = createArrangement();
        menu();
    }

    private static void menu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        switch (new Scanner(System.in).nextInt()) {
            case 1:
                showTheSeats();
            case 2:
                buyTicket();
            case 3:
                getStatistics();
            case 0:
                exit();
        }
        System.out.println();
    }

    private static char[][] createArrangement() {
        System.out.println("Enter the number of rows:");
        rows = new Scanner(System.in).nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = new Scanner(System.in).nextInt();
        arrangement = new char[rows][seats];
        System.out.println();
        for (int r = 0; r < rows; r++) {
            for (int s = 0; s < seats; s++) {
                arrangement[r][s] = 'S';
            }
        }
        return arrangement;
    }

    private static void showTheSeats() {
        StringBuilder stringBuilderSeatPlace = new StringBuilder("  1");
        for (int seat = 2; seat <= arrangement[0].length; seat++) {
            stringBuilderSeatPlace.append(" " + seat);
        }
        System.out.println("\nCinema:");
        System.out.println(stringBuilderSeatPlace);

        StringBuilder stringBuilderRowPlace = new StringBuilder();
        for (int row = 1; row <= arrangement.length; row++){
            stringBuilderRowPlace.append(row);
            for (int seat = 0; seat < arrangement[row - 1].length; seat++){
                stringBuilderRowPlace.append(" " + arrangement[row - 1][seat]);
            }
            stringBuilderRowPlace.append("\n");
        }
        System.out.println(stringBuilderRowPlace);
        menu();
    }

    private static void buyTicket() {
        System.out.println("\nEnter a row number:");
        int rowPlace = new Scanner(System.in).nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatPlace = new Scanner(System.in).nextInt();

        if (rowPlace <= 0 || seatPlace <= 0 || rowPlace * seatPlace > rows * seats ||
                rowPlace > rows || seatPlace > seats) {
            System.out.println("Wrong input!");
            buyTicket();
        } else if (arrangement[rowPlace - 1][seatPlace - 1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            buyTicket();
        } else {
            arrangement[rowPlace - 1][seatPlace - 1] = 'B';
            int ticketPrice = 8;
            if (rows * seats < 60) {
                ticketPrice = 10;
            } else {
                if (rows % 2 > 0) {
                    boolean inFront = (rows - 1) / 2 >= rowPlace;
                    if (inFront) {
                        ticketPrice = 10;
                    } else {
                        ticketPrice = 8;
                    }
                }
            }
            currentIncome += ticketPrice;
            numberOfPurchasedTickets++;
            System.out.println("Ticket price: $" + ticketPrice + "\n");
            menu();
        }
    }

    private static void getStatistics() {
        getNumberOfPurchasedTickets();
        getPercentage();
        getCurrentIncome();
        getTotalIncome();
        System.out.println();
        menu();
    }

    private static void getNumberOfPurchasedTickets() {
        System.out.println("\nNumber of purchased tickets: " + numberOfPurchasedTickets);
    }

    private static void getPercentage() {
        String formattedDouble = new DecimalFormat("#0.00")
                .format((double) numberOfPurchasedTickets / (seats * rows) * 100);
        System.out.println("Percentage: " + formattedDouble + "%");
    }

    private static void getCurrentIncome() {
        System.out.println("Current income: $" + currentIncome);
    }

    private static void getTotalIncome() {
        int totalInc;
        int totalSeats = rows * seats;
        if (totalSeats < 60) {
            totalInc = totalSeats * 10;
        } else {
            int frontRows = rows / 2;
            int backRows = rows - frontRows;
            totalInc = frontRows * seats * 10 + backRows * seats * 8;
        }
        System.out.println("Total income: $" + totalInc);
    }

    private static void exit() {
        return;
    }
}
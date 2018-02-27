import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // number of columns.
        int H = in.nextInt(); // number of rows.
        in.nextLine();
        int[][] rooms = new int[H][W];
        String[][] floors = new String[H][W];
        for (int i = 0; i < H; i++) {
            String LINE = in.nextLine(); // represents a line in the grid and contains W integers. Each integer represents one room of a given type.
            floors[i] = LINE.split("\\s");
            for (int j = 0; j < W; j++) {
                rooms[i][j] = Integer.parseInt(floors[i][j]);
            }
        }
        int EX = in.nextInt(); // the coordinate along the X axis of the exit (not useful for this first mission, but must be read).

        /*
        System.out.println(W);
        System.out.println(H);

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++)
                System.out.print(rooms[i][j]+" ");
            System.out.println();
        }
        */

        // game loop
        while (true) {
            int XI = in.nextInt();
            int YI = in.nextInt();
            String POS = in.next();
            
            if (rooms[YI][XI] == 1) {
                System.out.println(XI+" "+(YI+1));
                continue;
            }
            else if (rooms[YI][XI] == 2 || rooms[YI][XI] == 6) {
                if (POS.equals("LEFT")) {
                    System.out.println((XI+1)+" "+YI);
                    continue;
                }
                else {
                    System.out.println((XI-1)+" "+YI);
                    continue;
                }
            }
            else if (rooms[YI][XI] == 3) {
                System.out.println(XI+" "+(YI+1));
                continue;
            }
            else if (rooms[YI][XI] == 4) {
                if (POS.equals("TOP")) {
                    System.out.println((XI-1)+" "+(YI));
                    continue;
                }
                else {
                    System.out.println(XI+" "+(YI+1));
                    continue;
                }
            }
            else if (rooms[YI][XI] == 5) {
                if (POS.equals("TOP")) {
                    System.out.println((XI+1)+" "+(YI));
                    continue;
                }
                else {
                    System.out.println(XI+" "+(YI+1));
                    continue;
                }
            }
            else if (rooms[YI][XI] == 7) {
                if (POS.equals("TOP")) {
                    System.out.println(XI+" "+(YI+1));
                    continue;
                }
                else {
                    System.out.println((XI)+" "+(YI+1));
                    continue;
                }
            }
            else if (rooms[YI][XI] == 8) {
                if (POS.equals("LEFT")) {
                    System.out.println((XI)+" "+(YI+1));
                    continue;
                }
                else {
                    System.out.println((XI)+" "+(YI+1));
                    continue;
                }
            }
            else if (rooms[YI][XI] == 9) {
                if (POS.equals("TOP")) {
                    System.out.println(XI+" "+(YI+1));
                    continue;
                }
                else {
                    System.out.println((XI)+" "+(YI+1));
                    continue;
                }
            }
            else if (rooms[YI][XI] == 10) {
                System.out.println((XI-1)+" "+(YI));
                continue;
            }
            else if (rooms[YI][XI] == 11) {
                System.out.println((XI+1)+" "+(YI));
                continue;
            }
            System.out.println(XI+" "+(YI+1));
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // One line containing the X Y coordinates of the room in which you believe Indy will be on the next turn.
            
        }
    }
}
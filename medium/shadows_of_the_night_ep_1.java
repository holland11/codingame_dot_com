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
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X0 = in.nextInt();
        int Y0 = in.nextInt();
        int lowX = 0;
        int lowY = 0;

        int magnitude = 1;
        int count = 0;
        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)
            int y = Y0;
            int x = X0;

            if (bombDir.charAt(0) == 'U') {
                if ((Y0-lowY)/(2) > 0) {
                    if (((Y0-lowY)/2.00)%(1.00)>=0.5) 
                        Y0-= (Y0-lowY)/(2)+1;
                    else Y0-= (Y0-lowY)/(2);
                }
                else Y0-=1;
                H = y;
            }
            else if (bombDir.charAt(0) == 'D') {
                if ((H-Y0)/(2) > 0) {
                    if (((H-Y0)/2.00)%(1.00)>=0.5) 
                        Y0+= (H-Y0)/(2)+1;
                    else Y0+= (H-Y0)/(2);
                }
                else Y0+=1;
                lowY = y;
            }
            if (bombDir.contains("R")) {
                if ((W-X0)/(2) > 0) {
                    if (((W-X0)/2.00)%(1.00)>=0.5)
                        X0+= (W-X0)/(2)+1;
                    else X0+= (W-X0)/2;
                }
                else X0+=1;
                lowX = x;
                
            }
            else if (bombDir.contains("L")) {
                if ((X0-lowX)/(2) > 0) {
                    if (((X0-lowX)/2.00)%(1.00)>=0.5)
                        X0-= (X0-lowX)/(2)+1;
                    else X0-= (X0-lowX)/2;
                }
                else X0-=1;
                W = x;
            }
            
            
            System.out.println(X0+" "+Y0);
        }
    }
}
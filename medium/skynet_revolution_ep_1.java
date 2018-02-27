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
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        Set<Integer> gateways = new HashSet<Integer>();
        List<ArrayList<Integer>> links = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < N; i++) {
            links.add(new ArrayList<Integer>());
        }
        //System.out.println();
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            links.get(N1).add(N2);
            links.get(N2).add(N1);
            //System.out.println("i="+i+" "+N1+" "+N2);
        }
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            gateways.add(EI);
            //System.out.println("i="+i+" "+EI);
        }
        //System.out.println();

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            int found = 0;
            for (int i = 0, n = links.get(SI).size(); i < n; i++) {
                if (gateways.contains(links.get(SI).get(i))) {
                    System.out.println(SI+" "+links.get(SI).get(i));
                    found = 1;
                    continue;
                }
            }
            if (found == 1) continue;
            System.out.println(SI+" "+links.get(SI).get(0));
            /*
            for (int s : gateways) {
                System.out.println(s+" "+links.get(s).get(0));
                continue;
            } */
        }
    }
}
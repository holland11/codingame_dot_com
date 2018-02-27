import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
 
 // is startNode a gatelink? cut it if yes. if no, search gatelinks for double. if double exists, find closest double and cut. 
 
class Player {
    
    public static List<ArrayList<Integer>> links;
    public static List<Integer> gateways, gatelinks;
    public static int N,L,E;
    public static boolean[] visited;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        N = in.nextInt(); // the total number of nodes in the level, including the gateways
        L = in.nextInt(); // the number of links
        E = in.nextInt(); // the number of exit gateways
        visited = new boolean[N];
        links = new ArrayList<ArrayList<Integer>>();
        gateways = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) {
            links.add(new ArrayList<Integer>());
        }
        
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            links.get(N1).add(N2);
            links.get(N2).add(N1);
        }
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            gateways.add(EI);
        }
        
        gatelinks = gateLinks();

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn

            debugging("agent node: "+SI+"");
            
            
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // Example: 3 4 are the indices of the nodes you wish to sever the link between
            System.out.println(linkToCut(SI));
        }
    }
    
    public static List<Integer> gateLinks() {
        List<Integer> temp = new ArrayList<Integer>();
        for (int i = 0, n = gateways.size(); i < n; i++) {
            for (int j = 0, m = links.get(gateways.get(i)).size(); j < m; j++) {
                temp.add(links.get(gateways.get(i)).get(j));
            }
        }
        return temp;
    }
    
    public static void debugging(String additional) {
        System.err.println(linksToString() + "\n" + gatewaysToString() +
                           "\n" + gateLinksToString() + "\n" + additional);
    }
    
    public static String gateLinksToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("gatelinks: ");
        
        for (int i = 0, n = gatelinks.size(); i < n; i++) {
            sb.append(gatelinks.get(i)+" ");
        }
        return sb.toString();
    }
    
    public static String linksToString() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0, n = links.size(); i < n; i++) {
            sb.append("link["+i+"]: ");
            for (int j = 0, m = links.get(i).size(); j < m; j++) {
                sb.append(links.get(i).get(j)+" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static String gatewaysToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("gateways: ");
        for (int i = 0; i < gateways.size(); i++) {
            sb.append(gateways.get(i)+" ");
        }
        return sb.toString();
    }
    
    public static String linkToCut(int SI) {
        int node1;
        if (gatelinks.contains(SI)) {
            node1 = SI;
        }
        else {
            Node dangerNode = closestDangerNode(SI);
            node1 = dangerNode.num;
        }
        int node2 = -1;
        for (int i = 0, n = links.get(node1).size(); i < n; i++) {
            if (gateways.contains(links.get(node1).get(i))) {
                node2 = links.get(node1).get(i);
            }
        }
        
        removeLinks(node1,node2);
        gatelinks = gateLinks();
        
        return ""+node1+" "+node2;
    }
    
    public static Node closestDangerNode(int start) {
        for (int i = 0, n = visited.length; i<n; i++) {
            visited[i] = false;
        }
        visited[start] = true;
        for (int i = 0, n = gateways.size(); i < n; i++) {
            visited[gateways.get(i)] = true;
        }
        Node node = new Node(null,start,0);
        Node oneNode = null;
        Queue<Node> q = new LinkedList<Node>();
        q.add(node);
        Node result = null;
        Node doubleNode = null;
        
        while (!q.isEmpty()) {
            node = q.remove();
            if (gatelinks.contains(node.num)) {
                /*
                if (!showTwice(gatelinks,node.num)) {
                    if (oneNode == null) {
                        oneNode = node;
                    }
                    else if (oneNode.dist > node.dist) {
                        oneNode = node;
                    }
                }
                if (result == null && showTwice(gatelinks,node.num)) {
                    return node;
                }
                else if (result != null && result.dist >= node.dist && showTwice(gatelinks,node.num)) {
                    return node;
                }
                else if (result == null) {
                    result = node;
                }
                else if (result.dist > node.dist) {
                    result = node;
                } */
                
                if (showTwice(gatelinks,node.num)) {
                    if (dangerPath(node)) {
                        return node;
                    }
                    else if (doubleNode == null) {
                        doubleNode = node;
                    }
                }
                else {
                    if (oneNode == null) {
                        oneNode = node;
                    }
                    else if (oneNode.dist > node.dist) {
                        oneNode = node;
                    }
                }
                
            }
            for (int i = 0, n = links.get(node.num).size(); i < n; i++) {
                if (visited[links.get(node.num).get(i)] == false) {
                    q.add(new Node(node,links.get(node.num).get(i),node.dist+1));
                    visited[links.get(node.num).get(i)] = true;
                }
            }
            
        }
        if (doubleNode != null) {
            return doubleNode;
        }
        return oneNode;
    }
    
    public static boolean dangerPath(Node node) {
        while (node.prev != null) {
            if (!gatelinks.contains(node.num)) {
                return false;
            }
            node = node.prev;
        }
        return true;
    }
    
    public static boolean showTwice(List<Integer> list, int num) {
        boolean seen = false;
        for (int i = 0, n = list.size(); i < n; i++) {
            if (list.get(i) == num) {
                if (seen) {
                    return true;
                }
                seen = true;
            }
        }
        return false;
    }
    
    public static void removeLinks(int node1, int node2) {
        int index = -1;
        for (int i = 0, n = links.get(node1).size(); i < n; i++) {
            if (links.get(node1).get(i) == node2) {
                index = i;
            }
        }
        links.get(node1).remove(index);
        for (int i = 0, n = links.get(node2).size(); i < n; i++) {
            if (links.get(node2).get(i) == node1) {
                index = i;
            }
        }
        links.get(node2).remove(index);
    }
    
    private static class Node {
        int num,dist;
        Node prev;
        public Node(Node previous,int number,int distance) {
            prev = previous;
            num = number;
            dist = distance;
        }
    }
}
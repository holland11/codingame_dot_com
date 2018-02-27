import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
 
 /* 
  * C found:
  *     C reachable from kirk
  *         T reachable from C within alarm time
  *             go to C, go to T
  *         bfs to nearest ?
  *     bfs to nearest ?
  * bfs to nearest ?
  */
class Player {
    
    public static boolean[][] visited;
    public static char[][] grid; // . = empty, # = wall, C = control room, T = start, ? = unvisited
    public static int cX,cY,KR,KC;
    public static int tX,tY;
    public static int nodeX,nodeY;
    public static boolean inRouteC = false;
    public static boolean inRouteT = false;
    public static String visitedText;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt(); // number of rows.
        int C = in.nextInt(); // number of columns.
        visited = new boolean[R][C];
        grid = new char[R][C];
        cX = -1;
        cY = -1;
        int temp = 0;

        int A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.

        // game loop
        while (true) {
            if (temp == 0) {
                KR = in.nextInt();
                KC = in.nextInt();
                tX = KC;
                tY = KR;
                temp = 1;
            }
            else {
                KR = in.nextInt(); // row where Kirk is located.
                KC = in.nextInt(); // column where Kirk is located.
            }
            for (int i = 0; i < R; i++) {
                String ROW = in.next(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).
                for (int j = 0; j < C; j++) {
                    grid[i][j] = ROW.charAt(j);
                }
            }
            
            if (inRouteC) {
                if (cX == KC && cY == KR) {
                    inRouteC = false;
                    inRouteT = true;
                }
                else {
                    System.out.println(dirToC(KC,KR));
                    continue;
                }
            }
            
            if (inRouteT) {
                System.out.println(dirToT(KC,KR));
                continue;
            }
            
            if (!inRouteC && !inRouteT) {
                System.err.println("inRouteC: "+inRouteC+" | inRouteT: "+inRouteT);
                if (cExists()) {
                    System.err.println("cExists: true");
                    if (cReachable(KC,KR)) {
                        System.err.println("cReachable: true");
                        if (tReachableFromC()) {
                            System.err.println("tEachableFromC: true");
                            inRouteC = true;
                            System.out.println(dirToC(KC,KR));
                            continue;
                        }
                    }
                }
            }
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(dirToUnknown(KC,KR));
        }
    }
    
    public static boolean cReachable(int startX, int startY) {
        //debugging("start: "+startX+" "+startY);
        Node endNode = bfsRoute(startX,startY,'C');
        if (endNode == null) {
            return false;
        }
        else return true;
    }
    
    public static boolean tReachableFromC() {
        debugging("start: "+cX+" "+cY);
        Node endNode = bfsRoute(cX,cY,'T');
        if (endNode == null) {
            return false;
        }
        else return true;
    }
    
    public static Node aStarRoute(int startX, int startY, char target) {
        
        if (target == '?') {
            updateVisited2();
        }
        else {
            updateVisited();
        }
        
        double dist = Math.sqrt(Math.abs(startX-tX)+Math.abs(startY-tY));    // a^2 + b^2 = c^2 ---- a = |x1-x2|, b = |y1-y2| ----
        Node node = new Node(startX,startY,null,0,dist);
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        
        
        int maxX = visited[0].length-1;
        int maxY = visited.length-1;
        
        while (!nodes.isEmpty()) {
            node = getShortestDistNode(nodes);
            
            nodeY = node.y;
            nodeX = node.x;
            
            try {
            if (grid[node.y][node.x] == target) {
                //System.err.println("node: "+nodeX+" "+nodeY);
                return node;
            }
            } catch (ArrayIndexOutOfBoundsException e) {
                //debugging(nodeX+" "+nodeY+" | "+maxX+" "+maxY+"\n");
                continue;
            }
            
            visited[node.y][node.x] = true;
            if (node.x > 0 && visited[node.y][node.x-1] == false) {
                dist = Math.sqrt(Math.abs(startX-tX)+Math.abs(startY-tY));
                nodes.add(new Node(node.x-1,node.y,node,node.layer+1));
            }
            if (node.y > 0 && visited[node.y-1][node.x] == false) {
                nodes.add(new Node(node.x,node.y-1,node,node.layer+1));
            }
            if (node.x < maxX && visited[node.y][node.x+1] == false) {
                nodes.add(new Node(node.x+1,node.y,node,node.layer+1));
            }
            if (node.y < maxY && visited[node.y+1][node.x] == false) {
                nodes.add(new Node(node.x,node.y+1,node,node.layer+1));
            }
        }
        return null;
    }
    
    public static Node getShortestDistNode(ArrayList<Node> nodes) {
        int index = 0;
        Node node = nodes.get(0);
        
        for (int i = 0; i < nodes.size(); i++) {
            if (node.dist > nodes.get(i).dist) {
                index = i;
                node = nodes.get(i);
            }
        }
        nodes.remove(index);
        return node;
    }
    
    public static boolean cExists() {
        for (int i = 0, rows = grid.length; i < rows; i++) {
            for (int j = 0, cols = grid[i].length; j < cols; j++) {
                if (grid[i][j] == 'C') {
                    cX = j;
                    cY = i;
                    return true;
                }
            }
        }
        return false;
    }
    
    public static String dirToC(int startX, int startY) {
        Node endNode = bfsRoute(startX,startY,'C');
        return nodeToDir(endNode);
    }
    
    public static String dirToUnknown(int startX, int startY) {
        Node endNode = bfsRoute(startX,startY,'?');
        return nodeToDir(endNode);
    }
    
    public static String dirToT(int startX, int startY) {
        Node endNode = bfsRoute(startX,startY,'T');
        return nodeToDir(endNode);
    }
    
    public static Node bfsRoute(int startX, int startY, char target) {
        if (target == '?') {
            updateVisited2();
        }
        else {
            updateVisited();
        }
        Node node = new Node(startX,startY,0);
        Queue<Node> q = new LinkedList<Node>();
        q.add(node);
        
        int maxX = visited[0].length-1;
        int maxY = visited.length-1;
        
        while (!q.isEmpty()) {
            node = q.remove();
            
            nodeY = node.y;
            nodeX = node.x;
            
            
            
            try {
            if (grid[node.y][node.x] == target) {
                System.err.println("node: "+nodeX+" "+nodeY);
                return node;
            }
            } catch (ArrayIndexOutOfBoundsException e) {
                //debugging(nodeX+" "+nodeY+" | "+maxX+" "+maxY+"\n");
                continue;
            }
            
            visited[node.y][node.x] = true;
            if (node.x > 0 && visited[node.y][node.x-1] == false) {
                q.add(new Node(node.x-1,node.y,node,node.layer+1));
                visited[node.y][node.x-1] = true;
            }
            if (node.y > 0 && visited[node.y-1][node.x] == false) {
                q.add(new Node(node.x,node.y-1,node,node.layer+1));
                visited[node.y-1][node.x] = true;
            }
            if (node.x < maxX && visited[node.y][node.x+1] == false) {
                q.add(new Node(node.x+1,node.y,node,node.layer+1));
                visited[node.y][node.x+1] = true;
            }
            if (node.y < maxY && visited[node.y+1][node.x] == false) {
                q.add(new Node(node.x,node.y+1,node,node.layer+1));
                visited[node.y+1][node.x] = true;
            }
        }
        System.err.println("node: "+nodeX+" "+nodeY);
        return null;
        
    }
    
    public static void debugging(String additional) {
            System.err.println(printGrid()+"\n"+visitedText+"\n"+"inRouteC: "+inRouteC+
                                   " | inRouteT: "+inRouteT+"\n"+"kirk: "+
                                   KC+" "+KR+" | c: "+cX+" "+cY+"\n"+additional);
    }
    
    public static String nodeToDir(Node node) {
        if (node == null) {
            System.err.println("error nodeToDir()");
        }
        while (node.prev != null && node.prev.prev != null) {
            node = node.prev;
        }
        
        if (node.prev == null) {
            System.err.println("error nodeToDir()2");
        }
        
        int x2 = node.x;
        int y2 = node.y;
        int x1 = node.prev.x;
        int y1 = node.prev.y;
        
        if (x2 != x1 && y2 != y1) {
            System.err.println("error nodeToDir()3");
        }
        
        if (x2 > x1) {
            return "RIGHT";
        }
        else if (x2 < x1) {
            return "LEFT";
        }
        else if (y2 > y1) {
            return "DOWN";
        }
        else {
            return "UP";
        }
        
    }
    
    public static String printGrid() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, rows = grid.length; i < rows; i++) {
            for (int j = 0, cols = grid[i].length; j < cols; j++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return (sb.toString());
    }
    
    public static void updateVisited() {
        visited = new boolean[grid.length][grid[0].length];
        visitedText = "";
        for (int i = 0, rows = visited.length; i < rows; i++) {
            for (int j = 0, cols = visited[i].length; j < cols; j++) {
                if (grid[i][j] == '#' || grid[i][j] == '?') {
                    visited[i][j] = true;
                    visitedText += "?";
                }
                else {
                    visited[i][j] = false;
                    visitedText += ".";
                }
            }
            visitedText += "\n";
        }
    }
    
    public static void updateVisited2() {
        visited = new boolean[grid.length][grid[0].length];
        visitedText = "";
        for (int i = 0, rows = visited.length; i < rows; i++) {
            for (int j = 0, cols = visited[i].length; j < cols; j++) {
                if (grid[i][j] == '#') {
                    visited[i][j] = true;
                    visitedText += "#";
                }
                else {
                    visited[i][j] = false;
                    visitedText += ".";
                }
            }
            visitedText += "\n";
        }
    }
        
    private static class Node {
        public int x,y,layer;
        public Node prev;
        public double dist;
        
        public Node(int x, int y, Node prev) {
            this.x = x;
            this.y = y;
            this.prev = prev;
            layer = 0;
        }
        public Node(int x, int y, Node prev, int layer, double dist) {
            this.x = x;
            this.y = y;
            this.prev = prev;
            this.layer = layer;
            this.dist = dist;
        }
        public Node(int x, int y, int layer) {
            this(x,y,null,layer);
        }
        public Node(int x, int y, Node prev, int layer) {
            this.x = x;
            this.y = y;
            this.prev = prev;
            this.layer = layer;
        }
    }    
}
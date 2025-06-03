import java.util.ArrayList;
import java.util.LinkedList;

public class Search {
     /**
     * Finds the location of the nearest reachable cheese from the rat's position.
     * Returns a 2-element int array: [row, col] of the closest 'c'. If there are multiple
     * cheeses that are tied for the same shortest distance to reach, return
     * any one of them.
     * 
     * 'R' - the rat's starting position (exactly one)
     * 'o' - open space the rat can walk on
     * 'w' - wall the rat cannot pass through
     * 'c' - cheese that the rat wants to reach
     * 
     * If no rat is found, throws EscapedRatException.
     * If more than one rat is found, throws CrowdedMazeException.
     * If no cheese is reachable, throws HungryRatException
     *
     * oooocwco
     * woowwcwo
     * ooooRwoo
     * oowwwooo
     * oooocooo
     *
     * The method will return [0,4] as the nearest cheese.
     *
     * @param maze 2D char array representing the maze
     * @return int[] location of the closest cheese in row, column format
     * @throws EscapedRatException if there is no rat in the maze
     * @throws CrowdedMazeException if there is more than one rat in the maze
     * @throws HungryRatException if there is no reachable cheese
     */
    public static int[] nearestCheese(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException {
        boolean[][] visited = new boolean[maze.length][maze[0].length]; // assuming we have rectanglular array (maze[0])
        
        int[] start = ratLocation(maze);

        // queue = [], start off w/ empty queue
        Queue<int[]> queue = new LinkedList<>();

        // queue.add(start) , visit rat first
        queue.add(start);

        // while queue not empty
        //  current = queue.poll()
        //  if visited: continue, dont want to do anything,
        //  else -> mark visited
        //  if (current is cheese): return current
        //  else -> add all neighbors to queue
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curR = current[0];
            int curC = current[1];

            if (visited[curR][curC]) continue; // if visited is true since boolean array starts off all false

            visited[curR][curC] = true;

            if (maze[curR][curC] == 'c') return current; // why not return "maze[curR][curC]" ?????????? bc current is row, column location.

            // get our neighbors and put into list variable
            //for (int[] neighbor : getNeighbors(maze, current)) {
            //    queue.add(neighbor);
            //}

            queue.addAll(getNeighbors(maze, current));
        }

        // if searched entirely and never found it -> throw hungry exception
        throw new HungryRatException();
    }

    public static List<int[]> getNeighbors(char[][] maze, int[] current) {
        int curR = current[0];
        int curC = current[1]; // ?

        int[][] directions = {
            {-1, 0}, // UP
            {1, 0},  // DOWN
            {0, -1}, // LEFT
            {0, 1}   // RIGHT
        };

        List<int[]> possibleMovies = new ArrayList<>();

        for (int[] direction : directions) {
            int changeR = direction[0];
            int changeC = direction[1];

            int newR = curR + changeR;
            int newC = curC + changeC;

            if (newR >= 0 && newR < maze.length &&          // check row is valid
                newC >= 0 && newC < maze[newR].length &&    // check column is valid
                maze[newR][newC] != 'w')                    // make sure its not a wall
            {
                int[] validMove = {newR, newC};
                possibleMovies.add(validMove);
            }
        }

        return possibleMovies;
    }

    public static int[] ratLocation(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException {
        int[] location = null;

        // Searching
        for (int r = 0; r < maze.length; r++) {
            for(int c = 0; c < maze[r].length; c++) { // if that specific row.length
                if (maze[r][c] == 'R') {
                    if (location != null) throw new CrowdedMazeException();

                    location = new int[]{r, c};
                }
            }
        }

        if (location == null) {
            // 404 rat not found
            throw new EscapedRatException();
        }

        return location;
    }
}

public static void main(String[] args) {}
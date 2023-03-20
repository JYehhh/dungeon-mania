package dungeonmania;

import java.util.ArrayList;
import java.util.Collections;

import dungeonmania.staticentity.Wall;
import dungeonmania.util.Position;

public class DungeonGenerator {
    
    int xStart;
    int yStart;
    int xEnd;
    int yEnd;
    int width;
    int height;
    boolean maze[][]; 
    ArrayList<Coordinates> options;
    ArrayList<Entity> entities;

    DungeonGenerator(int xStart, int yStart, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.width = xEnd - xStart + 1;
        this.height = yEnd - yStart + 1;
        this.options = new ArrayList<Coordinates>();
        this.entities = new ArrayList<Entity>();
    }

    public ArrayList<Entity> generateEntities() {
        randomizesPrims(width, height);
        addBoundaryWalls(); 
        addMazeWalls();
        return entities;
    }

    public void randomizesPrims(int width, int height) {
        this.maze =  new boolean[width][height];

        maze[0][0] = true;
        generateNeighbors(0, 0, false, options);
        
        while (options.size() != 0) {
            Coordinates next = removeRandomOptions();
            
            ArrayList<Coordinates> neighbors = new ArrayList<Coordinates>();
            generateNeighbors(next.getxCoord(), next.getyCoord(), true, neighbors);

            if (neighbors.size() != 0) {
                Coordinates neighbor = removeRandomList(neighbors);
                maze[next.getxCoord()][next.getyCoord()] = true;

                // position between them 
                Coordinates inBetween = next.getInBetween(neighbor);
                maze[inBetween.getxCoord()][inBetween.getyCoord()] = true;
                
                maze[neighbor.getxCoord()][neighbor.getyCoord()] = true;
            }
            generateNeighbors(next.getxCoord(), next.getyCoord(), false, this.options);
        }

        // Ensure that the end square is a empty space and that there is at least 1 empty space
        // around the end tile
        if (maze[width - 1][height - 1] == false) {
            maze[width - 1][height - 1] = true;
            ArrayList<Coordinates> neighbors = new ArrayList<Coordinates>();
            generateNeighbors(width - 1, height - 1, true, neighbors);
            if (neighbors.size() == 0) {
                ArrayList<Coordinates> neighbors1 = new ArrayList<Coordinates>();
                generateNeighborsDist1(width - 1, height - 1, false, neighbors1);
                Coordinates neighbor = removeRandomList(neighbors1);
                maze[neighbor.getxCoord()][neighbor.getyCoord()] = true;
            }
        }
        
    }

    public void addBoundaryWalls() {
        for (int i = xStart - 1; i < xEnd + 2; i++) {
            for (int j = yStart - 1; j < yEnd + 2; j++) {
                if (i == (xStart - 1) || i == (xEnd + 1) || j == (yStart - 1) || j == (yEnd + 1)) {
                    entities.add(new Wall(new Position(i, j)));
                } 
            }
        }
    }
    
    public void addMazeWalls() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {       
                if (maze[i][j] == false) {
                    entities.add(new Wall(new Position(i + xStart, j + yStart)));
                } 
            }
        }
    }

    public void generateNeighbors(int xCoord, int yCoord, Boolean isEmpty, ArrayList<Coordinates> List) {
        if (validCoordinates(xCoord - 2, yCoord, width, height)) {
            if (maze[xCoord - 2][yCoord] == isEmpty) 
            List.add(new Coordinates(xCoord - 2, yCoord));
        }
        
        if (validCoordinates(xCoord, yCoord - 2, width, height)) {
            if (maze[xCoord][yCoord - 2] == isEmpty) 
            List.add(new Coordinates(xCoord, yCoord - 2));
        }
        
        if (validCoordinates(xCoord + 2, yCoord, width, height)) {
            if (maze[xCoord + 2][yCoord] == isEmpty) 
            List.add(new Coordinates(xCoord + 2, yCoord));
        }
        
        if (validCoordinates(xCoord, yCoord + 2, width, height)) {
            if (maze[xCoord][yCoord + 2] == isEmpty) 
            List.add(new Coordinates(xCoord, yCoord + 2));
        }
    }
    
    public void generateNeighborsDist1(int xCoord, int yCoord, Boolean isEmpty, ArrayList<Coordinates> List) {
        if (validCoordinates(xCoord - 1, yCoord, width, height)) {
            if (maze[xCoord - 1][yCoord] == isEmpty) 
            List.add(new Coordinates(xCoord - 1, yCoord));
        }
        
        if (validCoordinates(xCoord, yCoord - 1, width, height)) {
            if (maze[xCoord][yCoord - 1] == isEmpty) 
            List.add(new Coordinates(xCoord, yCoord - 1));
        }
        
        if (validCoordinates(xCoord + 1, yCoord, width, height)) {
            if (maze[xCoord + 1][yCoord] == isEmpty) 
            List.add(new Coordinates(xCoord + 1, yCoord));
        }
        
        if (validCoordinates(xCoord, yCoord + 1, width, height)) {
            if (maze[xCoord][yCoord + 1] == isEmpty) 
            List.add(new Coordinates(xCoord, yCoord + 1));
        }
    }

    public Coordinates removeRandomOptions() {
        Collections.shuffle(options);  
        return options.remove(0);
    }

    public Coordinates removeRandomList(ArrayList<Coordinates> List) {
        Collections.shuffle(List);  
        return List.remove(0);
    }

    //remove after implementation
    public void printMaze() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (maze[i][j] == true) {
                    System.out.print("_");
                } else {
                    System.out.print("W");
                }
            }
            System.out.println("\n");
        }
    }
    
    /**
     * @return true if a coordinate lies on the boundary
     */
    public boolean boundaryChecker(int xCoord, int yCoord, int width, int height) {
        if (xCoord == 0 || 
        xCoord == width - 1 || 
        yCoord == 0 || 
        yCoord == height - 1) 
        return true;
        return false;
    }
    
    
        /**
         * @return true if a coordinate is valid 
         */
    public boolean validCoordinates(int xCoord, int yCoord, int width, int height) {
        if (xCoord < 0 ||
            xCoord >= width ||
            yCoord < 0 || 
            yCoord >= height)
            return false;
        return true;
    }
 }

package dungeonmania;

public class Coordinates {
    int xCoord;
    int yCoord;

    Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public boolean validCoordinates(int width, int height) {
        if (xCoord < 0 ||
            xCoord >= width ||
            yCoord < 0 || 
            yCoord >- height)
            return false;
        return true;
    }

    public Coordinates getInBetween(Coordinates target) {

        if (xCoord == target.getxCoord()) {
            if (yCoord > target.getyCoord()) {
                return new Coordinates(xCoord, yCoord - 1);
            } else {
                return new Coordinates(xCoord, yCoord + 1);
            }

        } else if (yCoord == target.getyCoord()) {
            if (xCoord > target.getxCoord()) {
                return new Coordinates(xCoord - 1, yCoord);
            } else {
                return new Coordinates(xCoord + 1, yCoord);
            }
        }

        return new Coordinates(0, 0);
    }

    public void printCoordinate() {
        System.out.println("x: " + this.xCoord);
        System.out.println("y: " + this.yCoord);
        System.out.println("------");
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}

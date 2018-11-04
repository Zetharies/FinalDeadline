package models;

public enum EnumPlayerFacing {
    NORTH(0, 1), // X, Y Coordinates for North
    SOUTH(0, -1), // X, Y Coordinates for South
    EAST(1, 0), // X, Y Coordinates for East
    WEST(-1, 0); // X, Y Coordinates for West

    public static final EnumPlayerFacing N = NORTH;
    public static final EnumPlayerFacing S = SOUTH;
    public static final EnumPlayerFacing E = EAST;
    public static final EnumPlayerFacing W = WEST;
    public static final EnumPlayerFacing UP = NORTH;
    public static final EnumPlayerFacing DOWN = SOUTH;
    public static final EnumPlayerFacing RIGHT = EAST;
    public static final EnumPlayerFacing LEFT = WEST;

    private int x, y;

    EnumPlayerFacing(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

package se.liu.alfsj019.tetris;

public enum Direction
{
    LEFT, RIGHT, DOWN, UP;

    public Direction getOpposite() {
        if (this == LEFT) {
            return RIGHT;
        }
        if (this == RIGHT) {
            return LEFT;
        }
        if (this == DOWN) {
            return UP;
        }
        if (this == UP) {
            return DOWN;
        }
        return null;
    }
}


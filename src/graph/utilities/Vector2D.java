package graph.utilities;

public class Vector2D<E, T> {
    private E x;
    private T y;
    
    public Vector2D(E x, T y){
        this.x = x;
        this.y = y;
    }

    public E getX() {
        return x;
    }

    public void setX(E x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }
}

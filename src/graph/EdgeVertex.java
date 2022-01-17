package graph;

public class EdgeVertex<E> {
    private E vertex;
    private float weight;
    
    public EdgeVertex(E vertex, float weight){
        this.vertex = vertex;
        this.weight = weight;
    }

    public E getVertex() {
        return vertex;
    }

    public void setVertex(E vertex) {
        this.vertex = vertex;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}

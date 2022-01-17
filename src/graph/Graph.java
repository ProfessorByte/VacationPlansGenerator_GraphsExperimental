package graph;

import graph.utilities.Vector2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

public class Graph<E> {

    private HashMap<E, ArrayList<EdgeVertex<E>>> graph;
    private ArrayList<Vector2D<E, E>> edges;
    private boolean isDirected;

    public Graph() {
        this.graph = new HashMap<>();
        this.edges = new ArrayList<>();
        this.isDirected = false;
    }

    public HashMap<E, ArrayList<EdgeVertex<E>>> getHashMapGraph() {
        return graph;
    }

    public ArrayList<Vector2D<E, E>> getEdges() {
        return edges;
    }

    public ArrayList<E> getVertices() {
        ArrayList<E> ans = new ArrayList<>();
        ans.addAll(graph.keySet());
        return (ArrayList<E>) ans.clone();
    }

    public int getSize() {
        return edges.size();
    }

    public int getOrder() {
        return graph.keySet().size();
    }

    public boolean isDirected() {
        return this.isDirected;
    }

    public int handshakingLemma() {
        int res = -1;
        if (!isDirected) {
            res = 2 * getSize();
        }
        return res;
    }

    public int degreeVertex(E vertex) {
        int ans = -1;
        if (graph.containsKey(vertex) && !isDirected) {
            ans = graph.get(vertex).size();
        }
        return ans;
    }

    public ArrayList<E> adjacents(E vertex) {
        ArrayList<E> ans = new ArrayList<>();
        if (graph.containsKey(vertex)) {
            for (EdgeVertex<E> edgeVertex : graph.get(vertex)) {
                ans.add(edgeVertex.getVertex());
            }
        }
        return (ArrayList<E>) ans.clone();
    }

    //Recorrido Por Profundidad
    public ArrayList<E> DFS(E origin) {
        ArrayList<E> ans = new ArrayList<>();
        Stack<E> stack = new Stack<>();
        TreeSet<E> processeds = new TreeSet<>();
        stack.push(origin);
        while (!stack.isEmpty()) {
            E next = stack.pop();
            if (processeds.add(next)) {
                ans.add(next);
                stack.addAll(adjacents(next));
            }
        }
        return ans;
    }

    //Recorrido por Niveles
    public ArrayList<E> BFS(E origin) {
        ArrayList<E> ans = new ArrayList<>();
        Queue<E> queue = new LinkedList<>();
        TreeSet<E> processeds = new TreeSet<>();
        queue.offer(origin);
        while (!queue.isEmpty()) {
            E next = queue.poll();
            if (processeds.add(next)) {
                ans.add(next);
                queue.addAll(adjacents(next));
            }
        }
        return ans;
    }

    public void addVertex(E vertex) {
        if (!graph.containsKey(vertex)) {
            graph.put(vertex, new ArrayList<>());
        }
    }

    public void removeVertex(E vertex) {
        if (graph.containsKey(vertex)) {
            boolean isDirectedAux = isDirected();
            isDirected = false;
            for (E vertexAux : getVertices()) {
                if (thereIsAnEdge(vertex, vertexAux)) {
                    removeEdge(vertex, vertexAux);
                }
            }
            isDirected = isDirectedAux;
            graph.remove(vertex);
        }
    }

    public void add(E origin, E destination, float weight, boolean isDirected) {
        if (isDirected) {
            createEdge(origin, destination, weight);
            addVertex(destination);
            if (!this.isDirected) {
                this.isDirected = true;
            }
        } else {
            createEdge(origin, destination, weight);
            createEdge(destination, origin, weight);
        }
        edges.add(new Vector2D<>(origin, destination));
    }

    public void add(E origin, E destination, float weight) {
        add(origin, destination, weight, false);
    }

    public void add(E origin, E destination) {
        add(origin, destination, 1, false);
    }

    private void createEdge(E origin, E destination, float weight) {
        if (!graph.containsKey(origin)) {
            graph.put(origin, new ArrayList<>());
        }
        graph.get(origin).add(new EdgeVertex<>(destination, weight));
    }

    public void removeEdge(E vertex, E vertex1) {
        if (thereIsAnEdge(vertex, vertex1)) {
            if (isDirected()) {
                removeSimpleEdge(vertex, vertex1);
            } else {
                removeSimpleEdge(vertex, vertex1);
                removeSimpleEdge(vertex1, vertex);
            }
        }
    }

    private void removeSimpleEdge(E vertex, E vertex1) {
        if (contains(graph.get(vertex), vertex1)) {
            graph.get(vertex).remove(indexOf(graph.get(vertex), vertex1));
        }
        if (!contains(graph.get(vertex1), vertex)) {
            for (Vector2D<E, E> edge : edges) {
                if ((edge.getX().equals(vertex) && edge.getY().equals(vertex1)) || (edge.getY().equals(vertex) && edge.getX().equals(vertex1))) {
                    edges.remove(edge);
                    break;
                }
            }
        }
    }

    /**
     * Devuelve las matrices de distancias y de recorridos respectivamente,
     * siguiendo el orden del ArrayList de vertices que se le pase como
     * parametro.
     *
     * @param vertices - Los vertices del grafo ordenados segun se requiera
     * @return - Un objeto de tipo Vector2D que contiene las matrices de
     * Distancias y de Recorridos
     */
    public Vector2D<float[][], ArrayList<ArrayList<E>>> floydWarshall(ArrayList<E> vertices) {
        float[][] distanceMatrix = new float[graph.keySet().size()][graph.keySet().size()];
        ArrayList<ArrayList<E>> routeMatrix = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (contains(graph.get(vertices.get(i)), vertices.get(j))) {
                    if (vertices.get(i).equals(vertices.get(j))) {
                        distanceMatrix[i][j] = 0;
                    } else {
                        distanceMatrix[i][j] = graph.get(vertices.get(i)).get(indexOf(graph.get(vertices.get(i)), vertices.get(j))).getWeight();
                    }
                } else {
                    if (vertices.get(i).equals(vertices.get(j))) {
                        distanceMatrix[i][j] = 0;
                    } else {
                        distanceMatrix[i][j] = Float.POSITIVE_INFINITY;
                    }
                }
            }
        }
        for (int i = 0; i < vertices.size(); i++) {
            routeMatrix.add((ArrayList<E>) vertices.clone());
        }
        for (int j1i2 = 0; j1i2 < vertices.size(); j1i2++) {
            for (int i1 = 0; i1 < vertices.size(); i1++) {
                for (int j2 = 0; j2 < vertices.size(); j2++) {
                    if (distanceMatrix[i1][j1i2] < Float.POSITIVE_INFINITY && distanceMatrix[j1i2][j2] < Float.POSITIVE_INFINITY && i1 != j2 && distanceMatrix[i1][j1i2] + distanceMatrix[j1i2][j2] < distanceMatrix[i1][j2]) {
                        distanceMatrix[i1][j2] = distanceMatrix[i1][j1i2] + distanceMatrix[j1i2][j2];
                        routeMatrix.get(i1).set(j2, vertices.get(j1i2));
                    }
                }
            }
        }
        return new Vector2D(distanceMatrix, routeMatrix);
    }

    public Vector2D<ArrayList<E>, Vector2D<float[][], ArrayList<ArrayList<E>>>> floydWarshall() {
        ArrayList<E> verticesAux = getVertices();
        return new Vector2D<>(verticesAux, floydWarshall(verticesAux));
    }

    private boolean contains(ArrayList<EdgeVertex<E>> arr, E vertex) {
        boolean ans = false;
        for (EdgeVertex<E> edgeVertex : arr) {
            if (edgeVertex.getVertex().equals(vertex)) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    private int indexOf(ArrayList<EdgeVertex<E>> arr, E vertex) {
        int ans = -1;
        if (contains(arr, vertex)) {
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).getVertex().equals(vertex)) {
                    ans = i;
                    break;
                }
            }
        }
        return ans;
    }

    public Vector2D<Float, ArrayList<E>> bestRoute(E vertex, E vertex1) {
        ArrayList<E> ans = new ArrayList<>();
        ArrayList<E> ans1 = new ArrayList<>();
        Float ans2 = Float.POSITIVE_INFINITY;
        ArrayList<E> verticesAux = getVertices();
        Vector2D<ArrayList<E>, Vector2D<float[][], ArrayList<ArrayList<E>>>> floydWarshallMatrices = floydWarshall();
        if (floydWarshallMatrices.getX().indexOf(vertex) >= 0 && floydWarshallMatrices.getX().indexOf(vertex1) >= 0 && floydWarshallMatrices.getY().getX()[floydWarshallMatrices.getX().indexOf(vertex)][floydWarshallMatrices.getX().indexOf(vertex1)] < Float.POSITIVE_INFINITY) {
            if (verticesAux.contains(vertex) && verticesAux.contains(vertex1)) {
                bestRoute(vertex, vertex1, floydWarshallMatrices, ans);
                ans2 = floydWarshall().getY().getX()[floydWarshallMatrices.getX().indexOf(vertex)][floydWarshallMatrices.getX().indexOf(vertex1)];
            }
            for (int i = ans.size() - 1; i >= 0; i--) {
                ans1.add(ans.get(i));
            }
        }
        return new Vector2D<>(ans2, ans1);
    }

    private void bestRoute(E vertex, E vertex1, Vector2D<ArrayList<E>, Vector2D<float[][], ArrayList<ArrayList<E>>>> floydWarshallMatrices, ArrayList<E> ans) {
        ans.add(vertex1);
        if (floydWarshallMatrices.getY().getY().get(floydWarshallMatrices.getX().indexOf(vertex)).get(floydWarshallMatrices.getX().indexOf(vertex1)).equals(vertex1)) {
            ans.add(vertex);
        } else {
            bestRoute(vertex, floydWarshallMatrices.getY().getY().get(floydWarshallMatrices.getX().indexOf(vertex)).get(floydWarshallMatrices.getX().indexOf(vertex1)), floydWarshallMatrices, ans);
        }
    }

    /**
     * Devuelve la matriz de adyacencia del grafo siguiendo el orden del
     * ArrayList de vertices que se le pase como parametro.
     *
     * @param vertices - Los vertices del grafo ordenados segun se requiera
     * @return - La matriz de Adyacencia del grafo
     */
    public float[][] adjacenceMatrix(ArrayList<E> vertices) {
        float[][] ans = new float[getOrder()][getOrder()];
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                ans[i][j] = 0;
            }
        }
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (graph.containsKey(vertices.get(i)) && graph.containsKey(vertices.get(j))) {
                    for (EdgeVertex<E> edgeVertex : graph.get(vertices.get(i))) {
                        if (edgeVertex.getVertex().equals(vertices.get(j))) {
                            ans[i][j] = edgeVertex.getWeight();
                        }
                    }
                }
            }
        }
        return ans;
    }

    public Vector2D<ArrayList<E>, float[][]> adjacenceMatrix() {
        ArrayList<E> verticesAux = getVertices();
        return new Vector2D<>(verticesAux, adjacenceMatrix(verticesAux));
    }

    /**
     * Devuelve la matriz de incidencia del grafo siguiendo el orden de los
     * ArrayLists de vertices y de aristas que se le pase como parametro.
     *
     * @param vertices - Los vertices del grafo ordenados segun se requiera
     * @param edges - Las aristas del grafo ordenados segun se requiera
     * @return - La matriz de incidencia del grafo
     */
    public int[][] incidenceMatrix(ArrayList<E> vertices, ArrayList<Vector2D<E, E>> edges) {
        int[][] ans = new int[getOrder()][getSize()];
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                ans[i][j] = 0;
            }
        }
        for (E vertex : vertices) {
            for (Vector2D<E, E> edge : edges) {
                if (edge.getX().equals(vertex) || edge.getY().equals(vertex)) {
                    ans[vertices.indexOf(vertex)][edges.indexOf(edge)] = 1;
                }
            }
        }
        return ans;
    }

    public Vector2D<Vector2D<ArrayList<E>, ArrayList<Vector2D<E, E>>>, int[][]> incidenceMatrix() {
        ArrayList<E> verticesAux = getVertices();
        ArrayList<Vector2D<E, E>> edgesAux = getEdges();
        return new Vector2D<>(new Vector2D<>(verticesAux, edgesAux), incidenceMatrix(verticesAux, edgesAux));
    }

    public Graph completeGraph() {
        Graph ans = new Graph();
        ArrayList<E> vertices = getVertices();
        for (E vertex : vertices) {
            for (E vertex1 : vertices) {
                if (!vertex1.equals(vertex)) {
                    ans.add(vertex, vertex1);
                }
            }
        }
        return ans;
    }

    public Graph complement() {
        Graph ans = new Graph();
        ArrayList<E> vertices = getVertices();
        for (E vertex : vertices) {
            for (E vertex1 : vertices) {
                if (!vertex.equals(vertex1) && !thereIsAnEdge(vertex, vertex1) && !ans.thereIsAnEdge(vertex, vertex1)) {
                    ans.add(vertex, vertex1);
                }
            }
        }
        for (E vertex : vertices) {
            ans.addVertex(vertex);
        }
        return ans;
    }

    public boolean thereIsAnEdge(E vertex, E vertex1) {
        boolean ans = false;
        for (Vector2D<E, E> edge : getEdges()) {
            if ((edge.getX().equals(vertex) && edge.getY().equals(vertex1)) || (edge.getX().equals(vertex1) && edge.getY().equals(vertex))) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    @Override
    public String toString() {
        String ans = "V:{";
        ArrayList<E> vertices = getVertices();
        for (E vertex : vertices) {
            if (vertices.indexOf(vertex) == vertices.size() - 1) {
                ans += vertex + "}\nE:{";
            } else {
                ans += vertex + ", ";
            }
        }
        for (Vector2D<E, E> edge : getEdges()) {
            if (getEdges().indexOf(edge) == getEdges().size() - 1) {
                ans += "(" + edge.getX() + ", " + edge.getY() + ")}";
            } else {
                ans += "(" + edge.getX() + ", " + edge.getY() + "), ";
            }
        }
        return ans;
    }
}

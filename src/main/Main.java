package main;

import graph.Graph;
import graph.utilities.Vector2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void test() {
        Graph<Character> grafo = new Graph<>();
        grafo.add('A', 'B', -12, true);
        grafo.add('A', 'D', 87, true);
        grafo.add('D', 'B', 23, true);
        grafo.add('C', 'A', 19, true);
        grafo.add('D', 'C', -10, true);
        grafo.add('B', 'E', 11, true);
        grafo.add('E', 'D', 43, true);

        /*grafo.add(0, 1);
        grafo.add(0, 2);
        grafo.add(2, 1);
        grafo.add(3, 1);
        grafo.add(2, 3);
        grafo.add(4, 3);*/
        Vector2D<float[][], ArrayList<ArrayList<Character>>> floydWarshall = grafo.floydWarshall(grafo.getVertices());
        for (int i = 0; i < floydWarshall.getX().length; i++) {
            for (int j = 0; j < floydWarshall.getX()[i].length; j++) {
                System.out.print(floydWarshall.getX()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < floydWarshall.getY().size(); i++) {
            for (int j = 0; j < floydWarshall.getY().get(i).size(); j++) {
                System.out.print(floydWarshall.getY().get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(grafo.bestRoute('A', 'D').getX());
        ArrayList<Character> ruta = grafo.bestRoute('A', 'D').getY();
        for (Character c : ruta) {
            System.out.println(c + " ");
        }
        System.out.println();
        grafo.removeVertex('C');
        System.out.println(grafo.toString());
    }

    public static void main(String[] args) {
        Graph<Place> graphLand = new Graph<>();
        Graph<Place> graphAir = new Graph<>();
        //Graph<Place> graphSea = new Graph<>();

        Place bolivia = new Place("Bolivia", 3, 125);
        Place brasil = new Place("Brasil", 5, 175);
        Place peru = new Place("Peru", 3, 150);
        Place argentina = new Place("Argentina", 7, 200);
        Place espania = new Place("Espania", 5, 125);
        Place estadosUnidos = new Place("Estados Unidos", 5, 385);
        Place mexico = new Place("Mexico", 5, 225);
        Place canada = new Place("Canada", 10, 350);
        Place francia = new Place("Francia", 14, 380);
        Place italia = new Place("Italia", 7, 250);

        HashMap<String, Place> places = new HashMap<>();
        places.put("Bolivia", bolivia);
        places.put("Brasil", brasil);
        places.put("Peru", peru);
        places.put("Argentina", argentina);
        places.put("Espania", espania);
        places.put("Estados Unidos", estadosUnidos);
        places.put("Mexico", mexico);
        places.put("Canada", canada);
        places.put("Francia", francia);
        places.put("Italia", italia);

        graphLand.add(bolivia, brasil, 22);
        graphLand.add(bolivia, peru, 15);
        graphLand.add(bolivia, argentina, 20);
        graphLand.add(argentina, brasil, 12);
        graphLand.add(italia, francia, 10);
        graphLand.add(espania, francia, 15);
        graphLand.add(canada, estadosUnidos, 20);
        graphLand.add(estadosUnidos, mexico, 24);

        graphAir.add(bolivia, brasil, 2);
        graphAir.add(bolivia, peru, 2);
        graphAir.add(bolivia, italia, 15);
        graphAir.add(bolivia, francia, 13);
        graphAir.add(bolivia, canada, 10);
        graphAir.add(bolivia, mexico, 7);
        graphAir.add(bolivia, estadosUnidos, 9);
        graphAir.add(bolivia, espania, 12);
        graphAir.add(bolivia, argentina, 3);
        graphAir.add(brasil, peru, 4);
        graphAir.add(brasil, italia, 13);
        graphAir.add(brasil, francia, 17);
        graphAir.add(brasil, canada, 11);
        graphAir.add(brasil, mexico, 10);
        graphAir.add(brasil, estadosUnidos, 10);
        graphAir.add(brasil, espania, 11);
        graphAir.add(brasil, argentina, 4);
        graphAir.add(peru, italia, 13);
        graphAir.add(peru, francia, 13);
        graphAir.add(peru, canada, 8);
        graphAir.add(peru, mexico, 6);
        graphAir.add(peru, estadosUnidos, 8);
        graphAir.add(peru, espania, 12);
        graphAir.add(peru, argentina, 4);
        graphAir.add(italia, francia, 1);
        graphAir.add(italia, canada, 9);
        graphAir.add(italia, mexico, 15);
        graphAir.add(italia, estadosUnidos, 12);
        graphAir.add(italia, espania, 2);
        graphAir.add(italia, argentina, 17);
        graphAir.add(francia, canada, 7);
        graphAir.add(francia, mexico, 11);
        graphAir.add(francia, estadosUnidos, 11);
        graphAir.add(francia, espania, 2);
        graphAir.add(francia, argentina, 16);
        graphAir.add(canada, mexico, 5);
        graphAir.add(canada, estadosUnidos, 3);
        graphAir.add(canada, espania, 12);
        graphAir.add(canada, argentina, 11);
        graphAir.add(mexico, estadosUnidos, 2);
        graphAir.add(mexico, espania, 11);
        graphAir.add(mexico, argentina, 9);
        graphAir.add(estadosUnidos, espania, 10);
        graphAir.add(estadosUnidos, argentina, 13);
        graphAir.add(espania, argentina, 12);

        Scanner sc = new Scanner(System.in);
        ArrayList<Vector2D<Place, Integer>> placesDestination = new ArrayList<>();
        System.out.println("Introduce el lugar de origen:");
        String input = sc.nextLine();
        Place origin = null;
        if (places.containsKey(input)) {
            origin = places.get(input);
        } else {
            System.out.println("No existe el lugar especificado");
            System.exit(0);
        }
        System.out.println("Ingrese su presupuesto en $us:");
        float budget = sc.nextFloat();
        System.out.println("Ingrese L si su viaje sera por tierra o A si su viaje sera por aire: ");
        String typeTrip = sc.nextLine();
        typeTrip = sc.nextLine();
        while (!input.equals("")) {
            System.out.println("Introduce los lugares a visitar en formato: {[LUGAR A VISITAR], [DIAS DE ESTADIA]}, coloque un lugar a la vez");
            input = sc.nextLine();
            String[] data = input.split(", ");
            if (places.containsKey(data[0])) {
                if (places.get(data[0]).getMinDaysOfStay() <= Integer.parseInt(data[1])) {
                    placesDestination.add(new Vector2D<>(places.get(data[0]), Integer.parseInt(data[1])));
                } else {
                    System.out.println("La minima cantidad de dias para quedarse en " + places.get(data[0]).getName() + " es de " + places.get(data[0]).getMinDaysOfStay() + " dias");
                    System.exit(0);
                }
            } else if (!input.equals("")) {
                System.out.println("No existe el lugar especificado");
                System.exit(0);
            }
        }
        if (typeTrip.equals("L") || typeTrip.equals("l")) {
            VacationPlan vp = new VacationPlan(placesDestination, origin, budget, graphLand, 'L');
            vp.printReport();
        } else if (typeTrip.equals("A") || typeTrip.equals("a")) {
            VacationPlan vp = new VacationPlan(placesDestination, origin, budget, graphAir, 'A');
            vp.printReport();
        } else {
            System.out.println("Tipo de viaje no existente");
            System.exit(0);
        }
    }
}
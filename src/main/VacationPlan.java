package main;

import graph.Graph;
import graph.utilities.Vector2D;
import java.util.ArrayList;

public class VacationPlan {

    private ArrayList<Vector2D<Place, Integer>> placesDestination;
    private Vector2D<Place, Integer> origin;
    private float budget;
    private char typeTrip;
    private Graph<Place> graph;

    private float budgetMin;
    private ArrayList<Vector2D<Float, ArrayList<Place>>> timeInHoursAndRoutes;

    private static final int TRANSPORT_COST_PER_HOUR_LAND = 3;
    private static final int TRANSPORT_COST_PER_HOUR_AIR = 250;
    //private static final int TRANSPORT_COST_PER_DAY_SEA = 200;

    public VacationPlan(ArrayList<Vector2D<Place, Integer>> placesDestination, Place origin, float budget, Graph<Place> graph, char typeTrip) {
        this.placesDestination = placesDestination;
        this.origin = new Vector2D<>(origin, 0);
        this.budget = budget;
        this.typeTrip = typeTrip;
        this.graph = graph;

        this.budgetMin = 0;
        this.timeInHoursAndRoutes = new ArrayList<>();

        Vector2D<Place, Integer> placeAux = this.origin;
        for (Vector2D<Place, Integer> place : placesDestination) {
            this.timeInHoursAndRoutes.add(graph.bestRoute(placeAux.getX(), place.getX()));
            placeAux = place;
        }
        if (typeTrip == 'L') {
            for (int i = 0; i < timeInHoursAndRoutes.size(); i++) {
                this.budgetMin += this.timeInHoursAndRoutes.get(i).getX() * TRANSPORT_COST_PER_HOUR_LAND;
                this.budgetMin += this.placesDestination.get(i).getY() * this.placesDestination.get(i).getX().getCostPerDay();
            }
        } else if (typeTrip == 'A') {
            for (int i = 0; i < this.timeInHoursAndRoutes.size(); i++) {
                this.budgetMin += this.timeInHoursAndRoutes.get(i).getX() * TRANSPORT_COST_PER_HOUR_AIR;
                this.budgetMin += this.placesDestination.get(i).getY() * this.placesDestination.get(i).getX().getCostPerDay();
            }
        }
        if (typeTrip == 'L' && budget < budgetMin) {
            this.budgetMin = 0;
            for (int i = 0; i < this.timeInHoursAndRoutes.size(); i++) {
                this.budgetMin += this.timeInHoursAndRoutes.get(i).getX() * TRANSPORT_COST_PER_HOUR_LAND;
                this.budgetMin += this.placesDestination.get(i).getX().getMinDaysOfStay() * this.placesDestination.get(i).getX().getCostPerDay();
            }
        } else if (typeTrip == 'A' && budget < budgetMin) {
            this.budgetMin = 0;
            for (int i = 0; i < this.timeInHoursAndRoutes.size(); i++) {
                this.budgetMin += this.timeInHoursAndRoutes.get(i).getX() * TRANSPORT_COST_PER_HOUR_AIR;
                this.budgetMin += this.placesDestination.get(i).getX().getMinDaysOfStay() * this.placesDestination.get(i).getX().getCostPerDay();
            }
        }
    }

    public ArrayList<Vector2D<Place, Integer>> getPlacesDestination() {
        return placesDestination;
    }

    public void setPlacesDestination(ArrayList<Vector2D<Place, Integer>> placesDestination) {
        this.placesDestination = placesDestination;
    }

    public Vector2D<Place, Integer> getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2D<Place, Integer> origin) {
        this.origin = origin;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getBudgetMin() {
        return budgetMin;
    }

    public void setBudgetMin(float budgetMin) {
        this.budgetMin = budgetMin;
    }

    public ArrayList<Vector2D<Float, ArrayList<Place>>> getTimeInHoursAndRoutes() {
        return timeInHoursAndRoutes;
    }

    public void setTimeInHoursAndRoutes(ArrayList<Vector2D<Float, ArrayList<Place>>> timeInHoursAndRoutes) {
        this.timeInHoursAndRoutes = timeInHoursAndRoutes;
    }

    public void printReport() {
        if (budget < budgetMin) {
            System.out.println("Su presupuesto es muy bajo pero el plan mas basico por todas las ciudades que desea visitar es:");
            for (int i = 0; i < placesDestination.size(); i++) {
                String line = "";
                line += placesDestination.get(i).getX().getName() + ":";
                for (Place place : timeInHoursAndRoutes.get(i).getY()) {
                    line += " " + place.getName() + " -";
                }
                System.out.println(line + "> " + placesDestination.get(i).getX().getMinDaysOfStay() + " Dias");
            }
            System.out.println("El presupuesto para este viaje es de: " + budgetMin);
        } else {
            System.out.println("Su presupuesto es suficiente para este vaje, le sobrará " + (budget - budgetMin) + "$us");
            for (int i = 0; i < placesDestination.size(); i++) {
                String line = "";
                line += placesDestination.get(i).getX().getName() + ":";
                for (Place place : timeInHoursAndRoutes.get(i).getY()) {
                    line += " " + place.getName() + " -";
                }
                System.out.println(line + "> " + placesDestination.get(i).getY() + " Dias");
            }
            if (this.typeTrip == 'L') {
                System.out.println("El presupuesto para este viaje es de: " + (budgetMin + this.graph.bestRoute(origin.getX(), this.placesDestination.get(this.placesDestination.size() - 1).getX()).getX() * TRANSPORT_COST_PER_HOUR_LAND));
            } else if (this.typeTrip == 'A') {
                System.out.println("El presupuesto para este viaje es de: " + (budgetMin + this.graph.bestRoute(origin.getX(), this.placesDestination.get(this.placesDestination.size() - 1).getX()).getX() * TRANSPORT_COST_PER_HOUR_AIR));
            }
        }
    }
}

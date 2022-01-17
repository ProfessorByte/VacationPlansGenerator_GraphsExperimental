package main;

public class Place {

    private String name;
    private int minDaysOfStay;
    private float costPerDay;

    public Place(String name, int minDaysOfStay, float costPerDay) {
        this.name = name;
        this.minDaysOfStay = minDaysOfStay;
        this.costPerDay = costPerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinDaysOfStay() {
        return minDaysOfStay;
    }

    public void setMinDaysOfStay(int minDaysOfStay) {
        this.minDaysOfStay = minDaysOfStay;
    }

    public float getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(float costPerDay) {
        this.costPerDay = costPerDay;
    }
}

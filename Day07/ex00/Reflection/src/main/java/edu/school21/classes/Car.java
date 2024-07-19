package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
    private String brand;
    private String name;
    private Long costInDollars;
    private boolean isCarDriving;
    private Double mileage;

    public Car() {
        this.brand = "BMW";
        this.name = "X3";
        this.costInDollars = 46900L;
        this.isCarDriving = false;
        this.mileage = 10.5;
    }

    public Car(String brand, String name, Long costInDollars, Boolean isCarDriving, Double mileage) {
        this.brand = brand;
        this.name = name;
        this.costInDollars = costInDollars;
        this.isCarDriving = isCarDriving;
        this.mileage = mileage;
    }

    public Long makeDiscount(Integer valueInPercentage) {
        this.costInDollars *= 100 - valueInPercentage;
        this.costInDollars /= 100;
        return this.costInDollars;
    }

    public void driveCar() {
        this.isCarDriving = true;
    }

    public void stopDrivingCar(Double passedPathValue, String comment) {
        this.isCarDriving = false;
        this.mileage += passedPathValue;
        System.out.println("Your comment: " + comment);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("brand='" + brand + "'")
                .add("name='" + name + "'")
                .add("costInDollars=" + costInDollars)
                .add("isCarDriving=" + isCarDriving)
                .add("mileage=" + mileage)
                .toString();
    }
}

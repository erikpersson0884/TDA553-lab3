package Vehicles.Trucks;

import java.awt.Color;
import java.util.Stack;

import CustomExceptions.*;
import Ramps.TransporterRamp;
import Utility.CarStorage;
import Vehicles.Cars.ICar;

public class Transporter implements ITruck {
    private Truck truck;
    private CarStorage carStorage;


    public Transporter(double enginePower, Color color, int nrDoors, double x, double y) {
        truck = new Truck("Transporter", enginePower, color, nrDoors, x, y, new TransporterRamp());
        carStorage = new CarStorage(7, new Stack<ICar>(), 30);
    }

    // ------------ delegated methods -------------
    @Override
    public double getCurrentSpeed() {
        return truck.getCurrentSpeed();
    }

    @Override
    public double getEnginePower() {
        return truck.getEnginePower();
    }

    @Override
    public double getX() {
        return truck.getX();
    }

    @Override
    public double getY() {
        return truck.getY();
    }

    public boolean getRampIsInDrivingPosition() {
        return truck.getRampIsInDrivingPosition();
    }

    @Override
    public void move() {
        truck.move();
    }

    @Override
    public void turnLeft() {
        truck.turnLeft();
    }

    @Override
    public void turnRight() {
        truck.turnRight();
    }

    @Override
    public void brake(double amount) {
        truck.brake(amount);
    }

    // public void raiseRamp() {
    //     truck.raiseRampToMax();
    // }

    // public void lowerRamp() {
    //     truck.lowerRampToMin();
    // }

    @Override
    public void raiseRampToMax() {
        truck.raiseRampToMax();
    }

    @Override
    public void lowerRampToMin() {
        truck.lowerRampToMin();
    }
    // ----------- methods with own implementation ---------
    
    @Override
    public void gas(double amount) {
        truck.gas(amount);
    }

    // -------- Methods for loading cars ----------

    public void loadCar(ICar carToBeLoaded) {
        if (getRampIsInDrivingPosition()) {
            throw new RampIsNotInCorrectPositionException("The transporters' ramp is not down");
        } else {
            carStorage.loadCar(carToBeLoaded, truck);
        }
    }

    public void unLoadCar(ICar carToBeUnloaded) {
        if (getRampIsInDrivingPosition()) {
            throw new RampIsNotInCorrectPositionException("The transporters' ramp is not down");
        } else {
            carStorage.unLoadCar(carToBeUnloaded, truck);
        }

    }
}
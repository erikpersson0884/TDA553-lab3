package Trucks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CustomExceptions.*;
import Vehicles.Cars.ICar;
import Vehicles.Cars.Saab95;
import Vehicles.Cars.Volvo240;
import Vehicles.Trucks.Transporter;

public class TransporterTest {
    private Transporter Transporter;

    @Before
    public void createTestTransporter() {
        Transporter = new Transporter(500, Color.BLACK, 2, 0, 0);
    }

    @After
    public void removeTestTransporter() {
        // Garbage collector deletes
        Transporter = null;
    }

    // Tests to do, gas, brake, currentSpeed, move, direction, turn left, turn right
    @Test
    public void gas_cannot_result_in_the_speed_decreasing() {
        double previousSpeed = Transporter.getCurrentSpeed();
        Transporter.gas(1);
        assertFalse(previousSpeed > Transporter.getCurrentSpeed());
    }

    @Test
    public void gas_cannot_take_in_value_below_zero() {
        assertThrows(IllegalArgumentException.class, () -> {
            Transporter.gas(-1);
        });
    }

    @Test
    public void brake_cannot_result_in_the_speed_increasing() {
        Transporter.gas(0.5);
        double previousSpeed = Transporter.getCurrentSpeed();
        Transporter.brake(0.1);

        assertFalse(previousSpeed < Transporter.getCurrentSpeed());
    }

    @Test
    public void brake_cannot_take_in_value_above_one() {
        assertThrows(IllegalArgumentException.class, () -> {
            Transporter.brake(2);
        });
    }

    @Test
    public void car_speed_cannot_be_higher_than_enginePower() {
        for (int i = 0; i < 100; i++) {
            Transporter.gas(1);
        }

        assertFalse(Transporter.getEnginePower() < Transporter.getCurrentSpeed());
    }

    @Test
    public void car_speed_cannot_be_lower_than_0() {
        for (int i = 0; i < 100; i++) {
            Transporter.brake(1);
        }

        assertFalse(Transporter.getCurrentSpeed() < 0);
    }

    @Test
    public void moving_and_turning_the_car_to_its_original_position_should_result_in_same_x_position() {
        double prevX = Transporter.getX();

        Transporter.gas(0.5);

        Transporter.turnRight();

        Transporter.move();

        for (int i = 0; i < 4; i++) {
            Transporter.turnLeft();
        }

        Transporter.move();
    
        assertEquals(prevX, Transporter.getX(), 0.01);
    }

    @Test
    public void moving_and_turning_the_car_to_its_original_position_should_result_in_same_y_position() {
        double prevY = Transporter.getY();

        Transporter.gas(0.5);

        Transporter.turnRight();

        Transporter.move();

        for (int i = 0; i < 4; i++) {
            Transporter.turnLeft();
        }

        Transporter.move();
    
        assertEquals(prevY, Transporter.getY(), 0.01);
    }

    @Test
    public void ramp_raises_when_raised() {
        Transporter.raiseRamp();
        assertTrue(Transporter.getRampIsInDrivingPosition());
    }

    @Test
    public void ramp_lowers_when_lowered() {
        Transporter.lowerRamp();
        assertFalse(Transporter.getRampIsInDrivingPosition());
    }

    @Test
    public void loading_car_to_transporter_when_it_is_full_should_not_add_the_car() {
        ICar[] myCars = { new Volvo240(0, null, 0, 0), new Volvo240(0, null, 0, 0), new Saab95(0, null, 0, 0),
                new Volvo240(0, null, 0, 0),
                new Volvo240(0, null, 0, 0), new Volvo240(0, null, 0, 0), new Volvo240(0, null, 0, 0),
                new Volvo240(0, null, 0, 0) };

        Transporter.lowerRamp();
        
        assertThrows(CarStorageFullException.class, () -> {
            for (ICar car : myCars) {
                Transporter.loadCar(car);
            }
        });
    }

    @Test
    public void cant_load_same_car_twice_in_the_transporter() {
        ICar myVolvo = new Volvo240(0, null, 0, 0);
        Transporter.lowerRamp();
        assertThrows(CarIsAlreadyLoadedException.class, () -> {
            Transporter.loadCar(myVolvo);
            Transporter.loadCar(myVolvo);
        });
    }

    @Test
    public void car_get_new_coordinates_when_unloaded() {
        double prevX = 0;
        double prevY = 0;
        ICar myVolvo = new Volvo240(0, null, prevX, prevY);
        
        Transporter.lowerRamp();
        Transporter.loadCar(myVolvo);
        Transporter.raiseRamp();
        Transporter.gas(1);
        Transporter.move();

        while (Transporter.getCurrentSpeed() > 0) {
            Transporter.brake(1);
        }

        Transporter.lowerRamp();
        Transporter.unLoadCar(myVolvo);

        assertFalse(prevX == myVolvo.getX());
    }

}

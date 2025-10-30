class Vehicle {
    protected String registrationNumber;
    protected int capacity;

    public Vehicle(String registrationNumber, int capacity) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
    }

    public void displayInfo() {
        System.out.println("Registration: " + registrationNumber +
                ", Capacity: " + capacity + " people");
    }
}

class Bus extends Vehicle {
    private String routeNumber;

    public Bus(String registrationNumber, int capacity, String routeNumber) {
        super(registrationNumber, capacity);
        this.routeNumber = routeNumber;
    }

    @Override
    public void displayInfo() {
        System.out.println("BUS - Registration: " + registrationNumber +
                ", Capacity: " + capacity +
                ", Route: " + routeNumber);
    }
}

class Car extends Vehicle {
    private String model;

    public Car(String registrationNumber, int capacity, String model) {
        super(registrationNumber, capacity);
        this.model = model;
    }

    @Override
    public void displayInfo() {
        System.out.println("CAR - Registration: " + registrationNumber +
                ", Capacity: " + capacity +
                ", Model: " + model);
    }
}

class Motorcycle extends Vehicle {
    private int engineCapacity; // in cc

    public Motorcycle(String registrationNumber, int capacity, int engineCapacity) {
        super(registrationNumber, capacity);
        this.engineCapacity = engineCapacity;
    }

    @Override
    public void displayInfo() {
        System.out.println("MOTORCYCLE - Registration: " + registrationNumber +
                ", Capacity: " + capacity +
                ", Engine: " + engineCapacity + "cc");
    }
}

public class TransportSystem {
    public static void main(String[] args) {
        // Create different vehicles
        Vehicle[] vehicles = {
                new Bus("BUS001", 50, "Route 101"),
                new Car("CAR001", 5, "Toyota Camry"),
                new Motorcycle("MOTO001", 2, 150),
                new Bus("BUS002", 40, "Route 202")
        };

        System.out.println("=== Vehicle Registration System ===");

        // Demonstrate polymorphism
        for (Vehicle vehicle : vehicles) {
            vehicle.displayInfo();
        }
    }
}

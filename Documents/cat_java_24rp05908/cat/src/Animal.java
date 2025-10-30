class Animal {
    public void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow! Meow!");
    }
}

class Cow extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Moo! Moo!");
    }
}

class VeterinarySystem {
    public static void main(String[] args) {

        Animal[] animals = {
                new Dog(),
                new Cat(),
                new Cow(),
                new Dog()
        };

        System.out.println("=== Animal Sounds ===");
        // Polymorphism in action
        for (Animal animal : animals) {
            animal.makeSound();
        }
    }
}

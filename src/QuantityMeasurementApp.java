import java.util.Objects;

public class QuantityMeasurementApp {

    // Inner class to represent Feet measurement
    public static class Feet {
        // Encapsulating the measurement value as a private final field
        private final double value;

        // Constructor to initialize the Feet measurement
        public Feet(double value) {
            this.value = value;
        }

        // Getter method to access the value (if needed)
        public double getValue() {
            return value;
        }

        // Overriding the equals() method from Object class
        @Override
        public boolean equals(Object obj) {
            // Check if the current object and the passed object are the same reference
            if (this == obj) return true;

            // Check if the object is null or not of the same type
            if (obj == null || getClass() != obj.getClass()) return false;

            // Cast the object to Feet type
            Feet otherFeet = (Feet) obj;

            // Compare the values using Double.compare for precision
            return Double.compare(this.value, otherFeet.value) == 0;
        }

        // Optionally, override hashCode() to maintain the contract with equals()
        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    // Main method to test the equality comparison
    public static void main(String[] args) {
        // Test cases to validate the equality functionality
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);
        Feet feet3 = new Feet(2.0);
        Feet feet4 = new Feet(1.0);
        Feet feet5 = null;

        // Test for equality of same values
        System.out.println("Test 1: " + feet1.equals(feet2)); // Should print true

        // Test for inequality of different values
        System.out.println("Test 2: " + feet1.equals(feet3)); // Should print false

        // Test for null comparison
        System.out.println("Test 3: " + feet1.equals(feet5)); // Should print false

        // Test for non-null comparison with same value
        System.out.println("Test 4: " + feet1.equals(feet4)); // Should print true

        // Test for reflexive property (a == a)
        System.out.println("Test 5: " + feet1.equals(feet1)); // Should print true
    }
}
import java.util.Objects;

public class QuantityMeasurementApp {

    // Enum for the unit type
    public enum Unit {
        FEET, INCHES
    }

    // Generic Quantity class to represent measurements in different units
    public static class Quantity {
        private final double value;
        private final Unit unit;

        // Constructor to initialize the value and the unit (feet or inches)
        public Quantity(double value, Unit unit) {
            this.value = value;
            this.unit = unit;
        }

        // Getter for the value
        public double getValue() {
            return value;
        }

        // Getter for the unit
        public Unit getUnit() {
            return unit;
        }

        // Convert the quantity to inches for comparison
        public double toInches() {
            if (unit == Unit.FEET) {
                return value * 12; // 1 foot = 12 inches
            }
            return value; // If already in inches, return the value as is
        }

        // Overriding the equals() method to compare quantities
        @Override
        public boolean equals(Object obj) {
            // Check if the current object and the passed object are the same reference
            if (this == obj) return true;

            // Check if the object is null or not of the same type
            if (obj == null || getClass() != obj.getClass()) return false;

            // Cast the object to Quantity type
            Quantity other = (Quantity) obj;

            // Compare the values after converting both to inches
            return Double.compare(this.toInches(), other.toInches()) == 0;
        }

        // Override hashCode() to be consistent with equals()
        @Override
        public int hashCode() {
            return Objects.hash(value, unit);
        }
    }

    // Method to test equality of two quantities
    public static boolean testEquality(Quantity q1, Quantity q2) {
        return q1.equals(q2);
    }

    public static void main(String[] args) {
        // Test cases to validate the equality functionality

        // Feet measurements
        Quantity feet1 = new Quantity(1.0, Unit.FEET);
        Quantity feet2 = new Quantity(1.0, Unit.FEET);
        Quantity feet3 = new Quantity(2.0, Unit.FEET);

        // Inches measurements
        Quantity inch1 = new Quantity(1.0, Unit.INCHES);
        Quantity inch2 = new Quantity(1.0, Unit.INCHES);
        Quantity inch3 = new Quantity(2.0, Unit.INCHES);

        // Test for equality of same values (Feet)
        System.out.println("Test 1 (Feet): " + testEquality(feet1, feet2)); // Should print true

        // Test for equality of different values (Feet)
        System.out.println("Test 2 (Feet): " + testEquality(feet1, feet3)); // Should print false

        // Test for equality of same values (Inches)
        System.out.println("Test 3 (Inches): " + testEquality(inch1, inch2)); // Should print true

        // Test for equality of different values (Inches)
        System.out.println("Test 4 (Inches): " + testEquality(inch1, inch3)); // Should print false

        // Test for Feet vs Inches equality (1 foot = 12 inches)
        Quantity feetToInches = new Quantity(1.0, Unit.FEET);
        Quantity inchToCompare = new Quantity(12.0, Unit.INCHES);
        System.out.println("Test 5 (Feet to Inches): " + testEquality(feetToInches, inchToCompare)); // Should print true
    }
}
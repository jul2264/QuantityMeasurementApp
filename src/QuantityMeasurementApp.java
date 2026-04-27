import java.util.Objects;

public class QuantityMeasurementApp {

    // Enum for supported length units with their conversion factors to a base unit (inches)
    public enum LengthUnit {
        FEET(12), // 1 foot = 12 inches
        INCHES(1); // 1 inch = 1 inch

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    // Generic QuantityLength class to represent measurements of length with various units
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        // Constructor to initialize the value and unit
        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        // Getter for value
        public double getValue() {
            return value;
        }

        // Getter for unit
        public LengthUnit getUnit() {
            return unit;
        }

        // Convert the quantity to inches for comparison purposes
        public double toBaseUnitInches() {
            return value * unit.getConversionFactor();
        }

        // Overriding the equals() method to compare length quantities
        @Override
        public boolean equals(Object obj) {
            // Check if the current object and the passed object are the same reference
            if (this == obj) return true;

            // Check if the object is null or not of the same type
            if (obj == null || getClass() != obj.getClass()) return false;

            // Cast the object to QuantityLength type
            QuantityLength other = (QuantityLength) obj;

            // Handle the case where the unit is null
            if (this.unit == null || other.unit == null) return false;

            // Compare the values after converting both to inches (base unit)
            return Double.compare(this.toBaseUnitInches(), other.toBaseUnitInches()) == 0;
        }

        // Override hashCode() to be consistent with equals()
        @Override
        public int hashCode() {
            return Objects.hash(value, unit);
        }
    }

    // Method to test equality of two QuantityLength objects
    public static boolean testEquality(QuantityLength q1, QuantityLength q2) {
        return q1.equals(q2);
    }

    public static void main(String[] args) {
        // Test cases to validate the equality functionality

        // Feet measurements
        QuantityLength feet1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength feet2 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength feet3 = new QuantityLength(2.0, LengthUnit.FEET);

        // Inches measurements
        QuantityLength inch1 = new QuantityLength(1.0, LengthUnit.INCHES);
        QuantityLength inch2 = new QuantityLength(1.0, LengthUnit.INCHES);
        QuantityLength inch3 = new QuantityLength(2.0, LengthUnit.INCHES);

        // Test for equality of same values (Feet)
        System.out.println("Test 1 (Feet to Feet): " + testEquality(feet1, feet2)); // Should print true

        // Test for equality of different values (Feet)
        System.out.println("Test 2 (Feet to Feet): " + testEquality(feet1, feet3)); // Should print false

        // Test for equality of same values (Inches)
        System.out.println("Test 3 (Inches to Inches): " + testEquality(inch1, inch2)); // Should print true

        // Test for equality of different values (Inches)
        System.out.println("Test 4 (Inches to Inches): " + testEquality(inch1, inch3)); // Should print false

        // Test for equality of Feet vs Inches (1 foot = 12 inches)
        QuantityLength feetToInches = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inchToCompare = new QuantityLength(12.0, LengthUnit.INCHES);
        System.out.println("Test 5 (Feet to Inches equivalent): " + testEquality(feetToInches, inchToCompare)); // Should print true

        // Test for equality of Inches vs Feet (12 inches = 1 foot)
        QuantityLength inchToFeet = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength feetEquivalent = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println("Test 6 (Inches to Feet equivalent): " + testEquality(inchToFeet, feetEquivalent)); // Should print true

        // Test for invalid unit (Unsupported length unit)
        try {
            QuantityLength invalidUnit = new QuantityLength(10.0, null); // null unit should cause issue
            System.out.println("Test 7 (Invalid unit): " + testEquality(inch1, invalidUnit)); // Should throw an error or handle gracefully
        } catch (NullPointerException e) {
            System.out.println("Test 7 (Invalid unit): Caught exception: " + e.getMessage());
        }
    }
}
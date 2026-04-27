import java.util.Objects;

public class QuantityMeasurementApp {

    // Enum for supported length units with their conversion factors to a base unit (inches)
    public enum LengthUnit {
        FEET(12),       // 1 foot = 12 inches
        INCHES(1),      // 1 inch = 1 inch
        YARDS(36),      // 1 yard = 3 feet = 36 inches
        CENTIMETERS(0.393701); // 1 cm = 0.393701 inches

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

        // Yard measurements
        QuantityLength yard1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength yard2 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength yard3 = new QuantityLength(2.0, LengthUnit.YARDS);

        // Feet measurements
        QuantityLength feet1 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength feet2 = new QuantityLength(3.0, LengthUnit.FEET);

        // Inches measurements
        QuantityLength inch1 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength inch2 = new QuantityLength(36.0, LengthUnit.INCHES);

        // Centimeter measurements
        QuantityLength cm1 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength cm2 = new QuantityLength(0.393701, LengthUnit.INCHES);

        // Test for equality of same values (Yards)
        System.out.println("Test 1 (Yards to Yards): " + testEquality(yard1, yard2)); // Should print true

        // Test for equality of different values (Yards)
        System.out.println("Test 2 (Yards to Yards): " + testEquality(yard1, yard3)); // Should print false

        // Test for equality of Yard to Feet (1 yard = 3 feet)
        System.out.println("Test 3 (Yard to Feet equivalent): " + testEquality(yard1, feet1)); // Should print true

        // Test for equality of Feet to Yard (3 feet = 1 yard)
        System.out.println("Test 4 (Feet to Yard equivalent): " + testEquality(feet1, yard1)); // Should print true

        // Test for equality of Yard to Inches (1 yard = 36 inches)
        System.out.println("Test 5 (Yard to Inches equivalent): " + testEquality(yard1, inch1)); // Should print true

        // Test for equality of Inches to Yard (36 inches = 1 yard)
        System.out.println("Test 6 (Inches to Yard equivalent): " + testEquality(inch1, yard1)); // Should print true

        // Test for equality of Centimeter to Inch (1 cm = 0.393701 inches)
        System.out.println("Test 7 (Centimeter to Inch equivalent): " + testEquality(cm1, cm2)); // Should print true

        // Test for equality of Centimeter to Feet (1 cm != 1 foot)
        System.out.println("Test 8 (Centimeter to Feet non-equivalent): " + testEquality(cm1, feet1)); // Should print false

        // Test for equality of same unit with same value (Centimeter)
        System.out.println("Test 9 (Centimeter to Centimeter): " + testEquality(cm1, new QuantityLength(1.0, LengthUnit.CENTIMETERS))); // Should print true

        // Test for equality of different units with different values (Yard vs Feet non-equivalent)
        System.out.println("Test 10 (Yard to Feet non-equivalent): " + testEquality(yard1, new QuantityLength(2.0, LengthUnit.FEET))); // Should print false

        // Test for multi-unit transitive property (Yard -> Feet -> Inches)
        System.out.println("Test 11 (Transitive Property, Yard to Inches via Feet): " + testEquality(yard1, inch1)); // Should print true

        // Test for same reference (Yard)
        System.out.println("Test 12 (Same reference comparison): " + testEquality(yard1, yard1)); // Should print true

        // Test for null comparison (Yard)
        System.out.println("Test 13 (Null comparison): " + testEquality(yard1, null)); // Should print false
    }
}
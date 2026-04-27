import java.util.Objects;

/**
 * QuantityMeasurementApp demonstrates unit comparison, conversion, and now addition
 * using a generic QuantityLength class following DRY principles.
 */
public class QuantityMeasurementApp {

    /**
     * Enum representing supported length units.
     * Conversion factor is relative to base unit: INCHES.
     */
    public enum LengthUnit {
        FEET(12.0),            // 1 foot = 12 inches
        INCHES(1.0),           // base unit
        YARDS(36.0),           // 1 yard = 36 inches
        CENTIMETERS(0.393701); // 1 cm = 0.393701 inches

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    /**
     * Immutable value object representing a length with unit.
     */
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            validate(value, unit);
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        /**
         * Converts current object to another unit and returns a NEW instance.
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double converted = convert(this.value, this.unit, targetUnit);
            return new QuantityLength(converted, targetUnit);
        }

        /**
         * Converts value from source → target unit.
         */
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            validate(value, source);
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            // Same unit shortcut
            if (source == target) return value;

            // Convert to base (inches)
            double baseValue = toBaseUnit(value, source);

            // Convert base → target
            return baseValue / target.getFactor();
        }

        /**
         * Normalize to base unit (inches)
         */
        private static double toBaseUnit(double value, LengthUnit unit) {
            return value * unit.getFactor();
        }

        /**
         * Validation helper
         */
        private static void validate(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
        }

        /**
         * Equality based on normalized base unit.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisBase = toBaseUnit(this.value, this.unit);
            double otherBase = toBaseUnit(other.value, other.unit);

            return Double.compare(thisBase, otherBase) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(toBaseUnit(value, unit));
        }

        /**
         * Human-readable output
         */
        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }

        /**
         * Add two QuantityLength objects, resulting in the first operand's unit.
         */
        public static QuantityLength add(QuantityLength length1, QuantityLength length2) {
            if (length1 == null || length2 == null) {
                throw new IllegalArgumentException("Length objects cannot be null");
            }

            // Convert both lengths to the base unit (inches)
            double baseLength1 = toBaseUnit(length1.value, length1.unit);
            double baseLength2 = toBaseUnit(length2.value, length2.unit);

            // Add the lengths
            double resultBase = baseLength1 + baseLength2;

            // Convert result back to the unit of the first operand
            double result = resultBase / length1.unit.getFactor();

            // Return new QuantityLength in the unit of the first operand
            return new QuantityLength(result, length1.unit);
        }
    }

    // -------------------------------
    // Demonstration API (Overloading)
    // -------------------------------

    public static void demonstrateLengthAddition(QuantityLength length1, QuantityLength length2) {
        QuantityLength result = QuantityLength.add(length1, length2);
        System.out.println(length1 + " + " + length2 + " = " + result);
    }

    public static void demonstrateLengthAddition(double v1, LengthUnit u1,
                                                 double v2, LengthUnit u2) {
        QuantityLength length1 = new QuantityLength(v1, u1);
        QuantityLength length2 = new QuantityLength(v2, u2);
        demonstrateLengthAddition(length1, length2);
    }

    // -------------------------------
    // MAIN METHOD (Test Cases UC6)
    // -------------------------------
    public static void main(String[] args) {

        // Same unit addition
        demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(2.0, LengthUnit.FEET));
        demonstrateLengthAddition(new QuantityLength(6.0, LengthUnit.INCHES), new QuantityLength(6.0, LengthUnit.INCHES));

        // Cross-unit addition
        demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(12.0, LengthUnit.INCHES));
        demonstrateLengthAddition(new QuantityLength(12.0, LengthUnit.INCHES), new QuantityLength(1.0, LengthUnit.FEET));
        demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(3.0, LengthUnit.FEET));
        demonstrateLengthAddition(new QuantityLength(36.0, LengthUnit.INCHES), new QuantityLength(1.0, LengthUnit.YARDS));

        // Cross-unit with centimeters
        demonstrateLengthAddition(new QuantityLength(2.54, LengthUnit.CENTIMETERS), new QuantityLength(1.0, LengthUnit.INCHES));

        // Identity and Negative values
        demonstrateLengthAddition(new QuantityLength(5.0, LengthUnit.FEET), new QuantityLength(0.0, LengthUnit.INCHES));
        demonstrateLengthAddition(new QuantityLength(5.0, LengthUnit.FEET), new QuantityLength(-2.0, LengthUnit.FEET));

        // Commutativity check
        demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(12.0, LengthUnit.INCHES));
        demonstrateLengthAddition(new QuantityLength(12.0, LengthUnit.INCHES), new QuantityLength(1.0, LengthUnit.FEET));

        // Large and small values
        demonstrateLengthAddition(new QuantityLength(1e6, LengthUnit.FEET), new QuantityLength(1e6, LengthUnit.FEET));
        demonstrateLengthAddition(new QuantityLength(0.001, LengthUnit.FEET), new QuantityLength(0.002, LengthUnit.FEET));

        // Test invalid cases (uncomment to verify)
        // demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.FEET), null); // Should throw an exception
    }
}
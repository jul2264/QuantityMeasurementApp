import java.util.Objects;

/**
 * QuantityMeasurementApp demonstrates unit comparison and conversion
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
         * Converts current object to another unit and returns NEW instance.
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
    }

    // -------------------------------
    // Demonstration API (Overloading)
    // -------------------------------

    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") = " + result);
    }

    public static void demonstrateLengthConversion(QuantityLength quantity,
                                                   LengthUnit to) {
        QuantityLength converted = quantity.convertTo(to);
        System.out.println(quantity + " → " + converted);
    }

    public static void demonstrateLengthEquality(QuantityLength q1,
                                                 QuantityLength q2) {
        System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));
    }

    public static void demonstrateLengthComparison(double v1, LengthUnit u1,
                                                   double v2, LengthUnit u2) {
        QuantityLength q1 = new QuantityLength(v1, u1);
        QuantityLength q2 = new QuantityLength(v2, u2);
        demonstrateLengthEquality(q1, q2);
    }

    // -------------------------------
    // MAIN METHOD (Test Cases UC5)
    // -------------------------------
    public static void main(String[] args) {

        // Basic conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);     // 12
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);      // 9
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);   // 1
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);

        // Instance conversion
        QuantityLength length = new QuantityLength(2.0, LengthUnit.YARDS);
        demonstrateLengthConversion(length, LengthUnit.FEET);

        // Equality checks
        demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);
        demonstrateLengthComparison(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES);

        // Edge cases
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(-1.0, LengthUnit.FEET, LengthUnit.INCHES);

        // Round-trip test
        double v = 5.0;
        double roundTrip = QuantityLength.convert(
                QuantityLength.convert(v, LengthUnit.FEET, LengthUnit.INCHES),
                LengthUnit.INCHES,
                LengthUnit.FEET
        );
        System.out.println("Round-trip preserved: " + (Math.abs(v - roundTrip) < 1e-6));

        // Exception test (uncomment to verify)
        // QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES);
    }
}
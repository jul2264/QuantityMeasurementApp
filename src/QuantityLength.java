public class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid unit or value");
        }
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
     * Convert this QuantityLength to another unit.
     * Delegates conversion logic to LengthUnit class.
     */
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        // Convert to base unit (feet), then convert to target unit
        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
        return new QuantityLength(convertedValue, targetUnit);
    }

    /**
     * Add two QuantityLength objects, converting to base unit (feet), then to the target unit.
     */
    public static QuantityLength add(QuantityLength length1, QuantityLength length2, LengthUnit targetUnit) {
        if (length1 == null || length2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid length objects or target unit");
        }

        // Convert both lengths to base unit (feet)
        double baseValue1 = length1.unit.convertToBaseUnit(length1.value);
        double baseValue2 = length2.unit.convertToBaseUnit(length2.value);

        // Sum the base unit values
        double sumBaseValue = baseValue1 + baseValue2;

        // Convert the sum back to the target unit
        double result = targetUnit.convertFromBaseUnit(sumBaseValue);

        // Return the result in the target unit
        return new QuantityLength(result, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        // Convert both to base unit (feet) for comparison
        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        return Double.compare(baseValue1, baseValue2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
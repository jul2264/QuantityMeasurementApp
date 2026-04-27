public enum LengthUnit {
    FEET(1.0),             // Base unit: 1 foot = 1.0 feet
    INCHES(1.0 / 12.0),    // 1 inch = 1/12 feet
    YARDS(3.0),            // 1 yard = 3 feet
    CENTIMETERS(1.0 / 30.48); // 1 cm = 1/30.48 feet (approx)

    private final double factor;

    // Constructor for setting conversion factors
    LengthUnit(double factor) {
        this.factor = factor;
    }

    // Method to get the conversion factor for the unit (relative to feet)
    public double getFactor() {
        return factor;
    }

    // Convert the value from the current unit to the base unit (feet)
    public double convertToBaseUnit(double value) {
        return value * this.factor;
    }

    // Convert the value from the base unit (feet) to the current unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.factor;
    }
}
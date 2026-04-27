public enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592); // 1 lb ≈ 0.453592 kg

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    // Convert a value to kilograms (base unit)
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    // Convert a value from kilograms (base unit) to this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / this.conversionFactor;
    }
}
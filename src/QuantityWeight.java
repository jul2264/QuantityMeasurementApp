public class QuantityMeasurementApp {
    public static void main(String[] args) {
        // Example use cases for weight measurement
        QuantityWeight weight1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight weight2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight weight3 = new QuantityWeight(2.0, WeightUnit.POUND);

        // Equality checks
        System.out.println(weight1.equals(weight2));  // Should print true
        System.out.println(weight2.equals(weight3));  // Should print true

        // Conversion
        QuantityWeight convertedWeight = weight1.convertTo(WeightUnit.GRAM);
        System.out.println(convertedWeight);  // Should print 1000.0 GRAM

        // Addition (same unit)
        QuantityWeight sumSameUnit = weight1.add(new QuantityWeight(2.0, WeightUnit.KILOGRAM));
        System.out.println(sumSameUnit);  // Should print 3.0 KILOGRAM

        // Addition (different units)
        QuantityWeight sumCrossUnit = weight1.add(new QuantityWeight(1000.0, WeightUnit.GRAM));
        System.out.println(sumCrossUnit);  // Should print 2.0 KILOGRAM

        // Addition (explicit target unit)
        QuantityWeight sumExplicitUnit = weight1.add(new QuantityWeight(453.592, WeightUnit.GRAM), WeightUnit.POUND);
        System.out.println(sumExplicitUnit);  // Should print ~2.0 POUND
    }
}
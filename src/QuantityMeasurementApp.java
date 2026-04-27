public class QuantityMeasurementApp {

    // Demonstration method to show conversion and addition
    public static void demonstrateLengthOperations() {
        // Convert 1 foot to inches
        QuantityLength length1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength lengthInInches = length1.convertTo(LengthUnit.INCHES);
        System.out.println("1 foot = " + lengthInInches);

        // Add 1 foot and 12 inches, target unit is feet
        QuantityLength length2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength addedLength = QuantityLength.add(length1, length2, LengthUnit.FEET);
        System.out.println("1 foot + 12 inches = " + addedLength);

        // Add 1 yard and 3 feet, target unit is yards
        QuantityLength length3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength length4 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength addedLengthInYards = QuantityLength.add(length3, length4, LengthUnit.YARDS);
        System.out.println("1 yard + 3 feet = " + addedLengthInYards);

        // Convert 2.54 cm to inches
        QuantityLength length5 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength convertedToInches = length5.convertTo(LengthUnit.INCHES);
        System.out.println("2.54 cm = " + convertedToInches);
    }

    public static void main(String[] args) {
        demonstrateLengthOperations();
    }
}
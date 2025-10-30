import java.util.ArrayList;

class SoilAnalysis {
    private String farmerId;
    private String districtName;
    private double nitrogenLevel;
    private double phosphorusLevel;
    private double potassiumLevel;
    private String cropType;

    private final double MIN_BALANCED = 20.0;
    private final double MAX_BALANCED = 100.0;

    public SoilAnalysis(String farmerId, String districtName, double nitrogenLevel,
                        double phosphorusLevel, double potassiumLevel, String cropType) {
        this.farmerId = farmerId;
        this.districtName = districtName;
        this.nitrogenLevel = nitrogenLevel;
        this.phosphorusLevel = phosphorusLevel;
        this.potassiumLevel = potassiumLevel;
        this.cropType = cropType;
    }

    public String getFarmerId() { return farmerId; }
    public String getDistrictName() { return districtName; }
    public double getNitrogenLevel() { return nitrogenLevel; }
    public double getPhosphorusLevel() { return phosphorusLevel; }
    public double getPotassiumLevel() { return potassiumLevel; }
    public String getCropType() { return cropType; }

    public boolean isValidReading() {
        return nitrogenLevel >= 0 && phosphorusLevel >= 0 && potassiumLevel >= 0;
    }

    public boolean isBalanced() {
        return (nitrogenLevel >= MIN_BALANCED && nitrogenLevel <= MAX_BALANCED) &&
                (phosphorusLevel >= MIN_BALANCED && phosphorusLevel <= MAX_BALANCED) &&
                (potassiumLevel >= MIN_BALANCED && potassiumLevel <= MAX_BALANCED);
    }

    public String calculateFertilizerNeeded() {

        if (!isValidReading()) {
            return "Error: Invalid nutrient reading";
        }

        if (isBalanced()) {
            return "OPTIMAL - Maintenance fertilizer only";
        }

        ArrayList<String> excessiveNutrients = new ArrayList<>();
        if (nitrogenLevel > MAX_BALANCED) {
            excessiveNutrients.add("Nitrogen");
        }
        if (potassiumLevel > MAX_BALANCED) {
            excessiveNutrients.add("Potassium");
        }

        if (!excessiveNutrients.isEmpty()) {
            String nutrientNames = String.join(" ", excessiveNutrients);
            return "EXCESS - Reduce " + nutrientNames + " application";
        }

        ArrayList<String> deficientNutrients = new ArrayList<>();
        if (nitrogenLevel < MIN_BALANCED) {
            deficientNutrients.add("Nitrogen");
        }
        if (potassiumLevel < MIN_BALANCED) {
            deficientNutrients.add("Potassium");
        }

        if (!deficientNutrients.isEmpty()) {
            String nutrientNames = String.join(" ", deficientNutrients);
            return "DEFICIENCY - High application needed for " + nutrientNames;
        }

        if (phosphorusLevel > MAX_BALANCED) {
            return "EXCESS - Reduce Phosphorus application";
        }

        return "Analysis Inconclusive.";
    }
}


class FertilizerAdvisorySystem {

    public void processSample(SoilAnalysis sample) {
        String recommendation = sample.calculateFertilizerNeeded();

        System.out.println("farmerid: " + sample.getFarmerId());
        System.out.println("district: " + sample.getDistrictName());
        System.out.println("croptype: " + sample.getCropType());

        if (recommendation.startsWith("Error")) {
            System.out.println("recommendation: " + recommendation);
        } else {
            System.out.println("recommendation: " + recommendation);
        }
        System.out.println("--------------------------------------------------------");
    }

    public void processSamples(SoilAnalysis[] samples) {
        for (SoilAnalysis sample : samples) {
            processSample(sample);
        }
    }

    public static void main(String[] args) {

        SoilAnalysis[] samples = {
                new SoilAnalysis("F001", "Nyamagabe", 60.0, 70.0, 80.0, "Maize"),
                new SoilAnalysis("F002", "Rwamagana", 10.0, 50.0, 15.0, "Beans"),
                new SoilAnalysis("F003", "Musanze", 120.0, 90.0, 105.0, "Potatoes"),
                new SoilAnalysis("F004", "Rubavu", -5.0, 80.0, 90.0, "Rice")
        };

        FertilizerAdvisorySystem system = new FertilizerAdvisorySystem();

        system.processSamples(samples);
    }
}
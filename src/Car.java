import java.util.List;

public class Car {
    private List<String> variants;
    private List<String> price_range;

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public List<String> getPrice_range() {
        return price_range;
    }

    public void setPrice_range(List<String> price_range) {
        this.price_range = price_range;
    }

    @Override
    public String toString() {
        return "Variants: " + variants + ", Price Range: " + price_range;
    }
}

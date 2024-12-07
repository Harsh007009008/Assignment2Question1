import java.util.HashMap;

public class Accessories {
    private HashMap<String, String> items;

    public HashMap<String, String> getItems() {
        return items;
    }

    public void setItems(HashMap<String, String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Items: " + items;
    }
}

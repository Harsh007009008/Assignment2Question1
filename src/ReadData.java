import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadData {
    private HashMap<String, Car> cars = new HashMap<>();
    private HashMap<String, Accessories> accessories = new HashMap<>();

    public void readData() {
        try {
            String carContent = new String(Files.readAllBytes(Paths.get("Car.json")));
            JSONObject carJsonObject = new JSONObject(carContent);

            carJsonObject.keys().forEachRemaining(carModel -> {
                JSONObject carDetails = carJsonObject.getJSONObject(carModel);
                JSONArray variants = carDetails.getJSONArray("variants");
                JSONArray priceRange = carDetails.getJSONArray("price_range");

                Car car = new Car();
                ArrayList<String> var = new ArrayList<>();
                ArrayList<String> price = new ArrayList<>();
                for (int i = 0; i < variants.length(); i++) var.add(variants.getString(i));
                for (int i = 0; i < priceRange.length(); i++) price.add(priceRange.getString(i));

                car.setVariants(var);
                car.setPrice_range(price);

                cars.put(carModel, car);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String accessoryContent = new String(Files.readAllBytes(Paths.get("Accessories.json")));
            JSONObject accessoryJsonObject = new JSONObject(accessoryContent);

            accessoryJsonObject.keys().forEachRemaining(category -> {
                JSONObject items = accessoryJsonObject.getJSONObject(category);
                Accessories accessory = new Accessories();
                HashMap<String, String> itemMap = new HashMap<>();
                items.keys().forEachRemaining(item -> {
                    itemMap.put(item, items.getString(item));
                });
                accessory.setItems(itemMap);
                accessories.put(category, accessory);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Car> getCars() {
        return cars;
    }

    public HashMap<String, Accessories> getAccessories() {
        return accessories;
    }
}

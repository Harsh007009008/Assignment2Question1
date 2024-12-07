import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static double amount(double x){
        double registrationFee = x * 0.07;
        double insuranceFee = x * 0.12;
        double handlingFee = x * 0.02;
        return x + registrationFee + insuranceFee + handlingFee;
    }

    public static double originalPrice(double totalAmount){
        return totalAmount / 1.21;
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.00");

        ReadData data = new ReadData();
        data.readData();
        HashMap<String, Car> cars = data.getCars();
        HashMap<String, Accessories> accessories = data.getAccessories();

        Scanner scanner = new Scanner(System.in);

        HashMap<String, HashMap<String, Double>> list = new HashMap<>();
        do {
            System.out.println("To Buy Car press 1 : To Exit Press any other Number");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Please Enter Your Budget : ");
                double budget = scanner.nextDouble();

                for (Map.Entry<String, Car> s : cars.entrySet()) {
                    for (int i = 0; i < s.getValue().getPrice_range().size(); i++) {
                        String pri = s.getValue().getPrice_range().get(i);
                        double price = Double.parseDouble(pri);
                        price *= 100000;
                        double priceAfterOtherCharges = amount(price);
                        if (priceAfterOtherCharges <= budget) {
                            if (list.containsKey(s.getKey())) {
                                list.get(s.getKey()).put(s.getValue().getVariants().get(i), priceAfterOtherCharges);
                            } else {
                                HashMap<String, Double> temp = new HashMap<>();
                                temp.put(s.getValue().getVariants().get(i), priceAfterOtherCharges);
                                list.put(s.getKey(), temp);
                            }
                        }

                    }
                }
                if(list.isEmpty()){
                    System.out.println("Sorry No Cars in Budget");
                    continue;
                }
                choice = 1;
                System.out.println("Here is the Car You Can Choose : ");
                Map<Integer, String> carChoice = new HashMap<>();
                for (Map.Entry<String, HashMap<String, Double>> e : list.entrySet()) {
                    System.out.println(choice + "  " + e.getKey());
                    carChoice.put(choice, e.getKey());
                    choice++;
                }
                System.out.println("Enter Your Choice : ");
                int carChoosen = scanner.nextInt();
                if (carChoosen >= choice) break;
                else {
                    String carModel = carChoice.get(carChoosen);
                    HashMap<String, Double> temp = list.get(carChoice.get(carChoosen));
                    choice = 1;
                    System.out.println("Here is the Car Variant You Can Choose : ");
                    Map<Integer, String> variantChoice = new HashMap<>();
                    for (Map.Entry<String, Double> e : temp.entrySet()) {
                        System.out.println(choice + "  " + e.getKey());
                        variantChoice.put(choice, e.getKey());
                        choice++;
                    }
                    System.out.println("Enter Your Choice : ");
                    carChoosen = scanner.nextInt();
                    if (carChoosen >= choice) break;
                    else {
                        String carBought = variantChoice.get(carChoosen);
                        Double currentAmountCar = list.get(carModel).get(variantChoice.get(carChoosen));

                        System.out.println("Here are the accessories that you can choose:");
                        choice = 1;
                        HashMap<Integer, String> accessor = new HashMap<>();
                        for (Map.Entry<String, Accessories> e : accessories.entrySet()) {
                            System.out.println();
                            System.out.println();
                            System.out.println("                  " + e.getKey());
                            for (Map.Entry<String, String> x : e.getValue().getItems().entrySet()) {
                                System.out.println(choice + "  " + x.getKey() + "  " + x.getValue());
                                accessor.put(choice, x.getKey());
                                choice++;
                            }
                        }
                        System.out.println();
                        System.out.println();
                        System.out.println("Your current Budget is : " + df.format(budget - currentAmountCar) + " Please make your Choices :)");

                        HashMap<String, Double> bought = new HashMap<>();
                        Double currAmount = originalPrice(currentAmountCar);

                        while (true) {
                            System.out.println();
                            System.out.println("  To get out of Choice Mode press 0 !!!");
                            int accessChoice = scanner.nextInt();
                            if (accessChoice == 0) break;
                            if (accessChoice < 0 || accessChoice > 30) {
                                System.out.println("Wrong choice, please choose again!!!");
                                System.out.println();
                                continue;
                            }
                            System.out.println("Here is the range, enter the amount!!!");
                            String priceRange = null;
                            for (Map.Entry<String, Accessories> e : accessories.entrySet()) {
                                if (e.getValue().getItems().containsKey(accessor.get(accessChoice))) {
                                    priceRange = e.getValue().getItems().get(accessor.get(accessChoice));
                                    break;
                                }
                            }
                            if (priceRange == null) {
                                System.out.println("Price range not found for the selected accessory.");
                                continue;
                            }
                            System.out.println(accessor.get(accessChoice) + "   " + priceRange);
                            String first = "", second = "";
                            boolean flag = false;

                            for (int i = 0; i < priceRange.length(); i++) {
                                if (priceRange.charAt(i) == '-') flag = true;
                                if (!flag) {
                                    if (Character.isDigit(priceRange.charAt(i))) {
                                        first += priceRange.charAt(i);
                                    }
                                } else {
                                    if (Character.isDigit(priceRange.charAt(i))) {
                                        second += priceRange.charAt(i);
                                    }
                                }
                            }
                            Double one = Double.parseDouble(first);
                            Double two = Double.parseDouble(second);
                            double amt = scanner.nextDouble();
                            if (amt < one || amt > two) {
                                System.out.println("Amount entered out of Range");
                                continue;
                            } else {
                                if (amount(amt + currAmount) > budget) {
                                    System.out.println("This is getting out of Budget !!! The amount is : " + df.format(amount(amt + currAmount)));
                                    continue;
                                } else {
                                    bought.put(accessor.get(accessChoice), amt);
                                    currAmount += amt;
                                    System.out.println("Money Left in Budget : " + df.format(budget - amount(currAmount)));
                                }
                            }
                        }

                        System.out.println("Thank You For Your Purchase. Here is Your Bill. Visit Again :)");
                        System.out.println("---------------------------------------------------------------------");
                        System.out.println("Items    :   amount");
                        System.out.println(carBought + "   :   " + df.format(originalPrice(currentAmountCar)));
                        for (Map.Entry<String, Double> z : bought.entrySet()) {
                            System.out.println(z.getKey() + "   :   " + df.format(z.getValue()));
                        }
                        System.out.println("---------------------------------------------------------------------");
                        System.out.println("Total   :   " + df.format(currAmount));
                        System.out.println("Grand Total After 7% registration, 12% insurance, 2% handling fee");
                        System.out.println();
                        System.out.println("Total amount Paid   :   " + df.format(amount(currAmount)));
                        System.out.println();
                        System.out.println("                         Thank You");

                    }

                }


            } else {
                System.out.println("Thank You");
                break;
            }
        } while (list.isEmpty());
    }
}

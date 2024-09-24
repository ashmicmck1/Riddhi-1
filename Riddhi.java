import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Collectors;

/*
https://www.youtube.com/watch?v=0ada8fAMpVs

Riddhi Dutta gives a fairly thorough introduction to Java8 lambdas at the above URL.
You should watch the video more than once.

During class, I'll discuss:
    Enumerated Types by looking at the HotelQuality.java file.
    Refactoring code by re-writing TODO 1 in Hotels.java.
    Solve familiar Java problems (TODO 2-4) in Hotels.java.
    Pass a function as a parameter by doing TODO 5 and TODO 6 in Hotels.java.
    Implementing a functional interface by looking at the Hotel.java file.
    Solve TODO 7 and TODO 8 in Riddhi.java.
    And then you'll solve the remaining TODOs in this Riddhi.java file.
*/
public class Riddhi {

    static Random rand = new Random();

    public static void main(String[] args) {
        System.out.println("\n\nHello, Lambdas!\n");

        Hotels hotels = new Hotels();
        System.out.printf("Hotels:\n%s\n", hotels);

        // TODO 7 - Sort the hotels by price.
        List<Hotel> listOfHotels = hotels.get();
        // You may have used Java8 when sorting objects.
        // When you see an arrow -> in Java, you're defining (not calling) a function.
        listOfHotels.sort((h1, h2) -> h2.compareTo(h1)); // why not h1.compareTo(h2), this order results in ascending
                                                         // not descending
        System.out.printf("\nList<Hotel> (sorted by price):\n%s\n", listOfHotels);

        listOfHotels.removeLast();

        System.out.printf("\nHotels (sorted by price):\n%s\n", hotels);
        // TODO 8 - What happened to the 2025 hotel?
        // The 2025 hotel was removed from the list of hotels held in the Hotels class
        // using the removeLast method
        // The change occurred within the object because the listOfHotels variable did
        // not create a copy of the list.
        // It references the the hotel list from the class, so any changes made to it
        // are reflected
        // in the original list found in the hotels object.

        // TODO 9 - What are the 4 star hotels?
        // (a) In 1724, we'd write a for-i loop and return a list of hotels with quality
        // == 4
        // (b) then we learned to write an enhanced-for loop and return hotels with
        // quality == 4
        // (c) now we know we can use an enumerated type so quality ==
        // HotelQuality.FOUR_STAR
        // (d) Post-Java8, we'd use a lambda
        List<Hotel> fourStarHotels = hotels.filterBy((Hotel h) -> h.getQuality().equals(HotelQuality.FOUR_STAR));
        System.out.printf("\nThe four star hotels:\n%s\n", fourStarHotels);

        // ------------------------- Lambdas
        // When you see an arrow -> in Java, you're using a lambda.
        // A lambda gives us a way to pass a function as a parameter to some other
        // function.
        // When you see an arrow -> in Java, you're defining (not calling) a function.

        List<Hotel> costLessThan100 = hotels.filterBy((Hotel h) -> h.getPrice() < 100);
        System.out.printf("\nHotels less than $100: %s\n", costLessThan100);

        // TODO 10 - Which hotels are more than 10 years old?
        List<Hotel> moreThan10Old = hotels.filterBy((h) -> h.getRenovationYear() > LocalDate.now().getYear() - 10);
        System.out.printf("\nHotels more than 10 years old: %s\n", moreThan10Old);

        // TODO 11 - How many hotels are priced in 111..333?
        List<Hotel> costBetween111And333 = hotels.filterBy((h) -> h.getPrice() >= 111 & h.getPrice() <= 333);
        System.out.printf("\nHotels that cost between $111 and $333: %s\n", costBetween111And333);

        // TODO 12 - Which hotels cost more than 300 but are not 5-star hotels?
        List<Hotel> costMoreThan300ButNot5Star = hotels
                .filterBy((h) -> h.getPrice() >= 300 & h.getQuality() != HotelQuality.FIVE_STAR);
        System.out.printf("\nHotels that cost more than $300 but are not 5-star: %s\n", costMoreThan300ButNot5Star);

        // Java also has streams!

        // TODO 13 - What is the cheapest price of 5-star hotel(s) that are no more than
        // 3 years old?
        List<Hotel> topYoungHotels = hotels
                .filterBy(h -> h.getQuality() == HotelQuality.FIVE_STAR && h.getRenovationYear() > 2021);
        double price = topYoungHotels.stream().map(Hotel::getPrice).min(Double::compare).get();
        System.out.printf("\n$%,.2f is the cheapest price of a 5-star hotel renovated after 2021\n", price);

        // TODO 14 - Output the price and year of each hotel. Can I solve this using a
        // predicate?
        // A predicate would not be suitable for this because there are no conditions.
        System.out.println();
        hotels.get().stream()
                .forEach(h -> System.out.printf("Price: $%,.2f, Year: %d\n", h.getPrice(), h.getRenovationYear()));

        // TODO 15 - Output the hotel renovation years a decade from now.
        System.out.println();
        hotels.get().stream()
                .map(h -> h.getRenovationYear() + 10) // Add 10 years to the renovation year
                .forEach(year -> System.out.printf("Renovation year a decade from now: %d\n", year));

        // filter, map, and reduce
        // filter a predicate that separates the records in a stream
        // map applies a lambda to attributes of a record
        // reduce combines a bunch of intermediate records into a single value (sum,
        // count, toList)

        // TODO 16 - Create a new list of hotels by giving all hotels a 20% discount.
        List<Hotel> discountAllPrices = hotels.get()
                .stream()
                .map(h -> new Hotel(h.getPrice() * 0.80, h.getRenovationYear(), h.getQuality()))
                .collect(Collectors.toList());
        System.out.printf("\n20%% discount to all %d hotels: \t %s\n", discountAllPrices.size(), discountAllPrices);

        // TODO 17 - Create a new list of hotels by giving a 20% discount to the hotels
        // renovated in an even year.
        List<Hotel> evenRenovationYearHotels = hotels.filterBy((h) -> h.getRenovationYear() % 2 == 0);
        List<Hotel> discountHotels = evenRenovationYearHotels
                .stream()
                .map(h -> new Hotel(h.getPrice() * 0.80, h.getRenovationYear(), h.getQuality()))
                .collect(Collectors.toList());
        System.out.printf("\n20%% discount to all %d hotels renovated in an even year: \t %s\n", discountHotels.size(),
                discountHotels);

        // TODO 18 - What will it cost to get a room in all the hotels for one day?
        double sumPrice = hotels.get()
                .stream()
                .mapToDouble(h -> h.getPrice())
                .sum();
        System.out.printf("\nIt costs $%,.2f to rent all the hotel rooms\n", sumPrice);

        // TODO 19 - What is the average age (to 3 decimal places) of all the hotels?
        double averageAge = hotels.get()
                .stream()
                .mapToDouble(h -> LocalDate.now().getYear() - h.getRenovationYear())
                .average()
                // Need this to force type to be double
                .orElse(0.000);
        System.out.printf("\nThe average age of all the hotels is %.3f\n", averageAge);

        // TODO 20 - Subtract 33% from the price of all 5 star hotels,
        // add 100 to the age of all 4-star hotels,
        // and raise the quality of all 3-star hotels by a random 0 or 1 or 2.

        hotels.filterBy(h -> h.getQuality().equals(HotelQuality.FIVE_STAR))
                .stream()
                .map(h -> new Hotel(h.getPrice() * .67, h.getRenovationYear(), h.getQuality()));

        hotels.filterBy(h -> h.getQuality().equals(HotelQuality.FOUR_STAR))
                .stream()
                .map(h -> new Hotel(h.getPrice(), h.getRenovationYear() - 100, h.getQuality()));

        hotels.filterBy(h -> h.getQuality().equals(HotelQuality.THREE_STAR))
                .stream()
                .map(h -> {
                    HotelQuality newQuality;
                    int newStars = h.getQuality().numStars + rand.nextInt(0, 3);
                    if (newStars >= 5) {
                        newQuality = HotelQuality.FIVE_STAR;
                    } else if (newStars == 4) {
                        newQuality = HotelQuality.FOUR_STAR;
                    } else {
                        newQuality = HotelQuality.THREE_STAR;
                    }

                    return new Hotel(h.getPrice(), h.getRenovationYear(), newQuality);

                });

        // Why is there no "Hotel-3"?
        System.out.printf("\nList of hotels after changes: \n%s", hotels);

        // TODO 21 - Soon, we'll have an in-class 30 minute mini-test on lambdas
        // that will add [0..20] points to your Hwk03 score.

    }
}

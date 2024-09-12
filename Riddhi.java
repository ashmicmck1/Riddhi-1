import java.time.LocalDate;
import java.util.List;
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
        listOfHotels.sort((h1, h2) -> h2.compareTo(h1));  // why not     h1.compareTo(h2)
        System.out.printf("\nList<Hotel> (sorted by price):\n%s\n", listOfHotels);

        listOfHotels.removeLast();

        System.out.printf("\nHotels (sorted by price):\n%s\n", hotels);
        // TODO 8 - What happened to the 2025 hotel?

        // TODO 9 - What are the 4 star hotels?
        // (a) In 1724, we'd write a for-i loop and return a list of hotels with quality == 4
        // (b) then we learned to write an enhanced-for loop and return hotels with quality == 4
        // (c) now we know we can use an enumerated type so quality == HotelQuality.FOUR_STAR
        // (d) Post-Java8, we'd use a lambda
        List<Hotel> fourStarHotels = hotels.filterBy((Hotel h) -> h.getQuality().equals(HotelQuality.FOUR_STAR));
        System.out.printf("\nThe four star hotels:\n%s\n", fourStarHotels);

        //------------------------- Lambdas
        // When you see an arrow -> in Java, you're using a lambda.
        // A lambda gives us a way to pass a function as a parameter to some other function.
        // When you see an arrow -> in Java, you're defining (not calling) a function.

        List<Hotel> costLessThan100 = hotels.filterBy((Hotel h) -> h.getPrice() < 100);
        System.out.printf("\nHotels less than $100: %s\n", costLessThan100);

        // TODO 10 - Which hotels are more than 10 years old?

        // TODO 11 - How many hotels are priced in 111..333?

        // TODO 12 - Which hotels cost more than 300 but are not 5-star hotels?


        // Java also has streams!


        // TODO 13 - What is the cheapest price of 5-star hotel(s) that are no more than 3 years old?
        List<Hotel> topYoungHotels = hotels.filterBy(h ->
            h.getQuality() == HotelQuality.FIVE_STAR && h.getRenovationYear() > 2021);
        double price = topYoungHotels.stream().map(Hotel::getPrice).max(Double::compare).get();
        System.out.printf("\n$%,.2f is the cheapest price of a 5-star hotel renovated after 2021\n", price);

        // TODO 14 - Output the price and year of each hotel.  Can I solve this using a predicate?
        
        // TODO 15 - Output the hotel renovation years a decade from now.


        // filter, map, and reduce
        // filter      a predicate that separates the records in a stream
        // map         applies a lambda to attributes of a record
        // reduce      combines a bunch of intermediate records into a single value (sum, count, toList)
        
        
        // TODO 16 - Create a new list of hotels by giving all hotels a 20% discount.
        List<Hotel> discountAllPrices = hotels.get()
            .stream()
            .map(h -> new Hotel(h.getPrice() * 0.80, h.getRenovationYear(), h.getQuality())) 
            .collect(Collectors.toList());
        System.out.printf("\n20%% discount to all %d hotels: \t %s\n", discountAllPrices.size(), discountAllPrices);

        // TODO 17 - Create a new list of hotels by giving a 20% discount to the hotels renovated in an even year.

        // TODO 18 - What will it cost to get a room in all the hotels for one day?
        double sumPrice = hotels.get()
            .stream()
            .mapToDouble(h -> h.getPrice())
            .sum();
        System.out.printf("\nIt costs $%,.2f to rent all the hotel rooms\n", sumPrice);

        // TODO 19 - What is the average age (to 3 decimal places) of all the hotels?

        // TODO 20 - Subtract 33% from the price of all 5 star hotels,
        //    add 100 to the age of all 4-star hotels,
        //    and raise the quality of all 3-star hotels by a random 0 or 1 or 2.



        // TODO 21 - Soon, we'll have an in-class 30 minute mini-test on lambdas
        //     that will add [0..20] points to your Hwk03 score.

    }
}

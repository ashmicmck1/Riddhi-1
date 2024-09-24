import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Hotels {
    Random rand = new Random();

    ArrayList<Hotel> hotels = new ArrayList<>();

    public Hotels() {
        hotels.add(new Hotel(444.44, 2024, HotelQuality.THREE_STAR));
        hotels.add(new Hotel(55.55, 1999, HotelQuality.THREE_STAR));
        hotels.add(new Hotel(-99_999.99, 2025, HotelQuality.FIVE_STAR));
        hotels.add(new Hotel(333.33, 2000, HotelQuality.FOUR_STAR));
        hotels.add(new Hotel(222.22, 2023, HotelQuality.FIVE_STAR));
        hotels.add(new Hotel(444.44, 2006, HotelQuality.FOUR_STAR));

        hotels.add(new Hotel(
                rand.nextDouble(50, 500),
                rand.nextInt(1999, 2024),
                HotelQuality.THREE_STAR));

        hotels.add(new Hotel(
                rand.nextDouble(50, 500),
                rand.nextInt(1999, 2024),
                HotelQuality.THREE_STAR));
    }

    List<Hotel> get() {
        return hotels;
    }

    @Override
    public String toString() {
        return hotels.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    // TODO 1 - Get hotels with prices less than 100.
    // Google Java Style Guide https://google.github.io/styleguide/javaguide.html
    public ArrayList<Hotel> some_hotels() {
        ArrayList<Hotel> temp = new ArrayList<>();
        for (int i = 0; i < hotels.size(); i++) {
            if (hotels.get(i).getPrice() < 100) {
                temp.add(hotels.get(i));
            }
        }
        return temp;
    }

    // TODO 2 - Get hotels with prices less than 200.
    public ArrayList<Hotel> filterHotelsLessThan200() {
        ArrayList<Hotel> temp = new ArrayList<>();
        for (int i = 0; i < hotels.size(); i++) {
            if (hotels.get(i).getPrice() < 200) {
                temp.add(hotels.get(i));
            }
        }
        return temp;
    }

    // TODO 3 - Get hotels that are no more than 9 years old.
    public ArrayList<Hotel> filterHotelsYoungerThan10Years() {
        ArrayList<Hotel> temp = new ArrayList<>();
        for (int i = 0; i < hotels.size(); i++) {
            if (hotels.get(i).getRenovationYear() < (LocalDate.now().getYear() - 10)) {
                temp.add(hotels.get(i));
            }
        }
        return temp;
    }

    // TODO 4 - Get hotels that have the highest quality.
    public ArrayList<Hotel> filterHighestQualityHotels() {
        ArrayList<Hotel> temp = new ArrayList<>();
        for (int i = 0; i < hotels.size(); i++) {
            if (hotels.get(i).getQuality().equals(HotelQuality.FOUR_STAR)) {
                temp.add(hotels.get(i));
            }
        }
        return temp;
    }

    // Pre-Java8, it took a lot of code to combine TODO 1-4 into a single function.
    // We had to create a functional interface
    // 1. create a class that has only 1 function (predicate like "test")
    // 2. implement the interface for every one of your predicates
    // 3. pass instance of that implementation to the filter

    // TODO 5 - pass a filter function as a parameter
    public List<Hotel> filterBy(FilterFunction filter) {
        List<Hotel> filteredHotels = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (filter.test(hotel)) {
                filteredHotels.add(hotel);
            }
        }
        return filteredHotels;
    }

    // We are post-Java8 so you can skip Riddhi's video 11:25 - 25:35
    // then jump to Riddhi's discussion about lambdas.
    // TODO 6 - Look in my Riddhi.java file.

}

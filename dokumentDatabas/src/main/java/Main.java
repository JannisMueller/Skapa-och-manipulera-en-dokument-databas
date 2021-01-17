import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        RestaurantDao restaurantDao = new RestaurantDaoJBCDImpl();


        MongoCollection <Document> restaurantCollection = restaurantDao.connectionToCollection(
                "restaurantdb", "restaurant");

        //deletes all entries from the collection restaurant
        restaurantCollection.drop();

        Document documentRestaurantOne = restaurantDao.createNewDocument(
                "Sun Bakery Trattoria",
                4,
                new String[] { "Pizza", "Pasta", "Italian", "Coffee", "Sandwiches" });

        Document documentRestaurantTwo = restaurantDao.createNewDocument(
                "Blue Bagels Grill",
                3,
                new String[] {"Bagels", "Cookies", "Sandwiches"});

        Document documentRestaurantThree = restaurantDao.createNewDocument(
                "Hot Bakery Cafe",
                4,
                new String[] {"Bakery","Cafe", "Coffee", "Dessert"});


        List<Document> listOfRestaurants= new ArrayList<>();

        listOfRestaurants.add(documentRestaurantOne);
        listOfRestaurants.add(documentRestaurantTwo);
        listOfRestaurants.add(documentRestaurantThree);

        //inserts list with restaurants to collection
        restaurantCollection.insertMany(listOfRestaurants);

        //list of Documents with categories: Sandwiches
        restaurantDao.findDocumentsWithFilter(restaurantCollection,"categories","Sandwiches");


        //print out of all documents in the collection "restaurants"
        restaurantDao.printWholeCollection(restaurantCollection);




    }
}

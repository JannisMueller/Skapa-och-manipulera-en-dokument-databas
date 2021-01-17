
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public interface RestaurantDao {

    MongoCollection<Document> connectionToCollection (String nameDb, String collection);
    void printWholeCollection(MongoCollection<Document> collection);
    Document createNewDocument (String Name, int stars, String [] categories);
    void findDocumentsWithFilter(MongoCollection <Document> collection, String fieldName, String filter);



}

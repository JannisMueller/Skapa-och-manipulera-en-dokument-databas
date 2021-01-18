import com.mongodb.client.*;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;


public class RestaurantDaoJBCDImpl implements RestaurantDao {


    private MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");

    //connecting to local mongodb and displays all available database
    public RestaurantDaoJBCDImpl() {
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
    }

    //connects to specific Database and collection
    @Override
    public MongoCollection<Document> connectionToCollection(String nameDb, String collection) {
        MongoDatabase mongoDatabaseDB = mongoClient.getDatabase(nameDb);
        MongoCollection<Document> mongoCollection = mongoDatabaseDB.getCollection(collection);

        return mongoCollection;
        }

    @Override
    public void printWholeCollection(MongoCollection<Document> collection) {

        MongoCursor<Document> cursor = collection.find().iterator();

        try (cursor){
            System.out.println("List of all documents in collection " + collection);
            while(cursor.hasNext()){
                System.out.println(cursor.next().toJson());
            }

        }

    }

    @Override
    public Document createNewDocument(String name, int stars, String[] categories) {
        Document document = new Document()
                    .append("name", name)
                    .append("stars",stars)
                    .append("categories", Arrays.asList(categories));

        return document;
    }

    @Override
    public void findDocumentsWithFilter(MongoCollection <Document> collection, String fieldName, String filter, String fieldToIncludeInQueryOutput) {
        List <Document> queryResults = collection.find(eq(fieldName,filter))
                .projection(fields(excludeId(),
                        include(fieldToIncludeInQueryOutput))).
                        into(new ArrayList<>());

        System.out.println("Documents with " + fieldName + ": " +  filter);
        for(Document result : queryResults ) {
            System.out.println(result.toJson());
        }

    }

    @Override
    public void findOneAndUpdateWithIncrement(MongoCollection<Document> collection,
                                              String field, String nameOfRestaurant, String fieldToIncrement, int inc) {

        Bson filter = eq(field, nameOfRestaurant);
        Bson updateOperation = inc(fieldToIncrement,inc);

        collection.findOneAndUpdate(filter,updateOperation);

    }

    @Override
    public void findOneAndUpdateOneChangeNameOfRestaurant(MongoCollection<Document> collection, String field, String nameOfRestaurant, String newName) {
        Bson filter = eq(field, nameOfRestaurant);
        Bson updateOperation = set(field,newName);

        collection.updateOne(filter,updateOperation);
    }

    @Override
    public void findRestaurantWithFourOrMoreStars(MongoCollection<Document> collection) {
        List <Document> queryResults = collection.find(gte("stars",4)).projection(fields(excludeId(),
                        include("name","stars"))).into(new ArrayList<>());

        System.out.println("Documents with 4 or more stars" );
        for(Document result : queryResults ) {
            System.out.println(result.toJson());
        }

    }
}








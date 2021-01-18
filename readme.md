**Skapa-och-manipulera-en-dokument-databas**

Denna repository innehåller min lösning på en extrauppgift på Lab 3 (kurs "Utveckling mot databas och databasadministration", ITHS 202021)

**Task1 G-uppgiften**

Skriv alla satser och resultat från MongoDb som du använder i följande frågor i en txt fil:

\1. Skapa en databas vid namn citydb.

```java
> use citydb;

switched to db citydb
```

\2. Sätt in följande dokument i en kollektion med namn: cities

a. _id:1, name: Stockholm, population: 1372565

b. _id:2, name: Göteborg, population: 549839

c. _id:3, name: Malmö, population: 280415

d. _id:4, name: Uppsala, population: 140454

e. _id:5, name: Västerås, population: 110877

```java
> db.cities.insertMany( 
[
{ _id:1, name: "Stockholm", population: 1372565 },
{ _id:2, name: "Göteborg", population: 549839 },
{ _id:3, name: "Malmö", population: 280415 },
{ _id:4, name: "Uppsala", population: 140454 },
{_id:5, name: "Västerås", population: 110877}
] );

{ "acknowledged" : true, "insertedIds" : [ 1, 2, 3, 4, 5 ] }
```

\3. Hitta ett dokument där namnet är “Malmö” och visa bara “namn” och “population”.

```java
> db.cities.findOne({name: "Malmö"}, {name: 1, population: 1, _id: 0});

{ "name" : "Malmö", "population" : 280415 }
```

\4. Uppdatera dokumentet som har “namn” “Göteborg” med “population” 549890

```java
> db.cities.updateOne( 
{name: "Göteborg"},{ $set: { population: 549890 }});

{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
```

\5. Ta bort dokumentet med _id:4

```java
> db.cities.remove({_id: 4});

WriteResult({ "nRemoved" : 1 })
```

\6. Uppdatera dokumentet som har “namn” “Västerås” och öka (med increment) ”population”

fält så att det blir 110879. (increment by 2)

```java
> db.cities.update({name: "Västerås"}, { $inc: {population: 2}});

WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
```

\7. Lägg till ett fält “country” och värdet “Sweden” till alla dokument.

```java
> db.cities.updateMany({}, { $set: { country: "Sweden" }});

{ "acknowledged" : true, "matchedCount" : 4, "modifiedCount" : 4 }
```

\8. Byt namn på fältet “country” till “Country”.

```java
> db.cities.updateMany({}, { $rename: { "country": "Country" }});

{ "acknowledged" : true, "matchedCount" : 4, "modifiedCount" : 4 }
```



**Task 2**

Även här ska ni skriva både satserna och vad mongo svarar (resultat). Ladda ned animals.js

från ITHS Distans.

\1. Skapa en databas vid namn animalsdb.

\2. Gå till databasen animalsdb.

```java
> use animalsdb

switched to db animalsdb
```

\3. Skriv i mongo-skalet : load ("animals.js"). Vad gör den raden? Visa resultatet.

```java
load ("/Users/jannismuller/Documents/Jannis/It-högskolan/Javautvecklare 2020/Kurser/Utveckling mot databas och databasadministration/Labb 3- extra uppgift/animals.js")

true
```

\4. Hitta alla data från collection animals.

```java
> db.animals.find().pretty()
```

\5. Hitta hur många djur det finns totalt i databasen animals?

```java
> db.animals.count()
16
  
or:  

> db.animals.find().count()
16
```

\6. Lägg till en array “favorit_food” med “blueberry, honey och ants” för “Bear” .

```java
> db.animals.updateMany( {name: "Bear"}, { $set: { favorit_food: ["blueberry", "honey", "ants"] }});

{ "acknowledged" : true, "matchedCount" : 1, "modifiedCount" : 1 }
```

\7. Ta bort ants och honey från arrayen “favorit_food” och “rounded ears” från arrayen “data” för “Bear”.

```java
> db.animals.update( {name: "Bear"}, { $pull: { favorit_food: { $in: ["ants", "honey"] }, data: "rounded ears"}});

WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
```

\8. Hitta hur många gula djur det finns i kollektionen.

```java
> db.animals.find({color: "yellow"});

{ "_id" : ObjectId("60004c8dd6c276661ca12d4b"), "name" : "Meerkat", "found_in" : [ "Botswana", "Namibia", "Angola", "South Africa" ], "color" : "yellow", "family" : "Herpestidae", "suborder" : "Feliformia", "order" : "Carnivora", "weight" : 1, "data" : [ "can stand on its rear legs", "live in groups" ] }

{ "_id" : ObjectId("60004c8dd6c276661ca12d4c"), "name" : "Lion", "color" : "yellow", "weight" : 200, "class" : "Mammalia", "order" : "Carnivora", "family" : "Felidae", "subfamily" : "Pantherinae", "suborder" : "Feliformia", "data" : [ "live in groups", "carnivore", "smaller than tigers", "protractible claws" ] }
```

\9. Lägg till fältet “litter_size” med värdet “4-6-kits” för “Fox”.

```java
> db.animals.update( {name: "Fox"}, { $set: { litter_size: "4-6" } });

WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
```

\10. Lägg till ett fält med namn “likes meat” i arrayen “data” för alla med "order": "Carnivora".

```java
> db.animals.updateMany( {order: "Carnivora"}, { $addToSet: { data: "likes meat" } });

{ "acknowledged" : true, "matchedCount" : 10, "modifiedCount" : 10 }
```

\11. Gruppera efter färger och visa antal djur per färg.

```java
> db.animals.aggregate({$group: {_id: "$color", count: {$sum: 1 }}});

{ "_id" : "grey", "count" : 3 }
{ "_id" : "orange", "count" : 2 }
{ "_id" : "brown", "count" : 5 }
{ "_id" : "yellow", "count" : 2 }
{ "_id" : "gray", "count" : 1 }
{ "_id" : "black", "count" : 3 }
```

\12. Skriv ut antalet djur som väger mer än 100.

```java
> db.animals.find({ weight: { $gt: 100 }}).pretty();
```

\13. Skriv ut djur med “order” “Carnivora”, sorterat efter weight i fallande ordning.

```java
> db.animals.find({ order: "Carnivora"}).sort({weight: -1} ).pretty();
```

\14. Hitta alla dokument som har “mammal” eller "carnivore" i deras “data” fält.

```java
> db.animals.find( {data: {$in: ["mammal","carnivore"] }}).pretty();
```



**Task 3**

\1. Sätt in följande record i kollektion orders:

```java
> db.orders.insertMany([{  "_id": 100, "Name": "Eva", "Subscriber": true, "Payment": { "Type": "Credit-Card", "Total": 400, "Success": true }, "Note": "1st Complete Record" },                          { "_id": 101, "Name": "Johan", "Subscriber": true, "Payment": { "Type": "Debit-Card", "Total": 500, "Success": true }, "Note":null },                          { "_id": 102, "Name": "Matilda", "Subscriber": true, "Payment": { "Type": "Credit-Card", "Total": 700, "Success": false } },                          { "_id": 103, "Name": "Mikael", "Subscriber": false, "Payment": null, "Note": "Payment is Null" },                          { "_id": 104, "Name": "Rikard", "Subscriber": false, "Payment": { "Type": "Debit-Card", "Total": 300, "Success": false }, "Note": "Completed Payment" }]);
{
	"acknowledged" : true,
	"insertedIds" : [
		100,
		101,
		102,
		103,
		104
	]
}


```

\2. Hitta alla dokument där fältet “Note” är “null” eller saknas .

```java
> db.orders.find( {Note: null})
  
{ "_id" : 101, "Name" : "Johan", "Subscriber" : true, "Payment" : { "Type" : "Debit-Card", "Total" : 500, "Success" : true }, "Note" : null }
{ "_id" : 102, "Name" : "Matilda", "Subscriber" : true, "Payment" : { "Type" : "Credit-Card", "Total" : 700, "Success" : false } }
> 
```

\3. Hitta alla dokument där fältet “Note” saknas.

```java
> db.orders.find( {Note: {$exists:false}});

{ "_id" : 102, "Name" : "Matilda", "Subscriber" : true, "Payment" : { "Type" : "Credit-Card", "Total" : 700, "Success" : false } }
```

\4. Hitta alla dokument som har "Type": "Credit-Card" sorterat efter “Total” i stigande

ordning.

```java
> db.orders.find({"Payment.Type": "Credit-Card"}).sort({Total:1});

{ "_id" : 100, "Name" : "Eva", "Subscriber" : true, "Payment" : { "Type" : "Credit-Card", "Total" : 400, "Success" : true }, "Note" : "1st Complete Record" }

{ "_id" : 102, "Name" : "Matilda", "Subscriber" : true, "Payment" : { "Type" : "Credit-Card", "Total" : 700, "Success" : false } }
```

\5. Sortera all dokument efter Total i fallande ordning och visa bara dokument 2 och 3 (från

resultatet).

```java
> db.orders.find({_id: {$in: [102,103]}}).sort({Total: -1});

{ "_id" : 102, "Name" : "Matilda", "Subscriber" : true, "Payment" : { "Type" : "Credit-Card", "Total" : 700, "Success" : false } }

{ "_id" : 103, "Name" : "Mikael", "Subscriber" : false, "Payment" : null, "Note" : "Payment is Null" }
```

\6. Aggregera “Success” och visa antal i fallande ordning. (Hur många “Success” är “true”

och hur många är “false”).

```java
> db.orders.aggregate({$group: {_Sucesss: "$Payment.Success", count: {$sum: 1 }}}, {$sort: {count: -1}} );

{ "_id" : true, "count" : 2 }
{ "_id" : false, "count" : 2 }
{ "_id" : null, "count" : 1 }

```

VG-uppgiften

Skriv en Java klass (ni bestämmer själva vad den ska hetta).

Ert program ska:

Ansluta till mongodb (skapa en klient)

```java
private MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");

//connecting to local mongodb and displays all available database
public RestaurantDaoJBCDImpl() {
        List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
        databases.forEach(db -> System.out.println(db.toJson()));
}
```

Skapa en ny databas via klienten till exempel “lab3”

Skapa en kollektion vid namn “restaurants”

```java
//connects to specific Database and collection
@Override
public MongoCollection<Document> connectionToCollection(String nameDb, String collection) {
    MongoDatabase mongoDatabaseDB = mongoClient.getDatabase(nameDb);
    MongoCollection<Document> mongoCollection = mongoDatabaseDB.getCollection(collection);

    return mongoCollection;
    }
```

```java
Main:
//creates a new connection
RestaurantDao restaurantDao = new RestaurantDaoJBCDImpl();

MongoCollection <Document> restaurantCollection = restaurantDao.connectionToCollection(
        "restaurantdb", "restaurant");
```

Skriv en metod som skriver ut (Console.Writeline) alla dokument i samlingen.

```java
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
```

```java
Main:
//print out of all documents in the collection "restaurants"
restaurantDao.printWholeCollection(restaurantCollection);

------------
Output Consol:

List of all documents in collection com.mongodb.client.internal.MongoCollectionImpl@1018bde2
{"_id": {"$oid": "60053a0abbf9fe4299852976"}, "name": "Sun Bakery Trattoria", "stars": 4, "categories": ["Pizza", "Pasta", "Italian", "Coffee", "Sandwiches"]}
{"_id": {"$oid": "60053a0abbf9fe4299852977"}, "name": "Blue Bagels Grill", "stars": 3, "categories": ["Bagels", "Cookies", "Sandwiches"]}
{"_id": {"$oid": "60053a0abbf9fe4299852978"}, "name": "Hot Bakery Cafe", "stars": 4, "categories": ["Bakery", "Cafe", "Coffee", "Dessert"]}
```

Skriv en metod som skriver ut namnet på alla dokument som har kategorin “Cafe”

OBS! Exkludera id så att bara namn visas

```java
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
```

--------------
```java
Main:
//list of Documents with categories: Sandwiches
restaurantDao.findDocumentsWithFilter(restaurantCollection,"categories",
                                      "Sandwiches","name");
---------------
Output Consol:
Documents with categories: Sandwiches
{"name": "Sun Bakery Trattoria"}
{"name": "Blue Bagels Grill"}


```

Skriv en metod som uppdaterar genom increment “stars” för den restaurang som har “name”

“XYZ Coffee Bar” så att nya värdet på stars blir 6.

OBS! Ni ska använda increment .

OBS! Skriv ut alla restauranger igen, så att jag kan se att “stars” blivit 6, för denna restaurang

när jag kör ert program.

```java
 @Override
    public void findOneAndUpdateWithIncrement(MongoCollection<Document> collection,
                                              String field, String nameOfRestaurant,																							      String fieldToIncrement, int inc) {

        Bson filter = eq(field, nameOfRestaurant);
        Bson incUpdate = inc(fieldToIncrement,inc);
        collection.findOneAndUpdate(filter,incUpdate);
    }
}
```

Skriv en metod som uppdaterar “name” för "456 Cookies Shop" till “123 Cookies Heaven”

OBS! Skriv ut alla restauranger igen, så att jag kan se att namnet ändrats för denna restaurang

när jag kör ert program.

```java
@Override
public void findOneAndUpdateOneChangeNameOfRestaurant(MongoCollection<Document> collection, String field, String nameOfRestaurant, String newName) {
    Bson filter = eq(field, nameOfRestaurant);
    Bson updateOperation = set(field,newName);

    collection.updateOne(filter,updateOperation);
}
```

Skriv en metod som aggregerar en lista med alla restauranger som har 4 eller fler “stars” och

skriver ut endast “name” och “stars”


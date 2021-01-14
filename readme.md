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
> db.cities.insertMany( [
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
{name: "Göteborg"},
{ $set: 
{ population: 549890 }
});

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

\8. Byt namn på fältet “country” till “Country”.



Task 2

Även här ska ni skriva både satserna och vad mongo svarar (resultat). Ladda ned animals.js

från ITHS Distans.

\1. Skapa en databas vid namn animalsdb.

\2. Gå till databasen animalsdb.

\3. Skriv i mongo-skalet : load ("animals.js"). Vad gör den raden? Visa resultatet.

\4. Hitta alla data från collection animals.

\5. Hitta hur många djur det finns totalt i databasen animals?

\6. Lägg till en array “favorit_food” med “blueberry, honey och ants” för “Bear” .

\7. Ta bort ants och honey från arrayen “favorit_food” och “rounded ears” från arrayen

“data” för “Bear”.

\8. Hitta hur många gula djur det finns i kollektionen.

\9. Hitta alla namnet på alla djur vars färg är brun och som lever i Asien och visa deras

namn, color och found_in .

\10. Lägg till fältet “litter_size” med värdet “4-6-kits” för “Fox”.

\11. Lägg till ett fält med namn “likes meat” i arrayen “data” för alla med "order": "Carnivora"

och som är gula till färgen.

\12. Gruppera efter färger och visa antal djur per färg.

\13. Skriv ut antalet djur som väger mer än 100.

\14. Skriv ut djur med “order” “Carnivora”, sorterat efter weight i fallande ordning.

\15. Hitta alla dokument som har “mammal” eller "carnivore" i deras “data” fält.

\16. Hitta alla dokument som har “mammal” och "carnivore" i deras “data” fält

Task 3

Även här ska ni skriva både satserna och vad mongo svarar (resultat).

\1. Sätt in följande record i kollektion orders:

{

"Id": 100,

"Name": "Eva",

"Subscriber": true,

"Payment": {

"Type": "Credit-Card",

"Total": 400,

"Success": true

},

"Note": "1st Complete Record"

},

{

"Id": 101,

"Name": "Johan",

"Subscriber": true,

"Payment": {

"Type": "Debit-Card",

"Total": 500,

"Success": true

},

"Note":null

},

{

"Id": 102,

"Name": "Matilda",

"Subscriber": true,

"Payment": {

"Type": "Credit-Card",

"Total": 700,

"Success": false

}

},

{

"Id": 103,

"Name": "Mikael",

"Subscriber": false,

"Payment": null,

"Note": "Payment is Null"

},

{

"Id": 104,

"Name": "Rikard",

"Subscriber": false,

"Payment": {

"Type": "Debit-Card",

"Total": 300,

"Success": false

},

"Note": "Completed Payment"

}

\2. Hitta alla dokument där fältet “Note” är “null” eller saknas .

\3. Hitta alla dokument där fältet “Note” saknas.

\4. Hitta alla dokument som har "Type": "Credit-Card" sorterat efter “Total” i stigande

ordning.

\5. Sortera all dokument efter Total i fallande ordning och visa bara dokument 2 och 3 (från

resultatet).

\6. Aggregera “Success” och visa antal i fallande ordning. (Hur många “Success” är “true”

och hur många är “false”).

Exempel på hur svarsfilen för G-uppgiften ska se ut, formmässigt

Namn: Johan Mongosson Task 1. Fråga 1.

Skapa en databas vid namn fruits

\> <<här skriver du ditt mongo-kommand>>

Switched to db fruits ← Detta är vad mongodb svarar! Skall vara med.

Fråga 2. Skapa en collection vid namn “citrus”

\> <<här skriver du ditt mongo-kommando>>

{ "ok" : 1 } ← Detta är vad mongodb svarar, det ska du ta med i din fil.

Fråga 3. Hitta alla dokument i collection “citrus”

\> <<här skriver du ditt mongo-kommando>>

{ "_id" : 1, "name" : "orange" } { "_id" : 2, "name" : "lemon" } osv...



VG-uppgiften

Skriv en C# klass (ni bestämmer själva vad den ska hetta).

Ni kan använda lämpliga MongoDB paket ifrån NuGet

Ert program ska:

Ansluta till mongodb (skapa en klient)

Skapa en ny databas via klienten till exempel “lab3”

```
Se filer 
```


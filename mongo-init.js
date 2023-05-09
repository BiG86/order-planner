db = db.getSiblingDB("orderplanner");

db.depot.insert({
    "_id": ObjectId("639b2df6493f8d387f641586"),
    "_class": "it.snorcini.dev.orderplanner.entity.Depot",
    "name": "base1",
    "latitude": Long("45.4384958"),
    "longitude": Long("10.9924122")
});

db.order.insert({
    "_id": ObjectId("639b2e45493f8d387f641587"),
    "_class": "it.snorcini.dev.orderplanner.entity.Order",
    "packages": [
    {
        "description": "TestBox1",
        "destination":  {
            "latitude": Long("48.4384978"),
            "longitude": Long("9.9925123")
        }
    },
    {
        "description": "TestBox2",
        "destination":  {
            "latitude": Long("50.4383978"),
            "longitude": Long("11.9725143")
        }
    }
    ]
});
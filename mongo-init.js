db = db.getSiblingDB("orderplanner");

db.depot.insert({
    "_id": ObjectId("639b2df6493f8d387f641586"),
    "_class": "it.snorcini.dev.orderplanner.entity.Depot",
    "name": "base1",
    "latitude": 45.4384958,
    "longitude": 10.9924122
});

db.order.insert({
    "_id": ObjectId("639b2e45493f8d387f641587"),
    "_class": "it.snorcini.dev.orderplanner.entity.Order",
    "status": "INITIAL",
    "packages": [
    {
        "description": "TestBox1",
        "destination":  {
            "latitude": 48.4384978,
            "longitude": 9.9925123
        }
    },
    {
        "description": "TestBox2",
        "destination":  {
            "latitude": 50.4383978,
            "longitude": 11.9725143
        }
    }
    ]
});
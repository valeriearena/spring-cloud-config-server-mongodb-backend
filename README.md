# Spring Cloud Configuration

Spring cloud app that integrates with MongoDB.


http://localhost:8888/ModuleA/prod
http://localhost:8888/ModuleA-{shared1^shared2}/default

curl -X POST http://localhost:8080/actuator/refresh
curl -X POST http://localhost:8090/actuator/refresh

http://localhost:8080/modulea
http://localhost:8090/moduleb

curl -v -X PATCH http://localhost:8888/settings -H "Content-Type: application/json" -d '{"moduleName":"system","key":"shared1","value":"System shared1","settingLabel":"Shared Setting 1","description":"This is shared setting #1.","type":"java.lang.String","order":1}'

curl -v -X PATCH http://localhost:8888/settings -H "Content-Type: application/json" -d '{"moduleName": "configclienta","key":"setting1","value":"Module A Updated setting1","settingLabel":"Module A Setting 1","description":"This is Module A setting #1.","type":"java.lang.String","order":1}'

curl -v -X PATCH http://localhost:8888/settings -H "Content-Type: application/json" -d '{"moduleName": "configclientb","key":"setting1","value":"Module B Updated setting1","settingLabel":"Module B Setting 1","description":"This is Module B setting #1.","type":"java.lang.String","order":1}'
____________________________________

db.settings.insertOne({"moduleName":"system","key":"shared1","value":"System shared1","settingLabel":"Shared Setting 1","description":"This is shared setting #1.","type":"java.lang.String","order":1,"status":0})
db.settings.insertOne({"moduleName":"system","key":"shared2","value":"System shared2","settingLabel":"Shared Setting 2","description":"This is shared setting #2.","type":"java.lang.String","order":2,"status":0})

db.settings.insertOne({"moduleName": "configclienta","key":"setting1","value":"Module A setting1","settingLabel":"Module A Setting 1","description":"This is Module A setting #1.","type":"java.lang.String","order":1,"status":0})
db.settings.insertOne({"moduleName": "configclienta","key":"setting2","value":"Module A setting2","settingLabel":"Module A Setting 2","description":"This is Module A setting #2.","type":"java.lang.String","order":2,"status":0})

db.settings.insertOne({"moduleName": "configclientb","key":"setting1","value":"Module B setting1","settingLabel":"Module B Setting 1","description":"This is Module B setting #1.","type":"java.lang.String","order":1,"status":0})
db.settings.insertOne({"moduleName": "configclientb","key":"setting2","value":"Module B setting2","settingLabel":"Module B Setting 2","description":"This is Module B setting #2.","type":"java.lang.String","order":2,"status":0})
____________________________________

db.settings.find()
____________________________________

db.settings.update({"moduleName" : "System", "key": "shared1"},{$set:{"value":"System Shared Setting 1","status":0}});
db.settings.update({"moduleName" : "System", "key": "shared2"},{$set:{"value":"System Shared Setting 2","status":0}});

db.settings.update({"moduleName" : "configclienta", "key": "setting1"},{$set:{"value":"Module A Setting 1","status":0}});
db.settings.update({"moduleName" : "configclienta", "key": "setting2"},{$set:{"value":"Module A Setting 2","status":0}});

db.settings.update({"moduleName" : "configclientb", "key": "setting1"},{$set:{"value":"Module B Setting 1","status":0}});
db.settings.update({"moduleName" : "configclientb", "key": "setting2"},{$set:{"value":"Module B Setting 2","status":0}});

____________________________________

db.settings.find( { moduleName: { $eq: "ModuleA" }, "key": { $eq: "setting1" }} )
db.settings.find( { status: { $eq: 0 }} )

db.settings.aggregate([{ $match: {"status": 0}},{$group:{_id: "$moduleName",count: {$sum: 1}}}]);
db.settings.aggregate([{ $match: {"status": 0}},{$group:{_id: "$moduleName"}}]);

____________________________________

docker build -t server2 -f Dockerfile_Debug .
docker run -p 8888:8888 -t configserver --name configserver
http://varena.configserver.5g76798n52.eus.azds.io/ModuleA/prod

____________________________________


https://stackoverflow.com/questions/47014000/service-bus-multiple-listener-instances-with-same-subscription-not-receiving-mes
https://stackoverflow.com/questions/21199333/azure-service-bus-subscribers-can-independently-subscribe-to-a-subscription-an
https://www.serverless360.com/blog/azure-service-bus-topics-vs-event-grid
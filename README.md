Couchbase View Transferer - cb-view-transfer
============================================
`cb-view-transfer` transfers your Couchbase __map-reduce__ and __spatial__ views from one node to another. 

Usage
-----
```
java -jar cb-view-transfer.jar -buckets <comma-separated-args> -source <arg> -target <arg> -username <arg> -password <arg>
```
**-buckets**: Couchbase buckets that contains views that you want to transfer to target node from source node.

**-source**: Couchbase source node that contains views that you want to transfer.

**-target**: Couchbase target node that you want to transfer views to.

**-username**: target node web admin username.

**-password**: target node web admin password.

example usage:
```
java -jar cb-view-transfer.jar -buckets Customers,Orders -source http://172.10.12.45:8091/pools -target http://127.0.0.1:8091/pools -username Administrator -password 123456
```

Building
--------
To build the project, following packaging command might be used on root directory of the project.
```
mvn clean package
```
It generates a jar file named `cb-view-transfer.jar` in project's `target/` directory. 

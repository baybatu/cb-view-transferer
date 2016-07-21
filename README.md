Couchbase View Transferer - cb-view-transfer
============================================
`cb-view-transfer` transfers your Couchbase __map-reduce__ and __spatial__ views from one node or file to target node or file. 

Usage
-----
```
java -jar cb-view-transfer.jar -buckets <comma-separated-args> -source <arg> -target <arg> -username <arg> -password <arg>
```
**-buckets**: Couchbase buckets that contains views that you want to transfer to target from source.

**-source**: Couchbase node or XML file that contains views that you want to transfer. There is no need to add `/pools` path to end of node address.

**-target**: Couchbase node or XML file that you want to transfer views to. There is no need to add `/pools` path to end of node address.

**-username**: Node web admin username. It is required if Couchbase view information needed to be fetched from node.

**-password**: Node web admin password. It is required if Couchbase view information needed to be fetched from node.


Examples
--------

__To move views from node to XML file__

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

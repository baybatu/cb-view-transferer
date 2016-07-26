Couchbase View Transferer - cb-view-transfer
============================================
`cb-view-transfer` transfers your Couchbase __map-reduce__ and __spatial__ views from one node or XML file to target node or XML file. 

XML file contains *map-reduce* functions and description of each view. You can examine sample view xml file [sample_cb_views.xml](https://github.com/baybatu/cb-view-transferer/blob/master/src/main/resources/sample_cb_views.xml) 
in `resources/` directory in source code.

`cb-view-transfer` lets you to:

* document your Couchbase buckets and views with their *map-reduce* (*map* for spatial views) functions in XML file.
* store the XML file in your version control system such as git. 
* restore views with corresponding *map-reduce* (*map* for spatial views) functions to your Couchbase cluster that you want to have those views.
* transfer your views between Couchbase environments(dev, test, QA) easily.

Download
--------
You can download latest version of the jar file from [here](https://github.com/baybatu/cb-view-transferer/releases/latest)

Usage
-----
```
java -jar cb-view-transfer.jar -source <arg> -target <arg> [-buckets <comma-separated-args>] [-username <arg>] [-password <arg>]
```
**-source**: Couchbase node or XML file that contains views that you want to transfer. 
There is no need to add `/pools` path to end of node address.

**-target**: Couchbase node or XML file that you want to transfer views to. 
There is no need to add `/pools` path to end of node address.

**-buckets**: Optional. Couchbase buckets that contain views that you want to transfer to target from source.
If you intend to transfer all views, then no need to specify `buckets` parameter.

**-username**: Web admin username. Required if `source` parameter points to Couchbase node instead of XML file.

**-password**: Web admin password. Required if `source` parameter points to Couchbase node instead of XML file.


Examples
--------

__To move all views from one node to another node__

```
java -jar cb-view-transfer.jar -source http://172.10.12.45:8091/pools -target http://176.123.100.99:8091/pools -username Administrator -password 123456
```

__To move all views from XML file to node__

```
java -jar cb-view-transfer.jar -source /Users/berbat/myViewFile.xml -target http://176.123.100.99:8091/pools -username Administrator -password 123456
```

__To move all views from node to XML file__

```
java -jar cb-view-transfer.jar -source http://172.10.12.45:8091/pools -target /Users/berbat/myViewFile.xml -username Administrator -password 123456
```

__To move views in "Customers" and "Orders" buckets from node to another node__

```
java -jar cb-view-transfer.jar -buckets Customers,Orders -source http://172.10.12.45:8091/pools -target http://176.123.100.99:8091/pools -username Administrator -password 123456
```

__To move views in "Customers" and "Orders" buckets from XML file to node__

Since *source* is a plain XML file, we don't need to add `username` and `password` parameters. 

```
java -jar cb-view-transfer.jar -buckets Customers,Orders -source /Users/berbat/myViewFile.xml -target http://176.123.100.99:8091/pools
```

__To move views in "Customers" and "Orders" buckets from node to XML file__

```
java -jar cb-view-transfer.jar -buckets Customers,Orders -source http://172.10.12.45:8091/pools -target /Users/berbat/myViewFile.xml -username Administrator -password 123456
```

Building
--------
To build the project, following packaging command might be used on root directory of the project. 
It requires at least Java8 to build.
```
mvn clean package
```
It generates a jar file named `cb-view-transfer.jar` in project's `target/` directory. 

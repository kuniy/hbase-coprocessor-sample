Deployment - RegionObserverCount
-----------------------------------

1. region_observer_count.jar

maprfs:///user/root/coprocessor/region_observer_count.jar

2. run UserActionHTable.java

# java UserActionHTable <hbase-site.xml>

hbase> describe 'USERACTION'
DESCRIPTION
 {NAME => 'USERACTION', DEFERRED_LOG_FLUSH => 'false', MAX_FILESIZE => '268435456',
 READONLY => 'false', MEMSTORE_FLUSHSIZE => '67108864',
 FAMILIES => [{NAME => 'EXEC', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
 VERSIONS => '1', COMPRESSION => 'NONE', MIN_VERSIONS => '0', TTL => '2147483647', COMPRESSION_COMPACT => 'NONE',
 BLOCKSIZE => '65536', IN_MEMORY => 'false', BLOCKCACHE=> 'true'}]}

hbase(main)> describe 'USERACTION'
DESCRIPTION
 {NAME => 'USERACTION',
 COPROCESSOR$1 => 'maprfs:///user/root/coprocessor/region_observer_count.jar|jp.gr.java_conf.kuniy.hbase.coprocessor.sample.count.RegionObserverCount|USER',
 DEFERRED_LOG_FLUSH => 'false', MAX_FILESIZE => '268435456',
 READONLY => 'false', MEMSTORE_FLUSHSIZE => '67108864',
 FAMILIES => [{NAME => 'EXEC', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
 VERSIONS => '1', COMPRESSION => 'NONE', MIN_VERSIONS => '0', TTL => '2147483647', COMPRESSION_COMPACT => 'NONE',
 BLOCKSIZE => '65536', IN_MEMORY => 'false', BLOCKCACHE => 'true'}]}



TableNotEnabledException/TableNotDisabledException
--------------------------------------------------

hbase> enable 'USERACTION'

ERROR: org.apache.hadoop.hbase.TableNotDisabledException: org.apache.hadoop.hbase.TableNotDisabledException: USERACTION

Here is some help for this command:
Start enable of named table: e.g. "hbase> enable 't1'"


hbase> disable 'USERACTION'

ERROR: org.apache.hadoop.hbase.TableNotEnabledException: org.apache.hadoop.hbase.TableNotEnabledException: USERACTION

Here is some help for this command:
Start disable of named table: e.g. "hbase> disable 't1'"


# hbase zkcli
Connecting to XXX.XXX.XXX:5181
13/02/16 06:40:39 INFO zookeeper.ZooKeeper: Client environment:zookeeper.version=3.3.2--1, built on 08/30/2011 17:53 GMT
...
Welcome to ZooKeeper!
[zk:] ls /hbase/table
[USERACTION]
[zk:] ls /hbase/table/USERACTION
[]
[zk:] delete /hbase/table/USERACTION
[zk:] ls /hbase/table/USERACTION
Node does not exist: /hbase/table/USERACTION
[zk:] quit
Quitting...
13/02/16 06:41:45 INFO zookeeper.ZooKeeper: Session: 0x13cdea3cb550093 closed
13/02/16 06:41:45 INFO zookeeper.ClientCnxn: EventThread shut down

hbase> describe 'USERACTION'
DESCRIPTION                                                                         ENABLED
 {NAME => 'USERACTION', COPROCESSOR$1 => 'maprfs:///user/root/coprocessor/region_ob true
 server_count.jar|jp.gr.java_conf.kuniy.hbase.coprocessor.sample.count.RegionObserv
 erCount|USER', DEFERRED_LOG_FLUSH => 'false', MEMSTORE_FLUSHSIZE => '67108864', RE
 ADONLY => 'false', MAX_FILESIZE => '268435456', FAMILIES => [{NAME => 'EXEC', BLOO
 MFILTER => 'ROW', REPLICATION_SCOPE => '0', COMPRESSION => 'NONE', VERSIONS => '1'
 , TTL => '2147483647', MIN_VERSIONS => '0', COMPRESSION_COMPACT => 'NONE', BLOCKSI
 ZE => '65536', IN_MEMORY => 'false', BLOCKCACHE => 'true'}]}
1 row(s) in 0.1510 seconds


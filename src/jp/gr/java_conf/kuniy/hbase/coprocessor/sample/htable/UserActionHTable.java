package jp.gr.java_conf.kuniy.hbase.coprocessor.sample.htable;

import java.io.IOException;

import jp.gr.java_conf.kuniy.hbase.coprocessor.sample.count.RegionObserverCount;
import jp.gr.java_conf.kuniy.hbase.coprocessor.sample.util.HTableUtil;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

public class UserActionHTable {

	private static final String USER_ACTION_TABLE_NAME = "USERACTION";

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java CoprocessorHTable <hbase-site.xml path>");
		}
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path(args[0]));

		try {
			HTableDescriptor table = HTableUtil.getHTable(conf, "USERACTION");
			String jarpath = "file:/opt/region_observer_count.jar";
			table.setValue("COPROCESSOR$1", jarpath + "|" + RegionObserverCount.class.getCanonicalName() + "|" + "1");

			HBaseAdmin admin = new HBaseAdmin(conf);
			admin.disableTable(USER_ACTION_TABLE_NAME);
			admin.modifyTable(Bytes.toBytes(USER_ACTION_TABLE_NAME), table);
			admin.enableTable(USER_ACTION_TABLE_NAME);
			admin.close();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

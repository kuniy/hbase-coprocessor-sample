package jp.gr.java_conf.kuniy.hbase.coprocessor.sample.htable;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;

import jp.gr.java_conf.kuniy.hbase.coprocessor.sample.util.HTableUtil;


public class CoprocessorHTable {

	private static final String COPROCESSOR_TABLE_NAME = "COPROCESSOR";

	private static final String COPROCESSOR_FAMILY_NAME = "COPROCESSOR";

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java CoprocessorHTable <hbase-site.xml path>");
		}
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path(args[0]));

		try {
			if (! HTableUtil.exist(conf, COPROCESSOR_TABLE_NAME)) {
				HTableUtil.createHTable(conf, COPROCESSOR_TABLE_NAME, COPROCESSOR_FAMILY_NAME);
				HTableUtil.showHTableAttributes(conf, COPROCESSOR_TABLE_NAME);
			}
		}catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package jp.gr.java_conf.kuniy.hbase.coprocessor.sample.count;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

public class RegionObserverCount extends BaseRegionObserver {

	private static final byte[] COUNT_TABLE_NAME = Bytes.toBytes("COPROCESSOR");

	private static final byte[] COUNT_FAMILY_NAME = Bytes.toBytes("COPROCESSOR");

	private static final byte[] COUNT_COL_NAME = Bytes.toBytes("COUNT");

	@Override
	public void postPut(ObserverContext<RegionCoprocessorEnvironment> e,
			Put put, WALEdit edit, boolean writeToWAL) throws IOException {
		RegionCoprocessorEnvironment env = e.getEnvironment();
		byte[] rowkey = env.getRegion().getTableDesc().getName();

		HTableInterface table = env.getTable(COUNT_TABLE_NAME);
		table.incrementColumnValue(rowkey, COUNT_FAMILY_NAME, COUNT_COL_NAME, 1);
	}

}

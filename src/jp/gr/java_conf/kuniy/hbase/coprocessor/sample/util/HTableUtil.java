package jp.gr.java_conf.kuniy.hbase.coprocessor.sample.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.hfile.Compression;
import org.apache.hadoop.hbase.io.hfile.Compression.Algorithm;
import org.apache.hadoop.hbase.regionserver.StoreFile;
import org.apache.hadoop.hbase.regionserver.StoreFile.BloomType;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HTable Utility Class (thread unsafe)
 */
public class HTableUtil {

	// HTable Default Settings
	public static final long MAX_FILE_SIZE = 268435456;
	public static final boolean IS_READONLY = false;
	public static final long MEMSTORE_FLUSH_SIZE = 67108864;
	public static final boolean DEFERRED_LOG_FLUSH = false;

	// HColumn Default Settings
	public static final int MAX_VERSIONS = 1;  // default 3
	public static final Compression.Algorithm COMPSESSION = Algorithm.NONE;
	public static final Compression.Algorithm  COMPACTION_COMPSESSION = Algorithm.NONE;
	public static final int BLOCK_SIZE = 65536;
	public static final boolean IS_BLOCK_CACHE_ENABLED = true;
	public static final int TIME_TO_LIVE = 2147483647;
	public static final boolean IS_INMEMORY = false;
	public static final StoreFile.BloomType BLOOM_FILTER_TYPE = BloomType.ROW; // default none
	public static final int REPLICATION_SCOPE = 0;

	/**
	 * Create HTable
	 *
	 * @param conf org.apache.hadoop.conf.Configuration
	 * @param tableName String
	 * @param familyName String
	 * @return isTableAvailable boolean
	 * @throws ZooKeeperConnectionException
	 * @throws MasterNotRunningException
	 * @throws IOException
	 */
	public static boolean createHTable (Configuration conf, String tableName, String familyName)
			throws ZooKeeperConnectionException, MasterNotRunningException, IOException, IllegalArgumentException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.isTableAvailable(tableName)) {
			admin.close();
			return true;
		}

		HTableDescriptor.isLegalTableName(Bytes.toBytes(tableName));
		HColumnDescriptor.isLegalFamilyName(Bytes.toBytes(familyName));

		HTableDescriptor desc = new HTableDescriptor(tableName);
		desc.setMaxFileSize(MAX_FILE_SIZE);
		desc.setReadOnly(IS_READONLY);
		desc.setMemStoreFlushSize(MEMSTORE_FLUSH_SIZE);
		desc.setDeferredLogFlush(DEFERRED_LOG_FLUSH);

		HColumnDescriptor family = new HColumnDescriptor(familyName);
		family.setMaxVersions(MAX_VERSIONS);
		family.setCompressionType(COMPSESSION);
		family.setCompactionCompressionType(COMPACTION_COMPSESSION);
		family.setBlocksize(BLOCK_SIZE);
		family.setBlockCacheEnabled(IS_BLOCK_CACHE_ENABLED);
		family.setTimeToLive(TIME_TO_LIVE);
		family.setInMemory(IS_INMEMORY);
		family.setBloomFilterType(BLOOM_FILTER_TYPE);
		desc.addFamily(family);
		admin.createTable(desc);

		boolean isTableAvailable = admin.isTableAvailable(tableName);
		admin.close();
		return isTableAvailable;
	}

	/**
	 * Check HTable
	 *
	 * @param conf org.apache.hadoop.conf.Configuration
	 * @param tableName String
	 * @return isTableAvailable boolean
	 * @throws IOException
	 */
	public static boolean exist(Configuration conf, String tableName) throws IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		boolean isTableAvailable = admin.isTableAvailable(tableName);
		admin.close();
		return isTableAvailable;
	}

	/**
	 * Get HTable
	 *
	 * @param conf org.apache.hadoop.conf.Configuration
	 * @param tableName String
	 * @return HTableDescriptor
	 * @throws IOException
	 */
	public static HTableDescriptor getHTable(Configuration conf, String tableName) throws IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor table = admin.getTableDescriptor(Bytes.toBytes(tableName));
		admin.close();
		return table;
	}

	/**
	 * Get HTable List
	 *
	 * @param conf org.apache.hadoop.conf.Configuration
	 * @return tableList List<String>
	 * @throws IOException
	 */
	public static List<String> list(Configuration conf) throws IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		List<String> tableList = new ArrayList<String>();
		for (HTableDescriptor desc : admin.listTables()) {
			tableList.add(desc.getNameAsString());
		}
		admin.close();
		return tableList;
	}


	/**
	 * Show HTable Attributes
	 *
	 * @param conf org.apache.hadoop.conf.Configuration
	 * @param tableName String
	 * @throws IOException
	 */
	public static void showHTableAttributes (Configuration conf, String tableName) throws IOException {
		HTable table = new HTable(conf, tableName);
		HTableDescriptor desc = table.getTableDescriptor();
		System.out.println("TABLE NAME                : " + desc.getNameAsString());
		System.out.println("TABLE MAX_FILE_SIZE       : " + desc.getMaxFileSize());
		System.out.println("TABLE IS_READONLY         : " + desc.isReadOnly());
		System.out.println("TABLE MEMSTORE_FLUSH_SIZE : " + desc.getMemStoreFlushSize());
		System.out.println("TABLE DEFERRED_LOG_FLUSH  : " + desc.isDeferredLogFlush());

		for (HColumnDescriptor family : desc.getColumnFamilies()) {
			System.out.println("CF NAME                   : " + family.getNameAsString());
			System.out.println("CF MAX_VERSIONS           : " + family.getMaxVersions());
			System.out.println("CF COMPSESSION            : " + family.getCompression().getName());
			System.out.println("CF COMPACTION_COMPSESSION : " + family.getCompactionCompression().getName());
			System.out.println("CF BLOCK_SIZE             : " + family.getBlocksize());
			System.out.println("CF IS_BLOCK_CACHE_ENABLED : " + family.isBlockCacheEnabled());
			System.out.println("CF TIME_TO_LIVE           : " + family.getTimeToLive());
			System.out.println("CF IS_INMEMORY            : " + family.isInMemory());
			System.out.println("CF BLOOM_FILTER_TYPE      : " + family.getBloomFilterType().name());
			System.out.println("CF REPLICATION_SCOPE      : " + family.getScope());
		}

		table.close();
	}

}

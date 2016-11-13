package com.lcc.util;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

/**
 * 完全实时搜索，只要数据库变动，立即更新索引
 * 近实时索引，当数据变动，先把索引保存在内存中，然后在一个统一的时间提交
 * 使用NRTManager(near-real-time)和SearcherManager
 * @author lcc
 *
 */
public class LuceneContext {
	private static LuceneContext luceneContext = null;
	private static String INDEX_DIR="G:/lucene/solr/home/data/index";
	private static Version VERSION = Version.LUCENE_35;
	private static Directory directory;
	private static MMSegAnalyzer analyzer;
	private static IndexWriter indexWriter;
	private static NRTManager nrtManager;
	private static SearcherManager searcherManager;
	
	private LuceneContext (){
		
	}
	
	/**
	 * 注意：使用searcherManager 的acquire（）方法来获取IndexSearcher
	 * @return
	 */
	public static IndexSearcher getSearcher(){
		try {
			return searcherManager.acquire();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static LuceneContext getContext(){
		if (luceneContext == null) {
			init();
			new LuceneContext();
		}
		return luceneContext;
	}
	
	private static void init(){
		try {
			directory = FSDirectory.open(new File(INDEX_DIR));
			String dicDir = LuceneContext.class.getResource("data").getPath();
			analyzer = new MMSegAnalyzer(dicDir);
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(VERSION, analyzer));
			//注意，只有在lucene3.5中才有的构造方法，要是以后运行有问题的话直接换成3.6
			TrackingIndexWriter trackingIndexWriter = new TrackingIndexWriter(indexWriter);
			nrtManager = new NRTManager(trackingIndexWriter,new SearcherFactory());
			
			NRTManagerReopenThread thread = new NRTManagerReopenThread(nrtManager,5.0,0.025);
			thread.setDaemon(true);
			thread.setName("NRTManager Reopen Thread");
			thread.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commitIndex(){
		try {
			indexWriter.commit();
			indexWriter.forceMerge(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

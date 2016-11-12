package com.lcc.util;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrServer;

public class SolrContext {
	private static final String url ="http:?/localhost:8080/solr";
	private static HttpSolrServer server=null;
	
	static{
		try {
			server = new HttpSolrServer(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SolrServer getServer(){
		return server;
	}
}

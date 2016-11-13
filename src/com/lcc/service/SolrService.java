package com.lcc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcc.bean.IndexField;
import com.lcc.bean.IndexModel;
import com.lcc.bean.Message;
import com.lcc.bean.PageBean;
import com.lcc.dao.MessageDao;
import com.lcc.util.IndexUtil;
import com.lcc.util.SolrContext;

@Service
public class SolrService {
	
	@Autowired
	private MessageDao messageDao;
	
	public void commitRamIndex(){
		try {
			SolrContext.getServer().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重构所有的索引
	 * 把数据库中的记录查出开， 构建IndexField添加到索引中
	 */
	public void updateReconstructorIndex(){
		try {
			//删除所有的索引
			SolrContext.getServer().deleteByQuery("*:*");
			//取出所有数据，构建IndexField
			List<Message> messageList = messageDao.findAll();
			indexMessageList(messageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为所有的数据构建索引
	 * @param messageList
	 */
	private void indexMessageList(List<Message> messageList){
		for(Message message:messageList){
			IndexField indexField = IndexUtil.message2IndexField(message);
			addIndex(indexField);
		}
	}
	
	/**
	 * 添加索引
	 * @param indexField
	 */
	public void addIndex(IndexField indexField){
		try {
			//1 添加索引到solr
			SolrServer solrServer = SolrContext.getServer();
			solrServer.addBean(indexField);
			//2 优化索引
			solrServer.optimize();
			//3提交索引
			SolrContext.getServer().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PageBean<IndexModel> findByIndex(String keyword,String field,Integer start,int pageSize) throws Exception{
		if (keyword == null) {
			keyword="*";
		}
		if (field == null) {
			field = "*";
		}
		
		SolrQuery solrQuery = new SolrQuery(field+":"+keyword);
		solrQuery.addSortField("last_modified", SolrQuery.ORDER.desc);
		//设置高亮
		solrQuery.setHighlight(true)
		.setHighlightSimplePre("<span class='highlighter'>")
		.setHighlightSimplePost("</span>")//设置高亮的样式
		.setParam("hl.fl", "title,content,description")//设置高亮的域
		.setStart(start)//设置分页
		.setRows(pageSize);
		
		//solrQuery是SolrParams的子类
		QueryResponse queryResponse = SolrContext.getServer().query(solrQuery);
		//第一个map的key是文档document的ID，第二个map的key'是要高亮显示的field
		Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		
		PageBean<IndexModel> page = new PageBean<IndexModel>
		(new Long(solrDocumentList.getNumFound()).intValue());
		
		page.setPageSize(pageSize);
		page.setStartRow(start);
		
		List<IndexModel> datas = new ArrayList<>();
		for(SolrDocument sd : solrDocumentList){
			String id = (String) sd.getFieldValue("id");
			
			String title = (String) sd.getFieldValue("title");
			String summary = (String) sd.getFieldValue("description");
			List<String> content = (List<String>) sd.getFieldValue("content");//multiValued="true"的field返回List
			IndexModel indexModel = new IndexModel();
			indexModel.setTitle(title);
			indexModel.setSummary(summary);
			
			Date addTime = (Date) sd.getFieldValue("last_modified");
			indexModel.setId(id);
			indexModel.setContent(content.get(0));
			indexModel.setAddTime(addTime);
			datas.add(indexModel);
		}
		
		page.setResultList(datas);
		return page;
	}
	
	/**
	 * 删除索引
	 * @param indexField
	 */
	public void deleteIndex(IndexField indexField){
		try {
			SolrContext.getServer().deleteById(indexField.getId());
			SolrContext.getServer().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commitDBIndex(){
		try {
			SolrContext.getServer().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteIndex(){
		try {
			SolrContext.getServer().deleteByQuery("*:*");
			SolrContext.getServer().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

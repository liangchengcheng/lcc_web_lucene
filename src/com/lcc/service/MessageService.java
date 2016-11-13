package com.lcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcc.bean.IndexField;
import com.lcc.bean.Message;
import com.lcc.dao.MessageDao;
import com.lcc.util.IndexUtil;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private SolrService solrService;
	
	public List<Message> list(){
		return messageDao.findAll();
	}
	
	public void save(Message message){
		messageDao.save(message);
		
		//添加索引
		IndexField indexField = IndexUtil.message2IndexField(message);
		solrService.addIndex(indexField);
	}
	
	public void delete(int id){
		messageDao.del(id);
		
		//删除索引
		Message message = messageDao.findById(id);
		IndexField indexField = IndexUtil.message2IndexField(message);
		solrService.deleteIndex(indexField);
	}
	

}

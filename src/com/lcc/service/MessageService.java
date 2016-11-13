package com.lcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lcc.dao.MessageDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
	@Autowired
	private MessageDao messageDao;
	
	

}

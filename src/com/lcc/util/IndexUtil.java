package com.lcc.util;

import java.io.File;
import org.apache.tika.Tika;
import com.lcc.bean.IndexField;
import com.lcc.bean.Message;

public class IndexUtil {
	public static IndexField message2IndexField(Message message){
		
		IndexField indexField = new IndexField();
		indexField.setId(String.valueOf(message.getId()));
		indexField.setTitle(message.getTitle());
		indexField.setContent(message.getContent());
		indexField.setAddTime(message.getAddTime());
		try {
			File file = new File(message.getAttachUrl());
			if (file.exists()) {
				indexField.setSummary(new Tika().parseToString(file));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indexField;
	}
}

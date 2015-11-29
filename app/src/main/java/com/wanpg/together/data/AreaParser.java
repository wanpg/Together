package com.wanpg.together.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class AreaParser extends DefaultHandler {

	
	int id;
	int pid;
	int parentId;
    String name;
    String nodeName;
    Context context;

	private HashMap<Integer, PosModel> provinceMap;
	private OnParseEndListener listener;
    public AreaParser(Context context, OnParseEndListener listener) {
		// TODO Auto-generated constructor stub
    	this.context = context;
		this.listener = listener;
	}
    
	public void parse(InputStream is) {
        provinceMap = new HashMap<>();
		SAXParser parser;
		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(is, this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			onEnd();
		}		
	}

	@Override
    public void startDocument() throws SAXException {
		Log.d("wanpg", "解析地区数据");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		nodeName = localName;
		try {

			if ("Province".equals(localName)) {
				// 保存一级地区信息
				name = attributes.getValue("name");
				id = Integer.parseInt(attributes.getValue("ID"));
				String pid = attributes.getValue("PID");
				if(pid==null){
					pid = "0";
				}
				parentId = Integer.parseInt(pid);
				provinceMap.put(id, PosModel.create(id, parentId, name));
			} else if ("City".equals(localName)){
				// 保存一级地区信息
				name = attributes.getValue("name");
				id = Integer.parseInt(attributes.getValue("ID"));
				String pid = attributes.getValue("PID");
				if(pid==null){
					pid = "0";
				}
				parentId = Integer.parseInt(pid);
				provinceMap.put(id, PosModel.create(id, parentId, name));
			} else if ("District".equals(localName)){

				// 保存一级地区信息
				name = attributes.getValue("name");
				id = Integer.parseInt(attributes.getValue("ID"));
				String pid = attributes.getValue("PID");
				if(pid==null){
					pid = "0";
				}
				parentId = Integer.parseInt(pid);
				provinceMap.put(id, PosModel.create(id, parentId, name));
			}

		} catch (Exception e) {
			Log.e("wanpg", e.getMessage());
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		if (ch != null && length > 0) {
			
			
			if ("item".equals(nodeName)) {
				String value = new String(ch, start, length).trim();
				if (!TextUtils.isEmpty(value)) {
					name = value;
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if("item".equals(localName)){//结尾处保存数据
//			dao.save(new String[] { String.valueOf(id), name,
//					String.valueOf(pid) });
		}
	}

	@Override
	public void endDocument () throws SAXException {
		Log.d("CDH", "地区数据完毕");
		onEnd();
    }
	
	private void onEnd(){
		listener.onEnd(provinceMap);
	}

	public interface OnParseEndListener{
		void onEnd(HashMap<Integer, PosModel> map);
	}
}

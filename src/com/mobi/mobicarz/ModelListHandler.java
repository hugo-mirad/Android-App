package com.mobi.mobicarz;

import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



import android.content.Context;

public class ModelListHandler {
	Context context=null;
	ArrayList<ModelObjects> carModelList=null;
	 public ModelListHandler(Context context) {
		// TODO Auto-generated constructor stub
		 
		 this.context=context;
	}
	 
	 public ArrayList<ModelObjects> getModelListForMake(String makeId)
	 {
		 //Dont add string urls hear http://unitedcarexchange.com/CarService/CarsService.asmx/GetModelsInfoByID?MakeID=1
		 
		 HttpClient client=new DefaultHttpClient();
		 HttpGet httpPost=new HttpGet("http://unitedcarexchange.com/CarService/CarsService.asmx/GetModelsInfoByID?MakeID="+makeId);
		 
		 
		 try {
			HttpResponse httpResponse=client.execute(httpPost);
			
			InputStream is=httpResponse.getEntity().getContent();
//			System.out.println(httpPost.getMethod()+":::::"+is);
			SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
			try {
				SAXParser parser=saxParserFactory.newSAXParser();
				parser.parse(is, new MyModelListHandler());
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return carModelList;
	 }
	
	
class MyModelListHandler extends DefaultHandler{
		ModelObjects carModelObj=null;
		String value="";
		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.startDocument();
			carModelList=new ArrayList<ModelObjects>();
			
		}
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			if(localName.equals("ModelsInfo"))
			{
				carModelObj=new ModelObjects();
			}
			this.value=localName;
		}
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
			StringBuffer stringBuffer=new StringBuffer();
			stringBuffer.append(ch, start, length);
			if(stringBuffer.toString().trim().length()>0)
			{
				value=stringBuffer.toString();
			}
			
		}
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			if(localName.equals("Model"))
			{
				carModelObj.setModel(value);
			}
			if(localName.equals("MakeModelID"))
			{
				carModelObj.setMakeModelID(value);
			}
			if(localName.equals("MakeID"))
			{
				carModelObj.setMakeID(value);
			}
			
			if(localName.equals("ModelsInfo"))
			{
				carModelList.add(carModelObj);
				 
			}
		}
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
			 
		}
		
	}

}

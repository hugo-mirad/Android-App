package com.mobi.mobicarz;

import java.util.ArrayList;


import org.xml.sax.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;




public class MyHandler extends DefaultHandler{
	
	public ArrayList<CarInfo> carList=new ArrayList<CarInfo>();
	
	private String tempLoc="";
	private String PicLocation="";
	private String value="";
	private String URL="www.unitedcarexchange.com/";
	
	private CarInfo carInfo=null;

	 @Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
//		System.out.println("startDocument");
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		
		// PIC0 ;PICLOC0
		
		
		if(localName.equals("UsedCarsInfo"))
		{
			carInfo=new CarInfo();
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		
		//End of UsedCarsInfo
		if(localName.equals("PIC0"))
		{
			tempLoc=value;
//			carInfo.setPIC0(PicLocation+value);
	    }		
		if(localName.equals("PICLOC0"))
		{
			PicLocation=value;
			tempLoc=value+tempLoc;
			carInfo.setThumbnail(tempLoc);
		
		}
		if(localName.equals("PICLOC1"))
		{
			carInfo.setPICLOC1(value);
		
		}
		if(localName.equals("PostingID"))
		{
			carInfo.setPostingID(value);
			
		}
		if(localName.equals("_Model"))
		{
			carInfo.set_Model(value);
			
		}
		if(localName.equals("YearOfMake"))
		{
			carInfo.setYearOfMake(value);
			
		}
		if(localName.equals("Bodytype"))
		{
			carInfo.setBodytype(value);
			
		}
		if(localName.equals("Phone"))
		{
			carInfo.setPhone(value.toString());
			
		}
		if(localName.equals("PICLOC1"))
		{
			PicLocation =value;	
			System.err.println("loc1"+PicLocation);
		
		}
		if(localName.equals("PIC0"))
		{
			carInfo.setPIC1(PicLocation+value);
			System.err.println("loc"+PicLocation+value);
			
		}if(localName.equals("PIC2"))
		{
			carInfo.setPIC2(PicLocation+value);
		}if(localName.equals("PIC3"))
		{
			carInfo.setPIC3(PicLocation+value);
		}if(localName.equals("PIC4"))
		{
			carInfo.setPIC4(PicLocation+value);
		}if(localName.equals("PIC5"))
		{
			carInfo.setPIC5(PicLocation+value);
		}if(localName.equals("PIC6"))
		{
			carInfo.setPIC6(PicLocation+value);
		}if(localName.equals("PIC7"))
		{
			carInfo.setPIC7(PicLocation+value);
		}if(localName.equals("PIC8"))
		{
			carInfo.setPIC8(PicLocation+value);
		}if(localName.equals("PIC9"))
		{
			carInfo.setPIC9(PicLocation+value);
		}if(localName.equals("PIC10"))
		{
			carInfo.setPIC10(PicLocation+value);
		}
//		}if(localName.equals("PIC11"))
//		{
//			carInfo.setThumbnail11(PicLocation+value);
//		}
//		}if(localName.equals("PIC12"))
//		{
//			carInfo.setThumbnail12(PicLocation+value);
//		}if(localName.equals("PIC13"))
//		{
//			carInfo.setThumbnail13(PicLocation+value);
//		}if(localName.equals("PIC14"))
//		{
//			carInfo.setThumbnail14(PicLocation+value);
//		}if(localName.equals("PIC15"))
//		{
//			carInfo.setThumbnail15(PicLocation+value);
//		}if(localName.equals("PIC16"))
//		{
//			carInfo.setThumbnail16(PicLocation+value);
//		}if(localName.equals("PIC17"))
//		{
//			carInfo.setThumbnail17(PicLocation+value);
//		}if(localName.equals("PIC18"))
//		{
//			carInfo.setThumbnail18(PicLocation+value);
//		}if(localName.equals("PIC19"))
//		{
//			carInfo.setThumbnail19(PicLocation+value);
//		}if(localName.equals("PIC20"))
//		{
//			carInfo.setThumbnail20(PicLocation+value);
//		}
		if(localName.equals("ExteriorColor"))
		{
			carInfo.setExteriorColor(value);
			
		}
		
		if(localName.equals("Address1"))
		{
			carInfo.setAddress1(value);
			
		}
		if(localName.equals("Address2"))
		{
			carInfo.setAddress2(value);
			
		}
		if(localName.equals("City"))
		{
			carInfo.setCity(value);
			
		}
		if(localName.equals("InteriorColor"))
		{
			carInfo.setInteriorColor(value);
			
		}
		if(localName.equals("NumberOfDoors"))
		{
			carInfo.setNumberOfDoors(value);
			
		}
		if(localName.equals("DriveTrain"))
		{
			carInfo.setDriveTrain  (value);
			
		}
		if(localName.equals("VIN"))
		{
			carInfo.setVIN(value);
			
		}
		if(localName.equals("NumberOfCylinder"))
		{
			carInfo.setNumberOfCylinder(value);
			
		}
		if(localName.equals("Transmission"))
		{
			carInfo.setTransmission(value);
			
		}
		if(localName.equals("Fueltype"))
		{
			carInfo.setFueltype(value);
			
		}
		if(localName.equals("Mileage"))
		{
			carInfo.setMileage(value);
			
		}
		if(localName.equals("ConditionDescription"))
		{
			carInfo.setConditionDescription(value);
			
		}
		  
	
		if(localName.equals("PostingID"))
		{
			carInfo.setPostingID(value);
			
		}
		
		if(localName.equals("Carid"))
		{
			carInfo.setCarID(value);
			
		}
		if(localName.equals("Description"))
		{
			carInfo.setDescription(value);
			
		}
		if(localName.equals("Price"))
		{
			carInfo.setPrice(value);
        }
		 if(localName.equals("Make"))
			{
				carInfo.setMake(value);
		    }
		 if(localName.equals("Email"))
			{
				carInfo.setEmail(value);
		    }
		 if(localName.equals("Zip"))
			{
				carInfo.setZip(value);
		    }
		 if(localName.equals("State"))
			{
				carInfo.setState(value);
		    }
		 if(localName.equals("Model"))
			{
				carInfo.setModel(value);
             }
       if(localName.equals("UsedCarsInfo"))
		{
			carList.add(carInfo);
		}
}
@Override
public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		StringBuffer string=new StringBuffer();
		string.append(ch,start,length);
		if(string.toString().trim().length()>1)
		{
			value=string.toString();
		}
	}
@Override
public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
super.endDocument();
//System.out.println("endDocument"+carList.size());
}
}
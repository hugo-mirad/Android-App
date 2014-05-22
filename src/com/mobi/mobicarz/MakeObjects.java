package com.mobi.mobicarz;

public class MakeObjects {
	private String modelName="";
	private String modelId="";
	String Model="";
	String makecount="";
	public String getMakecount() {
		System.out.println("this is model name2"+makecount);
		return makecount;
	}
	public void setMakecount(String makecount) {
		this.makecount = makecount;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModel() {
		System.out.println("this is model name2"+Model);
		return Model;
	}
	public void setModel(String model) {
		Model = model;
		System.out.println("this is model name1"+Model);
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	

}

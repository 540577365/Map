package com.example.bean;

import java.io.Serializable;
/**
 * ʵ���࣬��λ��Ϣ�ľ�γ��
 * @author ly12974
 *
 */
public class Place implements Serializable{
	
	public Double lat;
	
	public Double lon;

	public Place(){}
	
	public Place(String name){}
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	

}

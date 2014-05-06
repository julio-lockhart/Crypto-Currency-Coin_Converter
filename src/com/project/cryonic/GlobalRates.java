/***
 * Class that holds all information of different global currencies
 * gathered from the API
 */

package com.project.cryonic;

public class GlobalRates{
   	private String code;
   	private String name;
   	private Double rate;
   	
   	public GlobalRates(String code, String name, Double rate) {
   		this.code = code;
   		this.name = name;
   		this.rate = rate;
   	}

 	public String getCode(){
		return this.code;
	}
	public void setCode(String code){
		this.code = code;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public Double getRate(){
		return this.rate;
	}
	public void setRate(Double rate){
		this.rate = rate;
	}
}

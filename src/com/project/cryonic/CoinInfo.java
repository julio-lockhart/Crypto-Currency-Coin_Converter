/***
 * Class that holds all information about crypto coins gathered
 * from the API
 */

package com.project.cryonic;

public class CoinInfo{
   	private Number adjustedratio;
   	private String algo;
   	private String avgHash;
   	private String avgProfit;
   	private String currentBlocks;
   	private String difficulty;
   	private String exchange;
   	private String exchange_url;
   	private String minBlockTime;
   	private String name;
   	private String networkhashrate;
   	private String price;
   	private Number ratio;
   	private String reward;
   	private String symbol;

 	public Number getAdjustedratio(){
		return this.adjustedratio;
	}
	public void setAdjustedratio(Number adjustedratio){
		this.adjustedratio = adjustedratio;
	}
 	public String getAlgo(){
		return this.algo;
	}
	public void setAlgo(String algo){
		this.algo = algo;
	}
 	public String getAvgHash(){
		return this.avgHash;
	}
	public void setAvgHash(String avgHash){
		this.avgHash = avgHash;
	}
 	public String getAvgProfit(){
		return this.avgProfit;
	}
	public void setAvgProfit(String avgProfit){
		this.avgProfit = avgProfit;
	}
 	public String getCurrentBlocks(){
		return this.currentBlocks;
	}
	public void setCurrentBlocks(String currentBlocks){
		this.currentBlocks = currentBlocks;
	}
 	public String getDifficulty(){
		return this.difficulty;
	}
	public void setDifficulty(String difficulty){
		this.difficulty = difficulty;
	}
 	public String getExchange(){
		return this.exchange;
	}
	public void setExchange(String exchange){
		this.exchange = exchange;
	}
 	public String getExchange_url(){
		return this.exchange_url;
	}
	public void setExchange_url(String exchange_url){
		this.exchange_url = exchange_url;
	}
 	public String getMinBlockTime(){
		return this.minBlockTime;
	}
	public void setMinBlockTime(String minBlockTime){
		this.minBlockTime = minBlockTime;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getNetworkhashrate(){
		return this.networkhashrate;
	}
	public void setNetworkhashrate(String networkhashrate){
		this.networkhashrate = networkhashrate;
	}
 	public String getPrice(){
		return this.price;
	}
	public void setPrice(String price){
		this.price = price;
	}
 	public Number getRatio(){
		return this.ratio;
	}
	public void setRatio(Number ratio){
		this.ratio = ratio;
	}
 	public String getReward(){
		return this.reward;
	}
	public void setReward(String reward){
		this.reward = reward;
	}
 	public String getSymbol(){
		return this.symbol;
	}
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
}

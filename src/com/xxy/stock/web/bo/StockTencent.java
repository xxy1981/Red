package com.xxy.stock.web.bo;

/**
 * StockTencent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockTencent extends StockSina implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private double zd;    			//涨跌
	private double hsl;    			//换手率
	private double syl;    			//市盈率
	private String index40;			//第40位信息
	
	private double sjl;    			//市净率
	private double ltsz;    		//流通市值（亿）
	private double zsz;    			//总市值（亿）
	private double ztj;    			//涨停价
	private double dtj;    			//跌停价
	private double lb;    			//量比
	
	public StockTencent(String code, String info, String zfColor, int updateSum) {
		super(code, info, zfColor, updateSum);
	}
	
	public double getZd() {
		return zd;
	}
	public void setZd(double zd) {
		this.zd = zd;
	}
	public double getHsl() {
		return hsl;
	}
	public void setHsl(double hsl) {
		this.hsl = hsl;
	}
	public double getSyl() {
		return syl;
	}
	public void setSyl(double syl) {
		this.syl = syl;
	}
	public String getIndex40() {
		return index40;
	}
	public void setIndex40(String index40) {
		this.index40 = index40;
	}
	public double getSjl() {
		return sjl;
	}
	public void setSjl(double sjl) {
		this.sjl = sjl;
	}
	public double getLtsz() {
		return ltsz;
	}
	public void setLtsz(double ltsz) {
		this.ltsz = ltsz;
	}
	public double getZsz() {
		return zsz;
	}
	public void setZsz(double zsz) {
		this.zsz = zsz;
	}
	public double getZtj() {
		return ztj;
	}
	public void setZtj(double ztj) {
		this.ztj = ztj;
	}
	public double getDtj() {
		return dtj;
	}
	public void setDtj(double dtj) {
		this.dtj = dtj;
	}
	public double getLb() {
		return lb;
	}
	public void setLb(double lb) {
		this.lb = lb;
	}
	
}
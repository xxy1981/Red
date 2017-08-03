package com.xxy.stock.web.bo;

/**
 * BuySellRate entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BuySellRate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;			//查询代码
	private String info;			//查询数据
	private String id;				//股票代码
	private String name;    		//股票名字
	private double buyBigRate;    	//主买大单比率
	private double buySamllRate;  	//主买小单比率
	private double SellBigRate;    	//主卖大单比率
	private double SellSamllRate;   //主卖小单比率
	private double buyRate;    		//主买比率
	private double sellRate;    	//主卖比率
	private String rateColor;    	//比率颜色
	private int updateSum;    		//数据刷新次数
    private double bigTime;         //大买大卖倍数

    private double closeToday;    	//当前价格
	private double jj;    			//均价
	private double zf;    			//涨幅
	private double jjZf;    		//均价涨幅
	private double zfC;    			//涨幅跟均价涨幅差
	private String zfColor;    		//涨幅颜色
	private String zfCColor;    	//涨幅差颜色
	private int upJjTimes;    		//股价运行均价线上次数
	private double ltsz;    		//流通市值（亿）
	private double zsz;    			//总市值（亿）
	private double lb;    			//量比

	// Constructors

	/** default constructor */
	public BuySellRate() {
	}

	/** minimal constructor */
	public BuySellRate(String code) {
		this.code = code;
	}

	/** minimal constructor */
	public BuySellRate(String code, String info, String rateColor, int updateSum) {
		this.code = code;
		this.info = info;
		this.rateColor = rateColor;
		this.updateSum = updateSum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuySellRate other = (BuySellRate) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBuyBigRate() {
		return buyBigRate;
	}

	public void setBuyBigRate(double buyBigRate) {
		this.buyBigRate = buyBigRate;
	}

	public double getBuySamllRate() {
		return buySamllRate;
	}

	public void setBuySamllRate(double buySamllRate) {
		this.buySamllRate = buySamllRate;
	}

	public double getSellBigRate() {
		return SellBigRate;
	}

	public void setSellBigRate(double sellBigRate) {
		SellBigRate = sellBigRate;
	}

	public double getSellSamllRate() {
		return SellSamllRate;
	}

	public void setSellSamllRate(double sellSamllRate) {
		SellSamllRate = sellSamllRate;
	}

	public double getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(double buyRate) {
		this.buyRate = buyRate;
	}

	public double getSellRate() {
		return sellRate;
	}

	public void setSellRate(double sellRate) {
		this.sellRate = sellRate;
	}

	public String getRateColor() {
		return rateColor;
	}

	public void setRateColor(String rateColor) {
		this.rateColor = rateColor;
	}

	public int getUpdateSum() {
		return updateSum;
	}

	public void setUpdateSum(int updateSum) {
		this.updateSum = updateSum;
	}

	public double getCloseToday() {
		return closeToday;
	}

	public void setCloseToday(double closeToday) {
		this.closeToday = closeToday;
	}

	public double getJj() {
		return jj;
	}

	public void setJj(double jj) {
		this.jj = jj;
	}

	public double getZf() {
		return zf;
	}

	public void setZf(double zf) {
		this.zf = zf;
	}

	public double getJjZf() {
		return jjZf;
	}

	public void setJjZf(double jjZf) {
		this.jjZf = jjZf;
	}

	public double getZfC() {
		return zfC;
	}

	public void setZfC(double zfC) {
		this.zfC = zfC;
	}

	public String getZfColor() {
		return zfColor;
	}

	public void setZfColor(String zfColor) {
		this.zfColor = zfColor;
	}

	public String getZfCColor() {
		return zfCColor;
	}

	public void setZfCColor(String zfCColor) {
		this.zfCColor = zfCColor;
	}

	public int getUpJjTimes() {
		return upJjTimes;
	}

	public void setUpJjTimes(int upJjTimes) {
		this.upJjTimes = upJjTimes;
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

	public double getLb() {
		return lb;
	}

	public void setLb(double lb) {
		this.lb = lb;
	}
    
    public double getBigTime() {
        return bigTime;
    }
    
    public void setBigTime(double bigTime) {
        this.bigTime = bigTime;
    }
	
}
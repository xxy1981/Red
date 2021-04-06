package com.xxy.stock.web.bo;

/**
 * StockSina entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockSina implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;			//sina代码
	private String info;			//sina数据
	private String id;				//股票代码
	private String name;    		//股票名字
	private double openToday;    	//今日开盘价
	private double closeYesterday;  //昨日收盘价
	private double closeToday;    	//当前价格
	private double highToday;    	//今日最高价
	private double lowToday;    	//今日最低价
	private double jinBuy;    		//竞买价
	private double jinSell;    		//竞卖价
	private int doneQuantity;    	//成交的股票数（单位股）
	private double doneAmount;    	//成交金额（元）
	private int buy1Quantity;    	//买一股
	private double buy1Price;    	//买一价
	private int buy2Quantity;    	//买二股
	private double buy2Price;    	//买二价
	private int buy3Quantity;    	//买三股
	private double buy3Price;    	//买三价
	private int buy4Quantity;    	//买四股
	private double buy4Price;    	//买四价
	private int buy5Quantity;    	//买五股
	private double buy5Price;    	//买五价
	private int sell1Quantity;    	//卖一股
	private double sell1Price;    	//卖一价
	private int sell2Quantity;    	//卖二股
	private double sell2Price;    	//卖二价
	private int sell3Quantity;    	//卖三股
	private double sell3Price;    	//卖三价
	private int sell4Quantity;    	//卖四股
	private double sell4Price;    	//卖四价
	private int sell5Quantity;    	//卖五股
	private double sell5Price;    	//卖五价
	private int buySumQuantity;     //五档总买股票数量
	private int sellSumQuantity;    //五档总卖股票数量
	private String updateDate;    	//日期
	private String updateTime;    	//时间
	private double zf;    			//涨幅
	private int alwaysStrongFlag;   //一直处于均价线上标志
	private double jj;    			//均价
	private double jjHigh;    		//最高均价
	private double jjZf;    		//均价涨幅
	private double zfC;    			//涨幅跟均价涨幅差
	private String zfCColor;    	//涨幅差颜色
	private int jjStrongFlag;    	//均价持强上升标志
	private int upJjTimes;    		//股价运行均价线上次数
	private String zfColor;    		//涨幅颜色
	private int updateSum;    		//数据刷新次数
	private String block;    		//板块信息
	private String openStatus;    	//开盘状态
	private String openColor;    	//开盘颜色
	private String window;    		//缺口标志
	private String windowColor;    	//缺口颜色
	private String selectMark;    	//自选股标志

	private int doneQuantity933;    //9:33分前成交的股票数（单位股）

	// Constructors

	/** default constructor */
	public StockSina() {
	}

	/** minimal constructor */
	public StockSina(String code) {
		this.code = code;
	}

	/** minimal constructor */
	public StockSina(String code, String info, String zfColor, int updateSum) {
		this.code = code;
		this.info = info;
		this.zfColor = zfColor;
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
		StockSina other = (StockSina) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getOpenToday() {
		return this.openToday;
	}

	public void setOpenToday(double openToday) {
		this.openToday = openToday;
	}

	public double getCloseYesterday() {
		return this.closeYesterday;
	}

	public void setCloseYesterday(double closeYesterday) {
		this.closeYesterday = closeYesterday;
	}

	public double getCloseToday() {
		return this.closeToday;
	}

	public void setCloseToday(double closeToday) {
		this.closeToday = closeToday;
	}

	public double getHighToday() {
		return this.highToday;
	}

	public void setHighToday(double highToday) {
		this.highToday = highToday;
	}

	public double getLowToday() {
		return this.lowToday;
	}

	public void setLowToday(double lowToday) {
		this.lowToday = lowToday;
	}

	public double getJinBuy() {
		return this.jinBuy;
	}

	public void setJinBuy(double jinBuy) {
		this.jinBuy = jinBuy;
	}

	public double getJinSell() {
		return this.jinSell;
	}

	public void setJinSell(double jinSell) {
		this.jinSell = jinSell;
	}

	public int getDoneQuantity() {
		return this.doneQuantity;
	}

	public void setDoneQuantity(int doneQuantity) {
		this.doneQuantity = doneQuantity;
	}

	public double getDoneAmount() {
		return this.doneAmount;
	}

	public void setDoneAmount(double doneAmount) {
		this.doneAmount = doneAmount;
	}

	public int getBuy1Quantity() {
		return this.buy1Quantity;
	}

	public void setBuy1Quantity(int buy1Quantity) {
		this.buy1Quantity = buy1Quantity;
	}

	public double getBuy1Price() {
		return this.buy1Price;
	}

	public void setBuy1Price(double buy1Price) {
		this.buy1Price = buy1Price;
	}

	public int getBuy2Quantity() {
		return this.buy2Quantity;
	}

	public void setBuy2Quantity(int buy2Quantity) {
		this.buy2Quantity = buy2Quantity;
	}

	public double getBuy2Price() {
		return this.buy2Price;
	}

	public void setBuy2Price(double buy2Price) {
		this.buy2Price = buy2Price;
	}

	public int getBuy3Quantity() {
		return this.buy3Quantity;
	}

	public void setBuy3Quantity(int buy3Quantity) {
		this.buy3Quantity = buy3Quantity;
	}

	public double getBuy3Price() {
		return this.buy3Price;
	}

	public void setBuy3Price(double buy3Price) {
		this.buy3Price = buy3Price;
	}

	public int getBuy4Quantity() {
		return this.buy4Quantity;
	}

	public void setBuy4Quantity(int buy4Quantity) {
		this.buy4Quantity = buy4Quantity;
	}

	public double getBuy4Price() {
		return this.buy4Price;
	}

	public void setBuy4Price(double buy4Price) {
		this.buy4Price = buy4Price;
	}

	public int getBuy5Quantity() {
		return this.buy5Quantity;
	}

	public void setBuy5Quantity(int buy5Quantity) {
		this.buy5Quantity = buy5Quantity;
	}

	public double getBuy5Price() {
		return this.buy5Price;
	}

	public void setBuy5Price(double buy5Price) {
		this.buy5Price = buy5Price;
	}

	public int getSell1Quantity() {
		return this.sell1Quantity;
	}

	public void setSell1Quantity(int sell1Quantity) {
		this.sell1Quantity = sell1Quantity;
	}

	public double getSell1Price() {
		return this.sell1Price;
	}

	public void setSell1Price(double sell1Price) {
		this.sell1Price = sell1Price;
	}

	public int getSell2Quantity() {
		return this.sell2Quantity;
	}

	public void setSell2Quantity(int sell2Quantity) {
		this.sell2Quantity = sell2Quantity;
	}

	public double getSell2Price() {
		return this.sell2Price;
	}

	public void setSell2Price(double sell2Price) {
		this.sell2Price = sell2Price;
	}

	public int getSell3Quantity() {
		return this.sell3Quantity;
	}

	public void setSell3Quantity(int sell3Quantity) {
		this.sell3Quantity = sell3Quantity;
	}

	public double getSell3Price() {
		return this.sell3Price;
	}

	public void setSell3Price(double sell3Price) {
		this.sell3Price = sell3Price;
	}

	public int getSell4Quantity() {
		return this.sell4Quantity;
	}

	public void setSell4Quantity(int sell4Quantity) {
		this.sell4Quantity = sell4Quantity;
	}

	public double getSell4Price() {
		return this.sell4Price;
	}

	public void setSell4Price(double sell4Price) {
		this.sell4Price = sell4Price;
	}

	public int getSell5Quantity() {
		return this.sell5Quantity;
	}

	public void setSell5Quantity(int sell5Quantity) {
		this.sell5Quantity = sell5Quantity;
	}

	public double getSell5Price() {
		return this.sell5Price;
	}

	public void setSell5Price(double sell5Price) {
		this.sell5Price = sell5Price;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public double getZf() {
		return this.zf;
	}

	public void setZf(double zf) {
		this.zf = zf;
	}

	public int getAlwaysStrongFlag() {
		return this.alwaysStrongFlag;
	}

	public void setAlwaysStrongFlag(int alwaysStrongFlag) {
		this.alwaysStrongFlag = alwaysStrongFlag;
	}

	public double getJj() {
		return this.jj;
	}

	public void setJj(double jj) {
		this.jj = jj;
	}

	public double getJjHigh() {
		return this.jjHigh;
	}

	public void setJjHigh(double jjHigh) {
		this.jjHigh = jjHigh;
	}

	public int getJjStrongFlag() {
		return this.jjStrongFlag;
	}

	public void setJjStrongFlag(int jjStrongFlag) {
		this.jjStrongFlag = jjStrongFlag;
	}

	public int getUpJjTimes() {
		return this.upJjTimes;
	}

	public void setUpJjTimes(int upJjTimes) {
		this.upJjTimes = upJjTimes;
	}

	public int getUpdateSum() {
		return updateSum;
	}

	public void setUpdateSum(int updateSum) {
		this.updateSum = updateSum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}

	public String getWindow() {
		return window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	public String getZfColor() {
		return zfColor;
	}

	public void setZfColor(String zfColor) {
		this.zfColor = zfColor;
	}

	public String getOpenColor() {
		return openColor;
	}

	public void setOpenColor(String openColor) {
		this.openColor = openColor;
	}

	public String getWindowColor() {
		return windowColor;
	}

	public void setWindowColor(String windowColor) {
		this.windowColor = windowColor;
	}

	public String getSelectMark() {
		return selectMark;
	}

	public void setSelectMark(String selectMark) {
		this.selectMark = selectMark;
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

	public String getZfCColor() {
		return zfCColor;
	}

	public void setZfCColor(String zfCColor) {
		this.zfCColor = zfCColor;
	}

	public int getBuySumQuantity() {
		return buySumQuantity;
	}

	public void setBuySumQuantity(int buySumQuantity) {
		this.buySumQuantity = buySumQuantity;
	}

	public int getSellSumQuantity() {
		return sellSumQuantity;
	}

	public void setSellSumQuantity(int sellSumQuantity) {
		this.sellSumQuantity = sellSumQuantity;
	}
	
	public int getDoneQuantity933() {
		return doneQuantity933;
	}

	public void setDoneQuantity933(int doneQuantity933) {
		this.doneQuantity933 = doneQuantity933;
	}
	
}
package classes_package;

final class AdvancedSettings {

    private int maxReconf;
    private int minReconf;
    private int totReconf;
    private double maxAwt;
    private double maxAvailability;
    private double maxPeak;
    private double maxCost;
    private double minAwt;
    private double minAvailability;
    private double minPeak;
    private double minCost;
    private double totAwt;
    private double totAvailability;
    private double totPeak;
    private double totCost;
	
    public AdvancedSettings(int maxReconf, int minReconf, int totReconf,
    		double maxAwt, double maxAvailability, double maxPeak, double maxCost, 
    		double minAwt, double minAvailability, double minPeak, double minCost, 
    		double totAwt, double totAvailability, double totPeak, double totCost){
    	
    	this.maxReconf = maxReconf;
    	this.minReconf = minReconf;
    	this.totReconf = totReconf;
    	this.maxAwt = maxAwt;
    	this.maxAvailability = maxAvailability;
    	this.maxPeak = maxPeak;
    	this.maxCost = maxCost;
    	this.minAwt = minAwt;
    	this.minAvailability = minAvailability;
    	this.minPeak = minPeak;
    	this.minCost = minCost;
    	this.totAwt = totAwt;
    	this.totAvailability = totAvailability;
    	this.totPeak = totPeak;
    	this.totCost = totCost;
    }
    
    public AdvancedSettings(){
    	maxReconf = 0;
    	minReconf = 999999;
    	totReconf = 0;
    	maxAwt = 0.0;
    	maxAvailability = 0.0;
    	maxPeak = 0.0;
    	maxCost = 0.0;
    	minAwt = 999.0;
    	minAvailability = 9999.0;
    	minPeak = 9999.0;
    	minCost = 999999.0;
    	totAwt = 0.0;
    	totAvailability = 0.0;
    	totPeak = 0.0;
    	totCost = 0.0;
    	
	}

	public int getMaxReconf() {
		return maxReconf;
	}

	public void setMaxReconf(int maxReconf) {
		this.maxReconf = maxReconf;
	}

	public int getMinReconf() {
		return minReconf;
	}

	public void setMinReconf(int minReconf) {
		this.minReconf = minReconf;
	}

	public int getTotReconf() {
		return totReconf;
	}

	public void setTotReconf(int totReconf) {
		this.totReconf = totReconf;
	}

	public double getMaxAwt() {
		return maxAwt;
	}

	public void setMaxAwt(double maxAwt) {
		this.maxAwt = maxAwt;
	}

	public double getMaxAvailability() {
		return maxAvailability;
	}

	public void setMaxAvailability(double maxAvailability) {
		this.maxAvailability = maxAvailability;
	}

	public double getMaxPeak() {
		return maxPeak;
	}

	public void setMaxPeak(double maxPeak) {
		this.maxPeak = maxPeak;
	}

	public double getMaxCost() {
		return maxCost;
	}

	public void setMaxCost(double maxCost) {
		this.maxCost = maxCost;
	}

	public double getMinAwt() {
		return minAwt;
	}

	public void setMinAwt(double minAwt) {
		this.minAwt = minAwt;
	}

	public double getMinAvailability() {
		return minAvailability;
	}

	public void setMinAvailability(double minAvailability) {
		this.minAvailability = minAvailability;
	}

	public double getMinPeak() {
		return minPeak;
	}

	public void setMinPeak(double minPeak) {
		this.minPeak = minPeak;
	}

	public double getMinCost() {
		return minCost;
	}

	public void setMinCost(double minCost) {
		this.minCost = minCost;
	}

	public double getTotAwt() {
		return totAwt;
	}

	public void setTotAwt(double totAwt) {
		this.totAwt = totAwt;
	}

	public double getTotAvailability() {
		return totAvailability;
	}

	public void setTotAvailability(double totAvailability) {
		this.totAvailability = totAvailability;
	}

	public double getTotPeak() {
		return totPeak;
	}

	public void setTotPeak(double totPeak) {
		this.totPeak = totPeak;
	}

	public double getTotCost() {
		return totCost;
	}

	public void setTotCost(double totCost) {
		this.totCost = totCost;
	}

}

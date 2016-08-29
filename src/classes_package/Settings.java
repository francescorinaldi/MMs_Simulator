package classes_package;

final class Settings {

    private double bootingTime;
    private double deactivationTimeout;
    private int hysteresis;
    private int iterations;
	
    public Settings(double bootingTime, double deactivationTimeout, int hysteresis, int iterations) {
        this.bootingTime = bootingTime;
        this.deactivationTimeout = deactivationTimeout;
        this.hysteresis = hysteresis;
        this.iterations = iterations;
    }
    
    public Settings(){
    	bootingTime = 900.00;
        deactivationTimeout = 3600.00;
        hysteresis = 3;
        iterations = 1;
	}

    public double getBootingTime() {
        return bootingTime;
    }

    public double getDeactivationTimeout() {
        return deactivationTimeout;
    }

	public int getHysteresis(){
		return hysteresis;
	}
	
	public int getIterations(){
		return iterations;
	}
	
	public void setBootingTime(double bootingTime){
		this.bootingTime = bootingTime;
	}

	public void setDeactivationTimeout(double deactivationTimeout){
		this.deactivationTimeout = deactivationTimeout;
	}
	
	public void setHysteresis(int hysteresis){
		this.hysteresis = hysteresis;
	}
	
	public void setIterations(int iterations){
		this.iterations = iterations;
	}


public void main() {
    System.out.println(getBootingTime() + " " + getDeactivationTimeout() + " " + getHysteresis() + " " + getIterations());
	}
}

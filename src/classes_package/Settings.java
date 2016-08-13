package classes_package;

final class Settings {

    private double bootingTime;
    private double deactivationTimeout;
    private int isteresi;
    private int iterations;
	
    public Settings(double bootingTime, double deactivationTimeout, int isteresi, int iterations) {
        this.bootingTime = bootingTime;
        this.deactivationTimeout = deactivationTimeout;
        this.isteresi = isteresi;
        this.iterations = iterations;
    }
    
    public Settings(){
    	bootingTime = 900.00;
        deactivationTimeout = 3600.00;
        isteresi = 3;
        iterations = 1;
	}

    public double getBootingTime() {
        return bootingTime;
    }

    public double getDeactivationTimeout() {
        return deactivationTimeout;
    }

	public int getIsteresi(){
		return isteresi;
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
	
	public void setIsteresi(int isteresi){
		this.isteresi = isteresi;
	}
	
	public void setIterations(int iterations){
		this.iterations = iterations;
	}


public void main() {
    System.out.println(getBootingTime() + " " + getDeactivationTimeout() + " " + getIsteresi() + " " + getIterations());
	}
}

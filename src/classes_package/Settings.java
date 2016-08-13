package classes_package;

final class Settings {

    private double bootingTime;
    private double deactivationTimeout;
    private int isteresi;
	
    public Settings(double bootingTime, double deactivationTimeout, int isteresi) {
        this.bootingTime = bootingTime;
        this.deactivationTimeout = deactivationTimeout;
        this.isteresi = isteresi;
    }
    
    public Settings(){
    	bootingTime = 900.00;
        deactivationTimeout = 3600.00;
        isteresi = 3;
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
	
	public void setBootingTime(double bootingTime){
		this.bootingTime = bootingTime;
	}

	public void setDeactivationTimeout(double deactivationTimeout){
		this.deactivationTimeout = deactivationTimeout;
	}
	
	public void setIsteresi(int isteresi){
		this.isteresi = isteresi;
	}


public void main() {
    System.out.println(getBootingTime() + " " + getDeactivationTimeout() + " " + getIsteresi());
	}
}

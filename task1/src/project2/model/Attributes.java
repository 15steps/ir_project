package project2.model;

public class Attributes {

	private double screenSize;
	private double cameraRes;
	private double screenResolutionWidth;
	private double screenResolutionHeight;
	private double batteryCapacity;
	private double ram;
	private double internalMemory;
	private double processorSpeed;
	private double weight;
	
	private int id;
	private String name;
	
	public Attributes() {
		
	}
	public Attributes(double screenSize, double cameraRes, double screenResolutionWidth, double screenResolutionHeight,
			double batteryCapacity, double ram, double internalMemory, double processorSpeed, double weight, int id,
			String name) {
		this.screenSize = screenSize;
		this.cameraRes = cameraRes;
		this.screenResolutionWidth = screenResolutionWidth;
		this.screenResolutionHeight = screenResolutionHeight;
		this.batteryCapacity = batteryCapacity;
		this.ram = ram;
		this.internalMemory = internalMemory;
		this.processorSpeed = processorSpeed;
		this.weight = weight;
		this.id = id;
		this.name = name;
	}

	public double getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(double screenSize) {
		this.screenSize = screenSize;
	}

	public double getCameraRes() {
		return cameraRes;
	}

	public void setCameraRes(double cameraRes) {
		this.cameraRes = cameraRes;
	}

	public double getScreenResolutionWidth() {
		return screenResolutionWidth;
	}

	public void setScreenResolutionWidth(double screenResolutionWidth) {
		this.screenResolutionWidth = screenResolutionWidth;
	}

	public double getScreenResolutionHeight() {
		return screenResolutionHeight;
	}

	public void setScreenResolutionHeight(double screenResolutionHeight) {
		this.screenResolutionHeight = screenResolutionHeight;
	}

	public double getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(double batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public double getRam() {
		return ram;
	}

	public void setRam(double ram) {
		this.ram = ram;
	}

	public double getInternalMemory() {
		return internalMemory;
	}

	public void setInternalMemory(double internalMemory) {
		this.internalMemory = internalMemory;
	}

	public double getProcessorSpeed() {
		return processorSpeed;
	}

	public void setProcessorSpeed(double processorSpeed) {
		this.processorSpeed = processorSpeed;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double[] getAttributesQuartis(){
		double[] attributes = new double[5];
		attributes[0] = screenSize;
		attributes[1] = cameraRes;
		attributes[2] = batteryCapacity;
		attributes[3] = processorSpeed;
		attributes[4] = weight;
		return attributes;
	}
	
	public String[] getNamesQuartis(){
		String[] attributes = new String[5];
		attributes[0] = "screen";
		attributes[1] = "camera";
		attributes[2] = "battery";
		attributes[3] = "processor";
		attributes[4] = "weight";
		return attributes;
	}
	
	public String[] getNamesNormal(){
		String[] attributes = new String[3];
		attributes[0] = "resolution";
		attributes[1] = "ram";
		attributes[2] = "memory";
		return attributes;
	}
	
}

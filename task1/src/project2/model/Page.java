package project2.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "page")
@XmlType(propOrder = { "id" ,"title", "screenSize", "cameraRes", 
		"screenResolution", "batteryCapacity", "ram", "internalMemory", 
		"processorSpeed", "weight", "body", "tokens", "name"})
public class Page {

	private String title;
	private String screenSize;
	private String cameraRes;
	private String screenResolution;
	private String batteryCapacity;
	private String ram;
	private String internalMemory;
	private String processorSpeed;
	private String weight;
	private String body;
	
	private List<Token> tokens = new ArrayList<Token>();
	private int id;
	private String name;
	private int countToken;
	
	public Page() {
		
	}

	public String getTitle() {
		return title;
	}
	
	@XmlElement(name="title")
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScreenSize() {
		return screenSize;
	}
	
	@XmlElement(name="screen_size")
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	public String getCameraRes() {
		return cameraRes;
	}
	
	@XmlElement(name="camera_res")
	public void setCameraRes(String cameraRes) {
		this.cameraRes = cameraRes;
	}
	public String getScreenResolution() {
		return screenResolution;
	}
	
	@XmlElement(name="screen_resolution")
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}
	public String getBatteryCapacity() {
		return batteryCapacity;
	}
	
	@XmlElement(name="battery_capacity")
	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	public String getRam() {
		return ram;
	}
	
	@XmlElement(name="ram")
	public void setRam(String ram) {
		this.ram = ram;
	}
	public String getInternalMemory() {
		return internalMemory;
	}
	
	@XmlElement(name="internal_memory")
	public void setInternalMemory(String internalMemory) {
		this.internalMemory = internalMemory;
	}
	public String getProcessorSpeed() {
		return processorSpeed;
	}
	
	@XmlElement(name="processor_speed")
	public void setProcessorSpeed(String processorSpeed) {
		this.processorSpeed = processorSpeed;
	}
	public String getWeight() {
		return weight;
	}
	
	@XmlElement(name="weight")
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBody() {
		return body;
	}
	
	@XmlElement(name="body")
	public void setBody(String body) {
		this.body = body;
	}	
	public int getId() {
		return id;
	}
	
	@XmlElementWrapper(name="token_list")
	@XmlElement(name="token")
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	@XmlElement(name="id")
	public void setId(int id) {
		this.id = id;
	}
	public List<Token> getTokens() {
		return tokens;
	}
	public String getName() {
		return name;
	}
	
	@XmlElement(name="name")
	public void setName(String name) {
		this.name = name;
	}

	public int getCountToken() {
		return countToken;
	}

	public void setCountToken(int countToken) {
		this.countToken = countToken;
	}
	
}

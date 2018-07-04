package project2.model;

public class Quartis {

	private double min;
	private double max;
	private String tag;
	
	public Quartis() {
		
	}
	public Quartis(double min, double max) {
		this.min = min;
		this.max = max;
		
		if(min == 0){
			this.tag = "[" + min + "-" + max + "]";
		}else{
			this.tag = "(" + min + "-" + max + "]";
		}
	}
	public boolean contem(double value){
		if(min == 0){
			return (value >= this.min && value <= this.max);
		}else{
			return (value > this.min && value <= this.max);
		}
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	@Override
	public String toString() {
		return "Quartis [min=" + min + ", max=" + max + ", tag=" + tag + "]";
	}
	
}


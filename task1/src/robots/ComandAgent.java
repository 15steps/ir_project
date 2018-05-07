package robots;

public class ComandAgent{
	
	private TComandAgent comandAgent;
	private String base;
	private String path;
	private String rejex;
	
	public ComandAgent(TComandAgent comandAgent, String base, String path) {
		this.comandAgent = comandAgent;
		this.path = path;
		this.base = base;
		String rejex = base + path.replaceAll("\\*", ".*");
		this.rejex = rejex.endsWith(".*") ? rejex : (rejex + ".*");
	}
	
	public TComandAgent getComandAgent() {
		return comandAgent;
	}
	public void setComandAgent(TComandAgent comandAgent) {
		this.comandAgent = comandAgent;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRejex() {
		return rejex;
	}
	public void setRejex(String rejex) {
		this.rejex = rejex;
	}

	@Override
	public String toString() {
		return "ComandAgent [comandAgent=" + comandAgent + ", base=" + base + ", path=" + path + ", rejex=" + rejex	+ "]";
	}
	
}
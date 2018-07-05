package project2.model;

public enum TAtributos {
	
	EMPTY("EMPTY", "EMPTY"),
	_0_0_TO_2_335_SCREEN("[0.0-2.335] / screen", "[0.0-2.335]"),
	_2_335_TO_4_67_SCREEN("(2.335-4.67] / screen", "(2.335-4.67]"),
	_4_67_TO_7_005_SCREEN("(4.67-7.005] / screen", "(4.67-7.005]"),
	_7_005_TO_9_34_SCREEN("(7.005-9.34] / screen", "(7.005-9.34]"),
	_0_0_TO_5_75_CAMERA("[0.0-5.75] / camera", "[0.0-5.75]"),
	_5_75_TO_11_5_CAMERA("(5.75-11.5] / camera", "(5.75-11.5]"),
	_11_5_TO_17_25_CAMERA("(11.5-17.25] / camera", "(11.5-17.25]"),
	_17_25_TO_23_0_CAMERA("(17.25-23.0] / camera", "(17.25-23.0]"),
	_0_0_TO_3250_0_BATTERY("[0.0-3250.0] / battery", "[0.0-3250.0]"),
	_3250_0_TO_6500_0_BATTERY("(3250.0-6500.0] / battery", "(3250.0-6500.0]"),
	_6500_0_TO_9750_0_BATTERY("(6500.0-9750.0] / battery", "(6500.0-9750.0]"),
	_9750_0_TO_13000_0_BATTERY("(9750.0-13000.0] / battery", "(9750.0-13000.0]"),
	_0_0_TO_0_775_PROCESSOR("[0.0-0.775] / processor", "[0.0-0.775]"),
	_0_775_TO_1_55_PROCESSOR("(0.775-1.55] / processor", "(0.775-1.55]"),
	_1_55_TO_2_325_PROCESSOR("(1.55-2.325] / processor", "(1.55-2.325]"),
	_2_325_TO_3_1_PROCESSOR("(2.325-3.1] / processor", "(2.325-3.1]"),
	_0_0_TO_126_5_WEIGHT("[0.0-126.5] / weight", "[0.0-126.5]"),
	_126_5_TO_253_0_WEIGHT("(126.5-253.0] / weight", "(126.5-253.0]"),
	_253_0_TO_379_5_WEIGHT("(253.0-379.5] / weight", "(253.0-379.5]"),
	_379_5_TO_506_0_WEIGHT("(379.5-506.0] / weight", "(379.5-506.0]");
	
	private String description;
	private String gap;
	
	TAtributos(String description, String gap){
		this.description = description;
		this.gap = gap;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGap() {
		return gap;
	}

	public void setGap(String gap) {
		this.gap = gap;
	}

}

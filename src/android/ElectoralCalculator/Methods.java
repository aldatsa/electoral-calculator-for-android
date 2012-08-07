package android.ElectoralCalculator;

public enum Methods {
	DHONDT, SAINTE_LAGUE, MODIFIED_SAINTE_LAGUE, IMPERIALI, HARE_QUOTA, DROOP_QUOTA;

	private static Methods method = Methods.DHONDT;
	
	public static Methods getCalculationMethod()
	{
		return method;
	}
	
	public static void setCalculationMethod(Methods calculationMethod)
	{
		method = calculationMethod;
	}
	
    public static int  getDivisor(int numSeats, Methods method) {
    	if (method.equals(Methods.DHONDT)) {
    		return numSeats + 1;
    	} else {
    		return -1; // ERROR: Unknown method
    	}
    }
}

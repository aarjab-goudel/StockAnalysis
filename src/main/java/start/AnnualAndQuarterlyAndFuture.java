package start;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;

public class AnnualAndQuarterlyAndFuture {
	
	public AnnualAndQuarterlyAndFuture() throws JSONException, IOException, ParseException{
		new AnnualAndFuture();
		new Quarterly();
	}
	
	public static void main(String[] args) throws JSONException, IOException, ParseException {
		new AnnualAndQuarterlyAndFuture();
		
	}

}

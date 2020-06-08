package analyze.excel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import analyze.excel.data.SheetAnalyzer;

public class AnalyzeSheetDataTest {

	@Test
	public void testCalculateAverageGrowthRate() {
		List<String> testList = new ArrayList<String>();
		testList.add("184221");
		testList.add("184089");
		testList.add("140861");
		testList.add("123135");
		testList.add("122671");
		BigDecimal result = SheetAnalyzer.calculateAverageGrowthRate(testList.get(0), testList.get(4));
		assertEquals("The average growth rate is wrong!", "0.0848", result.toString());
	}

	@Test
	public void testIsValueNegative() {
		List<String> testList = new ArrayList<String>();
		testList.add("184221");
		testList.add("184089");
		testList.add("140861");
		testList.add("123135");
		testList.add("122671");
		assertFalse("Testing if value is negative is wrong!", SheetAnalyzer.areValuesNegative(testList));

		List<String> testListTwo = new ArrayList<String>();
		testListTwo.add("-184221");
		testListTwo.add("184089");
		testListTwo.add("140861");
		testListTwo.add("123135");
		testListTwo.add("122671");

		assertTrue("Testing if value is negative is wrong!", SheetAnalyzer.areValuesNegative(testListTwo));

	}

	@Test
	public void testCountNegValues() {
		List<String> testList = new ArrayList<String>();
		testList.add("0.0231");
		testList.add("0.0832");
		testList.add("0.0700");
		testList.add("-0.031");
		testList.add("0.0421");

		assertEquals("Testing the number of negative values is wrong!", 1, SheetAnalyzer.countNegativeValues(testList));

	}

}

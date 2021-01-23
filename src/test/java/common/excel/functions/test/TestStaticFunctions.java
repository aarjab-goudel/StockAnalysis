/**
 * Author: Aarjab Goudel
 * Last Modified Date: 1/12/2021
 * 
 */
package common.excel.functions.test;

import org.junit.Test;

import create.excel.data.service.SaveExcel;
import excel.library.CommonFinancialLibrary;

public class TestStaticFunctions {

	@Test
	public void testCalculateGrowthRate() {
		System.out.println(CommonFinancialLibrary.calculateGrowthRate("-3735", "-1518"));
	}

	@Test
	public void testSaveExcel() {
		System.out.println(SaveExcel.getRootPath());
	}

	@Test
	public void testDesktopPath() {
		System.out.println(SaveExcel.getRootPath());
	}

}

package web.scrape.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.DomNode;

public class FinancialScrapper {

	private List<String> tickersToBuild;
	private static final int NUM_YEARS = 5;

	public FinancialScrapper(List<String> tickersToBuild) {
		this.tickersToBuild = tickersToBuild;
	}

	public List<String> getTickersToBuild() {
		return tickersToBuild;
	}

	public List<String> getOrderedDateValuesFromDomNodeRow(DomNode domNode) {
		List<String> orderedRow = new ArrayList<String>();
		if (domNode != null) {
			Iterator<DomNode> iterator = domNode.getChildren().iterator();
			int counter = 0;
			while (counter < NUM_YEARS) {
				DomNode node = iterator.next();
				String strNode = node.getChildNodes().get(1).toString();
				if (strNode.isBlank()) {
					orderedRow.add("0.00");
				} else {
					orderedRow.add(strNode);
				}
				counter++;
			}
		}
		assertEquals("The ordered row's size is not 5! ", orderedRow.size(), NUM_YEARS);
		Collections.reverse(orderedRow);
		return orderedRow;
	}

	public List<String> getOrderedRowValuesFromDomNodeRow(DomNode domNode) {
		List<String> orderedRow = new ArrayList<String>();
		if (domNode != null) {
			Iterator<DomNode> iterator = domNode.getChildren().iterator();
			int counter = 0;
			while (counter < NUM_YEARS) {
				DomNode node = iterator.next();
				String strNode = node.getFirstChild().toString();
				if (strNode.isBlank()) {
					orderedRow.add("0.00");
				} else {
					orderedRow.add(strNode);
				}
				counter++;
			}
		}
		Collections.reverse(orderedRow);
		assertEquals("The ordered row's size is not 5! ", orderedRow.size(), NUM_YEARS);
		return orderedRow;
	}

	public static int getNumYears() {
		return NUM_YEARS;
	}

}

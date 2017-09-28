/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public interface ITSheetsResponse<RESULTS extends IResults, SUP_DATA extends ISupplementalData> {

	/**
	 * @return
	 */
	RESULTS getResults();

	/**
	 * @param results
	 */
	void setResults(RESULTS results);
	
	/**
	 * @return
	 */
	SUP_DATA getSupplementalData();

	/**
	 * @param supplementalData
	 */
	void setSupplementalData(SUP_DATA supplementalData);

	/**
	 * @return
	 */
	boolean isMore();

	/**
	 * @param more
	 */
	void setMore(boolean more);

}

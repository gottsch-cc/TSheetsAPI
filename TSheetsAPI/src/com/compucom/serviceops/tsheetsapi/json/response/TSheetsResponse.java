/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class TSheetsResponse<RESULTS extends IResults, SUP_DATA extends ISupplementalData>
	extends AbstractTSheetsResponse<RESULTS, SUP_DATA> {

	/**
	 * 
	 */
	public TSheetsResponse() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TSheetsResponse [getResults()=" + getResults() + ", getSupplementalData()=" + getSupplementalData()
				+ ", isMore()=" + isMore() + "]";
	}
}

/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public abstract class AbstractTSheetsResponse<RESULTS extends IResults, SUP_DATA extends ISupplementalData>
	implements ITSheetsResponse<RESULTS, SUP_DATA> {
	@SerializedName("results") private RESULTS results;
	@SerializedName("supplemental_data") private SUP_DATA supplementalData;
	@SerializedName("more") private boolean more;
	
	/**
	 * @return the results
	 */
	@Override
	public RESULTS getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	@Override
	public void setResults(RESULTS results) {
		this.results = results;
	}
	
	/**
	 * @return the supplementalData
	 */
	@Override
	public SUP_DATA getSupplementalData() {
		return this.supplementalData;
	}

	/**
	 * @param supplementalData the supplementalData to set
	 */
	@Override
	public void setSupplementalData(SUP_DATA supplementalData) {
		this.supplementalData = supplementalData;
	}

	/**
	 * @return the more
	 */
	@Override
	public boolean isMore() {
		return more;
	}

	/**
	 * @param more the more to set
	 */
	@Override
	public void setMore(boolean more) {
		this.more = more;
	}
}

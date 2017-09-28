/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.compucom.serviceops.tsheetsapi.dto.TimesheetReportDto;
import com.compucom.serviceops.tsheetsapi.model.Timesheet;

/**
 * @author Mark Gottschling on Nov 14, 2016
 *
 */
@Repository("timesheetDao")
public class TimesheetDao extends AbstractTSheetsDao<Timesheet> {
	private static final Logger logger = LogManager.getLogger(TimesheetDao.class);

	/**
	 * @param startDate
	 * @param endDate
	 * @param filters
	 * @param sorting
	 * @return
	 */
	public List<TimesheetReportDto> findTimesheetReport(Date startDate, Date endDate, Map<String, Object> filters,
			Map<String, String> sorting) {
		List<TimesheetReportDto> list = null;
		final String S = " ";
		StringBuilder builder = new StringBuilder();
		
		return list;
	}

}

/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dto.TimesheetReportDto;

/**
 * @author Mark Gottschling on Dec 9, 2016
 *
 */
@Service("reportService")
@Transactional(transactionManager="tsheetsTxMgr")
public class ReportService {
	private static final Logger logger = LogManager.getLogger(ReportService.class);
	
	@Autowired private TimesheetService timesheetService;
	
	/**
	 * Generate a report file.
	 * @return the abolute filename of the report
	 */
	public String generateReport(Date startDate, Date endDate, Map<String, Object> filters, Map<String, String> sorting) {
		String reportFilename = "";
		
		List<TimesheetReportDto> report = timesheetService.getTimesheetReport(startDate, endDate, filters, sorting);
		logger.debug("Report.size=" + report.size());

		// TODO abstract out the excel report generation (ie ExcelReportGenerator)
		/**
		 * populate an Excel workbook and persist
		 */
		Workbook workbook = null;	
		FileOutputStream fos = null;
		
		/*
		 * TODO
		 *  save the meta of the report
		 */
		
		return reportFilename;
	}
}

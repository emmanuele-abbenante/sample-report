package com.everis.samplereport.business;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.common.file.CSVUtils;
import com.everis.common.properties.PropertiesUtils;
import com.everis.samplereport.model.CountryCurrencyReportItem;

public class CountryCurrencyReportExporter {
	
	private static final String TIMESTAMP_PATTERN = "yyyy-mm-dd hh:mm:ss";

	private static final Logger log = LoggerFactory.getLogger(CountryCurrencyReportExporter.class);
	
	private static final String REPORT_CSV_SUFFIX = "_report.csv";
	private static final String REPORT_XLS_SUFFIX = "_report.xls";
	
    private static Configuration config;
    static {
        try {
            config = PropertiesUtils.buildConfiguration("config.properties");
        } catch(Exception e) {
        	log.error("Error while loading configuration: ", e);
        }
    }
    
    private static CountryCurrencyReportExporter instance;
    
    private CountryCurrencyReportExporter(){}
    
    public static CountryCurrencyReportExporter getInstance(){
        if(instance == null){
            instance = new CountryCurrencyReportExporter();
        }
        return instance;
    }
    
    public String export(final String format, final List<CountryCurrencyReportItem> reportItems) throws Exception {
    	if(!StringUtils.isBlank(format) && "XLS".equals(format)){
    		return generateExcel(reportItems);
    	} else if(!StringUtils.isBlank(format) && "CSV".equals(format)){
    		return generateCsv(reportItems);
    	} else {
    		log.error("Export format not allowed: " + format);
    		return "";
    	}
    }
    
	private String generateCsv(List<CountryCurrencyReportItem> reportItems) throws FileNotFoundException {
		final List<String> csvRows = CountryCurrencyReportItemCSVMapper.getInstance().toCSV(reportItems);
		final String filePath = config.getString("export.basePath") + new Date().getTime() + REPORT_CSV_SUFFIX;
		CSVUtils.saveAsCsv(csvRows, filePath);
		return filePath;
	}

	public String generateExcel(List<CountryCurrencyReportItem> reportItems) throws Exception {
        final Workbook workbook = new HSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Report");
        
        final CellStyle cellStyle = workbook.createCellStyle();
        final CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(TIMESTAMP_PATTERN));
        
        int rowNum = 0;
        for (final CountryCurrencyReportItem item : reportItems) {
            final Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            
            Cell cell = row.createCell(colNum++);
            cell.setCellValue(item.getCountryName());
            cell = row.createCell(colNum++);
            cell.setCellValue(item.getCountryCode());
            cell = row.createCell(colNum++);
            cell.setCellValue(item.getGmt());
            cell = row.createCell(colNum++);
            cell.setCellValue(item.getCurrency());
            cell = row.createCell(colNum++);
            cell.setCellValue(item.getCurrencyCode());
            
            cell = row.createCell(colNum++);
            cell.setCellValue(item.getItalianTime());
            cell.setCellStyle(cellStyle);
            cell = row.createCell(colNum++);
            cell.setCellValue(item.getLocalTime());
            cell.setCellStyle(cellStyle);
        }
        
    	final String filePath = config.getString("export.basePath") + new Date().getTime() + REPORT_XLS_SUFFIX;
        final FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        workbook.close();
        
        return filePath;
    }
	
}

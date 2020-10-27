/**
 * Copyright © 2018 Mayo Clinic (RSTKNOWLEDGEMGMT@mayo.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpg.util.csv;

import static cpg.util.csv.ExcelToCSVExtractorConfig.ExcelToCSVExtractorOptions.AS_TEXT;
import static cpg.util.csv.ExcelToCSVExtractorConfig.ExcelToCSVExtractorOptions.SEPARATOR;
import static cpg.util.csv.ExcelToCSVExtractorConfig.ExcelToCSVExtractorOptions.SHEET;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelToCSVExtractor {

	private static final Logger logger = LoggerFactory.getLogger(ExcelToCSVExtractor.class);

	public Optional<byte[]> convertExcelToCSV( InputStream source,
	                                           ExcelToCSVExtractorConfig cfg ) {

		if ( source == null ) {
			return Optional.empty();
		}
		try {
			return Optional.of( this.convertToCSV( new XSSFWorkbook( source ),
			                                       cfg.getTyped( SHEET ),
			                                       cfg.getTyped( SEPARATOR ),
			                                       cfg.getTyped( AS_TEXT )  ) );
		} catch ( IOException e ) {
			logger.error(e.getMessage(),e);
			return Optional.empty();
		}
	}

	private String getSeparatorStr(ExcelToCSVExtractorConfig cfg) {
		return cfg.getTyped(SEPARATOR);
	}

	private byte[] convertToCSV(XSSFWorkbook workbook, int index, String separator, boolean asText) {
		final Sheet sheet = workbook.getSheetAt(index);
		final FormulaEvaluator evaluator = new XSSFFormulaEvaluator( workbook );
		int length = sheet.getRow(0).getPhysicalNumberOfCells();
		return saveCSVFile( IntStream.range( 0, sheet.getLastRowNum() + 1 )
		                             .mapToObj( j -> rowToCSV( sheet.getRow(j), length, evaluator, asText ) )
		                             .collect( Collectors.toList() ),
		                    separator );
	}

	private byte[] saveCSVFile( final List<List<String>> csvData, final String separator ) {
		return csvData.stream()
		       .map( line -> String.join(separator, line))
		       .collect( Collectors.joining( System.lineSeparator() ) )
		       .getBytes();
	}


	private List<String> rowToCSV(Row row, int length, FormulaEvaluator evaluator, boolean asText) {
		if ( row == null ) {
			return Collections.emptyList();
		}
		return IntStream.range( 0, length )
		                .mapToObj( row::getCell )
		                .map( xell -> xell != null ? evaluate( xell, evaluator, asText ) : "" )
		                .collect( Collectors.toList() );
	}

	private String evaluate( Cell cell, FormulaEvaluator evaluator, boolean asText ) {
		switch ( cell.getCellType() ) {
			case BLANK:
				return "";
			case STRING:
				return cell.getStringCellValue();
			case BOOLEAN:
				return cell.getBooleanCellValue() ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
			case NUMERIC:
				Number num = cell.getNumericCellValue();
				return "" + ( asText ? "" + num.longValue() : num );
			case FORMULA:
				return evaluator.evaluate( cell ).getStringValue();
			default:
				throw new UnsupportedOperationException( "Unexpected type of cell " + cell.getCellType().name() );
		}
	}

}
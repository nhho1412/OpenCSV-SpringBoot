package com.tiger.demoOpenCSV.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.tiger.demoOpenCSV.model.Country;
import com.tiger.demoOpenCSV.model.CountryAnn;

public class OpenCsvLogic {
	// the delimiter to use for separating entries
	private static final char DEFAULT_SEPARATOR = ',';

	// the character to use for quoted elements
	private static final char DEFAULT_QUOTE = '"';

	// the line number to skip for start reading
	private static final int NUM_OF_LINE_SKIP = 1;

	public static void main(String[] args) throws IOException {
		OpenCsvLogic logic = new OpenCsvLogic();
		
		String csvFilePath = "data.csv";

		logic.csvWriter(csvFilePath);
		logic.csvReader(csvFilePath);
		logic.parseCSVtoBean(csvFilePath);
		logic.ParseCsvToObjectUsingAnnotation(csvFilePath);
	}

	public void csvWriter(String csvFilePath) throws IOException {
		try (Writer writer = new FileWriter(csvFilePath);
				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			String[] headerRecord = { "id", "code", "name" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "US", "United States" });
			csvWriter.writeNext(new String[] { "2", "VN", "Viet Nam" });
			csvWriter.writeNext(new String[] { "3", "AU", "Australia" });

			System.out.println("write successfully\n");
		}
	}

	public void csvReader(String csvFilePath) throws IOException {
		try (Reader reader = new FileReader(csvFilePath); CSVReader csvReader = new CSVReader(reader);) {

			String[] line;
			while ((line = csvReader.readNext()) != null) {
				System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
			}
			System.out.println("read successfully\n");
		}
	}

	public void parseCSVtoBean(String csvFilePath) throws FileNotFoundException {
		ColumnPositionMappingStrategy<Country> strategy = new ColumnPositionMappingStrategy<Country>();
		strategy.setType(Country.class);
		String[] columns = new String[] { "id", "code", "name" };
		strategy.setColumnMapping(columns);

		CsvToBean<Country> csvToBean = new CsvToBeanBuilder<Country>(new FileReader(csvFilePath))
				.withMappingStrategy(strategy).withSeparator(DEFAULT_SEPARATOR).withQuoteChar(DEFAULT_QUOTE)
				.withSkipLines(NUM_OF_LINE_SKIP).withIgnoreLeadingWhiteSpace(true).build();

		List<Country> countries = csvToBean.parse();
		for (Country country : countries) {
			System.out.println(country);
		}
		System.out.println("read CSV to Bean successfully\n");
	}

	public void ParseCsvToObjectUsingAnnotation(String csvFilePath) throws FileNotFoundException, IOException {
		try (Reader reader = new FileReader(csvFilePath);) {
			CsvToBean<CountryAnn> csvToBean = new CsvToBeanBuilder<CountryAnn>(reader).withType(CountryAnn.class)
					.withSkipLines(1)
					.withIgnoreLeadingWhiteSpace(true).build();

			List<CountryAnn> contries = csvToBean.parse();
			for (CountryAnn contry : contries) {
				System.out.println(contry);
			}
			System.out.println("read CSV to Bean Using Annotation successfully\n");
		}
	}
}

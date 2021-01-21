package com.tiger.demoOpenCSV.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

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

	public void csvWriter() throws IOException {
		String csv = "data.csv";
		try (Writer writer = new FileWriter(csv);
				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			String[] headerRecord = { "id", "code", "name" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "US", "United States" });
			csvWriter.writeNext(new String[] { "2", "VN", "Viet Nam" });
			csvWriter.writeNext(new String[] { "3", "AU", "Australia" });
		}
	}

	public void csvReader() throws IOException {
		String csvFile = "data.csv";
		try (Reader reader = new FileReader(csvFile); CSVReader csvReader = new CSVReader(reader);) {
			// Reading Records One by One in a String array
			String[] line;
			while ((line = csvReader.readNext()) != null) {
				System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
			}
		}
	}

	public static void parseCSVtoBean(String filename) throws FileNotFoundException {
		ColumnPositionMappingStrategy<Country> strategy = new ColumnPositionMappingStrategy<Country>();
		strategy.setType(Country.class);
		String[] columns = new String[] { "id", "code", "name" };
		strategy.setColumnMapping(columns);

		CsvToBean<Country> csvToBean = new CsvToBeanBuilder<Country>(new FileReader(filename))
				.withMappingStrategy(strategy).withSeparator(DEFAULT_SEPARATOR).withQuoteChar(DEFAULT_QUOTE)
				.withSkipLines(NUM_OF_LINE_SKIP).withIgnoreLeadingWhiteSpace(true).build();

		List<Country> countries = csvToBean.parse();
		for (Country country : countries) {
			System.out.println(country);
		}
	}

	public static void ParseCsvToObjectUsingAnnotation() throws FileNotFoundException, IOException {
		String csvFile = "data.csv";
		try (Reader reader = new FileReader(csvFile);) {
			CsvToBean<CountryAnn> csvToBean = new CsvToBeanBuilder<CountryAnn>(reader).withType(CountryAnn.class)
					.withSkipLines(1) // skip header
					.withIgnoreLeadingWhiteSpace(true).build();

			List<CountryAnn> contries = csvToBean.parse();
			for (CountryAnn contry : contries) {
				System.out.println(contry);
			}
		}
	}
}

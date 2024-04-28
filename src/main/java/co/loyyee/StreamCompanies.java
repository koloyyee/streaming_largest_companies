package co.loyyee;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class StreamCompanies {
  private static final String filename = "largest_companies.csv";
  private final FileHandler fh = new FileHandler();
  private List<Company> companies = new ArrayList<>();

  public StreamCompanies() {
    this.companies = getCompaniesFromCsv(fh);
  }

  public static void main(String[] args) {

    StreamCompanies sc = new StreamCompanies();
		try {
			sc.covertStringToBigDecimal("JPMorgan Chase", "hello");
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

  public static List<Company> getCompaniesFromCsv(FileHandler fh) {

    List<Company> companies = new ArrayList<>();
    try {
      CSVReader reader = new CSVReader(new FileReader(fh.getResourceFile(filename)));
      CSVIterator iterator = new CSVIterator(reader);
      for (CSVIterator it = iterator; it.hasNext(); ) {
        String[] line = it.next();
        Company company =
            new Company(line[0], line[1], line[2], line[3], line[4], line[5], line[6]);
        companies.add(company);
      }
      return companies;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return companies;
  }

  public List<Company> getCompanies() {
    return companies.subList(1, companies.size());
  }

  public Company getCompanyByOrgName(String orgName) {
    return companies.stream()
        .filter(company -> company.organizationName().equals(orgName))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("No such company"));
  }

  public long getCountByCountry(String country) {
    return companies.stream()
        .filter(company -> company.country().equalsIgnoreCase(country))
        .count();
  }

  public Optional<BigDecimal> covertStringToBigDecimal(String orgName, String column) throws NoSuchFieldException {
    BigDecimal bMultiplier = new BigDecimal("100000000"); // billion multiplier
    BigDecimal mMultiplier = new BigDecimal("1000000"); // million multiplier
    Company target = getCompanyByOrgName(orgName);
    
      String value =
          switch (column.toLowerCase()) {
            case "revenue" -> target.revenue();
            case "profits" -> target.profits();
            case "marketValue" -> target.marketValue();
            case "assets" -> target.assets();
            default -> throw new NoSuchFieldException("Only pick revenue, profits, marketValue, or assets");
          };
        String amount = value.replaceAll("[^0-9.]", "");
        if (value.contains("M")) {
          return Optional.of(new BigDecimal(amount).multiply(mMultiplier));
        } else {
          return Optional.of(new BigDecimal(amount).multiply(bMultiplier));
        }
  }
}
;

package co.loyyee;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/***
 * TODO:
 * 1. Get the average revenue of the top 10 companies {@link #getTop10AvgRevenue()}
 * 2. Get the average profits of the top 10 companies {@link #getTop10AvgProfits()}
 * 3. Get the average revenue of the bottom 10 companies
 * 4. Get the average profits of the bottom 10 companies
 * 5. Get the median revenue of all companies
 * 6. Get the median profits of all companies
 * 7. Get the median assets of all companies
 * 8. Group companies by countries
 * 9. Get company's profit margin.
 * 10. Get company "time by earning" based on profit and market value
 * 11. Get companies ROA
 */

public class StreamCompanies {
  private static final String filename = "largest_companies.csv";
  private static List<Company> companies = new ArrayList<>();
  private final FileHandler fh = new FileHandler();

  public StreamCompanies() {
    this.companies = getCompaniesFromCsv(fh);
  }

  public static void main(String[] args) {}

  /**
   * This is a static method that populate the class value companies, so only 1 set of companies was
   * created. <br>
   *
   * @param fh - {@link FileHandler } access the CSV file from resource directory.
   * @return - List of Companies
   */
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

  /**
   * When iterating the CSV, the header row was included, this method ensure it return all companies
   * excluding the header row (the first row).
   *
   * @return a List of Companies
   */
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

  /**
   * The columns of revenue, profits, assets, and marketValue are all in String with either "M" or
   * "B". <br>
   * M - represents millions<br>
   * B - represents billions<br>
   * convertStringToBigDecimal will convert String to BigDecimal according to the M or B.
   *
   * @param orgName Organization Name e.g.:JPMorgan Chase
   * @param column only accept revenue, profits, assets, or marketValue else throw exception.
   * @return Optional return of possibly null or BigDecimal in millions or billions.
   * @throws NoSuchFieldException
   */
  public Optional<BigDecimal> convertStringToBigDecimal(String orgName, String column)
      throws NoSuchFieldException {
    BigDecimal bMultiplier = new BigDecimal("1000000000"); // billion multiplier
    BigDecimal mMultiplier = new BigDecimal("1000000"); // million multiplier
    Company target = getCompanyByOrgName(orgName);

    String value =
        switch (column.toLowerCase()) {
          case "revenue" -> target.revenue();
          case "profits" -> target.profits();
          case "marketValue" -> target.marketValue();
          case "assets" -> target.assets();
          default ->
              throw new NoSuchFieldException("Only pick revenue, profits, marketValue, or assets");
        };
    String amount = value.replaceAll("[^0-9.]", "");
    if (value.contains("M")) {
      return Optional.of(new BigDecimal(amount).multiply(mMultiplier));
    } else {
      return Optional.of(new BigDecimal(amount).multiply(bMultiplier));
    }
  }
  
  public int convertRank(String rank) {
    return Integer.parseInt(rank.trim().replaceAll(",", ""));
  }

  /** TODO-1: */
  public BigDecimal getTop10AvgRevenue() {
    return getCompanies().stream()
        .limit(10)
        .map(
            company -> {
              try {
                Optional<BigDecimal> bigDecimal =
                    this.convertStringToBigDecimal(company.organizationName(), Company.MonetaryColumn.Revenue.value());
                return bigDecimal.get();
              } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
              }
            })
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(BigDecimal.TEN);
  }
  /**TODO-2 */
  public BigDecimal getTop10AvgProfits() {
    return getCompanies().stream().limit(10).map(company -> {
			try {
				Optional<BigDecimal> profits =  this.convertStringToBigDecimal(company.organizationName(), Company.MonetaryColumn.Profits.value());
        return profits.get();
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.TEN);
  }
  
  public BigDecimal getTopNAvgByColumn(Company.MonetaryColumn column, int size ) {
    return getCompanies().stream().limit(size).map( company -> {
			try {
				Optional<BigDecimal> value = this.convertStringToBigDecimal(company.organizationName(), column.value());
        return value.get();
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(size));
  }
  
  public BigDecimal getBottomNAvgByColumn(Company.MonetaryColumn column, int size) {
    
    return null;
  }
}
;

package co.loyyee.service;

import co.loyyee.utils.FileHandler;
import co.loyyee.dto.Company;
import co.loyyee.utils.Converter;
import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/***
 * TODO:
 * [x] 1. Get the average revenue of the top 10 companies
 * {@link #getTop10AvgRevenue()}
 * <br>
 * [x] 2. Get the average profits of the top 10 companies
 * {@link #getTop10AvgProfits()}
 * <br>
 * 3. Get the average revenue of the bottom N companies
 * {@link #getBottomNAvgByColumn(Company.MonetaryColumn, int)} ()}
 * <br>
 * 4. Get the average profits of the bottom N companies
 * <br>
 * 5. Get the median revenue of all companies
 * <br>
 * 6. Get the median profits of all companies
 * <br>
 * 7. Get the median assets of all companies
 * <br>
 * 8. Group companies by countries
 * <br>
 * 9. Get company's profit margin.
 * <br>
 * 10. Get company "time by earning" based on profit and market value
 * <br>
 * 11. Get companies ROA
 */

public class StreamCompanies {
  private static final String filename = "largest_companies.csv";
  private List<Company> companies;
  private final FileHandler fh = new FileHandler();

  public StreamCompanies() {
    if(companies == null || companies.isEmpty()  ) {
      this.companies = getCompaniesFromCsv(fh);
    }
  }

  public static void main(String[] args) {}

  /**
   * This is a static method that populate the class value companies, so only 1 set of companies was
   * created. <br>
   *
   * @param fh - {@link FileHandler } access the CSV file from resource directory.
   * @return - List of Companies
   */
  private static List<Company> getCompaniesFromCsv(FileHandler fh) {
    List<Company> companies = new ArrayList<>();
    try {
      // CSVReader reader = new CSVReader(new FileReader(fh.getResourceFile(filename)));
      Path file = Paths.get("src/main/resources/" + filename);
      CSVReader reader = new CSVReader(new FileReader(file.toFile()));
      CSVIterator iterator = new CSVIterator(reader);
      /** skipping header row */
      iterator.next();
      for (CSVIterator it = iterator; it.hasNext(); ) {
        String[] line = it.next();
        BigDecimal revenue = Converter.convertStringToBigDecimal(line[3]).orElse(BigDecimal.ZERO);
        BigDecimal profits = Converter.convertStringToBigDecimal(line[4]).orElse(BigDecimal.ZERO);
        BigDecimal assets = Converter.convertStringToBigDecimal(line[5]).orElse(BigDecimal.ZERO);
        BigDecimal marketValue =
            Converter.convertStringToBigDecimal(line[6]).orElse(BigDecimal.ZERO);

        Company company =
            new Company(line[0], line[1], line[2], revenue, profits, assets, marketValue);
        companies.add(company);
//        System.out.println(company);
      }
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
    return companies;
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

  public List<Company> getCompaniesByCountry(String country) {
    return this.getCompanies().stream()
        .filter(company -> company.country().toLowerCase().contains(country.toLowerCase()))
        .collect(Collectors.toList());
  }

  public int convertRank(String rank) {
    return Integer.parseInt(rank.trim().replaceAll(",", ""));
  }

  public BigDecimal getTop10AvgRevenue() {
    return getCompanies().stream()
        .limit(10)
        .map(company -> company.revenue())
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(BigDecimal.TEN);
  }

  public BigDecimal getTop10AvgProfits() {
    return getCompanies().stream()
        .limit(10)
        .map(company -> company.profits())
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(BigDecimal.TEN);
  }

  public BigDecimal getTopNAvgByColumn(Company.MonetaryColumn column, int size) {
    return getCompanies().stream()
        .limit(size)
        .map(company -> switch (column) {
                  case Profits -> company.profits();
                  case Revenue -> company.revenue();
                  case Assets -> company.assets();
                  case MarketValue -> company.marketValue();
                })
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(new BigDecimal(size));
  }

  /** todo -3 */
  public BigDecimal getBottomNAvgByColumn(Company.MonetaryColumn column, int size) {
    return getCompanies().stream()
        .skip(getCompanies().size() - size)
        .map(company -> switch (column) {
          case Profits -> company.profits();
          case Revenue -> company.revenue();
          case Assets -> company.assets();
          case MarketValue -> company.marketValue();
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .divide(new BigDecimal(size));
  }
}

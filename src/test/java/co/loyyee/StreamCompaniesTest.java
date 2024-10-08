package co.loyyee;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import co.loyyee.dto.Company;
import co.loyyee.service.StreamCompanies;
import co.loyyee.utils.Converter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StreamCompaniesTest {
  @Test
  void shouldGetRank1Company() {
    StreamCompanies sc = new StreamCompanies();
    var actual = sc.getCompanies();
    //    JPMorgan Chase,United States,179.93 B,41.8 B,3,744.3 B,399.59 B
    BigDecimal revenue =  Converter.convertStringToBigDecimal("179.93 B").orElseThrow();
    BigDecimal profit =  Converter.convertStringToBigDecimal("41.8 B").orElseThrow();
    BigDecimal assets =  Converter.convertStringToBigDecimal("3,744.3 B").orElseThrow();
    BigDecimal marketValue =  Converter.convertStringToBigDecimal("399.59 B").orElseThrow();
    
    Company expected =
        new Company(
            "1", "JPMorgan Chase", "United States",revenue ,profit ,assets ,marketValue );
    assertEquals(expected, actual.get(0));
  }

  @Test
  void totalCountOfUnitedStatesCompanies() {
    StreamCompanies sc = new StreamCompanies();
    var actual = sc.getCountByCountry("United States");
    assertTrue(actual > 1L);
  }

  @Test
  void shouldReturnZeroIfCountryNotExisted() {
    StreamCompanies sc = new StreamCompanies();
    var actual = sc.getCountByCountry("Not Existed");
    assertTrue(actual == 0);
  }

  @Test
  void convertStringBillionToBigDecimal() {
    StreamCompanies sc = new StreamCompanies();
    sc.getCompanies();
  }

  @Test
  void shouldGetCompanyByOrgName() {
    StreamCompanies sc = new StreamCompanies();
    sc.getCompanies();
    var actual = sc.getCompanyByOrgName("Penn Entertainment");
    BigDecimal revenue =  Converter.convertStringToBigDecimal("6.51 B").orElseThrow();
    BigDecimal profit =  Converter.convertStringToBigDecimal("684.9 M").orElseThrow();
    BigDecimal assets =  Converter.convertStringToBigDecimal("17.04 B").orElseThrow();
    BigDecimal marketValue =  Converter.convertStringToBigDecimal("4.08 B").orElseThrow();
    var expected =
        new Company(
            "1989",
            "Penn Entertainment",
            "United States",
            revenue,
            profit,
            assets,
            marketValue
        );
    assertEquals(actual, expected);
  }

  @Test
  void shouldThrowNoSuchElement() {
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              StreamCompanies sc = new StreamCompanies();
              sc.getCompanies();
              sc.getCompanyByOrgName("NO SUCH THING");
            });
    var actual = exception.getMessage();
    var expected = "No such company";
    assertEquals(expected, actual);
  }

  @Test
  void shouldConvertStringToBillionInBigDecimal() throws NoSuchFieldException {
    StreamCompanies sc = new StreamCompanies();
    BigDecimal actual =  sc.getCompanies().stream().filter(c -> c.organizationName().equals("Penn Entertainment")).findFirst().get().revenue();
    var expected = new BigDecimal("6510000000");
    assertTrue(expected.compareTo(actual) == 0);
  }

  @Test
  void shouldConvertStringToMillionInBigDecimal() throws NoSuchFieldException {
    StreamCompanies sc = new StreamCompanies();
    BigDecimal actual =  sc.getCompanies().stream().filter(c -> c.organizationName().equals("Penn Entertainment")).findFirst().get().profits();
    var expected = new BigDecimal("684900000");
    assertTrue(expected.compareTo(actual) == 0);
  }

//  @Test
  void shouldThrowIfColumnNotAllowed() {
    Exception exception =
        assertThrows(
            NoSuchFieldException.class,
            () -> {
              StreamCompanies sc = new StreamCompanies();
              String actual =  sc.getCompanies().stream().filter(c -> c.organizationName().equals("Penn Entertainment")).findFirst().get().organizationName();
            });
    var actual = exception.getMessage();
    var expected = "Only pick revenue, profits, marketValue, or assets";
    assertTrue(actual.equals(expected));
  }

  @Test
  void shouldGetAvgRevenueOfTop10Companies() {
    StreamCompanies sc = new StreamCompanies();
    long length = sc.getCompanies().stream().limit(10).count();
    assertEquals(length, 10);
    BigDecimal avgRev = sc.getTop10AvgRevenue();
    assertTrue(avgRev.compareTo(BigDecimal.ZERO) != 0);
  }

  @Test
  void shouldGet2777930000000() {
    StreamCompanies sc = new StreamCompanies();
    long length = sc.getCompanies().stream().limit(10).count();
    assertEquals(length, 10);
    BigDecimal avgRev = sc.getTop10AvgRevenue();
    var expected = new BigDecimal("277793000000");
    assertTrue(expected.compareTo(avgRev) == 0);
  }

  @Test
  void shouldGetAvgProfitsOfTop10Companies() {
    StreamCompanies sc = new StreamCompanies();
    long length = sc.getCompanies().stream().limit(10).count();
    Company actualFirst = sc.getCompanies().stream().limit(10).findFirst().get();
    assertEquals(length, 10);
    
    BigDecimal revenue =  Converter.convertStringToBigDecimal("179.93 B").orElseThrow();
    BigDecimal profit =  Converter.convertStringToBigDecimal("41.8 B").orElseThrow();
    BigDecimal assets =  Converter.convertStringToBigDecimal("3,744.3 B").orElseThrow();
    BigDecimal marketValue =  Converter.convertStringToBigDecimal("399.59 B").orElseThrow();
    Company expectedFirst =
        new Company(
            "1", "JPMorgan Chase", "United States",revenue ,profit ,assets ,marketValue );
    assertEquals(expectedFirst, actualFirst);

    BigDecimal actualAvg = sc.getTop10AvgProfits();
    assertTrue(actualAvg.compareTo(BigDecimal.ZERO) != 0);
  }

  @Test
  void avgProfitsShouldBe64904000000() {

    StreamCompanies sc = new StreamCompanies();
    long length = sc.getCompanies().stream().limit(10).count();
    Company actualFirst = sc.getCompanies().stream().limit(10).findFirst().get();
    assertEquals(length, 10);

    BigDecimal revenue =  Converter.convertStringToBigDecimal("179.93 B").orElseThrow();
    BigDecimal profit =  Converter.convertStringToBigDecimal("41.8 B").orElseThrow();
    BigDecimal assets =  Converter.convertStringToBigDecimal("3,744.3 B").orElseThrow();
    BigDecimal marketValue =  Converter.convertStringToBigDecimal("399.59 B").orElseThrow();
    Company expectedFirst =
        new Company(
            "1", "JPMorgan Chase", "United States",revenue ,profit ,assets ,marketValue );
    
    
    assertEquals(expectedFirst, actualFirst);

    BigDecimal actualAvg = sc.getTop10AvgProfits();
    BigDecimal expectedAvg = new BigDecimal("64904000000");
    assertTrue(actualAvg.compareTo(expectedAvg) == 0);
  }

  @Test
  void shouldReturnTop10AvgByColumn() {
    StreamCompanies sc = new StreamCompanies();
    long length = sc.getCompanies().stream().limit(10).count();
    Company actualFirst = sc.getCompanies().stream().limit(10).findFirst().get();
    assertEquals(length, 10);
    
    BigDecimal revenue =  Converter.convertStringToBigDecimal("179.93 B").orElseThrow();
    BigDecimal profit =  Converter.convertStringToBigDecimal("41.8 B").orElseThrow();
    BigDecimal assets =  Converter.convertStringToBigDecimal("3,744.3 B").orElseThrow();
    BigDecimal marketValue =  Converter.convertStringToBigDecimal("399.59 B").orElseThrow();
    Company expectedFirst =
        new Company(
            "1", "JPMorgan Chase", "United States", revenue, profit, assets, marketValue);
    assertEquals(expectedFirst, actualFirst);

    var actual = sc.getTopNAvgByColumn(Company.MonetaryColumn.Revenue, 10);
    var expected = new BigDecimal("277793000000");
    assertTrue(expected.compareTo(actual) == 0);
  }
  
//  @Test
  void shouldGetBottom10Companies() {
    StreamCompanies sc = new StreamCompanies();
    long length =  sc.getCompanies().stream().skip( sc.getCompanies().size() - 10).count();
    assertEquals(length, 10);
    
    Company tenth =  sc.getCompanies().stream().skip( sc.getCompanies().size() - 10).findFirst().get();
   // "1,991",China Merchants Port Group,China,2.28 B,485.4 M,28.9 B,7.51 B
    
    BigDecimal revenue =  Converter.convertStringToBigDecimal("179.93 B").orElseThrow();
    BigDecimal profit =  Converter.convertStringToBigDecimal("41.8 B").orElseThrow();
    BigDecimal assets =  Converter.convertStringToBigDecimal("3,744.3 B").orElseThrow();
    BigDecimal marketValue =  Converter.convertStringToBigDecimal("399.59 B").orElseThrow();
   var expectedTenth = new Company("1,991", "China Merchants Port Group","China",revenue,profit,assets, marketValue);
    assertEquals(expectedTenth, tenth);
  }
  
  @Test
  void shouldGetAvgMarketValueOfTop10Companies() {
    
    StreamCompanies sc = new StreamCompanies();
    long length =  sc.getCompanies().stream().skip( sc.getCompanies().size() - 10).count();
    assertEquals(length, 10);

    BigDecimal avgMV =  sc.getTopNAvgByColumn(Company.MonetaryColumn.MarketValue, 10);
    var expected = new BigDecimal("1002942000000");
    System.out.println(avgMV);
    System.out.println(expected);
   assertTrue(avgMV.compareTo(expected) == 0 );
   
  }
  
  @Test
  void shouldConvertTo1998() {
    String target = "1,988";
    StreamCompanies sc = new StreamCompanies();
    var actual = sc.convertRank(target);
    var expected = 1988;
    assertEquals(expected, actual);
    
  }
  @Test
  void shouldConvertTo998() {
    String target = "988";
    StreamCompanies sc = new StreamCompanies();
    var actual = sc.convertRank(target);
    var expected = 988;
    assertEquals(expected, actual);
    
  }
  
  @Test
  void shouldGet24CompaniesCountFromSweden() {
    StreamCompanies sc = new StreamCompanies();
    int length =  sc.getCompaniesByCountry("Sweden").size();
    var expectedLength = 24;
    assertEquals(expectedLength, length);
  }
  @Test
  void shouldGet714CompaniesCountFromUnited() {
    StreamCompanies sc = new StreamCompanies();
    int length =  sc.getCompaniesByCountry("united").size();
    var expectedLength = 693;
    assertEquals(expectedLength, length, "count countries with 'united' " );
  }
}

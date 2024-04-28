package co.loyyee;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class StreamCompaniesTest {
  @Test
  void shouldGetRank1Company() {
    StreamCompanies sc = new StreamCompanies();
    var actual = sc.getCompanies();
    //    JPMorgan Chase,United States,179.93 B,41.8 B,3,744.3 B,399.59 B
    Company expected =
        new Company(
            "1", "JPMorgan Chase", "United States", "179.93 B", "41.8 B", "3,744.3 B", "399.59 B");
    assertEquals(expected, actual.get(0));
  }
  
  @Test
  void totalCountOfUnitedStatesCompanies() {
    StreamCompanies sc = new StreamCompanies();
    var actual =  sc.getCountByCountry("United States");
    assertTrue(actual > 1L );
  }
  
  @Test
  void shouldReturnZeroIfCountryNotExisted() {
    StreamCompanies sc = new StreamCompanies();
    var actual = sc.getCountByCountry("Not Existed");
    assertTrue(actual == 0 );
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
    var expected = new Company("1,988", "Penn Entertainment", "United States", "6.51 B", "684.9 M", "17.04 B", "4.08 B");
    assertEquals(actual, expected);
  }
  
  @Test
  void shouldThrowNoSuchElement() {
    Exception exception =  assertThrows(NoSuchElementException.class, () -> {
      StreamCompanies sc = new StreamCompanies();
      sc.getCompanies();
      sc.getCompanyByOrgName("NO SUCH THING");
    });
    var actual = exception.getMessage();
    var expected = "No such company";
    System.out.println(actual);
    System.out.println(expected);
    assertEquals(expected, actual);
  }
  
  @Test
  void shouldConvertStringToBillionInBigDecimal() {
    StreamCompanies sc = new StreamCompanies();
    var actual =  sc.covertStringToBigDecimal( "Penn Entertainment","revenue");
    var expected = new BigDecimal("651000000");
    assertTrue(expected.compareTo(actual) == 0);
  }
  
  @Test
  void shouldConvertStringToMillionInBigDecimal() {
    StreamCompanies sc = new StreamCompanies();
    var actual =  sc.covertStringToBigDecimal( "Penn Entertainment","profits");
    var expected = new BigDecimal("648900000");
  
    assertTrue(expected.compareTo(actual) == 0);
  }
  
}

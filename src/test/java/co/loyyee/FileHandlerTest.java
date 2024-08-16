package co.loyyee;

import static org.junit.jupiter.api.Assertions.*;

import co.loyyee.db.FileHandler;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
  @Test
  void shouldGetFile() throws URISyntaxException, FileNotFoundException {
    FileHandler fh = new FileHandler();
    File file = fh.getResourceFile("largest_companies.csv");
    assertNotNull(file);
  }

  @Test
  void shouldThrowNotFoundException() {
    Exception exception =
        assertThrows(
            FileNotFoundException.class,
            () -> {
              FileHandler fh = new FileHandler();
              fh.getResourceFile("no_such_file");
            });
    String expected = "no_such_file not found";
    String actual = exception.getMessage();
    assertTrue(expected.equals(actual));
  }

  @Test
  void shouldGetFirstLineOfFile() throws FileNotFoundException, URISyntaxException {
    FileHandler fh = new FileHandler();
    CSVReader reader = new CSVReader(new FileReader(fh.getResourceFile("largest_companies.csv")));
    try {
      var header = reader.readNext();
      String[] expected = {
        "rank", "organizationName", "country", "revenue", "profits", "assets", "marketValue"
      };
      assertArrayEquals(expected, header);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (CsvValidationException e) {
      throw new RuntimeException(e);
    }
  }

}

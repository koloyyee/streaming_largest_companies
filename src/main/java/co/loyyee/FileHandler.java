package co.loyyee;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FileHandler {

  public void getIOStream(String filename) {
    try {

      InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
      if (is == null) {
        throw new IOException(filename + " not found");
      } else {
        printContent(is);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public void printContent(InputStream is) {

    InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    reader.lines().forEachOrdered(System.out::println);
  }

  public File getResourceFile(String filename) throws URISyntaxException, FileNotFoundException {
    URL resource = this.getClass().getClassLoader().getResource(filename);
    if (resource == null) {
      throw new FileNotFoundException(filename + " not found");
    } else {
      return new File(resource.toURI());
    }
  }
}

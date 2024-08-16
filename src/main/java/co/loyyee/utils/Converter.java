package co.loyyee.utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public class Converter {
  private Converter() {}

  /**
   * The columns of revenue, profits, assets, and marketValue are all in String with either "M" or
   * "B". <br>
   * M - represents millions<br>
   * B - represents billions<br>
   * convertStringToBigDecimal will convert String to BigDecimal according to the M or B.
   * @param value String: revenue/profits/assets/marketValue
   * @return Billions or millions in BigDecimal
   */
  public static Optional<BigDecimal> convertStringToBigDecimal(@NotNull String value) {
    /** billion multiplier*/
    BigDecimal bMultiplier = new BigDecimal("1000000000");
    /** million multiplier*/
    BigDecimal mMultiplier = new BigDecimal("1000000");

    String amount = value.replaceAll("[^0-9.]", "").replace(",", "");
    if (value.contains("M")) {
      return Optional.of(new BigDecimal(amount).multiply(mMultiplier));
    } else {
      return Optional.of(new BigDecimal(amount).multiply(bMultiplier));
    }
  }
}

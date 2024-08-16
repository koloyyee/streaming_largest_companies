package co.loyyee.dto;

import java.math.BigDecimal;

public record Company(
    String rank,
    String organizationName,
    String country,
    BigDecimal revenue,
    BigDecimal profits,
    BigDecimal assets,
    BigDecimal marketValue) {
  public enum MonetaryColumn {
    Revenue,
    Profits,
    Assets,
    MarketValue;

    public String value() {
      if (!this.name().equals("MarketValue")) {
        return this.name().toLowerCase();
      } else {
        return this.name().substring(0, 1).toLowerCase()
            + this.name().substring(1, this.name().length());
      }
    }
  }
}

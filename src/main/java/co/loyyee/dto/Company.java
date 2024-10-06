package co.loyyee.dto;

import io.jstach.jstache.JStache;

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
  public String revenueStr() {
    return revenue().toPlainString();
  }
  public String profitStr() {
    return profits().toPlainString();
  }
  
  public double profitMargin() {
    return profits.divide(revenue, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue();
  }
}

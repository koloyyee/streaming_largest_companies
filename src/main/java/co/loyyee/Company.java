package co.loyyee;

public record Company(
    String rank,
    String organizationName,
    String country,
    String revenue,
    String profits,
    String assets,
    String marketValue) {
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

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
		Revenue, Profits, Assets, MarketValue;
		
		public String value() {
			return this.name().toLowerCase();
		}
	}
	
}

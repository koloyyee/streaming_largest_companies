package co.loyyee.jte;

import java.util.Optional;

import co.loyyee.dto.Company;
import co.loyyee.service.StreamCompanies;
import io.javalin.http.Context;

public class CompaniesPage extends Page {

  private StreamCompanies streamCompanies;

  public CompaniesPage(Context ctx) {
    super(ctx);
  }

  public CompaniesPage(Context ctx, StreamCompanies streamCompanies) {
    super(ctx);
    this.streamCompanies = streamCompanies;
  }

  @Override
  String template() {
    return "fragment/company.jte";
  }

  public String getCompanies(int n) {
    var topN = streamCompanies.getCompanies().stream().limit(n).toList();
    StringBuilder sb = new StringBuilder();
    for (Company c : topN) {
      sb.append(
          String.format(
              """
								<div>
									<a href="/get_company/%s" >
										<h1> %s: %s  </h1>
							 		</a>
									<p>Assets: %.2f</p>
								</div>
							""",
              c.rank(), c.organizationName(), c.country(), c.assets()));
    }
		
		
    return sb.toString();
  }
	
	public String getCompany(String rank) {
		Optional<Company> comp = streamCompanies.getCompanies().stream().filter((com) -> com.rank().equals(rank)).findFirst();
		
		if(comp.isPresent()) {
			Company c = comp.get();
			return String.format(
				"""
								<div>
									<h1> %s: %s  </h1>
									<p>Rank: %s</p>
									<p>Assets: %.2f</p>
									<p>Market Cap: %.2f</p>
								</div>
				""",   c.organizationName(), c.country(), c.rank(), c.assets(), c.marketValue() );
		}
		return "Not Found";
	}
}

package co.loyyee.controller;

import java.util.List;
import java.util.Optional;

import co.loyyee.dto.Company;
import co.loyyee.jte_page.CompaniesPage;
import co.loyyee.jte_page.Page;
import co.loyyee.service.StreamCompanies;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class CompanyController {
  private static StreamCompanies streamCompanies;
  public Handler renderComPage = (ctx) -> {
    Page page = new CompaniesPage(ctx);
    page.render();
  };
  public Handler getCompanies =

      (ctx) -> {
        CompaniesPage page = new CompaniesPage(ctx, streamCompanies);
        int n = Integer.parseInt(ctx.queryParam("n_companies"));
        ctx.html(page.getCompanies(n));
      };
  public Handler getCompany = (ctx) -> {
    CompaniesPage page = new CompaniesPage(ctx);
    String rank = ctx.pathParam("rank");
    System.out.println(rank);
    ctx.html(page.getCompany(rank));
  };

  public CompanyController(StreamCompanies streamCompanies) {
    this.streamCompanies = streamCompanies;
  }

  public Context company(Context ctx) {
    CompaniesPage page = new CompaniesPage(ctx, streamCompanies);
    String rank = ctx.pathParam("rank");
    System.out.println(rank);
    return ctx.html(page.getCompany(rank));
  }

  // apis
  public Context fetchCompanies(Context ctx) {
    List<Company> companies = streamCompanies.getCompanies();
    return ctx.json(companies);
  }

  public Context fetchCompanyByRank(Context ctx) {
    String rank = ctx.pathParam("rank");
    Optional<Company> company = streamCompanies.getCompanies().stream().filter(c -> c.rank().equals(rank)).findFirst();
    if (company.isPresent()) {
      return ctx.json(company.get());
    }
    return ctx.status(HttpStatus.NOT_FOUND);
  }
}

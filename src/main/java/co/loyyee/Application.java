package co.loyyee;

import co.loyyee.controller.CompanyController;
import co.loyyee.dto.Company;
import co.loyyee.jte.IndexPage;
import co.loyyee.service.StreamCompanies;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import java.nio.file.Path;
import java.util.Collections;

public class Application {
  public static final boolean devSystem = System.getProperty("environment") == null;

  public static void main(String[] args) {
    Javalin app =
        Javalin.create(
            config -> {
              config.fileRenderer(new JavalinJte(createTemplateEngine()));
              config.staticFiles.enableWebjars();
              config.staticFiles.add(staticFileConfig -> staticFileConfig.hostedPath = "/public");
            });
    //		DB conn = DB.conn;
    app.start(7070);
    IndexPage page = new IndexPage();
    page.message = "hellooooooooooooooooooooo!";
    app.get("/", (ctx) -> ctx.render("demo.jte", Collections.singletonMap("page", page)));
   
    
    // company
    // monolith
    CompanyController companyController = new CompanyController(new StreamCompanies());
    app.get("/company", companyController.renderComPage);
    app.get("/show_companies", companyController.getCompanies);
    app.get("/get_company/{rank}", companyController::company);
    
    // api for client-server
    app.get("/api/companies", companyController::fetchCompanies);
    app.get("/api/companies/{rank}", companyController::fetchCompanyByRank);
  }

  private static TemplateEngine createTemplateEngine() {
    if (devSystem) {
      DirectoryCodeResolver codeResolver =
          new DirectoryCodeResolver(Path.of("src", "main", "resources", "public"));
      return TemplateEngine.create(codeResolver, ContentType.Html);
    } else {
      return TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
    }
  }
}

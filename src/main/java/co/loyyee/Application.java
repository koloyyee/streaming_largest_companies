package co.loyyee;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.loyyee.controller.CompanyController;
import co.loyyee.dto.User;
import co.loyyee.jte_page.IndexPage;
import co.loyyee.service.StreamCompanies;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import io.jstach.jstachio.JStachio;

public class Application {
  public static final boolean devSystem = System.getProperty("environment") == null;

  public static void main(String[] args) {
    Javalin app = Javalin.create(
        config -> {
          config.fileRenderer(new JavalinJte(createTemplateEngine()));
          config.staticFiles.enableWebjars();
          config.staticFiles.add("src/main/resources/public", Location.EXTERNAL);
        });
    // DB conn = DB.conn;
    app.start(7070);
    IndexPage page = new IndexPage();
    page.message = "hellooooooooooooooooooooo!";
    app.get("/", (ctx) -> ctx.render("demo.jte", Collections.singletonMap("page", page)));
    app.get("/html", (ctx) -> ctx.render("index.html"));

    // company
    // monolith
    CompanyController companyController = new CompanyController(new StreamCompanies());
    app.get("/company", companyController.renderComPage);
    app.get("/show_companies", companyController.getCompanies);
    app.get("/get_company/{rank}", companyController::company);

    // api for client-server
    app.get("/api/companies", companyController::fetchCompanies);
    app.get("/api/companies/{rank}", companyController::fetchCompanyByRank);

    List<User.Item<String>> list = new ArrayList<>();
    list.add(new User.Item("helmet"));
    list.add(new User.Item("shower"));
    var sc = new StreamCompanies();

    // JStachio
    var companies = sc.getCompanies();
    User user = new User("John Doe", 21, new String[] { "Knowns nothing" }, list, companies);
    StringBuilder appendable = new StringBuilder();
    var result = JStachio.render(user, appendable);
    app.get("/user", ctx -> ctx.html(result.toString()));
  }

  private static TemplateEngine createTemplateEngine() {
    if (devSystem) {
      DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src", "main", "resources", "public", "jte"));
      return TemplateEngine.create(codeResolver, ContentType.Html);
    } else {
      return TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
    }
  }
}

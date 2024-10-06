package gg.jte.generated.ondemand;
import co.loyyee.dto.Company;
import co.loyyee.jte.IndexPage;
public final class JtedemoGenerated {
	public static final String JTE_NAME = "demo.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,6,6,6,6,7,7,7,8,8,8,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, IndexPage page, Company company) {
		jteOutput.writeContent("\n<p> ");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(page.message );
		jteOutput.writeContent("</p>\n<p> ");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(company.organizationName());
		jteOutput.writeContent("</p>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		IndexPage page = (IndexPage)params.get("page");
		Company company = (Company)params.get("company");
		render(jteOutput, jteHtmlInterceptor, page, company);
	}
}

package gg.jte.generated.ondemand;
import co.loyyee.jte_page.IndexPage;
public final class JtedemoGenerated {
	public static final String JTE_NAME = "demo.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,4,4,4,4,4,4,4,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, IndexPage page) {
		jteOutput.writeContent("\n<p> ");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(page.message );
		jteOutput.writeContent("</p>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		IndexPage page = (IndexPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}

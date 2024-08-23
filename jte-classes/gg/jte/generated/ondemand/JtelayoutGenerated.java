package gg.jte.generated.ondemand;
import gg.jte.Content;
public final class JtelayoutGenerated {
	public static final String JTE_NAME = "layout.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,8,8,10,10,10,13,13,13,16,16,16,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, Content content) {
		jteOutput.writeContent("\n<html>\n<head>\n        <script src=\"/webjars/htmx.org/2.0.2/dist/htmx.min.js\"></script>\n");
		jteOutput.writeContent("\n\n        <title>");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(title);
		jteOutput.writeContent("</title>\n    </head>\n<body>\n    ");
		jteOutput.setContext("body", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n</body>\n\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		Content content = (Content)params.get("content");
		render(jteOutput, jteHtmlInterceptor, title, content);
	}
}

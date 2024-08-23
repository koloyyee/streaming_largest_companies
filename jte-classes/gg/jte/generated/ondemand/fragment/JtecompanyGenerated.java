package gg.jte.generated.ondemand.fragment;
public final class JtecompanyGenerated {
	public static final String JTE_NAME = "fragment/company.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,0,0,11,11,11,11,11,11,11,11};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		gg.jte.generated.ondemand.JtelayoutGenerated.render(jteOutput, jteHtmlInterceptor, "company", new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <div id=\"replace_me\"> </div>\n    <form>\n        <select name=\"n_companies\"  hx-get=\"/show_companies\" hx-target=\"#replace_me\" >\n            <option ></option>\n            <option value=\"10\">10</option>\n            <option value=\"20\">20</option>\n            <option value=\"30\">30</option>\n        </select>\n        <button class=\"btn primary\" hx-get=\"/show_companies\" hx-target=\"#replace_me\" hx-swap=\"innerHTML\"> click me </button>\n    </form>\n");
			}
		});
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}

package co.loyyee.jte;

import gg.jte.extension.api.JteConfig;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinJte;

import java.util.Collections;

public abstract class Page {
	protected final Context ctx;
	
	public Page(Context ctx) {
		this.ctx = ctx;
	}
	
	abstract String template();
	
	public void render() {
    ctx.render(template(), Collections.singletonMap("page", this));
	}
}

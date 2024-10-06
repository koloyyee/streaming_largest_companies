package co.loyyee.jte_page;

import java.util.Collections;

import io.javalin.http.Context;

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

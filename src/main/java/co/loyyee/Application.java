package co.loyyee;

import co.loyyee.db.DB;
import io.javalin.Javalin;

public class Application {
	
	public static void main(String[] args){
		Javalin app = Javalin.create();
		DB conn = DB.conn;
		
		app.start(7070);
	}
}

package co.loyyee.db;

import co.loyyee.StreamCompanies;
import co.loyyee.dto.Company;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.PreparedBatch;

public class DB {
  public static DB conn = new DB();
  private Jdbi jdbi;

  private DB() {
		jdbi = Jdbi.create("jdbc:sqlite:src/main/resources/db/lg_com.db");
		if(tableSize() != 2000) {
			insertCompanies();
		}
  }

  public int tableSize() {
    return jdbi.withHandle(
        handle -> handle
						.createQuery("SELECT COUNT(*) FROM companies;")
						.mapTo(Integer.class)
						.one());
  }

  public void insertCompanies() {
    // rank,organizationName,country,revenue,profits,assets,marketValue
    jdbi.withHandle(
        handle -> {
          handle.execute(
              """
						CREATE TABLE IF NOT EXISTS \"companies\"
						( rank INTEGER,organizationName VARCHAR, country VARCHAR,
						 revenue INT, profits INT, assets INT, marketValue INT )
						""");
          var stmCom = new StreamCompanies();
          PreparedBatch batch =
              handle.prepareBatch(
                  """
								INSERT INTO \"companies\"
								( rank , organizationName , country ,
								revenue , profits , assets , marketValue )
								VALUES
								(:rank, :organizationName, :country,
								:revenue, :profits, :assets, :marketValue)
							""");

          for (Company comp : stmCom.getCompanies()) {
            batch
                .bind("rank", comp.rank())
                .bind("organizationName", comp.organizationName())
                .bind("country", comp.country())
                .bind("revenue", comp.revenue())
                .bind("profits", comp.profits())
                .bind("assets", comp.assets())
                .bind("marketValue", comp.marketValue())
                .add();
          }
          batch.execute();

          return null;
        });
  }
}

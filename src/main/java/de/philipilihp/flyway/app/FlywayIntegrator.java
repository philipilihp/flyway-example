package de.philipilihp.flyway.app;

import lombok.extern.log4j.Log4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of the hibernate {@link Integrator} to run flyway migrations
 * <b>before</b> hibernate validation.<br>
 *
 * The class must be registered in a file named
 * <b>META-INF/services/org.hibernate.integrator.spi.Integrator</b>.<br>
 *
 * The SQL files must be located at <b>src/main/resources/flyway/postgres/</b>.
 */
@Log4j
public class FlywayIntegrator implements Integrator {

    private static final String SCHEMA = "book_app";
    private static final String SCRIPT_LOCATIONS = "flyway/postgres";

    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {

        Connection connection = null;
        try {
            DataSource dataSource = (DataSource) sessionFactoryImplementor
                    .getProperties()
                    .get("hibernate.connection.datasource" );

            connection = dataSource.getConnection();

            Flyway flyway = new Flyway();
            flyway.setSchemas(SCHEMA);
            flyway.setLocations(SCRIPT_LOCATIONS);
            flyway.setDataSource(dataSource);
            flyway.migrate();

            log.info("Flyway migration finished. Database version is " + flyway.info().current().getVersion());
        }
        catch (SQLException e) {
            throw new RuntimeException("Unable to create JDBC connection.", e);
        }
        finally {
            if (connection != null) {
                JdbcUtils.closeConnection(connection);
            }
        }
    }

    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
        // Nothing to do.
    }

}

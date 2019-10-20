package io.soffa.core.persistence;

import io.soffa.core.exception.TechnicalException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class DatabaseLink {

    private String host;
    private String database;
    private String schema;
    private Integer port;
    private String user;
    private String password;
    private String driver;
    private String platform;
    private String dialect;
    private String jdbcUrl;

    private DatabaseLink(String host, String database, String schema, Integer port, String user, String password, String driver, String platform, String dialect) {
        this.host = host;
        this.database = database;
        this.schema = schema;
        this.port = port;
        this.user = user;
        this.password = password;
        this.driver = driver;
        this.platform = platform;
        this.dialect = dialect;

        if (POSTGRES_DRIVER.equals(driver)) {
            this.jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s?currentSchema=%s", host, port, database, schema);
        } else if (H2_DRIVER.equals(driver)) {
            if (StringUtils.isBlank(schema)) {
                this.jdbcUrl = String.format("jdbc:h2:mem:%s;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;", database);
            } else {
                this.jdbcUrl = String.format("jdbc:h2:mem:%s;INIT=CREATE SCHEMA IF NOT EXISTS %s;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;", database, schema);
            }
        }

    }

    public boolean hasSchema() {
        return StringUtils.isNotBlank(schema);
    }

    public boolean isH2() {
        return H2_DRIVER.equals(driver);
    }

    public static DatabaseLink parse(String url) {

        Matcher m1 = REGEX_TEST_DB.matcher(url);
        if (m1.find()) {
            return new DatabaseLink(null, m1.group(1), m1.group(3), null, "sa", null, H2_DRIVER, "h2", H2_DIALECT);
        }
        // engine://user(:password)?@host(:port)/db(?schema=xyz)?
        Matcher m2 = REGEX.matcher(url);
        if (!m2.find()) {
            throw new TechnicalException("Invalid database url: " + url);
        }
        String engine = m2.group(1);
        String user = m2.group(2);
        String password = m2.group(4);
        String host = m2.group(5);
        String sPort = m2.group(7);
        String database = m2.group(8);
        String schema = m2.group(10);

        String driver;
        String dialect;
        String platform;


        int port = 0;
        if (StringUtils.isNotBlank(sPort)) {
            port = Integer.parseInt(sPort);
        }

        if (engine.equals("h2")) {
            driver = H2_DRIVER;
            platform = "h2";
            dialect = H2_DIALECT;
            if (port == 0) {
                port = 9092;
            }
        } else if (engine.equals("postgres")) {
            driver = POSTGRES_DRIVER;
            platform = POSTGRES_DIALECT;
            dialect = POSTGRES_DIALECT;
            if (port == 0) {
                port = 5432;
            }
            if (schema == null) {
                schema = "public";
            }
        } else {
            throw new TechnicalException("Invalid database engine: " + engine);
        }

        return new DatabaseLink(host, database, schema, port, user, password, driver, platform, dialect);
    }

    public static final String H2_DRIVER = "org.h2.Driver";
    public static final String H2_DIALECT = "org.hibernate.dialect.H2Dialect";

    public static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    public static final String POSTGRES_DIALECT = "org.hibernate.dialect.PostgreSQL95Dialect";

    private static final Pattern REGEX = Pattern.compile("^(postgres|h2)://([^:]+)(:([^@]+))?@([^:]+)(:([0-9]+))?/([^?]+)(\\?schema=([^&]+))?$");
    private static final Pattern REGEX_TEST_DB = Pattern.compile("^mem://([^?]+)(\\?schema=([^&]+))?$");

}

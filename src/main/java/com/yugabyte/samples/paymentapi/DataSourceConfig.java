package com.yugabyte.samples.paymentapi;

import com.yugabyte.ysql.YBClusterAwareDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
  @Value("${yb.host}")
  private String hostName;
  @Value("${yb.port}")
  private String port;

  @Value("${yb.username}")
  private String user;

  @Value("${yb.password}")
  private String password;

  @Value("${yb.additional-endpoints}")
  private String additionalEndpoints;
  @Value("${yb.topology-keys}")
  private String topologyKeys;
  @Value("${yb.max-pool-size}")
  private String maxPoolSize;

  @Value("${yb.max-lifetime}")
  String maxLifetime;

  @Bean
  DataSource dataSource() {
    Properties p = new Properties();
    p.setProperty("dataSourceClassName", YBClusterAwareDataSource.class.getName());
    p.setProperty("dataSource.serverName", hostName);
    p.setProperty("dataSource.portNumber", port);
    p.setProperty("dataSource.user", user);
    p.setProperty("dataSource.password", password);
    p.setProperty("dataSource.additionalEndpoints", additionalEndpoints);
    p.setProperty("dataSource.topologyKeys", topologyKeys);
    p.setProperty("connectionInitSql", "select version();");
    p.setProperty("connectionTestQuery", "select count(*) from yb_servers();");
    p.setProperty("poolName", "ybcp-1");
    p.setProperty("maxLifetime", maxLifetime);
    p.setProperty("maximumPoolSize", maxPoolSize);
    p.setProperty("autoCommit", "true");

    HikariConfig hikariConfig = new HikariConfig(p);
    return new HikariDataSource(hikariConfig);
  }
}

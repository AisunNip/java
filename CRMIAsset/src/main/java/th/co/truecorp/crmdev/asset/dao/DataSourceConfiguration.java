package th.co.truecorp.crmdev.asset.dao;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "dcrmDS")
    @ConfigurationProperties(prefix = "spring.dcrmds")
    public DataSource dcrmDS() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "olcrmDS")
    @ConfigurationProperties(prefix = "spring.olcrm")
    public DataSource olcrmDS() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "cklistDS")
    @ConfigurationProperties(prefix = "spring.cklist")
    public DataSource cklistDS() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "dcrmbatchrecDS")
    @ConfigurationProperties(prefix = "spring.dcrmbatchrecds")
    public DataSource dcrmbatchrecDS() {
        return DataSourceBuilder.create().build();
    }
}
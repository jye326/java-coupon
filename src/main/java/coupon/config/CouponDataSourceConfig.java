package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CouponDataSourceProperties.class)
public class CouponDataSourceConfig {

    @Bean(name = "writerDataSource")
    public DataSource writerDataSource(CouponDataSourceProperties properties){
        return new HikariDataSource(properties.getWriter());
    }

    @Bean(name = "readerDataSource")
    public DataSource readerDataSource(CouponDataSourceProperties properties){
        return new HikariDataSource(properties.getReader());
    }
}

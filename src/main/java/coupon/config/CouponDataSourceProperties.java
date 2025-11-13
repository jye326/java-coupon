package coupon.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coupon.datasource")
@Getter @Setter
public class CouponDataSourceProperties {

    private HikariConfig writer;
    private HikariConfig reader;
}

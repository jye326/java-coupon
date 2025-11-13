package coupon.config;

import coupon.infra.DataSourceType;
import coupon.infra.ReadOnlyDataSourceRouter;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class RoutingDataSourceConfiguration {

    @Bean
    public DataSource routingDataSource(
            @Qualifier("writerDataSource") DataSource writer,
            @Qualifier("readerDataSource") DataSource reader
    ){
        ReadOnlyDataSourceRouter routing = new ReadOnlyDataSourceRouter();

        Map<Object, Object> dataSourceMap = Map.of(
                DataSourceType.WRITER, writer,
                DataSourceType.READER, reader
        );

        routing.setTargetDataSources(dataSourceMap);
        routing.setDefaultTargetDataSource(writer);

        return routing;
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}

package coupon.infra;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReadOnlyDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("DETERMINE!!!!");
        System.out.println("getCoupon() readOnly = " +
                org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        if(TransactionSynchronizationManager.isCurrentTransactionReadOnly()){
            System.out.println("READ ONLY!!!!");
            return DataSourceType.READER;
        }
        return DataSourceType.WRITER;
    }
}

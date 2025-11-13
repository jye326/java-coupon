package coupon.coupon;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CouponService {

    private CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon){
        couponRepository.save(coupon);
    }

    // Replica DB
    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        System.out.println("getCoupon() readOnly = " +
                org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        return couponRepository.findById(id).get();
    }
}

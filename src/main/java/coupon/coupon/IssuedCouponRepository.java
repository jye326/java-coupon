package coupon.coupon;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

    long countByCouponIdAndMemberId(Long couponId, Long memberId);

    List<IssuedCoupon> findByCouponIdAndMemberId(Long couponId, Long memberId);

    List<IssuedCoupon> findAllByMemberId(Long memberId);
}

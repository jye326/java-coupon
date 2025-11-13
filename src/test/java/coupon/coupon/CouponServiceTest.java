package coupon.coupon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @DisplayName("복제지연테스트")
    @Test
    void testReplicationLag() {
        // given
        Coupon coupon = new Coupon(1000, 10000);

        // when
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        // then
        Assertions.assertThat(savedCoupon).isNotNull();
    }
}

package coupon.coupon;

import coupon.member.Member;
import coupon.member.MemberRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IssuedCouponRepository issuedCouponRepository;

    @DisplayName("복제지연테스트")
    @Test
    void testReplicationLag() {
        // given
        Coupon coupon = new Coupon(1000, 10000);

        // when
        couponService.create(coupon);

        // then
        Assertions.assertThatThrownBy(() -> couponService.getCoupon(coupon.getId()))
                .isInstanceOf(Exception.class);   // 복제 지연으로 스키마 생성도 아직 안됨.
    }

    @DisplayName("회원은 동일 쿠폰을 최대 5장까지 발급받을 수 있다")
    @Test
    void issueCoupon_upToFive_isAllowed() {
        // given
        Coupon coupon = new Coupon(1000, 10000);
        couponService.create(coupon); // 영속화
        Member savedMember = memberRepository.save(new Member("회원1"));

        // when
        for (int i = 0; i < 5; i++) {
            couponService.issue(coupon, savedMember);
        }

        // then
        List<IssuedCoupon> issuedCoupons =
                issuedCouponRepository.findByCouponIdAndMemberId(coupon.getId(), savedMember.getId());

        Assertions.assertThat(issuedCoupons).hasSize(5);
    }

    @DisplayName("회원이 동일 쿠폰을 5장을 초과하여 발급받으려 하면 예외가 발생한다")
    @Test
    void issueCoupon_overFive_throwsException() {
        // given
        Coupon coupon = new Coupon(1000, 10000);
        couponService.create(coupon);
        Member savedMember = memberRepository.save(new Member("회원1"));

        // 이미 5장 발급
        for (int i = 0; i < 5; i++) {
            couponService.issue(coupon, savedMember);
        }

        // when
        ThrowableAssert.ThrowingCallable callable =
                () -> couponService.issue(coupon, savedMember);

        // then
        Assertions.assertThatThrownBy(callable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("동일 쿠폰은 최대 5개 소지가능");
    }
}

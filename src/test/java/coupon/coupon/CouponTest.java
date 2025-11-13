package coupon.coupon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("이름 테스트")
    @Nested
    class NameTest{
        @DisplayName("이름은 반드시 존재해야 한다")
        @Test
        void testCouponNameExist() {
            // given
            Assertions.assertThatThrownBy(() -> Coupon.builder()
                    .build()).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("이름은 30자 이하여야 한다")
        @Test
        void testCouponNameLength() {
            // given
            Assertions.assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("012345678901234567890123456789초과")
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("올바른 이름 생성")
        @Test
        void validName() {
            // given
            Assertions.assertThatCode(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .build()
            ).doesNotThrowAnyException();
        }
    }

}

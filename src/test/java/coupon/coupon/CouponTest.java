package coupon.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("이름 테스트")
    @Nested
    class NameTest {
        @DisplayName("이름은 반드시 존재해야 한다")
        @Test
        void testCouponNameExist() {
            // given
            assertThatThrownBy(() -> Coupon.builder()
                    .build()).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("이름은 30자 이하여야 한다")
        @Test
        void testCouponNameLength() {
            // given
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("012345678901234567890123456789초과")
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("올바른 이름 생성")
        @Test
        void validName() {
            // given
            assertThatCode(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .build()
            ).doesNotThrowAnyException();
        }
    }

    @DisplayName("할인 금액 테스트")
    @Nested
    class DiscountAmountTest {
        @DisplayName("할인 금액은 1,000원 이상이어야 한다.")
        @Test
        void testDiscountAmountPriceRange1() {
            // given
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(500)
                            .build()
            )
                    .isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("할인 금액은 10,000원 이하여야 한다.")
        @Test
        void testDiscountAmountPriceRange2() {
            // given
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(10500)
                            .build()
            )
                    .isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("할인 금액은 500원 단위로 설정할 수 있다.")
        @Test
        void testDiscountAmountPriceUnit() {
            // given
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(1100)
                            .build()
            )
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @DisplayName("최소 주문 금액 테스트")
    @Nested
    class MinOrderPriceTest {

        @DisplayName("최소 주문 금액은 5,000원 이상이어야 한다.")
        @Test
        void minOrderPriceLowerBound() {
            // given & when & then
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(5_000)
                            .minOrderPrice(4_000) // 5,000 미만
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("최소 주문 금액은 100,000원 이하여야 한다.")
        @Test
        void minOrderPriceUpperBound() {
            // given & when & then
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(5_000)
                            .minOrderPrice(100_001) // 100,000 초과
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("올바른 최소 주문 금액은 예외가 발생하지 않는다.")
        @Test
        void validMinOrderPrice() {
            // given & when & then
            assertThatCode(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(5_000)
                            .minOrderPrice(50_000) // 5,000 이상 100,000 이하
                            .build()
            ).doesNotThrowAnyException();
        }

        @DisplayName("최소 주문 금액이 null이면 검증하지 않는다.")
        @Test
        void nullableMinOrderPrice() {
            // given & when & then
            assertThatCode(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(5_000)
                            .minOrderPrice(null)
                            .build()
            ).doesNotThrowAnyException();
        }
    }

    @DisplayName("할인율(할인 비율) 테스트")
    @Nested
    class DiscountPercentageTest {

        @DisplayName("할인율은 3% 이상이어야 한다.")
        @Test
        void discountPercentageLowerBound() {
            // given
            // discountAmount / minOrderPrice < 3% 인 상황을 가정
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(2_000)
                            .minOrderPrice(100_000) // 2%
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("할인율은 20% 이하여야 한다.")
        @Test
        void discountPercentageUpperBound() {
            // given
            // discountAmount / minOrderPrice > 20% 인 상황을 가정
            assertThatThrownBy(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(10_000)
                            .minOrderPrice(30_000) // 약 33%
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("할인 금액과 최소 주문 금액이 모두 존재할 때만 할인율을 검증한다.")
        @Test
        void discountPercentageNotCheckedIfAnyNull() {
            // 할인 금액이 null인 경우
            assertThatCode(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(null)
                            .minOrderPrice(50_000)
                            .build()
            ).doesNotThrowAnyException();

            // 최소 주문 금액이 null인 경우
            assertThatCode(
                    () -> Coupon.builder()
                            .name("올바른 쿠폰 이름")
                            .discountAmount(5_000)
                            .minOrderPrice(null)
                            .build()
            ).doesNotThrowAnyException();
        }

        @DisplayName("할인율이 3% 이상 20% 이하라면 정상 생성된다.")
        @Test
        void validDiscountPercentage() {
            // given
            Coupon coupon = Coupon.builder()
                    .name("올바른 쿠폰 이름")
                    .discountAmount(10_000)
                    .minOrderPrice(50_000) // 20%
                    .build();

            // then
            assertThat(coupon.getDiscountPercentage())
                    .isNotNull()
                    .isBetween(3, 20);
        }
    }

    @DisplayName("발급 기간 테스트")
    @Nested
    class IssueDateTest {

        @DisplayName("startDay와 endDay가 둘 다 null이면 유효하다.")
        @Test
        void bothNullValid() {
            assertThatCode(() ->
                    Coupon.builder()
                            .name("쿠폰")
                            .discountAmount(1000)
                            .minOrderPrice(20000)
                            .build()
            ).doesNotThrowAnyException();
        }

        @DisplayName("startDay만 존재하면 예외 발생")
        @Test
        void onlyStartDayInvalid() {
            assertThatThrownBy(() ->
                    Coupon.builder()
                            .name("쿠폰")
                            .discountAmount(1000)
                            .minOrderPrice(20000)
                            .startDay(LocalDate.of(2025, 1, 1))
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("endDay만 존재하면 예외 발생")
        @Test
        void onlyEndDayInvalid() {
            assertThatThrownBy(() ->
                    Coupon.builder()
                            .name("쿠폰")
                            .discountAmount(1000)
                            .minOrderPrice(20000)
                            .endDay(LocalDate.of(2025, 1, 10))
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("endDay가 startDay보다 이전이면 예외 발생")
        @Test
        void endBeforeStartInvalid() {
            assertThatThrownBy(() ->
                    Coupon.builder()
                            .name("쿠폰")
                            .discountAmount(1000)
                            .minOrderPrice(20000)
                            .startDay(LocalDate.of(2025, 1, 10))
                            .endDay(LocalDate.of(2025, 1, 5)) // end < start
                            .build()
            ).isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("startDay와 endDay가 같아도 유효하다")
        @Test
        void sameDateValid() {
            assertThatCode(() ->
                    Coupon.builder()
                            .name("쿠폰")
                            .discountAmount(1000)
                            .minOrderPrice(20000)
                            .startDay(LocalDate.of(2025, 1, 10))
                            .endDay(LocalDate.of(2025, 1, 10)) // same date
                            .build()
            ).doesNotThrowAnyException();
        }

        @DisplayName("endDay가 startDay보다 이후면 유효하다")
        @Test
        void endAfterStartValid() {
            assertThatCode(() ->
                    Coupon.builder()
                            .name("쿠폰")
                            .discountAmount(1000)
                            .minOrderPrice(20000)
                            .startDay(LocalDate.of(2025, 1, 1))
                            .endDay(LocalDate.of(2025, 1, 5)) // end > start
                            .build()
            ).doesNotThrowAnyException();
        }
    }

}

package coupon.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer discountAmount;

    private Integer discountPercentage;

    private Integer minOrderPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate startDay;

    private LocalDate endDay;

    // 예시 테스트용 쿠폰 생성자
    public Coupon(Integer discountAmount, Integer minOrderPrice) {
        this.name = "테스트용";
        this.discountAmount = discountAmount;
        this.minOrderPrice = minOrderPrice;
    }

    // 생성 조건 적용을 위한 빌더 클래스 (@Builder 활용)
    public static class CouponBuilder {

        public Coupon build() {
            if (this.name == null || name.length() > 30) {
                throw new IllegalStateException("이름 규칙 위반");
            }
            if (invalidDiscountAmount()) {
                throw new IllegalStateException("할인 금액 규칙 위반");
            }
            if (invalidMinOrderPrice()) {
                throw new IllegalStateException("최소 주문 금액 규칙 위반");
            }
            if (invalidDiscountPercentage()) {
                throw new IllegalStateException("할인율 규칙 위반 : "+ getDiscountPercentage());
            }
            if (invalidIssueDate()) {
                throw new IllegalStateException("발급 기간 규칙 위반");
            }
            return new Coupon(null, this.name, this.discountAmount, getDiscountPercentage(), this.minOrderPrice,
                    this.category, this.startDay, this.endDay);
        }

        private boolean invalidDiscountAmount() {
            if (this.discountAmount == null) {
                return false;
            }
            return this.discountAmount < 1000
                    || this.discountAmount > 10000
                    || this.discountAmount % 500 != 0;
        }

        private boolean invalidMinOrderPrice() {
            if (this.minOrderPrice == null) {
                return false;
            }
            return minOrderPrice < 5000 || minOrderPrice > 100000;
        }

        private boolean invalidDiscountPercentage() {
            if (this.discountAmount == null || this.minOrderPrice == null) {
                return false;
            }
            Integer percentage = getDiscountPercentage();
            return percentage < 3 || percentage > 20;
        }

        private Integer getDiscountPercentage() {
            if (this.discountAmount == null || this.minOrderPrice == null) {
                return null;
            }
            return Math.round(this.discountAmount * 100 / this.minOrderPrice);
        }

        private boolean invalidIssueDate() {
            if (this.startDay == null && this.endDay == null) {
                return false;
            }
            if (this.startDay == null || this.endDay == null) {
                return true;
            }
            return endDay.isBefore(startDay);
        }
    }
}

package coupon.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Double discountPercent;

    private Integer minOrderPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalTime startDay;

    private LocalTime endDay;

    // 테스트용 쿠폰 생성자
    public Coupon(Integer discountAmount, Integer minOrderPrice){
        this.name = "테스트용";
        this.discountAmount = discountAmount;
        this.minOrderPrice = minOrderPrice;
    }

    // 생성 조건 적용을 위한 빌더 클래스 (@Builder 활용)
    public static class CouponBuilder {

        public Coupon build() {
            if(this.name == null || name.length() > 30){
                throw new IllegalStateException("이름 규칙 위반");
            }
//            if()
            return new Coupon(null, this.name, this.discountAmount, this.discountPercent, this.minOrderPrice, this.category, this.startDay, this.endDay);
        }
    }

}

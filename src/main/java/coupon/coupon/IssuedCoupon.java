package coupon.coupon;

import coupon.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long couponId;

    private Long memberId;

    private Boolean used;

    private LocalDateTime issuedDateTime;

    private LocalDateTime expiredDateTime;

    public IssuedCoupon(Coupon coupon, Member member) {
        this.couponId = coupon.getId();
        this.memberId = member.getId();
        this.used = false;
        this.issuedDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.expiredDateTime = issuedDateTime.plusDays(7).toLocalDate().atStartOfDay().minusNanos(1);
    }
}

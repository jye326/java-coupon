package coupon.coupon;

import coupon.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Coupon coupon;

    @OneToOne
    private Member member;

    private Boolean used;

    private LocalDateTime issuedDateTime;

    private LocalDateTime expiredDateTime;

    public IssuedCoupon(Coupon coupon, Member member) {
        this.coupon = coupon;
        this.member = member;
        this.used = false;
        this.issuedDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.expiredDateTime = issuedDateTime.plusDays(7).toLocalDate().atStartOfDay().minusNanos(1);
    }
}

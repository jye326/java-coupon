package coupon.coupon;

import coupon.member.Member;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CouponService {

    private CouponRepository couponRepository;
    private IssuedCouponRepository issuedCouponRepository;

    @Transactional
    public void create(Coupon coupon){
        couponRepository.save(coupon);
    }

    // Replica DB
    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id).get();
    }

    public void issue(Coupon coupon, Member member){
        long count = issuedCouponRepository.countByCouponIdAndMemberId(coupon.getId(), member.getId());
        if(count>=5){
            throw new IllegalStateException("동일 쿠폰은 최대 5개 소지가능");
        }
        IssuedCoupon issuedCoupon = new IssuedCoupon(coupon, member);
        issuedCouponRepository.save(issuedCoupon);
    }

    public List<Coupon> findAllCoupons(Member member){
        List<IssuedCoupon> issuedCoupons = issuedCouponRepository.findAllByMemberId(member.getId());
        List<Long> couponIds = issuedCoupons.stream()
                .map(IssuedCoupon::getCouponId)
                .toList();
        Map<Long, Coupon> couponMap = couponIds.stream().collect();
        List<Coupon> coupons = couponRepository.findAllById(couponIds);

        return coupons;
    }
}

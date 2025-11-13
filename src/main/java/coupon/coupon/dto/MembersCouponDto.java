package coupon.coupon.dto;

import coupon.coupon.Coupon;
import coupon.coupon.IssuedCoupon;

public record MembersCouponDto(
        Coupon coupon,
        IssuedCoupon issuedCoupon
) {
}

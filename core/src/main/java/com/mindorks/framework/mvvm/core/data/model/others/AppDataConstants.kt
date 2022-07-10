package com.mindorks.framework.mvvm.core.data.model.others

class AppDataConstants {

    object StatusBook{
        const val STATUS_APPROVED = "approved"
        const val STATUS_REJECT = "reject"
        const val STATUS_PENDING = "pending"
        const val STATUS_CANCEL = "cancel"
    }
    object Register {
        const val user = "user"
        const val uname = "username"
        const val gender = "gender"
        const val password = "password"
        const val email = "email"
        const val firstName = "first_name"
        const val lastName = "last_name"
        const val usertype = "user_type"
    }

    object UserApply {
        const val serviceId = "service_id"
        const val description = "description"
        const val couponId = "coupon_id"
        const val customerId = "customer_id"
    }

    object PembinaApproval {
        const val id = "id"
        const val status = "status"
        const val reason = "reason"
    }

}
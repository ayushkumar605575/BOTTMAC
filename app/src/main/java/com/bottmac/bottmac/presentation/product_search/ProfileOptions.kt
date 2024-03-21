package com.bottmac.bottmac.presentation.product_search

sealed class ProfileOptions(
    val title: String,
    val subTitle: String,
) {
    data object MyOrders: ProfileOptions(
        "My Orders",
        "Recent Orders"
    )
    data object ShippingAddress: ProfileOptions(
        "Shipping Address",
        "Your Addresses"
    )
    data object EditProfile: ProfileOptions(
        "Edit Profile",
        ""
    )
}

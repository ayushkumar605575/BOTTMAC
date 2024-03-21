package com.bottmac.bottmac.presentation.profile

sealed class ProfileOptions(
    val routes: String,
    val title: String,
    val subTitle: String,
) {
    data object MyOrders: ProfileOptions(
        "myOrders",
        "My Orders",
        "Recent Orders"
    )
    data object ShippingAddress: ProfileOptions(
        "shippingAddress",
        "Shipping Address",
        "Your Addresses"
    )
    data object EditProfile: ProfileOptions(
        "editProfile",
        "Edit Profile",
        "Update Personal Information"
    )
}

package com.careem.mt.adma.ui.signin.login.model

import java.io.Serializable

data class UserModel(
    val user: User, val cityId: Int, val drivingLicenceNumber: String, val homeAddress: String,
    val joinDate: Any, val journeyHours: Int, val phoneNumber: String, val statusId: Int,
    val suppierId: Int, val totalTrips: Int) : Serializable

data class User(val userId: Int, val firstName: String, val lastName: String)

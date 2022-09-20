package com.elkenany.entities.home_data

data class ContactUsData(
    val offices: List<Office?>,
)

data class Office(
    val id: Long?,
    val name: String?,
    val address: String?,
    val latitude: String?,
    val longitude: String?,
    val desc: String?,
    val status: Long?,
    val phones: List<Phone?>,
    val emails: List<Email?>,
    val mobiles: List<Mobile?>,
    val faxs: List<Fax?>,
    val selected: Long?,
)

data class Phone(
    val phone: String?,
)

data class Email(
    val email: String?,
)

data class Mobile(
    val mobile: String?,
)

data class Fax(
    val fax: String?,
)

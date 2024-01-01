package com.recommend_tour.data

import com.google.gson.annotations.SerializedName

open class IntroductionItem(
    @SerializedName("contentid")
    val contentId: String? = null,
    @SerializedName("contenttypeid")
    val contenttypeid: String? = null,
)

/**
 * SERVER
 * -> JSON STRING
 * -> STRING -> JSON ELEMENT key value string map MAP<STRING, STRING>
 *     {
 *        contentId: "1234"
 *        contenttypeid: "12"
 *       chkcreditcard : ~~
 *       chkpet: ~~
 *     }
 * -> IntroductionItem ���⼭ reflection.
 * -> TourDetailIntroduction("1234", "12", "~~", "~~")
 */

class TourDetailIntroduction(
    @SerializedName("accomcount") val accomcount: String? = null,
    @SerializedName("chkbabycarriage") val chkbabycarriage: String? = null,
    @SerializedName("chkcreditcard") val chkcreditcard: String? = null,
    @SerializedName("chkpet") val chkpet: String? = null,
    @SerializedName("expagerange") val expagerange: String? = null,
    @SerializedName("expguide") val expguide: String? = null,
    @SerializedName("heritage1") val heritage1: String? = null,
    @SerializedName("heritage2") val heritage2: String? = null,
    @SerializedName("heritage3") val heritage3: String? = null,
    @SerializedName("infocenter") val infocenter: String? = null,
    @SerializedName("opendate") val opendate: String? = null,
    @SerializedName("parking") val parking: String? = null,
    @SerializedName("restdate") val restdate: String? = null,
    @SerializedName("useseason") val useseason: String? = null,
    @SerializedName("usetime") val usetime: String? = null,
    contentId: String?,
    contenttypeid: String?,
) : IntroductionItem(
    contentId,
    contenttypeid
)

class CulturalIntroduction(
    val accomcountculture: String?,
    val chkbabycarriageculture: String?,
    val chkcreditcardculture: String?,
    val chkpetculture: String?,
    val discountinfo: String?,
    val infocenterculture: String?,
    val parkingculture: String?,
    val parkingfee: String?,
    val restdateculture: String?,
    val usefee: String?,
    val usetimeculture: String?,
    val scale: String?,
    val spendtime: String?,
    contentId: String?,
    contenttypeid: String?,
) : IntroductionItem(
    contentId,
    contenttypeid
)

class FestivalIntroduction(
    val agelimit: String? = null,
    val bookingplace: String? = null,
    val discountinfofestival: String? = null,
    val eventenddate: String? = null,
    val eventhomepage: String? = null,
    val eventplace: String? = null,
    val eventstartdate: String? = null,
    val festivalgrade: String? = null,
    val placeinfo: String? = null,
    val playtime: String? = null,
    val program: String? = null,
    val spendtimefestival: String? = null,
    val sponsor1: String? = null,
    val sponsor1tel: String? = null,
    val sponsor2: String? = null,
    val sponsor2tel: String? = null,
    val subevent: String? = null,
    val spendtime: String? = null,
    val usetimefestival: String? = null,
    contentId: String? = null,
    contenttypeid: String? = null,
) : IntroductionItem(
    contentId,
    contenttypeid
)

class TourCourseIntroduction(
    val distance: String? = null,
    val infocentertourcourse: String? = null,
    val schedule: String? = null,
    val taketime: String? = null,
    val theme: String? = null,
    contentId: String? = null,
    contenttypeid: String? = null,
) : IntroductionItem(
    contentId,
    contenttypeid
)

class LeisureIntroduction(
    val accomcountleports: String? = null,
    val chkbabycarriageleports: String? = null,
    val chkcreditcardleports: String? = null,
    val chkpetleports: String? = null,
    val expagerangeleports: String? = null,
    val infocenterleports: String? = null,
    val openperiod: String? = null,
    val parkingfeeleports: String? = null,
    val parkingleports: String? = null,
    val reservation: String? = null,
    val restdateleports: String? = null,
    val scaleleports: String? = null,
    val usefeeleports: String? = null,
    val usetimeleports: String? = null,
    contentId: String? = null,
    contenttypeid: String? = null,
) : IntroductionItem(
    contentId,
    contenttypeid
)

class LodgingIntroduction(
    val accomcountlodging: String? = null,
    val benikia: String? = null,
    val checkintime: String? = null,
    val checkouttime: String? = null,
    val chkcooking: String? = null,
    val foodplace: String? = null,
    val goodstay: String? = null,
    val hanok: String? = null,
    val infocenterlodging: String? = null,
    val parkinglodging: String? = null,
    val pickup: String? = null,
    val roomcount: String? = null,
    val reservationlodging: String? = null,
    val reservationurl: String? = null,
    val roomtype: String? = null,
    val scalelodging: String? = null,
    val subfacility: String? = null,
    val barbecue: String? = null,
    val beauty: String? = null,
    val beverage: String? = null,
    val bicycle: String? = null,
    val campfire: String? = null,
    val fitness: String? = null,
    val karaoke: String? = null,
    val publicbath: String? = null,
    val publicpc: String? = null,
    val sauna: String? = null,
    val seminar: String? = null,
    val sports: String? = null,
    val refundregulation: String? = null,
    contentId: String? = null,
    contenttypeid: String? = null,
) : IntroductionItem(
    contentId,
    contenttypeid
)

class ShoppingIntroduction(
    val chkbabycarriageshoppin: String? = null,
    val chkcreditcardshopping: String? = null,
    val chkpetshopping: String? = null,
    val culturecenter: String? = null,
    val fairday: String? = null,
    val infocentershopping: String? = null,
    val opendateshopping: String? = null,
    val opentime: String? = null,
    val parkingshopping: String? = null,
    val restdateshopping: String? = null,
    val restroom: String? = null,
    val saleitem: String? = null,
    val saleitemcost: String? = null,
    val scaleshopping: String? = null,
    val shopguide: String? = null,
    contentId: String,
    contenttypeid: String,
) : IntroductionItem(
    contentId,
    contenttypeid
)

class FoodIntroduction(
    val chkcreditcardfood: String? = null,
    val discountinfofood: String? = null,
    val firstmenu: String? = null,
    val infocenterfood: String? = null,
    val kidsfacility: String? = null,
    val opendatefood: String? = null,
    val opentimefood: String? = null,
    val packing: String? = null,
    val parkingfood: String? = null,
    val reservationfood: String? = null,
    val restdatefood: String? = null,
    val scalefood: String? = null,
    val seat: String? = null,
    val smoking: String? = null,
    val treatmenu: String? = null,
    val lcnsno: String? = null,
    contentId: String,
    contenttypeid: String,
) : IntroductionItem(
    contentId,
    contenttypeid
)

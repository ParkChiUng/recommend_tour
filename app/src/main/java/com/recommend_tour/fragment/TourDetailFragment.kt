package com.recommend_tour.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.recommend_tour.MainActivity
import com.recommend_tour.R
import com.recommend_tour.api.DataApiService
import com.recommend_tour.data.CulturalIntroduction
import com.recommend_tour.data.FestivalIntroduction
import com.recommend_tour.data.FoodIntroduction
import com.recommend_tour.data.LeisureIntroduction
import com.recommend_tour.data.LodgingIntroduction
import com.recommend_tour.data.ShoppingIntroduction
import com.recommend_tour.data.TourCourseIntroduction
import com.recommend_tour.data.TourDetailIntroduction
import com.recommend_tour.data.TourItem
import com.recommend_tour.databinding.FragmentTourDetailBinding
import com.recommend_tour.databinding.TourDetailCourseBinding
import com.recommend_tour.databinding.TourDetailCulturalBinding
import com.recommend_tour.databinding.TourDetailFestivalBinding
import com.recommend_tour.databinding.TourDetailFoodBinding
import com.recommend_tour.databinding.TourDetailLeisureBinding
import com.recommend_tour.databinding.TourDetailLodingBinding
import com.recommend_tour.databinding.TourDetailShoppingBinding
import com.recommend_tour.databinding.TourDetailTourBinding
import com.recommend_tour.viewmodels.TourDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TourDetailFragment : Fragment() {

    @Inject
    lateinit var apiClient: DataApiService

    private val viewModel: TourDetailViewModel by viewModels()
    private lateinit var binding: FragmentTourDetailBinding
    private lateinit var tourBinding: TourDetailTourBinding
    private lateinit var culturalBinding: TourDetailCulturalBinding
    private lateinit var courseBinding: TourDetailCourseBinding
    private lateinit var leisureBinding: TourDetailLeisureBinding
    private lateinit var festivalBinding: TourDetailFestivalBinding
    private lateinit var lodingBinding: TourDetailLodingBinding
    private lateinit var foodBinding: TourDetailFoodBinding
    private lateinit var shoppingBinding: TourDetailShoppingBinding
    private lateinit var fragmentLinearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        fragmentLinearLayout = binding.tourDetailItem

        tourBinding = TourDetailTourBinding.inflate(inflater, container, false)
        culturalBinding = TourDetailCulturalBinding.inflate(inflater, container, false)
        courseBinding = TourDetailCourseBinding.inflate(inflater, container, false)
        leisureBinding = TourDetailLeisureBinding.inflate(inflater, container, false)
        festivalBinding = TourDetailFestivalBinding.inflate(inflater, container, false)
        lodingBinding = TourDetailLodingBinding.inflate(inflater, container, false)
        foodBinding = TourDetailFoodBinding.inflate(inflater, container, false)
        shoppingBinding = TourDetailShoppingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tourItem = arguments?.getParcelable<TourItem>("tourItem")

        binding.tourAddress.text = "주소 : ${tourItem?.address}"
        Glide.with(binding.tourImage.context)
            .load(tourItem?.firstImage)
            .into(binding.tourImage)

        (activity as? MainActivity)?.setActionBarTitle(tourItem?.title)

        CoroutineScope(Dispatchers.IO).launch {
            getTourDetailItem(tourItem!!)
        }
    }

    private suspend fun getTourDetailItem(tourItem: TourItem) {
        val response = try {
            apiClient.getTourDetailItem(tourItem.contentId, tourItem.contentTypeId!!)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        val apiResultCode = response.response?.header?.resultCode
        val body = response.response?.body

        if (apiResultCode == "0000") {
            body?.items?.item?.forEach {
                Log.d(" test ", "LodgingIntroduction it : $it")
                when (it) {
                    is TourDetailIntroduction -> {
                        /**
                         * setup for tour detail view
                         */
                        Log.d("test", "TourDetailIntroduction : ${it.usetime}")
                        viewModel.setTextViewText(tourBinding.acaccomcount, "수용 인원", it.accomcount)
                        viewModel.setTextViewText(tourBinding.chkbabycarriage, "유모차 대여 정보", it.chkbabycarriage)
                        viewModel.setTextViewText(tourBinding.chkcreditcard, "신용 카드 가능 정보", it.chkcreditcard)
                        viewModel.setTextViewText(tourBinding.chkpet, "애완 동물 동반 가능정보", it.chkpet)
                        viewModel.setTextViewText(tourBinding.expagerange, "체험 가능 연령", it.expagerange)
                        viewModel.setTextViewText(tourBinding.expguide, "체험 안내", it.expguide)
                        viewModel.setTextViewText(tourBinding.heritage1, "세계문화유산 유무", it.heritage1)
                        viewModel.setTextViewText(tourBinding.infocenter, "문의 및 안내", it.infocenter)
                        viewModel.setTextViewText(tourBinding.opendate, "개장일", it.opendate)
                        viewModel.setTextViewText(tourBinding.parking, "주차 시설", it.parking)
                        viewModel.setTextViewText(tourBinding.restdate, "쉬는 날", it.restdate)
                        viewModel.setTextViewText(tourBinding.useseason, "이용 시기", it.useseason)
                        viewModel.setTextViewText(tourBinding.usetime, "이용 시간", it.usetime?.replace("<br>",""))

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(tourBinding.root)
                        }
                    }

                    is CulturalIntroduction -> {
                        Log.d("test", "CulturalIntroduction")

                        viewModel.setTextViewText(culturalBinding.accomcountculture, "수용 인원", it.accomcountculture)
                        viewModel.setTextViewText(culturalBinding.chkbabycarriageculture, "유모차 대여 정보", it.chkbabycarriageculture)
                        viewModel.setTextViewText(culturalBinding.chkcreditcardculture, "신용 카드 가능 정보", it.chkcreditcardculture)
                        viewModel.setTextViewText(culturalBinding.chkpetculture, "애완 동물 동반 가능 정보", it.chkpetculture)
                        viewModel.setTextViewText(culturalBinding.discountinfo, "할인 정보", it.discountinfo)
                        viewModel.setTextViewText(culturalBinding.infocenterculture, "문의 및 안내", it.infocenterculture)
                        viewModel.setTextViewText(culturalBinding.parkingculture, "주차 시설", it.parkingculture)
                        viewModel.setTextViewText(culturalBinding.parkingfee, "주차 요금", it.parkingfee)
                        viewModel.setTextViewText(culturalBinding.restdateculture, "쉬는 날", it.restdateculture)
                        viewModel.setTextViewText(culturalBinding.usefee, "이용 요금", it.usefee)
                        viewModel.setTextViewText(culturalBinding.usetimeculture, "이용 시간", it.usetimeculture)
                        viewModel.setTextViewText(culturalBinding.scale, "규모", it.scale)
                        viewModel.setTextViewText(culturalBinding.spendtime, "관람 소요 시간", it.spendtime)

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(culturalBinding.root)
                        }
                    }

                    is TourCourseIntroduction -> {
                        Log.d("test", "TourCourseIntroduction")

                        viewModel.setTextViewText(courseBinding.distance, "코스 총 거리", it.distance)
                        viewModel.setTextViewText(courseBinding.infocentertourcourse, "문의 및 안내", it.infocentertourcourse)
                        viewModel.setTextViewText(courseBinding.schedule, "코스 일정", it.schedule)
                        viewModel.setTextViewText(courseBinding.taketime, "코스 총 소요 시간", it.taketime)
                        viewModel.setTextViewText(courseBinding.theme, "코스 테마", it.theme)

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(courseBinding.root)
                        }
                    }

                    is LeisureIntroduction -> {
                        Log.d("test", "LeisureIntroduction")

                        viewModel.setTextViewText(leisureBinding.accomcountleports, "수용 인원", it.accomcountleports)
                        viewModel.setTextViewText(leisureBinding.chkbabycarriageleports, "유모차 대여 정보", it.chkbabycarriageleports)
                        viewModel.setTextViewText(leisureBinding.chkcreditcardleports, "신용 카드 가능 정보", it.chkcreditcardleports)
                        viewModel.setTextViewText(leisureBinding.chkpetleports, "애완 동물 동반 가능 정보", it.chkpetleports)
                        viewModel.setTextViewText(leisureBinding.expagerangeleports, "체험 가능 연력", it.expagerangeleports)
                        viewModel.setTextViewText(leisureBinding.infocenterleports, "문의 및 안내", it.infocenterleports)
                        viewModel.setTextViewText(leisureBinding.openperiod, "개장 기간", it.openperiod)
                        viewModel.setTextViewText(leisureBinding.parkingfeeleports, "주차 요금", it.parkingfeeleports)
                        viewModel.setTextViewText(leisureBinding.parkingleports, "주차 시설", it.parkingleports)
                        viewModel.setTextViewText(leisureBinding.reservation, "예약 안내", it.reservation)
                        viewModel.setTextViewText(leisureBinding.restdateleports, "쉬는 날", it.restdateleports)
                        viewModel.setTextViewText(leisureBinding.scaleleports, "규모", it.scaleleports)
                        viewModel.setTextViewText(leisureBinding.usefeeleports, "입장료", it.usefeeleports)
                        viewModel.setTextViewText(leisureBinding.usetimeleports, "이용 시간", it.usetimeleports)

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(leisureBinding.root)
                        }
                    }

                    is FestivalIntroduction -> {
                        Log.d("test", "FestivalIntroduction")

                        viewModel.setTextViewText(festivalBinding.agelimit, "관람 가능 연령", it.agelimit)
                        viewModel.setTextViewText(festivalBinding.bookingplace, "예매처", it.bookingplace)
                        viewModel.setTextViewText(festivalBinding.discountinfofestival, "할인 정보", it.discountinfofestival)
                        viewModel.setTextViewText(festivalBinding.eventenddate, "행사 종료일", it.eventenddate)
                        viewModel.setTextViewText(festivalBinding.eventhomepage, "행사 홈페이지", it.eventhomepage)
                        viewModel.setTextViewText(festivalBinding.eventplace, "행사 장소", it.eventplace)
                        viewModel.setTextViewText(festivalBinding.eventstartdate, "행사 시작일", it.eventstartdate)
                        viewModel.setTextViewText(festivalBinding.festivalgrade, "축제 등급", it.festivalgrade)
                        viewModel.setTextViewText(festivalBinding.placeinfo, "행사장 위치 안내", it.placeinfo)
                        viewModel.setTextViewText(festivalBinding.playtime, "공연 시간", it.playtime)
                        viewModel.setTextViewText(festivalBinding.program, "행사 프로그램", it.program)
                        viewModel.setTextViewText(festivalBinding.spendtimefestival, "관람 소요 시간", it.spendtimefestival)
                        viewModel.setTextViewText(festivalBinding.sponsor1, "주최자 정보", it.sponsor1)
                        viewModel.setTextViewText(festivalBinding.sponsor1tel, "주최자 연락처", it.sponsor1tel)
                        viewModel.setTextViewText(festivalBinding.sponsor2, "주관사 정보", it.sponsor2)
                        viewModel.setTextViewText(festivalBinding.sponsor2tel, "주관사 연락처", it.sponsor2tel)
                        viewModel.setTextViewText(festivalBinding.subevent, "부대 행사", it.subevent)
                        viewModel.setTextViewText(festivalBinding.usetimefestival, "이용 요금", it.usetimefestival)

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(festivalBinding.root)
                        }
                    }

                    is LodgingIntroduction -> {
                        Log.d("test", "LodgingIntroduction : $it")

                        viewModel.setTextViewText(lodingBinding.accomcountlodging, "수용 가능 인원", it.accomcountlodging)
                        viewModel.setTextViewText(lodingBinding.benikia, "베니키아 여부", it.benikia)
                        viewModel.setTextViewText(lodingBinding.checkintime, "입실 시간", it.checkintime)
                        viewModel.setTextViewText(lodingBinding.checkouttime, "퇴실 시간", it.checkouttime)
                        viewModel.setTextViewText(lodingBinding.chkcooking, "객실 내 취사 여부", it.chkcooking)
                        viewModel.setTextViewText(lodingBinding.foodplace, "식 음료장", it.foodplace)
                        viewModel.setTextViewText(lodingBinding.goodstay, "굿스테이 여부", it.goodstay)
                        viewModel.setTextViewText(lodingBinding.hanok, "한옥 여부", it.hanok)
                        viewModel.setTextViewText(lodingBinding.infocenterlodging, "문의 및 안내", it.infocenterlodging)
                        viewModel.setTextViewText(lodingBinding.parkinglodging, "주차 시설", it.parkinglodging)
                        viewModel.setTextViewText(lodingBinding.pickup, "픽업 서비스", it.pickup)
                        viewModel.setTextViewText(lodingBinding.roomcount, "객실 수", it.roomcount)
                        viewModel.setTextViewText(lodingBinding.reservationlodging, "예약 안내", it.reservationlodging)
                        viewModel.setTextViewText(lodingBinding.reservationurl, "예약 안내 홈페이지", it.reservationurl)
                        viewModel.setTextViewText(lodingBinding.roomtype, "객실 유형", it.roomtype)
                        viewModel.setTextViewText(lodingBinding.scalelodging, "규모", it.scalelodging)
                        viewModel.setTextViewText(lodingBinding.subfacility, "부대시설 (기타)", it.subfacility)
                        viewModel.setTextViewText(lodingBinding.barbecue, "바비큐장 여부", it.barbecue)
                        viewModel.setTextViewText(lodingBinding.beauty, "뷰티 시설 정보", it.beauty)
                        viewModel.setTextViewText(lodingBinding.beverage, "식음료장 여부", it.beverage)
                        viewModel.setTextViewText(lodingBinding.bicycle, "자전거 대여 여부", it.bicycle)
                        viewModel.setTextViewText(lodingBinding.campfire, "캠프 파이어 여부", it.campfire)
                        viewModel.setTextViewText(lodingBinding.fitness, "휘트니스 센터 여부", it.fitness)
                        viewModel.setTextViewText(lodingBinding.karaoke, "노래방 여부", it.karaoke)
                        viewModel.setTextViewText(lodingBinding.publicbath, "공용 샤워실 여부", it.publicbath)
                        viewModel.setTextViewText(lodingBinding.publicpc, "공용 PC실 여부", it.publicpc)
                        viewModel.setTextViewText(lodingBinding.sauna, "사우나실 여부", it.sauna)
                        viewModel.setTextViewText(lodingBinding.seminar, "세미나실 여부", it.seminar)
                        viewModel.setTextViewText(lodingBinding.sports, "스포츠 시설 여부", it.sports)
                        viewModel.setTextViewText(lodingBinding.refundregulation, "환불 규정", it.refundregulation)

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(lodingBinding.root)
                        }
                    }

                    is ShoppingIntroduction -> {
                        Log.d("test", "ShoppingIntroduction ${it.shopguide}")

                        viewModel.setTextViewText(shoppingBinding.chkbabycarriageshopping, "유모차 대여 정보", it.chkbabycarriageshoppin)
                        viewModel.setTextViewText(shoppingBinding.chkcreditcardshopping, "신용 카드 가능 정보", it.chkcreditcardshopping)
                        viewModel.setTextViewText(shoppingBinding.chkpetshopping, "애완 동물 동반 가능 정보", it.chkpetshopping)
                        viewModel.setTextViewText(shoppingBinding.culturecenter, "문화 센터 바로가기", it.culturecenter)
                        viewModel.setTextViewText(shoppingBinding.fairday, "장 서는 날", it.fairday)
                        viewModel.setTextViewText(shoppingBinding.infocentershopping, "문의 및 안내", it.infocentershopping)
                        viewModel.setTextViewText(shoppingBinding.opendateshopping, "개장일", it.opendateshopping)
                        viewModel.setTextViewText(shoppingBinding.opentime, "영업 시간", it.opentime)
                        viewModel.setTextViewText(shoppingBinding.parkingshopping, "주차 시설", it.parkingshopping)
                        viewModel.setTextViewText(shoppingBinding.restdateshopping, "쉬는 날", it.restdateshopping)
                        viewModel.setTextViewText(shoppingBinding.restroom, "화장실 설명", it.restroom)
                        viewModel.setTextViewText(shoppingBinding.saleitem, "판매 품목", it.saleitem)
                        viewModel.setTextViewText(shoppingBinding.saleitemcost, "판매 품목 별 가격", it.saleitemcost)
                        viewModel.setTextViewText(shoppingBinding.scaleshopping, "규모", it.scaleshopping)
                        viewModel.setTextViewText(shoppingBinding.shopguide, "매장 안내", it.shopguide)

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(shoppingBinding.root)
                        }
                    }

                    is FoodIntroduction -> {
                        Log.d("test", "FoodIntroduction : ${it.discountinfofood}")

                        viewModel.setTextViewText(foodBinding.chkcreditcardfood, "신용 카드 가능 정보", it.chkcreditcardfood)
                        viewModel.setTextViewText(foodBinding.discountinfofood, "할인 정보", it.discountinfofood)
                        viewModel.setTextViewText(foodBinding.firstmenu, "대표 메뉴", it.firstmenu)
                        viewModel.setTextViewText(foodBinding.infocenterfood, "문의 및 안내", it.infocenterfood)
                        viewModel.setTextViewText(foodBinding.kidsfacility, "어린이 놀이방 여부", it.kidsfacility)
                        viewModel.setTextViewText(foodBinding.opendatefood, "개업일", it.opendatefood)
                        viewModel.setTextViewText(foodBinding.opentimefood, "영업 시간", it.opentimefood)
                        viewModel.setTextViewText(foodBinding.packing, "포장 가능", it.packing)
                        viewModel.setTextViewText(foodBinding.parkingfood, "주차 시설", it.parkingfood)
                        viewModel.setTextViewText(foodBinding.reservationfood, "예약 안내", it.reservationfood)
                        viewModel.setTextViewText(foodBinding.restdatefood, "쉬는 날", it.restdatefood)
                        viewModel.setTextViewText(foodBinding.scalefood, "규모", it.scalefood)
                        viewModel.setTextViewText(foodBinding.seat, "좌석 수", it.seat)
                        viewModel.setTextViewText(foodBinding.smoking, "금연/흡연 여부", it.smoking)
                        viewModel.setTextViewText(foodBinding.treatmenu, "취급 메뉴", it.treatmenu)
                        viewModel.setTextViewText(foodBinding.lcnsno, "인허가 번호", it.lcnsno)

                        activity?.runOnUiThread {
                            fragmentLinearLayout.addView(foodBinding.root)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomNavigationView()
    }

    private fun hideBottomNavigationView() {
        val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottomNav)
        bottomNav.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        showBottomNavigationView()
    }

    private fun showBottomNavigationView() {
        val bottomNav: BottomNavigationView = requireActivity().findViewById(R.id.bottomNav)
        bottomNav.visibility = View.VISIBLE
    }
}

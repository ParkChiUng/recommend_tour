package com.recommend_tour.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import androidx.room.Room
import com.recommend_tour.api.DataApiService
import com.recommend_tour.data.AppDatabase
import com.recommend_tour.data.TourDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TourDataUpdateService : Service() {

    @Inject
    lateinit var apiClient: DataApiService

    private lateinit var database: AppDatabase // 데이터베이스 인스턴스 추가
    private lateinit var tourDao: TourDao // DAO 추가
    private lateinit var sharedPref: SharedPreferences
    private var prefTotalCount = -1
    private var prefPageNumber = 1
    private var prefareaCodeCount = 1
    private val numOfRows = 1000


    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AppDatabase::class.java, "tour-db").build()
        tourDao = database.tourDao()
        sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        prefTotalCount = sharedPref.getInt("lastSaveTotalCount", -1)
        prefPageNumber = sharedPref.getInt("lastSavePageNumber", 1)
        prefareaCodeCount = sharedPref.getInt("lastSaveAreaCodeCount", 1)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            getAreaCodeFromApi()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    // 지역 코드 호출
    private suspend fun getAreaCodeFromApi() {
        val response = try{
            apiClient.getAreaCode()
        }catch (e:Exception){
            e.printStackTrace()
            return
        }

        val apiResultCode = response.response?.header?.resultCode

        if (apiResultCode == "0000") {
            val totalCount: Int = response.response?.body?.totalCount ?: 0
            val areaCodeItems = response.response?.body?.items?.item

            if (totalCount != prefareaCodeCount) {
                if (areaCodeItems != null) {
                    tourDao.insertAreaCode(areaCodeItems)

                    with(sharedPref.edit()) {
                        putInt("lastSaveAreaCodeCount", totalCount)
                        apply()
                    }
                }
                checkTourItemFromApi()
            }else
                stopSelf()
        }
    }

    // totalCount 변경 체크
    private suspend fun checkTourItemFromApi() {
        val response = try {
            apiClient.getAllData(1, 1)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        val apiResultCode = response.response?.header?.resultCode

        if (apiResultCode == "0000") {
            val totalCount: Int = response.response?.body?.totalCount ?: 0
            val lastPageNumber = (totalCount + (numOfRows - 1)) / numOfRows

            // 처음 설치, 아이템이 늘어난 경우, 마지막으로 저장한 pageNumber가 마지막이 아닌 경우
            if (prefTotalCount == -1 || totalCount > prefTotalCount || lastPageNumber != prefPageNumber) {
                CoroutineScope(Dispatchers.IO).launch {
                    getTourItemFromApi()
                }
            }else{
                stopSelf()
            }
        }
    }

    // tour 리스트 전체 호출
    private suspend fun getTourItemFromApi() {
        while (true) {
            val response = try {
                apiClient.getAllData(prefPageNumber, numOfRows)
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }

            val body = response.response?.body ?: break
            val apiResultCode = response.response?.header?.resultCode

            if (apiResultCode == "0000") {
                val totalCount = body.totalCount
                val lastPageNumber = (totalCount + (numOfRows - 1)) / numOfRows
                val tourItems = body.items?.item

                if (tourItems.isNullOrEmpty()) break

                tourDao.insertAll(tourItems)

                val intent = Intent("tourDataReady")
                sendBroadcast(intent)

                with(sharedPref.edit()) {
                    putInt("lastSaveTotalCount", totalCount)
                    putInt("lastSavePageNumber", prefPageNumber)
                    apply()
                }

                if (++prefPageNumber > lastPageNumber) break

            } else {
                break
            }
        }

        stopSelf()
    }
}

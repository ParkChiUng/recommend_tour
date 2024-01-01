package com.recommend_tour.viewmodels

import android.view.View
import android.widget.TextView
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.recommend_tour.adapter.TourPagingAdapter
import com.recommend_tour.data.TourItem
import com.recommend_tour.data.TourPagingSource
import com.recommend_tour.data.TourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourDetailViewModel @Inject internal constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun setTextViewText(textView: TextView, label: String, value: String?) {
        if (!value.isNullOrEmpty()) {
            textView.text = "$label : $value"
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }
}
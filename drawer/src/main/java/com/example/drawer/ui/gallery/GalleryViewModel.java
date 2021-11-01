package com.example.drawer.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.drawer.ui.gallery.beans.ItemBean;

import java.util.ArrayList;

public class GalleryViewModel extends ViewModel {

    MutableLiveData<ArrayList<ItemBean>> liveData = new MutableLiveData<>();

    public GalleryViewModel() {

    }

    public LiveData<ArrayList<ItemBean>> getLiveData() {
        return liveData;
    }

    public void setLiveData(ArrayList<ItemBean> beans) {
        liveData.postValue(beans);
    }
}
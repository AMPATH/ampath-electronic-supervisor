package com.ckb.labs.e_supervisor.ui.supervisions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SupervisionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SupervisionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
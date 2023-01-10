package com.example.repeatingtasks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.repeatingtasks.utils.CustomError;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Base class for the AndroidViewModel used in this project
 */
public abstract class BaseAndroidViewModel extends AndroidViewModel {

    private final List<Disposable> disposableList;
    protected MutableLiveData<CustomError> error;

    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
        disposableList = new ArrayList<>();
    }

    /**
     * Adds the disposable to the lists and removes the disposed ones
     */
    protected void addDisposable(Disposable disposable) {
        removeDisposed();
        disposableList.add(disposable);
    }

    /**
     * Removes remaining disposable objects from the lists
     */
    public void dispose() {
        for (Disposable disposable : disposableList) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        removeDisposed();
    }

    /**
     * @return MutableLiveData for the {@link CustomError}
     */
    public MutableLiveData<CustomError> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    /**
     * removes disposed object from the list
     */
    private void removeDisposed() {
        disposableList.removeIf(disposable -> disposable != null && disposable.isDisposed());
    }


}

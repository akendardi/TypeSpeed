package com.example.typespeed;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TypeActivityViewModel extends ViewModel {

    private MutableLiveData<List<String>> text = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<List<String>> getText() {
        return text;
    }

    public void loadText() {
        Disposable disposable = ApiFactory.apiService.loadText()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextResponse>() {
                    @Override
                    public void accept(TextResponse textResponse) throws Throwable {
                        text.setValue(textAfter(textResponse.getText().toLowerCase()));
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    private List<String> textAfter(String text){
        List<String> strings = Arrays.asList(text.split("\\s+"));
        List<String> wordList = new ArrayList<>();
        for (int i = 1; i <= strings.size(); i++){
            if (i != strings.size()) {
                String newStr = "";
                if (strings.get(i).charAt(0) == ',') {
                    newStr = strings.get(i - 1) + ",";
                } else {
                    newStr = strings.get(i - 1);
                }
                wordList.add(newStr.trim() + " ");
            }else {
                wordList.add(strings.get(i-1));
            }
        }
        return wordList;
    }

}

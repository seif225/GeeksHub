package com.example.geekshub.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.geekshub.data.FirebaseQueryRepo;
import com.example.geekshub.data.UserModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {

    private MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    private FirebaseQueryRepo query;
    public void setUserModel(UserModel userModel, final Application context) {
        query = new FirebaseQueryRepo(context);
        Observable<UserModel> observable = Observable.just(userModel);
        Observer<UserModel> observer = new Observer<UserModel>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(UserModel userModel) {
                query.setUser(context, userModel);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(observer);
    }
    public MutableLiveData<UserModel> getUserModelMutableLiveData(String i, Application context) {
        query = new FirebaseQueryRepo(context);
        userModelMutableLiveData = query.getUser(userModelMutableLiveData,i);
        return userModelMutableLiveData;
    }
}

package com.example.geekshub.viewModel;
import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.geekshub.data.BookModel;
import com.example.geekshub.data.FirebaseQueryRepo;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
public class BooksViewModel extends ViewModel {
    MutableLiveData<List<BookModel>> listMutableLiveData = new MutableLiveData<>();
    FirebaseQueryRepo queryRepo;

    public MutableLiveData<List<BookModel>> getListMutableLiveData(Application application) {
        initializeQuerRepo(application);
        Observable<MutableLiveData<List<BookModel>>> observable = Observable.just(queryRepo.getListOfBooks(listMutableLiveData));
        Observer<MutableLiveData<List<BookModel>>> observer = new Observer<MutableLiveData<List<BookModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MutableLiveData<List<BookModel>> listMutableLiveData1) {
                listMutableLiveData = listMutableLiveData1;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.computation()).subscribe(observer);

        return listMutableLiveData;
    }

    private void initializeQuerRepo(Application application) {
        queryRepo = FirebaseQueryRepo.getInstance(application);
    }
}

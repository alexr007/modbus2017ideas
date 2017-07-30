package app.decision.test.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import javafx.beans.value.ObservableValue;

import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 29.07.2017.
 */
public class rxTest1 {
    public void test1() {
        Observable<String> observable = Observable.fromArray("first", "second", "third", "fourth", "fifths");


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(String s) {
                System.out.println(String.format("Next String: %s", s));
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error");
            }

            @Override
            public void onComplete() {
                System.out.println("Finished");
            }
        };
        observable.subscribe(observer);

    }

    public void test2() {
        Observable<Long> range = Observable.intervalRange(10, 5, 0, 510, TimeUnit.MILLISECONDS);
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Long aLong) {
                System.out.println(String.format("Next : %s", aLong));
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error");
            }

            @Override
            public void onComplete() {
                System.out.println("Finished");
            }
        };

        range.subscribe(observer);

    }

    public void test0() {

        Observable<String> o1 = Observable.create(e -> {
            new Thread( () -> {
                e.onNext("A1");
                e.onNext("B1");
                e.onNext("C1");
                e.onComplete();
            }).start();
        });
        Observable<String> o2 = Observable.create(e -> {
            new Thread( () -> {
                e.onNext("A2");
                e.onNext("B2");
                e.onNext("C2");
                e.onComplete();
            }).start();
        });
        Observable<String> o3 = Observable.merge(o1, o2);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        o3.subscribe(observer);
        System.out.println("async");
    }
}

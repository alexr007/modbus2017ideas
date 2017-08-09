package app.decision.test.rx;

import app.persistence.BIOcore;
import constants.ChanName;
import entities.EnRelay;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import jbus.modbus.response.ValuesMapped;
import jwad.chanvalue.ChanValue;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by mac on 29.07.2017.
 */
public class rxTest1 {
    public void test10() {
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

    public void test11() {
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

        Observable.fromArray("first", "second", "third", "fourth", "fifths")
            .subscribe(observer);
    }

    public void test12() {
        Observable.fromArray("first", "second", "third", "fourth", "fifths")
            .subscribe(new Observer<String>() {
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
            });
    }

    public void test13() {
        Observable<Integer> range = Observable.range(1, 10);
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.printf("Next : %d\n", integer);
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

    public void test14() {
        Observable.interval(0, 500, TimeUnit.MILLISECONDS)
            .take(10)
            .doOnNext(aLong -> System.out.println(aLong))
            .doOnComplete(() -> System.out.println("done"))
            .doOnComplete(() -> Thread.sleep(5000))
            .blockingSubscribe();

        System.out.println("z");

    }

    public void test15(BIOcore core) {

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

    public void test16(BIOcore core) {
        EnRelay r1 = new EnRelay(core.channels().get(ChanName.R1));
        EnRelay r2 = new EnRelay(core.channels().get(ChanName.R2));
        EnRelay r3 = new EnRelay(core.channels().get(ChanName.R3));

        Observable<Long> o1 = Observable.interval(0, 500, TimeUnit.MILLISECONDS)
            .take(10)
            .doFinally(()-> System.out.println("1 finaly"))
            .doOnNext(aLong -> {
                if (aLong % 2 == 0) r1.on();
                else r1.off();
            })
            ;

        Observable<Long> o2 = Observable.interval(100, 500, TimeUnit.MILLISECONDS)
            .take(10)
            .doOnNext(aLong -> {
                if (aLong % 2 == 0) r2.on();
                else r2.off();
            });

        Observable<Long> o3 = Observable.interval(200, 500, TimeUnit.MILLISECONDS)
            .take(10)
            .doOnNext(aLong -> {
                if (aLong % 2 == 0) r3.on();
                else r3.off();
            });

        Observable<Long> o = Observable.merge(o1, o2, o3);
        o.blockingSubscribe(l-> System.out.printf("Subscriber1:%d\n",l));
        o.blockingSubscribe(l-> System.out.printf("Subscriber2:%d\n",l));
    }

    public void test17_OK(BIOcore core) {
        EnRelay r1 = new EnRelay(core.channels().get(ChanName.R1));
        long start = System.currentTimeMillis();

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.zip(
            Observable.range(1, 5),
            Observable.interval(1000, TimeUnit.MILLISECONDS),
            (obs, timer) -> obs)
            .doOnNext(item -> {
                System.out.println(System.currentTimeMillis() - start);
                System.out.printf("val:%d\n", item);
            })
            .toList()
            .toObservable()
            .blockingSubscribe();




/*
        Observable.create(
                (ObservableOnSubscribe<ValuesMapped>) e -> {
                    IntStream.range(0,10).
                        forEach(i->{
                            try {
                                r1.on();
                                e.onNext(r1.get());
                                Thread.sleep(1000);
                                r1.off();
                                e.onNext(r1.get());
                                Thread.sleep(1000);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        });
                }
            )
            .blockingSubscribe(new Consumer<ValuesMapped>() {
                @Override
                public void accept(ValuesMapped valuesMapped) throws Exception {
                    System.out.println(valuesMapped.map());
                }
            })
        ;
*/
    }

    public void test18(BIOcore core) {
        Single<List<Long>> listSingle = Observable.interval(0, 500, TimeUnit.MILLISECONDS)
            .take(10)
            .skip(5)
            .take(2)
            .doOnNext(aLong -> System.out.println(aLong))
            .toList();
        //.blockingSubscribe();
        List<Long> longs = listSingle.blockingGet();
//            .blockingSubscribe();
        System.out.println(longs);
    }

    public void test19(BIOcore core) {
    }

    public void test20_idea(BIOcore core) {
        EnRelay r1 = new EnRelay(core.channels().get(ChanName.R1));
        long start = System.currentTimeMillis();

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(System.currentTimeMillis() - start);
                System.out.printf("val:%d\n", integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable<Integer> obRange = Observable.range(1, 5);
        Observable<Long> obInterval = Observable.interval(1000, TimeUnit.MILLISECONDS);

        Observable.zip(
            obRange,
            obInterval,
            (obs, timer) -> obs)
            .doOnNext(item -> {
                System.out.println(System.currentTimeMillis() - start);
                System.out.printf("thread:%s,val:%d\n", Thread.currentThread().getName(),item);
            })
            .blockingSubscribe();
        System.out.printf("Thread:%s", Thread.currentThread().getName());

/*
            .toList()
            .flatMap()
            .toObservable();
*/
        //.blockingSubscribe();

/*
*/
/*
            .toList()
            .toObservable()
            .blockingSubscribe();
*/

/*
        o.subscribe(observer);
*/


/*
        Observable.create(
                (ObservableOnSubscribe<ValuesMapped>) e -> {
                    IntStream.range(0,10).
                        forEach(i->{
                            try {
                                r1.on();
                                e.onNext(r1.get());
                                Thread.sleep(1000);
                                r1.off();
                                e.onNext(r1.get());
                                Thread.sleep(1000);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        });
                }
            )
            .blockingSubscribe(new Consumer<ValuesMapped>() {
                @Override
                public void accept(ValuesMapped valuesMapped) throws Exception {
                    System.out.println(valuesMapped.map());
                }
            })
        ;
*/
    }

    private static ObservableSource<?> apply(Integer i) {
        return Observable.just(i).delay(1000, TimeUnit.MILLISECONDS);
    }

    public void test0(BIOcore core) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();

        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.printf("Item: %d , Time: %dms\n", o, System.currentTimeMillis() - startTime);
                EnRelay r1 = new EnRelay(core.channels().get(ChanName.R1));
                if ((Integer)o % 2 ==0) {r1.on();} else
                {r1.off();}
            }
        };

        Observable<?> observable = Observable.range(1, 10)
            .concatMap(rxTest1::apply)
/*
            .doOnNext(i -> {
            })
*/
            ;

        observable.blockingSubscribe(consumer);

    }

}

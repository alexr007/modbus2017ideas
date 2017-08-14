package app.decision.test.rx;

import app.persistence.BIOcore;
import app.persistence.Persistence;
import app.persistence.ValuesTail;
import constants.ChanName;
import entities.EnRelay;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import jwad.chanvalue.ChanValue;
import org.javatuples.Pair;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static constants.ChanName.*;

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

    private static ObservableSource<Integer> applyDelay(Integer i) {
        return Observable.just(i)
            .delay(1000, TimeUnit.MILLISECONDS);
    }

    public void test21_time_real(BIOcore core) {
        long startTime = System.currentTimeMillis();
        Date date = new Date();
        Instant now = Instant.now();
        System.out.printf("starttime: %d\nDate: %d\nInstant:%d",
        startTime, date.getTime(), now.toEpochMilli());
    }

    public void test22(BIOcore core) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer o) throws Exception {
                System.out.printf("Item: %d , Time: %dms\n", o, System.currentTimeMillis() - startTime);
                EnRelay r1 = new EnRelay(core.channels().get(ChanName.R1));
                if (o%2==0) r1.on(); else r1.off();
            }
        };

        Observable<Integer> observable = Observable.range(1, 10)
            .concatMap(rxTest1::applyDelay);

        observable.blockingSubscribe(consumer);
/*
        Phaser p = new Phaser();
        CountDownLatch cdl = new CountDownLatch();
        CyclicBarrier cb = new CyclicBarrier();
        Semaphore s = new Semaphore();
        CompletableFuture<Object> cf = new CompletableFuture<>();
        new CountedCompleter<>();
        new Exchanger<>();
        new Future<>();
        new FutureTask<>();
        new ThreadLocalRandom()
*/
    }


    public void test23(BIOcore core) throws ExecutionException, InterruptedException {
/*
        Observable<Pair> o = Observable.create((ObservableEmitter<Pair> s) ->
        {
            s.onNext(new Pair<>(1, "a"));
            s.onNext(new Pair<>(2, "b"));
            s.onNext(new Pair<>(1, "c"));
            s.onNext(new Pair<>(2, "d"));
            s.onComplete();
        });

        o
            .filter(new Predicate<Pair>() {
                @Override
                public boolean test(Pair pair) throws Exception {
                    return (int)pair.getValue0()==1;
                }
            })
            .groupBy(pair -> (Integer) pair.getValue0())
            .subscribe(
                g -> {
                    System.out.println(g.getKey());
                    System.out.println(g);
                }
            );
*/

/*
        o.map(v->"Item: "+v.toString())
            .subscribe(s-> System.out.println(s));
*/

        Observable<Object> o1 = Observable.create(s -> {
                s.onNext(new Pair<>(1, "1a"));
                s.onNext(new Pair<>(2, "1b"));
                s.onNext(new Pair<>(1, "1c"));
                s.onNext(new Pair<>(2, "1d"));
                s.onComplete();
        });
        Observable<Object> o2 = Observable.create(s -> {
                s.onNext(new Pair<>(1, "2a"));
                s.onNext(new Pair<>(2, "2b"));
                s.onNext(new Pair<>(1, "2c"));
                s.onNext(new Pair<>(2, "2d"));
                s.onComplete();
        });

        Observable<Object> o = Observable.merge(o1, o2);

        o
            .skip(1)
            .subscribe(item->{
            System.out.printf("Item:%s, try:1, thread:%s\n",item,  Thread.currentThread().getName());
        });
    }

    public void test0(BIOcore core) {
        Persistence p = core.persistence();
        System.out.println(p);

        p.write(V10_2, ChanValue.A(10));
        p.write(V10_2, ChanValue.A(11));
        p.write(V10_2, ChanValue.A(12));
        p.write(V10_2, ChanValue.A(13));

        System.out.println(p.read(V10_2));



        ValuesTail vt = new ValuesTail(3);
        vt.add(ChanValue.A(10));
        vt.add(ChanValue.A(20));
        vt.add(ChanValue.A(30));
        System.out.println(vt.list());
        System.out.println(vt.size());
        vt.add(ChanValue.A(40));
        System.out.println(vt.list());
        System.out.println(vt.size());

/*
        ArrayList<String> l = new ArrayList<>();
        l.add("1st");
        l.add("2nd");
        l.add("3rd");
        System.out.println(l);
        l.add("4th");
        l.remove(0);
        System.out.println(l);
        IntStream.range(0,l.size())
            .forEach( index -> {
                System.out.printf("Index:%d, Value:%s\n", index, l.get(l.size()-index-1));
            });

*/
    }

    public String test77 (Set<String> set) {
        return set.
            stream()
            .reduce((t, u) -> t + " " + u)
            .orElse("");
    }

    public String test78 (Set<String> set) {
        return set.
            stream()
            .collect(Collectors.joining(" "));
    }

}

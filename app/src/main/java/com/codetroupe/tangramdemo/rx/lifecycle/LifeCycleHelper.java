/*
 * MIT License
 *
 * Copyright (c) 2018 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.codetroupe.tangramdemo.rx.lifecycle;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by longerian on 2018/4/8.
 *
 * @author longerian
 * @date 2018/04/08
 */

public class LifeCycleHelper {

    public static <T, E> LifecycleTransformer<T> bindUntilEvent(Observable<E> lifecycle, final E event) {
        return new LifecycleTransformer<>(lifecycle.filter(new Predicate<E>() {
            @Override
            public boolean test(E e) throws Exception {
                return e.equals(event);
            }
        }));
    }

    public static <T, E> LifecycleTransformer<T> bindToLifeCycle(Observable<E> lifecycle,
        final Function<E, E> correspondingEvents) {
        Observable<E> lifecycleCopy = lifecycle.share();
        return new LifecycleTransformer<>(Observable.combineLatest(lifecycle.take(1).map(correspondingEvents),
            lifecycleCopy.skip(1),
            new BiFunction<E, E, Boolean>() {

                @Override
                public Boolean apply(E e, E e2) throws Exception {
                    return e.equals(e2);
                }
            }).filter(new Predicate<Boolean>() {
            @Override
            public boolean test(Boolean cmpResult) throws Exception {
                return cmpResult;
            }
        }));
    }

}

package com.wildwolf.mycsdn.model;

import rx.Observable;

/**
 * Created by ${wild00wolf} on 2017/1/20.
 */
public interface IEarModel {
    Observable<String> getEarData(String cid, int page);
}

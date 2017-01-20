package com.wildwolf.mycsdn.model.impl;



import com.wildwolf.mycsdn.api.EarService;
import com.wildwolf.mycsdn.model.IEarModel;
import com.wildwolf.mycsdn.net.NetManager;

import rx.Observable;

/**
 * Created by ${wild00wolf} on 2016/11/24.
 */
public class EarModelImpl implements IEarModel {

    @Override
    public Observable<String> getEarData(String cid, int page) {
        EarService service = NetManager.getInstance().create1(EarService.class);
        return service.getEARData(cid, page);
    }
}

package com.wildwolf.mycsdn.parsenter;


import com.wildwolf.mycsdn.model.IEarModel;
import com.wildwolf.mycsdn.model.impl.EarModelImpl;
import com.wildwolf.mycsdn.rx.RxManager;
import com.wildwolf.mycsdn.rx.RxSubscriber;
import com.wildwolf.mycsdn.ui.view.EarView;
import com.wildwolf.mycsdn.utils.JsoupUtil;

/**
 * Created by ${wild00wolf} on 2016/11/24.
 */
public class EarPresenter extends BasePresenter<EarView> {
    private IEarModel mModel;

    public EarPresenter() {
        mModel = new EarModelImpl();
    }


    public void getEarData(String cid, int page) {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getEarData(cid, page), new RxSubscriber<String>(false) {
            @Override
            protected void _onNext(String s) {
                mView.onSuccess(JsoupUtil.parseEar(s));
            }

            @Override
            protected void _onError() {
                mView.onError();
            }
        });
    }
}


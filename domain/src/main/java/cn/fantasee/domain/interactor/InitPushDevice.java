package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;


public class InitPushDevice extends RxBaseCase<BooleanResult> {

    private String devicePushId;

    private DataRepositoryDomain mDataRepository;

    public InitPushDevice(DataRepositoryDomain dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        devicePushId = paras[0];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return this.mDataRepository.initPushDevice(devicePushId);
    }
}

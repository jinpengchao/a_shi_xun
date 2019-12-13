package h.jpc.vhome.parents.fragment.radio_ximalaya.base;

public interface IBasePresenter<T> {
    void registerViewCallback(T t);
    void unRegisterViewCallback(T t);
}

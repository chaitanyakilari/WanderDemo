
package com.wanderdemo.view;

public interface BaseView<T> {

//    void showProgress();
//
//    void hideProgress();

    void onResult(T result, int returnType, int apiType);
}

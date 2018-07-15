package com.wanderdemo.callback;

/**
 * @Author: Chaitanya kumar
 * @Since: 19/02/17.
 * Retrofit API Interface Callback
 */
public interface HttpCallback<T> {
    public final int REQUEST_TYPE_GET = 0;
    public final int REQUEST_TYPE_POST = 1;
    public final int REQUEST_TYPE_DELETE = 2;
    public final int RETURN_TYPE_FAILURE = 0;
    public final int RETURN_TYPE_SUCCESS = 1;


    /**
     * Http Callback from Api client, to update result on UI thread
     *
     * @param apiType     int value of the api url called, useful when one fragment/activity makes more than one API call
     * @param requestType REQUEST_TYPE_GET or REQUEST_TYPE_POST
     * @param returnType  RETURN_TYPE_FAILURE or RETURN_TYPE_SUCCESS
     * @param result      DataBean  that retrofit returns
     */
    void resultCallback(int apiType, int requestType, int returnType, int statusCode, T result);
}
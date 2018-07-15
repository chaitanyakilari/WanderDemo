package com.wanderdemo.utils;


import android.support.v4.app.Fragment;

import com.wanderdemo.R;
import com.wanderdemo.callback.HttpCallback;
import com.wanderdemo.constants.APIConstants;
import com.wanderdemo.constants.UrlConstants;
import com.wanderdemo.model.NotesData;
import com.wanderdemo.model.rest.ApiClient;
import com.wanderdemo.model.rest.ApiInterface;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wanderdemo.utils.UiUtils.getString;


public class ApiUtils {

    public static final String TAG = "ApiUtils";

    /**
     * Shows error messages based on exceptions generated from API calls
     *
     * @param result API response Object
     * @return isError
     */
    public static boolean isAPIException(Object result) {
        if (result instanceof UnknownHostException) {
            UiUtils.showToastSafe(getString(R.string.noNetwork));
            return true;
        }
        if (result instanceof SocketTimeoutException) {
            UiUtils.showToastSafe(getString(R
                    .string.badNetwork));
            return true;
        }
        return false;
    }

    /**
     * Shows error messages based on exceptions generated from API calls for fragments
     *
     * @param result   API response Object
     * @param fragment Fragment context
     * @return isError
     */
    public static boolean isAPIException(Object result, Fragment fragment) {
        if (result instanceof UnknownHostException) {
            UiUtils.showToast(fragment, getString(R.string.noNetwork));
            return false;
        }
        return true;
    }

    public static void getNotesData(final HttpCallback httpCallback) {

        ApiInterface apiService =
                ApiClient.getClient(UrlConstants.APP_SERVER_URL).create(ApiInterface.class);
        Call<Map<String, NotesData>> call = apiService.getNotesData();
        call.enqueue(new Callback<Map<String, NotesData>>() {
            @Override
            public void onResponse(Call<Map<String, NotesData>> call, Response<Map<String, NotesData>> response) {

                if (response.code() == 200) {
                    final Map<String, NotesData> notesDataMap = response.body();
                    httpCallback.resultCallback(APIConstants.API_GET_NOTES, HttpCallback.REQUEST_TYPE_GET,
                            HttpCallback.RETURN_TYPE_SUCCESS, response.code(), notesDataMap);
                } else {

                    httpCallback.resultCallback(APIConstants.API_GET_NOTES, HttpCallback.REQUEST_TYPE_GET,
                            HttpCallback.RETURN_TYPE_FAILURE, 0, "");
                }
            }

            @Override
            public void onFailure(Call<Map<String, NotesData>> call, Throwable t) {
                // Log error here since request failed
                httpCallback.resultCallback(APIConstants.API_GET_NOTES, HttpCallback.REQUEST_TYPE_GET,
                        HttpCallback.RETURN_TYPE_FAILURE, 0, t.getMessage());
                t.printStackTrace();
            }
        });
    }
}

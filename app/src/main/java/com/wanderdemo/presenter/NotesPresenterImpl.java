/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.wanderdemo.presenter;

import com.wanderdemo.callback.HttpCallback;
import com.wanderdemo.constants.APIConstants;
import com.wanderdemo.utils.ApiUtils;
import com.wanderdemo.view.BaseView;

public class NotesPresenterImpl implements NotesPresenter, HttpCallback {

    private BaseView baseView;

    public NotesPresenterImpl(BaseView baseView) {
        this.baseView = baseView;
    }


    @Override
    public void resultCallback(int apiType, int requestType, int returnType, int statusCode, Object result) {

        if (ApiUtils.isAPIException(result)) {

            baseView.onResult(result, returnType, apiType);
        }
        switch (apiType) {

            case APIConstants.API_GET_NOTES:

                switch (returnType) {

                    case HttpCallback.RETURN_TYPE_SUCCESS:

                        baseView.onResult(result, returnType, apiType);

                        break;

                    case HttpCallback.RETURN_TYPE_FAILURE:

                        baseView.onResult(result, returnType, apiType);

                        break;

                }

                break;


        }

    }

    @Override
    public void getNotes() {

        ApiUtils.getNotesData(this);
    }

}

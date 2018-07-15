package com.wanderdemo.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Tag;
import com.wanderdemo.R;
import com.wanderdemo.adapter.CommonAdapter;
import com.wanderdemo.adapter.base.ViewHolder;
import com.wanderdemo.callback.HttpCallback;
import com.wanderdemo.constants.APIConstants;
import com.wanderdemo.constants.KeyConstants;
import com.wanderdemo.constants.UrlConstants;
import com.wanderdemo.dialog.LoadingDialog;
import com.wanderdemo.model.NotesData;
import com.wanderdemo.model.rest.ApiClient;
import com.wanderdemo.model.rest.ApiInterface;
import com.wanderdemo.presenter.NotesPresenter;
import com.wanderdemo.presenter.NotesPresenterImpl;
import com.wanderdemo.utils.ApiUtils;
import com.wanderdemo.utils.NetUtil;
import com.wanderdemo.utils.UiUtils;
import com.wanderdemo.view.BaseView;
import com.wanderdemo.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment implements BaseView {
    public static final String FRAGMENT_TAG = HomeFragment.class.getSimpleName();
    private NotesData albumsData;
    @BindView(R.id.rvNotesList)
    RecyclerView rvNotesList;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    private CommonAdapter<NotesData> adapter;
    List<NotesData> arrayList;
    private NotesPresenter notesPresenter;
    private LoadingDialog pd;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {


        pd = new LoadingDialog(context, R.style.LoadingDialog);
        pd.setCancelable(false);
        pd.show();

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        tvToolbarTitle.setText(UiUtils.getString(R.string.home));
        RxBus.get().register(this);
        rvNotesList.setLayoutManager(new LinearLayoutManager(context));
        rvNotesList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        notesPresenter = new NotesPresenterImpl(this);

        if (NetUtil.isNetworkAvailable(context)) {
            pd.show();
            notesPresenter.getNotes();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomDialogFragment bottomDialogFragment = BottomDialogFragment.getInstance();
                bottomDialogFragment.show(getActivity().getSupportFragmentManager(), bottomDialogFragment.getTag());

            }
        });


    }

    @Override
    protected void initData(Bundle bundle) {


    }

    private void dismissDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    public void onResult(Object result, int returnType, int apiType) {

        if (ApiUtils.isAPIException(result)) {
            dismissDialog();
            return;
        }
        switch (apiType) {

            case APIConstants.API_GET_NOTES:

                switch (returnType) {

                    case HttpCallback.RETURN_TYPE_SUCCESS:

                        dismissDialog();

                        if (result instanceof Map) {

                            Map<String, NotesData> notesDataMap = (Map<String, NotesData>) result;
                            if (arrayList != null)
                                arrayList.clear();
                            arrayList = new ArrayList<NotesData>(notesDataMap.values());
                            adapter = new CommonAdapter<NotesData>(context, R.layout.row_layout, arrayList) {
                                @Override
                                protected void convert(ViewHolder holder, NotesData s, int position) {

                                    holder.setText(R.id.tvNotes, arrayList.get(position).getNotes());

                                }
                            };
                            rvNotesList.setAdapter(adapter);
                            dismissDialog();

                        }

                        break;

                    case HttpCallback.RETURN_TYPE_FAILURE:

                        dismissDialog();
                        UiUtils.showToastSafe(R.string.badNetwork);

                        break;

                }

                break;

        }

    }

    @com.hwangjr.rxbus.annotation.Subscribe(
            tags = {@Tag(KeyConstants.KEY_SAVE)}
    )
    public void uploadData(NotesData notesData) {

        if (NetUtil.isNetworkAvailable(context)) {

            pd.show();

            final ApiInterface apiService =
                    ApiClient.getClient(UrlConstants.APP_SERVER_URL).create(ApiInterface.class);

            apiService.saveNotes(notesData.getNotes(), notesData).flatMap(new Function<NotesData, ObservableSource<Map<String, NotesData>>>() {
                @Override
                public ObservableSource<Map<String, NotesData>> apply(NotesData notesData) throws Exception {
                    return apiService.getNotes();
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Map<String, NotesData>>() {
                @Override
                public void accept(Map<String, NotesData> stringNotesDataMap) throws Exception {

                    if (arrayList != null)
                        arrayList.clear();
                    arrayList = new ArrayList<NotesData>(stringNotesDataMap.values());


                    adapter = new CommonAdapter<NotesData>(context, R.layout.row_layout, arrayList) {
                        @Override
                        protected void convert(ViewHolder holder, NotesData s, int position) {

                            holder.setText(R.id.tvNotes, arrayList.get(position).getNotes());

                        }
                    };
                    rvNotesList.setAdapter(adapter);
                    dismissDialog();

                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }
}

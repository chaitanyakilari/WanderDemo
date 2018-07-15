package com.wanderdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hwangjr.rxbus.RxBus;
import com.wanderdemo.R;
import com.wanderdemo.constants.KeyConstants;
import com.wanderdemo.model.NotesData;
import com.wanderdemo.utils.UiUtils;

public class BottomDialogFragment extends BottomSheetDialogFragment {
    public static final String FRAGMENT_TAG = BottomDialogFragment.class.getSimpleName();
    Button btnSave;
    EditText etNotes;
    private Context context;

    public static BottomDialogFragment getInstance() {
        return new BottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.custom_bottom_sheet, container, false);
        this.context = getActivity();
        btnSave = view.findViewById(R.id.btnSave);
        etNotes = view.findViewById(R.id.etNotes);
        RxBus.get().register(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UiUtils.closeSoftKeys(context);

                if (!UiUtils.isEmpty(etNotes.getText().toString())) {

                    NotesData notesData = new NotesData();
                    notesData.setNotes(etNotes.getText().toString());
                    etNotes.setText("");
                    RxBus.get().post(KeyConstants.KEY_SAVE, notesData);
                    dismiss();

                } else {

                    UiUtils.showToastSafe(R.string.validate_text);
                    dismiss();
                }
            }
        });
        return view;
    }
}

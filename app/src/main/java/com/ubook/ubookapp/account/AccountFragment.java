package com.ubook.ubookapp.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ubook.ubookapp.base.BaseApplication;
import com.ubook.ubookapp.R;
import com.ubook.ubookapp.signin.SignInActivity;
import com.ubook.ubookapp.helper.UbookHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountFragment extends Fragment {

    @BindView(R.id.llLanguage)
    LinearLayout llLanguage;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    //onclick event
    @OnClick(R.id.llLanguage)
    public void onLanguage(){
        String[] languages = {"Tiếng Việt", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.language));
        builder.setItems(languages, (dialog, pos) -> {
            switch (pos){
                case 0: {
                    BaseApplication.getInstance().sharedPreferencesUtils.setLanguageCode("vi");
                    UbookHelper.changeLanguage("vi", getActivity());
                    break;
                }
                case 1: {
                    BaseApplication.getInstance().sharedPreferencesUtils.setLanguageCode("en");
                    UbookHelper.changeLanguage("en", getActivity());
                    break;
                }
            }
            UbookHelper.restartActivity(getActivity());
        });
        builder.show();
    }

    @OnClick(R.id.llSignOut)
    public void onSignOut() {
        BaseApplication.getInstance().sharedPreferencesUtils.signOut();
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

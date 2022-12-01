
package com.example.fani.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.fani.utils.DialogUtils;
import com.example.fani.utils.Helper.WifiStateHelper;
import com.example.fani.utils.LogUtil;

import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;

public abstract class BaseMVVMActivity<V extends ViewBinding, VM extends BaseViewModel> extends AppCompatActivity {

    private V binding;
    private VM viewModel;

    NoInternetDialogSignal noInternetDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = getLayoutBinding();
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(getViewModelClass());

        checkConnectWifi();

        initialize();

    }

    protected abstract V getLayoutBinding();

    protected abstract Class<VM> getViewModelClass();

    protected V getViewBinding() {
        return binding;
    }

    protected VM getViewModel() {
        return viewModel;
    }

    protected abstract void initialize();

    private void checkConnectWifi() {

        WifiStateHelper.wifiStateObs.observe(this, isConnect -> {
            LogUtil.e("wifiStateObs" + isConnect);
            if (!isConnect) {
                DialogUtils.displayDialogInternet(this, getLifecycle());
                LogUtil.e("No wifi");
            } else {
                LogUtil.e("wifi connect");
                if (noInternetDialog != null) {
                    noInternetDialog.destroy();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogUtils.displayDialogInternet(this, getLifecycle());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (noInternetDialog != null) {
            noInternetDialog.destroy();
        }
    }
}
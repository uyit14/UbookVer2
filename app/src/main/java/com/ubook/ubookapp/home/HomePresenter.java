package com.ubook.ubookapp.home;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.ubook.ubookapp.base.BaseApplication;
import com.ubook.ubookapp.base.BasePresenter;
import com.ubook.ubookapp.base.BaseResponse;
import com.ubook.ubookapp.helper.UbookHelper;
import com.ubook.ubookapp.network.model.ListShops;
import com.ubook.ubookapp.network.service.ApiFunction;
import com.ubook.ubookapp.network.service.ApiStatus;
import com.ubook.ubookapp.utils.Constants;
import com.ubook.ubookapp.utils.InternetConnection;

public class HomePresenter extends BasePresenter<IHome.View> implements IHome.Presenter {
    public HomePresenter(IHome.View mView) {
        super(mView);
    }

    @Override
    public void requestGetShopByLocation(Integer distance, Integer page, Integer pageRecords, Double geoLng, Double geoLat, String timezone) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("distance", distance);
        jsonObject.addProperty("page", page);
        jsonObject.addProperty("page_size", pageRecords);
        jsonObject.addProperty("geo_lng", geoLng);
        jsonObject.addProperty("geo_lat", geoLat);
        if (!TextUtils.isEmpty(timezone)) {
            jsonObject.addProperty(Constants.TIME_ZONE, timezone);
        }
        if (!InternetConnection.getInstance().isOnline(BaseApplication.getInstance())) {
            getmView().networkConnectError();
            return;
        }
        getmView().showDialogLoading();
        mApiMethod.shopsByLocation(UbookHelper.createTokenHeader(BaseApplication.getInstance().sharedPreferencesUtils.getAccessToken()),"2.5.2", jsonObject);
    }

    @Override
    public void onResponseListener(ApiFunction apifunction, ApiStatus statusId, Object object) {
        if (!InternetConnection.getInstance().isOnline(BaseApplication.getInstance())) {
            getmView().networkConnectError();
            return;
        }
        switch (statusId){
            case CALL_API_RESULT_SUCCESS:
                switch (apifunction) {
                    case SHOP_BY_LOCATION:
                        BaseResponse<ListShops> listShops = (BaseResponse<ListShops>) object;
                        getmView().hideDialogLoading();
                        getmView().getShopByLocationSuccess(listShops.getData());
                        break;
                }
                break;


            case CALL_API_RESULT_FAILURE: {
                switch (apifunction) {
                    case SHOP_BY_LOCATION:
                        getmView().hideDialogLoading();
                        getmView().getShopByLocationFail(object.toString());
                        break;
                }
                break;
            }
        }
    }
}

package com.ubook.ubookapp.home;

import com.ubook.ubookapp.base.BaseView;
import com.ubook.ubookapp.network.model.ListShops;

public interface IHome {
    interface View extends BaseView{
        void getShopByLocationSuccess(ListShops listShops);
        void getShopByLocationFail(String messageError);
    }

    interface Presenter{
        void requestGetShopByLocation(Integer distance, Integer page, Integer pageRecords, Double geoLng, Double geoLat, String timezone);
    }
}

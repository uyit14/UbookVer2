package com.ubook.ubookapp.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop implements Parcelable, Comparable<Shop> {
    @SerializedName("price_min")
    @Expose
    private String priceMin;
    @SerializedName("promotion_url")
    @Expose
    private String promotionUrl;
    @SerializedName("geo_lat")
    @Expose
    private Double geoLat;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("promotion_title")
    @Expose
    private String promotionTitle;
    @SerializedName("price_max")
    @Expose
    private String priceMax;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("opening")
    @Expose
    private String opening;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("language_code")
    @Expose
    private String languageCode;
    @SerializedName("wifi_ssid")
    @Expose
    private String wifiSsid;
    @SerializedName("inventory")
    @Expose
    private Inventory inventory;
    @SerializedName("wifi_mac")
    @Expose
    private String wifiMac;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("geo_lng")
    @Expose
    private Double geoLng;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("wifi_password")
    @Expose
    private String wifiPassword;
    @SerializedName("wifi_ip")
    @Expose
    private String wifiIp;
    @SerializedName("shop_photo")
    @Expose
    private String shopPhoto;
    @SerializedName("is_opening")
    private boolean isOpening;

    public boolean isOpening() {
        return isOpening;
    }

    public void setOpening(boolean opening) {
        isOpening = opening;
    }

    private boolean isChosen;

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public String getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPromotionUrl() {
        return promotionUrl;
    }

    public void setPromotionUrl(String promotionUrl) {
        this.promotionUrl = promotionUrl;
    }

    public Double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(Double geoLat) {
        this.geoLat = geoLat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getWifiSsid() {
        return wifiSsid;
    }

    public void setWifiSsid(String wifiSsid) {
        this.wifiSsid = wifiSsid;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getWifiMac() {
        return wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(Double geoLng) {
        this.geoLng = geoLng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public String getWifiIp() {
        return wifiIp;
    }

    public void setWifiIp(String wifiIp) {
        this.wifiIp = wifiIp;
    }

    @Override
    public int compareTo(Shop shop) {
        if (convertDistance(getDistance()) == 0 || shop.getDistance() == null) {
            return 0;
        }
        if (convertDistance(getDistance()) > convertDistance(shop.getDistance())) {
            return 1;
        } else if (convertDistance(getDistance()) == convertDistance(shop.getDistance())) {
            return 0;
        } else {
            return -1;
        }
    }

    private double convertDistance(String distance) {
        try {
            return Double.parseDouble(distance);
        } catch (Exception e) {
            return 0;
        }
    }

    public static class Inventory implements Parcelable {

        @SerializedName("total_slots")
        @Expose
        private Long totalSlots;
        @SerializedName("total_sockets")
        @Expose
        private Long totalSockets;
        @SerializedName("return_slots")
        @Expose
        private Long returnSlots;
        @SerializedName("total_not_available")
        @Expose
        private Long totalNotAvailable;
        @SerializedName("total_available")
        @Expose
        private Long totalAvailable;
        @SerializedName("available")
        @Expose
        private Available available;

        public Long getTotalSlots() {
            return totalSlots;
        }

        public void setTotalSlots(Long totalSlots) {
            this.totalSlots = totalSlots;
        }

        public Long getTotalSockets() {
            return totalSockets;
        }

        public void setTotalSockets(Long totalSockets) {
            this.totalSockets = totalSockets;
        }

        public Long getReturnSlots() {
            return returnSlots;
        }

        public void setReturnSlots(Long returnSlots) {
            this.returnSlots = returnSlots;
        }

        public Long getTotalNotAvailable() {
            return totalNotAvailable;
        }

        public void setTotalNotAvailable(Long totalNotAvailable) {
            this.totalNotAvailable = totalNotAvailable;
        }

        public Long getTotalAvailable() {
            return totalAvailable;
        }

        public void setTotalAvailable(Long totalAvailable) {
            this.totalAvailable = totalAvailable;
        }

        public Available getAvailable() {
            return available;
        }

        public void setAvailable(Available available) {
            this.available = available;
        }


        public static class Available implements Parcelable {

            @SerializedName("pac-lightning")
            @Expose
            private Long pacLightning;
            @SerializedName("pac-mini-usb")
            @Expose
            private Long pacMiniUsb;
            @SerializedName("pac-usb-type-c")
            @Expose
            private Long pacUsbTypeC;

            public Long getPacLightning() {
                return pacLightning;
            }

            public void setPacLightning(Long pacLightning) {
                this.pacLightning = pacLightning;
            }

            public Long getPacMiniUsb() {
                return pacMiniUsb;
            }

            public void setPacMiniUsb(Long pacMiniUsb) {
                this.pacMiniUsb = pacMiniUsb;
            }

            public Long getPacUsbTypeC() {
                return pacUsbTypeC;
            }

            public void setPacUsbTypeC(Long pacUsbTypeC) {
                this.pacUsbTypeC = pacUsbTypeC;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(this.pacLightning);
                dest.writeValue(this.pacMiniUsb);
                dest.writeValue(this.pacUsbTypeC);
            }

            public Available() {
            }

            protected Available(Parcel in) {
                this.pacLightning = (Long) in.readValue(Long.class.getClassLoader());
                this.pacMiniUsb = (Long) in.readValue(Long.class.getClassLoader());
                this.pacUsbTypeC = (Long) in.readValue(Long.class.getClassLoader());
            }

            public static final Creator<Available> CREATOR = new Creator<Available>() {
                @Override
                public Available createFromParcel(Parcel source) {
                    return new Available(source);
                }

                @Override
                public Available[] newArray(int size) {
                    return new Available[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.totalSlots);
            dest.writeValue(this.totalSockets);
            dest.writeValue(this.returnSlots);
            dest.writeValue(this.totalNotAvailable);
            dest.writeValue(this.totalAvailable);
            dest.writeParcelable(this.available, flags);
        }

        public Inventory() {
        }

        protected Inventory(Parcel in) {
            this.totalSlots = (Long) in.readValue(Long.class.getClassLoader());
            this.totalSockets = (Long) in.readValue(Long.class.getClassLoader());
            this.returnSlots = (Long) in.readValue(Long.class.getClassLoader());
            this.totalNotAvailable = (Long) in.readValue(Long.class.getClassLoader());
            this.totalAvailable = (Long) in.readValue(Long.class.getClassLoader());
            this.available = in.readParcelable(Available.class.getClassLoader());
        }

        public static final Creator<Inventory> CREATOR = new Creator<Inventory>() {
            @Override
            public Inventory createFromParcel(Parcel source) {
                return new Inventory(source);
            }

            @Override
            public Inventory[] newArray(int size) {
                return new Inventory[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.priceMin);
        dest.writeString(this.promotionUrl);
        dest.writeValue(this.geoLat);
        dest.writeString(this.name);
        dest.writeValue(this.id);
        dest.writeString(this.contact);
        dest.writeString(this.promotionTitle);
        dest.writeString(this.priceMax);
        dest.writeString(this.code);
        dest.writeString(this.distance);
        dest.writeString(this.duration);
        dest.writeString(this.opening);
        dest.writeString(this.address);
        dest.writeString(this.languageCode);
        dest.writeString(this.wifiSsid);
        dest.writeParcelable(this.inventory, flags);
        dest.writeString(this.wifiMac);
        dest.writeString(this.token);
        dest.writeValue(this.geoLng);
        dest.writeString(this.description);
        dest.writeString(this.wifiPassword);
        dest.writeString(this.wifiIp);
        dest.writeString(this.shopPhoto);
        dest.writeByte((byte) (isOpening ? 1 : 0));
    }

    public Shop() {
    }

    protected Shop(Parcel in) {
        this.priceMin = in.readString();
        this.promotionUrl = in.readString();
        this.geoLat = (Double) in.readValue(Double.class.getClassLoader());
        this.name = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.contact = in.readString();
        this.promotionTitle = in.readString();
        this.priceMax = in.readString();
        this.code = in.readString();
        this.distance = in.readString();
        this.duration = in.readString();
        this.opening = in.readString();
        this.address = in.readString();
        this.languageCode = in.readString();
        this.wifiSsid = in.readString();
        this.inventory = in.readParcelable(Inventory.class.getClassLoader());
        this.wifiMac = in.readString();
        this.token = in.readString();
        this.geoLng = (Double) in.readValue(Double.class.getClassLoader());
        this.description = in.readString();
        this.wifiPassword = in.readString();
        this.wifiIp = in.readString();
        this.shopPhoto = in.readString();
        isOpening = in.readByte() != 0;
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}

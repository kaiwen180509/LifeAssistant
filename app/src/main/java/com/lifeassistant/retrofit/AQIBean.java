package com.lifeassistant.retrofit;

import com.google.gson.annotations.SerializedName;

public class AQIBean {
    /**
     * SiteName : 屏東(琉球)
     * County : 屏東縣
     * AQI : 143
     * Pollutant : 臭氧八小時
     * Status : 對敏感族群不健康
     * SO2 : 2.1
     * CO : 0.38
     * CO_8hr : 0.4
     * O3 : 74
     * O3_8hr : 83
     * PM10 : 62
     * PM2.5 : 35
     * NO2 : 8
     * NOx : 8
     * NO : 0
     * WindSpeed : 0.5
     * WindDirec : 0.1
     * PublishTime : 2019-02-27 19:00
     * PM2.5_AVG : 49
     * PM10_AVG : 81
     * SO2_AVG : 4
     * Longitude : 120.377222
     * Latitude : 22.352222
     */

    private String SiteName;
    private String County;
    private String AQI;
    private String Pollutant;
    private String Status;
    private String SO2;
    private String CO;
    private String CO_8hr;
    private String O3;
    private String O3_8hr;
    private String PM10;
    @SerializedName("PM2.5")
    private String _$PM2554; // FIXME check this code
    private String NO2;
    private String NOx;
    private String NO;
    private String WindSpeed;
    private String WindDirec;
    private String PublishTime;
    @SerializedName("PM2.5_AVG")
    private String _$PM25_AVG3; // FIXME check this code
    private String PM10_AVG;
    private String SO2_AVG;
    private String Longitude;
    private String Latitude;

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String SiteName) {
        this.SiteName = SiteName;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String County) {
        this.County = County;
    }

    public String getAQI() {
        return AQI;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    public String getPollutant() {
        return Pollutant;
    }

    public void setPollutant(String Pollutant) {
        this.Pollutant = Pollutant;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getSO2() {
        return SO2;
    }

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getCO_8hr() {
        return CO_8hr;
    }

    public void setCO_8hr(String CO_8hr) {
        this.CO_8hr = CO_8hr;
    }

    public String getO3() {
        return O3;
    }

    public void setO3(String O3) {
        this.O3 = O3;
    }

    public String getO3_8hr() {
        return O3_8hr;
    }

    public void setO3_8hr(String O3_8hr) {
        this.O3_8hr = O3_8hr;
    }

    public String getPM10() {
        return PM10;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public String get_$PM2554() {
        return _$PM2554;
    }

    public void set_$PM2554(String _$PM2554) {
        this._$PM2554 = _$PM2554;
    }

    public String getNO2() {
        return NO2;
    }

    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }

    public String getNOx() {
        return NOx;
    }

    public void setNOx(String NOx) {
        this.NOx = NOx;
    }

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(String WindSpeed) {
        this.WindSpeed = WindSpeed;
    }

    public String getWindDirec() {
        return WindDirec;
    }

    public void setWindDirec(String WindDirec) {
        this.WindDirec = WindDirec;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String PublishTime) {
        this.PublishTime = PublishTime;
    }

    public String get_$PM25_AVG3() {
        return _$PM25_AVG3;
    }

    public void set_$PM25_AVG3(String _$PM25_AVG3) {
        this._$PM25_AVG3 = _$PM25_AVG3;
    }

    public String getPM10_AVG() {
        return PM10_AVG;
    }

    public void setPM10_AVG(String PM10_AVG) {
        this.PM10_AVG = PM10_AVG;
    }

    public String getSO2_AVG() {
        return SO2_AVG;
    }

    public void setSO2_AVG(String SO2_AVG) {
        this.SO2_AVG = SO2_AVG;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }
}

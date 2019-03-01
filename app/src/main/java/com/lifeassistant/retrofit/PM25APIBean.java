package com.lifeassistant.retrofit;

public class PM25APIBean {
    /**
     * Site : 崙背
     * county : 雲林縣
     * PM25 : 40
     * DataCreationDate : 2019-01-31 10:00
     * ItemUnit : μg/m3
     */

    private String Site;
    private String county;
    private String PM25;
    private String DataCreationDate;
    private String ItemUnit;

    public String getSite() {
        return Site;
    }

    public void setSite(String Site) {
        this.Site = Site;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPM25() {
        return PM25;
    }

    public void setPM25(String PM25) {
        this.PM25 = PM25;
    }

    public String getDataCreationDate() {
        return DataCreationDate;
    }

    public void setDataCreationDate(String DataCreationDate) {
        this.DataCreationDate = DataCreationDate;
    }

    public String getItemUnit() {
        return ItemUnit;
    }

    public void setItemUnit(String ItemUnit) {
        this.ItemUnit = ItemUnit;
    }
}

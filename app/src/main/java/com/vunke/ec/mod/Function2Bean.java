package com.vunke.ec.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/5/24.
 */
public class Function2Bean {

    /**
     * data : [{"implement_id":7,"implement_address":"com.vunke.ec.activity.OfficeGuideActivity",implement_package : "","image":"http://124.232.136.236:8083/kjmgr/image/function_mainview1.png"},{"implement_id":8,"implement_address":"com.vunke.ec.activity.PartyAffairsActivity",implement_package : "","image":"http://124.232.136.236:8083/kjmgr/image/function_mainview2.png"},{"implement_id":9,"implement_address":"com.vunke.ec.activity.VillageAffairsActivity",implement_package : "","image":"http://124.232.136.236:8083/kjmgr/image/function_mainview3.png"},{"implement_id":10,"implement_address":"com.vunke.ec.activity.PovertyAlleviationActivity",implement_package : "","image":"http://124.232.136.236:8083/kjmgr/image/function_mainview4.png"}]
     * title : 益村信息
     */

    private String title;
    /**
     * implement_id : 7
     * implement_address : com.vunke.ec.activity.OfficeGuideActivity
     * image : http://124.232.136.236:8083/kjmgr/image/function_mainview1.png
     * ,implement_package : ""
     */

    private List<DataBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String implement_id;
        private String implement_address;
        private String image;
        private String implement_package;
        private String implement_titie;

        public String getImplement_titie() {
            return implement_titie;
        }

        public void setImplement_titie(String implement_titie) {
            this.implement_titie = implement_titie;
        }

        public String getImplement_id() {
            return implement_id;
        }

        public void setImplement_id(String implement_id) {
            this.implement_id = implement_id;
        }

        public String getImplement_address() {
            return implement_address;
        }

        public void setImplement_address(String implement_address) {
            this.implement_address = implement_address;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImplement_package() {
            return implement_package;
        }

        public void setImplement_package(String implement_package) {
            this.implement_package = implement_package;
        }
    }
}

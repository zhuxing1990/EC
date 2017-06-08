package com.vunke.ec.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/5/30.
 */
public class getPBlockIdBean {

    /**
     * data : [{"accessnum":0,"blockid":"0201","blockname":"村务公开","hrefurl":"","modeno":"1.0","pblockid":"02","sublevel":2},{"accessnum":0,"blockid":"0202","blockname":"最美家乡","hrefurl":"","modeno":"1.0","pblockid":"02","sublevel":2},{"accessnum":0,"blockid":"0203","blockname":"益村头条","hrefurl":"","modeno":"1.0","pblockid":"02","sublevel":2},{"accessnum":0,"blockid":"0204","blockname":"扶贫公示","hrefurl":"","modeno":"1.0","pblockid":"02","sublevel":2},{"accessnum":0,"blockid":"0205","blockname":"扶贫政策","hrefurl":"","modeno":"1.0","pblockid":"02","sublevel":3}]
     * code : 200
     * message : 成功
     */

    private String code;
    private String message;
    /**
     * accessnum : 0
     * blockid : 0201
     * blockname : 村务公开
     * hrefurl :
     * modeno : 1.0
     * pblockid : 02
     * sublevel : 2
     */

    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "getPBlockIdBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int accessnum;
        private String blockid;
        private String blockname;
        private String hrefurl;
        private String modeno;
        private String pblockid;
        private int sublevel;

        public int getAccessnum() {
            return accessnum;
        }

        public void setAccessnum(int accessnum) {
            this.accessnum = accessnum;
        }

        public String getBlockid() {
            return blockid;
        }

        public void setBlockid(String blockid) {
            this.blockid = blockid;
        }

        public String getBlockname() {
            return blockname;
        }

        public void setBlockname(String blockname) {
            this.blockname = blockname;
        }

        public String getHrefurl() {
            return hrefurl;
        }

        public void setHrefurl(String hrefurl) {
            this.hrefurl = hrefurl;
        }

        public String getModeno() {
            return modeno;
        }

        public void setModeno(String modeno) {
            this.modeno = modeno;
        }

        public String getPblockid() {
            return pblockid;
        }

        public void setPblockid(String pblockid) {
            this.pblockid = pblockid;
        }

        public int getSublevel() {
            return sublevel;
        }

        public void setSublevel(int sublevel) {
            this.sublevel = sublevel;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "accessnum=" + accessnum +
                    ", blockid='" + blockid + '\'' +
                    ", blockname='" + blockname + '\'' +
                    ", hrefurl='" + hrefurl + '\'' +
                    ", modeno='" + modeno + '\'' +
                    ", pblockid='" + pblockid + '\'' +
                    ", sublevel=" + sublevel +
                    '}';
        }
    }
}

package com.vunke.ec.mod;

/**
 * Created by zhuxi on 2017/5/26.
 */
public class DetailsBean {

    /**
     * type : 党务公开
     * mode : {"authname":"李鸿","blockid":"0101","content":"<p><img src=\"http://124.232.136.236:8083/kjmgr/bgmgr/images/ecimages/36b54452aec60ea1dabf26dc1ddeb0f6.png\" title=\"title\" alt=\"blob.png\" style=\"white-space: normal;\"/><\/p>","createtime":"2017-05-11","infoid":11382,"inter1":0,"inter2":0,"inter3":0,"inter4":0,"istop":"","lastauthtime":"","lasttime":"","mainpicurl":"","modeno":"1.0","pic1":"","pic2":"","pic3":"","pic4":"","pic5":"","relblocks":"","seqno":11382,"stataccessnum":13,"state":"10.2","string1":"邹家桥村","string10":"42","string11":"","string12":"","string13":"","string14":"","string15":"1311685703115776","string2":"邹家桥村","string3":"","string4":"","string5":"","string6":"","string7":"","string8":"","string9":"","tag":"","title":"邹家桥村2017年党员一句话承诺","txt1":"","txt2":"","txt3":"","type1":"","type2":"html","type3":"","type4":"","type5":"","type6":"","userid":0,"username":""}
     * code : 200
     * message : 请求成功
     */

    private String type;
    /**
     * authname : 李鸿
     * blockid : 0101
     * content : <p><img src="http://124.232.136.236:8083/kjmgr/bgmgr/images/ecimages/36b54452aec60ea1dabf26dc1ddeb0f6.png" title="title" alt="blob.png" style="white-space: normal;"/></p>
     * createtime : 2017-05-11
     * infoid : 11382
     * inter1 : 0
     * inter2 : 0
     * inter3 : 0
     * inter4 : 0
     * istop :
     * lastauthtime :
     * lasttime :
     * mainpicurl :
     * modeno : 1.0
     * pic1 :
     * pic2 :
     * pic3 :
     * pic4 :
     * pic5 :
     * relblocks :
     * seqno : 11382
     * stataccessnum : 13
     * state : 10.2
     * string1 : 邹家桥村
     * string10 : 42
     * string11 :
     * string12 :
     * string13 :
     * string14 :
     * string15 : 1311685703115776
     * string2 : 邹家桥村
     * string3 :
     * string4 :
     * string5 :
     * string6 :
     * string7 :
     * string8 :
     * string9 :
     * tag :
     * title : 邹家桥村2017年党员一句话承诺
     * txt1 :
     * txt2 :
     * txt3 :
     * type1 :
     * type2 : html
     * type3 :
     * type4 :
     * type5 :
     * type6 :
     * userid : 0
     * username :
     */

    private ModeBean mode;
    private String code;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ModeBean getMode() {
        return mode;
    }

    public void setMode(ModeBean mode) {
        this.mode = mode;
    }

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

    public static class ModeBean {
        private String authname;
        private String blockid;
        private String content;
        private String createtime;
        private int infoid;
        private int inter1;
        private int inter2;
        private int inter3;
        private int inter4;
        private String istop;
        private String lastauthtime;
        private String lasttime;
        private String mainpicurl;
        private String modeno;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic4;
        private String pic5;
        private String relblocks;
        private int seqno;
        private int stataccessnum;
        private String state;
        private String string1;
        private String string10;
        private String string11;
        private String string12;
        private String string13;
        private String string14;
        private String string15;
        private String string2;
        private String string3;
        private String string4;
        private String string5;
        private String string6;
        private String string7;
        private String string8;
        private String string9;
        private String tag;
        private String title;
        private String txt1;
        private String txt2;
        private String txt3;
        private String type1;
        private String type2;
        private String type3;
        private String type4;
        private String type5;
        private String type6;
        private int userid;
        private String username;

        public String getAuthname() {
            return authname;
        }

        public void setAuthname(String authname) {
            this.authname = authname;
        }

        public String getBlockid() {
            return blockid;
        }

        public void setBlockid(String blockid) {
            this.blockid = blockid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getInfoid() {
            return infoid;
        }

        public void setInfoid(int infoid) {
            this.infoid = infoid;
        }

        public int getInter1() {
            return inter1;
        }

        public void setInter1(int inter1) {
            this.inter1 = inter1;
        }

        public int getInter2() {
            return inter2;
        }

        public void setInter2(int inter2) {
            this.inter2 = inter2;
        }

        public int getInter3() {
            return inter3;
        }

        public void setInter3(int inter3) {
            this.inter3 = inter3;
        }

        public int getInter4() {
            return inter4;
        }

        public void setInter4(int inter4) {
            this.inter4 = inter4;
        }

        public String getIstop() {
            return istop;
        }

        public void setIstop(String istop) {
            this.istop = istop;
        }

        public String getLastauthtime() {
            return lastauthtime;
        }

        public void setLastauthtime(String lastauthtime) {
            this.lastauthtime = lastauthtime;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public String getMainpicurl() {
            return mainpicurl;
        }

        public void setMainpicurl(String mainpicurl) {
            this.mainpicurl = mainpicurl;
        }

        public String getModeno() {
            return modeno;
        }

        public void setModeno(String modeno) {
            this.modeno = modeno;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
        }

        public String getRelblocks() {
            return relblocks;
        }

        public void setRelblocks(String relblocks) {
            this.relblocks = relblocks;
        }

        public int getSeqno() {
            return seqno;
        }

        public void setSeqno(int seqno) {
            this.seqno = seqno;
        }

        public int getStataccessnum() {
            return stataccessnum;
        }

        public void setStataccessnum(int stataccessnum) {
            this.stataccessnum = stataccessnum;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getString1() {
            return string1;
        }

        public void setString1(String string1) {
            this.string1 = string1;
        }

        public String getString10() {
            return string10;
        }

        public void setString10(String string10) {
            this.string10 = string10;
        }

        public String getString11() {
            return string11;
        }

        public void setString11(String string11) {
            this.string11 = string11;
        }

        public String getString12() {
            return string12;
        }

        public void setString12(String string12) {
            this.string12 = string12;
        }

        public String getString13() {
            return string13;
        }

        public void setString13(String string13) {
            this.string13 = string13;
        }

        public String getString14() {
            return string14;
        }

        public void setString14(String string14) {
            this.string14 = string14;
        }

        public String getString15() {
            return string15;
        }

        public void setString15(String string15) {
            this.string15 = string15;
        }

        public String getString2() {
            return string2;
        }

        public void setString2(String string2) {
            this.string2 = string2;
        }

        public String getString3() {
            return string3;
        }

        public void setString3(String string3) {
            this.string3 = string3;
        }

        public String getString4() {
            return string4;
        }

        public void setString4(String string4) {
            this.string4 = string4;
        }

        public String getString5() {
            return string5;
        }

        public void setString5(String string5) {
            this.string5 = string5;
        }

        public String getString6() {
            return string6;
        }

        public void setString6(String string6) {
            this.string6 = string6;
        }

        public String getString7() {
            return string7;
        }

        public void setString7(String string7) {
            this.string7 = string7;
        }

        public String getString8() {
            return string8;
        }

        public void setString8(String string8) {
            this.string8 = string8;
        }

        public String getString9() {
            return string9;
        }

        public void setString9(String string9) {
            this.string9 = string9;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTxt1() {
            return txt1;
        }

        public void setTxt1(String txt1) {
            this.txt1 = txt1;
        }

        public String getTxt2() {
            return txt2;
        }

        public void setTxt2(String txt2) {
            this.txt2 = txt2;
        }

        public String getTxt3() {
            return txt3;
        }

        public void setTxt3(String txt3) {
            this.txt3 = txt3;
        }

        public String getType1() {
            return type1;
        }

        public void setType1(String type1) {
            this.type1 = type1;
        }

        public String getType2() {
            return type2;
        }

        public void setType2(String type2) {
            this.type2 = type2;
        }

        public String getType3() {
            return type3;
        }

        public void setType3(String type3) {
            this.type3 = type3;
        }

        public String getType4() {
            return type4;
        }

        public void setType4(String type4) {
            this.type4 = type4;
        }

        public String getType5() {
            return type5;
        }

        public void setType5(String type5) {
            this.type5 = type5;
        }

        public String getType6() {
            return type6;
        }

        public void setType6(String type6) {
            this.type6 = type6;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}


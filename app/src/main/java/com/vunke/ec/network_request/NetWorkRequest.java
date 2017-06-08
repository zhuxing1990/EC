package com.vunke.ec.network_request;

/**
 * Created by zhuxi on 2017/3/29.
 */
public class NetWorkRequest {
    /**
     * 基础地址
     */
    public static final String BaseUrl = "http://124.232.136.236:8083";
//    public static final String BaseUrl2 = "http://192.168.0.123:8082";

    /**
     * 首页地址
     */
//    public static final String HOME_DATE = "/intf/index.shtml";
    public static final String HOME_DATE2 = "/kjmgr/intf/index.shtml";

    /**
     * 根据info_id查询单个详细信息
     */
//    public static final String  FIND_BYI_ID_INFO = "/intf/findByiIdInfo.shtml";
    public static final String  FIND_BYI_ID_INFO2 = "/kjmgr/intf/findByIdInfo.shtml";



    /**
     * 收费内容点击量接口
     */
//    public static final String SETTLEMENT = "/intf/Settlement.shtml";
    public static final String SETTLEMENT2 = "/kjmgr/intf/Settlement.shtml";

    /**
     * 排行榜查询接口
     */
//    public static final String SELECT_RANKINGS = "/inter/selectAllRanking.shtml";
    public static final String SELECT_RANKINGS2 = "/kjmgr/inter/selectAllRanking.shtml";



    /**
     *推荐位点击量接口
     */
//    public static final String STATISTICES_RBIT = "/intf/StatisticsRbit.shtml";
    public static final String STATISTICES_RBIT2 = "/kjmgr/intf/StatisticsRbit.shtml";


    /**
     *相关视频查询接口
     */
//    public static final String INFO_VIDEO_QUERY = "/intf/InfoVideoQuery.shtml";
    public static final String INFO_VIDEO_QUERY2 = "/kjmgr/intf/InfoVideoQuery.shtml";

    /**
     * 查询单个栏目接口
     */
    public static final String QUERY_INFO_JSON = "/kjmgr/ec/info/queryInfoJson.shtml?blockid=";

    public static final String FINDBY_BLOCK_ID = "/kjmgr/intf/findByBlockId.shtml";

    /**
     * 查询栏目内的分类
     */
    public static final String FINDBY_PBLOCK_ID = "/kjmgr/intf/findByPblockId.shtml";
}

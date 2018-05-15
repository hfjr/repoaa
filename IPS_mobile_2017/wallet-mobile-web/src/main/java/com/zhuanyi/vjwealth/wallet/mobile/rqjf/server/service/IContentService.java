package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service;

import java.util.List;
import java.util.Map;

public interface IContentService {


    /**
     * 查询列表
     * [
     {
     "content_add_datetime": 1501653362000,
     "content_update_datetime": 1501653362000,
     "content_publish_time": 1501653362000,
     "content_source_link": "",
     "content_update_user": "ganrui",
     "external_link_title": "http://www.xxxx.com/August.shtml",
     "content_create_user": "ganrui",
     "content_summary": "",
     "content_author": "",
     "content_txt": "",
     "attach_path": "5993b06d34024327c6fbeb3c",
     "content_picture": "",
     "content_source": "原创",
     "content_title": "8月主活动",
     "id": 1264,
     "content_status": 1,
     "app_external_link": "",
     "channel_ids": "15"
     }
     ]
     *
     * @return
     */
    List<Map<String,Object>> queryList(String page, String channelIds, String contentSequence);


}

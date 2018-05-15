package com.zhuanyi.vjwealth.wallet.mobile.hlb.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.MyAssetsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.WjActivityInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserShareInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IHLBWeiXinMapper {

    //    获取帮助中心一级标题
    public List<Map<String, String>> helpCenterTypeWX();

    //    获取帮助中心二级标题
    public List<Map<String, String>> helpCenterTypeSubWX();

    //查询客服热线
    String queryServiceHotLine();

    List<BillListQueryDTO> getAllBillListByUserIdAndType(@Param("userId") String userId, @Param("page") Integer page);

    List<BillListQueryDTO> getIncomeBillListByUserIdAndType(@Param("userId") String userId, @Param("page") Integer page);

    List<BillListQueryDTO> getWithdrawBillListByUserIdAndType(@Param("userId") String userId, @Param("page") Integer page);

    List<BillListQueryDTO> getFrozenAllBillListByUserIdAndType(@Param("userId") String userId, @Param("page") Integer page);

    List<MyAssetsDTO> queryMyAssets(String userId);

    List<WjActivityInfoDTO> queryAppHomeActivityList();

    UserShareInfoDTO queryHLBFriendHelpUserShareInfo();
}

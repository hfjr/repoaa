package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.SHQBUserInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface ISHQBUserMapper {
    SHQBUserInfoDTO queryUserInfoByPhone(@Param("phone") String phone);

    SHQBUserInfoDTO queryUserInfoByIdentityNo(@Param("identityNo") String identityNo);

    int updateUserBankCardNo(@Param("phone") String phone, @Param("bankCardNo") String bankCardNo);

    String querySHQBEnterpriseId();

    int saveChannelForSHQB(@Param("userId") String userId);

    String queryUserSecurityCardNo(@Param("userId") String userId);

    int updateUserSecurityCardNo(@Param("userId") String userId, @Param("newBankCardNo") String newBankCardNo, @Param("oldBankCardNo") String oldBankCardNo,@Param("bankName") String bankName,@Param
            ("bankCode") String bankCode);

    int createUserInfo(SHQBUserInfoDTO userInfoDTO);

    Map<String, String> queryBankInfoByCardBin(@Param("cardBin6") String cardBin6, @Param("cardBin8") String cardBin8);

    String queryNoMatchTip();
}

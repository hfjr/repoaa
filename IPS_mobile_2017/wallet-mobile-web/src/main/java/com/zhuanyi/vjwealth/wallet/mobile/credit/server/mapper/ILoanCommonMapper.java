package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd.BankTypeInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd.BindedCardDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by yi on 16/5/20.
 */
@Mapper
public interface ILoanCommonMapper {
	
	/**
	 * 查询用户的安全卡
	 * @param
	 * @return
	 */
	BindedCardDTO queryUserSecurityCardList(@Param("userId") String userId);

	/**
	 * 查询用户基本信息
	 */
	UserInfoDTO queryUserInfoById(@Param("userId") String userId);


	/**
	 * 根据卡bin查询卡信息
	 * @param binNo
	 * @return
     */
	BankTypeInfoDTO queryBankinfoByCardBin(@Param("binNo") String binNo);

	boolean validatorBankIsNormal(@Param("bankCode") String bankCode,@Param("plateformId") String plateformId);


	/**
	 * 查询用户的安全卡
	 * @param
	 * @return
	 */
	BindedCardDTO queryUserRechargeCardByCardNo(@Param("userId") String userId,@Param("cardNo") String cardNo);

}

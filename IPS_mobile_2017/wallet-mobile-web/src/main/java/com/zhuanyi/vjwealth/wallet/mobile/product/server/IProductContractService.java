package com.zhuanyi.vjwealth.wallet.mobile.product.server;

import java.util.Map;


public interface IProductContractService {

	Map<String,Object> queryWarningAndProtectionContracts(String userId);

}

package com.rqb.ips.depository.platform.faced;

public interface IpsBidCardService {
    /**
     * 根据用户id查询用户的存管号码
     * @param userId
     * @return
     */
	String queryIsBidCard(String userId);

	
}

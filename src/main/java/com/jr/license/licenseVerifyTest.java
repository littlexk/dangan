package com.jr.license;



public class licenseVerifyTest {
	public static void main(String[] args){
		VerifyLicense vLicense = new VerifyLicense();
		//生成证书
		boolean bo =vLicense.verify();
		System.out.println(bo);
	}
}

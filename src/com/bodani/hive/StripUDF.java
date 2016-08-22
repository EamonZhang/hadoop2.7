//package com.bodani.hive;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.hadoop.hive.ql.exec.UDF;
//import org.apache.hadoop.io.Text;
///**
// * 截取字段首尾特殊符号
// * 用法
// * ADD JAR /path/hive-udf.jar
// * 别名
// * CREATE TEMPORARY FUNCTION strip AS 'com.bodani.hive.StripUDF'
// * 
// * @author Eamon
// *
// */
//public class StripUDF extends UDF{
//	//对象重用
//	 private Text result = new Text();
//	 
//	 public Text evaluate(Text str){
//		 if(str == null){
//			 return null;
//		 }
//		 result.set(StringUtils.strip(str.toString()));
//		 return result;
//	 }
//	 
//	 public Text evaluate(Text str,String stripChars){
//		 if(str == null){
//			 return null;
//		 }
//		 result.set(StringUtils.strip(str.toString(),stripChars));
//		 return result;
//	 }
//}

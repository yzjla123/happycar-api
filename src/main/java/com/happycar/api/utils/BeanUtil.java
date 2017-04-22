package com.happycar.api.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class BeanUtil extends org.springframework.beans.BeanUtils{
//	/**
//	 * 通过反射拷贝非空数据(把后面的复制到前面)
//	 * @param target
//	 * @param src
//	 */
//	public static void copyProperty(Object target,Object src){
//		PropertyDescriptor[] pds=PropertyUtils.getPropertyDescriptors(target);
//		for(PropertyDescriptor pd:pds){
//			try{
//				Object result=PropertyUtils.getProperty(src, pd.getName());
//				if(result!=null){
//					PropertyUtils.setProperty(target, pd.getName(),result);
//				}
//			}catch(Exception e){
//				new RuntimeException("复制对象"+src.getClass().getName()+"的属性出错了",e);
//			}
//		}
//	}
//	
//	/** 
//     * 功能 : 只复制source对象的非空属性到target对象上 
//     * */  
//    public static void copyNotNullProperties(Object source, Object target) throws BeansException {    
//      Assert.notNull(source, "Source must not be null");    
//      Assert.notNull(target, "Target must not be null");    
//      
//      Class<?> actualEditable = target.getClass();    
//      PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);    
//      for (PropertyDescriptor targetPd : targetPds) {    
//        if (targetPd.getWriteMethod() != null) {    
//          PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());    
//          if (sourcePd != null && sourcePd.getReadMethod() != null) {    
//            try {    
//              Method readMethod = sourcePd.getReadMethod();    
//              if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {    
//            	  readMethod.setAccessible(true);    
//              }    
//              Object value = readMethod.invoke(source);    
//              // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等    
//              if (value != null) {    
//                Method writeMethod = targetPd.getWriteMethod();    
//                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {    
//                	writeMethod.setAccessible(true);    
//                }    
//                writeMethod.invoke(target, value);    
//              }    
//            } catch (Throwable ex) {
//            	throw new FatalBeanException("Could not copy properties from source to target", ex);    
//            }    
//          }    
//        }    
//      }    
//    } 

}

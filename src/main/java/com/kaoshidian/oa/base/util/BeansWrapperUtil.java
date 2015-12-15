/**
 * 
 */
package com.kaoshidian.oa.base.util;

import com.kaoshidian.oa.base.exception.KsdBizSystemErrorException;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * 用于FreeMarker对象封装
 * 
 * @author <p>
 *         Innate Solitary 于 2011-12-12 下午5:02:51
 *         </p>
 * 
 */
public final class BeansWrapperUtil {
	/**
	 * 此方法传入的是一个枚举类型的class，返回的是一个此枚举中所有枚举常量的map,此map的key是枚举常量的name，可以通过调用name()方法获得，如果没有重写toString()，也可以用toString()方法
	 * 
	 * @param clazz
	 * @return
	 * @throws KsdBizSystemErrorException
	 */
	public static TemplateHashModel wrapEnum(Class<? extends Enum<?>> clazz) {
		if (!clazz.isEnum()) {
			throw new KsdBizSystemErrorException("类 " + clazz.getName() + " 不是枚举");
		}
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel enumModel = wrapper.getEnumModels();
		try {
			return (TemplateHashModel) enumModel.get(clazz.getName());
		} catch (TemplateModelException e) {
			throw new KsdBizSystemErrorException(e);
		}
	}

}

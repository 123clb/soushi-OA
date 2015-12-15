package com.kaoshidian.oa.base;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.lang.StringUtils;


public class KsdRowProcessor extends BasicRowProcessor {
	
	public KsdRowProcessor(){
		super();
	}
	
	static {
		// 修改BeansUtil日期转换
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value){  
		        if(value == null){  
		            return null;  
		        }else if(type == Timestamp.class){  
		            return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");  
		        }else if(type == Date.class){  
		            return convertToDate(type, value, "yyyy-MM-dd");  
		        }else if(type == String.class){  
		            return convertToString(type, value);  
		        }  
		  
		        throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());  
		    }  
		  
		    protected Object convertToDate(Class type, Object value, String pattern) {  
		        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
		        if(value instanceof String){  
		            try{  
		                if(StringUtils.isEmpty(value.toString())){  
		                    return null;  
		                }  
		                Date date = sdf.parse((String) value); 
		                if(type.equals(Timestamp.class)){  
		                    return new Timestamp(date.getTime());  
		                }  
		                return date;  
		            }catch(Exception pe){  
		                return null;  
		            }  
		        }else if(value instanceof Date){  
		            return value;  
		        }  
		          
		        throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());  
		    }  
		  
		    protected Object convertToString(Class type, Object value) {  
		        if(value instanceof Date){  
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		              
		            if (value instanceof Timestamp) {  
		                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		            }   
		              
		            try{  
		                return sdf.format(value);  
		            }catch(Exception e){  
		                throw new ConversionException("日期转换为字符串时出错！");  
		            }  
		        }else{  
		            return value.toString();  
		        }  
		    }  
		}, Date.class);
		
//		ConvertUtils.register(new Converter() {
//			
//			@Override
//			public Object convert(Class type, Object value) {
//				if(value == null) {
//					return null;
//				} else if(type == String.class) {
//					return this.convertToString(type, value);
//				} else {
//					return null;
//				}
//			}
//			
//			protected Object convertToString(Class type, Object value) {
//				if(value == null) {
//					return null;
//				}
//				return value.toString();
//			}
//			
//			protected Object convertToEnum(Class type, Object value) {
//				if(value == null) {
//					return null;
//				}
//				if(value instanceof String) {
//					if(StringUtils.isEmpty(value.toString())) {
//						return null;
//					} 
//				}
//				
//				return null;
//			}
//		}, EnumJobStatus.class);
	}
	
	/**
	 * @functionDescription :由于数据库中字段名可能带有下划线，去掉其中的下划线
	 * @author 王渊博
	 * @date 2009-7-20 下午03:55:59
	 */
	@Override
	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
		Map<String, Object> map =  super.toMap(rs);
		//父类里将其处理为key对大小写不敏感，这里借用父类的大小写不敏感map
		//map, key对大小写不敏感,先将所有key取出，去掉'_'之后再put进去
		List<String> keyList = new ArrayList<String>();
		for(Iterator<String> ite = map.keySet().iterator();ite.hasNext();){
			String colName = (String) ite.next();
			keyList.add(colName);
		}
		for(String key:keyList){
			Object value = map.get(key);
			if(value instanceof Date){
				Timestamp v1 = rs.getTimestamp(key);
				if(v1.compareTo((Date)value)!=0){
					value=v1;
				}
			}
			String newKey = KsdMappingRuleUtils.columnNameToPropertyName(key);
			//新key和旧key不一致，则删除旧key,降低数据量,ajax通过json调用时，数据量较大
			if(!newKey.equals(key)){
				map.remove(key);
			}
			
			
			map.put(newKey, value);
		}
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object toBean(ResultSet rs, Class type) throws SQLException {
		Map<String, Object> map = this.toMap(rs);
		Object bean = null;
		
		// 如果字段中有枚举行，需要把字段的值转换成枚举
		Field[] fileds = type.getDeclaredFields();
		for (Field field : fileds) {
			if(map.get(field.getName()) == null) {
				continue;
			}
	        if(field.getType().isEnum()) {
	        	try {
	                Object enumValue = field.getType().getMethod("valueOf", String.class).invoke(field.getType(), map.get(field.getName()).toString());
	                map.put(field.getName(), enumValue);
                } catch (IllegalAccessException e) {
	                throw new SQLException(e);
                } catch (IllegalArgumentException e) {
                	throw new SQLException(e);
                } catch (InvocationTargetException e) {
                	throw new SQLException(e);
                } catch (NoSuchMethodException e) {
                	throw new SQLException(e);
                } catch (SecurityException e) {
                	throw new SQLException(e);
                }
	        }
        }
		try {
			bean = type.newInstance();
			org.apache.commons.beanutils.BeanUtils.populate(bean, map);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List toBeanList(ResultSet rs, Class type) throws SQLException {
		// 修正当查询的Bean中有枚举时，可以转化枚举类型。
		List<Object> beanList = new ArrayList<Object>();
		while(rs.next()) {
			beanList.add(this.toBean(rs, type));
		}
		return beanList;
	}
	
	public Object[] toArray(ResultSet rs) throws SQLException {
		return super.toArray(rs);
	}
	
}

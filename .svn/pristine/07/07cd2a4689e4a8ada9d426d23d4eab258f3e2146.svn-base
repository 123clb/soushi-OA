/**
 * 
 */
package com.kaoshidian.oa.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

import net.htmlparser.jericho.Source;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 * @author <p>
 *         Innate Solitary 于 2012-6-7 上午9:20:43
 *         </p>
 * 
 */
public final class Util {
	private static final String[] CHAPTER_UNIT = new String[] {"章", "讲", "节"};
	private static final String FILE_NAME_START = "第";
	private static final Map<String, Integer> NUMBER_MAP = new HashMap<String, Integer>();
	private static final String[] CHAPTER_NAME_SEG = new String[] {" ", ":", "："};
	private static final String[] DATA_UNIT = new String[] {"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB", "BB"};
	
	static {
		NUMBER_MAP.put("一", 1);
		NUMBER_MAP.put("二", 2);
		NUMBER_MAP.put("三", 3);
		NUMBER_MAP.put("四", 4);
		NUMBER_MAP.put("五", 5);
		NUMBER_MAP.put("六", 6);
		NUMBER_MAP.put("七", 7);
		NUMBER_MAP.put("八", 8);
		NUMBER_MAP.put("九", 9);
		NUMBER_MAP.put("〇", 0);
		NUMBER_MAP.put("零", 0);
		NUMBER_MAP.put("十", 10);
		NUMBER_MAP.put("百", 100);
		NUMBER_MAP.put("千", 1000);
		NUMBER_MAP.put("万", 10000);
	}

	/**
	 * 用http请求一个URL
	 * 
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String requestHttp(String url) throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient http = new DefaultHttpClient();
		HttpGet get = new HttpGet();
		get.setURI(new URI(url));
		HttpResponse response = http.execute(get);
		int status = response.getStatusLine().getStatusCode();
		StringBuffer sb = new StringBuffer("");
		if (status == 200) {
			BufferedReader bReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

			String line = "";
			while ((line = bReader.readLine()) != null) {
				sb.append(line);
			}
			bReader.close();
		} else {
			JSONObject json = new JSONObject();
			json.element("statusCode", status);
			json.element("message", "请求URL[" + url + "] 失败");
			return json.toString();
		}
		return sb.toString();
	}

	public static String getFileNameByDateTime(String filename) {
		String datestr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String filenameWithoutExt = getFileNameWithoutExt(filename);
		String fileext = getFileExt(filename);
		String randomint = new DecimalFormat("####").format(new Random().nextInt(10000));
		return String.format("%s_%s_%s.%s", datestr, randomint, filenameWithoutExt, fileext);
	}
	
	public static String getFileNameWithoutExt(String filename){
		int index = filename.indexOf(".");
		//没有扩展名
		if(index==-1){
			return filename;
		}else{
			return filename.substring(0, index);
		}
		
	}
	
	public static String getFileExt(String filename) {
		int index = filename.lastIndexOf(".");
		if(index < 0) {
			return "";
		} else {
			return filename.substring(index + 1);
		}
	}
	
	/**
	 * 如果无法取到章节序号，则会返回-1
	 * @param fileName
	 * @return
	 */
	public static int getFileSeq(String fileName) {
		if(!fileName.startsWith(FILE_NAME_START)) {
			return 0;
		}
		int start = 1;
		int end = -1;
		for (String unit : CHAPTER_UNIT) {
	        int temp = fileName.indexOf(unit);
	        if(end == -1 && temp > 0) {
	        	end = temp;
	        } else if(temp < end && temp > 0) {
	        	end = temp;
	        }
        }
		
		if(end == -1) {
			return -1;
		}

		
		String fileSeq = fileName.substring(start, end).trim();
		
		if(StringUtils.isEmpty(fileSeq)) {
			// 如何是空字符串，返回-1
			return -1;
		}
		
		if(StringUtils.isNumeric(fileSeq)) {
			// 如果是数字则直接转换成数字返回
			return Integer.parseInt(fileSeq);
		}
		
		// 如果不是数字，则人为他是大写汉字
		char[] fileSeqChars = fileSeq.toCharArray();
		int seq_temp = 0;
		int num_temp = 0;
		String op = null;
		for (char c : fileSeqChars) {
	        Integer num = NUMBER_MAP.get(Character.toString(c));
	        if(num == null) {
	        	seq_temp = 0;
	        	break;
	        }
	        if(op == null) {
	        	num_temp = num;
	        } else if("+".equals(op)) {
	        	num_temp += num;
	        	op = null;
	        } else if("*".equals(op)) {
	        	num_temp *= num;
	        	op = null;
	        	seq_temp += num_temp;
	        	num_temp = 0;
	        }
	        
	        
	        
	        if(num.intValue() == NUMBER_MAP.get("十").intValue() || num.intValue() == NUMBER_MAP.get("百").intValue() || num.intValue() == NUMBER_MAP.get("千").intValue() || num.intValue() == NUMBER_MAP.get("万").intValue()) {
	        	op = "+";
	        } else {
	        	op = "*";
	        }
	        num = 0;
        }
		
		// 如果解析出来的是0，则认为不合法，返回-1
		return seq_temp + num_temp == 0 ? -1 : seq_temp + num_temp;
	}
	
	public static String parseFileName(String name) {
		if(!name.startsWith(FILE_NAME_START)) {
			return name;
		}
		
		int seg = -1;

		for (String segsym : CHAPTER_NAME_SEG) {
			seg = name.indexOf(segsym);
	        if(seg >= 0) {
	        	break; 
	        }
        }
		
		if(seg < 0) {
			return name;
		}
		
		String str = name.substring(0, seg).trim();
		if(Arrays.binarySearch(CHAPTER_UNIT, str.substring(str.length() - 1)) < 0) {
			return name;
		}
		String alias = name.substring(seg + 1).trim();
		return alias.length() == 0 ? name : alias;
	}
	
	public static String randomColor() {
		String r = Integer.toHexString(RandomUtils.nextInt(255));
		if(r.length() == 1) {
			r = "0" + r;
		}
		String g = Integer.toHexString(RandomUtils.nextInt(255));
		if(g.length() == 1) {
			g = "0" + g;
		}
		String b = Integer.toHexString(RandomUtils.nextInt(255));
		if(b.length() == 1) {
			b = "0" + b;
		}
        return "#" + r + g + b;
	}
	
	/**
	 * 读取文件最后N行
	 * @param filePath 文件路径
	 * @param lineCount 读取的行数
	 * @return 
	 * @throws IOException
	 */
	public static String readFileLastLines(String filePath, int lineCount) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(filePath, "r");
		long seek = raf.length() - 1;
		long curLineCount = 1;
		StringBuffer sb = new StringBuffer();
		while (true) {
			raf.seek(seek);
			
			
			if(seek == 0) {
				raf.seek(0);
				String line = raf.readLine();
				sb.insert(0, new String(line.getBytes("ISO-8859-1"), "UTF-8") + "\r\n");
				break;
			}
			
			byte c = raf.readByte();
	        if(c == '\n') {
	        	curLineCount++;
	        	String line = raf.readLine();
	        	if(line == null) {
	        		seek--;
	        		continue;
	        	}
	        	sb.insert(0, new String(line.getBytes("ISO-8859-1"), "UTF-8") + "\r\n");
	        }

	        
	        
	        
	        if(curLineCount > lineCount) {
	        	break;
	        }
	        
	        seek--;
        }
		raf.close();
		return sb.toString();
	}
	
	
	public static String getMRMSChange() throws IOException {
		StringBuffer sb = new StringBuffer("");
        try {
	        URLConnection conn = new URL("http://javaserver:8080/hudson/job/%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83-%E9%83%A8%E7%BD%B2mrms%E5%88%B0230%28MRMS%29/changes").openConnection();
	        conn.setConnectTimeout(3000);
	        conn.setReadTimeout(3000);
	        InputStream is = conn.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
	        sb = new StringBuffer();
	        for(String line = br.readLine(); line != null; line = br.readLine()) {
	        	sb.append(line);
	        }
	        br.close();
	        is.close();
        } catch (MalformedURLException e) {
        	return e.getMessage();
        } catch (IOException e) {
        	return e.getMessage();
        }
		
        Source source = new Source(sb.toString());
        String change = source.getElementById("main-panel").getRenderer().toString();
        StringBuffer changesb = new StringBuffer();
        LineNumberReader lnr = new LineNumberReader(new StringReader(change));
        for (String line = lnr.readLine(); line != null; line = lnr.readLine()) {
	        if(StringUtils.isEmpty(line)) {
	        	changesb.append(line);
	        }
	        
	        if(line.startsWith("#")) {
	        	changesb.append(line);
	        }
	        
	        if(line.trim().matches("^\\d+\\.")) {
	        	
	        }
        }
        return change;
	}
	
	/**
	 * 返回-1标识传入参数有误，大于0标识对应的字节数
	 * @param datasize
	 * @return
	 */
	public static long toBytesCount(String datasize) {
		
		if(StringUtils.isEmpty(datasize)) {
			return -1;
		}
		
		long byteCount = 0;
		String dataUnit = "B";
		if(datasize.trim().contains(" ")) {
			String[] datas = datasize.split(" ");
			if(!StringUtils.isNumeric(datas[0])) {
				return -1;
			}
			byteCount = Long.parseLong(datas[0]);
			dataUnit = datas[1];
		}
		
		int index = ArrayUtils.indexOf(DATA_UNIT, dataUnit);
		
		if(index < 0) {
			return -1;
		}
		
		return byteCount * (long)Math.pow(1024, index);
	}
	
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，一般第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			ip = request.getRemoteAddr();
			return ip.length() > 15 ? "" :ip;
		}
	}
	
	public static void main(String[] args) throws URISyntaxException {
    }
}

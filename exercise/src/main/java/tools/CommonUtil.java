package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


/**
 * 常用工具类通用类
 * 
 * @version 2018年10月22日
 * @see CommonUtil
 * @since
 */
public class CommonUtil {

	private static final String NONE_PRESCRIPTION = "0";

	/**
	 *
	 * isEmpty:判断对象是否为空
	 * @author yudi
	 * @version 2018年5月16日
	 * @param o 对象
	 * @return boolean 是否为空
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if ((o instanceof String)) {
			if (((String)o).trim().length() == 0) {
				return true;
			}
		}
		else if ((o instanceof Collection)) {
			if (((Collection)o).isEmpty()) {
				return true;
			}
		}
		else if (o.getClass().isArray()) {
			if (((Object[])o).length == 0) {
				return true;
			}
		}
		else if (((o instanceof Map)) && (((Map)o).isEmpty())) {
			return true;
		}

		return false;
	}

	/**
	 *
	 * 计算时效
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 时效
	 */
	public String calculateAging(Date startTime, Date endTime)
		throws ParseException {
		if (isEmpty(endTime) || isEmpty(startTime)) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		startTime = sdf.parse(sdf.format(startTime));
		endTime = sdf.parse(sdf.format(endTime));
		Long millisecond = startTime.getTime() - endTime.getTime();
		if (NONE_PRESCRIPTION.equals(millisecond.toString())) {
			return "0";
		}
		int d = 24 * 60 * 60 * 1000;
		int h = 60 * 60 * 1000;
		int m = 60 * 1000;
		Long day = millisecond / d;
		Long hour = (millisecond % d) / h;
		Long minute = ((millisecond % d) % h) / m;
		return day + "天" + hour + "小时" + minute + "分钟";
	}
}

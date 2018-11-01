/**
 * 文件名：DateConversionUtil.java 版权：Copyright by Air 描述： 修改人：zhangbc 修改时间：2018年9月10日 跟踪单号：
 * 修改单号： 修改内容：
 */
package tools;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 〈时间转换工具类〉
 * 
 * @author zhangbc
 * @version 2018年9月10日
 * @see DateConversionUtil
 * @since
 */
public class DateConversionUtil {

    private static final int TODAY = 0;

    private static final int YESTERDAY = 1;

    private static boolean IfToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());

        if (calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)) {
            int diffDay = nowCalendar.get(Calendar.DAY_OF_YEAR)
                          - calendar.get(Calendar.DAY_OF_YEAR);

            if (diffDay == TODAY) {
                return true;
            }
        }
        return false;

    }
    
    private static boolean IfYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());

        if (calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)) {
            int diffDay = nowCalendar.get(Calendar.DAY_OF_YEAR)
                          - calendar.get(Calendar.DAY_OF_YEAR);

            if (diffDay == YESTERDAY) {
                return true;
            }
        }
        return false;

    }
    
    private static boolean IfThisYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());

        if (calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }
    
    /**
     * ConversionDateForMsgList:( 
     * 〈转换为对应时间格式，对应格式如下
                        当天：今天 HH:mm
                        昨天：昨天 HH:mm
                        本年：月-日 HH:mm
                        往年：年-月-日                                                         
                       〉
                        适用于消息模块列表                       
                       )
     * @author zhangbc  
     * @version 2018年9月12日  
     * @param date
     * @return
     * @return String
     * @throws ParseException 
     */
    public static String ConversionDateForMsgList(Date date) throws ParseException {
        if (date==null ) {
            return "";
        }
        if (IfToday(date)) {
            SimpleDateFormat todayFormat = new SimpleDateFormat("今天 HH:mm");
            return todayFormat.format(date);
        }else if
         (IfYesterday(date)) {
            SimpleDateFormat todayFormat = new SimpleDateFormat("昨天 HH:mm");
            return todayFormat.format(date);
        }else if
         (IfThisYear(date)) {
            SimpleDateFormat todayFormat = new SimpleDateFormat("MM-dd HH:mm");
            return todayFormat.format(date);
        }else {
            SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd");
            return todayFormat.format(date);
        }
        
    }
    
    /**
     * ConversionDateForList:(          
                        本年：月-日 HH:mm
                        往年：年-月-日        )
                        适用于普遍列表
     * @author zhangbc  
     * @version 2018年9月12日  
     * @param date
     * @return
     * @return String
     * @throws ParseException 
     */
    public static String ConversionDateForList(Date date) throws ParseException {
        if (date==null ) {
            return "";
        }
        if(IfThisYear(date)) {
            SimpleDateFormat todayFormat = new SimpleDateFormat("MM-dd HH:mm");
            return todayFormat.format(date);
        }else {
            SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd");
            return todayFormat.format(date);
        }
    }
    
    /**
     * ConversionDateForDetail:(          
                        年-月-日 HH:mm )
                        适用于详情页面
     * @author zhangbc  
     * @version 2018年9月12日  
     * @param date
     * @return
     * @return String
     * @throws ParseException 
     */
    public static String ConversionDateForDetail(Date date) throws ParseException {
        if (date==null ) {
            return "";
        }
        SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return todayFormat.format(date);
    }
    
/*    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = format.parse("0000-00-00 00:00:00");
        //Date parse = format.parse("2018-09-12 16:11:00");
        String conversionDate = DateConversionUtil.ConversionDateForDetail(parse);
        System.out.println(conversionDate);
    }*/
}

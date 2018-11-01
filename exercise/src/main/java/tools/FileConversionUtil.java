package tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * 图片上传下载通用类
 *
 * @version 2018年5月9日
 * @see FileConversionUtil
 * @since
 */
public class FileConversionUtil {
    private static final String url = "http://192.168.1.40:9011/f/file/attach?id=";

    public static String getUrl() {
        return url;
    }

    /**
     * [{"fileId":"xxxx","isDisplayToList":"1"},{"fileId":"xxxx","isDisplayToList":"0"},{"fileId":"xxxx","isDisplayToList":"0"}]
     * jsonToList:(数据库json转List). <br/>
     *
     * @param jsonString
     * @return String
     * @version 2018年5月10日
     */
    public static List<String> jsonToList(String jsonString) {
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        List<String> picList = new ArrayList<String>();
        try {
            for (int i = 0; i < jsonArray.size(); i++ ) {
                String picString = url + jsonArray.getJSONObject(i).getString("fileId");
                picList.add(picString);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return picList;
    }

    /**
     * jsonToString:(数据库json转String(只要主图)). <br/>
     *
     * @param jsonString
     * @return String
     * @version 2018年5月10日
     */
    public static String jsonToString(String jsonString) {
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        String picString = "";
        try {
            for (int i = 0; i < jsonArray.size(); i++ ) {
                if ("1".equals(jsonArray.getJSONObject(i).getString("isDisplayToList"))) {
                    picString = url + jsonArray.getJSONObject(i).getString("fileId");
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return picString;
    }

    /**
     * @author: lizhuoran
     * json转带有首页图片的bean
     * @param url 文件URL
     * @param json json数据(String)
     * @param clazz 自定义bean类型
     * @throws Exception
     * @return null传入数据为空 tBean返回传入bean的类型对象
     * @version 2018年10月27日
     */
    public static <T extends FileBean> T jsonToLogo(String url, String json, Class<T> clazz) {
        if ("".equals(url)) {
            return null;
        }
        if ("".equals(json)) {
            return null;
        }
        T tBean = null;
        try {
            List<T> tList = jsonToBean(url, json, clazz);
            assert tList != null;
            for (T t : tList) {
                if (t.getIsDisplayToList().equals("isDisplayToList")) {
                    tBean = t;
                    t.setFileResId(t.getFileId());
                    t.setFileId(url + t.getFileId());
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tBean;
    }

    /**
     * stringToJson:(APP页面String 转 json). <br/>
     * 规定只上传一张图片，并且该图片是主图
     *
     * @param picId
     * @return String
     * @version 2018年5月10日
     */
    public static String stringToJson(String picId) {
        String jsonString = "[{'fileId':'" + picId + "','isDisplayToList':'1'}]";
        return jsonString;
    }

    /**
     * stringListToJson:(APP页面上传多张图片，暂时默认第一张为主图，其余为附图). <br/>
     * 参数接收图片id集合
     *
     * @param picList
     * @return String
     * @version 2018年5月25日
     */
    public static String stringListToJson(List<String> picList) {
        StringBuffer sb = new StringBuffer();
        if (picList != null && picList.size() > 0) {
            for (int i = 0; i < picList.size(); i++ ) {
                if (i == 0) {
                    sb.append("[{'fileId':'" + picList.get(i) + "','isDisplayToList':'1'}").append(
                        (i == (picList.size() - 1)) ? "]" : ",");
                }
                else {
                    sb.append("{'fileId':'" + picList.get(i) + "','isDisplayToList':'0'}").append(
                        (i == (picList.size() - 1)) ? "]" : ",");
                }
            }
        }
        return sb.toString();
    }

    /**
     * jsonArrayToList:(Json数组String 转成list). <br/>
     *
     * @param jsonArr Json 数组
     * @param clazz   返回集合类型
     * @return List<T>
     * @author ZhangZhiBin
     * @version 2018年9月6日
     */

    /*
     * public static <T extends FileBean> List<T> jsonArrayToList(String jsonArr, Class<T> clazz) { if
     * ("".equals(jsonArr)) { return null; } List<T> list = JSON.parseArray(jsonArr, clazz); for (T t :
     * list) { // 文件id 用于文件编辑后向后台回传数据 t.setFileResId(t.getFileId()); // 文件URL t.setFileId(url +
     * t.getFileId()); } return list; }
     */

    /**
     * @author: lizhuoran
     * json转为beanList
     * @param url 文件URL
     * @param json json数据(String)
     * @param clazz 自定义bean类型
     * @throws Exception
     * @return null传入数据为空 tList返回的传入的bean类型对象的List集合
     * @version 2018年10月25日
     */
    public static <T extends FileBean> List<T> jsonToBean(String url, String json, Class<T> clazz) {
        if ("".equals(url)) {
            return null;
        }
        if ("".equals(json)) {
            return null;
        }
        List<T> tList = JSON.parseArray(json, clazz);
        try {
            for (T t : tList) {
                t.setFileResId(t.getFileId());
                t.setFileId(url + t.getFileId());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tList;
    }

    /**
     * @author: lizhuoran
     * bean转json
     * @param obj bean对象
     * @return JSON.toJSONString(obj) json字符串
     * @version 2018年10月24日
     */
    public static String beanToJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

}

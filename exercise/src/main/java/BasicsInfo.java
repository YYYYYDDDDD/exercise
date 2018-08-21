import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;


public class BasicsInfo {
    private static final String propertiesFileName = "info";
    //卸货位置
    private String basics_site;
    //标准工时
    private String standard_working_hours;
    //装箱比例
    private String packing_proportion;
    //装箱比例 null
    private String packing_proportion_null;
    //单品管理 是
    private String sku_control_yes;
    //单品管理 否
    private String sku_control_no;
    //上架位置
    private String shelves_site;
    //凭证类型1
    private String document_type1;
    //凭证类型2
    private String document_type2;
    //应卸货位置
    private String should_basics_site;
    //清点方式 计数
    private String check_type_count;
    //清点方式 计量
    private String check_type_metering;
    //应上架位置
    private String should_shelves_site;
    //处理人id
    private String processing_person_id;
    //处理人name
    private String processing_person_name;

    public String getBasics_site() {
        return setInfo("basics_site");
    }

    public String getStandard_working_hours() {
        return setInfo("standard_working_hours");
    }

    public String getPacking_proportion() {
        return setInfo("packing_proportion");
    }

    public String getPacking_proportion_null() {
        return setInfo("packing_proportion_null");
    }

    public String getSku_control_yes() {
        return setInfo("sku_control_yes");
    }

    public String getSku_control_no() {
        return setInfo("sku_control_no");
    }

    public String getShelves_site() {
        return setInfo("shelves_site");
    }

    public String getDocument_type1() {
        return setInfo("document_type1");
    }

    public String getDocument_type2() {
        return setInfo("document_type2");
    }

    public String getShould_basics_site() {
        return setInfo("should_basics_site");
    }

    public String getCheck_type_count() {
        return setInfo("check_type_count");
    }

    public String getCheck_type_metering() {
        return setInfo("check_type_metering");
    }

    public String getShould_shelves_site() {
        return setInfo("should_shelves_site");
    }

    public String getProcessing_person_id() {
        return setInfo("processing_person_id");
    }

    public String getProcessing_person_name() {
        return setInfo("processing_person_name");
    }

    @Override public String toString() {
        return "BasicsInfo{" + "basics_site='" + basics_site + '\'' + ", standard_working_hours='"
               + standard_working_hours + '\'' + ", packing_proportion='" + packing_proportion + '\''
               + ", packing_proportion_null='" + packing_proportion_null + '\'' + ", sku_control_yes='"
               + sku_control_yes + '\'' + ", sku_control_no='" + sku_control_no + '\'' + ", shelves_site='"
               + shelves_site + '\'' + ", document_type1='" + document_type1 + '\'' + ", document_type2='"
               + document_type2 + '\'' + ", should_basics_site='" + should_basics_site + '\'' + ", check_type_count='"
               + check_type_count + '\'' + ", check_type_metering='" + check_type_metering + '\''
               + ", should_shelves_site='" + should_shelves_site + '\'' + ", processing_person_id='"
               + processing_person_id + '\'' + ", processing_person_name='" + processing_person_name + '\'' + '}';
    }

    public String setInfo(String field){
        ResourceBundle resource = ResourceBundle.getBundle(propertiesFileName);
        String key = "";
        try {
            key = new String(resource.getString(field).getBytes("ISO-8859-1"),"GBK");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
//            throw new RuntimeException();
        }
        return key;
    }
}

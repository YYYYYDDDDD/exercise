public class DemoJsonBean {
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

    @Override public String toString() {
        return "DemoJsonBean{" + "basics_site='" + basics_site + '\'' + ", standard_working_hours='"
               + standard_working_hours + '\'' + ", packing_proportion='" + packing_proportion + '\''
               + ", packing_proportion_null='" + packing_proportion_null + '\'' + ", sku_control_yes='"
               + sku_control_yes + '\'' + ", sku_control_no='" + sku_control_no + '\'' + ", shelves_site='"
               + shelves_site + '\'' + ", document_type1='" + document_type1 + '\'' + ", document_type2='"
               + document_type2 + '\'' + ", should_basics_site='" + should_basics_site + '\'' + ", check_type_count='"
               + check_type_count + '\'' + ", check_type_metering='" + check_type_metering + '\''
               + ", should_shelves_site='" + should_shelves_site + '\'' + ", processing_person_id='"
               + processing_person_id + '\'' + ", processing_person_name='" + processing_person_name + '\'' + '}';
    }

    public String getBasics_site() {
        return basics_site;
    }

    public void setBasics_site(String basics_site) {
        this.basics_site = basics_site;
    }

    public String getStandard_working_hours() {
        return standard_working_hours;
    }

    public void setStandard_working_hours(String standard_working_hours) {
        this.standard_working_hours = standard_working_hours;
    }

    public String getPacking_proportion() {
        return packing_proportion;
    }

    public void setPacking_proportion(String packing_proportion) {
        this.packing_proportion = packing_proportion;
    }

    public String getPacking_proportion_null() {
        return packing_proportion_null;
    }

    public void setPacking_proportion_null(String packing_proportion_null) {
        this.packing_proportion_null = packing_proportion_null;
    }

    public String getSku_control_yes() {
        return sku_control_yes;
    }

    public void setSku_control_yes(String sku_control_yes) {
        this.sku_control_yes = sku_control_yes;
    }

    public String getSku_control_no() {
        return sku_control_no;
    }

    public void setSku_control_no(String sku_control_no) {
        this.sku_control_no = sku_control_no;
    }

    public String getShelves_site() {
        return shelves_site;
    }

    public void setShelves_site(String shelves_site) {
        this.shelves_site = shelves_site;
    }

    public String getDocument_type1() {
        return document_type1;
    }

    public void setDocument_type1(String document_type1) {
        this.document_type1 = document_type1;
    }

    public String getDocument_type2() {
        return document_type2;
    }

    public void setDocument_type2(String document_type2) {
        this.document_type2 = document_type2;
    }

    public String getShould_basics_site() {
        return should_basics_site;
    }

    public void setShould_basics_site(String should_basics_site) {
        this.should_basics_site = should_basics_site;
    }

    public String getCheck_type_count() {
        return check_type_count;
    }

    public void setCheck_type_count(String check_type_count) {
        this.check_type_count = check_type_count;
    }

    public String getCheck_type_metering() {
        return check_type_metering;
    }

    public void setCheck_type_metering(String check_type_metering) {
        this.check_type_metering = check_type_metering;
    }

    public String getShould_shelves_site() {
        return should_shelves_site;
    }

    public void setShould_shelves_site(String should_shelves_site) {
        this.should_shelves_site = should_shelves_site;
    }

    public String getProcessing_person_id() {
        return processing_person_id;
    }

    public void setProcessing_person_id(String processing_person_id) {
        this.processing_person_id = processing_person_id;
    }

    public String getProcessing_person_name() {
        return processing_person_name;
    }

    public void setProcessing_person_name(String processing_person_name) {
        this.processing_person_name = processing_person_name;
    }
}

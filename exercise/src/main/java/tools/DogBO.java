package tools;

import java.util.List;
import java.util.Map;


/**
 * Description:
 * ModelName:workspace
 *
 * @author: DAKANG
 * Create at:  2018年10月29日 16时35分
 * Company: 沈阳艾尔时代科技发展有限公司
 * Copyright: (c)2018 AIR Times Inc. All rights reserved.
 * @version: 1.0
 */
public class DogBO {
    private Integer id;

    private List<EquipBO> equipS;
    private EquipBO equip;
    private List<Integer> list;

    private Map<String, EquipBO> equipBOMap;

    public DogBO() {
        super();
    }

    public DogBO(Integer id) {
        super();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EquipBO> getEquipS() {
        return equipS;
    }

    public void setEquipS(List<EquipBO> equipS) {
        this.equipS = equipS;
    }

    public EquipBO getEquip() {
        return equip;
    }

    public void setEquip(EquipBO equip) {
        this.equip = equip;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public Map<String, EquipBO> getEquipBOMap() {
        return equipBOMap;
    }

    public void setEquipBOMap(Map<String, EquipBO> equipBOMap) {
        this.equipBOMap = equipBOMap;
    }
}

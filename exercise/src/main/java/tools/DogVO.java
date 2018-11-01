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
public class DogVO {
    private Integer id;

    private List<EquipVO> equipS;
    private EquipVO equip;
    private List<Integer> list;

    private Map<String, EquipVO> equipVOMap;

    public DogVO() {
        super();
    }

    public DogVO(Integer id) {
        super();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EquipVO> getEquipS() {
        return equipS;
    }

    public void setEquipS(List<EquipVO> equipS) {
        this.equipS = equipS;
    }

    public EquipVO getEquip() {
        return equip;
    }

    public void setEquip(EquipVO equip) {
        this.equip = equip;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public Map<String, EquipVO> getEquipVOMap() {
        return equipVOMap;
    }

    public void setEquipVOMap(Map<String, EquipVO> equipVOMap) {
        this.equipVOMap = equipVOMap;
    }
}

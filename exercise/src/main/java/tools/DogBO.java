package tools;

import java.util.Date;
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
public class DogBO extends AnimalBO {
    private Integer id;
    private EquipBO equip;
    private List<EquipBO> equipBOS;

    private List<Integer> list;
    private Date addtime;

    private Map<String, EquipBO> equipBOMap;
    private Map<String, List<EquipBO>> equipBOListMap;
    private Map<String, List<Map<String,EquipBO>>> equipBOListMapMap;

	public Map<String, List<EquipBO>> getEquipBOListMap() {
		return equipBOListMap;
	}

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public void setEquipBOListMap(Map<String, List<EquipBO>> equipBOListMap) {
		this.equipBOListMap = equipBOListMap;
	}

	public EquipBO getEquip() {
        return equip;
    }

    public void setEquip(EquipBO equip) {
        this.equip = equip;
    }

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

    public List<EquipBO> getEquipBOS() {
        return equipBOS;
    }

    public void setEquipBOS(List<EquipBO> equipBOS) {
        this.equipBOS = equipBOS;
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

	public Map<String, List<Map<String, EquipBO>>> getEquipBOListMapMap() {
		return equipBOListMapMap;
	}

	public void setEquipBOListMapMap(Map<String, List<Map<String, EquipBO>>> equipBOListMapMap) {
		this.equipBOListMapMap = equipBOListMapMap;
	}

	@Override
	public String toString() {
		return "DogBO [id=" + id + ", equip=" + equip + ", equipBOS=" + equipBOS + ", list=" + list + ", equipBOMap="
				+ equipBOMap + ", equipBOListMap=" + equipBOListMap + ", equipBOListMapMap=" + equipBOListMapMap + "]";
	}

    
}

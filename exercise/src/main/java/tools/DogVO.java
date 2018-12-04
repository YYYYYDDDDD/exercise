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
public class DogVO extends AnimalVO {
    private Integer id;

    private EquipVO equip;

    private List<EquipVO> equipVOS;

    private List<Integer> list;

    private Map<String, EquipVO> equipVOMap;
    
    private Map<String, List<EquipVO>> equipVOListMap;

    private Map<String, List<Map<String,EquipVO>>> equipVOListMapMap;
    private Date addtime;

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public EquipVO getEquip() {
        return equip;
    }

    public void setEquip(EquipVO equip) {
        this.equip = equip;
    }

    public DogVO() {
        super();
    }

    public DogVO(Integer id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EquipVO> getEquipVOS() {
        return equipVOS;
    }

    public void setEquipVOS(List<EquipVO> equipVOS) {
        this.equipVOS = equipVOS;
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

	public Map<String, List<EquipVO>> getEquipVOListMap() {
		return equipVOListMap;
	}

	public void setEquipVOListMap(Map<String, List<EquipVO>> equipVOListMap) {
		this.equipVOListMap = equipVOListMap;
	}


	@Override
	public String toString() {
		return "DogVO [id=" + id + ", equip=" + equip + ", equipVOS=" + equipVOS + ", list=" + list + ", equipVOMap="
				+ equipVOMap + ", equipVOListMap=" + equipVOListMap + ", equipVOListMapMap=" + equipVOListMapMap + "]";
	}

	public Map<String, List<Map<String, EquipVO>>> getEquipVOListMapMap() {
		return equipVOListMapMap;
	}

	public void setEquipVOListMapMap(Map<String, List<Map<String, EquipVO>>> equipVOListMapMap) {
		this.equipVOListMapMap = equipVOListMapMap;
	}

}

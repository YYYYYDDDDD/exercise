package tools;

/**
 * Description: ModelName:workspace
 *
 * @author: DAKANG Create at: 2018年10月29日 16时37分 Company: 沈阳艾尔时代科技发展有限公司
 *          Copyright: (c)2018 AIR Times Inc. All rights reserved.
 * @version: 1.0
 */
public class EquipBO {
	private int id;
	private String name;

	public EquipBO() {

	}

	public EquipBO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EquipBO [id=" + id + ", name=" + name + "]";
	}

}

package tools;

/**
 * Description:
 * ModelName:workspace
 *
 * @author: DAKANG
 * Create at:  2018年11月06日 17时20分
 * Company: 沈阳艾尔时代科技发展有限公司
 * Copyright: (c)2018 AIR Times Inc. All rights reserved.
 * @version: 1.0
 */
public class AnimalVO {
    private int age;

    private String sex;
    @Override
   	public String toString() {
   		return "AnimalVO [age=" + age + ", sex=" + sex + "]";
   	}
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

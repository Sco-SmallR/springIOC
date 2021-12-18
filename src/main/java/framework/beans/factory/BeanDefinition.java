package framework.beans.factory;

/**
 * @Author: SmallRong
 * @Description:用来封装bean标签数据
 * id属性
 * class属性
 * property子标签的数据
 * @Date: Created in 14:32 2021/12/7
 * @Modified By;
 */
public class BeanDefinition {
    private String id;
    private String className;
    private MutablePropertyValues propertyValues;

    public BeanDefinition() {
        propertyValues = new MutablePropertyValues();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPropertyValues(MutablePropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public MutablePropertyValues getPropertyValues() {
        return propertyValues;
    }
}
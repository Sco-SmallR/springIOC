package framework.beans.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: SmallRong
 * @Description:存储管理多个PropertyValue对象
 * @Date: Created in 13:50 2021/12/7
 * @Modified By;
 */
public class MutablePropertyValues implements Iterable<PropertyValue> {

    //定义list集合对象，用来存储PropertyValue对象
    private final List<PropertyValue> propertyValueList;

    public MutablePropertyValues() {
        //final属性，需要在类构造之前对其赋值
        this.propertyValueList = new ArrayList<PropertyValue>();
    }

    public MutablePropertyValues(List<PropertyValue> propertyValueList) {
        //final属性，需要在类构造之前对其赋值
        if (propertyValueList == null) {
            this.propertyValueList = new ArrayList<PropertyValue>();
        } else {
            this.propertyValueList = propertyValueList;
        }
    }

    //获取所有的propertyValue对象，返回以数组的形式
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    //根据name属性值获取propertyValue对象
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }


    //添加propertyValue对象
    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        //判断集合中存储的对象时是否与传递的重复了，如果是，进行覆盖
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPv = this.propertyValueList.get(i);
            if (currentPv.getName().equals(pv.getName())) {
                this.propertyValueList.set(i, new
                        PropertyValue(pv.getName(), pv.getRef(), pv.getValue()));
                return this;//目的是实现链式编程
            }
        }
        this.propertyValueList.add(pv);
        return this;//目的是实现链式编程
    }

    //判断是否有指定name属性值的对象
    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    //获取迭代器对象
    @Override
    public Iterator<PropertyValue> iterator() {
        return propertyValueList.iterator();
    }

    //判断集合是否为空
    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }
}

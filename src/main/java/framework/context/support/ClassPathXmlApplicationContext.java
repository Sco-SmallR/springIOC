package framework.context.support;

import framework.beans.factory.BeanDefinition;
import framework.beans.factory.MutablePropertyValues;
import framework.beans.factory.PropertyValue;
import framework.beans.factory.support.BeanDefinitionRegistry;
import framework.beans.factory.xml.XmlBeanDefinitionReader;
import framework.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * @Author: SmallRong
 * @Description:IOC容器具体的子实现类 用于加载类路径下的xml格式的配置文件
 * @Date: Created in 14:47 2021/12/8
 * @Modified By;
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configLocation) {
        this.configLocation = configLocation;
        //构建解析器对象
        beanDefinitionReader = new XmlBeanDefinitionReader();//对父类中声明的解析器进行赋值
        try {
            this.refresh();//父类中的方法
        } catch (Exception e) {
        }
    }

    //根据bean对象的名称获取bean对象
    @Override
    public Object getBean(String name) throws Exception {
        //判断容器中是否有指定名称的bean对象，有就直接返回，如果不包含就创建
        Object obj = singletonObjects.get(name);
        if (obj != null) {
            return obj;
        }

        //从注册表获取BeanDefinition对象
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        BeanDefinition beanDefinition = registry.getBeanDefinition(name);
        //获取bean信息中的className
        String className = beanDefinition.getClassName();
        //通过反射创建对象
        Class<?> clazz = Class.forName(className);//初始化但未实例化
        Object beanObj = clazz.newInstance();//实例化

        //进行依赖注入操作
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            //获取name属性值
            String propertyName = propertyValue.getName();
            //获取value属性
            String value = propertyValue.getValue();
            //获取ref属性
            String ref = propertyValue.getRef();

            if (ref != null && !"".equals(ref)) {
                //获取依赖的bean对象
                Object bean = getBean(ref);//递归调用getBean方法
                //拼接方法名
                String methodByFieldName = StringUtils.getSetterMethodByFieldName(propertyName);
                //获取所有的方法对象
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (methodByFieldName.equals(method.getName())) {
                        //执行setter方法
                        method.invoke(beanObj, bean);
                    }
                }
            }
            if (value != null && !"".equals(value)) {
                //拼接方法名
                String methodByFieldName = StringUtils.getSetterMethodByFieldName(propertyName);
                //获取method对象
                Method method = clazz.getMethod(methodByFieldName, String.class);
                method.invoke(beanObj, value);
            }
        }
        //在返回beanObj对象之前，将该对象存储到map容器中
        singletonObjects.put(name, beanObj);
        return beanObj;
    }

    @Override
    public <T> T getBean(String name, Class<? extends T> clazz) throws Exception {
        Object bean = getBean(name);
        if (bean == null){
            return null;
        }
           return clazz.cast(bean);//类型强转后返回
    }
}

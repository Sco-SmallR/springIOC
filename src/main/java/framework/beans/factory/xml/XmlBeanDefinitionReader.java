package framework.beans.factory.xml;

import framework.beans.factory.BeanDefinition;
import framework.beans.factory.MutablePropertyValues;
import framework.beans.factory.PropertyValue;
import framework.beans.factory.support.BeanDefinitionReader;
import framework.beans.factory.support.BeanDefinitionRegistry;
import framework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @Author: SmallRong
 * @Description:针对xml配置文件进行解析的类
 * @Date: Created in 16:14 2021/12/7
 * @Modified By;
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {
    //声明注册表对象
    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader() {
        registry = new SimpleBeanDefinitionRegistry();
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public void loadBeanDefinitions(String configLocation) throws Exception {
        //使用dom4j进行xml配置文件的解析//需要引入jar包
        SAXReader reader = new SAXReader();
        InputStream inputStream = XmlBeanDefinitionReader.class.getClassLoader().getResourceAsStream(configLocation);
        Document document = reader.read(inputStream);

        //根据Document对象获取根标签对象（beans）
        Element rootElement = document.getRootElement();
        //获取根标签下的bean标签对象
        List<Element> beanElements = rootElement.elements("bean");
        //遍历集合
        for (Element beanElement : beanElements) {
            //获取id属性
            String id = beanElement.attributeValue("id");
            //获取class属性
            String className = beanElement.attributeValue("class");
            //将id属性和class属性封装到BeanDefinition对象中
            //1.创建BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(id);
            beanDefinition.setClassName(className);

            //创建MutablePropertyValues对象
            MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
            //获取bean标签下所有的property标签对象
            List<Element> propertyElements = beanElement.elements("property");
            for (Element propertyElement : propertyElements) {
                String name = propertyElement.attributeValue("name");
                String ref = propertyElement.attributeValue("ref");
                String value = propertyElement.attributeValue("value");
                PropertyValue propertyValue = new PropertyValue(name, ref, value);

                mutablePropertyValues.addPropertyValue(propertyValue);
            }
            //将MutablePropertyValues对象封装到BeanDefinition对象中
            beanDefinition.setPropertyValues(mutablePropertyValues);

            //将BeanDefinition对象注册到注册表中
            registry.registerBeanDefinition(id, beanDefinition);
        }
    }
}

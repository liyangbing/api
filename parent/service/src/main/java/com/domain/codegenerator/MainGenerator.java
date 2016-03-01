package com.domain.codegenerator;

/**
 * MyBatisGeneratorCall
 */
public class MainGenerator {

    public static void main(String[] args) throws Exception {
        // 产生Bean和对应的MyBatis XML文件
        BeanXMLGenerator beanGenerator = new BeanXMLGenerator();
        beanGenerator.generate();

        // 产生DAO和对应的Manager类
        String[] entities = new String[]{"City"};
        BusinessGenerator businessGenerator = new BusinessGenerator();
        businessGenerator.generate(entities);
    }
}


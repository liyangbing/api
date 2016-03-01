package com.domain.codegenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 产生Service层和DAO层
 *  Created by ybli on 2015/5/19.
 */
public class BusinessGenerator {

    public void generate(String[] entitys) throws Exception {
        //初始化FreeMarker配置
        //创建一个Configuration实例
        Configuration cfg = new Configuration();

        //设置FreeMarker的模版文件位置
        cfg.setDirectoryForTemplateLoading(new File(GeneratorConfig.PROJECT_PATH + GeneratorConfig.TEMPL_PATH, "templ"));
      //  File fileEntity = new File(GeneratorConfig.PROJECT_PATH + "/" + GeneratorConfig.ENTITY_GEN_PATH);
//        String[] entitys = fileEntity.list(new FilenameFilter() {
//            @Override
//            public boolean accept(File file, String s) {
//                if ("xml".equals(s)) {
//                    return false;
//                }
//                return true;
//            }
//        });

        File daoPath = new File(GeneratorConfig.PROJECT_PATH + GeneratorConfig.DAO_GEN_PATH);
        File daoImplPath = new File(GeneratorConfig.PROJECT_PATH + GeneratorConfig.DAO_IMPL_GEN_PATH);
        File servicePath = new File(GeneratorConfig.PROJECT_PATH + GeneratorConfig.SERVICE_GEN_PATH);
        File serviceImplPath = new File(GeneratorConfig.PROJECT_PATH + GeneratorConfig.SERVICE_IMPL_GEN_PATH);

        Template daoftl = cfg.getTemplate("dao.ftl");
        Template daoImplftl = cfg.getTemplate("daoImpl.ftl");
        Template serviceftl = cfg.getTemplate("service.ftl");
        Template serviceImplftl = cfg.getTemplate("serviceImpl.ftl");

        // 遍历实体类
        for (String entityName : entitys) {
            entityName = entityName + ".java";
            System.out.println("为 " + entityName + "生成服务类、控制类开始");
            String entityNameClass = entityName.substring(0, entityName.indexOf(".java"));

            // 首字母小写类名
            String entityNameClassLower = entityNameClass.replace(
                    entityNameClass.charAt(0), (char) (entityNameClass.charAt(0) + 32));

            Map root = new HashMap();
            root.put("author", "");
            root.put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date()));
            root.put("entityName", entityNameClass);
            root.put("entityNameLower", entityNameClassLower);

            File daoClass = new File(daoPath, entityNameClass + "DAO.java");
            File daoImplClass = new File(daoImplPath, entityNameClass + "DAOImpl.java");
            File serviceClass = new File(servicePath, entityNameClass + "Service.java");
            File serviceImplClass = new File(serviceImplPath, entityNameClass + "ServiceImpl.java");

            daoftl.process(root, new OutputStreamWriter(new FileOutputStream(daoClass)));
            daoImplftl.process(root, new OutputStreamWriter(new FileOutputStream(daoImplClass)));
            serviceftl.process(root, new OutputStreamWriter(new FileOutputStream(serviceClass)));
            serviceImplftl.process(root, new OutputStreamWriter(new FileOutputStream(serviceImplClass)));
            System.out.printf("为 " + entityName + "生成服务类、控制类结束");
        }
    }
}

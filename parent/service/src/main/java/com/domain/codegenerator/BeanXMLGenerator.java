package com.domain.codegenerator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ybli on 2015/5/19.
 */
public class BeanXMLGenerator {
    /**
     * 产生Mybatis
     */
    public void generate() throws Exception {
        System.out.println("generate xml and bean start");
        List warnings = new ArrayList();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        boolean overwrite = true;
        File file = new File(GeneratorConfig.PROJECT_PATH +  GeneratorConfig.GENERATOR_CONFIG);
        Configuration configuration = cp.parseConfiguration(file);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.printf("generate xml and bean finish");
    }
}

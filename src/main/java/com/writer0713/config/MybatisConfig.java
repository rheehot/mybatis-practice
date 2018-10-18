package com.writer0713.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MybatisConfig {

  @Autowired
  BasicDataSource basicDataSource;

  @Bean
  public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

    // setDataSource
    factoryBean.setDataSource(basicDataSource);

    // setTypeAliasesPackage
    factoryBean.setTypeAliasesPackage("com.writer0713.domains");

    // setMapperLocations
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Resource[] resources = resolver.getResources("classpath:mybatis/mappers/*.xml");
    factoryBean.setMapperLocations(resources);

    // setConfiguration
    org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
    config.setMapUnderscoreToCamelCase(true);
    factoryBean.setConfiguration(config);

    return factoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean());
  }


}

package com.cafe.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
/**
* The login action.
*
* @author  	phap.nguyen
* @version 	1.0
* @since   	2015-05-27 
* @update   
*/
public class MyBatisUtil {
	public final static String MYBATIS_CONFIG="mybatis-config.xml";
	private static SqlSessionFactory sessionFactory = getSessionFactory();
	private static Logger log = Logger.getLogger(MyBatisUtil.class);

	public static SqlSessionFactory getSessionFactory() {
		if (null == sessionFactory) {
			InputStream stream;
			try {
				stream = Resources.getResourceAsStream(MYBATIS_CONFIG);
				sessionFactory = new SqlSessionFactoryBuilder().build(stream);
			} catch (IOException e) {
				log.error("not find mybatis-config.xml");
				throw new RuntimeException(e.getCause());
			}
		}
		return sessionFactory;
	}

	public static SqlSession openSession() {
		return sessionFactory.openSession();
	}
}

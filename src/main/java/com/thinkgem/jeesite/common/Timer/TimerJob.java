package com.thinkgem.jeesite.common.Timer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/** 
 * @author Changjielai
 * @version 2017年7月27日 上午10:26:11 
 * 系统定时任务
 */
@Configuration
@PropertySource("classpath:jeesite.properties")
@Component("timerJob")  
@EnableScheduling
@Lazy(false)
public class TimerJob {
	@Autowired  
    private Environment env;
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 同步人事系统、教务系统数据
	 * @author Changjielai
	 */
	@Scheduled(cron = "${syn.time}")
    @Transactional
    public void jobSynDataFormHr() {
    	String status = env.getProperty("syn.status");
    	if (StringUtils.equals(status, "true")) {
    		System.out.println("--开始同步人事系统、教务系统数据--");
    		Session session = sessionFactory.getCurrentSession();
    		session.doWork(new Work(){
	    		public void execute(Connection conn) throws SQLException {
		    		ResultSet rs = null;
		    		CallableStatement call = conn.prepareCall("{call p_syn_data.do_syn()}");
		    		rs = call.executeQuery();
		    		call.close();
		    		rs.close(); 
	    		}
    		});
    		System.out.println("--完成同步人事系统、教务系统数据--");
		}
	}
	
    /**
     * 系统首页统计定时更新
     * @author Changjielai
     */
    @Scheduled(cron = "${index.time}")
    @Transactional
    public void jobSynDataFormEdu() {
    	String status = env.getProperty("index.status");
    	if (StringUtils.equals(status, "true")) {
    		System.out.println("--开始首页统计定时更新--");
    		Session session = sessionFactory.getCurrentSession();
    		session.doWork(new Work(){
	    		public void execute(Connection conn) throws SQLException {
	    			Calendar calendar = Calendar.getInstance();
	    			String year = String.valueOf(calendar.get(Calendar.YEAR));
		    		ResultSet rs = null;
		    		CallableStatement call = conn.prepareCall("{call proc_index_org_info('FADF7D0B6C534160BD6EEF37909581F7',"+year+","+UserUtils.getUser().getId()+")}");
		    		rs = call.executeQuery();
		    		call.close();
		    		rs.close(); 
	    		}
    		});
    		System.out.println("--完成首页统计定时更新--");
		}
	}
}

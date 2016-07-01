package com.sumscope.optimus.ibroker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class Config {

	static Logger m_log = LogManager.getLogger(Config.class.getName());
	private String m_config;
	private InputStream m_in = null;
	private OutputStream m_out = null;
	private Properties m_prop = new Properties();
	private boolean m_isOK = false;	
	private boolean m_readonly = true;
	private static SimpleDateFormat m_settlement_date_formater = new SimpleDateFormat("yyyyMMdd");
	private static String m_settlement_date = "";
	
	public Config(String config, boolean readonly) {
		m_config = config;
		
		try {
			m_in = getClass().getClassLoader().getResourceAsStream(m_config);
			m_prop.load(m_in);			
			m_settlement_date = m_settlement_date_formater.format(new Date());
			m_readonly = readonly;
			m_isOK = true;
		} catch (Exception e) {				
			m_log.error(e.toString());
			m_isOK = false;
		}
	}
	
	public String get_config_file() {
		return m_config;
	}
	
	//return null if not found
	public String get_property(String key) {
		return m_prop.getProperty(key);
	}
	
	public void set_property(String key,String value) {
		if( m_readonly ) {
			return;
		}
		m_prop.setProperty(key,value);
		
	}
	
	public String get_settlement_date () {
		return m_settlement_date;
	}
	
	public boolean isOk () {
		return m_isOK;
	}
	
	public void flush() {
		if( m_readonly ) {
			return;
		}
		try {			
			java.net.URL url = getClass().getClassLoader().getResource(m_config); 
			File file = new File(url.toURI() );
			if( file.exists()) {
				m_out = new FileOutputStream(file,false);
			} else {
				m_out = System.out;
			}			
			m_prop.store(m_out,"");		
			m_out.close();
		} catch (Exception e) {
			m_log.error(e.toString());
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return m_prop.toString();		
	}
	
	public static void main(String[] args) throws Exception {
		Config ins =new Config("last-ref.properties", false);
		String str =ins.get_property("LAST_DONE_DATE");
		str="000";
		ins.set_property("LAST_DONE_DATE", str);
		ins.flush();
		
		str = "111";
		ins.set_property("LAST_DONE_DATE", str);
		ins.flush();
		
		str = "222";
		ins.set_property("LAST_DONE_DATE", str);
		ins.flush();
	}

}

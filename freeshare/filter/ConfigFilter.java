package com.free4lab.freeshare.filter;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.python.util.PythonInterpreter;

import com.free4lab.freeshare.Const;


public class ConfigFilter implements Filter {
	 static String APIPrefix_FreeSearch;

	public void init(FilterConfig filterConfig) throws ServletException {
		Properties p = new Properties();
		try {
			p.load(ConfigFilter.class.getClassLoader().getResourceAsStream("url.properties"));
			APIPrefix_FreeSearch = p.getProperty("APIPrefix_FreeSearch");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if(Const.isDev()){
			if(APIPrefix_FreeSearch.contains("newfreesearch")){
				chain.doFilter(request, response);
			}else{
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendError(500, "config is wrong,check the config");
			}
		}else{
			if(APIPrefix_FreeSearch.contains("freetestsearch")){
				chain.doFilter(request, response);
			}else{
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendError(500, "config is wrong,check the config");
			}
		}
	}

	public void destroy() {
		
	}
	
	public static void main(String[] args){
		char sep = File.separatorChar;
		System.out.println(System.getProperty("user.dir") + sep + "python/config/ConfigCheck.py");
		String path = "python/config/ConfigCheck.py";
		PythonInterpreter pi = new PythonInterpreter();
		pi.exec("import sys");
		pi.exec("sys.path.append('python/config')");
		pi.execfile(path);
		Vector<String> v = new Vector<String>();
		v.add(null);
	}

}

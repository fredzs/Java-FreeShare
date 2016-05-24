package com.free4lab.freeshare.filter;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.free4lab.freeshare.URLConst;


/**
 * 检查用户权限，登录失败后跳转到指定的登录页面
 * 
 * <pre>
 * 在Filter的定义中需加入如下描述：
 * &lt;init-param&gt;
 *   &lt;description&gt;权限不够时转向的路径&lt;/description&gt;
 *   &lt;param-name&gt;failPage&lt;/param-name&gt;
 *   &lt;param-value&gt;权限不够的跳转页面&lt;/param-value&gt;
 * &lt;/init-param&gt;	
 * &lt;init-param&gt;
 *   &lt;description&gt;下面的url列表不进行权限检查,多个URI用;分开&lt;/description&gt;
 *   &lt;param-name&gt;exludeURLs&lt;/param-name&gt;
 *   &lt;param-value&gt;需要排除的URI&lt;/param-value&gt;
 * &lt;/init-param&gt;
 * </pre>
 * 
 * @author hubaiyu
 * 
 */
public abstract class PermissionFilter implements Filter {
	private static final Logger logger = Logger.getLogger(PermissionFilter.class);
	private HashSet<String> excludeURIs = new HashSet<String>();

	public void destroy() {
		excludeURIs = null;
	}

	/**
	 * 子类验证权限或者检查是否在放行列表中<br/>
	 * 常量定义 {@link APIConstants}
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (excludeURIs.contains(req.getRequestURI()) || checkPermission(req, res)) {
			chain.doFilter(request, response);
		} else {
			logger.debug("验证未登录， 跳转登录页面...");
			try {
				String schemeServerPort = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
				if(req.getQueryString() == null) {
					res.sendRedirect(URLConst.APIPrefix_FreeAccount + "/login?customId=freeshare&handleUrl="
						+ java.net.URLEncoder.encode(schemeServerPort + req.getContextPath() + "/login"
						+ "?redirectUrl=" + java.net.URLEncoder.encode(schemeServerPort + req.getRequestURI(),"utf-8"),"utf-8"));
				} else {
					res.sendRedirect(URLConst.APIPrefix_FreeAccount  + "/login?customId=freeshare&handleUrl="
						+ java.net.URLEncoder.encode(schemeServerPort + req.getContextPath() + "/login"
						+ "?redirectUrl=" + java.net.URLEncoder.encode(schemeServerPort + req.getRequestURI() + "?" + req.getQueryString(),"utf-8"),"utf-8"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void init(FilterConfig config) throws ServletException {
		
		String contextPath = config.getServletContext().getContextPath();

		String excludeURIString = config.getInitParameter("excludeURIs");
		StringBuilder uris = new StringBuilder();
		if (excludeURIString != null && !excludeURIString.trim().equals("")) {
			excludeURIString = excludeURIString.replaceAll("[\t\n]", "");
			for (String uri : excludeURIString.split(";")) {
				uri = contextPath + uri.trim();
				excludeURIs.add(uri.trim());
				uris.append(uri);
				uris.append(';');
			}
			if (uris.length() > 0) {
				uris.deleteCharAt(uris.length() - 1);
			}		
		}		
	}

	abstract protected boolean checkPermission(HttpServletRequest request, HttpServletResponse response);
}

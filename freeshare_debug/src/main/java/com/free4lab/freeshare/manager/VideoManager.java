package com.free4lab.freeshare.manager;

import org.jsoup.Jsoup;
/*对视频进行url的特殊处理*/
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class VideoManager {
	/*
	 * 优酷获取
	 */
	private static Video getYoukuVideo(String url) throws Exception {
		Document doc = getURLContent(url);
		String pic = getElementAttrById(doc, "s_msn2", "href");
		if (pic == null || pic == "") {
			pic = "http://freedisk.free4lab.com/download?uuid=172c7251-55ba-4805-97a5-97f6e07e6063";
		} else {
			int local = pic.indexOf("screenshot=") + 11;
			pic = pic.substring(local, pic.length());
		}
		String flash = getElementAttrById(doc, "link2", "value");
		Video video = new Video();
		video.setPic(pic);
		video.setFlash(flash);
		return video;
	}

	/*
	 * 土豆获取
	 */
	private static Video getTudouVideo(String url) throws Exception {
		Document doc = getURLContent(url);
		String content = doc.html();
		int beginLocal = content.indexOf("<script>");
		int endLocal = content.indexOf("</script>");
		content = content.substring(beginLocal, endLocal);

		String flash = getScriptVarByName("iid_code = icode =", content);
		flash = "http://www.tudou.com/v/" + flash + "/v.swf";
		Video video = new Video();
		video.setPic("");
		video.setFlash(flash);
		return video;
	}

	/*
	 * 56获取
	 */
	private static Video get56Video(String url) throws Exception {
		Document doc = getURLContent(url);
		String content = doc.html();
		String pic = "";
		int begin = content.indexOf("\"img\":\"");
		if ((begin + 200) < content.length()) {
			content = content.substring(begin + 7, begin + 200);
			int end = content.indexOf("jpg \"");
			if (end == -1) {
				pic = "http://freedisk.free4lab.com/download?uuid=172c7251-55ba-4805-97a5-97f6e07e6063";
			} else {
				pic = content.substring(0, end + 4).trim();
				pic = pic.replaceAll("\\\\", "");
			}
		}
		String flash = "http://player.56.com"
				+ url.substring(url.lastIndexOf("/"), url.lastIndexOf(".html"))
				+ ".swf";
		Video video = new Video();
		video.setPic(pic);
		video.setFlash(flash);
		return video;
	}

	/*
	 * 酷6获取
	 */
	private static Video getKu6Video(String url) throws Exception {
		Document doc = getURLContent(url);
		String flash = getElementAttrById(doc, "swf_url", "value");
		Video video = new Video();
		video.setPic("");
		video.setFlash(flash);
		return video;

	}

	private static String getScriptVarByName(String name, String content) {
		String script = content;
		int begin = script.indexOf(name);
		script = script.substring(begin + name.length() + 2);
		int end = script.indexOf(",");
		script = script.substring(0, end);
		String result = script.replaceAll("'", "");
		result = result.trim();
		return result;
	}

	private static String getElementAttrById(Document doc, String id,
			String attrName) {
		try {
			Element et = doc.getElementById(id);
			String attrValue = et.attr(attrName);
			return attrValue;
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 获取网页的内容
	 */
	private static Document getURLContent(String url) throws Exception {
		Document doc = Jsoup.connect(url).data("query", "Java")
				.userAgent("Mozilla").cookie("auth", "token").timeout(6000)
				.post();
		return doc;
	}

	private Video getVideo(String url) throws Exception {
		Video v = new Video();
		if (url.indexOf("v.youku.com") != -1) {
			v = getYoukuVideo(url);
		} else if (url.indexOf("tudou.com") != -1) {
			v = getTudouVideo(url);
		} else if (url.indexOf("v.ku6.com") != -1) {
			v = getKu6Video(url);
		} else if (url.indexOf("56.com") != -1) {
			v = get56Video(url);
		}
		return v;
	}

	public String getFlash(String url) {
		Video v = new Video();
		try {
			v = getVideo(url);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return v.getFlash();
	}

	public String getImgsrc(String url) {
		Video v = new Video();
		try {
			v = getVideo(url);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return v.getPic();
	}

	public static void main(String[] args) {
		Video v = new Video();
		String url = "http://www.56.com/u71/v_NzI2ODA1ODA.html";
		try {
			v = get56Video(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(v.getFlash());
	}
}

class Video {
	private String flash;
	private String pic;

	public Video() {
	};

	public String getFlash() {
		return flash;
	}

	public void setFlash(String flash) {
		this.flash = flash;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
}

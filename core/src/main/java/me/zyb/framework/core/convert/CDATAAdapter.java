package me.zyb.framework.core.convert;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author zhangyingbin
 */
public class CDATAAdapter extends XmlAdapter<String, String> {
	private static final String CDATA_BEGIN = "<![CDATA[";
	private static final String CDATA_END = "]]>";
	@Override
	public String unmarshal(String v) {
		if (v.startsWith(CDATA_BEGIN) && v.endsWith(CDATA_END)) {
			v = v.substring(CDATA_BEGIN.length(), v.length() - CDATA_END.length());
		}
		return v;
	}

	@Override
	public String marshal(String v) {
		return CDATA_BEGIN + v + CDATA_END;
	}
}

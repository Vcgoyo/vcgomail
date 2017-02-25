package com.vcgo.item.pojo;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.vcgo.pojo.TbItem;

public class Item extends TbItem{


	public String[] getImages() {
		if(StringUtils.isNotEmpty(getImage())){
			String[] images = getImage().split(",");
			return images;
		}
		return null;
	}
	
}

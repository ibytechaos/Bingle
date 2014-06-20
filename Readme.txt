*作者：伍志鹏
*邮箱：wzplovextt@icloud.com
*使用方法：配置你的电脑，将SolrCloud文件全放在/Library/文件夹下，建议在linux下。将你的master的虚拟ip调为10.211.55.2 slave1的ip调为10.211.55.4，slave2的ip调为10.211.55.5，master的hostname调为zplovett，salve1的hostname调为ubuntu，slave2的hostname调为wbuntu。
*注意：jdk请用java8
*Servlet文件夹内是Bingle的源代码，其中BinglePhone为基于iphone4s的phongap web端的源代码
*Xcode文件夹内为iOS端phonegap的本地源代码，直接编译即可用
*功能说明：关键词可以为情感关键词，也可为普通关键词，结果返回有地图，图片，使用solr的组建有高亮，more like this，拼写纠错，地理位置检索，分页显示结果，自己写的算法为基于汉语的拼写建议，二维码本地生成，调用外部借口为百度地图js段，百度地图ip转经纬度接口，flickr的java接口的图片检索，具体的见ppt和截图。。。
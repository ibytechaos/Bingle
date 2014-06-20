package bingle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
public class PostSearch
        extends HttpServlet
{
    private final int DEFAULT_PAGES = 2;
    private final int MAX_PAGES = 50;

    private int[] caluPages(int curPage, long totalPages)
    {
        int[] result = { curPage, 2 };
        if (totalPages < 2L) {
            result = new int[] { 1, 2 };
        } else if (curPage < 2) {
            result = new int[] { 1, 5 };
        } else {
            result = new int[] { curPage - 2 + 1, 5 };
        }
        return result;
    }

    public void destroy()
    {
        super.destroy();
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>Bingle</TITLE><meta http-equiv=\"refresh\" content=\"0;url=http://localhost:8080/BinglePhone\"> </HEAD>");
        out.println("  <BODY>");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String keywards = request.getParameter("keywards");
        String startRow;
        try
        {
            startRow = request.getParameter("startRow");
        }
        catch (Exception e)
        {
            //String startRow;
            startRow = "0";
        }
        String pg;
        try
        {
            pg = request.getParameter("pg");
        }
        catch (Exception e)
        {
            //String pg;
            pg = "1";
        }
        try
        {
            //e = request.getParameter("id");
        }
        catch (Exception localException1) {}
        PrintWriter out = response.getWriter();
        out.println("<!doctype html>");
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<TITLE>Bingle</TITLE>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/BinglePhone/css/button.css\" title=\"Style\">");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/BinglePhone/css/autoComplete.css\" title=\"Style\">");
        out.println("<style type=\"text/css\">");
        out.println(".img {");
        out.println("\tdisplay: inline-block;");
        out.println("\tposition: relative;");
        out.println("\tz-index: 2;");
        out.println("\ttop: 20px;");
        out.println("\tleft: 106.1px;");
        out.println("}");
        out.println(".keywards {");
        out.println("    width: 200px;");
        out.println("    height: 22px;");
        out.println("    font-size: 16px;");
        out.println("    line-height: 22px;");
        out.println("    font-family: arial;");
        out.println("    margin: 5px 0px 0px 7px;");
        out.println("    background: none repeat scroll 0% 0% #FFF;");
        out.println("    outline: 0px none;");
        out.println("}");
        out.println(".buttons {");
        out.println("    width: 75px;");
        out.println("    height: 29px;");
        out.println("    font-size: 16px;");
        out.println("    background-color: #4789FA;");
        out.println("    border-style :none;");
        out.println("}");
        out.println("#form {");
        out.println("\tposition: absolute;");
        out.println("\ttop: 60px;");
        out.println("\tz-index: 2;");
        out.println("\theight: 34px;");
        out.println("\twidth: 100%;");
        out.println("}");
        out.println("#bac_keywards{");
        out.println("\tposition: absolute;");
        out.println("\tleft: 0;");
        out.println("\ttop: 0px;");
        out.println("\tz-index: 1;");
        out.println("\tbackground-color: #F2F2F2;");
        out.println("\theight: 110px;");
        out.println("\twidth: 100%;");
        out.println("}");
        out.println(".result-document {");
        out.println("  border: 1px solid #999;");
        out.println("  padding: 5px;");
        out.println("  margin: 5px;");
        out.println("  background-color: #F2F2F2;");
        out.println("  margin-left: 5px;");
        out.println("  right: 10px;");
        out.println("  margin-bottom: 15px;");
        out.println("}");
        out.println(".result-num {");
        out.println("  padding: 5px;");
        out.println("  margin: 5px;");
        out.println("  margin-left: 5px;");
        out.println("  right: 10px;");
        out.println("  margin-bottom: 15px;");
        out.println("}");
        out.println(".results {");
        out.println("\tposition: absolute;");
        out.println("\ttop: 132px;");
        out.println("}");
        out.println(".resultsNum {");
        out.println("\tposition: absolute;");
        out.println("\ttop: 112px;");
        out.println("\tleft: 7px;");
        out.println("}");
        out.println("a.title:link {");
        out.println("\tcolor: #045DEA;");
        out.println("\ttext-decoration: none;");
        out.println("}");
        out.println("a.title:visited {");
        out.println("\ttext-decoration: none;");
        out.println("\tcolor: #650885;");
        out.println("}");
        out.println("a.title:hover {");
        out.println("\ttext-decoration: none;");
        out.println("\tcolor: #805984;");
        out.println("}");
        out.println("a.title:active {");
        out.println("\ttext-decoration: none;");
        out.println("\tcolor: #B5BEBF;");
        out.println("}");
        out.println("a.title {");
        out.println("\tfont-size: 22px;");
        out.println("}");
        out.println("a.url {");
        out.println("\tfont-size: 17px;");
        out.println("}");
        out.println("a.url:link {");
        out.println("\ttext-decoration: none;");
        out.println("\tcolor: #006621;");
        out.println("}");
        out.println("a.trans {");
        out.println("\tfont-size: 17px;");
        out.println("}");
        out.println("a.trans:link {");
        out.println("\ttext-decoration: none;");
        out.println("\tcolor: #045DEA;");
        out.println("}");
        out.println("</style>");
        out.println("<script src=\"/BinglePhone/jQueryAssets/autoComplete.js\" type=");
        out.println("\"text/javascript\"></script>");
        out.println("");
        out.println("</HEAD>");
        Hanyu hanyu = new Hanyu();
        String strPinyin = hanyu.getStringPinYin(keywards);
        //System.out.println(strPinyin);
        //out.println("  <BODY onload=\"initialize0(),initialize1(),initialize2(),initialize3(),initialize4(),initialize5()\">");
        out.println("<BIDY>");

        
        out.println("<div class=\"bac_keywards\" id=\"bac_keywards\">");
        out.println("\t<div class=\"img\" id=\"img\"><img src=\"/BinglePhone/img/Bingle.png\" width=\"107.8\" height=\"38\" alt=\"Bingle\"/></div>");
        out.println("\t<div class=\"form\" id=\"form\">");
        out.println("\t<form id=\"form1\" name=\"form1\" method=\"post\" action=\"/BinglePhone/servlet/PostSearch\">");
        out.println("\t\t<input name=\"keywards\" type=\"text\" class=\"keywards\" id=\"keywards\" value=\"\" size=\"60\" maxlength=\"76\" onkeyup=\"autoComplete.start(event)\">");
        out.println("\t\t<div class=\"auto_hidden\" id=\"auto\"><!--自动完成 DIV--></div>");
        out.println("\t\t  ");
        out.println("\t\t<input type=\"submit\" class=\"buttons\" name=\"submit\" id=\"submit\" value=\"Bingle\">");
        out.println("\t</form>");
        out.println("<script type=\"text/javascript\">");
        String pinyinlist = "";
        String pingkey=keywards;
        BufferedReader br = new BufferedReader(new FileReader("/Library/Tomcat/webapps/BinglePhone/list/autoComplete.txt"));
        String data = br.readLine();
        while (data != null)
        {
            pinyinlist = pinyinlist + data;
            data = br.readLine();
        }
        String checkexitString = "$" + pingkey + "#";
        int position = pinyinlist.indexOf(checkexitString);
        if (position == -1)
        {
            FileWriter fw = new FileWriter("/Library/Tomcat/webapps/BinglePhone/list/autoComplete.txt", true);
            String s = ",'0^" + strPinyin + "$" + pingkey + "#'";
            fw.write(s, 0, s.length());
            fw.flush();
            fw.close();
        }
        if (position != -1)
        {
            String subFirString = pinyinlist.substring(0, position);
            int posFir = subFirString.lastIndexOf("^");
            int posSec = subFirString.lastIndexOf("'");
            posSec++;
            String subSecString = subFirString.substring(posSec, posFir);
            int jsSolt = Integer.parseInt(subSecString);
            int jsSoltChe = jsSolt + 1;
            pinyinlist = pinyinlist.replace(jsSolt + "^" + strPinyin + "$" + pingkey + "#", jsSoltChe + "^" + strPinyin + "$" + keywards + "#");
            FileWriter fw = new FileWriter("/Library/Tomcat/webapps/BinglePhone/list/autoComplete.txt");
            String s = pinyinlist;
            fw.write(s, 0, s.length());
            fw.flush();
            fw.close();
        }
        out.println("var autoComplete=new AutoComplete('keywards','auto',[" + pinyinlist + "]);");
        out.println("</script>");
        out.println("");
        out.println("<script type=\"text/javascript\">");
        out.println("\t\tdocument.getElementById(\"submit\").onclick=function(){");
        out.println("\t\tvar kws=document.getElementById(\"keywards\").value;");
        out.println("\t\tkws=kws.replace(/\\s+/g,\"\");");
        out.println("\t\tif(kws==\"\"){");
        out.println("\t\t\treturn false;");
        out.println("\t\t}");
        out.println("\t\t}");
        out.println("</script>");
        out.println("\t</div>");
        out.println("</div>");
        int PAGE_ROWS = 6;
        int curPage = 1;
        int startRows;
        try
        {
            startRows = Integer.parseInt(startRow);
        }
        catch (Exception e)
        {
            //int startRows;
            startRows = 0;
        }
        if (startRows == 0) {
            startRows = 0;
        }
        if (pg == null) {
            pg = "";
        }
        if (pg != "")
        {
            try
            {
                curPage = Integer.parseInt(pg);
            }
            catch (Exception e)
            {
                curPage = 0;
            }
            startRows = (curPage - 1) * PAGE_ROWS;
        }
        
        String flickrkew=keywards;
        if(keywards.indexOf("无聊")!=-1){
        	keywards=keywards+"fun";
        	flickrkew="fun";
        }
        if(keywards.indexOf("饿")!=-1){
        	keywards="美食附近的餐馆吃的"+keywards;
        	flickrkew="美食";
        }
        if(keywards.indexOf("我失恋")!=-1){
        	keywards="婚介所"+keywards;
        	flickrkew="美女";
        }
        String zkHost = "localhost:2181";
        String defaultCollection = "collection1";
        CloudSolrServer solr = new CloudSolrServer(zkHost);
        solr.setDefaultCollection(defaultCollection);

        SolrQuery query = new SolrQuery();
        query.setQuery(keywards);
       
        query.setHighlight(true);
        query.addHighlightField("title");
        query.addHighlightField("content");
        query.addHighlightField("url");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        query.setHighlightFragsize(150);
        query.setStart(0);
        query.setRows(100);
        query.setParam("mlt", new String[] { "true" });
        query.setParam("mlt.fl", new String[] { "title" });
        query.set("spellcheck", new String[] { "on" });
        query.set("spellcheck.q", new String[] { keywards });
        query.set("spellcheck.collate", new String[] { "true" });
        solr.connect();
        int numa = 0;
        try
        {
            QueryResponse res = solr.query(query);
            SolrDocumentList docs = res.getResults();
            out.println("<div class=\"resultsNum\">");
            out.println("<font color=#CDB39B> 一共" + docs.getNumFound() + "条结果(" + res.getQTime() + ")</font>");
            out.println("</div>");
            out.println("<div class=\"results\">");
            if (docs.getNumFound() == 0L) {
                out.println("     抱歉，没有找到与“<font color=#CC0000>" + keywards + "</font>" + "”相关的网页。" + "<br><br>     建议：<br>     检查输入是否正确<br>     简化输入词<br>     尝试其他相关词，如同义、近义词等");
            }
            try{
            String flickrurlString="https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=13d7ae703cb9302172876e1fc284c8a2&&lat=+30.555332&lon=104.000417&radius=10&text="+flickrkew;
            URLConnection uc = new URL(flickrurlString).openConnection();
            DataInputStream dis = new DataInputStream(uc.getInputStream());
            out.println("<div class=\"result-document\">");
            FileWriter fw = new FileWriter(new File("/Library/Tomcat/webapps/BinglePhone/list/photo.xml"));
            String nextline;
            String[] servers = new String[10000000];
            String[] ids = new String[10000000];
            String[] secrets = new String[10000000];
            while ((nextline = dis.readLine()) != null) {
                fw.append(nextline);
            }
            dis.close();
            fw.close();
            String filename = "/Library/Tomcat/webapps/BinglePhone/list/photo.xml";
            XMLInputFactory factory = XMLInputFactory.newInstance();
            //System.out.println("FACTORY: " + factory);

            XMLEventReader r = factory.createXMLEventReader(filename, new FileInputStream(filename));
            int i = 0;
            while (r.hasNext()) {

                XMLEvent event = r.nextEvent();
                if (event.isStartElement()) {
                    StartElement element = (StartElement) event;
                    String elementName = element.getName().toString();
                    if (elementName.equals("photo")) {
                        i++;
                        Iterator iterator = element.getAttributes();
                        URI uri=null;
                        while (iterator.hasNext()) {

                            Attribute attribute = (Attribute) iterator.next();
                            QName name = attribute.getName();
                            String value = attribute.getValue();
                            //System.out.println("Attribute name/value: " + name + "/" + value);
                            if ((name.toString()).equals("server")) {
                                servers[i] = value;
                                //System.out.println("Server Value" + servers[0]);
                            }
                            if ((name.toString()).equals("id")) {
                                ids[i] = value;
                            }
                            if ((name.toString()).equals("secret")) {
                                secrets[i] = value;
                            }
                            //System.out.println(i);
                            String flickrurl = "http://static.flickr.com/" + servers[i] + "/" + ids[i] + "_" + secrets[i] + ".jpg";
                            //System.out.println(flickrurl);
                            uri = new URI(flickrurl);
                            //System.out.println(uri);
                            
                        }
                        out.println("<img src="+uri+" width=\"92\" height=\"92\"/>");
                        if(i>=9){break;}
                    }
                }
               
            }out.println("</div>");} catch (Exception e)
            {
            }
            for (SolrDocument doc : docs)
            {
                Map<String, List<String>> bingleMap = (Map)res.getHighlighting().get(doc.getFieldValue("id"));
                if ((bingleMap != null) && (!bingleMap.isEmpty()))
                {
                    List<String> titleList = (List)bingleMap.get("title");
                    List<String> contentList = (List)bingleMap.get("content");
                    List<String> urlList = (List)bingleMap.get("url");
                    String urlr = (String)doc.getFieldValue("url");
                    String titler = (String)doc.getFieldValue("title");
                    String contentr = (String)doc.getFieldValue("content");
                    String id = (String)doc.getFieldValue("id");
                    String spdy=(String)doc.getFieldValue("spdy");
                    String sptime=null;
                    String sptype=null;
                    String spzy=null;
                    String boost=null;
                    
                    try{
                    	sptime=(String)doc.getFieldValue("sptime");
                    }catch (Exception e){}
                    try{
                    	boost=(String)doc.getFieldValue("boost");
                    }catch (Exception e){}
                    try{
                    	sptype=(String)doc.getFieldValue("sptype");
                    }catch (Exception e){}
                    try{
                    	spzy=(String)doc.getFieldValue("spzy");
                    }catch (Exception e){}
                    if(sptime!=null){
                    	//out.println("<div class=\"result-document\">");
                    	
                    	//out.println("</div>");
                    }
                    out.println("<div class=\"result-document\">");
                    String loa_str=null;
                    String loa_str_1_coordinate=null;
                    String loa_str_0_coordinate=null;
                    try{
                    	loa_str = (String)doc.getFieldValue("loa_str");
                    }catch (Exception e){}
                        if (loa_str!= null)
                        {
                        	/*
                            out.println("<script type=\"text/javascript\" src=\"http://maps.googleapis.com/maps/api/js?key=AIzaSyCPVkHHIuutyMWkFlhnQ55-I6Id51TK0-k&sensor=TRUE\"></script>");
                            out.println("<script type=\"text/javascript\">");
                            out.println("function initialize" + numa + "() {");
                            out.println("var locat=new google.maps.LatLng(" + loa_str + ");");
                            out.println("var mapOptions = {");
                            out.println("center: locat,");
                            out.println("zoom: 12,");
                            out.println("mapTypeId: google.maps.MapTypeId.ROADMAP");
                            out.println("};");
                            out.println("var map = new google.maps.Map(document.getElementById(\"map_canvas" + numa + "\"),");
                            out.println("    mapOptions);");
                            out.println("var marker = new google.maps.Marker({");
                            out.println("position: locat,");
                            out.println("});");
                            out.println("marker.setMap(map);");
                            out.println("");
                            out.println("");
                            out.println("");
                            out.println("}");
                            out.println("</script>");
                            out.println("<div id=\"map_canvas" + numa + "\" style=\"width:100%; height:300px\"></div>");
                            */
                        	int postdou=0;
                        	int postdouu=0;
                        	postdou=loa_str.indexOf(",");
                        	postdouu=postdou+1;
                        	loa_str_1_coordinate=loa_str.substring(postdouu);
                        	loa_str_0_coordinate=loa_str.substring(0,postdou);
                        	out.println("<div id=\"allmap"+numa+"\" style=\"width:100%; height:300px\"></div>");
                        	out.println("<script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=2.0&ak=CDYiH2KtPtWwFgOp9nSCU1nO\"></script>");
                        	out.println("<script type=\"text/javascript\">");
                        	out.println("var map = new BMap.Map(\"allmap"+numa+"\");");
                        	out.println("var point = new BMap.Point("+loa_str_1_coordinate+","+loa_str_0_coordinate+");");
                        	out.println("map.centerAndZoom(point, 15);");
                        	out.println("var marker = new BMap.Marker(point);");
                        	out.println("map.addOverlay(marker);");
                        	out.println("marker.setAnimation(BMAP_ANIMATION_BOUNCE);");
                        	out.println("var opts = {");
                        	out.println("width : 200,");
                        	out.println("height: 60, ");
                        	out.println("title : \""+titler+"\" ,");
                        	out.println("enableMessage:true,");
                        	if(spdy=="美食"){
                        	out.println("message:\"亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~\"");}
                        	if(spdy=="fun"){
                        	out.println("message:\"亲耐滴，周末一起出来玩吧？戳下面的链接看下地址喔~\"");
                        	}
                        	out.println("}");
                        	String baiduaddress=null;
                        	if(contentr.length()>=30){
                        		baiduaddress=contentr.substring(0, 30)+"...";
                        	}
                        	else{baiduaddress=contentr;}
                        	out.println("var infoWindow = new BMap.InfoWindow(\"地址："+baiduaddress+"\", opts);");
                        	out.println("map.openInfoWindow(infoWindow,point);");
                        	out.println("</script>");
                        }
                        numa++;
                    out.println("<form action=\"/BinglePhone/servlet/MoreLikeThis\" method=\"post\" name=\"form3\" id=\"form3\">");
                    out.print("<a class=\"title\" target=\"_blank\" href=\"");
                    out.print(urlr);
                    out.println("\">");
                    out.print("<b>");
                    try
                    {
                        for (String title : titleList) {
                            out.print(title);
                        }
                    }
                    catch (Exception e)
                    {
                    	if(titler.length()>=100){
                    		String titlelen=titler.substring(0, 100)+"...";
                    		out.print(titlelen);
                    	}
                    	else{
                        out.print(titler);
                        }
                    }
                    out.print("</b>");
                    out.println("</a>");
                    String text = urlr;
                    int width = 177;
                    int height = 177;

                    String format = "png";
                    Hashtable hints = new Hashtable();

                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                    hints.put(EncodeHintType.MARGIN, Integer.valueOf(0));
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                            BarcodeFormat.QR_CODE, width, height, hints);

                    String urlString = id;
                    urlString = urlString.replaceAll("\\/", "");
                    urlString = urlString.replaceAll("\\:", "");
                    urlString = urlString.replaceAll("\\.", "");
                    File outputFile = new File("/Library/Tomcat/webapps/BinglePhone/img/" + File.separator + urlString + ".png");
                    MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
                    out.println("<input name=\"id\" type=\"hidden\" id=\"id\" value=\"" + id + "\">");
                    out.println("<input name=\"keywards\" type=\"hidden\" id=\"keywards\" value=\"" + titler + "\">");
                    out.println("<Button type=\"submit\" class=\"submit blue rarrow\" name=\"morelikethis\" id=\"morelikethis\" value=\"MoreLikeThis\">More Like This</button>");
                    out.println("<br>");

                    out.println("<img src=\"/BinglePhone/img/" + urlString + ".png\" width\"177\" height=\"177\" alt=\"二维码\"/>");
                    out.println("</form>");
                    out.println("<br>");
                    out.print("<a class=\"url\" target=\"_blank\" href=\"");
                    out.print(urlr);
                    out.println("\">");
                    try
                    {
                        for (String url : urlList) {
                            out.print(url);
                        }
                    }
                    catch (Exception e)
                    {
                    	if(urlr.length()>=55){
                    		String urllen=urlr.substring(0, 55)+"...";
                    		out.print(urllen);
                    	}
                    	else{
                        out.print(urlr);
                        }
                    }
                    out.println("</a>      ||<a class=\"trans\" target=\"_blank\" href=\"http://translate.google.com.hk/translate?hl=en&sl=zh-CN&u=" + urlr + "\">" + "Translate this page" + "</a>");
                    out.println("<br>");
                    try
                    {
                        for (String content : contentList) {
                            out.println(content);
                        }
                    }
                    catch (Exception e)
                    {
                    	if(contentr.length()>=200){
                    		String contentlen=contentr.substring(0, 200)+"...";
                    		out.print(contentlen);
                    	}
                    	else{
                        out.print(contentr);
                        }
                    }
                }
                out.println("</div>");
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println("</div>");
        
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {}

    public String getServletInfo()
    {
        return "This is my default servlet created by Eclipse";
    }

    public void init()
            throws ServletException
    {}
}
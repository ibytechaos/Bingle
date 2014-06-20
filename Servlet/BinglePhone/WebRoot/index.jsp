<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Bingle</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="/BinglePhone/css/button.css" title="Style">
	<link rel="stylesheet" type="text/css" href="/BinglePhone/css/autoComplete.css" title="Style">
	<style type="text/css">
.img {
	display: inline-block;
	position: relative;
	z-index: 1;
	left: 45px;
	top: 100px;
}
.keywards {
    width: 265px;
    height: 33px;
    font-size: 27px;
    line-height: 35px;
    font-family: arial;
    margin: 5px 0px 0px 7px;
    background: none repeat scroll 0% 0% #FFF;
    outline: 0px none;
}
.button {
    width: 45px;
    height: 39px;
    font-size: 8px;
    background-color: #F2F2F2;
    border-style :none;
}
#form {
	position: absolute;
	left: 10px;
	right: 10px;
	top: 200px;
	height: 40px;
	
}
</style>
<script src="/BinglePhone/jQueryAssets/autoComplete.js" type=
"text/javascript"></script>
  </head>
  
  <body>
	<div class="img" id="img"><img src="img/Bingle.png" width="220" height="77.5" alt="Bingle"/></div>
	<br/><br/><br/><br/>
	<div class="form" id="form">
    <form id="form1" name="form1" method="post" action="/BinglePhone/servlet/PostSearch">
    	<input name="keywards" type="text" class="keywards" id="keywards" value=""  maxlength="76" onkeyup="autoComplete.start(event)">
    	<div class="auto_hidden" id="auto"><!--自动完成 DIV--></div>
    </form>
    <script>
    <%
    	String pinyinlist="";
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader("/Library/Tomcat/webapps/BinglePhone/list/autoComplete.txt"));  
		String data = br.readLine();//一次读入一行，直到读入null为文件结束  
		while( data!=null){  
		      pinyinlist=pinyinlist+data; 
		      data = br.readLine(); //接着读下一行  
		} 
    %>
	var autoComplete=new AutoComplete('keywards','auto',[<%=pinyinlist%>]);
	</script>
    <script	type="text/javascript">
       document.getElementById("submit").onclick=function(){
       var kws=document.getElementById("keywards").value;
       kws=kws.replace(/\s+/g,"");
       if(kws==""){
           return false;
         }
       }
   </script>
</div>
</body>
</html>

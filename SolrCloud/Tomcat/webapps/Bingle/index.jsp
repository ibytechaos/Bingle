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
	<link rel="stylesheet" type="text/css" href="/Bingle/css/button.css" title="Style">
	<link rel="stylesheet" type="text/css" href="/Bingle/css/autoComplete.css" title="Style">
	<style type="text/css">
.img {
	display: inline-block;
	position: relative;
	z-index: 1;
	left: 450px;
	top: 175px;
}
.keywards {
    width: 605px;
    height: 32px;
    font-size: 16px;
    line-height: 22px;
    font-family: arial;
    margin: 5px 0px 0px 7px;
    background: none repeat scroll 0% 0% #FFF;
    outline: 0px none;
}
.button {
    width: 95px;
    height: 29px;
    font-size: 16px;
    background-color: #F2F2F2;
    border-style :none;
}
#form {
	position: absolute;
	left: 360px;
	top: 387px;
	height: 34px;
}
</style>
<script src="/Bingle/jQueryAssets/autoComplete.js" type=
"text/javascript"></script>
  </head>
  
  <body>
	<div class="img" id="img"><img src="img/Bingle.png" width="539" height="190" alt="Bingle"/></div>
	<div class="form" id="form">
    <form id="form1" name="form1" method="post" action="/Bingle/servlet/PostSearch">
    	<input name="keywards" type="text" class="keywards" id="keywards" value="" size="60" maxlength="76" onkeyup="autoComplete.start(event)">
    	<div class="auto_hidden" id="auto"><!--自动完成 DIV--></div>
    	&nbsp;&nbsp;
    	<button type="submit" class="submit gray tags" name="submit" id="submit" value="Bingle">Bingle</button>
    </form>
    <script>
    <%
    	String pinyinlist="";
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader("/Library/Tomcat/webapps/Bingle/list/autoComplete.txt"));  
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

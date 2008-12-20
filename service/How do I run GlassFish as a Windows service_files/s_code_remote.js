/* SiteCatalyst code version: H.14. Copyright Omniture, Inc. More info available at http://www.omniture.com */
/* Author: Neil Evans */
/************************** CONFIG SECTION ****************************************/
/* Specify the Report Suite(s) */
var s_account="sunjnetdev";
var sun_dynamicAccountSelection=true;
var sun_dynamicAccountList="sunjnet,sunglobal=java.net;sunjnetdev=.";	
/* Specify the Report Suite ID */
var s_siteid="jnet:";
/* Settings for pageName */
var param=window.top.location.search;
var url=window.top.location.host+window.top.location.pathname;
if (typeof s_pageType=='undefined' || s_pageType=="") {
	if (url=="wiki.glassfish.java.net/Wiki.jsp"){ var s_pageName=window.top.location.host+window.top.location.pathname+param; }
else { var s_pageName=url; }
}
s_pageName=s_pageName.replace(".java.net",":");
/* Remote Omniture JS call  */
var sun_ssl=(window.location.protocol.toLowerCase().indexOf("https")!=-1);
	if(sun_ssl == true) { var fullURL = "https://www.sun.com/share/metrics/metrics_group1.js"; }
		else { var fullURL= "http://www-cdn.sun.com/share/metrics/metrics_group1.js"; }
document.write("<sc" + "ript type=\"text/javascript\" src=\""+fullURL+"\"></sc" + "ript>");
/************************** END CONFIG SECTION **************************************/
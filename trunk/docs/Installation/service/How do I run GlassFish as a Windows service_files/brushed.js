/**
 ** Javascript routines to support the BrushedTemplate
 ** In order to localise the used namespace as much as possible,
 ** all functions are encapsulated in wrapper objects. 
 **
 **
 ** 010 String functions (generic stuff) : repeat, trim, decamelize
 ** 020 Array functions (generic stuff) : ], first, last
 ** 030 DOM functions (generic stuff) : 
 **      getNodeText, getElementsByClassName, getElementsByCssSelector, 
 **      getElementsByClassName, getElementsByTTagAndClassName, getElementsByCssSelector 
 ** 040 Cookie functions (generic stuff) : setCookie, getCookie
 ** 050 URL parsing
 **
 ** 100 Wiki object
 **   onPageLoad reading UserPrefs and setting focus
 **   edit Find and Replace 
 ** 110 AttachmentViewer stuff
 ** 115 Upload : support multiple uploads in one go
 **     EditTools object => see brushededit.js
 ** 120 QuickLinks object 
 ** 130 TabbedSection object
 ** 140 SearchBox object: remember 10 most recent search topics
 ** 150 Colors, GraphBar object: e.g. used on the findpage
 ** 160 URL
 **
 ** 200 Collapsable list items 
 ** 220 RoundedCorners ffs
 ** 230 Sortable : fast sort wikitables
 ** 240 Table-filter : excel like table filters
 ** 250 Categories : turn wikipage link into AJAXed popup *new*
 **
 ** 280 ZebraTable : color odd/even row of a table
 ** 290 HighlightWord : refactored
 **
 **/


/**
 ** 010 String stuff
 **/

// experimental ....
var LocalizedStrings = new Array;
LocalizedStrings["Download Attachment"]   = "Download Attachment";
LocalizedStrings["Image Width "]          = "Width ";
LocalizedStrings["Image Height "]         = "Height ";
LocalizedStrings["No Image Selected"]     = "No Image Selected";
LocalizedStrings["Click to load attachment"] = "Click to load attachment";
LocalizedStrings["Loading Image "]        = "Loading Image ";
LocalizedStrings["Loading Image expired"] = "Loading image expired";
LocalizedStrings["( all )"]               = "( all )";
LocalizedStrings["( start of page )"]     = "( start of page )";
LocalizedStrings["No match found!"]       = "No match found!";
LocalizedStrings["Go To Top"]             = "Go To Top";
LocalizedStrings["Go To Previous Section"]= "Go To Previous Section";
LocalizedStrings["Edit This Section"]     = "Edit This Section";
LocalizedStrings["Go To Next Section"]    = "Go To Next Section";
LocalizedStrings["Go To Bottom"]          = "Go To Bottom";
LocalizedStrings["Clear Recent Searches"] = "Clear Recent Searches";
LocalizedStrings["Click to collapse"]     = "Click to collapse";
LocalizedStrings["Click to expand"]       = "Click to expand";
LocalizedStrings["Click to sort"]         = "Click to sort";
LocalizedStrings["Ascending order"]       = "Ascending order - Click to sort in descending order";
LocalizedStrings["Descending order"]      = "Descending order - Click to sort in ascending order";
LocalizedStrings["Clone new page as "]    = "Clone new page as ";
LocalizedStrings["Please make first a selection."]    = "Please make first a selection.";
//
String.prototype.localized = function( )
{
  if( LocalizedStrings == undefined) return this;
  var s = LocalizedStrings[ this ]; if( !s ) return this;
  return s;
}


// repeat string size time
String.prototype.repeat = function( size )
{
   var a = new Array( size );
   for( var i=0; i < size; i++ ) { a[i] = this; }
   return( a.join("") );
}
// remove leading and trailing whitespace
String.prototype.trim = function() 
{
  return this.replace(/^\s+|\s+$/g,'');
}
// split CamelCase string in readable string
String.prototype.deCamelize = function()
{
  return this.replace(/([a-z])([A-Z])/g,'$1 $2');
}
// replace xml chars by &entities;
String.prototype.escapeXML = function()
{
  var s = this.replace( /&/g, "&amp;" ); 
  s = s.replace( /</g, "&lt;" ); 
  s = s.replace( />/g, "&gt;" ); 
  return s;
}

/* parse number anywhere inside a string */
Number.REparsefloat = new RegExp( "([+-]?\\d+(:?\\.\\d+)?(:?e[-+]?\\d+)?)", "i");

/* parse a date anywhere inside a string */
/* todo */


/**
 ** TABLE stuff
 **/
function $T( id )
{
  var t = $( id ); if( !t ) return null;
  if( t.tBodies[0] ) t = t.tBodies[0];
  return t;
}


/**
 ** 030 DOM document functions
 **/
 
// get text of a dhtml node
getNodeText = function( node )
{
  return( node.innerHTML.stripTags() );
}

function encapsulateChildren( node, tagName)
{
  var e = document.createElement( tagName );
  while( node.firstChild ) e.appendChild( node.firstChild );
  node.appendChild( e ) ;
  return e;
}


// find first ancestor element with tagName
function getAncestorByTagName( node, tagName ) 
{
  if( !node) return null;
  if( node.nodeType == 1 && (node.tagName.toLowerCase() == tagName.toLowerCase()))
    return node;
  else
    return getAncestorByTagName( node.parentNode, tagName );
}

// walk all ancestors and match node with the given classname
function getAncestorsByClassName ( node, clazz, matchFirst)
{
  var result = [];
  var re = new RegExp ('(?:^| )'+clazz+'(?: |$)');  
  while( node )
  { 
    if( re.test( node.className ) )
    {
      if( matchFirst ) return( node );
      result.push( node );
    }
    node = node.parentNode;
  }
  return( (result.length==0) ? null : result ); 
}


// returns an array of elements matching the classname
// returns only the first element matching the classname when matchFirst is true
// returns null when nothing found
function getElementsByClassName( node, clazz, matchFirst )
{
  var result = [];
  var re;
  if( clazz instanceof RegExp) { re = clazz; } 
  else if( typeof clazz == 'string' ) { re = new RegExp ('(?:^| )'+clazz+'(?: |$)'); }
  else return null;

  var n = (node.all) ? node.all : node.getElementsByTagName("*");
  for( var i=0; i<n.length; i++ )
  { 
    if( re.test(n[i].className) )  
    { 
      if( matchFirst ) return(n[i]);
      result.push(n[i]); 
    }
  }
  return( (result.length==0) ? null : result );
}

  
// className = css class name of any element
// matchFirst = true, when matching only the first occurence
document.getElementsByClassName = function( className, matchFirst ) 
{
  return getElementsByClassName( document.documentElement, className, matchFirst );
}

// tagName = name of element, like DIV...
// className = css class name
// matchFirst = true, when matching only the first occurence
document.getElementsByTagAndClassName = function( tagName, className, matchFirst )
{
  return getElementsByClassName( this.getElementsByTagName( tagName ) 
                               , className, matchFirst );
}


/**
 ** 040  cookie stuff 
 **/
document.setCookie = function( name, value, expires, path, domain, secure )
{
  var c = name + "=" + encodeURIComponent( value );

  if( !expires )
  {
    expires = new Date();
  expires.setFullYear( expires.getFullYear() + 1 );
  }
  /* Store the cookies agains the basepath of wiki
     so that different URLformats are supported properly !
   */
  if( !path ) path = Wiki.getBasePath();

  if( expires ) { c += "; expires=" + expires.toGMTString(); } // Date()
  if( path    ) { c += "; path=" + path; }
  if( domain  ) { c += "; domain=" + domain; }
  if( secure  ) { c += "; secure"; } //true = only via https
  //alert("cookie: "+c);
  document.cookie = c;
}

document.getCookie = function( name )
{
  var reMatchCookie = new RegExp ( "(?:; )?" + name + "=([^;]*);?" );
  return( reMatchCookie.test( document.cookie ) ? decodeURIComponent(RegExp.$1) : null );
}

/**
 ** 050  URL handling
 ** - onPageLoad/interal 500ms : make sure URL#HASH is visible
 ** - parseParemeters : read parameter from the URL
 **/
var URL =
{
  parseParameters : function( aParm )
  {
    var u = document.URL.split( '&' );
    for( var i=1; i < u.length; i++ )
    {
      var item = u[i].split('=');
      if( item.length == 1 ) continue;
      if( item[0] == aParm ) return( item[1] );
    } 
    return(0);
  } ,
  
  onPageLoad : function()
  {
    setInterval( URL.makeVisible, 500 );
  } ,

  CurrentURL : null,  //current URL#HASH value
  makeVisible : function()
  {
    if( this.CurrentURL && (this.CurrentURL == location.href) ) return;      
    this.CurrentURL = location.href;
    
    var h = location.hash;    if( h=="" ) return;
    h = h.substring(1); //drop leading #
    node = $(h); if( !node ) return;
    
    while( node )
    {
      if( (node.style) && !Element.visible(node) ) 
      {
        Element.show(node); //eg collapsedBoxes : fixme

        if( TabbedSection.reMatchTabs.test( node.className ) )
        {
          TabbedSection.onclick( node );
        }
      }
      node = node.parentNode;
    } 
    Element.scrollTo( h );
  }
  
}


/**
 ** 100 Wiki functions
 **/
 
var Wiki = 
{
  DELIM : "\u00A4", // non-typeable char - used as delimitter
                    // See also David Lindquist (http://www.gazingus.org) for more great ideas!

  setPageName : function( p ) { this.pagename = p; } ,
  getPageName : function()    { return this.pagename; } ,
  setUserName : function( u ) { if( u != "" ) this.username = u; } ,
  getUserName : function()    { return this.username; } ,

  // baseURL  e.g. www.xxx.org/some-app-name/
  // basePath e.g. /some-app/name  [used by the setCookie function]
  setBaseURL : function( aBaseURL)
  {
    this.BaseURL = aBaseURL;
    this.BasePath = this.BaseURL.slice( this.BaseURL.indexOf( location.host ) 
                                      + location.host.length, -1 );
  } ,
  getBaseURL  : function() { return this.BaseURL; } ,
  getBasePath : function() { return this.BasePath; } ,
  
  setDELIM              : function( d ) { this.DELIM = d; } ,
  setPrefDateFormat     : function( d ) { this.prefDateFormat = d; } ,
  setPrefSkinName       : function( s ) { this.prefSkinName   = s; } ,
  setPrefTimeZone       : function( t ) { this.prefTimeZone   = t; } ,
  setPrefEditAreaHeight : function( e ) { this.prefEditAreaHeight = parseInt(e,10); },
  setPrefShowQuickLinks : function( f ) { this.prefShowQuickLinks = (f == "yes"); },
  setPrefSowCalendar    : function( f ) { this.prefShowCalendar   = (f == "yes"); },
  
  // get main page content element
  getPageElement : function()
  {
    return( $( "page" ) 
          || document.getElementsByClassName( "page", true ) ) ;
  } ,

  onPageLoad : function()
  { 
    //deduct permission level
    this.permissionEdit = ( document.getElementsByClassName( "actionEdit", true) != null )
   
    var f ;  // put focus on the first form element within a page
              f  = $( "workarea" );        // plain.jsp
    if( !f  ) f  = $( "editorarea" );      // plain.jsp
    if( !f  ) f  = $( "editor" );          // Comment.jsp
    if( !f  ) f  = $( "j_username" );      // Login.jsp
    if( !f  ) f  = $( "username" );        // UserPreference.jsp
    if( !f  ) f  = $( "query2" );          // Search.jsp
    if( f && Element.visible(f) ) f .focus();  //IE chokes when focus on invisible element
  } 
}


/**
 ** 110 AttachmentViewer stuff
 **/
var Attach = 
{
  reImageTypes : new RegExp( '(.bmp|.gif|.png|.jpg|.jpeg|.tiff)$', 'i' ) ,
  pic       : null,
  maxWidth  : 0,
  maxHeight : 0,
  countdown : 0, 

  showImage : function( attachment, attDELIM, maxWidth, maxHeight )
  {
    // contains Name[0], Link-url[1], Info-url[2], Size[3, Version[4] 
    var attachArr = attachment.value.split( attDELIM );
    var attachImg  = $("attachImg");
    var attachInfo = $("attachInfo");

    if( !attachImg ) return true;

    if( attachArr.length == 1 ) //no image selected
    {
      if( attachInfo ) Element.hide( attachInfo );
      attachImg.onclick    = Prototype.emptyFunction;
      attachImg.title      = "No Image Selected".localized();
      attachImg.innerHTML  = attachImg.title; 
      return;
    }

    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
    if( attachInfo )
    { 
      attachInfo.href = attachArr[2];
      Element.show( attachInfo );
    }
    attachImg.title = "Click to load attachment".localized();
    attachImg.onclick = function() { location.href = attachArr[1]; };

    if( !this.reImageTypes.test( attachArr[0] ) ) 
    { 
      attachImg.innerHTML  = attachImg.title; 
      return;
    }  

    //start loading image preview
    this.pic = new Image();
    this.pic.src = attachArr[1];
    this.countdown = 30;

    setTimeout( function() { Attach.showLoadedImage(); }, 10 );
  } ,

  showLoadedImage : function ()
  {
    var attachImg  = $("attachImg");

    if( this.pic && this.pic.complete ) 
    { 
      var w = parseInt(this.pic.width);  var wo = w;
      var h = parseInt(this.pic.height); var ho = h;

      if( w > this.maxWidth  ) { h *= this.maxWidth/w;  w = this.maxWidth; }
      if( h > this.maxHeight ) { w *= this.maxHeight/h; h = this.maxHeight; }
      attachImg.innerHTML = "<img src='" + this.pic.src 
                          + "' width='" + parseInt(w) 
                          + "' height='" + parseInt(h)
                          + "' style='margin-top:"+ parseInt((this.maxHeight-h)/2) 
                          + "px;' ></img>";

      attachImg.title += "\n" + "Image Width ".localized() + wo + "px  " 
                       + "Image Height ".localized() + ho  + "px";
      this.countdown = 0; 
      this.pic = null;
      return;  
    }

    if( this.countdown <= 0 ) 
    {  
      attachImg.innerHTML = "Loading Image expired".localized();
      attachImg.title = "Click to load attachment".localized();
      return;
    } 

    this.countdown--;
    setTimeout( function() { Attach.showLoadedImage(); }, 200 ); 
    attachImg.innerHTML = "Loading image ".localized() + this.countdown;
  }
}

/**
 ** 115 Upload: support multiple attachment uploads in one go
 ** Inspired by MultiSelector by Stickman -- http://www.the-stickman.com
 ** with specific stuff to support safari
 **/
var Upload = 
{
  countdown : 8,              // maximum uploads in one go
  idIndex   : 0,              // give unique names to each input file
  
  addFile : function( prevfile )
  {
    var p = prevfile.parentNode;

    if( Upload.isDuplicate( prevfile ) )
    {
      alert( "Please select another file. File '"+prevfile.value+"' has already been selected!");
      p.removeChild( prevfile );
      Upload.countdown++;  
    }
    else
    {
      prevfile.id = "content" + Upload.idIndex++;
      prevfile.name = prevfile.id;

      //Element.hide( prevfile ); --nok in safari
      prevfile.style.position = "absolute";
      prevfile.style.left = "-1000px";

      /* create new entry in the upload viewer */
      var div = document.createElement( "div" );
      div.innerHTML = "<input type='button' value='Remove' onclick='Upload.removeFile(this)' />&nbsp; "
                    + prevfile.value;
      div.fileElement = prevfile;  //store related input file element
      p.appendChild( div );
    }

    /* create new empty file input element */
    var newfile = document.createElement( "input" );
    newfile.type = "file";
    newfile.size = prevfile.size;
    newfile.onchange = function() { Upload.addFile(this); };
    p.insertBefore( newfile, p.firstChild );
   
    if( --Upload.countdown <= 0 ) 
    {
      newfile.id = 'uploadDisabled'; 
      newfile.name = newfile.id;
      //newfile.disabled = "true";  //nok on safari
      Element.hide( newfile );    //safari trick
    } 
    prevfile = null;
  } ,
  
  removeFile: function( button )
  {
    var row = button.parentNode;
    var file = row.fileElement;

    row.parentNode.removeChild( row );
    file.parentNode.removeChild( file );

    Upload.countdown++;  
    var e = $( "uploadDisabled" ); if( !e ) return;
    e.id = "content" + Upload.idIndex++;
    e.name = e.id;
    //e.disabled = '';   //nok on safari
    Element.show( e ); //safari trick
  } ,

  isDuplicate: function( prevfile )
  {
    for( var n = prevfile.parentNode.firstChild; n ; n = n.nextSibling )
    {
      if( n == prevfile ) continue;
      if( n.tagName != "INPUT" ) continue;
      if( n.value == prevfile.value ) return true;
    }
    return false;
  }
}

//FIXME - better extend FORM.ELEMENT of prototype.js
var WikiForm = 
{
  /*
   * SubmitOnce: 
   * real submit button must be hidden, but will be part of the get/post
   * button acts like a proxy submit, but will be deactivated on form submit
   */
  submitOnce : function( form )
  {
    for ( var i = 0; i < form.elements.length; i++ )
    {
      var t = form.elements[i];
      if( t.type.toLowerCase() == "button" ) t.disabled = true;
    }
    return true; 
  }
}


/**
 ** 120 brushed quick links
 **/
var QuickLinks =
{
  sections : [],
  /* build list of wikipage sections - starting with H2, H3, H4 */
  /* exclude TableOfContents, Attachment block, WebLog entries  */
  reMatchHeaders : new RegExp('H2|H3|H4'),
  reExcludeDivs  : new RegExp('toc|weblog|attachments'),
  
  parseSections : function( node )
  {
    if( node.nodeType != 1 ) return; //should not happen
    
    for( var n = node.firstChild; n ; n = n.nextSibling )
    {
      if( n.nodeType != 1 ) continue;
  
      if( ( this.reMatchHeaders.test( n.nodeName ) ) && ( n.id.indexOf("section-") == 0 ) ) 
      {
        this.sections.push( n );
      }
      else if( (n.nodeName == "DIV") && ( !this.reExcludeDivs.test( n.className )) )
      {
        this.parseSections( n ); //recursion
      }
    }  
  } ,
  
  onPageLoad : function()
  {
    if( $("previewcontent") ) return;
    if( !Wiki.prefShowQuickLinks ) return; 
    
    //use the page element to match valid sections
    var page = Wiki.getPageElement();  
    if( !page ) return;
    this.parseSections( page );
  
    var bottom = this.sections.length;
  
    var frag1 = "", frag2 = "", frag3 = "", frag4 = "";
    frag1 += "<span class='quick2Top'><a href='#wikibody' title='"+"Go to Top".localized()+"'   >&laquo;</a></span>";
    frag1 += "<span class='quick2Prev'><a href='#section" ; 
    frag2 += "' title='"+"Go to Previous Section".localized()+"'>&lsaquo;</a></span>";
 
    if( Wiki.permissionEdit )
    {
    frag2 += "<span class='quick2Edit'><a href='Edit.jsp?page=" + Wiki.getPageName() + "&section=";
    frag3 += "' title='"+"Edit this section".localized()+"'>&bull;</a></span>";
    }
    frag3 += "<span class='quick2Next'><a href='#section";
    frag4 +="' title='"+"Go to Next Section".localized()+"'>&rsaquo;</a></span>";
    frag4 += "<span class='quick2Bottom'><a href='#footer" ;  
    frag4 += "' title='"+"Go to Bottom".localized()+"' >&raquo;</a></span>";
  
    var nxt = 0, prv = 0, f="";
    for( var section = 1; section <= bottom; section++)
    {
      nxt = ( section > 1 ) ?      section-1 : 1;
      prv = ( section < bottom ) ? section+1 : bottom;
      
      f = frag1 + nxt + frag2 + (Wiki.permissionEdit ? section : "") + frag3 + prv + frag4;
  
      var div = document.createElement("div");
      div.className = "quicklinks" ;
      div.id = "section" + section;
      div.innerHTML = f;
  
      var n  = this.sections[section-1];
      n.appendChild(div); //fixed
    }
  }
}

/**
 ** 130 Tabbed Section
 **/
var TabbedSection = 
{
  reMatchTabs : new RegExp( "(?:^| )tab-(\\S+)" ),

  onPageLoad : function()
  {
    var t = document.getElementsByClassName( "tabbedSection" );
    if( !t ) return;
  
    for( var i = 0; i<t.length; i++)
    {
      if( !t[i].hasChildNodes ) continue; //take next section
      
      t[i].className = t[i].className +" tabs";
      var tabmenu = [];
      var active = true; //first tab assumed to be the active one
      for( var n = t[i].firstChild; n ; n = n.nextSibling )
      {
        if( !this.reMatchTabs.test( n.className ) ) continue; // not a tab: take next element
  
        if( (n.id==null) || (n.id=="") ) n.id = n.className;
        n.style.display = ( active ? "" : "none" );
  
        /* <span><a class="active" href="#" id="menu-tabID" 
                   onclick="TabbedSection.onclick('tabID')" >xyz</a></span>
        */
        tabmenu.push( "<span><a class='" + ( active ? "activetab" : "" ) + "' " ); 
        tabmenu.push( "id='menu-" + n.id + "'" ); 
        tabmenu.push( "onclick='TabbedSection.onclick(\"" + n.id + "\")' >" );
        tabmenu.push( RegExp.$1.deCamelize() + "</a></span>" );
        active=false;
      }
      if( tabmenu.length == 0 ) continue; //take next section
      var e = document.createElement( "div" );
      e.className = "tabmenu" ;
      e.innerHTML = tabmenu.join( "" );
      t[i].parentNode.insertBefore( e, t[i] );
  
    } // take next section  
  } ,
  
  onclick : function ( tabId )
  {
    var target = $( tabId );
    // fixme: DiffContents.jsp uses empty <a /> which goes ahoc for js DOM tree routines
    var section = target.parentNode;
    if( !section ) return;
    
    for( var n = section.firstChild; n ; n = n.nextSibling )
    {
      if( !n.id ) continue;
      var m = $( "menu-" + n.id );
      if( m && m.className == "activetab" )
      {
        if( n.id == target.id ) break; //stop - is already activetab
        n.style.display = "none";
        m.className = "";
        target.style.display = "";
        $( "menu-" + target.id ).className = "activetab";
        break;
      }
    }  
    return false;
  }
}  

/**
 ** 140 SearchBox
 **  Remember 10 most recent search topics
 **  Uses a cookie to store to 10 most recent search topics
 **
 **  Extensions for quick link to View Page, Edit Page, Find as is.
 **  (based on idea of Ron Howard - Nov 05)
 **/
var SearchBox = 
{
  submit : function ( queryValue )
  {
    for(var i=0; i < this.recentSearches.length; i++)
    {
      if( this.recentSearches[i] == queryValue ) return;
    }

    if( !this.recentSearches ) this.recentSearches = new Array();
    if( this.recentSearches.length > 9 ) this.recentSearches.pop();
    this.recentSearches.unshift( queryValue );

    document.setCookie( "JSPWikiSearchBox", this.recentSearches.join( Wiki.DELIM) );
  } ,

  onPageLoad : function()
  {
    this.searchForm = $("searchForm");
    if( !this.searchForm ) return;

    this.recentSearchesDIV = $("recentSearches");
    if( !this.recentSearchesDIV ) return;

    this.recentSearches = new Array();
    var c = document.getCookie( "JSPWikiSearchBox" );
    if( c ) this.recentSearches = c.split( Wiki.DELIM );
  
    var s = "";
    if( this.recentSearches.length == 0 ) return;

    var div1 = "<div onclick='SearchBox.doSearch(this)'>";
    var div2 = "</div>";
  
    var s = "Recent Searches:"; 
    var t = [];
    s += div1 + this.recentSearches.join( div2+div1 ) + div2;
    s += "<br /><div onclick='SearchBox.clearRecentSearches()'>" +
         "Clear Recent Searches".localized()+"</div>";
    this.recentSearchesDIV.innerHTML = s;
  } ,

  doSearch : function ( searchDiv )
  {
    this.searchForm.query.value = searchDiv.innerHTML; //nodeValue seems not to work
    this.searchForm.submit();
  } ,

  clearRecentSearches : function()
  {
    document.setCookie( "JSPWikiSearchBox", "" );
    this.recentSearches = new Array();
    this.recentSearchesDIV.innerHTML = "";
  } ,

  navigation : function( url, pagename )
  {
    var s = SearchBox.searchForm.query.value;
    if( s == 'Search' ) s = '';
    if( s == '' ) s = pagename ;
    //fix suggested by Dan Frankowski
    if( s != '' ) location.href = url.replace('__PAGEHERE__', s);
    return(false); //dont exec the click on the <a href=#>
  } ,
  
  clone : function( url, pagename )
  {
    var s = SearchBox.searchForm.query.value;
    if( s == 'Search' ) s = '';
    if( s == '' ) s = prompt( "Clone new page as ".localized() , 
                              pagename + "-new" );
    if( (s != null) && (s != '') ) 
      location.href = url.replace( '__PAGEHERE__', s+"&clone="+pagename );
    return(false); //dont exec the click on the <a href=#>
  }
}


/**
 ** 150 GraphBar Object : also used on the findpage
 ** %%graphBars ... %%
 ** convert numbers inside %%gBar ... %% tags to graphic horizontal bars
 ** no img needed.
 ** supported parameters: bar-color and bar-maxsize
 ** e.g. %%graphBars-e0e0e0 ... %%  use color #e0e0e0, default size 120
 ** e.g. %%graphBars-blue-red ... %%  blend colors from blue to red
 ** e.g. %%graphBars-red-40 ... %%  use color red, maxsize 40 chars
 ** e.g. %%graphBars-vertical ... %%  vertical bars
 ** e.g. %%graphBars-progress ... %%  progress bars in 2 colors
 ** e.g. %%graphBars-gauge ... %%  gauge bars in gradient colors
 **/
var Colors = 
{
  HTMLColors : 
  { black  :"000000", green :"008000", silver :"C0C0C0", lime  :"00FF00",
    gray   :"808080", olive :"808000", white  :"FFFFFF", yellow:"FFFF00",
    maroon :"800000", navy  :"000080", red    :"FF0000", blue  :"0000FF",
    purple :"800080", teal  :"008080", fuchsia:"FF00FF", aqua  :"00FFFF" },
  

  gradient: function( percent, from, to )
  {
    if( percent < 0 ) percent = 0;
    if( percent > 1 ) percent = 1;

    var r = to[0]-from[0], g = to[1]-from[1], b = to[2]-from[2];
    
    return this.asHTML( [from[0]+r*percent, from[1]+g*percent, from[2]+b*percent] );
  } ,

  invert: function( color )
  {
    color = this.parse( color ); if( !color ) return null;
    
    return( [ 255-color[0], 255-color[1], 255-color[2] ] );
  } ,


  /* Convert html-code to Array(r,g,b)  EG: 00FFFF = Array(0,255,255) */
  REparseColor:  new RegExp( "^[0-9a-fA-F]+$" ),
  parse : function( c )
  {
    if( c instanceof Array) return( c ); //already done??
    if( c.charAt(0) == '#' ) c = c.substr(1); 
    if( this.HTMLColors[ c.toLowerCase() ] ) c = this.HTMLColors[ c.toLowerCase() ];
    if( !this.REparseColor.test(c) ) return null;

	if( c.length == 3 ) c = c.replace(/(.)(.)(.)/, "$1$1$2$2$3$3");
    var r = parseInt( c.substr(0,2), 16 );
    var g = parseInt( c.substr(2,2), 16 );
    var b = parseInt( c.substr(4,2), 16 );
	return new Array( r, g, b );  
  } ,
  
  /* Convert Array(r,g,b) to string  EG: [0,255,255]    = #00FFFF */
  /* Convert rgb(r,g,b)   to string  EG: rgb(0,255,255) = #00FFFF */
  /* Convert ffff00       to string  EG: '00FFFF'       = #00FFFF */
  RErgbFormat : new RegExp( "rgb\\((\\d+),\\s*(\\d+),\\s*(\\d+)\\)","i"),
  _array2hex: function( arr )
  {
    s=[];
    for( i = 0; i<arr.length; i++ ) 
    {
      var v = Math.round( arr[i] ).toString( 16 );
      if( v.length < 2)  s.push('0');
      s.push(v);
    }
    return s.join('');
  } ,
  asHTML: function ( c )
  {
    var result = '';

    if( c instanceof Array) 
    { 
      result = this._array2hex( c.slice(0,3) );
    }
    else if( this.RErgbFormat.test( c ) )
    {
      result = this._array2hex( [RegExp.$1, RegExp.$2, RegExp.$3] );
    }
    else
    {
      result = c;
    }
    if( this.REparseColor.test(result) ) result = '#' + result;
    return result;
  }    
}

var GraphBar = 
{
  REclassName : new RegExp( "(?:^| )graphBars([^-]*)(-\\S+)?" ),
  
  onPageLoad  : function()
  {
    var g = document.getElementsByClassName( GraphBar.REclassName ); if( !g ) return;

    for( var i=0; i < g.length; i++ )
    {
      /* parse parameters */
      var lbound = 20;     //max - lowerbound size of bar
      var ubound = 320;    //min - upperbound size of bar
      // max value as parameter - to overrule automatic max calcul
      // min value - to overrule automatic min calcul
      var color1 = null; // bar color
      var color2 = null; // 2nd bar color used depending on bar-type
      var colorTable = []; //color value of each bar
      var isHorizontal = true;   // orientation horizontal or vertical
      var isProgress   = false;  // progress bar
      var isGauge      = false;  // gauge bar
      
      GraphBar.REclassName.test( g[i].className );
      var barName = RegExp.$1; 
      var parms = RegExp.$2.toLowerCase().split('-');
      for( var pi=0; pi < parms.length; pi++ )
      {   
        p = parms[pi];
        if( p == "vertical")             { isHorizontal = false; }
        else if( p == "progress")        { isProgress = true;  }
        else if( p == "gauge")           { isGauge = true; }
        else if( p.indexOf("min") == 0 ) { lbound = parseInt( p.substr(3), 10 ); }
        else if( p.indexOf("max") == 0 ) { ubound = parseInt( p.substr(3), 10 ); }
        else
        {
          p = Colors.parse( p );
          if( !color1 )                  { color1 = p; }
          else if( !color2 )             { color2 = p; }
        }
      }
      if( lbound > ubound ) { var m = ubound; ubound=lbound; ubound=m;  }
      if( isProgress && !color1 ) color2 = "transparent";
      if( (isGauge || isProgress)  && color1 && !color2 ) color2 = Colors.invert( color1 );
      if( color1 && !color2 ) color2 = color1;

      /* collect all gBar elements */
      var bars = getElementsByClassName( g[i], "gBar"+barName ); 
      if( !bars && barName && (barName!="") )  // check for matching table columns or rows
      {
        bars = this.getTableValues( g[i], barName );
      }
      if( !bars ) continue;
  
      /* parse bar data and scale according to lbound an ubound */
      var barData = this.parseBarData( bars, lbound, ubound );

      /* modify DOM for each bar element */
      var e = document.createElement( "span" );
      e.className = "graphBar"; 
      e.innerHTML = "&nbsp;"; 

      //alert( color1+" " +color2);
      for( var j=0; j < bars.length; j++ )
      {
        var b  = bars[j];           //gBar element
        var pb = b.parentNode;      //parent of gBar element
        var n  = e.cloneNode(true); //principal bar to be inserted
        var nn = null;              //2nd bar to be inserted ico progress
        var c  = null;              //principal bar color

        if( color1 )
        {
          var percent = ( j / (bars.length-1) );
          if( isGauge ) percent = (barData[j]-lbound) / (ubound-lbound);
          c = Colors.gradient( percent, color1, color2 ); //colorTable[gg]; //get color value
        }
 
        if( isHorizontal )
        {
          n.style.borderLeftStyle = "solid";
          n.style.borderLeftWidth = barData[j] + "px";

          if( isProgress ) 
          { 
            nn = n.cloneNode(true);
            nn.style.paddingRight = "0";
            if( color1 ) nn.style.borderLeftColor = Colors.asHTML( color1 );

            n.style.borderLeftWidth = (ubound - barData[j]) + "px";
            if( color2) n.style.borderLeftColor = Colors.asHTML( color2 );
            n.style.marginLeft = "-1ex";  //overlap &nbsp; char
          }
          else 
          { 
            if( c ) n.style.borderLeftColor = c; 
          } 
          
        }
        else /* vertical bars */
        {
          n.style.borderBottomStyle = "solid";
          n.style.borderBottomWidth = barData[j] + "px";
          b.style.position = "relative"; //needed for inserted spans ;-)) hehe

          // behaviour of relative on a TD is undefined - so insert a help div block
          // see http://www.w3.org/TR/CSS21/visuren.html#propdef-position ;-(
          if( pb.nodeName == 'TD' ) { pb = encapsulateChildren( pb, "div" );  } 
                    
          var fontsize = 20;  //can we read this info somewhere ? nono
          pb.style.height = (ubound + fontsize) + "px";           
          pb.style.position = "relative";  

          if( !isProgress ) { b.style.top = (ubound - barData[j]) + "px"; }

          n.style.position = "absolute";
          n.style.bottom   = "0px";          
          n.style.width    = "1em";  //can you retrieve the width of b ? nono

          if( isProgress ) 
          { 
            if( color1 ) n.style.borderBottomColor = Colors.asHTML( color1 );

            nn = n.cloneNode(true); //first insert 'background' bar with color1
            nn.style.borderBottomWidth = ubound + "px";
            if( color2) nn.style.borderBottomColor = Colors.asHTML( color2 );
          }
          else
          { 
            if( c ) n.style.borderBottomColor = c; 
          }
          
        }

        if( nn ) pb.insertBefore( nn, b );
        pb.insertBefore( n, b ); 

      }
      e = null; // avoid ie memory leak      
    }
  } ,

  parseBarData : function( nodes, lbound, ubound )
  {
    var barData = [], count = nodes.length; maxValue = Number.MIN_VALUE, minValue = Number.MAX_VALUE;    
    var span = ubound - lbound;

    var isnum = true;
    var isdate = true;
    
    for( var j=0; j < count; j++ )
    {
      var s = getNodeText( nodes[j] );
      barData[j] = s;      
      if( isnum  ) isnum  = !isNaN( parseFloat( s.match( Number.REparsefloat ) ) );    
      if( isdate ) isdate = !isNaN( Date.parse( s ) );    
    }


    for( var j=0; j < count; j++ )
    {
      var k = barData[j];
      if( isdate )     { k = new Date( Date.parse( k ) ).valueOf();  }
      else if( isnum ) { k = parseFloat( k.match( Number.REparsefloat ) ); } 
      
      maxValue = Math.max( maxValue, k ); 
      minValue = Math.min( minValue, k );
      barData[j] = k;
    }

    for( var j=0; j < count; j++ ) /* scale values */
    {
      barData[j] = parseInt( (span * (barData[j]-minValue) / (maxValue-minValue) ) + lbound ) ;
    }  
    return barData;    
  } ,
  
  /*
   * Fetch set of gBar values from a table
   * Check first-row to match field-name: return array with col values
   * Check first-column to match field-name: return array with row values
   * insert SPANs qs place-holder of the missing gBars
   */
  getTableValues : function( node, fieldName )
  {
    var table = node.getElementsByTagName( "table" )[0]; if( !table ) return null;
    var tlen = table.rows.length;

    if( tlen > 1 ) /* check for COLUMN based table */
    {
      var r = table.rows[0];    
      for( var h=0; h < r.cells.length; h++ )
      {
        if( getNodeText( r.cells[h] ).trim() == fieldName ) 
        {
          var result = [];
          for( var i=1; i< tlen; i++)  
            result.push( encapsulateChildren( table.rows[i].cells[h], "span") );
          return result;
        }
      }
    }

    for( var h=0; h < tlen; h++ )  /* check for ROW based table */
    {
      var r = table.rows[h];
      if( getNodeText( r.cells[0] ).trim() == fieldName )
      {
        var result = [];
        for( var i=1; i< r.cells.length; i++)  
          result.push( encapsulateChildren( r.cells[i], "span" ) );
        return result;
      }
    }    

    return null;  
  }

}

/**
 ** 200 Collapsable list items 
 **
 ** See also David Lindquist <first name><at><last name><dot><net>
 ** See: http://www.gazingus.org/html/DOM-Scripted_Lists_Revisited.html
 **
 ** Add stuff to support collabsable boxes, Nov 05, D.Frederickx
 **
 **/
var Collapsable = 
{
  tmpcookie    : null,
  cookies      : [],
  cookieNames  : [],

  ClassName        : "collapse",
  ClassNameBox     : "collapsebox",
  REClassNameBox   : new RegExp( "(?:^| )collapsebox(-closed)?" ),
  ClassNameBody    : "collapsebody",
  CollapseID       : "clps", //prefix for unique IDs of inserted DOM nodes
  MarkerOpen       : "O",    //cookie state chars 
  MarkerClose      : "C", 
  CookiePrefix     : "JSPWikiCollapse",

  onPageLoad : function()
  {  
    this.OpenTip  = "Click to collapse".localized();
    this.CloseTip = "Click to expand".localized();

    this.bullet = document.createElement("div"); // template bullet node
    this.bullet.className = "collapseBullet";
    this.bullet.innerHTML = "&bull;";

    this.initialise( "favorites",   this.CookiePrefix + "Favorites" );
    this.initialise( "pagecontent", this.CookiePrefix + Wiki.getPageName() );  
    //this.initialise( "pageinfo",    this.CookiePrefix + "PageInfo" );
  } ,
  
  initialise : function( domID, cookieName )
  {
    var page  = $( domID );  if( !page ) return;
    this.tmpcookie = document.getCookie( cookieName );
    this.cookies.push( "" ) ; //initialise new empty collapse cookie
    this.cookieNames.push( cookieName );
  
    var nodes;
    nodes = getElementsByClassName( page, this.ClassName ); 
    if( nodes ) 
    { 
      for( var i=0; i < nodes.length; i++)  this.collapseNode( nodes[i] );
    }
    nodes = getElementsByClassName( page, this.REClassNameBox );
    if( nodes )   
    { 
      for( var i=0; i < nodes.length; i++)  this.collapseBox( nodes[i] );
    }    
  } ,
  
  REboxtitle : new RegExp ( "h2|h3|h4" ) ,
  collapseBox : function( node )
  {
    var title = node.firstChild; 
    while( (title != null) && (!this.REboxtitle.test( title.nodeName.toLowerCase() )) )
    {
      title = title.nextSibling;
    }
    if( !title ) return;  
    if( !title.nextSibling ) return;
    
    var body = document.createElement( "div" );
    body.className = this.ClassNameBody;
    while( title.nextSibling ) body.appendChild( title.nextSibling );
    node.appendChild( body );    

    var isClosed = ( node.className.indexOf("-closed") > 0 ); 
    node.className = node.className.replace( this.REClassNameBox, this.ClassNameBox ) ;
 
    var bullet  = this.bullet.cloneNode(true);
    this.initBullet( bullet, body, (isClosed) ? this.MarkerClose : this.MarkerOpen );
    title.appendChild( bullet );
  } ,
  
  // Modifies the list such that sublists can be hidden/shown by clicking the listitem   bullet
  // The listitem bullet is a node inserted into the DOM tree as the first child of the 
  // listitem containing the sublist.
  collapseNode : function( node )
  {
    var items = node.getElementsByTagName("li");
    for( i=0; i < items.length; i++ )
    {
      var nodeLI = items[i];
      var nodeXL = ( nodeLI.getElementsByTagName("ul")[0] || 
                     nodeLI.getElementsByTagName("ol")[0] ); 
  
      //dont insert bullet when LI is "empty" -- iow it has no text or no non ulol tags inside
      //eg. * a listitem
      //    *** a nested list item - intermediate level is empty
      var emptyLI = true;
      for( var n = nodeLI.firstChild; n ; n = n.nextSibling )
      {
        if((n.nodeType == 3 ) && ( n.nodeValue.trim() == "" ) ) continue; //keep searching
        if((n.nodeName == "UL") || (n.nodeName == "OL")) break; //seems like an empty li 
        emptyLI = false; 
        break;
      }
      if( emptyLI ) continue; //do not insert a bullet
  
      var bullet  = this.bullet.cloneNode(true);
      
      if( nodeXL )
      {
        var defaultState = (nodeXL.nodeName == "UL") ? this.MarkerOpen : this.MarkerClose ;
        this.initBullet( bullet, nodeXL, defaultState );
      }
      nodeLI.insertBefore( bullet, nodeLI.firstChild ); 
    }
  } ,
  
  
  // initialise bullet according to parser settings
  initBullet : function( bullet, body, defaultState )
  {
    var collapseState = this.parseCookie( defaultState ); 
    bullet.onclick = this.toggleBullet;
    bullet.id = this.CollapseID + "." + (this.cookies.length-1) + 
                                  "." + (this.cookies.last().length-1);
    this.setOpenOrClose( bullet, ( collapseState == this.MarkerOpen ), body );
  } ,
  
  // modify dom-node according to the setToOpen flag
  setOpenOrClose : function( bullet, setToOpen, body )
  {
    bullet.innerHTML   = (setToOpen) ? "&raquo;"      : "&laquo;" ;
    bullet.className   = (setToOpen) ? "collapseOpen" : "collapseClose" ;
    bullet.title       = (setToOpen) ? this.OpenTip   : this.CloseTip ;
    body.style.display = (setToOpen) ? "block"        : "none" ;
  } ,
      
  // parse cookie 
  // this.tmpcookie contains cookie being validated agains the document
  // this.cookies.last contains actual cookie being constructed
  //    this cookie is stored in the cookies[] 
  //    and only persisted when the user opens/closes something
  // returns collapseState MarkerOpen, MarkerClose
  parseCookie : function( token )
  {
    var currentcookie = this.cookies.last();
    var cookieToken = token; //default value
  
    if( (this.tmpcookie) && (this.tmpcookie.length > currentcookie.length) )
    {
      cookieToken = this.tmpcookie.charAt( currentcookie.length );
      if(  ( (token == this.MarkerOpen) && (cookieToken == this.MarkerClose) ) 
        || ( (token == this.MarkerClose) && (cookieToken == this.MarkerOpen) ) ) //##fixed
          token = cookieToken ; 
      if( token != cookieToken )  //mismatch between tmpcookie and expected token
          this.tmpcookie = null;
    }   
    this.cookies[this.cookies.length - 1] += token; //append and save currentcookie
  
    return( token );    
  } ,
  
  // toggle bullet and update corresponding cookie
  // format of ID of bullet = "collapse.<cookies-index>.<cookie-charAt>"
  toggleBullet : function( )
  {
    var ctx = Collapsable; //avoid confusion with this == clicked bullet
  
    var idARR  = this.id.split(".");  if( idARR.length != 3 ) return;
    var cookie = ctx.cookies[idARR[1]]; // index in cookies array
  
    var body;
    if( ctx.REboxtitle.test( this.parentNode.nodeName.toLowerCase() ) )
    {
      body = this.parentNode.nextSibling;
    } 
    else
    {
      body = ( this.parentNode.getElementsByTagName("ul")[0] || 
               this.parentNode.getElementsByTagName("ol")[0] ); 
    }
    if( !body ) return;
  
    ctx.setOpenOrClose( this, (body.style.display == "none"), body );
    
    var i = parseInt(idARR[2]); // position inside cookie
    var c = ( cookie.charAt(i) == ctx.MarkerOpen ) ? ctx.MarkerClose : ctx.MarkerOpen; 
    cookie = cookie.substring(0,i) + c + cookie.substring(i+1) ;
  
    document.setCookie( ctx.cookieNames[idARR[1]], cookie );
    ctx.cookies[idARR[1]] = cookie;
  
    return false;  
  }
}  

/**
 ** 220 RoundedCorners --experimental
 ** based on Nifty corners by Allesandro Fulciniti
 ** www.pro.html.it
 ** Refactored for JSPWiki
 **
 ** JSPWiki syntax:
 **
 **  %%roundedCorners-<corners>-<color>-<borderColor>
 **  %%
 **
 **  roundedCorners-yyyy-ffc5ff-c0c0c0
 **
 **  corners : "yyyy" where first y: top-left,    2nd y: top-right, 
 **                           3rd y: bottom-left; 4th y: bottom-right 
 **     value can be : "y" : Normal rounded corner (lowercase y)
 **                    "s" : Small rounded corner (lowercase s)
 **                    "n" : Normal square corner
 **
 **/

var RoundedCorners = 
{
  REclassName : new RegExp( "(?:^| )roundedCorners(-\\S+)?" ),

  /** Definition of CORNER dimensions 
   ** Normal    Normal+Border  Small  Small+Border
   ** .....+++  .....BBB       ..+++  ..BBB
   ** ...+++++  ...BB+++       .++++  .B+++
   ** ..++++++  ..B+++++       +++++  B++++
   ** .+++++++  .B++++++
   ** .+++++++  .B++++++
   ** ++++++++  B+++++++
   **
   ** legend: . background, B border, + forground color
   **/
  NormalTop : 
     [ { margin: "5px", height: "1px", borderSide: "0", borderTop: "1px" }
     , { margin: "3px", height: "1px", borderSide: "2px" }
     , { margin: "2px", height: "1px", borderSide: "1px" } 
     , { margin: "1px", height: "2px", borderSide: "1px" }
     ] ,
  SmallTop :  
     [ { margin: "2px", height: "1px", borderSide: "0", borderTop: "1px" }
     , { margin: "1px", height: "1px", borderSide: "1px" }
     ] ,
  //NormalBottom : see onPageLoad()
  //SmallBottom : see onPageLoad()
  
  /**
   ** Usage:
   ** RoundedCorners.register( "header", ['yyyy', '00f000', '32cd32'] );
   **/
  registry : {},
  register : function( selector, parameters )
  {
    this.registry[selector] = parameters;
  } ,
  
  onPageLoad : function()
  {  
    /* make reverse copies for bottom definitions */
    this.NormalBottom = this.NormalTop.slice(0).reverse();
    this.SmallBottom  = this.SmallTop.slice(0).reverse();
    

    for( selector in this.registry )
    {
      //var n = document.getElementsBySelector( selector );
      var n = $( selector );
      var parms = this.registry[selector];
      this.exec( [n], parms[0], parms[1], parms[2], parms[3] );
    }
    
    var p = $( "pagecontent" );       if( !p ) return;
    var r = getElementsByClassName ( p, this.REclassName ); if( !r ) return;
  
    for( var i=0; i < r.length; i++ )
    {
      this.REclassName.test( r[i].className );
      var parms = RegExp.$1.split('-');
      if( parms.length < 2 ) continue;
      this.exec( [r[i]], parms[1], parms[2], parms[3], parms[4] );
    }
  } ,
  
  exec : function( nodes, corners, color, borderColor, background )
  {
    corners = ( corners ? corners+"nnnn" : "yyyy" );
    color   = ( color ? Colors.asHTML(color) : "transparent" );
    if( borderColor ) borderColor = Colors.asHTML( borderColor );
    if( background  ) background = Colors.asHTML( background );
  
    var c = corners.split(''); 
    /* [0]=top-left; [1]=top-right; [2]=bottom-left; [3]=bottom-right; */
  
    var nodeTop = null;
    var nodeBottom = null; 
    
    if( c[0]+c[1] != "nn" )  //add top rounded corners
    {
      nodeTop = document.createElement("b") ;
      nodeTop.className = "roundedCorners" ;
  
      if( (c[0] == "y") || (c[1] == "y") ) 
      {
        this.addCorner( nodeTop, this.NormalTop, c[0], c[1], color, borderColor );
      }
      else if( (c[0] == "s") || (c[1] == "s") )
      {
        this.addCorner( nodeTop, this.SmallTop, c[0], c[1], color, borderColor );    
      }
    }
  
    if( c[2]+c[3] != "nn" ) //add bottom rounded corners
    {
      nodeBottom = document.createElement("b"); 
      nodeBottom.className = "roundedCorners";
  
      if( (c[2] == "y") || (c[3] == "y") )
      {
        this.addCorner( nodeBottom, this.NormalBottom, c[2], c[3], color, borderColor );
      }
      else if( (c[2] == "s") || (c[3] == "s") ) 
      {
        this.addCorner( nodeBottom, this.SmallBottom, c[2], c[3], color, borderColor );    
      }
    }
    
    if( (!nodeTop) && (!borderColor) && (!nodeBottom) ) return; 
    
    for( var i=0; i<nodes.length; i++)
    {
      if( !nodes[i] ) continue;
      this.addBody( nodes[i], color, borderColor );
      if( nodeTop     )  nodes[i].insertBefore( nodeTop.cloneNode(true), nodes[i].firstChild );
      if( nodeBottom  )  nodes[i].appendChild( nodeBottom.cloneNode(true) );
    }
  } ,
      
  addCorner : function( node, arr, left, right, color, borderColor )
  {
    for( var i=0; i< arr.length; i++ )
    {
      var n =  document.createElement("div");
      n.style.height = arr[i].height;
      n.style.overflow = "hidden";
      n.style.borderWidth = "0";
      n.style.backgroundColor = color;
   
      if( borderColor ) 
      { 
        n.style.borderColor = borderColor;
        n.style.borderStyle = "solid";
        if(arr[i].borderTop)
        {
          n.style.borderTopWidth = arr[i].borderTop;
          n.style.height = "0";
        }
      }
  
      if( left != 'n' ) n.style.marginLeft = arr[i].margin;
      if( right != 'n' ) n.style.marginRight = arr[i].margin;
      if( borderColor ) 
      {
        n.style.borderLeftWidth  = ( left  == 'n' ) ? "1px" : arr[i].borderSide;    
        n.style.borderRightWidth = ( right == 'n' ) ? "1px" : arr[i].borderSide;
      }
      node.appendChild( n );
    }
  } ,

  // move all children of the node inside a DIV and set color and bordercolor 
  addBody : function( node, color, borderColor)
  {
    if( node.passed ) return;
      
    var container = encapsulateChildren( node, "div" );
    
    container.style.padding = "0 4px";
    container.style.backgroundColor = color;
    if( borderColor )  
    {
      container.style.borderLeft  = "1px solid " + borderColor;
      container.style.borderRight = "1px solid " + borderColor;
    }
  
    node.passed=true;  
  }
}


/**
 ** 230 Sortable -- for all tables
 **/
var Sortable = 
{
  ClassName           : "sortable",
  ClassSort           : "sort",
  ClassSortAscending  : "sortAscending",
  ClassSortDescending : "sortDescending",
  
  onPageLoad : function()
  {
    this.TitleSort           = "Click to sort".localized();
    this.TitleSortAscending  = "Ascending order".localized();
    this.TitleSortDescending = "Descending order".localized();

    var sortables = document.getElementsByClassName( Sortable.ClassName );  if( !sortables ) return;
    for( i=0; i<sortables.length; i++ )
    {
      var table = sortables[i].getElementsByTagName( "table" )[0];
      if( !table ) continue;
      if( table.rows.length < 2 ) continue;
    
      for( var j=0; j < table.rows[0].cells.length; j++ )
      {
        var c = table.rows[0].cells[j];
        if( c.nodeName != "TH" ) break;
        c.onclick    = function() { Sortable.sort(this); } ;
        c.title      = this.TitleSort;
        c.className += " " + this.ClassSort;
      }
    }
  } ,  
  
  REclassName : new RegExp ('(?:^| )(sort|sortAscending|sortDescending)(?: |$)'), 

  sort : function( thNode )
  {
    var table = getAncestorByTagName(thNode, "table" ); if( !table ) return;
    var filter = (table.filterStack != undefined);
    if( table.tBodies[0] ) table = table.tBodies[0];
    if( table.rows.length < 2 ) return;
    var colidx = 0; //target column to sort
    var thNodeClassName = this.ClassSort; //default column header classname
    
    //validate header row
    for( var i=0; i < table.rows[0].cells.length; i++ )
    {
      var c = table.rows[0].cells[i];
      if( c.nodeName != "TH" ) return;
      
      if( thNode == c ) 
      { 
        colidx = i; 
        if( Sortable.REclassName.test(c.className) ) thNodeClassName = RegExp.$1; 
      }
      else
      {
        c.className = c.className.replace(Sortable.REclassName, "" ) + " " + this.ClassSort ;
        c.title = this.TitleSort;
      }
    } 
    
    //find body rows and guess data type of colidx
    var rows = new Array();
    var num  = true;
    var date = true;
    for( var i=1; i< table.rows.length; i++)
    {
      if( (i==1) && (filter) ) continue;
      //rows[i-1] = table.rows[i] ;
      rows.push( table.rows [i] );
      //var val = table.rows[i].cells[colidx].firstChild.nodeValue;
      //var val = getNodeText( rows[i-1].cells[colidx] );
      var val = getNodeText( table.rows[i].cells[colidx] );      
      if( num  ) num  = !isNaN( parseFloat( val.match( Number.REparsefloat ) ) );    
      if( date ) date = !isNaN( Date.parse( val ) );    
    }
    var datatype = "string";
    if( num ) datatype = "num";
    if( date ) datatype = "date";
  
    //do the actual sorting
    if( thNodeClassName == this.ClassSort ) //first time sort of column table.sortCol == colidx ) 
    {
      rows.sort( Sortable.createCompare( colidx, datatype ) );
      thNodeClassName = this.ClassSortAscending;
      thNode.title    = this.TitleSortAscending; 
    }
    else
    { 
      rows.reverse(); 
      if( thNodeClassName == this.ClassSortAscending )
      {
        thNodeClassName = this.ClassSortDescending;
        thNode.title    = this.TitleSortDescending;
      }
      else
      {
        thNodeClassName = this.ClassSortAscending;
        thNode.title    = this.TitleSortDescending;
      }
    }
    thNode.className = thNode.className.replace(Sortable.REclassName, "") + " " + thNodeClassName ;
    
    //put the sorted table back into the document
    var frag = document.createDocumentFragment();
    for( var i=0; i < rows.length; i++ )
    {
      frag.appendChild( rows[i] );
    }
    table.appendChild( frag );
  } ,
  
  convert : function( val, datatype )
  {
    switch( datatype )
    {
      case "num"  : return parseFloat( val.match( Number.REparsefloat ) ); 
      case "date" : return new Date( Date.parse( val ) );
      default     : return val.toString();
    }
  } ,
  
  createCompare : function( colidx, datatype )
  {
    return function(row1, row2)
    {
      var val1 = Sortable.convert( getNodeText(row1.cells[colidx]), datatype );
      var val2 = Sortable.convert( getNodeText(row2.cells[colidx]), datatype );
  
      if     ( val1 < val2 ) { return -1; }
      else if( val1 > val2 ) { return 1;  }
      else { return 0; }
    } 
  }
}
  
/**
 ** 240 table-filters
 ** inspired by http://www.codeproject.com/jscript/filter.asp
 ** Dirk Frederickx, Jan 06
 **/
var TableFilter =
{
  ClassName : "table-filter",
  FilterRow : 1,
  
  onPageLoad : function()
  {
    this.FilterAll = "( All )".localized();
  
    var t = document.getElementsByClassName( this.ClassName );  if( !t ) return;
    for( i=0; i < t.length; i++ )
    {
      var table = t[i].getElementsByTagName( "table" )[0];
      if( !table ) continue;
      if( table.rows.length < 2 ) continue;
    
      var r = table.insertRow( TableFilter.FilterRow );
      for( var j=0; j < table.rows[0].cells.length; j++ ) //bugfix miltiple filtertables
      {
        var c = document.createElement("TH");
        var select = document.createElement("select");
        select.onchange = TableFilter.filter;
        select.fCol     = j; //store index
        c.appendChild(select);
        r.appendChild(c);
      }
      table.filterStack = new Array(); 
      TableFilter.buildEmptyFilters( table );
    }  
  } ,
  
  // this function initialises the not filtered columns
  buildEmptyFilters : function( table )
  {
    for( var i=0; i < table.rows[0].cells.length; i++ )
    {
      if( !this.isFiltered( table, i ) ) this.buildFilter( table, i );
    }
  } ,
  
  // check filterStack whether this column is filtered or not
  isFiltered : function( table, col )
  {
    for( var i=0; i < table.filterStack.length; i++ )
    {
      if( table.filterStack[i].fCol == col ) return true;
    }
    return false;  
  } ,
  
  // this function initialises a column filter
  // if the selectedValue is not supplied, the first value is taken
  buildFilter : function( table, col, selectedValue )
  {
    // Get a reference to the dropdownbox.
    var select = table.rows[this.FilterRow].cells[col].firstChild;
    
    // remove all existing items
    while( select.length > 0 )  select.remove( 0 );
    
    var values = new Array();
    var num  = true;
    var date = true;
    for(var i = this.FilterRow+1; i < table.rows.length; i++ )
    {
      var row = table.rows[i];
      if( row.style.display != "none" )
      {
        //var val = row.cells[col].innerHTML.toLowerCase();
        var val = getNodeText(row.cells[col]).toLowerCase();
        if( num  ) num  = !isNaN( parseFloat( val ) ) ;    
        if( date ) date = !isNaN( Date.parse( val ) );    
        values.push( val );
      }
    }
    var datatype = "string";
    if( num ) datatype = "num";
    if( date ) datatype = "date";
  
    values.sort( TableFilter.createCompare( datatype ) );
    
    //add each unique string to the dopdownbox
    select.options[0]= new Option( this.FilterAll, this.FilterAll ) ;
    var value;
    for( var i = 0; i < values.length; i++ )
    {
      if( values[i].toLowerCase() != value )
      {
        value = values[i].toLowerCase();
        select.options[select.options.length] = new Option( values[i], value ) ;
      }
    }
  
    if( selectedValue != undefined )
    {
      select.value = selectedValue;
    }
    else
    {
      select.options[0].selected = true;
    }
  } ,
  
  // create compare funtion for the sort
  // reuse Sortable datatype convertion routine
  createCompare : function( datatype )
  {
    return function( v1, v2)
    {
      var val1 = Sortable.convert( v1, datatype );
      var val2 = Sortable.convert( v2, datatype );
  
      if     ( val1 < val2 ) { return -1; }
      else if( val1 > val2 ) { return 1;  }
      else { return 0; }
    } 
  } ,
  
  showAll : function( table )
  {
    for( var i = this.FilterRow + 1; i < table.rows.length; i++ )
    {
      table.rows[i].style.display = "";
    }
  } ,
  
  // called when the dropdown(=this) is changed
  filter : function()
  {
    var col   = this.fCol; 
    var value = this.value;
    var table = getAncestorByTagName( this, "table" ); 
    if( !table ) return;
    if( table.style.display == "none" ) return;
  
    // First check if the column is allready in the filter.
    var bFound = false;  
    for( var i = 0; i < table.filterStack.length; i++ )
    {
      if( table.filterStack[i].fCol == col)
      {
        bFound = true;
        if( value == TableFilter.FilterAll ) //remove column from filter
        {
          table.filterStack.splice( i, 1 );
        }
        else
        {
          table.filterStack[i].fValue = value;
        }
        break;
      }
    } 
    // push new column on the filterStack
    if( !bFound )  table.filterStack.push( { fValue:value, fCol:col } ) ;
    
    TableFilter.showAll( table );    
  
    // now filter the right rows
    for( var i = 0; i < table.filterStack.length; i++ )
    {
      var icol   = table.filterStack[i].fCol;
      var ivalue = table.filterStack[i].fValue;
  
      TableFilter.buildFilter( table, icol, ivalue ); //fill dropdown of this column
      //apply filter
      for( var j = TableFilter.FilterRow + 1; j < table.rows.length; j++ )
      {
        var row = table.rows[j];
        if( ivalue != getNodeText(row.cells[icol]).toLowerCase() )
        {
          row.style.display = "none";
        }
      }
    }
  
    TableFilter.buildEmptyFilters( table ); //fill remaining dropdowns
  }
}


/**
 ** 250 Categories : turn wikipage link into AJAXed popup *new*
 ** Uses prototype.js library
 **/
var Categories = 
{
  idx :        0, // generate unique popup ids
  ClassName:   "category",
  templateDir: "/AJAXCategories.jsp",

  onPageLoad: function ()
  {
    // added to commonheader.jsp -- fixme
    var t = $( "template-dir" ); if( !t || !t.href ) return; 
    this.templateDir = t.href + this.templateDir;
  
    var p = $( "pagecontent" ); if( !p ) return; 
    var cats = getElementsByClassName( p, this.ClassName );  if( !cats ) return;
    for( i=0; i<cats.length; i++ )
    {   
      var links = cats[i].getElementsByTagName( "a" ); if( !links ) continue;
      for( l=0; l<links.length; l++ )
      {
        var link = links[l];
        if( link.className == "wikipage" ) this.activateLink( link );
      }
    }
  } ,

  activateLink: function( link )
  {
    var page = Wiki.href2pagename( link.href ) ;
    
    var container = document.createElement( "span" );
    link.parentNode.insertBefore( container, link );
    container.appendChild( link );
    
    var popup = document.createElement("div");
    popup.id  = "categoryPopup" + Categories.idx++;
    popup.className = "categoryPopup" ;
    Element.hide( popup ) ;
    container.appendChild( popup );

    Element.addClassName( link, "categoryLink" );
    link.href    = "#";
    link.title   ="Show category [" + page + "] ...";
    link.onclick = function() 
    {
      new Ajax.Updater
      ( popup.id, 
        Categories.templateDir,
        { 
          asynchronous: true,
          parameters: "&page=" + page,
          onComplete: function() 
          { 
            link.title = "";
            container.onmouseover = function(e) 
            { 
              Element.show( popup ); 
            } ;
            container.onmouseout = function() 
            { 
              Element.hide( popup ); 
            } ; 
            popup.onclick = Prototype.emptyFunction; 
            popup.style.left = Position.cumulativeOffset( link )[0] + "px";
            Element.show( popup ); 
          }  
        } ) ;
    } ;
  } 
}

//fixme!!!
Wiki.href2pagename = function( href )
{
  var p=href;   
  var reMatchPageParm = new RegExp ( "\\?page=([^&]+)" );

  if( reMatchPageParm.test( href) ) return RegExp.$1;
  
  //alert( Wiki.BaseURL + "\n" + Wiki.BasePath + "\n" + pageURL);
  //todo
  
  return p;
}


/**
 ** 280 ZebraTable
 ** Color odd/even rows of table differently
 ** 1) odd rows get css class odd (ref. jspwiki.css )
 **   %%zebra-table ... %%
 **
 ** 2) odd rows get css style='background=<color>'
 ** %%zebra-<odd-color> ... %%  
 **
 ** 3) odd rows get odd-color, even rows get even-color
 ** %%zebra-<odd-color>-<even-color> ... %%
 **
 ** colors are specified in HEX (without #) format or html color names (red, lime, ...)
 **
 **/
var ZebraTable = 
{
  REclassName : new RegExp( "(?:^| )zebra-(\\S+)" ),
  
  onPageLoad : function()
  {
    var z = document.getElementsByClassName ( this.REclassName ); if( !z ) return;
  
    for( var i=0; i<z.length; i++)
    {
      var rows = z[i].getElementsByTagName( "TR" );  if( !rows ) continue;
      this.REclassName.test( z[i].className );
      var parms = RegExp.$1.split('-');
  
      if( parms[0] == 'table' )
      {
        for( var r=0; r < rows.length; r+=2 ) rows[r].className += " odd";
        continue;
      }
     
      if( parms[0] )
      { 
        var c = Colors.asHTML(parms[0]);
        for( var r=2; r < rows.length; r+=2 )  rows[r].style.backgroundColor = c;
      }
      if( parms[1] )
      {
        var c = Colors.asHTML(parms[1]);
        for( var r=1; r < rows.length; r+=2 ) rows[r].style.backgroundColor = c;
      }
    }
  }
}

/**
 ** 290 Highlight Word
 **
 ** Inspired by http://www.kryogenix.org/code/browser/searchhi/ 
 ** Modified 21006 to fix query string parsing and add case insensitivity 
 ** Modified 20030227 by sgala@hisitech.com to skip words 
 **                   with "-" and cut %2B (+) preceding pages 
 ** Refactored for JSPWiki -- now based on regexp, by D.Frederickx. Nov 2005
 **
 **/
var HighlightWord = 
{
  ClassName      : "searchword" ,
  ClassNameMatch : "<span class='searchword' >$1</span>" ,
  ReQuery : new RegExp( "(?:\\?|&)(?:q|query)=([^&]*)", "g" ) ,
  
  onPageLoad : function () 
  {
    if( !this.ReQuery.test( document.referrer ) ) return;
  
    var words = decodeURIComponent(RegExp.$1);
    words = words.replace( /\+/g, " " );
    words = words.replace( /\s+-\S+/g, "" );
    words = words.replace( /([\(\[\{\\\^\$\|\)\?\*\.\+])/g, "\\$1" ); //escape metachars
    words = words.trim().split(/\s+/).join("|");
    this.reMatch = new RegExp( "(" + words + ")" , "gi");
    
    this.walkDomTree( $("pagecontent") );
  } ,
  
  // recursive tree walk matching all text nodes
  walkDomTree : function( node )
  {
    if( !node ) return; /* bugfix */
    var nn = null; 
    for( var n = node.firstChild; n ; n = nn )
    {
      nn = n. nextSibling; /* prefetch nextSibling cause the tree will be modified */
      this.walkDomTree( n );
    }
    
    // continue on text-nodes, not yet highlighted, with a word match
    if( node.nodeType != 3 ) return; 
    if( node.parentNode.className == this.ClassName ) return;
    var s = node.nodeValue;
    s = s.escapeXML(); /* bugfix - nodeValue apparently unescapes the xml entities ?! */
    if( !this.reMatch.test( s ) ) return;  
    var tmp = document.createElement("span");
    tmp.innerHTML = s.replace( this.reMatch, this.ClassNameMatch );
  
    var f = document.createDocumentFragment();
    while( tmp.firstChild ) f.appendChild( tmp.firstChild );
  
    node.parentNode.replaceChild( f, node );  
  }
}

/**
 ** OnLoad : run following stuff after the page was loaded
 **/
Event.observe( 
  window, 
  "load", 
  function()
  {
    if( !document.createElement ) return; //following stuff is DOM dependent
    Wiki.onPageLoad();
    TabbedSection.onPageLoad(); //asap
    QuickLinks.onPageLoad();
    Collapsable.onPageLoad();
    SearchBox.onPageLoad();
    Sortable.onPageLoad();
    TableFilter.onPageLoad();
    RoundedCorners.onPageLoad();
    ZebraTable.onPageLoad();
    HighlightWord.onPageLoad(); 
    GraphBar.onPageLoad();
    Categories.onPageLoad();
    URL .onPageLoad();
  } );

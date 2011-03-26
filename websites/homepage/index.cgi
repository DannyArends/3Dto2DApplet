#!/usr/bin/perl -w
use strict;
use Socket;
use CGI qw(:standard);

#Global Variables across all includes
our %form;

#include our own files & functions
require "cgi-bin/utils.cgi";

#Constants

#Variables
our $title = "DannyArends.nl";
our $subtitle = "Homepage of Danny Arends";
our $theme = "/themes/default.css";
our $email = "Danny.Arends\@gmail.com";

#Functions



#Main
# Print a http header
receivePost();
if($form{"p"} eq ""){
	$form{"p"} = "index"; 
}
printHTTPHeader();
print "
<table width='95%'>
<tr>
  <td colspan=3>
  <i>Last updated: 26 March 2011</i><br>
  [<a href=\"/Index.cgi\" title=\"The index page of dannyarends.nl\"><font color=\"black\">Home</font></a>
  |<a href=\"/myblog.cgi\" title=\"Blog by Danny Arends\"><font color=\"black\">My Blog</font></a>
  |<a href=\"/Index.cgi?p=Publications\" title=\"Overview of Publication by Danny Arends\"><font color=\"black\">Publications</font></a>
  |<a href=\"/Index.cgi?p=Presentations\" title=\"Overview of Presentations by Danny Arends\"><font color=\"black\">Presentations</font></a>
  |<a href=\"/Index.cgi?p=Drawings\" title=\"Overview of Drawings made by Danny Arends\"><font color=\"black\">Drawings</font></a>
  |<a href=\"/Index.cgi?p=Hobbies\" title=\"Summary of my hobbies\"><font color=\"black\">Hobbies</font></a>
  |<a href=\"/Index.cgi?p=Contact\" title=\"Contact information\"><font color=\"black\">Contact</font></a>
  |<a href=\"/Index.cgi?p=Applet\" title=\"Java applet game created by Danny Arends\"><font color=\"black\">Java Applet world</font></a>
  |<a href=\"/Index.cgi?p=Various\" title=\"Other things that intrest me\"><font color=\"black\">Other stuff</font></a>
  |<a href=\"/Index.cgi?p=Links\" title=\"Links to other web pages\"><font color=\"black\">Links</font></a>
  |<a href=\"/cgi-bin/\" title=\"Admin part of the website\"><font color=\"black\">Management Console</font></a>
  ]<br><hr>
    </td>
<tr>
  <td valign='top' width='49%'>
";
print(showFile("pages",$form{"p"}."_l.txt")."\n");
print "</td>
  <td valign='top'>";
print(showFile("pages",$form{"p"}."_m.txt")."\n");
  print "</td>
  <td valign='top' width='49%'>
  ";
print(showFile("pages",$form{"p"}."_r.txt")."\n");
print "
  </td>
</tr>
<tr>
  <td colspan=3>
  <div align=\"center\">";
print "<font color=\"white\">Page requested via rest: " . $form{"p"} . "</font>\n";
print "
  </div>
  </td>
</tr>
</table>
<a href=\"http://validator.w3.org/check?uri=referer\"><img src=\"http://www.w3.org/Icons/valid-html401-blue\" alt=\"Valid HTML 4.0.1 Loose\" style=\"border:0;width:88px;height:31px\"></a><a href=\"http://jigsaw.w3.org/css-validator/check/referer\"><img style=\"border:0;width:88px;height:31px\" src=\"http://www.w3.org/Icons/valid-css2-blue.png\" alt=\"Valide CSS!\"></a>
</div>
";


printEmptyFooter("");

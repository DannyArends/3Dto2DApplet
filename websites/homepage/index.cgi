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
  <i>Last updated: 08 March 2011</i><br/>
  [<a href=\"/Index.cgi\"><font color=\"black\">Home</font></a>
  |<a href=\"/Index.cgi?p=Publications\"><font color=\"black\">Publications</font></a>
  |<a href=\"/Index.cgi?p=Presentations\"><font color=\"black\">Presentations</font></a>
  |<a href=\"/Index.cgi?p=Drawings\"><font color=\"black\">Drawings</font></a>
  |<a href=\"/Index.cgi?p=Hobbies\"><font color=\"black\">Hobbies</font></a>
  |<a href=\"/Index.cgi?p=Contact\"><font color=\"black\">Contact</font></a>
  |<a href=\"/Index.cgi?p=Applet\"><font color=\"black\">Java Applet world</font></a>
  |<a href=\"/Index.cgi?p=Various\"><font color=\"black\">Other stuff</font></a>
  |<a href=\"/Index.cgi?p=Links\"><font color=\"black\">Links</font></a>
  |<a href=\"/cgi-bin/\"><font color=\"black\">Management Console</font></a>
  ]<br/><hr/>
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
<a href=\"http://validator.w3.org/check?uri=referer\"><img src=\"http://www.w3.org/Icons/valid-xhtml10-blue\" alt=\"Valid XHTML 1.0 Strict\" style=\"border:0;width:88px;height:31px\" /></a><a href=\"http://jigsaw.w3.org/css-validator/check/referer\"><img style=\"border:0;width:88px;height:31px\" src=\"http://www.w3.org/Icons/valid-css2-blue.png\" alt=\"Valide CSS!\" /></a>
</div>
";


printEmptyFooter("");

#!/usr/bin/perl -w

#
# server.cgi
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
#
# Start by pushing the location of the includes
BEGIN {
  push @INC,"./cgi-bin";
}

use strict;
use Socket;
use CGI qw(:standard);

#Global Variables across all includes
our %form = ();
our @paramnames = ('');
our $write_location = "dist/write/";
our $data_location = "dist/data/";

our $title = "Management console";
our $subtitle = "Management part of the web site";
our $theme = "/themes/default.css";
our $email = "Danny.Arends\@gmail.com";

#include our own files & functions
require "utils.cgi";
require "data.cgi";
require "user.cgi";

#Constants

#Variables
$form{"online"} = '';
$form{"list_files"} = '';

# Print a http header
printHTTPHeader('NOINDEX,FOLLOW');
receivePost();
writeIPtoLog();
print("<table width='95%'>
<tr>
  <td colspan=3>
  <i>Last updated: 27 April 2011</i><br>
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
  ]<br><hr></td></tr></table>");
if($form{"error"} ne ""){
	# Print an error
	printError();
}else{
	if($form{"page"} ne ""){
		if(lc($form{"page"}) eq "register"){
			print "<h3>Please Register</h3>"."\n";
			create_html_registrar();
		}
		print "Page requested via rest: " . $form{"page"} . "<br/>\n"; 
	}else{
		print "<p>Please Login to continue</p>"."\n";
		create_html_login();
		print "Please <a href='?page=register'><font color=\"black\">Register</font></a>"."\n";
	}
}
printHTTPFooter("");

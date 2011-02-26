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
  push @INC,"c:/Rtools/perl/lib";
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

our $title = "Managment console";
our $theme = "http://localhost:8080/themes/default.css";
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
printHTTPHeader();
receivePost();
writeIPtoLog();

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

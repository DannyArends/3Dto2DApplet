#!/usr/bin/perl -w

#
# server.cgi
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
#

use strict;
use Socket;
use CGI qw(:standard);

#Global Variables across all includes
our %form;
our $write_location = "../dist/write/";
our $data_location = "../dist/data/";

#include our own files & functions
require "utils.cgi";
require "data.cgi";
require "user.cgi";

#Constants

#Variables

#Main
printTXTHeader();
receivePost();
writeIPtoLog();
if($form{"online"} eq "true"){
	printPost();
	printServerStats();
	printUserStats();
}

if($form{"list_files"} ne ""){
	print("#AVAILABLE_ICONS" . "\n");
	list_files($form{"list_files"},$form{"dir"});
}

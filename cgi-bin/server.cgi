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
our $write_location = "dist/write/";
our $data_location = "dist/data/";

#include our own files & functions
require "utils.cgi";
require "data.cgi";
require "user.cgi";

#Constants

#Variables
$form{"online"} = '';
$form{"list_files"} = '';

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

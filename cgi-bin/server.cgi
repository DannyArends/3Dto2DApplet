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
sub online{
	printPost();
	printServerStats();
	printUserStats();
}

if($form{"function"} ne ""){
	print("#BY FUNCTION: " . $form{"function"} . "\n");
	my $temp = '';
	my $cnt = 0;
	@paramnames = sort(@paramnames);
	foreach (@paramnames) {
		my $p = $_;
		if(defined($p) && $p ne "" && $p ne "function"){
			if($cnt){ $temp = $temp . ", "; } 
			$temp = $temp . "'" . $form{$p} . "'";
			$cnt = 1;
		}
	}
	print("#".$form{"function"} . "(".$temp.")" . "\n");
	print(eval $form{"function"} . "(".$temp.")");
}

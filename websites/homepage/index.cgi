#!/usr/bin/perl -w

use strict;
use Socket;
use CGI qw(:standard);
use Benchmark ':hireswallclock';

#Global Variables across all includes
our %form;

#include our own files & functions
require "cgi-bin/functions.cgi";
require "cgi-bin/layout.cgi";
#Constants

#Variables
my @paramnames;
my $time_start;
my $time_end;

#Functions
sub loadPost{
	foreach my $p (param()) {
		push (@paramnames, $p);
		$form{$p} = param($p);
	}
	if(!defined($form{"Page"}) or $form{"Page"} eq ""){
		$form{"Page"} = "1) Index";
	}
}


#Main
$time_start = new Benchmark;
printHeader();
loadPost();
printHead();
printBeginBody();
printLayout();
$time_end = new Benchmark;
printTimeDiff(timestr(timediff($time_end, $time_start)));
printEndBody();
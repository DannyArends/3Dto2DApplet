#!/usr/bin/perl -w
use strict;
use Socket;
use CGI qw(:standard);
use Benchmark ':hireswallclock';

#Global Variables across all includes
our %form;

#include our own files & functions

#Constants

#Variables
my $time_start;
my $time_end;

#Functions
sub printTXTHeader{
	print("Content-type: text/plain"."\n\n");
}

sub printHTTPHeader{
	print("Content-type: text/html"."\n\n");
}

sub printPost{
  print("#FROM_CLIENT" . "\n");
	foreach my $p (param()) {
		$form{$p} = param($p);
    print($p . " = " . $form{$p} . "\n");
	}
}

sub printTime{
  my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)=localtime(time);
  printf("T: %02d:%02d:%02d %02d-%02d-%4d" . "\n",$hour,$min,$sec,$mday,$mon+1,$year+1900);
}

sub printServerStats{
	print("#SERVER_STATUS" . "\n");
	print("V: 0.0.1" . "\n");
	printTime();
}

sub printUserStats{
	print("#USER_STATUS"."\n");
	print("L: 0" . "\n");
	print("N: Anonymous" . "\n");
}


#Main
$time_start = new Benchmark;
printTXTHeader();
printPost();
printServerStats();
printUserStats();
$time_end = new Benchmark;
  
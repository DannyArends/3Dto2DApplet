#!/usr/bin/perl -w

#
# data.cgi
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
#

use strict;

#Global Variables across all includes
our %form;
our $write_location;
our $data_location;

#Functions
sub list_all{
	my $file;
	opendir(IMD, $data_location) || die print("Cannot open directory");
	my @thefiles= readdir(IMD);
	closedir(IMD); 
	foreach $file (@thefiles) {
   		print($file . "\n");
 	} 
}

sub list_type{
	my $type = "\." . $_[0];
	my $file;
	opendir(IMD, $data_location) or die print("Cannot open directory");
	my @thefiles= readdir(IMD);
	closedir(IMD); 
	foreach $file (@thefiles) {
   		if($file =~ m/$type/){ 
   			print($file . "\n"); 
   		}
 	} 
}

return 1;

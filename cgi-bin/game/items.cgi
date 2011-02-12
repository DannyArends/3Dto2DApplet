#!/usr/bin/perl -w

#
# items.cgi
#
# copyright (c) 2009-2011, Danny Arends
# last modified Jan, 2011
# first written Dec, 2011
#

use strict;

#Global Variables across all includes
our %form;
our $write_location;
our $data_location;

#Functions
sub create_empty_storage{
	my $username = lc $_[0];
	print "#Creating storage: 500 * 1 * 2\n";
	create_map($username."_store",500,1,2,"storages");
	print "#Creating inventory: 5 * 5 * 1\n";
	create_map($username."_inv",5,5,1,"storages");
}

sub get_storage{
	my $username = lc $_[0];
	list_map($username."_store","storages");
}

sub get_inventory{
	my $username = lc $_[0];
	list_map($username."_inv","storages");
}

sub list_items{
	my $filename = $_[0];
	my $location = "game";
	my $file = $data_location;
	my $line;
	my @item;
	if(!defined($filename)){$filename = "items"}
	$file .= "$location/$filename.dat";
	open(MYFILE, "$file") or die "Missing items.dat";
	while($line  = <MYFILE>) {
		chomp($line);
		@item = split(/\t/,$line);
		if(defined($item[1]) && !($item[0] =~ m/#./)){
			print $item[0] . "\t" . $item[1] . "\n";
		}
	}
	close (MYFILE);
}

sub list_buildings{
	list_items("buildings");
}

sub list_tiles{
	list_items("tiles");
}

sub item_stats{
	my $itemid = lc $_[0];
	my $location = $_[1];
	my $file = $data_location;
	my $line;
	my @item;
	my $found=0;
	if(!defined($location)){$location = "game";}
	$file .= "$location/items.dat";
	open(MYFILE, "$file") or die "Missing items.dat";
	while($line  = <MYFILE>) {
		chomp($line);
		@item = split(/\t/,$line);
		if($item[0] eq $itemid){
			$found=1;
 			print "itemID: " . $item[0] . "\n";
 			print "Name: " . $item[1] . "\n";
 			print "Weight: " . $item[2] . "\n";
 			if($item[3] ne 0){
 				print "Class: " . $item[3] . "\n";
 				print "ClassInfo: ". $item[4] ."\n";
 				if($item[3] eq "C"){print "Tools: ". $item[5] ."\n";}
 			}
		}
	}
	if($found eq 0){print "Unknown item";}
	close (MYFILE);
}

return 1;
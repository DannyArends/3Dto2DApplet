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

## create_empty_storage : Create an empty storage for a user
sub create_empty_storage{
	my $username = lc $_[0];
	print "#Creating storage: 500 * 1 * 2\n";
	create_map($username."_store",500,1,2,"storages");
	print "#Creating inventory: 5 * 5 * 1\n";
	create_map($username."_inv",5,5,1,"storages");
}

## get_storage : get the storage list for a user
sub get_storage{
	my $username = lc $_[0];
	list_map($username."_store","storages");
}

## get_inventory : get the inventory list for a user
sub get_inventory{
	my $username = lc $_[0];
	list_map($username."_inv","storages");
}

## list_items : List known items
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
			print $line . "\n";
		}
	}
	close (MYFILE);
}

## list_buildings : List known buildings
sub list_buildings{
	list_items("buildings");
}

## list_tiles : List known tiles
sub list_tiles{
	list_items("tiles");
}

## building_stats : Item statistics for a certain building
sub building_stats{
	item_stats(lc $_[0],"buildings");
}

## tile_stats : Item statistics for a certain tile
sub tile_stats{
	item_stats(lc $_[0],"tiles");
}

## item_stats : Item statistics for a certain item
sub item_stats{
	my $itemid = lc $_[0];
	my $filename = $_[1];
	my $location = "game";
	my $file = $data_location;
	my $line;
	my @item;
	my $found=0;
	if(!defined($filename)){$filename = "items";}
	$file .= "$location/$filename.dat";
	open(MYFILE, "$file") or die "Missing items.dat";
	while($line  = <MYFILE>) {
		chomp($line);
		@item = split(/\t/,$line);
		if($item[0] eq $itemid){
			$found=1;
 			print $line."\n";
		}
	}
	if($found eq 0){print "Unknown";}
	close (MYFILE);
}

return 1;
#!/usr/bin/perl -w

#
# map.cgi
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

#Creates a 3D integer mapping cube
sub create_map{
	my $map = lc $_[0];
	my $dimx = $_[1];
	my $dimy = $_[2];
	my $dimtile = $_[3];
	my $location = $_[4];
	my $file = $write_location;
	my $create = 0 ;
	my $line;
	if(!defined($location)){$location = "maps";print("Creating empty Map\n")}
	if($dimx > 1000 || $dimy > 30 || $dimtile > 12){ print "Dimensions unsuited, and we are generous";return}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/$map.map";
		open(MYFILE, "$file") or $create = 1;
		if($create==1){
			open(MYFILE, ">>$file") or $create = 2;
			for (my $x = 0; $x < $dimx; $x++) {
				my $cnt = 0;
				for (my $y = 0; $y < $dimy; $y++) {
					if($cnt){print MYFILE " ";}
					my $tcnt = 0;
					for (my $t = 0; $t < $dimtile; $t++) {
						if($tcnt){print MYFILE ";";}
						print MYFILE "0";
						$tcnt++;
					}
					$cnt++;
				}
				print MYFILE "\n";
			}
			close (MYFILE);
			open(MYFILE, "$file") or die "Couldn't create map $file"
		}
		while($line  = <MYFILE>) {
 			chomp($line);
 			print $line . "\n";
		}
		close (MYFILE);
	}
}

#List a 3D integer mapping cube
sub list_map{
	my $map = lc $_[0];
	my $location = $_[1];
	my $file = $write_location;
	my $create = 0 ;
	my $line;
	if(!defined($location)){$location = "maps";}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/".$map.".map";
		open(MYFILE, "$file") or $create = 1;
		while($line  = <MYFILE>) {
 			chomp($line);
 			print $line . "\n";
		}
		close (MYFILE);
	}
}

#List a dimensions of a 3d integer mapping cube
sub map_stats{
	my $map = lc $_[0];
	my $location = $_[1];
	my $file = $write_location;
	my $create = 0 ;
	my $line;
	my $tile;
	my @ydimension;
	my @tiledimension;
	my $xdimension;
	if(!defined($location)){$location = "maps";}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/".$map.".map";
		open(MYFILE, "$file") or die print "No such map: $file\n";
		$xdimension=0;
		while($line  = <MYFILE>) {
 			chomp($line);
 			$xdimension++;
 			@ydimension = split(/ /,$line);
 			for my $tile (@ydimension) {
 				@tiledimension = split(/;/,$tile);
 			}
		}
		print "Map: $file\n";
		print "Dimensions: ". $xdimension ."," . @ydimension  . " * " . @tiledimension . "\n";
		close (MYFILE);
	}else{
		print "No such map: $file\n";
	}
}

return 1;
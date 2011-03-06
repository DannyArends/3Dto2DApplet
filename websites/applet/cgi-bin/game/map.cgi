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

## list_maps : lists available maps
sub list_maps{
	my $location = $_[0];
	if(!defined($location)){$location = "maps";}
	opendir(DIR, $write_location.$location);
	my @files = grep(/\.map$/,readdir(DIR));
	closedir(DIR);

	# print all the filenames in our array
	foreach my $file (@files) {
   		print "$file\n";
	}
}

## get_filecontent : Get full content of a text file
sub get_filecontent{
	my $file = $_[0];
	my $line;
	my $content;
	open(MYFILE, "<$file") or die "No such file: $file";
	while($line  = <MYFILE>){
 		$content .= $line;
 	}
 	close (MYFILE);
 	return $content;
}

## update_map : Update a whole map and user that performs the update
sub update_map{
	my $map = lc $_[0];
	my $location = $_[1];
	my $file = $write_location;
	my $content;
	if(!defined($location)){$location = "maps";}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/$map.map";
		open(MYFILE, "$file") or die "No such map";
		my $epoch_timestamp = (stat(MYFILE))[9];
		close(MYFILE);
		$content = get_filecontent($file);
		my $epoch_now		= time;
		my $fwrd = abs($epoch_timestamp-$epoch_now);
		if($fwrd > 10){
			print "Updating map: $file ".$fwrd."\n";
			#TODO: Update the map elements using some game logic
			open(MYFILE, ">$file") or die "No such map";
			for my $line (split(/\n/,$content)) {
				print MYFILE $line."\n";
			}
			close (MYFILE);
			#TODO: If a user is updating the map also update user
		}else{
			print "Stale map: $file ".$fwrd."\n";
		}
	}
}

## update_tile : Update a tile
# TODO: Create a working and shorter update tile function
sub update_tile{
	my $map = lc $_[0];
	my $atx = $_[1];
	my $aty = $_[2];
	my $att = $_[3];
	my $newint = $_[4];
	my $location = $_[5];
	my $file = $write_location;
	my $line;
	my @ydimension;
	my @tiledimension;
	my $content;
	if(!defined($location)){$location = "maps";}
	if($atx > 1000 || $aty > 300 || $att > 5){ print "Dimensions unsuited, and we are generous";return}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/$map.map";
		$content = get_filecontent($file);
		open(MYFILE, ">$file") or die "No such map";
		for my $line (split(/\n/,$content)) {
			if($atx==0){
				@ydimension = split(/ /,$line);
 				@tiledimension = split(/;/,$ydimension[$aty]);
 				$tiledimension[$att] = $newint;
 				for my $tile (@ydimension) {
 					if($aty==0){
 						my $tcnt = 0;
 						for my $tiled (@tiledimension) {
 							if(defined($tiled) && $tiled ne ""){
 								if($tcnt){print MYFILE ";";print ";";}
 								print MYFILE $tiled;
 								print $tiled;
 							}
 							$tcnt++;
 						}
 						print MYFILE " ";
 					}else{
 						print MYFILE $tile." ";
 					}
 					$aty--;
 				}
 				print MYFILE "\n";
			}else{
				print MYFILE $line."\n";
			}
			$atx--;
		}
		close (MYFILE);
	}
}

#TODO: Perhaps re-write to start with some kind of standard
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
	if($dimx > 1000 || $dimy > 300 || $dimtile > 5){ print "Dimensions unsuited, and we are generous";return}
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
					my $trandom = int(rand(3))+1;
					for (my $t = 0; $t < $dimtile; $t++) {
						if($tcnt){print MYFILE ";";}
						if($t==0){
							print MYFILE ($trandom-2)*int(rand(100));
						}else{
							if($t==1){
								print MYFILE $trandom;
							}else{
								print MYFILE 0;
							}
						}
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
 			for my $tile (@ydimension){
 				@tiledimension = split(/;/,$tile);
 			}
		}
		my $epoch_timestamp = (stat(MYFILE))[9];
		my $epoch_now		= time;
		print "Map: $file\n";
		print "Last-updated: ".localtime($epoch_timestamp)."\n";
		print "Time passed since: ".abs($epoch_timestamp-$epoch_now)."\n";
		print "Dimensions: ". $xdimension ."," . @ydimension  . " * " . @tiledimension . "\n";
		close (MYFILE);
	}else{
		print "No such map: $file\n";
	}
}

sub get_map_x{
	my $map = lc $_[0];
	my $location = $_[1];
	my $file = $write_location;
	my $line;
	my $xdimension;
	if(!defined($location)){$location = "maps";}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/".$map.".map";
		open(MYFILE, "$file") or return 0;
		$xdimension=0;
		while($line  = <MYFILE>) {
 			$xdimension++;
		}
		close (MYFILE);
		return $xdimension;
	}else{
		return 0;
	}
}

sub get_map_y{
	my $map = lc $_[0];
	my $location = $_[1];
	my $file = $write_location;
	my $line;
	my @ydimension;
	if(!defined($location)){$location = "maps";}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/".$map.".map";
		open(MYFILE, "$file") or return 0;
		while($line  = <MYFILE>) {
 			chomp($line);
 			@ydimension = split(/ /,$line);
		}
		close (MYFILE);
		return @ydimension;
	}else{
		return 0;
	}
}

sub get_map_tile{
	my $map = lc $_[0];
	my $location = $_[1];
	my $file = $write_location;
	my $line;
	my @ydimension;
	my @tiledimension;
	
	if(!defined($location)){$location = "maps";}
	if(!defined($map) || $file eq $write_location){
		$file .= "$location/".$map.".map";
		open(MYFILE, "$file") or return 0;
		while($line  = <MYFILE>) {
 			chomp($line);
 			@ydimension = split(/ /,$line);
 			@tiledimension = split(/;/,$ydimension[0]);
		}
		close (MYFILE);
		return "".@tiledimension;
	}else{
		return 0;
	}
}

return 1;
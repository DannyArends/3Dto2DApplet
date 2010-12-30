#!/usr/bin/perl -w

#
# user.cgi
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

my $user_file = $write_location . "users.log";

#Functions

sub user_exists{
	my $username = lc $_[0];
	my $found = 0;
	my $line = '';
	
	open(MYFILE, $user_file) or die print("Error: opening " . $user_file . " file for reading");
 	while($line  = <MYFILE>) {
 		chomp($line);
 		if($line =~ m/$username\t(.*)/){ $found = 1; }
 	}
 	close (MYFILE);
 	return $found;
}

sub create_user{
	my $username = lc $_[0];
	my $password = $_[1];
	
	if(!defined($username) || $username eq ""){return 0;}
	if(!defined($password) || $password eq ""){return 0;}
	
	my $found = user_exists($username);
	
	if(!$found){
		open(MYFILE, ">>$user_file") or die print("Error: opening user log file for writing");
		print MYFILE $username . "\t" . $password . "\t" . $username . "\t0.0\t0.0\t0.0" .  "\n";
		close (MYFILE);
		return 1;
	}else{
		return 0;
	}
}

sub delete_user{
	
}

sub list_all_users{
	my $line = '';
	my $return = '';
	open(MYFILE, $user_file) or die print("Error: opening user log file for reading");
	while($line  = <MYFILE>) {
 		chomp($line);
 		if($line =~ /(.*?)\t/){
 			$return .= $1 . "\n";
 		}
 	}
	close (MYFILE);
	return $return;
}

sub login_user{
	my $username = lc $_[0];
	my $password = $_[1];
	if(!defined($username) || $username eq ""){return 0;}
	if(!defined($password) || $password eq ""){return 0;}
	my $found = user_exists($username);
	my $line = '';
	if($found){
		open(MYFILE, $user_file) or die print("Error: opening user log file for reading");
		while($line  = <MYFILE>) {
 			chomp($line);
 			if($line =~ /$username\t(.*?)\t/){
 				if($password eq $1){ return 1; }
 			}
 		}
		close (MYFILE);
		return 0;
	}else{
		return 0;
	}
}

sub get_user_location{
	my $username = lc $_[0];
	if(!defined($username) || $username eq ""){return 0;}
	my $found = user_exists($username);
	my $line = '';
	if($found){
		open(MYFILE, $user_file) or die print("Error: opening user log file for reading");
		while($line  = <MYFILE>) {
 			chomp($line);
 			if($line =~ /$username\t(.*?)\t(.*?)\t(.*?)\t(.*?)\t(.*)/){
 				return $3 . "," . $4 . "," . $5 . "@" . $2;
 			}
 		}
		close (MYFILE);
		return 0;
	}else{
		return 0;
	}
}

sub set_user_password{
	
}

return 1;

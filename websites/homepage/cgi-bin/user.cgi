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

#Functions

## user_exists : Check if a user exists
sub user_exists{
	my $username = lc $_[0];
	my $found = 0;
	my $line = '';
	
	open(MYFILE, $write_location . "users.log") or die print("Error: opening users.log file for reading");
 	while($line  = <MYFILE>) {
 		chomp($line);
 		if($line =~ m/$username\t(.*)/){ $found = 1; }
 	}
 	close (MYFILE);
 	return $found;
}

## registrar_exists : Check if a registrar exists
sub registrar_exists{
	my $username = lc $_[0];
	my $found = 0;
	my $line = '';
	
	open(MYFILE, $write_location . "registrars.log") or die print("Error: opening users.log file for reading");
 	while($line  = <MYFILE>) {
 		chomp($line);
 		if($line =~ m/$username\t(.*)/){ $found = 1; }
 	}
 	close (MYFILE);
 	return $found;
}

sub create_html_login{
	print "<p>"."\n";
	print "<form method='post' action='server.cgi'>"."\n";
	print "<input type='hidden' name='function' value='login_user'>"."\n";
	print "<input type='hidden' name='file' value=''>"."\n";
	print "<input type='text' name='username' value='username'><br/>"."\n";
	print "<input type='password' name='password' value='password'><br/>"."\n";
	print "<input type='submit' value = 'login'>"."\n";
	print "</form>"."\n";
	print "</p>"."\n";
}

sub create_html_registrar{
	print "<p>"."\n";
	print "<form method='post' action='server.cgi'>"."\n";
	print "<input type='hidden' name='function' value='create_user'>"."\n";
	print "<input type='hidden' name='file' value='registrars'>"."\n";
	print "<input type='text' name='username' value='username'><br/>"."\n";
	print "<input type='password' name='password0' value='password0'><br/>"."\n";
	print "<input type='password' name='password1' value='password1'><br/>"."\n";
	print "<input type='submit' value = 'Submit Registration'>"."\n";
	print "</form>"."\n";
	print "</p>"."\n";
}

sub validate_registration{
	my $username = lc $_[0];
	#TODO: Copy the user from registrars.log to users.log
}

sub view_registrations{
	list_all_users("registrars");
}

## create_user : Create a new user in either the registrars or users file
sub create_user{
	my $file = $write_location . lc $_[0];
	my $password1 = $_[1];
	my $password2 = $_[2];
	my $username = lc $_[3];

	if(!defined($file) || $file eq ""){$file = "registrars";}
	if(!defined($username) || $username eq ""){return 0;}
	if(!defined($password1) || $password2 eq ""){return 0;}
	if($password1 ne $password2){return 0;}
	my $found = user_exists($username);
	
	if(!$found){
		open(MYFILE, ">>$file.log") or die print("Error: opening users.log file for writing");
		print MYFILE $username . "\t" . $password1 . "\t" . $username . "\t0.0\t0.0\t0.0" .  "\n";
		close (MYFILE);
		create_empty_storage($username);
		create_map($username,25,25,3);
		create_xp($username);
		return 1;
	}else{
		return 0;
	}
}

## delete_user : Delete a user
sub delete_user{
	my $password = $_[0];
	my $username = lc $_[1];
	#TODO: Check if user exists
	#TODO: Check if password matches
	#TODO: Delete the individual
}

## list_all_users : lists all users
sub list_all_users{
	my $file = $write_location . lc $_[0];
	my $line = '';
	if(!defined($file) || $file eq $write_location){
		$file = $write_location . "registrars";
	}
	
	open(MYFILE, "$file.log") or die print("Error: opening user log file for reading");
	my $cnt = 0;
	while($line  = <MYFILE>) {
 		chomp($line);
 		if($line =~ /(.*?)\t/){
 			print $1 . "\n";
 			$cnt++;
 		}
 	}
 	close (MYFILE);
 	if($cnt eq 0){print("No registrations")}
}

## login_user : login a user checking username and password
sub login_user{
	my $file = $write_location . lc $_[0];
	my $password = $_[1];
	my $username = lc $_[2];

	if(!defined($file) || $file eq $write_location){
		$file = $write_location . "users";
	}
	if(!defined($username) || $username eq ""){print("Please supply a username"); return 0;}
	if(!defined($password) || $password eq ""){print("Please supply a password"); return 0;}

	my $found = user_exists($username);
	my $line = '';

	if($found){
		open(MYFILE, "$file.log") or die print("Error: opening user log file for reading");
		while($line  = <MYFILE>) {
 			chomp($line);
 			if($line =~ /$username\t(.*?)\t/){
 				if($password eq $1){ 
 					print("$username logged in");
 					return 1; 
 				}else{
 					print("Wrong password");
 					return 0;
 				}
 			}
 		}
		close (MYFILE);
		print("Unknown error, please contact: Danny.Arends\@gmail.com"); 
		return 0;
	}else{
		print("No such user"); 
		return 0;
	}
}

## get_user_location : Get the location of a user
sub get_user_location{
	my $file = $write_location . lc $_[0];
	my $username = lc $_[1];
	
	if(!defined($file) || $file eq $write_location){
		$file = $write_location . "users";
	}
	if(!defined($username) || $username eq ""){return 0;}
	my $found = user_exists($username);
	my $line = '';
	if($found){
		open(MYFILE, "$file.log") or die print("Error: opening user log file for reading");
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

## set_user_password : Change user password
sub set_user_password{
	my $oldpassword = $_[0];
	my $password1 = $_[1];
	my $password2 = $_[2];
	my $username = lc $_[3];
	#TODO: Check if user exists
	#TODO: Check if old password matches
	#TODO: Change password
}

return 1;

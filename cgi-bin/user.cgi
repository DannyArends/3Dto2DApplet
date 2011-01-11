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
	print "<input type='text' name='email' value='email\@email.com'><br/>"."\n";
	print "<input type='password' name='password0' value='password0'><br/>"."\n";
	print "<input type='password' name='password1' value='password1'><br/>"."\n";
	print "<input type='submit' value = 'Submit Registration'>"."\n";
	print "</form>"."\n";
	print "</p>"."\n";
}

sub create_user{
	my $file = $write_location . lc $_[0];
	my $password = $_[1];
	my $username = lc $_[2];
	
	if(!defined($file) || $file eq ""){$file = "registrars";}
	if(!defined($username) || $username eq ""){return 0;}
	if(!defined($password) || $password eq ""){return 0;}
	
	my $found = user_exists($username);
	
	if(!$found){
		open(MYFILE, ">>$file.log") or die print("Error: opening users.log file for writing");
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
	my $file = $write_location . lc $_[0];
	my $line = '';
	my $return = '';
	if(!defined($file) || $file eq $write_location){
		$file = $write_location . "registrars";
	}
	
	open(MYFILE, "$file.log") or die print("Error: opening user log file for reading");
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
	my $file = $write_location . lc $_[0];
	my $password = $_[1];
	my $username = lc $_[2];
	
	if(!defined($file) || $file eq $write_location){
		$file = $write_location . "users";
	}
	if(!defined($username) || $username eq ""){return 0;}
	if(!defined($password) || $password eq ""){return 0;}
	
	my $found = user_exists($username);
	my $line = '';
	
	if($found){
		open(MYFILE, "$file.log") or die print("Error: opening user log file for reading");
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

sub set_user_password{
	
}

return 1;

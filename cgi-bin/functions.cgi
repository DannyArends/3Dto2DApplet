#!/usr/bin/perl -w
use strict;

#Global Variables across all includes
our %form;
our $write_location;
our $data_location;

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
	print("L: " . $ENV{REMOTE_HOST} . " " . $ENV{SERVER_SOFTWARE} . "\n");
	print("V: 0.0.1" . "\n");
	printTime();
}

sub printUserStats{
	print("#USER_STATUS"."\n");
	print("L: 0 " . $ENV{REMOTE_ADDR} . "\n");
	print("N: Anonymous" . "\n");
}

sub writeIPtoLog{
	my $found = 0;
	my $filename = $write_location . "output.log";
	open(MYFILE, "$filename") or die print("Error: opening file for reading");
 	while (<MYFILE>) {chomp;
 		if($_ == $ENV{REMOTE_ADDR}){ $found = 1; }
 	}
	if(!$found){
		close (MYFILE);
		open(MYFILE, ">>$filename") or die print("Error: opening file for writing");
		print("ADD: $ENV{REMOTE_ADDR}" . "\n");
		print MYFILE $ENV{REMOTE_ADDR} . "\n";
	}else{
		print("FOUND: $ENV{REMOTE_ADDR}" . "\n");
	}
 	close (MYFILE);
}

return 1;

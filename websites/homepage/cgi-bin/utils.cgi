#!/usr/bin/perl -w

#
# utils.cgi
#
# copyright (c) 2009-2011, Danny Arends
# last modified Mrt, 2011
# first written Dec, 2010
#

use strict;

#Global Variables across all includes
our %form;
our @paramnames;
our $write_location;
our $data_location;
our $title;
our $subtitle;
our $theme;
our $email;

#Functions
sub printTXTHeader{
	print("Content-type: text/plain"."\n\n");
}

sub printHTTPHeader{
	print("Content-type: text/html"."\n\n");
	print("<?xml version=\"1.0\" encoding=\"utf-8\"?> 
	<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n
	<html>\n
		<head>\n
			<link rel=\"stylesheet\" type=\"text/css\" href=\"$theme\">
			<title>$title - ".$form{"p"}."</title>\n
	<meta name='Keywords' content='Danny Arends Bioinformatics biology Programming R Genetical Genomics QTL MQM GBIC Groningen Biology Biosources' > 
	<meta name='Description' content='DannyArends.nl: ".$form{"p"}." page of Danny Arends his personal home page' > 
	<meta name=\"google-site-verification\" content=\"vWrAcVNC0zm0pKDS2um8eSIQTUTyhtZXxcDd35a7A0c\" >
	<meta name='Author' content='Danny Arends' >
	<meta name='Robots' content='INDEX,FOLLOW' >
  <script type='text/javascript'></script>
  <script type='text/javascript' src='jQuery/jquery.min.js'></script>
  <script type='text/javascript' src='jQuery/jquery.cycle.min.js'></script>
  <script type='text/javascript'>
  \t\$(document).ready(function() {
  \t\t\$('.slideshow').cycle({
  \t\tfx: 'fade'
  \t\t});
  \t});
  </script>			
		</head>\n
		<body>\n
		<div class='whitebg'>
			<h1>$title</h1>\n
			<h2>$subtitle</h2>\n
			\n");
}

sub printHTTPFooter{
	my $additive = $_[0];
	print("<ul><li><a href=\"/$additive\"><font color=\"black\">Back</font></a></li></ul>
			<div align=\"center\">\n
				<a href=\"http://github.com/DannyArends/3Dto2DApplet\">Github</a><br>\n
			</div>\n");
	printEmptyFooter();
}

sub showFile{
	my $dir = shift;
	my $name = shift;
	my @rawdata;
	my $line;
	my $text;
	open(FILE, "$dir/$name");
	@rawdata=<FILE>; 
	close(FILE);
	foreach $line (@rawdata){
		chomp($line);
		if($line =~ /<h\C{1}>/){
			#Headers
			$text .= $line."\n";
		}elsif($line eq ""){
			#Empty lines
			$text .= "\n";
		}else{
			#Normal line
			$text .= $line."<br>"."\n";
		}	
	} 
	return $text;
}

sub printEmptyFooter{
	print("</body>\n
	</html>\n");
}

sub printError{
	print("<font color='red' size='+3'>".$form{"error"}."</font>" ."<br>". "\n");
	if($form{"page"} ne ""){
		print("<p>Missing page: ".$form{"page"} . "<br/><br/>". "\n");
		print("Did you mean to <a href=''><font color='green'>Create page '".$form{"page"} . "'?</font></a><br>". "\n");
		print("Contact the admin: ". $email ."</p>" . "\n");
	}
}

sub receivePost{
  	#print("#FROM_CLIENT" . "\n");
	foreach my $p (param()) {
		push (@paramnames, $p);
		$form{$p} = param($p);
	}
}

sub printPost{
    print("#FROM_CLIENT" . "\n");
	foreach my $p (param()) {
    	print($p . " = " . $form{$p} . "\n");
	}
}

sub printTime{
  my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)=localtime(time);
  printf("T: %02d:%02d:%02d %02d-%02d-%4d" . "\n",$hour,$min,$sec,$mday,$mon+1,$year+1900);
}

sub printServerStats{
	print("#SERVER_STATUS" . "\n");
	if(defined($ENV{REMOTE_ADDR}) && defined($ENV{SERVER_SOFTWARE})){
		print("L: " . $ENV{REMOTE_HOST} . " " . $ENV{SERVER_SOFTWARE} . "\n");
	}
	print("V: 0.0.1" . "\n");
	printTime();
}

sub printUserStats{
	print("#USER_STATUS"."\n");
	if(defined($ENV{REMOTE_ADDR})){
		print("L: 0 " . $ENV{REMOTE_ADDR} . "\n");
	}
	print("N: Anonymous" . "\n");
}

sub writeIPtoLog{
	my $found = 0;
	my $line = '';
	my $filename = $write_location . "output.log";
	if(defined($ENV{REMOTE_ADDR})){
		open(MYFILE, $filename) or die print("Error: opening ip log file: ".$filename." for reading");
 		while($line  = <MYFILE>) {
 			chomp($line);
 			if($line eq $ENV{REMOTE_ADDR}){ $found = 1; }
 		}
		if(!$found){
			close (MYFILE);
			open(MYFILE, ">>$filename") or die print("Error: opening ip ".$filename." file for appending");
			#print("ADD: $ENV{REMOTE_ADDR}" . "\n");
			print MYFILE $ENV{REMOTE_ADDR} . "\n";
		}else{
			#print("FOUND: $ENV{REMOTE_ADDR}" . "\n");
		}
 		close (MYFILE);
	}
}

return 1;

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
  my $spidercommand = $_[0];
	print("Content-type: text/html"."\n\n");
	print("<?xml version=\"1.0\" encoding=\"utf-8\" xml:lang=\"en\"?> 
	<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n
	<html dir='ltr' lang='en-EN'>\n
		<head>\n
    	<meta name='robots' content='all'> 
      <meta http-equiv='Content-Type' content='text/html;charset=utf-8' >
      <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"etc/favicon.ico\">
			<link rel=\"alternate\" type=\"application/xml\" title=\"Sitemap\" href=\"/Sitemap.xml\">
			<link rel=\"alternate\" type=\"application/rss+xml\" title=\"Blog RSS feed\" href=\"/myblog.cgi?do=RSS\">
			<title>$title - ".$form{"p"}."</title>\n
	<meta name='Keywords' content='Bioinformatics, QTL, Computational, Line Drawings, Groningen University, Biology, Publications, R, R package, PHD Student, C, Rqtl, Genetical Genomics, QTL' > 
	<meta name='Description' content='DannyArends.nl: ".$form{"p"}."' > 
	<meta name=\"google-site-verification\" content=\"vWrAcVNC0zm0pKDS2um8eSIQTUTyhtZXxcDd35a7A0c\" >
	<meta name=\"alexaVerifyID\" content=\"lT8ju1qrjgO2ak7jEZvOEQWcqpo\" >
  <meta name=\"y_key\" content=\"9fbec0326696c2ee\" >
	<meta name='Author' content='Danny Arends' >
	<meta name='Robots' content='$spidercommand' >
	
  <script type='text/javascript'></script>
  <style type=\"text/css\" media=\"screen\"> \@import \"http://www.dannyarends.nl/etc/css/style.css\";</style>
		<style type=\"text/css\" media=\"print\"> \@import \"http://www.dannyarends.nl/etc/css/print.css\";</style> 
		<script type=\"text/javascript\" src=\"etc/js/jquery.min.js\"></script>
		<script language=\"javascript\" type=\"text/javascript\">
			function surroundText(text1, text2, textarea){
			// Can a text range be created?
			if (typeof(textarea.caretPos) != \"undefined\" && textarea.createTextRange){
				var caretPos = textarea.caretPos, temp_length = caretPos.text.length;
			caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == \' \' ? text1 + caretPos.text + text2 + \' \' : text1 + caretPos.text + text2;

		if (temp_length == 0)
		{
			caretPos.moveStart(\"character\", -text2.length);
			caretPos.moveEnd(\"character\", -text2.length);
			caretPos.select();
		}
		else
			textarea.focus(caretPos);
	}
	// Mozilla text range wrap.
	else if (typeof(textarea.selectionStart) != \"undefined\"){
		var begin = textarea.value.substr(0, textarea.selectionStart);
		var selection = textarea.value.substr(textarea.selectionStart, textarea.selectionEnd - textarea.selectionStart);
		var end = textarea.value.substr(textarea.selectionEnd);
		var newCursorPos = textarea.selectionStart;
		var scrollPos = textarea.scrollTop;

		textarea.value = begin + text1 + selection + text2 + end;

		if (textarea.setSelectionRange){
			if (selection.length == 0)
				textarea.setSelectionRange(newCursorPos + text1.length, newCursorPos + text1.length);
			else
				textarea.setSelectionRange(newCursorPos, newCursorPos + text1.length + selection.length + text2.length);
			textarea.focus();
		}
		textarea.scrollTop = scrollPos;
	}else{
		textarea.value += text1 + text2;
		textarea.focus(textarea.value.length - 1);
	}
}
</script> 
    <script type='text/javascript' src='jQuery/jquery.cycle.min.js'></script>
		<script type=\"text/javascript\">
			\$(window).load(function() {    
				var theWindow        = \$(window),
					\$bg              = \$(\"#bg\"),
					aspectRatio      = \$bg.width() / \$bg.height();
				function resizeBg() {
					if ( (theWindow.width() / theWindow.height()) < aspectRatio ) {
						\$bg
							.removeClass()
							.addClass('bgheight');
					} else {
						\$bg
							.removeClass()
							.addClass('bgwidth');
					}
				}
				theWindow.resize(function() {
					resizeBg();
				}).trigger(\"resize\");
			});
		</script>		
  <script type='text/javascript'>
  \t\$(document).ready(function() {
  \t\t\$('.slideshow').cycle({
  \t\tfx: 'fade'
  \t\t});
  \t});
  </script>
<script type=\"text/javascript\">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-22162565-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>	
		</head>\n
    <body>\n
		<!-- Door: Maarten Hunink, Zinnebeeld ** IE6 WARS **--> \n
		<!--[if lte IE 6]><div id=\"ie6_banner\"><div id=\"ie6_wrap\"><div id=\"ie6_links\"><a href=\"http://www.mozilla.com/nl\"><img src=\"http://www.wijstoppenook.nl/site/gfx/firefox_small.png\" alt=\"\" />Firefox</a><a href=\"http://www.google.com/chrome\"><img src=\"http://www.wijstoppenook.nl/site/gfx/chrome_small.png\" alt=\"\" />Chrome</a><a href=\"http://www.apple.com/safari\"><img src=\"http://www.wijstoppenook.nl/site/gfx/safari_small.png\" alt=\"\" />Safari</a><a href=\"http://www.opera.com\"><img src=\"http://www.wijstoppenook.nl/site/gfx/opera_small.png\" alt=\"\" />Opera</a> </div><h1>U gebruikt een oude versie van Internet Explorer</h1><p>Helaas worden Internet Explorer 6 en oudere versies niet meer ondersteund op deze website. Wij raden u aan over te schakelen naar een modernere internetbrowser. <a href=\"http://www.microsoft.com/ie\">Download hier</a> de nieuwste versie van Internet explorer, of kies een browser uit het overzicht hiernaast. Deze browsers zijn veelal sneller en veiliger en voldoen beter aan de webstandaarden. U kunt ze gratis downloaden en installeren kost slechts enkele minuten.</p></div></div><![endif]-->
		<!--[if gt IE 6]><div><img src=\"http://www.dannyarends.nl/etc/img/bg".(int(rand(5))+1).".jpg\" id=\"bg\" alt=\"dannyarends.nl\"></div><![endif]-->
		<!--[if !IE]><!--><div><img src=\"http://www.dannyarends.nl/etc/img/bg".(int(rand(5))+1).".jpg\" id=\"bg\" alt=\"dannyarends.nl\"></div><!--<![endif]-->
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
	unless (-e "$dir/$name") {
		$form{"error"} = "Page not found";
		$form{"p"} = "Index";
		printError();
		return;
	}
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
			$text .= $line."\n";
		}	
	} 
	return $text;
}

sub printEmptyFooter{
	print("<div align=\"center\" id=footer>");
	print("Copyright 'Danny Arends' 2011 - All Rights Reserved\n");
	print("</div>\n");
	print("</body>\n</html>\n");
}

sub printError{
	print("<h1> 404 - ".$form{"error"}."</h1>" ."<br>". "\n");
	print("<p>The page you were looking for was not found<br/>". "\n");
	print("Contact the admin: ". $email ."</p>" . "\n");
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

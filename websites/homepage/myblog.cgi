#!/usr/bin/perl

#####################################################
# 	PPLOG (Perl Powered Blog)
#	The idea of this blog, is a very simple yet
#	powerful blog. Enjoy
#	
#	Coded by Federico Ramírez (fedekun)
#	fedekiller@gmail.com
#  Adapted by Danny Arends (RotationZ)
# Danny.Arends@gmail.com
#	
#	PPLOG uses the GNU Public Licence v3
#	http://www.opensource.org/licenses/gpl-3.0.html	
#
#	Powered by YAGNI (You Ain't Gonna Need It)	
#	YAGNI: Only add things, when you actually 
#	need them, not because you think you will.
#	
#	Version: 1.1.1
#####################################################


use CGI::Carp qw/fatalsToBrowser/;	# This is optional
use CGI':all';
use POSIX qw(ceil floor);
use strict;							# This is also optional

our $config_blogTitle = 'My Blog - by Danny Arends';								# Blog title
our $config_adminPass = 'Windows2003';								# Admin password for adding entries
our $config_smiliesFolder = '/smilies';								# Path to smilies, if you dont want smilies, just, dont upload that folder
our $config_smiliesFolderName = 'smilies';																	# Smilies Folder Name
our $config_postsDatabaseFolder = '/posts';							# Name of the folder where entries will be saved. If you cant create entries with this path, try /home/www/user/posts
our $config_commentsDatabaseFolder = '/posts/comments';						# Name of the folder where entries will be saved. If you cant create entries with this path, try /home/www/user/posts
our $config_dbFilesExtension = 'ppl';								# Extension of the files used as databases
our $config_currentStyleFolder = 'themes';								# Styles folder (. = in the same path as ths file)
our $config_entriesPerPage = 5;										# For pagination... How many entries will be displayed per page?
our $config_maxPagesDisplayed = 5;									# Maximum number of pages displayed at the bottom
our $config_metaRevisitAfter = 1;									# This is for search Engines... How often will they check for updates, in days
our $config_metaDescription = 'My blog about programming, biology, software and other stuff';				# Also for search engines
our $config_metaKeywords = 'Bioinformatics, QTL, Computational, Line Drawings, Groningen University, Biology, Publications, R, R package, PHD Student, C, Rqtl, Genetical Genomics, QTL';					# Also for search engines...
our $config_textAreaCols = 50;										# Cols of the textarea to add and edit entries
our $config_textAreaRows = 10;										# Rows of the textarea to add and edit entries
our @config_ipBan = qw/202.325.35.145 165.265.26.65/;				# 2 random IPS, sorry if it is yours... Just edit this, separate ips with spaces
our $config_bannedMessage = 'Sorry, you have been banned.';			# This message will appear when an user is banned from the blog
our $config_allowComments = 1;										# Allow comments
our $config_bbCodeOnCommentaries = 1;								# Allow BBCODE on commentaries									(0 = No, 1 = Yes)
our $config_commentsMaxLenght = 200;								# Comment maximum characters
our $config_commentsSecurityCode = 1;								# Allow security code for comments 								(0 = No, 1 = Yes)
our @config_commentsForbiddenAuthors = qw/admin administrator danny/;		# These are the usernames that normal users cant use, if you use one of these, it will ask for password
our $config_commentsDescending = 0;									# Showing NOT in descending order, this will show oldest first	(0 = No, 1 = Yes)
our $config_searchMinLength = 4;									# Minimum lenght of the keyword to search, this is for avoiding seeking words like "and", "or", "a", etc
our $config_redditAllowed = 0;										# Allow the reddit option, to share your posts					(0 = No, 1 = Yes)
our $config_menuEntriesLimit = 10;									# Limits of entries to show in the menu
our @config_menuLinks = ('http://www.dannyarends.nl,My Homepage','http://google.com,google');										# Links to be displayed at the menu
our $config_menuShowLinks = 0;										# Show links at the menu?										(0 = No, 1 = Yes)
our $config_menuLinksHeader = 'Links';								# This is the header before the links appear, you can change it as you wish, normal is Links or Blogroll
our $config_allowCustomHTML = 0;									# Want to add some code? Edit here								(0 = No, 1 = Yes)
our $config_customHTML = '<h1>Hello</h1> This is custom HTML';		# HTML here
our $config_showHits = 0;											# Want to show how many users are browsing your blog?			(0 = No, 1 = Yes)
our $config_sendMailWithNewComment = 1;								# Receive a mail when someone posts a comment					(0 = No, 1 = Yes) It works only if you host allows sendmail
our $config_sendMailWithNewCommentMail = 'Danny.Arends@gmail.com';		# Email adress to send mail if allowed
our $config_showUsersOnline = 1;									# Wanna show how many users are browsing your site?				(0 = No, 1 = Yes)
our $config_usersOnlineTimeout = 120;								# How long is an user considered online? In seconds
our $config_gmt = +1;												# Your GMT, -3 = Buenos Aires
our $config_showLatestComments = 1;									# Show latest comments on the menu
our $config_showLatestCommentsLimit = 5;							# Show 10 latest comments
our $config_allowBBcodeButtonsOnComments = 1;						# Allow BBCODE Buttons on Comments Form
our $config_commentsPerPage = 20;									# How many comments will be shown per page
our $config_showGmtOnFooter = 1;									# Display GMT on footer
our $config_securityQuestionOnComments = 0;							# Allow the option to display a question which users have to answer in order to post comments
our $config_commentsSecurityQuestion = 'My name? ;)';	# You shall change it, choose a question all your users will know
our $config_commentsSecurityAnswer = 'Danny';						# Answer of the security question. The comparison will be CaSe InSeNsItIvE
our $config_randomString = 'ajhd092nmbd20dbJASDK1BFGAB1';			# This is for password encryption... Edit if you want
our $config_entriesOnRSS = 0;										# 0 = ALL ENTRIES, if you want a limit, change this
our $config_useHtmlOnEntries =1;									# Allow HTML on entries when making a new post (THIS WILL DISALLOW BBCODE!!)
our $config_useWYSIWYG = 1;											# You must allow HTML on entries for this to work // Note, WYSIWYG wont allow smilies
our $config_onlyNumbersOnCAPTCHA = 0;								# Use only numbers on CAPTCHA
our $config_CAPTCHALenght = 8;										# Just to make different codes
our $config_baseurl='http://www.dannyarends.nl/myblog.cgi'; #for rss

# Basic Functions
sub r{
	escapeHTML(param($_[0]));
}

sub basic_r{
	param($_[0]);
}

sub bbcode{
	$_ = $_[0];
	s/\n/<br \/>/gi;
	s/\[b\](.+?)\[\/b\]/<b>$1<\/b>/gi;
	s/\[i\](.+?)\[\/i\]/<i>$1<\/i>/gi;
	s/\[u\](.+?)\[\/u\]/<u>$1<\/u>/gi;
	s/\[\*\](.+?)\[\/\*\]/<li>$1<\/li>/gi;
	s/\[center\](.+?)\[\/center\]/<center>$1<\/center>/gi;
	s/\[url\](.+?)\[\/url\]/<a href=$1 target=_blank>$1<\/a>/gi;
	s/\[url=(.+?)\](.+?)\[\/url\]/<a href=$1 target=_blank>$2<\/a>/gi;
	s/\[img\](.+?)\[\/img\]/<img src=$1 \/>/gi;
	s/\[code\](.+?)\[\/code\]/<div class=code><pre>$1<\/pre><\/div>/gi;
	s/\[quote\](.+?)\[\/quote\]/<div class=quote>$1<\/div>/gi;
	if(-d "$config_smiliesFolder")
	{
		my @smilies;
		my $s;
		if(opendir(DH, $config_smiliesFolder))
		{
			@smilies = grep {/gif/ || /jpg/ || /png/;} readdir(DH);
		}
		foreach $s(@smilies)
		{
			my @i = split(/\./, $s);
			s/\:$i[0]\:/<img src=$config_smiliesFolderName\/$i[0].$i[1] \/>/gi;
		}
	}
	return $_;
}

sub bbdecode{
	$_ = $_[0];
	s/\n//;
	s/<br \/>//gi;
	s/\<b\>(.+?)\<\/b\>/\[b\]$1\[\/b\]/gi;
	s/\<i\>(.+?)\<\/i\>/\[i\]$1\[\/i\]/gi;
	s/\<u\>(.+?)\<\/u\>/\[u\]$1\[\/u\]/gi;
	s/\<li\>(.+?)\<\/li\>/\[\*\]$1\[\/\*\]/gi;
	s/\<center\>(.+?)\<\/center\>/\[center\]$1\[\/center\]/gi;
	s/\<a href=(.+?)\ target=_blank\>(.+?)\<\/a\>/\[url=$1\]$2\[\/url\]/gi;
	s/\<img src=(.+?) \/>/\[img\]$1\[\/img\]/gi;
	s/\<div class=code\>\<pre\>(.+?)\<\/pre\>\<\/div\>/\[code\]$1\[\/code\]/gi;
	s/\<div class=quote\>(.+?)\<\/div\>/\[quote\]$1\[\/quote\]/gi;
	return $_;
}

sub txt2html{
	$_ = $_[0];
	s/\n/<br \/>/gi;
	return $_;
}

sub getdate{
	my $gmt = $_[0];
	my $date = gmtime;
	my @dat = split(' ', $date);
	my @time = split(':',$dat[3]);
	
	my $day = $dat[2];
	my $hour = $time[0]+$gmt;
			
	if($hour < 1)
	{
		$hour = floor($hour+24);
		$day--;
	}
	elsif($hour > 24)
	{
		$hour = floor(((24-$hour)*-1)+1);
		$day++;
	}
	
	return $day.' '.$dat[1].' '.$dat[4].', '.$hour.':'.$time[1];
}

sub array_unique{
	my %seen = ();
	@_ = grep { ! $seen{ $_ }++ } @_;
}

sub getFiles			# This function returns all files from the db folder
{
	if(!(opendir(DH, $_[0])))
	{
		mkdir($config_postsDatabaseFolder, 0755);
	}
	
	my @entriesFiles = (); 		# This one has all files names
	my @entries = (); 			# This one has the content of all files not splitted
	
	foreach(readdir DH)
	{
		unless($_ eq '.' or $_ eq '..' or (!($_ =~ /$config_dbFilesExtension$/)))
		{
			push(@entriesFiles, $_);
		}
	}
	
	@entriesFiles = sort{$b <=> $a}(@entriesFiles);		# Here I order the array in descending order so i show Newest First
	
	foreach(@entriesFiles)
	{
		my $tempContent = '';
		open(FILE, "<".$_[0]."/$_");
		while(<FILE>)
		{
			$tempContent.=$_;
		}
		close FILE;
		push(@entries, $tempContent);
	}
	return @entries;
}

sub getCategories		# This function is to get the categories not repeated in one array
{
	my @categories = ('General');
	my @tempCategories = ();
	if(-d "$config_postsDatabaseFolder")
	{
		my @entries = getFiles($config_postsDatabaseFolder);
		foreach(@entries)
		{
			my @finalEntries = split(/"/, $_);
			push(@tempCategories, $finalEntries[3]);
		}
		@categories = array_unique(@tempCategories);
	}
	return @categories;
}

sub getPages
{
	open (FILE, "<$config_postsDatabaseFolder/pages.$config_dbFilesExtension.page");
	my $pagesContent;
	while(<FILE>)
	{
		$pagesContent.=$_;
	}
	close FILE;
	
	my @pages = split(/-/, $pagesContent);
}

sub getComments
{
	open(FILE, "<$config_commentsDatabaseFolder/latest.$config_dbFilesExtension");
	my $content;
	while(<FILE>)
	{
		$content.=$_;
	}
	close(FILE);
	
	my @comments = split(/'/, $content);	
	@comments = reverse(@comments);			# We want newer first right?
}

if(r('do') eq 'RSS')
{
	my @baseUrl = split(/\?/, 'http://'.$ENV{'HTTP_HOST'}.$ENV{'REQUEST_URI'});
	my $base = $config_baseurl;
	my @entries = getFiles($config_postsDatabaseFolder);
	my $limit;
	
	print header('text/xml'),'<?xml version="1.0" encoding="ISO-8859-1"?>
	<rss version="2.0">
	<channel>
	<title>'.$config_blogTitle.'</title>
	<description>'.$config_metaDescription.'</description>
	<link>'.$config_baseurl.'</link>';
	
	if($config_entriesOnRSS == 0)
	{
		$limit = scalar(@entries);
	}
	else
	{
		$limit = $config_entriesOnRSS;
	}
	
	for(my $i = 0; $i < $limit; $i++)
	{
		my @finalEntries = split(/"/, $entries[$i]);
		my $content = $finalEntries[1];
		$content =~ s/\</&lt;/gi;
		$content =~ s/\>/&gt;/gi;
    $content =~ s/&nbsp;/ /gi;
		print '<item>
		<link>'.$base.'?viewDetailed='.$finalEntries[4].'</link>
		<title>'.$finalEntries[0].'</title>
		<category>'.$finalEntries[3].'</category>
		<description>'.$content.'</description>
		</item>';
	}
	
	print '</channel>
	</rss>';
}
else
{
print header(), '<!DOCTYPE HTML PUBLIC -//W3C//DTD HTML 4.01 Transitional//EN
http://www.w3.org/TR/html4/loose.dtd>
<html>
<head>
<meta http-equiv=Content-Type content=text/html; charset=iso-8859-1>
<meta name="Name" content="'.$config_blogTitle.'" />
<meta name="Revisit-After" content="'.$config_metaRevisitAfter.'" />
<meta name="Keywords" content="'.$config_metaKeywords.'" />
<meta name="Description" content="'.$config_metaDescription.'" />
<title>'.$config_blogTitle.'</title>
<script language="javascript" type="text/javascript">
// FUNCTION BY SMF FORUMS http://www.simplemachines.org
function surroundText(text1, text2, textarea)
{
	// Can a text range be created?
	if (typeof(textarea.caretPos) != "undefined" && textarea.createTextRange)
	{
		var caretPos = textarea.caretPos, temp_length = caretPos.text.length;

		caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == \' \' ? text1 + caretPos.text + text2 + \' \' : text1 + caretPos.text + text2;

		if (temp_length == 0)
		{
			caretPos.moveStart("character", -text2.length);
			caretPos.moveEnd("character", -text2.length);
			caretPos.select();
		}
		else
			textarea.focus(caretPos);
	}
	// Mozilla text range wrap.
	else if (typeof(textarea.selectionStart) != "undefined"){
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
<link href='.$config_currentStyleFolder.'/default.css rel=stylesheet type=text/css>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push([\'_setAccount\', \'UA-22162565-1\']);
  _gaq.push([\'_trackPageview\']);

  (function() {
    var ga = document.createElement(\'script\'); ga.type = \'text/javascript\'; ga.async = true;
    ga.src = (\'https:\' == document.location.protocol ? \'https://ssl\' : \'http://www\') + \'.google-analytics.com/ga.js\';
    var s = document.getElementsByTagName(\'script\')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>	
</head>
<body>
<div class="whitebg">
<h1>DannyArends.nl</h1>
<h2>My Blog</h2>
<table width="95%">
<tr>
  <td colspan=3>
  <i>Last updated: 27 April 2011</i><br>
  [<a href="/Index.cgi" title="The index page of dannyarends.nl"><font color="black">Home</font></a>
  |<a href="/myblog.cgi" title="Blog by Danny Arends"><font color="black">My Blog</font></a>
  |<a href="/Index.cgi?p=Publications" title="Overview of Publication by Danny Arends"><font color="black">Publications</font></a>
  |<a href="/Index.cgi?p=Presentations" title="Overview of Presentations by Danny Arends"><font color="black">Presentations</font></a>
  |<a href="/Index.cgi?p=Drawings" title="Overview of Drawings made by Danny Arends"><font color="black">Drawings</font></a>
  |<a href="/Index.cgi?p=Hobbies" title="Summary of my hobbies"><font color="black">Hobbies</font></a>
  |<a href="/Index.cgi?p=Contact" title="Contact information"><font color="black">Contact</font></a>
  |<a href="/Index.cgi?p=Applet" title="Java applet game created by Danny Arends"><font color="black">Java Applet world</font></a>
  |<a href="/Index.cgi?p=Various" title="Other things that intrest me"><font color="black">Other stuff</font></a>
  |<a href="/Index.cgi?p=Links" title="Links to other web pages"><font color="black">Links</font></a>
  ]<br><hr>
    </td>
</tr>
<tr>
<td valign="top" width="20%" id=menu>
<h3>Blog Menu</h3>
<a href=?do=newEntry>New Entry</a><br>
<a href=?do=archive>Archive</a><br>
<a href="?do=RSS">RSS Feed</a><br>
<h3>Categories</h3>';			# Show Categories on Menu	THIS IS THE MENU SECTION
my @categories = sort(getCategories());
foreach(@categories)
{
	print '<a href="?viewCat='.$_.'">'.$_.'</a><br>';
}
print 'No categories yet.' if scalar(@categories) == 0;
print '<h3>Search</h3>
<form name="form1" method="post" action="myblog.cgi">
<input type="text" name="keyword">
<input type="hidden" name="do" value="search">
<input type="submit" name="Submit" value="Search"><br>
By Title <input name="by" type="radio" value="0" checked> By Content <input name="by" type="radio" value="1">
</form>
<h3>Latest Entries</h3>';

my @entriesOnMenu = getFiles($config_postsDatabaseFolder);
my $i = 0;
foreach(@entriesOnMenu)
{
	if($i <= $config_menuEntriesLimit)
	{
		my @entry = split(/"/, $_);
		my $title = $entry[0];
		my $fileName = $entry[4];
		my @pages = getPages();
		my $do = 1;
		foreach(@pages)
		{
			if($_ == $entry[4])
			{
				$do = 0;
				last;
			}
		}
		
		if($do == 1)
		{
			print '<a href="?viewDetailed='.$fileName.'">'.$title.'</a><br>';
		}
		
		$i++;
	}
}

# Display Pages
my @pages = getPages();

if(scalar(@pages) > 0)
{
	print '<h1>Pages</h1>';
}

foreach(@pages)
{
	my $fileName = $_;
	my $content;
	open(FILE, "<$config_postsDatabaseFolder/$fileName.$config_dbFilesExtension");
	while(<FILE>)
	{
		$content.=$_;
	}
	close FILE;
	my @data = split(/"/, $content);
	my $title = $data[0];
	print '<a href="?viewDetailed='.$fileName.'">'.$title.'</a><br>';
}

if($config_redditAllowed == 1)
{
	# Show the Reddit Button if allowed
	
	print '<h1>Share</h1>
	<a target="_blank" href="http://reddit.com/submit?url=http://'.$ENV{'HTTP_HOST'}.$ENV{'REQUEST_URI'}.'">
	Reddit This <img border="0" src="images/reddit.gif" /></a>';
}

if($config_menuShowLinks == 1)
{
	# Show Some Links Defined on the Configuration
	
	if(@config_menuLinks > 0)
	{
		print '<h1>'.$config_menuLinksHeader.'</h1>';
		foreach(@config_menuLinks)
		{
			my @link = split(/,/, $_);
			print '<a href="'.$link[0].'">'.$link[1].'</a><br>';
		}
	}
}

if($config_showLatestComments == 1)
{
	# Latest comments on the menu
		
	my @comments = getComments();
	
	if(scalar(@comments) > 0)
	{
		print '<h1>Latest Comments</h1>';
	}
	
	my $i = 0;
	
	foreach(@comments)
	{
		if($i <= $config_showLatestCommentsLimit)
		{
			my @entry = split(/"/, $_);
			print '<a href="?viewDetailed='.$entry[4].'" title="Posted by '.$entry[1].'">'.$entry[0].'</a><br>';
			$i++;
		}
	}
	print '<a href="?do=listComments">List All Comments</a>' if scalar(@comments) > 0;
}

if($config_allowCustomHTML == 1)
{
	# Display Custom HTML Defined on the configuration
	
	print $config_customHTML;
}

if(($config_showUsersOnline == 1) || ($config_showHits == 1))
{
	print '<br><br>';
}

if($config_showUsersOnline == 1)
{
	# Show users online
	
	my $remote = $ENV{"REMOTE_ADDR"};
	my $timestamp = time();
	my $timeout = ($timestamp-$config_usersOnlineTimeout);
	
	if((-s "$config_postsDatabaseFolder/online.$config_dbFilesExtension.uo") > (1024*5))		# If its bigger than 0.5 MB, truncate the file and start again
	{
		open(FILE, "+>$config_postsDatabaseFolder/online.$config_dbFilesExtension.uo");
	}
	else
	{
		open(FILE, ">>$config_postsDatabaseFolder/online.$config_dbFilesExtension.uo");
	}
	
	print FILE $remote."||".$timestamp."\n";
	close FILE;
	my @online_array = ();
	my $content;
	open(FILE, "<$config_postsDatabaseFolder/online.$config_dbFilesExtension.uo");
	while(<FILE>)
	{
		$content.=$_;
	}
	close FILE;
	
	my @l = split(/\n/, $content);
	foreach(@l)
	{
		my @f = split(/\|\|/, $_);
		my $ip = $f[0];
		my $time = $f[1];
		if($time >= $timeout)
		{
			push(@online_array, $ip);
		}
	}
	@online_array = array_unique(@online_array);
	print 'Users Online: '.scalar(@online_array).'<br />';
}

if($config_showHits == 1)
{
	# Display Hits
	
	# Check hits
	open(FILE, "<$config_postsDatabaseFolder/hits.$config_dbFilesExtension.hits");
	my $content;
	while(<FILE>)
	{
		$content.=$_;
	}
	close FILE;
			
	# Add hits
	open(FILE, ">$config_postsDatabaseFolder/hits.$config_dbFilesExtension.hits");
	print FILE ++$content;
	close FILE;
	
	print 'Hits: '.$content;
}

print '</td><td valign="top" width="40%"><div id=content class="white">';

foreach(@config_ipBan)
{
	if($ENV{'REMOTE_ADDR'} == $_)
	{
		die($config_bannedMessage);
	}
}

# Start with GETS and POSTS		CONTENT SECTION

if(r('do') eq 'newEntry')
{
	# Add Secure (This page will appear before the add one)
	
	print '<h1>Adding Entry...</h1>
	<form name="form1" method="post" action="myblog.cgi">
	<table>
	<td>Pass</td>
	<td><input name="pass" type="password" id="pass">
	<input name="process" type="hidden" id="process" value="doNewEntry"></td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	<td><input type="submit" name="Submit" value="Login"></td>
	</tr>
	</table>
	</form>';
}elsif(r('process') eq 'doNewEntry'){
	# Blog Add New Entry Form
	
	my $pass = r('pass');
	if($pass eq $config_adminPass)
	{
		my @categories = getCategories();
		print '<h1>Making new entry...</h1>	
		<form name="submitform" method="post" action="myblog.cgi">
		<table>
		<tr>
		<td>Title</td>
		<td><input name=title type=text id=title></td>
		</tr>';
		if($config_useHtmlOnEntries == 0)
		{
			print '<tr>
			<td>&nbsp;</td>
			<td><input type="button" style="width:50px;font-weight:bold;" onClick="surroundText(\'[b]\', \'[/b]\', document.forms.submitform.content); return false;" value="b" />
			<input type="button" style="width:50px;font-style:italic;" onClick="surroundText(\'[i]\', \'[/i]\', document.forms.submitform.content); return false;" value="i" />
			<input type="button" style="width:50px;text-decoration:underline;" onClick="surroundText(\'[u]\', \'[/u]\', document.forms.submitform.content); return false;" value="u" />
			<input type="button" style="width:50px;" onClick="surroundText(\'[url]\', \'[/url]\', document.forms.submitform.content); return false;" value="url" />
			<input type="button" style="width:50px;" onClick="surroundText(\'[img]\', \'[/img]\', document.forms.submitform.content); return false;" value="img" /></td>
			</tr>';
		}
		else
		{
			print '<script src="http://js.nicedit.com/nicEdit.js" type="text/javascript"></script><script type="text/javascript">bkLib.onDomLoaded(nicEditors.allTextAreas);</script>' if($config_useWYSIWYG == 1);
		}
		print '<tr><td>Content<br />(You can use BBCODE)<br /><a href="?do=showSmilies" target="_blank">Show Smilies</a></td>
		<td><textarea name="content" cols='.$config_textAreaCols.'" rows="'.$config_textAreaRows.'" ';
		print ' style="height: 400px; width: 400px;" ' if( ($config_useWYSIWYG == 1) && ($config_useHtmlOnEntries == 1) );
		print ' id="content"></textarea></td></tr><tr><td>Category<br />(Available: ';
		my $i = 1;
		foreach(@categories)
		{
			if($i < scalar(@categories))	# Here we display a comma between categories so is easier to undesrtand
			{
				print $_.', ';
			}
			else
			{
				print $_;
			}
			$i++;
		}
		print ')</td>
		<td><input name="category" type="text" id="category"></td>
		</tr>
		<tr>
		<td>Is A Page <a href="javascript:alert(\'A page is basically a post which is linked in the menu and not displayed normally\')">(?)</a></td>
		<td><input type="checkbox" name="isPage" value="1"></td>
		</tr>
		<tr>
		<td>Pass</td>
		<td><input name="pass" type="password" id="pass">
		<input name="process" type="hidden" id="process" value="newEntry"></td>
		</tr>
		<tr>
		<td>&nbsp;</td>
		<td><input type="submit" name="Submit" value="Add Entry"></td>
		</tr>
		</table>
		</form>';
	}
	else
	{
		print 'Wrong Password';
	}
}
elsif(r('process') eq 'newEntry')
{
	# Blog Add New Entry Page
	
	my $pass = r('pass');
	if($pass eq $config_adminPass)
	{
		my @files = getFiles($config_postsDatabaseFolder);
		my @lastOne = split(/"/, $files[0]);
		my $i = 0;
		
		if($lastOne[4] eq '')
		{
			$i = sprintf("%05d",0);
		}
		else
		{
			$i = sprintf("%05d",$lastOne[4]+1);
		}
		
		unless(-d "$config_postsDatabaseFolder")
		{
			print 'The folder '.$config_postsDatabaseFolder.' does not exists...Creating it...<br />';
			mkdir($config_postsDatabaseFolder, 0755);
		}
		
		open(FILE, ">$config_postsDatabaseFolder/$i.$config_dbFilesExtension");
		my $title = r('title');
		my $content = '';
		if($config_useHtmlOnEntries == 0)
		{
			$content = bbcode(r('content'));
		}
		else
		{
			$content = basic_r('content');
		}
		my $category = r('category');
		my $isPage = r('isPage');
		
		if($title eq '' || $content eq '' || $category eq '')
		{
			die("All fields are neccesary!");
		}
		
		my $date = getdate($config_gmt);
		print FILE $title.'"'.$content.'"'.$date.'"'.$category.'"'.$i;	# 0: Title, 1: Content, 2: Date, 3: Category, 4: FileName
		print 'Your post '.$title.' has been saved. <a href="?page=1">Go to Index</a>';
		close FILE;
		
		if($isPage == 1)
		{
			open(FILE, ">>$config_postsDatabaseFolder/pages.$config_dbFilesExtension.page");
			print FILE $i.'-';
			close FILE;
		}
	}
	else
	{
		print 'Wrong password!';
	}
}
elsif(r('viewCat') ne '')
{
	# Blog Category Display
	
	my $cat = r('viewCat');
	my @entries = getFiles($config_postsDatabaseFolder);
	my @thisCategoryEntries = ();
	my @categories = ();
	foreach(@entries)
	{
		my @split = split(/"/, $_);											# [0] = Title	[1] = Content	[2] = Date	[3] = Category
		if($split[3] eq $cat)
		{
			push(@thisCategoryEntries, $_);
		}
	}
	
	# Pagination - This is the so called Pagination
	my $page = r('p');																# The current page
	if($page eq ''){ $page = 1; }													# Makes page 1 the default page
	my $totalPages = ceil((scalar(@thisCategoryEntries))/$config_entriesPerPage);	# How many pages will be?
	# What part of the array should i show in the page?
	my $arrayEnd = ($config_entriesPerPage*$page);									# The array will start from this number
	my $arrayStart = $arrayEnd-($config_entriesPerPage-1);							# And loop till this number
	# As arrays start from 0, i will lower 1 to these values
	$arrayEnd--;
	$arrayStart--;

	my $i = $arrayStart;															# Start Looping...
	while($i<=$arrayEnd)
	{
		unless($thisCategoryEntries[$i] eq '')
		{
			my @finalEntries = split(/"/, $thisCategoryEntries[$i]);
			my @pages = getPages();
			my $do = 1;
			foreach(@pages)
			{
				if($_ == $finalEntries[4])
				{
					$do = 0;
					last;
				}
			}
			
			if($do == 1)
			{
				print '<h1><a href="?viewDetailed='.$finalEntries[4].'">'.$finalEntries[0].'</a></h1>'.$finalEntries[1].'<br /><br /><center><i>Posted on '.$finalEntries[2].' - Category: <a href="?viewCat='.$finalEntries[3].'">'.$finalEntries[3].'</a><br /><a href="?viewDetailed='.$finalEntries[4].'">Comments</a> - <a href="?edit='.$finalEntries[4].'">Edit</a> - <a href="?delete='.$finalEntries[4].'">Delete</a></i></center><br /><br />';
			}
		}
		$i++;
	}
	# Now i will display the pages
	if($totalPages >= 1)
	{
		print '<center> Pages: ';
	}
	else
	{
		print '<center> No posts under this category.';
	}
	
	my $startPage = $page == 1 ? 1 : ($page-1);
	my $displayed = 0;
	for(my $i = $startPage; $i <= (($page-1)+$config_maxPagesDisplayed); $i++)
	{
		if($i <= $totalPages)
		{
			if($page != $i)
			{
				if($i == (($page-1)+$config_maxPagesDisplayed) && (($page-1)+$config_maxPagesDisplayed) < $totalPages)
				{
					print '<a href="?viewCat='.$cat.'&p='.$i.'">['.$i.']</a> ...';
				}
				elsif($startPage > 1 && $displayed == 0)
				{
					print '... <a href="?viewCat='.$cat.'&p='.$i.'">['.$i.']</a> ';
					$displayed = 1;
				}
				else
				{
					print '<a href="?viewCat='.$cat.'&p='.$i.'">['.$i.']</a> ';
				}
			}
			else
			{
				print '['.$i.'] ';
			}
		}
	}
	print '</center>';
}
elsif(r('edit') ne '')
{
	# Edit Secure (This page will appear before the edit one)
		
	my $fileName = r('edit');
	print '<h1>Editing Entry...</h1>
	<form name="form1" method="post" action="myblog.cgi">
	<table>
	<td>Pass</td>
	<td><input name="pass" type="password" id="pass">
	<input name="process" type="hidden" id="process" value="editSecured">
	<input name="fileName" type="hidden" id="fileName" value="'.$fileName.'"></td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	<td><input type="submit" name="Submit" value="Edit Entry"></td>
	</tr>
	</table>
	</form>';
}
elsif(r('process') eq 'editSecured')
{
	# Edit Entry Page
	
	my $pass = r('pass');
	
	if($pass eq $config_adminPass)
	{
		my $id = r('fileName');
		my $tempContent = '';
		open(FILE, "<$config_postsDatabaseFolder/$id.$config_dbFilesExtension");
		while(<FILE>)
		{
			$tempContent.=$_;
		}
		close FILE;
		my @entry = split(/"/, $tempContent);
		my $fileName = $entry[4];
		my $title = $entry[0];
		my $content = $entry[1];
		my $category = $entry[3];
		print '<h1>Editing Entry...</h1>
		<form name="submitform" method="post" action="myblog.cgi">
		<table>
		<tr>
		<td>Title</td>
		<td><input name=title type=text id=title value="'.$title.'"></td>
		</tr>';
		if($config_useHtmlOnEntries == 0)
		{
			print '<tr>
			<td>&nbsp;</td>
			<td><input type="button" style="width:50px;font-weight:bold;" onClick="surroundText(\'[b]\', \'[/b]\', document.forms.submitform.content); return false;" value="b" />
			<input type="button" style="width:50px;font-style:italic;" onClick="surroundText(\'[i]\', \'[/i]\', document.forms.submitform.content); return false;" value="i" />
			<input type="button" style="width:50px;text-decoration:underline;" onClick="surroundText(\'[u]\', \'[/u]\', document.forms.submitform.content); return false;" value="u" />
			<input type="button" style="width:50px;" onClick="surroundText(\'[url]\', \'[/url]\', document.forms.submitform.content); return false;" value="url" />
			<input type="button" style="width:50px;" onClick="surroundText(\'[img]\', \'[/img]\', document.forms.submitform.content); return false;" value="img" /></td>
			</tr>';
		}
		else
		{
			print '<script src="http://js.nicedit.com/nicEdit.js" type="text/javascript"></script><script type="text/javascript">bkLib.onDomLoaded(nicEditors.allTextAreas);</script>' if($config_useWYSIWYG == 1);
		}		
		print '<tr><td>Content<br /><a href="?do=showSmilies" target="_blank">Show Smilies</a></td><td><textarea name=content cols='.$config_textAreaCols.'"';
		print ' style="height: 400px; width: 400px;" ' if( ($config_useWYSIWYG == 1) && ($config_useHtmlOnEntries == 1) );
		print ' rows="'.$config_textAreaRows.'" id="content">';
		if($config_useHtmlOnEntries == 0)
		{
			print bbdecode($content);
		}
		else
		{
			print $content;
		}
		print '</textarea></td></tr><tr><td>Category<br />(Available: ';
		my @categories = getCategories();
		my $i = 1;
		foreach(@categories)
		{
			if($i < scalar(@categories))	# Here we display a comma between categories so is easier to undesrtand
			{
				print $_.', ';
			}
			else
			{
				print $_;
			}
			$i++;
		}
		print ')</td>
		<td><input name="category" type="text" id="category" value="'.$category.'"></td>
		</tr>
		<tr>
		<td>Pass</td>
		<td><input name="pass" type="password" id="pass">
		<input name="process" type="hidden" id="process" value="editEntry">
		<input name="fileName" type="hidden" id="fileName" value="'.$fileName.'"></td>
		</tr>
		<tr>
		<td>&nbsp;</td>
		<td><input type="submit" name="Submit" value="Edit Entry"></td>
		</tr>
		</table>
		</form>';
	}
	else
	{
		print 'Wrong Pass.';
	}
}
elsif(r('process') eq 'editEntry')
{
	# Edit process
	
	my $pass = r('pass');
	if($pass eq $config_adminPass)
	{
		my $title = r('title');
		my $content = '';
		if($config_useHtmlOnEntries == 0)
		{
			$content = bbcode(r('content'));
		}
		else
		{
			$content = basic_r('content');
		}
		my $category = r('category');
		my $fileName = r('fileName');
		
		open(FILE, "+>$config_postsDatabaseFolder/$fileName.$config_dbFilesExtension");
		
		if($title eq '' or $content eq '' or $category eq '')
		{
			die("All fields are neccesary!");
		}
		
		my $date = getdate($config_gmt);
		print FILE $title.'"'.$content.'"'.$date.'"'.$category.'"'.$fileName;	# 0: Title, 1: Content, 2: Date, 3: Category, 4: FileName
		print 'Your post '.$title.' has been edited. <a href="?viewDetailed='.$fileName.'">Go Back</a>';
		close FILE;
	}
	else
	{
		print 'Wrong password!';
	}
}
elsif(r('delete') ne '')
{
	# Delete Entry Page
	
	my $fileName = r('delete');
	print '<h1>Deletting Entry...</h1>
	<form name="form1" method="post" action="myblog.cgi">
	<table>
	<td>Pass</td>
	<td><input name="pass" type="password" id="pass">
	<input name="process" type="hidden" id="process" value="deleteEntry">
	<input name="fileName" type="hidden" id="fileName" value="'.$fileName.'"></td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	<td><input type="submit" name="Submit" value="Delete Entry"></td>
	</tr>
	</table>
	</form>';
}
elsif(r('process') eq 'deleteEntry')
{
	# Delete Entry Process
	
	my $pass = r('pass');

	if($pass eq $config_adminPass)
	{
		my $fileName = r('fileName');
		my @pages = getPages();
		my $isPage = 0;
		foreach(@pages)
		{
			if($_ == $fileName)
			{
				$isPage = 1;
				last;
			}
		}
		
		my $newPages;
		if($isPage == 1)
		{
			foreach(@pages)
			{
				if($_ != $fileName)
				{
					$newPages.=$_.'-';
				}
			}
			
			open(FILE, "+>$config_postsDatabaseFolder/pages.$config_dbFilesExtension.page");
			print FILE $newPages;
			close FILE;
		}
		
		unlink("$config_postsDatabaseFolder/$fileName.$config_dbFilesExtension");		
		print 'Entry deletted. <a href="?page=1">Go to Index</a>';
	}
	else
	{
		print 'Wrong password!';
	}
}
elsif(r('do') eq 'search')
{
	# Search Function

	my $keyword = r('keyword');
	my $do = 1;
	
	if(length($keyword) < $config_searchMinLength)
	{
		print 'The keyword must be at least '.$config_searchMinLength.' characters long!';
		$do = 0;
	}
	
	my $by = r('by');							# This can be 0 (by title) or 1 (by id) based on the splitted array
	if(($by != 0) && ($by != 1)){ $by = 0; }	# Just prevention from CURL or something...
	my $sBy = $by == 0 ? 'Title' : 'Content';	# This is a shorter way of "my $sBy = ''; if($by == 0) { $sBy = 'Title'; } else { $sBy = 'Content'; }"
	
	if($do == 1)
	{
		print 'Searching for '.$keyword.' by '.$sBy.'...<br /><br />';
		my @entries = getFiles($config_postsDatabaseFolder);
		my $matches = 0;
		foreach(@entries)
		{
			my @currEntry = split(/"/, $_);
			if(($currEntry[$by] =~ m/$keyword/i))
			{
				print '<a href="?viewDetailed='.$currEntry[4].'">'.$currEntry[0].'</a><br />';
				$matches++;
			}
		}
		print '<br /><center>'.$matches.' Matches Found.</center>';
	}
}
elsif(r('viewDetailed') ne '')
{
	# Display Individual Entry
	
	my $fileName = r('viewDetailed');
	my $do = 1;
	
	unless(-e "$config_postsDatabaseFolder/$fileName.$config_dbFilesExtension")
	{
		print 'Sorry, that entry does not exists or it has been deletted.';
		$do = 0;
	}
	
	# First Display Entry
	if($do == 1)		# Checks if the file exists before doing all this
	{
		my $tempContent;
		open(FILE, "<$config_postsDatabaseFolder/$fileName.$config_dbFilesExtension");
		while(<FILE>)
		{
			$tempContent.=$_;
		}
		close FILE;
		my @entry = split(/"/, $tempContent);
		my $fileName = $entry[4];
		my $title = $entry[0];
		my $category = $entry[3];
		print '<h1><a href="?viewDetailed='.$entry[4].'">'.$entry[0].'</a></h1>'.$entry[1].'<br /><br /><center><i>Posted on '.$entry[2].' - Category: <a href="?viewCat='.$entry[3].'">'.$entry[3].'</a><br /><a href="?edit='.$entry[4].'">Edit</a> - <a href="?delete='.$entry[4].'">Delete</a></i></center><br /><br />';
		
		# Now Display Comments
		unless(-d $config_commentsDatabaseFolder)		# Does the comments folder exists? We will save comments there...
		{
			mkdir($config_commentsDatabaseFolder, 0755);
		}
	
		my $content = '';
		open(FILE, "<$config_commentsDatabaseFolder/$fileName.$config_dbFilesExtension");
		while(<FILE>)
		{
			$content.=$_;
		}
		close FILE;
		
		if($content eq '')
		{
			print 'No comments posted yet.';
		}
		else
		{
			print '<h1>Comments:</h1>';
			
			my @comments = split(/'/, $content);
			
			if($config_commentsDescending == 1)
			{
				@comments = reverse(@comments);			# We want the newest first? (Edit at the top on the configuration if you do want newest first)
			}
			
			my $i = 0;
			foreach(@comments)
			{
				my @comment = split(/"/, $_);
				my $title = $comment[0];
				my $author = $comment[1];
				my $content = $comment[2];
				my $date = $comment[3];
				print 'Posted on <b>'.$date.'</b> by <b>'.$author.'</b><br /><i>'.$title.'</i><br />';
				if($config_bbCodeOnCommentaries == 0)
				{
					print txt2html($content);
				}
				else
				{
					print bbcode($content);
				}
				print '<br /><a href="?deleteComment='.$fileName.'.'.$i.'">Delete</a><br /><br />';
				$i++;	# This is used for deletting comments, to i know what comment number is it :]
			}
		}
		# Add comment form
		if($config_allowComments == 1)
		{
			print '<br /><br /><h1>Add Comment</h1>
			<form name="submitform" method="post" action="myblog.cgi">
			<table>
			<tr>
			<td>Title</td>
			<td><input name="title" type="text" id="title"></td>
			</tr>
			<tr>
			<td>Author</td>
			<td><input name="author" type="text" id="author"></td>
			</tr>';
			
			print '<tr>
			<td>&nbsp;</td>
			<td><input type="button" style="width:50px;font-weight:bold;" onClick="surroundText(\'[b]\', \'[/b]\', document.forms.submitform.content); return false;" value="b" />
			<input type="button" style="width:50px;font-style:italic;" onClick="surroundText(\'[i]\', \'[/i]\', document.forms.submitform.content); return false;" value="i" />
			<input type="button" style="width:50px;text-decoration:underline;" onClick="surroundText(\'[u]\', \'[/u]\', document.forms.submitform.content); return false;" value="u" />
			<input type="button" style="width:50px;" onClick="surroundText(\'[url]\', \'[/url]\', document.forms.submitform.content); return false;" value="url" />
			<input type="button" style="width:50px;" onClick="surroundText(\'[img]\', \'[/img]\', document.forms.submitform.content); return false;" value="img" /></td>
			</tr>' if $config_allowBBcodeButtonsOnComments == 1 && $config_bbCodeOnCommentaries == 1;
			
			print '<tr>
			<td>Content<br /><a href="?do=showSmilies" target="_blank">Show Smilies</a></td>
			<td><textarea name="content" id="content" cols="'.$config_textAreaCols.'" rows="'.$config_textAreaRows.'"></textarea></td>
			</tr>
			<tr>';
			
			if($config_commentsSecurityCode == 1)
			{
				my $code = '';
				if($config_onlyNumbersOnCAPTCHA == 1)
				{
					$code = substr(rand(999999),1,$config_CAPTCHALenght);
				}
				else
				{
					$code = uc(substr(crypt(rand(999999), $config_randomString),1,$config_CAPTCHALenght));
				}
				$code =~ s/\.//;
				$code =~ s/\///;
				print '<td>Security Code</td>
				<td><font face="Verdana, Arial, Helvetica, sans-serif" size="2">'.$code.'</font><input name="originalCode" value="'.$code.'" type="hidden" id="originalCode"></td>
				</tr>
				<tr>
				<td></td>
				<td><input name="code" type="text" id="code"></td>
				</tr>';
			}
			
			print '<tr>
			<td>'.$config_commentsSecurityQuestion.'</td>
			<td><input name="question" type="text" id="question"></td>
			</tr>
			<tr>' if $config_securityQuestionOnComments == 1;
			
			print '<tr>
			<td>Password (So people cannot steal your identity)</td>
			<td><input name="pass" type="password" id="pass"></td>
			</tr>
			<tr>
			<td>&nbsp;</td>
			<td><input type="submit" name="Submit" value="Add Comment"><input name="sendComment" value="'.$fileName.'" type="hidden" id="sendComment"></td>
			</tr>
			</table>
			</form>';
		}
	}
}
elsif(r('sendComment') ne '')
{
	# Send Comment Process
	
	my $fileName = r('sendComment');
	my $title = r('title');
	my $author = r('author');
	my $content = r('content');
	my $pass = r('pass');
	my $date = getdate($config_gmt);
	my $do = 1;
	my $triedAsAdmin = 0;
	
	if($title eq '' || $author eq '' || $content eq '' || $pass eq '')
	{
		print 'All fields are neccessary. Go back and fill them all.';
		$do = 0;
	}
	
	if($config_commentsSecurityCode == 1)
	{
		my $code = r('code');
		my $originalCode = r('originalCode');
		
		unless($code eq $originalCode)
		{
			print 'Security Code does not match. Please, try again';
			$do = 0;
		}
	}
	
	if($config_securityQuestionOnComments == 1)
	{
		my $question = r('question');
		unless(lc($question) eq lc($config_commentsSecurityAnswer))
		{
			print 'Incorrect security answer. Please, try again.';
			$do = 0;
		}
	}
	
	my $hasPosted = 0;					# This is to see if the user has posted already, so we add him/her to the database :]
	
	foreach(@config_commentsForbiddenAuthors)
	{
		if($_ eq $author)
		{
			unless($pass eq $config_adminPass)		# Prevent users from using nicks like "admin"
			{
				$do = 0;
				print 'Wrong password for using '.$_.' as nickname';
				last;
			}
			else
			{
				$hasPosted = 1;
			}
			$triedAsAdmin = 1;
		}
	}
	
	# Start of author checking, for identity security
	open(FILE, "<$config_commentsDatabaseFolder/users.$config_dbFilesExtension.dat");
	my $data = '';
	while(<FILE>)
	{
		$data.=$_;
	}
	close(FILE);
	
	if($triedAsAdmin == 0)
	{
		my @users = split(/"/, $data);
		foreach(@users)
		{
			my @data = split(/'/, $_);
			if($author eq $data[0])
			{
				$hasPosted = 1;
				if(crypt($pass, $config_randomString) ne $data[1])
				{
					$do = 0;
					print 'The username '.$author.' is already taken and that password is incorrect. Please choose other author or try again.';
				}
				last;
			}
		}
	}
	
	if($hasPosted == 0)
	{
		open(FILE, ">>$config_commentsDatabaseFolder/users.$config_dbFilesExtension.dat");
		print FILE $author."'".crypt($pass, $config_randomString).'"';
		close FILE;
		print 'You are a new user posting here... You will be added to a database so nobody can steal your identity. Remember your password!<br>';
	}
	# End of author checking, start adding comment
	
	if($do == 1)
	{	
		if($title eq '' or $author eq '' or $content eq '')
		{
			print 'All fields are neccessary.';
		}
		else
		{
			if(length($content) > $config_commentsMaxLenght)
			{
				print 'The content is too long! Max characters is '.$config_commentsMaxLenght.' you typed '.length($content);
			}
			else
			{
				my $content = $title.'"'.$author.'"'.$content.'"'.$date.'"'.$fileName."'";
				
				# Add comment
				open(FILE, ">>$config_commentsDatabaseFolder/$fileName.$config_dbFilesExtension");
				print FILE $content;
				close FILE;
				
				# Add coment number to a file with latest comments				
				open(FILE, ">>$config_commentsDatabaseFolder/latest.$config_dbFilesExtension");
				print FILE $content;
				close FILE;
				
				print 'Comment added. Thanks '.$author.'!<br /><center><a href="?viewDetailed='.$fileName.'">Go Back</a></center>';
				
				# If Comment Send Mail is active
				if($config_sendMailWithNewComment == 1)
				{
					my $content = "Hello, i am sending this mail beacuse $author commented on your blog: http://".$ENV{'HTTP_HOST'}.$ENV{'REQUEST_URI'}."\nTitle: $title\nComment: $content\nDate: $date\n\nRemember you can disallow this option changing the ".'$config_sendMailWithNewComment Variable to 0';
					open (MAIL,"|/usr/lib/sendmail -t");
					print MAIL "To: $config_sendMailWithNewCommentMail\n";
					print MAIL "From: PPLOG \n";
					print MAIL "Subject: New Comment on your PPLOG Blog\n\n";
					print MAIL $content;
					close(MAIL);
				}
			}
		}
	}
}
elsif(r('deleteComment') ne '')
{
	# Delete Comment
	
	my $data = r('deleteComment');
	
	print '<h1>Deletting Comment...</h1>
	<form name="form1" method="post" action="myblog.cgi">
	<table>
	<td>Pass</td>
	<td><input name="pass" type="password" id="pass">
	<input name="process" type="hidden" id="process" value="deleteComment">
	<input name="data" type="hidden" id="data" value="'.$data.'"></td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	<td><input type="submit" name="Submit" value="Delete Entry"></td>
	</tr>
	</table>
	</form>';
}
elsif(r('process') eq 'deleteComment')
{
	# Delete Comment Process
	
	my $pass = r('pass');
	if($pass eq $config_adminPass)
	{
		my $data = r('data');
		my @info = split(/\./, $data);
		my $fileName = $info[0];
		my $part = $info[1];
		my $commentToDelete;
		
		my $content = '';
		open(FILE, "<$config_commentsDatabaseFolder/$fileName.$config_dbFilesExtension");
		while(<FILE>)
		{
			$content.=$_;
		}
		close FILE;
		
		my @comments = split(/'/, $content);
		
		if($config_commentsDescending == 1)
		{
			@comments = reverse(@comments);
		}
		
		my $newContent = '';
		
		my $i = 0;
		my @newComments =();
		foreach(@comments)
		{
			if($i != $part)
			{
				push(@newComments, $_);
			}
			else
			{
				$commentToDelete = $_;
			}
			$i++;
		}
		
		if($i == 1)		# There was only 1 comment
		{
			unlink("$config_commentsDatabaseFolder/$fileName.$config_dbFilesExtension");
		}
		else
		{		
			@newComments = reverse(@newComments);
			
			foreach(@newComments){
				$newContent.=$_."'";
			}
			
			open(FILE, "+>$config_commentsDatabaseFolder/$fileName.$config_dbFilesExtension");	# Open for writing, and delete everything else
			print FILE $newContent;
			close FILE;
		}
		
		# Now delete comment from the latest comments file where all comments are saved
		open(FILE, "<$config_commentsDatabaseFolder/latest.$config_dbFilesExtension");
		$newContent = '';
		while(<FILE>)
		{
			$newContent.=$_;
		}
		close FILE;
		
		my @commentslist = split(/'/, $newContent);
		my $finalCommentsToAdd;
		foreach(@commentslist)
		{
			unless($_ eq $commentToDelete)
			{
				$finalCommentsToAdd.=$_."'";
			}
		}
		
		open(FILE, "+>$config_commentsDatabaseFolder/latest.$config_dbFilesExtension");	# Open for writing, and delete everything else
		print FILE $finalCommentsToAdd;
		close FILE;
		
		# Finally print this
		print 'Comment Deletted. <a href="?viewDetailed='.$fileName.'">Go Back</a>';
	}
	else
	{
		print 'Wrong password!';
	}
}
elsif(r('do') eq 'archive')
{
	# Show blog archive
	
	print '<h1>Archive</h1>';
	my @entries = getFiles($config_postsDatabaseFolder);
	print 'No entries created yet.' if scalar(@entries) == 0;
	# Split the data in the post so i have them in this format "13 Dic 2008, 24:11|0001|Entry title" date|fileName|entryTitle
	my @dates = map { my @d = split(/"/, $_); $d[2].'|'.$d[4].'|'.$d[0]; } @entries;
	my @years;
	foreach(@dates)
	{
		my @date = split(/\|/, $_);
		my @y = split(/\s/, $date[0]);
		$y[2] =~ s/,//;
		if($y[2] =~ /^\d+$/)
		{
			push(@years, $y[2]);
		}
	}
	@years = reverse(sort(array_unique(@years)));
	for my $actualYear(@years)
	{
		print '<b>Year '.$actualYear.'</b><br />';
		# Now i make my hash with the empty months, why define them? because this is the order they will be executed
		my %months = ('Jan'=>'', 'Feb'=>'', 'Mar'=>'', 'Apr'=>'', 'May'=>'','Jun'=>'','Jul'=>'','Aug'=>'','Sep'=>'','Oct'=>'','Nov'=>'','Dic'=>'');
		# Array with all entries from that year
		my @entries = grep { /$actualYear/; } @dates;
		# Now assign the post number to the hash
		foreach(@entries)
		{ 
			my @d = split(/\s/, $_);
			my @e = split(/\|/, $_);
			$months{$d[1]} .= $e[0].'|'.$e[1].'|'.$e[2].'&-;';
		}
		# Now i have my months hash with a string to be splitted into all posts from that month
		while(my($k, $v) = each(%months))
		{
			unless($k =~ /^\d/)
			{
				print '<br /><b>'.$k.':</b><br /><table>' unless $v eq '';
				# Here are all entries from this month, sort them in ascending order, oldest first
				my @entries = sort{$a <=> $b}reverse((split(/&-;/, $months{$k})));	# Why reverse if then im sorting, well so days are in ascending order
				foreach(@entries)
				{
					my @data = split(/\|/, $_);
					my @d = map {my @e = split(/\s/, $_); $e[0]} split(/,/, $data[0]);
					print '<tr>
					<td>Day '.$d[0].':</td>
					<td><a href="?viewDetailed='.$data[1].'">'.$data[2].'</a></td>
					</tr>';
				}
				print '</table>' unless $v eq '';
			}
		}
	}
}
elsif(r('do') eq 'listComments')
{
	print '<h1>Listing All Comments</h1>';
	my @comments = getComments();
	# This is pagination... Again :]
	my $page = r('page');												# The current page
	if($page eq ''){ $page = 1; }										# Makes page 1 the default page (Could be... $page = 1 if $page eq '')
	my $totalPages = ceil((scalar(@comments))/$config_commentsPerPage);	# How many pages will be?
	# What part of the array should i show in the page?
	my $arrayEnd = ($config_commentsPerPage*$page);						# The array will start from this number
	my $arrayStart = $arrayEnd-($config_commentsPerPage-1);				# And loop till this number
	# As arrays start from 0, i will lower 1 to these values
	$arrayEnd--;
	$arrayStart--;
	my $i = $arrayStart;												# Start Looping...
	if(scalar(@comments) > 0)
	{
		print '<table width="100%"><tr><td><i>Comment Title</i></td><td><i>Comment Author</i></td></tr>';
	}
	else
	{
		print 'No comments posted yet.';
	}
	while($i<=$arrayEnd)
	{
		unless($comments[$i] eq '')
		{
			my @finalEntries = split(/"/, $comments[$i]);
			my @pages = getPages();
			my $do = 1;
			foreach(@pages)
			{
				if($_ == $finalEntries[4])
				{
					$do = 0;
					last;
				}
			}
			
			if($do == 1)
			{
				print '<tr><td><a href="?viewDetailed='.$finalEntries[4].'">'.$finalEntries[0].'</a></td><td><b>'.$finalEntries[1].'</b></td></tr>';
			}
		}
		$i++;
	}
	# Now i will display the pages
	print '</table><center> Pages: ' if scalar(@comments) > 0;
	my $startPage = $page == 1 ? 1 : ($page-1);
	my $displayed = 0;
	for(my $i = $startPage; $i <= (($page-1)+$config_maxPagesDisplayed); $i++)
	{
		if($i <= $totalPages)
		{
			if($page != $i)
			{
				if($i == (($page-1)+$config_maxPagesDisplayed) && (($page-1)+$config_maxPagesDisplayed) < $totalPages)
				{
					print '<a href="?do=listComments&page='.$i.'">['.$i.']</a> ...';
				}
				elsif($startPage > 1 && $displayed == 0)
				{
					print '... <a href="?do=listComments&page='.$i.'">['.$i.']</a> ';
					$displayed = 1;
				}
				else
				{
					print '<a href="?do=listComments&page='.$i.'">['.$i.']</a> ';
				}
			}
			else
			{
				print '['.$i.'] ';
			}
		}
	}
	print '</center>';
}
elsif(r('do') eq 'showSmilies')
{
	if(-d "$config_smiliesFolder")
	{
		if(opendir(DH, $config_smiliesFolder))
		{
			my @smilies;
			print '<h1>Smilies</h1><table width="100%"><tr><td>Smilie</td><td>Code</td></tr>';
			@smilies = grep {/gif/ || /jpg/ || /png/;} readdir(DH);
			foreach(@smilies)
			{
				my @n = split(/\./, $_);
				print '<tr><td><img src="'.$config_smiliesFolderName.'/'.$_.'" /></td><td>:'.$n[0].':</td></tr>';
			}
			print '</table>';
		}
		else
		{
			print 'Error opening '.$config_smiliesFolder.' folder.';
		}
	}
	else
	{
		print 'The admin owner did not allow smilies for this blog.';
	}
}
else
{
	# Blog Main Page
	my @entries = getFiles($config_postsDatabaseFolder);
	if(scalar(@entries) != 0)
	{
		# Pagination - This is the so called Pagination
		my $page = r('page');												# The current page
		if($page eq ''){ $page = 1; }										# Makes page 1 the default page
		my $totalPages = ceil((scalar(@entries))/$config_entriesPerPage);	# How many pages will be?
		# What part of the array should i show in the page?
		my $arrayEnd = ($config_entriesPerPage*$page);						# The array will start from this number
		my $arrayStart = $arrayEnd-($config_entriesPerPage-1);				# And loop till this number
		# As arrays start from 0, i will lower 1 to these values
		$arrayEnd--;
		$arrayStart--;

		my $i = $arrayStart;												# Start Looping...
		while($i<=$arrayEnd)
		{
			unless($entries[$i] eq '')
			{
				my @finalEntries = split(/"/, $entries[$i]);
				my @pages = getPages();
				my $do = 1;
				foreach(@pages)
				{
					if($_ == $finalEntries[4])
					{
						$do = 0;
						last;
					}
				}
				
				if($do == 1)
				{
					# This is for displaying how many comments are posted on that entry
					my $commentsLink;
					my $content;
					open(FILE, "<$config_commentsDatabaseFolder/$finalEntries[4].$config_dbFilesExtension");
					while(<FILE>){$content.=$_;}
					close FILE;
					
					my @comments = split(/'/, $content);
					if(scalar(@comments) == 0)
					{
						$commentsLink = 'No comments';
					}
					elsif(scalar(@comments) == 1)
					{
						$commentsLink = '1 Comment';
					}
					else
					{
						$commentsLink = scalar(@comments).' Comments';
					}
					
					print '<h1><a href="?viewDetailed='.$finalEntries[4].'">'.$finalEntries[0].'</a></h1>'.$finalEntries[1].'<br /><br /><center><i>Posted on '.$finalEntries[2].' - Category: <a href="?viewCat='.$finalEntries[3].'">'.$finalEntries[3].'</a><br /><a href="?viewDetailed='.$finalEntries[4].'">'.$commentsLink.'</a> - <a href="?edit='.$finalEntries[4].'">Edit</a> - <a href="?delete='.$finalEntries[4].'">Delete</a></i></center><br /><br />';
				}
			}
			$i++;
		}
		# Now i will display the pages
		print '<center> Pages: ';
		my $startPage = $page == 1 ? 1 : ($page-1);
		my $displayed = 0;
		for(my $i = $startPage; $i <= (($page-1)+$config_maxPagesDisplayed); $i++){
			if($i <= $totalPages)
			{
				if($page != $i)
				{
					if($i == (($page-1)+$config_maxPagesDisplayed) && (($page-1)+$config_maxPagesDisplayed) < $totalPages)
					{
						print '<a href="?page='.$i.'">['.$i.']</a> ...';
					}
					elsif($startPage > 1 && $displayed == 0)
					{
						print '... <a href="?page='.$i.'">['.$i.']</a> ';
						$displayed = 1;
					}
					else
					{
						print '<a href="?page='.$i.'">['.$i.']</a> ';
					}
				}
				else
				{
					print '['.$i.'] ';
				}
			}
		}
		print '</center>';
	}else{
		print 'No entries created. Why dont you <a href="?do=newEntry">make one</a>?';
	}
}
print '</td><td valign="top"></td></tr><tr><td colspan=3><div align="center" id=footer>Copyright '.$config_blogTitle.' 2011 - All Rights Reserved'; print ', all posts are using GMT '.$config_gmt if $config_showGmtOnFooter == 1; print '</div></td></tr></table></div></body></html>';
}
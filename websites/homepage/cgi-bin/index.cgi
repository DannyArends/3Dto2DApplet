#!/usr/bin/perl -w

#
# server.cgi
#
# copyright (c) 2009-2010, Danny Arends
# last modified Dec, 2010
# first written Dec, 2010
#
# Start by pushing the location of the includes
BEGIN {
  push @INC,"./cgi-bin";
}

use strict;
use Socket;
use CGI qw(:standard);

#Global Variables across all includes
our %form = ();
our @paramnames = ('');
our $write_location = "dist/write/";
our $data_location = "dist/data/";

our $title = "Management console";
our $subtitle = "Management part of the web site";
our $theme = "/themes/default.css";
our $email = "Danny.Arends\@gmail.com";

#include our own files & functions
require "utils.cgi";
require "data.cgi";
require "user.cgi";

#Constants

#Variables
$form{"online"} = '';
$form{"list_files"} = '';

# Print a http header
printHTTPHeader('NOINDEX,FOLLOW');
receivePost();
writeIPtoLog();
print "
<div id=\"container\" class=\"centre maxwidth\">
			<div id=\"head\" class=\"maxwidth\">
				<a href=\"/Index.cgi?p=Index\" class=\"logo\"><span class=\"noview\">Danny Arends</span></a>
				<div id=\"menu\">
					<ul>
						<li class=\"home\"><a href=\"/Index.cgi\"><span class=\"noview\">home</span></a></li>
						<li class=\"blog\"><a href=\"/Index.cgi?p=Blog\"><span class=\"noview\">blog</span></a></li>
						<li class=\"research\"><a href=\"/Index.cgi?p=Research\"><span class=\"noview\">research</span></a></li>
						<li class=\"personal\"><a href=\"/Index.cgi?p=Personal\"><span class=\"noview\">personal</span></a></li>
						<li class=\"contact\"><a href=\"/Index.cgi?p=Contact\"><span class=\"noview\">contact</span></a></li>
					</ul>
				</div>
			</div>
			<div class=\"printlogo\"><img src=\"etc/img/logo.png\" alt=\"Dannyarends.nl\"	width=\"200\" height=\"32\"></div>
			<div id=\"content\" class=\"maxwidth\">
				<div class=\"top maxwidth\"></div>
				<div class=\"mid\">
					<div class=\"left\">
";
if($form{"error"} ne ""){
	# Print an error
	printError();
}else{
	if($form{"p"} ne ""){
		if(lc($form{"p"}) eq "register"){
			print "<h3>Please Register</h3>"."\n";
			create_html_registrar();
		}
		print "Page requested via rest: " . $form{"p"} . "<br/>\n"; 
	}else{
		print "<p>Please Login to continue</p>"."\n";
		create_html_login();
		print "Please <a href='?page=register'><font color=\"black\">Register</font></a>"."\n";
	}
}

print "</div>
					<div class=\"right\">";
print "						<div class=\"contact\">
							<h3>Contact</h3>
							<p>
							Faculty of Mathematics and Natural Sciences<br>
							Bioinformatics - Groninger Institute for Biomolecular Sciences & Biotechnology<br>
							<br>
							Nijenborgh 7<br>
							9747 AG Groningen<br>
							The Netherlands<br>
							<br>
							+31 50 363 8082<br>
							<a href=\"mailto:danny.arends\@gmail.com\">danny.arends\@gmail.com</a>
							</p>
						</div>";
print "					<div id=\"social\">
							<ul>
								<li class=\"social hyves\"><a href=\"http://justdanny.hyves.nl/\" target=\"_blank\"><span class=\"noview\">Hyves</span></a></li>
								<li class=\"social twitter\"><a href=\"http://twitter.com/#!/DannyArends\" target=\"_blank\"><span class=\"noview\">Twitter</span></a></li>
								<li class=\"social facebook\"><a href=\"http://www.facebook.com/Arends.Danny\" target=\"_blank\"><span class=\"noview\">Facebook</span></a></li>
								<li class=\"social linkedin\"><a href=\"http://nl.linkedin.com/in/dannyarends\" target=\"_blank\"><span class=\"noview\">LinkedIn</span></a></li>
								<li class=\"social github\"><a href=\"https://github.com/DannyArends\" target=\"_blank\"><span class=\"noview\">GitHub</span></a></li>
							</ul>
						</div>
					</div>
					<br class=\"clear\">
				</div>
				<div class=\"bottom maxwidth\"></div>
			</div>
			<div id=\"footer\" class=\"maxwidth\">
				<div class=\"top maxwidth\"></div>
				<div class=\"mid\">
					<div class=\"sitemap\">
						<h3>dannyarends.nl / Contents</h3>
						<ul class=\"hoofd\">
							<li>";
								print '<a href="/Index.cgi?p=blog">Blog</a>';
print " 					
							<ul class=\"sub\">
									<li><a href=\"/Index.cgi?p=blog\">My blog</a></li>
								</ul>
							</li>
							<li>
								<a href=\"/Index.cgi?p=Research\">Research</a>
								<ul class=\"sub\">
									<li><a href=\"/Index.cgi?p=Publications\">Publications</a></li>
									<li><a href=\"/Index.cgi?p=Presentations\">Presentations</a></li>
									<li><a href=\"/Index.cgi?p=Links\">Links</a></li>
								</ul>
							</li>
							<li>
								<a href=\"/Index.cgi?p=Personal\">Personal</a>
								<ul class=\"sub\">
                  					<li><a href=\"/Index.cgi?p=Hobbies\">Hobbies</a></li>
                  					<li><a href=\"/Index.cgi?p=Oscar\">Oscar</a></li>
                  					<li><a href=\"/Index.cgi?p=Drawings\">Drawings</a></li>
                  					<li><a href=\"/Index.cgi?p=GameDesign\">Game Design</a></li>
								</ul>
							</li>
							<li>
								<a href=\"#\">Contact</a>
								<ul class=\"sub\">
									<li><a href=\"http://justdanny.hyves.nl/\" target=\"_blank\">Hyves</a></li>
									<li><a href=\"http://twitter.com/#!/DannyArends\" target=\"_blank\">Twitter</a></li>
									<li><a href=\"http://www.facebook.com/Arends.Danny\" target=\"_blank\">Facebook</a></li>
									<li><a href=\"http://nl.linkedin.com/in/dannyarends\" target=\"_blank\">LinkedIn</a></li>
									<li><a href=\"https://github.com/DannyArends\" target=\"_blank\">GitHub</a></li>
								</ul>
							</li>
						</ul>
					</div>
					<br class=\"clear\">
				</div>
				<div class=\"bottom maxwidth\"></div>
			</div>
			<div id=\"disclaimer\" class=\"centre\">Copyright Danny Arends 2011 - All Rights Reserved</div>
		</div>
	</body>
</html>
";

#!/usr/bin/perl -w
use strict;
use Socket;
use CGI qw(:standard);

#Global Variables across all includes
our %form;

#include our own files & functions
require "cgi-bin/utils.cgi";
require "cgi-bin/myblog.cgi";

#Constants

#Variables
our $title = "DannyArends.nl";
our $subtitle = "Homepage of Danny Arends";
our $theme = "/themes/default.css";
our $email = "Danny.Arends\@gmail.com";

#Functions



#Main
# Print a http header
receivePost();
if($form{"p"} eq ""){
	$form{"p"} = "Index"; 
}
if($form{"do"} ne "" || $form{"viewDetailed"} ne "" || $form{"viewCat"} ne "" || $form{"process"} || $form{"sendComment"}){
<<<<<<< HEAD
	$form{"p"} = "Blog"; 
=======
	$form{"p"} = "blog"; 
>>>>>>> 4756bc56aa3378376781c77f670247cb71343fa9
}
if($form{"do"} ne "RSS"){
printHTTPHeader('INDEX,FOLLOW');
print "
<div id=\"container\" class=\"centre maxwidth\">
			<div id=\"head\" class=\"maxwidth\">
<<<<<<< HEAD
				<a href=\"/Index.cgi?p=Index\" class=\"logo\"><span class=\"noview\">Danny Arends</span></a>
				<div id=\"menu\">
					<ul>
						<li class=\"home\"><a href=\"/Index.cgi\"><span class=\"noview\">home</span></a></li>
						<li class=\"blog\"><a href=\"/Index.cgi?p=Blog\"><span class=\"noview\">blog</span></a></li>
						<li class=\"research\"><a href=\"/Index.cgi?p=Research\"><span class=\"noview\">research</span></a></li>
						<li class=\"personal\"><a href=\"/Index.cgi?p=Personal\"><span class=\"noview\">personal</span></a></li>
						<li class=\"contact\"><a href=\"/Index.cgi?p=Contact\"><span class=\"noview\">contact</span></a></li>
=======
				<a href=\"/index.cgi?p=index\" class=\"logo\"><span class=\"noview\">Danny Arends</span></a>
				<div id=\"menu\">
					<ul>
						<li class=\"home\"><a href=\"/index.cgi\"><span class=\"noview\">home</span></a></li>
						<li class=\"blog\"><a href=\"/index.cgi?p=blog\"><span class=\"noview\">blog</span></a></li>
						<li class=\"research\"><a href=\"/index.cgi?p=research\"><span class=\"noview\">research</span></a></li>
						<li class=\"personal\"><a href=\"/index.cgi?p=personal\"><span class=\"noview\">personal</span></a></li>
						<li class=\"contact\"><a href=\"/index.cgi?p=contact\"><span class=\"noview\">contact</span></a></li>
>>>>>>> 4756bc56aa3378376781c77f670247cb71343fa9
					</ul>
				</div>
			</div>
			<div class=\"printlogo\"><img src=\"etc/img/logo.png\" alt=\"Dannyarends.nl\"	width=\"200\" height=\"32\"></div>
			<div id=\"content\" class=\"maxwidth\">
				<div class=\"top maxwidth\"></div>
				<div class=\"mid\">
					<div class=\"left\">
";
<<<<<<< HEAD
if($form{"p"} ne "Blog"){
=======
if($form{"p"} ne "blog"){
>>>>>>> 4756bc56aa3378376781c77f670247cb71343fa9
	print(showFile("pages",$form{"p"}.".txt")."\n");
}elsif($form{"viewDetailed"} ne ""){
	DisplaySinglePost();
}elsif ($form{"do"} eq "archive"){
	DisplayArchive()
}else{
	DisplayBlogOverview();
}
print "</div>
					<div class=\"right\">";
						
if($form{"p"} eq "Blog"){
	print "<div class=\"submenu\">";
	print("<h3>Latest entries / Content</h3>");
	DisplayEntries();
	print "	</div>";
}else{
<<<<<<< HEAD
	if($form{"p"} eq "Research" || $form{"p"} eq "Presentations" || $form{"p"} eq "Publications" || $form{"p"} eq "Links"){
		print "<div class=\"submenu\">";
		print "<h3>Research / Content</h3>
							<ul>
								<li><a href=\"/Index.cgi?p=Publications\">Publications</a></li>
								<li><a href=\"/Index.cgi?p=Presentations\">Presentations</a></li>
								<li><a href=\"/Index.cgi?p=Links\">Links</a></li>
=======
	if($form{"p"} eq "research" || $form{"p"} eq "presentations" || $form{"p"} eq "publications" || $form{"p"} eq "links"){
		print "<div class=\"submenu\">";
		print "<h3>Research / Content</h3>
							<ul>
								<li><a href=\"/index.cgi?p=publications\">Publications</a></li>
								<li><a href=\"/index.cgi?p=presentations\">Presentations</a></li>
								<li><a href=\"/index.cgi?p=links\">Links</a></li>
>>>>>>> 4756bc56aa3378376781c77f670247cb71343fa9
							</ul>
						";
		print "	</div>";
	}
<<<<<<< HEAD
	if($form{"p"} eq "Personal"){
		print "<div class=\"submenu\">";
		print "<h3>Personal / Content</h3>
							<ul>
                  				<li><a href=\"/Index.cgi?p=Hobbies\">Hobbies</a></li>
                  				<li><a href=\"/Index.cgi?p=Oscar\">Oscar</a></li>
                  				<li><a href=\"/Index.cgi?p=Drawings\">Drawings</a></li>
                  				<li><a href=\"/Index.cgi?p=GameDesign\">Game Design</a></li>
=======
	if($form{"p"} eq "personal"){
		print "<div class=\"submenu\">";
		print "<h3>Personal / Content</h3>
							<ul>
                  				<li><a href=\"/index.cgi?p=hobbies\">Hobbies</a></li>
                  				<li><a href=\"/index.cgi?p=oscar\">Oscar</a></li>
                  				<li><a href=\"/index.cgi?p=drawings\">Drawings</a></li>
                  				<li><a href=\"/index.cgi?p=gameDesign\">Game Design</a></li>
>>>>>>> 4756bc56aa3378376781c77f670247cb71343fa9
							</ul>
						";
		print "	</div>";
	}

}

<<<<<<< HEAD
if($form{"p"} eq "Index"){
=======
if($form{"p"} eq "index"){
>>>>>>> 4756bc56aa3378376781c77f670247cb71343fa9
print "					<div class=\"bio\">
							<h3>Short bio</h3>
							<p>
							<strong>Spoken languages:</strong> Dutch, English, German, French<br>
							<strong>Location:</strong> Groningen, The Netherlands<br>
							<strong>Programming languages:</strong> R, C++, C, Java, PHP, Perl and some more<br>
							<strong>Likes:</strong> My Cat, Biology, Genetics, Computers, Drawing, Film and Movies, Discussions<br>
							</p>
						</div>";
}
					
print "					<div class=\"contact\">
							<h3>Contact</h3>
							<p>
							Faculty of Mathematics and Natural Sciences<br>
							Bioinformatics (GBIC) - Groningen Bioinformatics Centre<br> 
							Gron Inst for Biomolecular Sciences & Biotechnology<br>
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
<<<<<<< HEAD
								print '<a href="/Index.cgi?p=Blog">Blog</a>';
								DisplayEntries();
print " 					</li>
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
								<a href=\"/Index.cgi?p=Contact\">Contact</a>
=======
								print '<a href="/index.cgi?p=blog">Blog</a>';
								DisplayEntries();
print " 					</li>
							<li>
								<a href=\"/index.cgi?p=research\">Research</a>
								<ul class=\"sub\">
									<li><a href=\"/index.cgi?p=publications\">Publications</a></li>
									<li><a href=\"/index.cgi?p=presentations\">Presentations</a></li>
									<li><a href=\"/index.cgi?p=links\">Links</a></li>
								</ul>
							</li>
							<li>
								<a href=\"/index.cgi?p=personal\">Personal</a>
								<ul class=\"sub\">
                  					<li><a href=\"/index.cgi?p=hobbies\">Hobbies</a></li>
                  					<li><a href=\"/index.cgi?p=oscar\">Oscar</a></li>
                  					<li><a href=\"/index.cgi?p=drawings\">Drawings</a></li>
                  					<li><a href=\"/index.cgi?p=gamedesign\">Game Design</a></li>
								</ul>
							</li>
							<li>
								<a href=\"/index.cgi?p=contact\">Contact</a>
>>>>>>> 4756bc56aa3378376781c77f670247cb71343fa9
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
}else{
DisplayRSS();
}
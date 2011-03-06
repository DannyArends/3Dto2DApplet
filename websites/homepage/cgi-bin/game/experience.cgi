#!/usr/bin/perl -w

#
# experience.cgi
#
# copyright (c) 2009-2011, Danny Arends
# last modified Jan, 2011
# first written Jan, 2011
#

use strict;

#Global Variables across all includes
our %form;
our $write_location;
our $data_location;

#Functions

## create_xp : Create an empty xp list for a user
sub create_xp{
	my $username = lc $_[0];
	print "#Creating xp-matrix: 4 Skills * 2 XP Dimension * 5 Levels\n";
	create_map($username."_xp",4,2,5,"xp");
}

## get_xp : get the xp list for a user
sub get_xp{
	my $username = lc $_[0];
	list_map($username."_xp","xp");
}

return 1;
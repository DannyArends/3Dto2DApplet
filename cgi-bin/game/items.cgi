#!/usr/bin/perl -w

#
# items.cgi
#
# copyright (c) 2009-2011, Danny Arends
# last modified Jan, 2011
# first written Dec, 2011
#

use strict;

#Global Variables across all includes
our %form;
our $write_location;
our $data_location;

#Functions
sub create_empty_storage{
	my $username = lc $_[0];
	print "#Creating storage: 500 * 1 * 2\n";
	create_map($username."_store",500,1,2,"storages");
	print "#Creating inventory: 5 * 5 * 1\n";
	create_map($username."_inv",5,5,1,"storages");
}

sub get_storage{
	my $username = lc $_[0];
	list_map($username."_store","storages");
}

sub get_inventory{
	my $username = lc $_[0];
	list_map($username."_inv","storages");
}

return 1;
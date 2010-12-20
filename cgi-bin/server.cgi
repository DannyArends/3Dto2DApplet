#!/usr/bin/perl -w
use strict;
use Socket;
use CGI qw(:standard);

#Global Variables across all includes
our %form;
our $write_location = "../dist/write/";
our $data_location = "../dist/data/";

#include our own files & functions
require "functions.cgi";
require "data.cgi";
require "user.cgi";

#Constants

#Variables

#Main
printTXTHeader();
printPost();
printServerStats();
printUserStats();
writeIPtoLog();
print("#AVAILABLE_DATASETS" . "\n");
list_type("dat");
print("#AVAILABLE_MODELS" . "\n");
list_type("3ds");

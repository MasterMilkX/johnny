#!/bin/perl

#import file
#print "File: text/";
my ($file) = @ARGV;
open (FILE, "text/$file") || dir ("WTF?!");
my @lines = <FILE>;

#clean each indivdiual line
foreach my $line(@lines){
	chomp($line);

	$line =~s/\[.+\]//g;
	$line =~s/\(.+\)//g;
}

my @new_set = ();
foreach my $line(@lines){
	if($line =~/^[A-Z]+:/){
		push(@new_set, $line);
	}
}


#recreate files and remove any empty lines
open (FILE2, ">", "format_text/$file") || die ("um....");
foreach my $line(@new_set){
	if($line){
		print FILE2 "$line\n";
	}
}
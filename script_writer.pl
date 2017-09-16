#!/bin/perl

#import file
#print "File: text/";
chomp ($file = <STDIN>);
open (FILE, "text/$file") || dir ("WTF?!");
my @lines = <FILE>;

#clean each indivdiual line
foreach my $line(@lines){
	chomp($line);

	$line =~s/\[.+\]//g;
	$line =~s/\(.+\)//g;
}

#recreate files and remove any empty lines
open (FILE2, ">", "format_text/$file") || die ("um....");
foreach my $line(@lines){
	if($line){
		print FILE2 "$line\n";
	}
}
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
 	#$line =~s/(               )(\s)*//g;
}

#concatanate the first and following dialogue line
my @new_set = ();
my $num_lines = @lines;
for(my $l=0;$l<$num_lines-1;$l++){
	my $line = @lines[$l];
	my $d = 1;
	if($line =~/(                                     )/){
		my $dia = "";
		$line =~s/(                                     )//g;
		while($lines[$d+$l] =~/(                         )/){
			my $dia_line = $lines[$d+$l];
			$dia_line =~s/(                         )//g;
			$dia .= $dia_line;
			$d++;
		}
		push(@new_set, ("$line: " . $dia));
	}
}

#recreate files and remove any empty lines
open (FILE2, ">", "format_text/$file") || die ("um....");
foreach my $line(@new_set){
	if($line){
		print FILE2 "$line\n";
	}
}
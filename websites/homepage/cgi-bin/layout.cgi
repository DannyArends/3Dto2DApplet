
sub showFile{
	my $dir = shift;
	my $name = shift;
	my @rawdata;
	my $line;
	my $text;
	open(FILE, "$dir/$name");
	@raw_data=<FILE>; 
	close(FILE);
	foreach $line (@raw_data)
	{
		chomp($line);
		if($line =~ /<h\C{1}>/){
			#Headers
			$text .= $line."\n";
		}elsif($line eq ""){
			#Empty lines
			$text .= "\n";
		}else{
			#Normal line
			$text .= $line."<br/>"."\n";
		}	
	} 
	return $text;
}

sub ShowTopMenu{
	my $dirname = shift;
	my @files;
	my $full_fileName;
	my $FileName;
	
	@files = <$dirname/*>;
	foreach $full_fileName (@files) {
		if($full_fileName =~ /\/(\C*)\./){
			$fileName = $1;
			print("<div class='SButton' onclick=\"document.location.href='index.cgi?Page=$fileName'\"><a href='index.cgi?Page=$fileName' class='black'>$fileName</a></div>"."\n");
		}
		
	}
	return;
}

sub printLayout{
  print("<table width='750'><tr>"."\n");
  print("<td colspan='2'>"."\n");
  print("<div><img src='/images/Top_1.jpg' alt='top image'/></div>"."\n");
  print("</td>"."\n");
  print("</tr><tr>"."\n");
  print("<td class='Lnavi'>"."\n");
  print(ShowTopMenu("pages")."\n");
  print("<br/><br/><script type=\"text/javascript\" src=\"http://widgets.twimg.com/j/2/widget.js\"></script><script type=\"text/javascript\">new TWTR.Widget({  version: 2,  type: 'profile',  rpp: 5,  interval: 6000,  width: 250,  height: 300,  theme: {    shell: {      background: '#e0e0e0',      color: '#000000'    },    tweets: {      background: '#ffffff',      color: '#000000',      links: '#fc080c'    }  },  features: {    scrollbar: false,    loop: false,    live: false,    hashtags: true,    timestamp: true,    avatars: false,    behavior: 'all'  }}).render().setUser('DannyArends').start();</script>"."\n");
  print("</td>"."\n");
  print("<td class='main'>"."\n");
  print(showFile("pages",$form{"Page"}.".txt")."\n");
  print("</td>"."\n");
  print("</tr></table>"."\n");
}

sub printEnviromental{
	print("<h2>Enviromental Variables</h2>"."\n");
	foreach my $key (sort(keys(%ENV))) {
		print("$key = $ENV{$key}<br/>"."\n");
	}
}

return 1;
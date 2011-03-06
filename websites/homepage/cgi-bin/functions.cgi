#Variables

sub printHeader{
	print("Content-type: text/html"."\n\n");
}

sub printDocType{
  print("<?xml version=\"1.0\" encoding=\"utf-8\"?>"."\n");
  print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"."\n");
}

sub printHead{
  printDocType();
  print("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:svg=\"http://www.w3.org/2000/svg\">"."\n");
	print("\t<head>"."\n");
	print("\t<title>Danny.Biosources.nl - ". substr($form{"Page"},index($form{"Page"},')')+1) . "</title>"."\n");
	print("\t\t<link href='theme/theme.css' type='text/css' rel='stylesheet' />"."\n");
	print("\t\t<meta name='Keywords' content='Danny Arends Nano Bio Informatics Genetical Genomics QTL MQM RF Groningen Biology Biosources' />"."\n");
	print("\t\t<meta name='Description' content='Danny.Biosources.nl personal homepage of Danny Arends.' />"."\n");
	print("\t\t<meta name=\"google-site-verification\" content=\"LGbxXM8J708GT066FCsuAdoFJs9LL9PLtiasWpEdUbA\" />"."\n");
	print("\t\t<meta name='Author' content='Danny Arends' />"."\n");
	print("\t\t<meta name='Robots' content='INDEX,FOLLOW' />"."\n");
  print("\t\t<script type='text/javascript'></script>"."\n");
  print("\t\t<script type='text/javascript' src='jQuery/jquery.min.js'></script>"."\n");
  print("\t\t<script type='text/javascript' src='jQuery/jquery.cycle.min.js'></script>"."\n");
  print("\t\t<script type='text/javascript'>"."\n");
  print("\t\t\t\$(document).ready(function() {"."\n");
  print("\t\t\t\t\$('.slideshow').cycle({"."\n");
  print("\t\t\t\tfx: 'fade'"."\n");
  print("\t\t\t\t});"."\n");
  print("\t\t\t});"."\n");
  print("\t\t</script>"."\n");
	print("\t</head>"."\n");
}

sub printTimeDiff{
	my $time_diff = shift;
	if($time_diff =~ /(\d*.\d*e-\d*)/ or $time_diff =~ /(\d*.\d*)/ ){
		$time_diff = $1;
		print("\t\t\t<p>Page generated in: ".$time_diff." Sec</p>\n");
	}else{
		print("\t\t\t<p>Page generated in: ".$time_diff." Sec</p>\n");
	}
}

sub printBeginBody{
	print("\t<body>"."\n");
}

sub printEndBody{
	print("\t</body>"."\n");
  print("</html>"."\n");
}

sub logger{
	my $logFile = shift;
	my $msg = shift;
	my $time;
	$time = localtime();
	open(FILE,">>logs/$logFile") || print("2Bad<br>");
	print FILE "$time-$msg\n"; 
	close(FILE); 
}

return 1;
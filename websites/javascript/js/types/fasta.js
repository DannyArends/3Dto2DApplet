// Basic fasta file-reader

function fasta(id){
  this.id = id;
  this.sequence_descr = [];
  this.sequences = [];
  
  this.parseFromString = function(string){
    var lines = string.split("\n");
    var c_sequence = null;
    for(var i = 0, line; line = lines[i]; i++) {
      if(line.charAt(0) == ">"){
    	if(c_sequence != null)this.sequences.push(c_sequence);
    	c_sequence = null;
    	this.sequence_descr.push(line.slice(1,line.length-1));
      }else{
    	 c_sequence = (c_sequence==null)? line : c_sequence + line; 
      }
    }
    if(c_sequence != null){
    	this.sequences.push(c_sequence);
    }
    if(this.sequences.length != this.sequence_descr.length){
      debug.writeln(this.sequence_descr.length + " != " + this.sequences.length +"Warning not all sequences have a description line, shifts might have occured");
    }else{
      debug.writeln("Parsed: " + this.sequences.length + " sequences out of " + lines.length+ " lines");
    }
    return this;
  }
  
  this.render = function(ctx){
		ctx.font = "14px Times New Roman";
		var rendered =0;
		var skipped =0;
		for(var r = 0; r < this.sequence_descr.length; r++) {
	      if(inHeight(r*12)){
	      
		  ctx.fillStyle = "White";
		  ctx.fillText(this.sequence_descr[r].slice(0,20), 0, (r+1)*12);
		  var line = this.sequences[r];
		  for(var i = 0; i < line.length; i++) {
		    if(onScreen(150+i*12, r*12)){
		      if(line.charAt(i)=="A"){
		    	  ctx.fillStyle = "rgb(255,0,0)";
		      }
		      if(line.charAt(i)=="T"){
		    	  ctx.fillStyle = "rgb(255,255,0)";
		      }
		      if(line.charAt(i)=="C"){
		    	  ctx.fillStyle = "rgb(0,0,255)";
		      }
		      if(line.charAt(i)=="G"){
		    	  ctx.fillStyle = "rgb(0,255,255)";
		      }
		      debug.writeln("->"+line.charAt(i));
	    	  ctx.fillRect(150+i*12, r*12, 11, 11); 
	    	  rendered++;
		    }else{
			  skipped++;
		    }
	      }
	      }
	    }
		debug.writeln(rendered + " / " + skipped);
	  }
}

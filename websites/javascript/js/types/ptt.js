//Reader for NCBI PTT files

function ptt(id){
  this.header = "";
  this.sep = "\t";
  this.colnames = [];
  this.rownames = [];
  this.data = [];

  this.parseFromString = function(string){
    var lines = string.split("\n");
    this.header = lines[0];
    this.colnames = lines[2].split(this.sep);
    for(var i = 3, line; line = lines[i]; i++) {
      var elements = line.split(this.sep);
      if(elements.length == 9){
        this.rownames.push(elements[7]); //We the Synonym column
        this.data.push(elements.slice(0,6).concat(elements.slice(8,9)));
      }else{
    	debug.writeln("Line length mismatch 9!=" + elements.length);
    	return null;
      }
    }
    debug.writeln("Loaded " + this.rownames.length + " sequence annotations");
    return this;
  }

  this.render = function(ctx){
	ctx.font = "16px Times New Roman";  
	ctx.fillStyle = "White";
    ctx.fillText("L: " + this.header, 0,  12);
  }
}

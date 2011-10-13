//A matrix 'class' in JavaScript

function matrix(id){
  this.id = id;
  this.sep;
  this.data = [];
  this.colnames = [];
  this.rownames = [];
  this.min = null;
  this.max = null;
  
  this.parseFromString = function(string){
    var lines = string.split("\n");
    this.sep = findSep(lines[0]);
    this.colnames = lines[0].split(this.sep);
    for(var i = 1, line; line = lines[i]; i++) {
      if(line.length > 0){
        var elements = line.split(this.sep);
        if(elements.length != this.colnames.length){ 
        	debug.writeln("Columns don't match up at line " + i);
        	return null
        }
        this.rownames.push(elements[0]);
        this.data.push(elements.slice(1,(elements.length-1)));
      }else{
    	  return null;
      }
    }
    return this;
  }
	
  this.printAsHtml = function(){
    var s = '<tr><th>Name</th><th>' + this.colnames.join('</th><th>') + '</th></tr>';
    for(var i = 0, name; name = this.rownames[i]; i++) {
      s += "<tr><td>" + name + "</td><td>" + this.data[i].join("</td><td>") + "</tr>";
    }
    return s;
  }
  
  this.getMinValue = function(){
    if(this.min != null) return this.min;
    for(var r = 0, rowname; rowname = this.rownames[r]; r++) {
      for(var c = 0;c < (this.colnames.length-1);c++) {
    	  if(isNumber(this.data[r][c])){
    		  if(this.min == null) this.min = this.data[r][c];
    		  this.min = Math.min(this.min,this.data[r][c]);
    	  }
      }
    }
    debug.writeln("Minimum value:" + this.min);
    return this.min;
  }

  this.getMaxValue = function(){
	if(this.max != null) return this.max;
    for(var r = 0, rowname; rowname = this.rownames[r]; r++) {
      for(var c = 0;c < (this.colnames.length-1);c++) {
    	  if(isNumber(this.data[r][c])){
    		  if(this.max == null) this.max = this.data[r][c];
    		  this.max = Math.max(this.max,this.data[r][c]);
    	  }
      }
    }
    debug.writeln("Maximum value:" + this.max);
    return this.max;
  }
  
  this.render = function(ctx){
	ctx.font = "14px Times New Roman";
	var rendered =0;
	var skipped =0;
	for(var r = 0, rowname; rowname = this.rownames[r]; r++) {
	  if(inHeight(r*12)){
        ctx.fillStyle = "White";
	    ctx.fillText(rowname, 0, (r+1)*12);
	    for(var c = 0;c < (this.colnames.length-1);c++) {
		  
	    if(onScreen(150+c*12, r*12)){
    	  if(isNumber(this.data[r][c])){
            ctx.fillStyle = "rgb("+this.getWithinColor(this.data[r][c])+",0,125)";
          }else{
            if(this.data[r][c] == "A"){
        	    ctx.fillStyle = "rgb(255,255,0)";
            }else if(this.data[r][c] == "B"){
        	    ctx.fillStyle = "rgb(0,255,0)";
            }else{
              ctx.fillStyle = "rgb(255,255,255)";
            }
          }
          ctx.fillRect(150+c*12, r*12, 11, 11); 
          rendered++;
	    }else{
		  skipped++;
	    }
        }
	  }
    }
	//debug.writeln(rendered + " / " + skipped);
  }
  
  this.getWithinColor = function(x){
	var f = (x-this.getMinValue())/(this.getMaxValue()-this.getMinValue());
	return parseInt(f*255);
  }
}

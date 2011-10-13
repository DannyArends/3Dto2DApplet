// Detect mouse clicks on the canvas
function mouseHandler(canvas){
  this.ldragging = false;
  this.rdragging = false;
  var location = {
    x:0,
    y:0,
    z:0
  };
  
  up = function(event){
    var x = event.x;
    var y = event.y;
		
    x -= canvas.offsetLeft;
    y -= canvas.offsetTop;
    debug.writeln(event.which + " x:" + x + " y:" + y);
    if(event.which==1) this.ldragging = !this.ldragging;
    if(event.which==3) this.rdragging = !this.rdragging;
  }
  
  down = function(event){
    var x = event.x;
    var y = event.y;
			
    x -= canvas.offsetLeft;
    y -= canvas.offsetTop;
    debug.writeln(event.which + " x:" + x + " y:" + y);
    if(event.which==1)this.ldragging = !this.ldragging;
    if(event.which==3){
    	this.ldragging = false;
    	this.rdragging = !this.rdragging;
    }
    location.x=x;
    location.y=y;
  }
  
  move = function(event){
   var x = event.x;
   var y = event.y;
   
   x -= canvas.offsetLeft;
   y -= canvas.offsetTop;
   
    if(this.ldragging){
      //debug.writeln("Left mouse drag x:" + x + " y:" + y);
      x -= location.x;
      y -= location.y;
      camera.update(x,y);
      location.x += x;
      location.y += y;
    }
    if(this.rdragging)debug.writeln("Right mouse drag x:" + x + " y:" + y);
  },
  
  leave = function(event){
	  debug.writeln("Mouse left");
	  this.ldraggin=false;
	  this.rdraggin=false;
  }
  
  canvas.addEventListener("mousedown", down, false);
  canvas.addEventListener("mouseup", up, false);
  canvas.addEventListener("mousemove", move, false);
  canvas.addEventListener("mouseleave", leave, false);
}




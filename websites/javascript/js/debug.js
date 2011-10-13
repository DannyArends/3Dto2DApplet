var debug = {
  debug_container: [],
  debug_ctx:null,
  debug_canvas:null,

  init : function(){
	debug_canvas = document.getElementById('debugCanvas');
	debug_ctx = debug_canvas.getContext('2d');
  },

  writeln : function(debug_text){
    this.debug_container.push(debug_text);
  },

//Writes the debug buffer
  render : function(){
	debug_ctx.clearRect(0, 0, debug_canvas.width, debug_canvas.height);
	debug_ctx.font = "16px Times New Roman";  
	debug_ctx.fillStyle = "Black";
	var lines=0;
    for(var i = (this.debug_container.length-1); i >= 0 && lines < 15; i--){
      debug_ctx.fillText(this.debug_container[i], 0,  12*((this.debug_container.length-i)+1));
      lines++;
    }
  }
}

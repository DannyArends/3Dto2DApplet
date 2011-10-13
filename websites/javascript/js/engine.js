//Basic .js engine, for canvas rendering
include('js/types/matrix.js');
include('js/types/fasta.js');
include('js/types/ptt.js');
include('js/types/camera.js');
include('js/functions/engine.js');
include('js/functions/numbers.js');
include('js/debug.js');
include('js/fileloader.js');
include('js/mouse.js');


var engine = {
  speed: 100,
  canvas:null,
  ctx:null,
  renderables:[],
  frame:1,

  init: function(){
    document.getElementById('files').addEventListener('change', handleFileSelect, false);
    debug.init();
  
    canvas = document.getElementById('renderingCanvas'); 
    canvas.oncontextmenu=new Function("return false")
    debug.writeln("Starting Engine v0.01");
    if(canvas.getContext){
      ctx = canvas.getContext('2d');  
      mouseHandler(canvas);
      return setInterval(engine.draw, engine.speed);  
    }
  },

  draw: function(){
	debug.render(debug_ctx);
	ctx.save();
	ctx.fillStyle = "rgb(0,0,0)";
    ctx.fillRect(0,0,canvas.width,canvas.height);
    ctx.translate(camera.xtrans, camera.ytrans);
    if(engine.renderables.length == 0){
      if(isEven(engine.frame)){
        ctx.fillStyle = "rgb(200,0,0)";
        ctx.fillRect (10, 10, 55, 50);
      }else{
        ctx.fillStyle = "rgba(0, 0, 200, 0.9)";
        ctx.fillRect (30, 30, 55, 50);
      }
    }else{
    	for(var i =0;i<engine.renderables.length;i++){
    	  engine.renderables[i].render(ctx);
    	}
    }
    ctx.restore();
    engine.frame++;
  },
  
  addrenderable: function(r){
	  engine.renderables.push(r);
  }
}

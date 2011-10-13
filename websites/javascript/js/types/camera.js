//A 2D / 3D container to remember locations (think: camera)

var camera = {
  xtrans:0,
  ytrans:0,
  ztrans:0,
  
  update: function(x,y){
    camera.xtrans += x;
    camera.ytrans += y;
  }
}
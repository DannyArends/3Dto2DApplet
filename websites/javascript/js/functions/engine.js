//Additional functions that are used by the engine

function onScreen(x,y){
  if(x >= 0-camera.xtrans && x <= canvas.width-camera.xtrans){
    if(y >= 0-camera.ytrans && y <= canvas.height-camera.ytrans){
      return true;
	}
  }
  return false;
}

function inHeight(y){
  if(y >= 0-camera.ytrans && y <= canvas.height-camera.ytrans){
    return true;
  }
  return false;
}
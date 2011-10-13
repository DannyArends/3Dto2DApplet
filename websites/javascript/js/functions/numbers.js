//Some functions related to numbers

function isNumber(n) {
  var RE = /^-{0,1}\d*\.{0,1}\d+$/;
  return (RE.test(n));
}

function isEven(n){
  if(n%2 == 0){
    return true;
  }else{
    return false;
  }
}

function findSep(line){
  var length = 1;
  var bestsep = 0;
  var seps = ["\t", " "];
  for(var i = 0, sep; sep = seps[i]; i++) {
    if(line.split(sep).length > length){
      bestsep=i;
    }
  }
  return seps[bestsep];
}

// Handle file upload from user
include('js/types/matrix.js');

function updateProgress(evt) {
  if(evt.lengthComputable){
    var percentLoaded = Math.round((evt.loaded / evt.total) * 100);
    if (percentLoaded < 100) {
      progress.style.width = percentLoaded + '%';
      progress.textContent = percentLoaded + '%';
    }
  }
}
  
function errorHandler(evt){
  switch(evt.target.error.code) {
  case evt.target.error.NOT_FOUND_ERR:
    debug.writeln('The file selected is not found.');
    break;
  case evt.target.error.NOT_READABLE_ERR:
	debug.writeln('The file selected is not readable.');
    break;
  case evt.target.error.ABORT_ERR:
    break;
  default:
	debug.writeln('An error occurred reading the file.');
  };
}

function createCallback(id, progress){
  return function(e){
    progress.style.width = '100%';
    progress.textContent = '100%';
    var r;
    if((r = new ptt(id).parseFromString(e.target.result))   != null){engine.addrenderable(r);return;}
    if((r = new matrix(id).parseFromString(e.target.result))!= null){engine.addrenderable(r);return;}
    if((r = new fasta(id).parseFromString(e.target.result)) != null){engine.addrenderable(r);return;}
  };
}

function handleFileSelect(evt) {
  var files = evt.target.files;
  var output = [];
  var reader = new FileReader();
  var progress = document.querySelector('.percent');

  
  for(var i = 0, f; f = files[i]; i++){
    output.push('<li><strong>', f.name, '</strong> (', f.type || 'N/A', ') - ', f.size, ' bytes</li>');
    reader.onerror = errorHandler;
    reader.onprogress = updateProgress;
    reader.onabort = function(e){ alert('File read cancelled'); };
    reader.onloadstart = function(e){
      document.getElementById('progress_bar').className = 'loading';
    };
    reader.onload = createCallback(f,progress);
    reader.readAsText(f);
  }
  document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

function abortRead(){
  reader.abort();
}

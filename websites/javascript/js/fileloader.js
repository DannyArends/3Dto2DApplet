// Handle file upload from user
include('js/types/matrix.js');

var fileloader = {
  speed: 100,
  allfiles:[],
  reader:null,
  progress:null,
  
  init: function(){
	  progress = document.querySelector('.percent');
  },
  
  loadFiles: function(evt) {
	  var output = [];
	  var files = evt.target.files;
	  for(var i = 0, f; f = files[i]; i++){
		  output.push('<li><strong>', f.name, '</strong> (', f.type || 'N/A', ') - ', f.size, ' bytes</li>');
		  reader = new FileReader();
		  reader.onerror = errorHandler;
		  reader.onprogress = updateProgress;
		  reader.onabort = function(e){ 
			if(reader.readyState != 2){
			  debug.writeln("File reading cancelled");
			  progress.style.background = "#aa1100";
			  progress.style.color = "White";
			  reader=null;
			  return false;
		    }
	      };
		  reader.onloadstart = function(e){
		    document.getElementById('progress_bar').className = 'loading';
		    progress.style.background = "#00aa11";
		    progress.style.color = "Black";
		  };
		  reader.onload = createCallback(f,progress);
		  reader.readAsText(f);
		  fileloader.allfiles.push(f);
	  }
	  document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
  },
  
  abortRead : function (){
    if(reader !=null) reader.abort();
  }
}

function updateProgress(evt) {
  if(evt.lengthComputable){
    var percentLoaded = Math.round((evt.loaded / evt.total) * 100);
    if(percentLoaded < 100){
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
	//Should be here: fileloader.allfiles.push(f);
  };
}

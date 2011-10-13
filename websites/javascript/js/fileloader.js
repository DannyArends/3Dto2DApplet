// Handle file upload from user
include('js/types/matrix.js');

function handleFileSelect(evt) {
  var files = evt.target.files;
  var output = [];
  
  for(var i = 0, f; f = files[i]; i++) {
    output.push('<li><strong>', f.name, '</strong> (', f.type || 'N/A', ') - ', f.size, ' bytes</li>');
    var reader = new FileReader();

    reader.onload = (function(theFile) {
      return function(e){
    	//var m = new matrix(1).parseFromString(e.target.result);
    	var m = new fasta(1).parseFromString(e.target.result);
    	engine.addmatrix(m);
      };
    })(f);
    
    reader.readAsText(f);
  }
  document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
}

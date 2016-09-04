function Point(x, y){
  this.x = x;
  this.y = y;
  this.w = 1;
}

var canvas = document.getElementById('mycanvas');
var W = canvas.width = 100;
var H = canvas.height = 100;

var c = canvas.getContext('2d');
var bufferDeVideo = c.createImageData(W, H);
var listaDePontos = [];

function setPixel (x, y, r, g, b, a) {
    var index = (x + y * W) * 4;

    bufferDeVideo.data[index + 0] = r ? r : 0;
    bufferDeVideo.data[index + 1] = g ? g : 0;
    bufferDeVideo.data[index + 2] = b ? b : 0;
    bufferDeVideo.data[index + 3] = a || 255;
}

function paint(){

  /*for(var i = 0; i < listaDePontos.length; i++){
    var p = listaDePontos[i];
    var p1 = listaDePontos[i+1];

    //desenhar linha
  }

  if(listaDePontos.length >= 2){
    var p = listaDePontos[0];
    var p1 = listaDePontos[listaDePontos.length];
  }*/

  c.putImageData(bufferDeVideo, 0, 0);

  painter();
}

function painter(){
  setTimeout(function(){
    paint();
  }, 100);
}


var point1;
var point2;

function onClick(event){


  var pos = getCursorPosition(canvas, event);


  var tool = document.querySelector('input[name="tool"]:checked').value;

  if (tool === 'line') {
    if(point1){
      point2 = new Point(pos.x, pos.y);
      console.log(point1, point2);
      drawLine(point1.x, point1.y, point2.x, point2.y);


      point1 = point2;
    } else {
      point1 = new Point(pos.x, pos.y);
    }
  } else {
    floodfill(pos.x, pos.y, {r: 0, g: 0, b: 0, a: 255}, bufferDeVideo, 100, 100, 0);
  }

}

function getCursorPosition(canvas, event) {
  var rect = canvas.getBoundingClientRect();
  var x = event.clientX - rect.left;
  var y = event.clientY - rect.top;
  return {
    x: x, y: y
  }
}

function drawLine(x1, y1, x2, y2){
  var slope, dx, dy, incE, incNE, d, x, y;
  // Onde inverte a linha x1 > x2
  if (x1 > x2){
      drawLine(x2, y2, x1, y1);
       return;
  }
  dx = x2 - x1;
  dy = y2 - y1;

  if (dy < 0){
      slope = -1;
      dy = -dy;
  }
  else{
     slope = 1;
  }
  // Constante de Bresenham
  incE = 2 * dy;
  incNE = 2 * dy - 2 * dx;
  d = 2 * dy - dx;
  y = y1;
  for (x = x1; x <= x2; x++){
      setPixel(x, y);
      if (d <= 0){
        d += incE;
      }
      else{
        d += incNE;
        y += slope;
      }
  }
}

painter();

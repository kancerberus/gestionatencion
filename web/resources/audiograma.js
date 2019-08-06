var modelo = new Array();
var oidos = new Array('OD', 'OI');

var cardinal = new Array();
cardinal['OD'] = 'derecho';
cardinal['OI'] = 'izquierdo';

var oidoCanvas = new Array();
oidoCanvas['canvasOD'] = 'OD';
oidoCanvas['canvasOI'] = 'OI';

var color = new Array();
color['OD'] = '#D01313';
color['OI'] = '#0000D0';

var ejeX = new Array('250', '500', '1K', '2K', '4K', '8K');
var ejeY = new Array('0', '10', '20', '30', '40', '50', '60', '70', '80', '90', '100', '110', '120');
var simbolos = new Array('aerea', 'aerea_mascara', 'osea', 'osea_mascara');
var dimension = 200;

//Logo
var ejeXLogo = new Array('10', '20', '30', '40', '50', '60', '70', '80', '90', '100');
var ejeYLogo = new Array('100', '90', '80', '70', '60', '50', '40', '30', '20', '10', '0');
var altoCanvasLogo = 200;
var anchoCanvasLogo = 300;
var simbolosLogo = new Array('aerea');

function inicializarModelos() {
    var i, j, k, l, nombreCanvas, nombreModelo;
    for (i = 0; i < oidos.length; i++) {
        nombreCanvas = 'canvas' + oidos[i];
        modelo[nombreCanvas] = new Object();
        modelo[nombreCanvas].ejeXK = new Array();
        modelo[nombreCanvas].ejeYK = new Array();
        modelo[nombreCanvas].matriz = new Array();

        for (j = 0; j < ejeX.length; j++) {
            modelo[nombreCanvas].matriz[ejeX[j]] = new Array();
            for (k = 0; k < ejeY.length; k++) {
                modelo[nombreCanvas].matriz[ejeX[j]][ejeY[k]] = new Array();
                for (l = 0; l < simbolos.length; l++) {
                    modelo[nombreCanvas].matriz[ejeX[j]][ejeY[k]][simbolos[l]] = false;
                }
            }
        }
    }

    //logo
    for (i = 0; i < oidos.length; i++) {
        nombreModelo = 'Logo' + oidos[i];
        modelo[nombreModelo] = new Object();
        modelo[nombreModelo].ejeXK = new Array();
        modelo[nombreModelo].ejeYK = new Array();
        modelo[nombreModelo].matriz = new Array();

        for (j = 0; j < ejeXLogo.length; j++) {
            modelo[nombreModelo].matriz[ejeXLogo[j]] = new Array();
            for (k = 0; k < ejeYLogo.length; k++) {
                modelo[nombreModelo].matriz[ejeXLogo[j]][ejeYLogo[k]] = new Array();
                for (l = 0; l < simbolosLogo.length; l++) {
                    modelo[nombreModelo].matriz[ejeXLogo[j]][ejeYLogo[k]][simbolosLogo[l]] = false;
                }
            }
        }
    }

}


//Recibe un identificador del elemento canvas y carga el canvas
//Devueve el contexto del canvas o FALSE si no ha podido conseguise
function cargaContextoCanvas(idCanvas) {
    var elemento = document.getElementById(idCanvas);
    if (elemento && elemento.getContext) {
        var contexto = elemento.getContext('2d');
        if (contexto) {
            return contexto;
        }
    }
    return false;
}



function analizarTabla(oido) {

    var parser, xmlDoc;
    var i, j;

    var nombreCanvas = 'canvas' + oido;
    var datos = document.getElementById('frmEstudioAudiologico:datosXML' + oido).value;
    parser = new DOMParser();
    xmlDoc = parser.parseFromString(datos, "text/xml");


    for (i = 0; i < xmlDoc.getElementsByTagName("puntos").length; i++) {
        for (j = 1; j < xmlDoc.getElementsByTagName("puntos")[0].childNodes.length; j++) {
            if (xmlDoc.getElementsByTagName("puntos")[i].childNodes[j].innerHTML != '')
                modelo[nombreCanvas].matriz[xmlDoc.getElementsByTagName("puntos")[i].childNodes[0].innerHTML][xmlDoc.getElementsByTagName("puntos")[i].childNodes[j].innerHTML][simbolos[j - 1]] = true;
        }
    }
}

function analizarTablaLogo() {

    var parser, xmlDoc;
    var i, j, k, nombreModelo, datos;

    for (k = 0; k < oidos.length; k++) {
        nombreModelo = 'Logo' + oidos[k];
        datos = document.getElementById('frmEstudioAudiologico:datosXMLLogo' + oidos[k]).value;
        parser = new DOMParser();
        xmlDoc = parser.parseFromString(datos, "text/xml");


        for (i = 0; i < xmlDoc.getElementsByTagName("puntos").length; i++) {
            for (j = 1; j < xmlDoc.getElementsByTagName("puntos")[0].childNodes.length; j++) {
                if (xmlDoc.getElementsByTagName("puntos")[i].childNodes[j].innerHTML != '')
                    modelo[nombreModelo].matriz[xmlDoc.getElementsByTagName("puntos")[i].childNodes[0].innerHTML][xmlDoc.getElementsByTagName("puntos")[i].childNodes[j].innerHTML][simbolosLogo[j - 1]] = true;
            }
        }
    }


}

function dibujarPuntos(oido) {
    var nombreCanvas = 'canvas' + oido;
    var contexto = cargaContextoCanvas(nombreCanvas);
    var canvas = document.getElementById(nombreCanvas);
    var img;
    var i, j, k;

    //dibujar simbolos
    for (i = 0; i < ejeX.length; i++) {
        for (j = 0; j < ejeY.length; j++) {
            for (k = 0; k < simbolos.length; k++) {
                if (modelo[nombreCanvas].matriz[ejeX[i]][ejeY[j]][simbolos[k]]) {
                    img = new Image();
                    //img.src = modelo[idCanvas].matriz[i]['contenido'][j]['simbolo']+"."+modelo[idCanvas].matriz[i]['oido']+".png";//'aerea.derecha.png';
                    img.src = "resources/imagenes/" + simbolos[k] + "." + cardinal[oido] + ".png";//'aerea.derecha.png';
                    img.indicei = i;
                    img.indicej = j;
                    img.nombreCanvas = 'canvas' + oido;
                    //
                    img.oido = oido;

                    img.onload = function () {
                        contexto.drawImage(this, modelo[this.nombreCanvas].ejeXK[ejeX[this.indicei]] - 16, modelo[this.nombreCanvas].ejeYK[ejeY[this.indicej]] - 8, 32, 16);
                        //

                        document.getElementById('frmEstudioAudiologico:datos64' + this.oido).value = (document.getElementById(this.nombreCanvas).toDataURL()).replace(/^data:image\/(png|jpg);base64,/, "");
                    }
                }
            }
        }
    }
    //unir simbolos
    var primero = true;
    contexto.strokeStyle = color[oido];
    for (i = 0; i < ejeX.length; i++) {
        for (j = 0; j < ejeY.length; j++) {
            //for(k=0;k< simbolos.length ;k++) {
            if (modelo[nombreCanvas].matriz[ejeX[i]][ejeY[j]]['aerea'] || modelo[nombreCanvas].matriz[ejeX[i]][ejeY[j]]['aerea_mascara']) {
                if (primero) {
                    contexto.beginPath();
                    contexto.moveTo(modelo[nombreCanvas].ejeXK[ejeX[i]], modelo[nombreCanvas].ejeYK[ejeY[j]]);
                    primero = false;
                } else {
                    contexto.lineTo(modelo[nombreCanvas].ejeXK[ejeX[i]], modelo[nombreCanvas].ejeYK[ejeY[j]]);
                    contexto.moveTo(modelo[nombreCanvas].ejeXK[ejeX[i]], modelo[nombreCanvas].ejeYK[ejeY[j]]);
                }

            }
            //}
        }
    }
    contexto.stroke();

    //datos 64
    //document.getElementById('frmEstudioAudiologico:datos64' + oido).value = canvas.toDataURL();
    //alert(document.getElementById('frmEstudioAudiologico:datos64' + oido).value);

}

function dibujarPuntosLogo() {
    var nombreModelo;
    var contexto;
    var canvas;
    var img;
    var i, j, k, m;

    //Recorre los dos modelos(uno por oido en el mismo grafico)
    for (m = 0; m < oidos.length; m++) {
        nombreModelo = 'Logo' + oidos[m];
        contexto = cargaContextoCanvas('canvasLogo');
        canvas = document.getElementById('canvasLogo');

        //dibujar simbolos
        for (i = 0; i < ejeXLogo.length; i++) {
            for (j = 0; j < ejeYLogo.length; j++) {
                for (k = 0; k < simbolosLogo.length; k++) {
                    if (modelo[nombreModelo].matriz[ejeXLogo[i]][ejeYLogo[j]][simbolosLogo[k]]) {
                        img = new Image();
                        //img.src = modelo[idCanvas].matriz[i]['contenido'][j]['simbolo']+"."+modelo[idCanvas].matriz[i]['oido']+".png";//'aerea.derecha.png';
                        img.src = "resources/imagenes/" + simbolosLogo[k] + "." + cardinal[oidos[m]] + ".png";//'aerea.derecha.png';
                        img.indicei = i;
                        img.indicej = j;
                        img.nombreCanvas = 'canvasLogo';
                        img.nombreModelo = 'Logo' + oidos[m];
                        img.oido = oidos[m];

                        img.onload = function () {
                            contexto.drawImage(this, modelo[this.nombreModelo].ejeXK[ejeXLogo[this.indicei]] - 16, modelo[this.nombreModelo].ejeYK[ejeYLogo[this.indicej]] - 8, 32, 16);
                            //

                            document.getElementById('frmEstudioAudiologico:datos64Logo').value = (document.getElementById(this.nombreCanvas).toDataURL()).replace(/^data:image\/(png|jpg);base64,/, "");
                        }
                    }
                }
            }
        }
        //unir simbolos
        var primero = true;
        contexto.strokeStyle = color[oidos[m]];
        for (i = 0; i < ejeXLogo.length; i++) {
            for (j = 0; j < ejeYLogo.length; j++) {
                //for(k=0;k< simbolos.length ;k++) {
                if (modelo[nombreModelo].matriz[ejeXLogo[i]][ejeYLogo[j]]['aerea']) {
                    if (primero) {
                        contexto.beginPath();
                        contexto.moveTo(modelo[nombreModelo].ejeXK[ejeXLogo[i]], modelo[nombreModelo].ejeYK[ejeYLogo[j]]);
                        primero = false;
                    } else {
                        contexto.lineTo(modelo[nombreModelo].ejeXK[ejeXLogo[i]], modelo[nombreModelo].ejeYK[ejeYLogo[j]]);
                        contexto.moveTo(modelo[nombreModelo].ejeXK[ejeXLogo[i]], modelo[nombreModelo].ejeYK[ejeYLogo[j]]);
                    }

                }
                //}
            }
        }
        contexto.stroke();
    }





    //datos 64
    //document.getElementById('frmEstudioAudiologico:datos64' + oido).value = canvas.toDataURL();
    //alert(document.getElementById('frmEstudioAudiologico:datos64' + oido).value);

}

function dibujarGrilla(oido) {
    //Recibimos el elemento canvas
    var nombreCanvas = 'canvas' + oido;
    var contexto = cargaContextoCanvas(nombreCanvas);
    var i, indiceX = 0, indiceY = 0, pasoX, pasoY;
    contexto.strokeStyle = '#000000';
    if (contexto) {
        contexto.clearRect(0, 0, dimension, dimension);
        contexto.strokeRect(0, 0, dimension, dimension);

        pasoX = dimension / 7;
        pasoY = dimension / 14;

        //lineas horizontales
        contexto.lineWidth = 0.4;

        for (i = 0.5; i < dimension; i = i + pasoY) {
            contexto.beginPath();
            contexto.moveTo(0, i);
            contexto.lineTo(dimension, i);
            contexto.stroke();
            if (indiceY >= 1 && indiceY <= 13)
                modelo[nombreCanvas].ejeYK[ejeY[indiceY - 1]] = i;
            indiceY++;
        }

        //lineas verticales
        for (i = 0.5; i < dimension; i = i + pasoX) {
            contexto.beginPath();
            contexto.moveTo(i, 0);
            contexto.lineTo(i, dimension);
            contexto.stroke();
            if (indiceX >= 1 && indiceX <= 6)
                modelo[nombreCanvas].ejeXK[ejeX[indiceX - 1]] = i;
            indiceX++;
        }
    }
}

function dibujarGrillaLogo() {
    //Recibimos el elemento canvas
    var nombreCanvas = 'canvasLogo';
    var contexto = cargaContextoCanvas(nombreCanvas);
    var i, indiceX = 0, indiceY = 0, pasoX, pasoY;
    contexto.strokeStyle = '#000000';
    if (contexto) {
        contexto.clearRect(0, 0, anchoCanvasLogo, altoCanvasLogo);
        contexto.strokeRect(0, 0, anchoCanvasLogo, altoCanvasLogo);

        pasoX = anchoCanvasLogo / 12;
        pasoY = altoCanvasLogo / 12;

        //lineas horizontales
        contexto.lineWidth = 0.4;

        for (i = 0.5; i < altoCanvasLogo; i = i + pasoY) {
            contexto.beginPath();
            contexto.moveTo(0, i);
            contexto.lineTo(anchoCanvasLogo, i);
            contexto.stroke();
            if (indiceY >= 1 && indiceY <= 11) {
                modelo['LogoOD'].ejeYK[ejeYLogo[indiceY - 1]] = i;
                modelo['LogoOI'].ejeYK[ejeYLogo[indiceY - 1]] = i;
            }

            indiceY++;
        }

        //lineas verticales
        for (i = 0.5; i < anchoCanvasLogo; i = i + pasoX) {
            contexto.beginPath();
            contexto.moveTo(i, 0);
            contexto.lineTo(i, altoCanvasLogo);
            contexto.stroke();
            if (indiceX >= 1 && indiceX <= 11) {
                modelo['LogoOD'].ejeXK[ejeXLogo[indiceX - 1]] = i;
                modelo['LogoOI'].ejeXK[ejeXLogo[indiceX - 1]] = i;
            }
            indiceX++;
        }
    }
}



function actualizarModelo(idCanvas) {

    var contexto = cargaContextoCanvas(idCanvas);
    var canvas = document.getElementById(idCanvas);

    var bound = canvas.getBoundingClientRect();

//let x = event.clientX - bound.left - canvas.clientLeft - 8;
//let y = event.clientY - bound.top - canvas.clientTop - 8;

//Coordenadas reales de acuerdo al canvas
    var ClickX = event.clientX - bound.left;
    var ClickY = event.clientY - bound.top;

    var i, puntoMed, ejeGrillaX, ejeGrillaY;

//encontrar ejeX mas cercano
    for (i = 0; i < ejeX.length - 1; i++) {
        if (i == 0 && ClickX <= modelo[idCanvas].ejeXK[ejeX[i]]) {
            ejeGrillaX = ejeX[i];
            break;
        } else if (ClickX > modelo[idCanvas].ejeXK[ejeX[i]] && ClickX <= modelo[idCanvas].ejeXK[ejeX[i + 1]]) {
            puntoMed = Math.ceil((modelo[idCanvas].ejeXK[ejeX[i + 1]] - modelo[idCanvas].ejeXK[ejeX[i]]) / 2);
            if (ClickX > modelo[idCanvas].ejeXK[ejeX[i]] && ClickX <= modelo[idCanvas].ejeXK[ejeX[i]] + puntoMed) {
                ejeGrillaX = ejeX[i];
            } else if (ClickX > modelo[idCanvas].ejeXK[ejeX[i]] + puntoMed && ClickX <= modelo[idCanvas].ejeXK[ejeX[i + 1]]) {
                ejeGrillaX = ejeX[i + 1];
            }
            break;
        } else if (i == ejeX.length - 2 && ClickX > modelo[idCanvas].ejeXK[ejeX[i + 1]]) {
            ejeGrillaX = ejeX[i + 1];
            break;
        }
    }

//encontrar ejeY mas cercano
    for (i = 0; i < ejeY.length - 1; i++) {
        if (i == 0 && ClickY <= modelo[idCanvas].ejeYK[ejeY[i]]) {
            ejeGrillaY = ejeY[i];
            break;
        } else if (ClickY > modelo[idCanvas].ejeYK[ejeY[i]] && ClickY <= modelo[idCanvas].ejeYK[ejeY[i + 1]]) {
            puntoMed = Math.ceil((modelo[idCanvas].ejeYK[ejeY[i + 1]] - modelo[idCanvas].ejeYK[ejeY[i]]) / 2);
            if (ClickY > modelo[idCanvas].ejeYK[ejeY[i]] && ClickY <= modelo[idCanvas].ejeYK[ejeY[i]] + puntoMed) {
                ejeGrillaY = ejeY[i];
            } else if (ClickY > modelo[idCanvas].ejeYK[ejeY[i]] + puntoMed && ClickY <= modelo[idCanvas].ejeYK[ejeY[i + 1]]) {
                ejeGrillaY = ejeY[i + 1];
            }
            break;
        } else if (i == ejeY.length - 2 && ClickY > modelo[idCanvas].ejeYK[ejeY[i + 1]]) {
            ejeGrillaY = ejeY[i + 1];
            break;
        }
    }


    var radAccion = document.getElementsByName("frmEstudioAudiologico:accion");
    var radSimbolo = document.getElementsByName("frmEstudioAudiologico:simbolo");

    var accion, simbolo, i;
    for (i = 0; i < radAccion.length; i++) {
        if (radAccion[i].checked)
            accion = radAccion[i].value;
    }

    for (i = 0; i < radSimbolo.length; i++) {
        if (radSimbolo[i].checked)
            simbolo = radSimbolo[i].value;
    }


//si agregar, recorrer x (variar y) falseando
//luego X y Y true del simbolo
    if (accion == "agregar") {
        if (simbolo == 'aerea' || simbolo == 'aerea_mascara') {
            for (i = 0; i < ejeY.length; i++) {
                //modelo[nombreCanvas].matriz[ejeX[j]][ejeY[k]][simbolos[l]] = false;
                modelo[idCanvas].matriz[ejeGrillaX][ejeY[i]]['aerea'] = false;
                modelo[idCanvas].matriz[ejeGrillaX][ejeY[i]]['aerea_mascara'] = false;
            }
            modelo[idCanvas].matriz[ejeGrillaX][ejeGrillaY][simbolo] = true;
        }
        if (simbolo == 'osea' || simbolo == 'osea_mascara') {
            for (i = 0; i < ejeY.length; i++) {
                //modelo[nombreCanvas].matriz[ejeX[j]][ejeY[k]][simbolos[l]] = false;
                modelo[idCanvas].matriz[ejeGrillaX][ejeY[i]]['osea'] = false;
                modelo[idCanvas].matriz[ejeGrillaX][ejeY[i]]['osea_mascara'] = false;
            }
            modelo[idCanvas].matriz[ejeGrillaX][ejeGrillaY][simbolo] = true;
        }
    }

    if (accion == "retirar") {
        if (simbolo == 'aerea' || simbolo == 'aerea_mascara') {
            modelo[idCanvas].matriz[ejeGrillaX][ejeGrillaY]['aerea'] = false;
            modelo[idCanvas].matriz[ejeGrillaX][ejeGrillaY]['aerea_mascara'] = false;
        }
        if (simbolo == 'osea' || simbolo == 'osea_mascara') {
            modelo[idCanvas].matriz[ejeGrillaX][ejeGrillaY]['osea'] = false;
            modelo[idCanvas].matriz[ejeGrillaX][ejeGrillaY]['osea_mascara'] = false;
        }
    }


//construir xmlDOM a partir del modelo y actualizar el xml
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString("<audiograma></audiograma>", "text/xml");
    var nodeRow, nodeCol, root;
    /*
     var node = xmlDoc.createElement("heyHo");
     var elements = xmlDoc.getElementsByTagName("root");
     elements[0].appendChild(node);
     */
    root = xmlDoc.getElementsByTagName("audiograma");
    for (i = 0; i < ejeX.length; i++) {
        nodeRow = xmlDoc.createElement("puntos");
        nodeCol = xmlDoc.createElement("frecuencia");
        nodeCol.innerHTML = ejeX[i];
        nodeRow.appendChild(nodeCol);

        for (k = 0; k < simbolos.length; k++) {
            nodeCol = xmlDoc.createElement(simbolos[k]);
            for (j = 0; j < ejeY.length; j++) {
                if (modelo[idCanvas].matriz[ejeX[i]][ejeY[j]][simbolos[k]]) {
                    nodeCol.innerHTML = ejeY[j];
                }
            }
            nodeRow.appendChild(nodeCol);
        }
        root[0].appendChild(nodeRow);
    }



    /*
     
     <audiograma>
     <puntos><frecuencia>2K</frecuencia><aerea>10</aerea><aerea_mascara></aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     <puntos><frecuencia>8K</frecuencia><aerea>30</aerea><aerea_mascara></aerea_mascara><osea></osea><osea_mascara>10</osea_mascara></puntos>
     <puntos><frecuencia>250</frecuencia><aerea></aerea><aerea_mascara>50</aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     <puntos><frecuencia>500</frecuencia><aerea></aerea><aerea_mascara>20</aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     <puntos><frecuencia>1K</frecuencia><aerea>90</aerea><aerea_mascara></aerea_mascara><osea></osea><osea_mascara>10</osea_mascara></puntos>
     <puntos><frecuencia>4K</frecuencia><aerea></aerea><aerea_mascara>120</aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     </audiograma>
     
     */

//datos XML
    document.getElementById('frmEstudioAudiologico:datosXML' + oidoCanvas[idCanvas]).value = new XMLSerializer().serializeToString(xmlDoc.documentElement);
    //alert(document.getElementById('frmEstudioAudiologico:datosXML' + oidoCanvas[idCanvas]).value);

}

function actualizarModeloLogo() {

    var contexto = cargaContextoCanvas('canvasLogo');
    var canvas = document.getElementById('canvasLogo');

    var bound = canvas.getBoundingClientRect();

    var radCardinalLogo = document.getElementsByName("frmEstudioAudiologico:cardinalLogo");
    var cardinalLogo, p;
    for (p = 0; p < radCardinalLogo.length; p++) {
        if (radCardinalLogo[p].checked)
            cardinalLogo = radCardinalLogo[p].value;
    }
    
    var nombreModelo = 'Logo' + cardinalLogo;

//Coordenadas reales de acuerdo al canvas
    var ClickX = event.clientX - bound.left;
    var ClickY = event.clientY - bound.top;

    var i, puntoMed, ejeGrillaX, ejeGrillaY;

//encontrar ejeX mas cercano
    for (i = 0; i < ejeXLogo.length - 1; i++) {
        if (i == 0 && ClickX <= modelo[nombreModelo].ejeXK[ejeXLogo[i]]) {
            ejeGrillaX = ejeXLogo[i];
            break;
        } else if (ClickX > modelo[nombreModelo].ejeXK[ejeXLogo[i]] && ClickX <= modelo[nombreModelo].ejeXK[ejeXLogo[i + 1]]) {
            puntoMed = Math.ceil((modelo[nombreModelo].ejeXK[ejeXLogo[i + 1]] - modelo[nombreModelo].ejeXK[ejeXLogo[i]]) / 2);
            if (ClickX > modelo[nombreModelo].ejeXK[ejeXLogo[i]] && ClickX <= modelo[nombreModelo].ejeXK[ejeXLogo[i]] + puntoMed) {
                ejeGrillaX = ejeXLogo[i];
            } else if (ClickX > modelo[nombreModelo].ejeXK[ejeXLogo[i]] + puntoMed && ClickX <= modelo[nombreModelo].ejeXK[ejeXLogo[i + 1]]) {
                ejeGrillaX = ejeXLogo[i + 1];
            }
            break;
        } else if (i == ejeXLogo.length - 2 && ClickX > modelo[nombreModelo].ejeXK[ejeXLogo[i + 1]]) {
            ejeGrillaX = ejeXLogo[i + 1];
            break;
        }
    }

//encontrar ejeY mas cercano
    for (i = 0; i < ejeYLogo.length - 1; i++) {
        if (i == 0 && ClickY <= modelo[nombreModelo].ejeYK[ejeYLogo[i]]) {
            ejeGrillaY = ejeYLogo[i];
            break;
        } else if (ClickY > modelo[nombreModelo].ejeYK[ejeYLogo[i]] && ClickY <= modelo[nombreModelo].ejeYK[ejeYLogo[i + 1]]) {
            puntoMed = Math.ceil((modelo[nombreModelo].ejeYK[ejeYLogo[i + 1]] - modelo[nombreModelo].ejeYK[ejeYLogo[i]]) / 2);
            if (ClickY > modelo[nombreModelo].ejeYK[ejeYLogo[i]] && ClickY <= modelo[nombreModelo].ejeYK[ejeYLogo[i]] + puntoMed) {
                ejeGrillaY = ejeYLogo[i];
            } else if (ClickY > modelo[nombreModelo].ejeYK[ejeYLogo[i]] + puntoMed && ClickY <= modelo[nombreModelo].ejeYK[ejeYLogo[i + 1]]) {
                ejeGrillaY = ejeYLogo[i + 1];
            }
            break;
        } else if (i == ejeYLogo.length - 2 && ClickY > modelo[nombreModelo].ejeYK[ejeYLogo[i + 1]]) {
            ejeGrillaY = ejeYLogo[i + 1];
            break;
        }
    }
    
    //alert(ejeGrillaX + " " +ejeGrillaY);


    var radAccion = document.getElementsByName("frmEstudioAudiologico:accion");
    

    var accion, simbolo = 'aerea';
    for (i = 0; i < radAccion.length; i++) {
        if (radAccion[i].checked)
            accion = radAccion[i].value;
    }
/////hasta aqui

//si agregar, recorrer x (variar y) falseando
//luego X y Y true del simbolo
    if (accion == "agregar") {
        if (simbolo == 'aerea') {
            for (i = 0; i < ejeYLogo.length; i++) {
                //modelo[nombreCanvas].matriz[ejeX[j]][ejeY[k]][simbolos[l]] = false;
                modelo[nombreModelo].matriz[ejeGrillaX][ejeYLogo[i]]['aerea'] = false;
                //modelo[nombreModelo].matriz[ejeGrillaX][ejeYLogo[i]]['aerea_mascara'] = false;
            }
            modelo[nombreModelo].matriz[ejeGrillaX][ejeGrillaY][simbolo] = true;
        }
        /*
        if (simbolo == 'osea' || simbolo == 'osea_mascara') {
            for (i = 0; i < ejeY.length; i++) {
                //modelo[nombreCanvas].matriz[ejeX[j]][ejeY[k]][simbolos[l]] = false;
                modelo[nombreModelo].matriz[ejeGrillaX][ejeY[i]]['osea'] = false;
                modelo[nombreModelo].matriz[ejeGrillaX][ejeY[i]]['osea_mascara'] = false;
            }
            modelo[nombreModelo].matriz[ejeGrillaX][ejeGrillaY][simbolo] = true;
        }
        */
    }

    if (accion == "retirar") {
        if (simbolo == 'aerea') {
            modelo[nombreModelo].matriz[ejeGrillaX][ejeGrillaY]['aerea'] = false;
            //modelo[nombreModelo].matriz[ejeGrillaX][ejeGrillaY]['aerea_mascara'] = false;
        }
        /*
        if (simbolo == 'osea' || simbolo == 'osea_mascara') {
            modelo[nombreModelo].matriz[ejeGrillaX][ejeGrillaY]['osea'] = false;
            modelo[nombreModelo].matriz[ejeGrillaX][ejeGrillaY]['osea_mascara'] = false;
        }
        */
    }


//construir xmlDOM a partir del modelo y actualizar el xml
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString("<audiograma></audiograma>", "text/xml");
    var nodeRow, nodeCol, root;
    /*
     var node = xmlDoc.createElement("heyHo");
     var elements = xmlDoc.getElementsByTagName("root");
     elements[0].appendChild(node);
     */        
    
    
    root = xmlDoc.getElementsByTagName("audiograma");
    for (i = 0; i < ejeXLogo.length; i++) {
        nodeRow = xmlDoc.createElement("puntos");
        nodeCol = xmlDoc.createElement("frecuencia");
        nodeCol.innerHTML = ejeXLogo[i];
        nodeRow.appendChild(nodeCol);

        for (k = 0; k < simbolosLogo.length; k++) {
            nodeCol = xmlDoc.createElement(simbolosLogo[k]);
            for (j = 0; j < ejeYLogo.length; j++) {
                if (modelo[nombreModelo].matriz[ejeXLogo[i]][ejeYLogo[j]][simbolosLogo[k]]) {
                    nodeCol.innerHTML = ejeYLogo[j];
                }
            }
            nodeRow.appendChild(nodeCol);
        }
        root[0].appendChild(nodeRow);
    }



    /*
     
     <audiograma>
     <puntos><frecuencia>2K</frecuencia><aerea>10</aerea><aerea_mascara></aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     <puntos><frecuencia>8K</frecuencia><aerea>30</aerea><aerea_mascara></aerea_mascara><osea></osea><osea_mascara>10</osea_mascara></puntos>
     <puntos><frecuencia>250</frecuencia><aerea></aerea><aerea_mascara>50</aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     <puntos><frecuencia>500</frecuencia><aerea></aerea><aerea_mascara>20</aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     <puntos><frecuencia>1K</frecuencia><aerea>90</aerea><aerea_mascara></aerea_mascara><osea></osea><osea_mascara>10</osea_mascara></puntos>
     <puntos><frecuencia>4K</frecuencia><aerea></aerea><aerea_mascara>120</aerea_mascara><osea>10</osea><osea_mascara></osea_mascara></puntos>
     </audiograma>
     
     */

//datos XML datosXMLLogoOD datosXMLLogoOI

    document.getElementById('frmEstudioAudiologico:datosXML' + nombreModelo).value = new XMLSerializer().serializeToString(xmlDoc.documentElement);
    //alert(document.getElementById('frmEstudioAudiologico:datosXML' + nombreModelo).value);
    //alert(document.getElementById('frmEstudioAudiologico:datosXML' + oidoCanvas[idCanvas]).value);

}

function iniciar() {
    var i;
    inicializarModelos();
    for (i = 0; i < oidos.length; i++) {
        dibujarGrilla(oidos[i]);
        analizarTabla(oidos[i]);
        dibujarPuntos(oidos[i]);
    }
    dibujarGrillaLogo();
    analizarTablaLogo();
    dibujarPuntosLogo();
}

function actualizarGrafica(idCanvas) {

    //borrar y volver a dibujar grilla
    dibujarGrilla(oidoCanvas[idCanvas]);
    //analizar coordenadas, accion y actualizar arreglo
    actualizarModelo(idCanvas);
    //dibujar los puntos de acuerdo a la informacion del arreglo
    dibujarPuntos(oidoCanvas[idCanvas]);
}

function actualizarGraficaLogo() {

    //borrar y volver a dibujar grilla
    dibujarGrillaLogo();
    //analizar coordenadas, accion y actualizar arreglo
    actualizarModeloLogo();
    //dibujar los puntos de acuerdo a la informacion del arreglo
    dibujarPuntosLogo();
}
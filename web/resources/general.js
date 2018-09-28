function replaceAll( text, busca, reemplaza ){ 
while (text.toString().indexOf(busca) != -1){ 
text = text.toString().replace(busca,reemplaza); 
}return text; 
}

function Mayusculas(id){
        var x=document.getElementById(id);
        x.valxue=x.value.toUpperCase();
}

function setFoco (componente){
    document.getElementById(document.forms[0].id+':'+componente).focus()
}

function Trim(sString){
    while (sString.substring(0,1) == ' ')
        sString = sString.substring(1, sString.length);
    while (sString.substring(sString.length-1, sString.length) == ' ')
        sString = sString.substring(0,sString.length-1);
    return sString;
}

function validarEntero(valor){
    valor = parseInt(valor)
    if (isNaN(valor)) {
         return '0';
    }else{
         return valor;
    }
}

function validarEntero2(valor){
    if (!/^([0-9])+$/.test(valor))
        return false;
    else
        return true;
}

function validarFlotante(valor) {
    var totalNumeros=0;
    for (i = 0; i < valor.length; i++) {
        if ( !( (valor.charAt(i) >= "0") && (valor.charAt(i) <= "9") ) ){
            if (valor.charAt(i) != "."){
                return '0';
            }
        }
        else{
            totalNumeros++;
        }
    }
    if (totalNumeros==0){
        return '0';
    }
    return valor;
}

function validarTelefono(valor) {
    if (!/^([0-9\s])*$/.test(valor))
        return false;
    else
        return true;
}

function validarIdentificacion(valor) {
    if (!/^[a-zA-Z0-9\-]+$/.test(valor))
        return false;
    else
        return true;
}

function EsNumero(campo,ceros) {
    var campo1 = document.getElementById(campo);
    var totalNumeros=0;
    for (i = 0; i < campo1.value.length; i++) {
        if ( !( (campo1.value.charAt(i) >= "0") && (campo1.value.charAt(i) <= "9") ) ){
            if (campo1.value.charAt(i) != "." && campo1.value.charAt(i) != "E" && campo1.value.charAt(i) != "R" && campo1.value.charAt(i) != "-"){
                campo1.value = '0';
                campo1.focus();
                return false;
            }
        }
        else{
            totalNumeros++;
        }
    }
    if (totalNumeros==0){
        campo1.value = '0';
        campo1.focus();
        return false;
    }
    return true;         	        	         	       
}

function diferenciaFechas (key1,key2){
    
    if (key1=='' || key2==''){
        return false;
    } 
    
    var fecha1 = key1.split('-');
    var fecha2 = key2.split('-');

   
    if (Number(fecha1[2]) > Number(fecha2[2])){
        return true;
    }else if (Number(fecha1[2]) >= Number(fecha2[2]) && Number(fecha1[1])>Number(fecha2[1])){
        
        return true;
    }else if (Number(fecha1[2]) >= Number(fecha2[2]) && Number(fecha1[1])==Number(fecha2[1]) && Number(fecha1[0])>=Number(fecha2[0])) {
        
        return true;
    }else {
        return false;
    }
    return false;
}

function mod(dividendo,divisor) {
    return Math.round(dividendo - (Math.floor(dividendo/divisor)*divisor));
}

function validarLogin(){
  var usu=document.getElementById('pagIndex:txtUsuario').value;
  var clave=document.getElementById('pagIndex:txtClave').value;
  var emp=document.getElementById('pagIndex:txtEmpresas').value;


  if(usu==''){
      alert('Por favor digite el usuario');
      document.getElementById('pagIndex:txtUsuario').focus();
      return false;
  }
  if(clave==''){
      alert('Por favor digite la clave');
      document.getElementById('pagIndex:txtClave').focus();
      return false;
  }
  
    if(emp==''){
      alert('Por favor seleccione una empresa');
      document.getElementById('pagIndex:txtEmpresas').focus();
      return false;
  }

  return true;//Devolver true para habilitar el action del boton

}

function get(id) {
    return document.getElementById(document.forms[0].id+':'+id);
}


function mensajes(data) {
    for(var i = 0; i < data.length; i++){
        if(data[i].tipo=='alert'){
            alert(data[i].descripcion);
            
            document.getElementById('pagIngreso:txtSerialAudifarma').value='';
            document.getElementById('pagIngreso:txtSerialHardware').value='';
            document.getElementById('pagIngreso:txtSerialAudifarma').focus();

        } else if(data[i].tipo=='confirm'){
            if(confirm(data[i].descripcion)){
                try{
                    eval(data[i].respuestaSi+"('"+data[i].parametros+"')");
                }catch(E){
                    alert(E);
                }
            } else {
                try{
                    eval(data[i].respuestaNo+"('"+data[i].parametros+"')");
                }catch(E){
                    alert(E);
                }
            }
        } else if(data[i].tipo=='windowopen'){
            data[i].ruta = replaceAll(data[i].ruta,'%','');
            window.open(data[i].ruta);
        } else if(data[i].tipo=='modal'){
            Richfaces.showModalPanel(data[i].descripcion);
            //document.getElementById('pagF:detalle:aceptar').focus();
        } else if (data[i].tipo=='hide-modal'){
             Richfaces.hideModalPanel(data[i].descripcion);
        }else if(data[i].tipo=='data'){
            eval(data[i].descripcion);
        }
    }
}




function validarGarantrep(){

  var fechaIng=document.getElementById('PagGarantrep:date').value;
  var Proveedor=document.getElementById('PagGarantrep:cmbProveedor').value;
  var Tipomov=document.getElementById('PagGarantrep:cmbTipomovimiento').value;
  var SerialAudi=document.getElementById('PagGarantrep:txtSerialAudifarma').value;
  var SerialHw=document.getElementById('PagGarantrep:txtSerialHardware').value;

  patron=/^[a-zA-Z0-9\-\s\xf1\xe1\xe9\xed\xf3\xfa\xd1\xc1\xc9\xcd\xd3\xda]+$/;



     if (fechaIng==''){
        alert('Por favor digite la Fecha');
        document.getElementById('PagGarantrep:date').focus();
        return false;
     }
     
     if (Proveedor=='none'){      
        alert('Por favor seleccione un Proveedor');
        document.getElementById('PagGarantrep:cmbProveedor').focus();
        return false;
     }
     
     if (Tipomov=='none'){      
        alert('Por favor seleccione el Tipo de Movimiento');
        document.getElementById('PagGarantrep:cmbTipomovimiento').focus();
        return false;
     }     

     if (!/^[0-9]/.test(SerialAudi)){
        alert('Por favor digite el Serial Audifarma');
        document.getElementById('PagGarantrep:txtSerialAudifarma').focus();
        return false;
     }
  
     if (SerialHw==''){
        alert('Por favor digite Serial del Activo');
        document.getElementById('PagGarantrep:txtSerialHardware').focus();
        return false;
     }

        return true;//Devolver true para habilitar el action del boton
}



function validarIngresohistorico(){

  var fechaIng=document.getElementById('pagTraslados:date').value;
  var tmov=document.getElementById('pagTraslados:cmbTipomovimiento').value; 
  var SerialAudi=document.getElementById('pagTraslados:txtSerialAudifarma').value;
  var SerialHw=document.getElementById('pagTraslados:txtSerialHardware').value;
  var empresa=document.getElementById('pagTraslados:cmbEmpresa').value;
  var caf=document.getElementById('pagTraslados:cmbCaf').value;
  var depto=document.getElementById('pagTraslados:cmbDepto').value; 
  var funcionario=document.getElementById('pagTraslados:cmbFuncionario').value;
  var Incidencia=document.getElementById('pagTraslados:txtIncidencia').value;

  patron=/^[a-zA-Z0-9\-\s\xf1\xe1\xe9\xed\xf3\xfa\xd1\xc1\xc9\xcd\xd3\xda]+$/;
        


  if(fechaIng==''){
      alert('Por favor digite la Fecha');
      document.getElementById('pagTraslados:date').focus();
      return false;
  }
  
  if(tmov=='none'){      
      alert('Por favor seleccione el Tipo de Movimiento');
      document.getElementById('pagTraslados:cmbTipomovimiento').focus();
      return false;
  }
  
 if(SerialAudi=='' || !patron.test(SerialAudi)){
      alert('Por favor digite Serial Audifarma');
      document.getElementById('pagTraslados:txtSerialAudifarma').focus();
      return false;
 }  
  
 if(SerialHw=='' || !patron.test(SerialHw)){
      alert('Por favor digite Serial Activo');
      document.getElementById('pagTraslados:txtSerialHardware').focus();
      return false;
 }
     if (!/^[0-9]/.test(Incidencia)){
     alert('Por favor verifique el número de Incidencia');
     setFoco('txtIncidencia');
     return false;
    }

  if(empresa=='none'){
      alert('Por favor seleccione una Empresa');
      document.getElementById('pagIngreso:cmbEmpresa').focus();
      return false;
  }

  if(caf=='none'){
      alert('Por favor seleccione Centro Operacion');
      document.getElementById('pagTraslados:cmbCaf').focus();
      return false;
  }
  
  if(funcionario=='none'){
      alert('Por favor seleccione Responsable');
      document.getElementById('pagTraslados:cmbFuncionario').focus();
      return false;
   }  
  
  
    if(depto=='none'){
      alert('Por favor seleccione el Departamento');
      document.getElementById('pagTraslados:cmbDepto').focus();
      return false;
  }

  return true;//Devolver true para habilitar el action del boton

}

function mensajesHistorico(data) {
    for(var i = 0; i < data.length; i++){
        if(data[i].tipo=='alert'){
            alert(data[i].descripcion);
             
          document.getElementById('pagTraslados:cmbTipomovimiento').value='none';
          document.getElementById('pagTraslados:cmbTipomovimiento').label='Seleccione...';                    
          document.getElementById('pagTraslados:txtSerialAudifarma').value='';
          document.getElementById('pagTraslados:txtSerialHardware').value='';          
          document.getElementById('pagTraslados:txtIncidencia').value='';
          document.getElementById('pagTraslados:cmbCaf').value='none';
          document.getElementById('pagTraslados:cmbCaf').label='Seleccione...';
          document.getElementById('pagTraslados:cmbEmpresa').value='none';          
          document.getElementById('pagTraslados:cmbEmpresa').label='Seleccione...';          
          document.getElementById('pagTraslados:cmbDepto').value='none';
          document.getElementById('pagTraslados:cmbDepto').label='Seleccione...';          
          document.getElementById('pagTraslados:cmbFuncionario').value='none';
          document.getElementById('pagTraslados:cmbFuncionario').label='Seleccione...';          
          document.getElementById('pagTraslados:observaciones').value='';
          document.getElementById('pagTraslados:cmbTipomovimiento').focus();
            
        } else if(data[i].tipo=='confirm'){
            if(confirm(data[i].descripcion)){
                try{
                    eval(data[i].respuestaSi+"('"+data[i].parametros+"')");
                }catch(E){
                    alert(E);
                }
            } else {
                try{
                    eval(data[i].respuestaNo+"('"+data[i].parametros+"')");
                }catch(E){
                    alert(E);
                }
            }
        } else if(data[i].tipo=='windowopen'){
            data[i].ruta = replaceAll(data[i].ruta,'%','');
            window.open(data[i].ruta);
        } else if(data[i].tipo=='modal'){
            Richfaces.showModalPanel(data[i].descripcion);
            //document.getElementById('pagF:detalle:aceptar').focus();
        } else if (data[i].tipo=='hide-modal'){
             Richfaces.hideModalPanel(data[i].descripcion);
        }else if(data[i].tipo=='data'){
            eval(data[i].descripcion);
        }
    }
}

function borrarTraslado() {
                 
          document.getElementById('pagTraslados:cmbTipomovimiento').value='none';
          document.getElementById('pagTraslados:cmbTipomovimiento').label='Seleccione...';                    
          document.getElementById('pagTraslados:txtSerialAudifarma').value='';
          document.getElementById('pagTraslados:txtSerialHardware').value='';          
          document.getElementById('pagTraslados:txtIncidencia').value='';
          document.getElementById('pagTraslados:txtEmpresaActual').value='';     
          document.getElementById('pagTraslados:cmbEmpresa').value='none';          
          document.getElementById('pagTraslados:cmbEmpresa').label='Seleccione...';
          document.getElementById('pagTraslados:cmbDepto').value='none';
          document.getElementById('pagTraslados:cmbDepto').label='Seleccione...';
          document.getElementById('pagTraslados:cmbCaf').value='none';
          document.getElementById('pagTraslados:cmbCaf').label='Seleccione...';
          document.getElementById('pagTraslados:txtResponsable').value='';                       
          document.getElementById('pagTraslados:cmbFuncionario').value='none';
          document.getElementById('pagTraslados:cmbFuncionario').label='Seleccione...'; 
          document.getElementById('pagTraslados:txtTipoActivo').value='';                                 
          document.getElementById('pagTraslados:observaciones').value='';
          document.getElementById('pagTraslados:txtSerialAudifarma').focus();
            
        }



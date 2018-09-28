/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function validarMostrarAgenda() {
    if (document.getElementById('frmNuevaCita:cedulaPaciente').value == '' || document.getElementById('frmNuevaCita:nombrePaciente').value == '') {
        PF('mensajesCS').renderMessage({"summary": "Ingrese un paciente.", "severity": "info"});
    }
    else {
        if (document.getElementById('frmNuevaCita:especialidad_input').value == '') {
            PF('mensajesCS').renderMessage({"summary": "Elija una especialidad.", "severity": "info"});
        }
        else {
            PF('dlgCalendarioCitaW').show();
        }
    }
}



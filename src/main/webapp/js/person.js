// Arreglo de registros de personas
let arreglo = [];

// Constantes del CRUD para personas
const ACCION_NUEVO = "NUEVO";
const ACCION_EDITAR = "EDITAR";
const ACCION_ELIMINAR = "ELIMINAR";
const ACCION_RESTAURAR = "RESTAURAR";

// Campos del formulario de edición de personas
let accion = document.getElementById("accion");
let frmPersonId = document.getElementById("frmPersonId");
let frmFirstName = document.getElementById("frmFirstName");
let frmLastName = document.getElementById("frmLastName");
let frmPhoneNumber = document.getElementById("frmPhoneNumber");
let frmDateOfBirth = document.getElementById("frmDateOfBirth");
let frmTypeDocument = document.getElementById("frmTypeDocument");
let frmNumberDocument = document.getElementById("frmNumberDocument");
let frmGender = document.getElementById("frmGender");
let frmEmail = document.getElementById("frmEmail");
let frmActive = document.getElementById("frmActive");
let frmDepartmentId = document.getElementById("frmDepartmentId");
let frmProvinceId = document.getElementById("frmProvinceId");
let frmDistrictId = document.getElementById("frmDistrictId");

// Variables para los botones
let controlBtnNuevo = document.getElementById("controlBtnNuevo");
let btnProcesar = document.getElementById("btnProcesar");

// Programación de botones
if (controlBtnNuevo) {
    controlBtnNuevo.addEventListener("click", fnControlBtnNuevo);
}

if (btnProcesar) {
    btnProcesar.addEventListener("click", fnBtnProcesar);
}

// Llamada inicial para mostrar datos al cargar la página
window.onload = function () {
    fnControlBtnConsultar();
};

// Funciones de los botones
function fnControlBtnConsultar() {
    let urlPersonas = "/crud/personas/activas";

    // Proceso con AJAX para obtener todas las personas
    let xhttpPersonas = new XMLHttpRequest();
    xhttpPersonas.open("GET", urlPersonas, true);
    xhttpPersonas.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            arreglo = JSON.parse(this.responseText);
            mostrarDatosEnTabla();
        }
    };
    xhttpPersonas.send();
}

function fnControlBtnNuevo() {
    let tituloRegistro = document.getElementById("exampleModalLabel");
    if (tituloRegistro) {
        tituloRegistro.innerHTML = ACCION_NUEVO + " PERSONA";
    } else {
        console.error("Elemento con ID 'exampleModalLabel' no encontrado.");
        return;
    }

    document.getElementById("accion").value = ACCION_NUEVO;
    fnEstadoFormulario(true);

    // Lógica para abrir el modal
    let modalEdicionElement = document.getElementById('modalEdicion');
    if (modalEdicionElement) {
        let modalEdicion = new bootstrap.Modal(modalEdicionElement);
        modalEdicion.show();
    } else {
        console.error("Elemento con ID 'modalEdicion' no encontrado.");
    }
}

function fnBtnProcesar() {
    if (!fnValidar()) {
        return;
    }

    // Preparar los datos
    let datos = "accion=" + accion.value;
    datos += "&personId=" + frmPersonId.value;
    datos += "&firstName=" + frmFirstName.value;
    datos += "&lastName=" + frmLastName.value;
    datos += "&phoneNumber=" + frmPhoneNumber.value;
    datos += "&dateOfBirth=" + frmDateOfBirth.value;
    datos += "&typeDocument=" + frmTypeDocument.value;
    datos += "&numberDocument=" + frmNumberDocument.value;
    datos += "&gender=" + frmGender.value;
    datos += "&email=" + frmEmail.value;
    datos += "&active=" + frmActive.value;
    datos += "&departmentId=" + frmDepartmentId.value;
    datos += "&provinceId=" + frmProvinceId.value;
    datos += "&districtId=" + frmDistrictId.value;

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/crud/personas/procesar-persona", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                // Utilizar SweetAlert2 para mostrar un mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Éxito',
                    text: 'Proceso completado correctamente.',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    // Cerrar el modal después de mostrar el mensaje
                    let modalEdicionElement = document.getElementById('modalEdicion');
                    if (modalEdicionElement) {
                        let modalEdicion = bootstrap.Modal.getInstance(modalEdicionElement);
                        modalEdicion.hide();
                    }
                    // Luego de procesar, actualizamos la lista de personas
                    fnControlBtnConsultar();
                });
            } else {
                // Mostrar mensaje de error con SweetAlert2
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Hubo un error en el proceso. Por favor, inténtalo nuevamente.',
                });
            }
        }
    };
    xhttp.send(datos);
}

function fnEstadoFormulario(esNuevo) {
    // Ajusta el estado del formulario según sea necesario
    if (esNuevo) {
        // Lógica para cuando se está creando una nueva persona
        frmPersonId.value = "";
        frmFirstName.value = "";
        frmLastName.value = "";
        frmPhoneNumber.value = "";
        frmDateOfBirth.value = "";
        frmTypeDocument.value = "";
        frmNumberDocument.value = "";
        frmGender.value = "";
        frmEmail.value = "";
        frmActive.value = "";
        frmDepartmentId.value = "";
        frmProvinceId.value = "";
        frmDistrictId.value = "";
    }
}

function fnValidar() {
    // Agrega la lógica de validación según tus necesidades
    return true;
}

function mostrarDatosEnTabla() {
    let detalleTabla = "";
    arreglo.forEach(function (item) {
        detalleTabla += "<tr>";
        detalleTabla += "<td>" + item.firstName + " " + item.lastName + "</td>";
        detalleTabla += "<td>" + item.phoneNumber + "</td>";
        detalleTabla += "<td>" + item.dateOfBirth + "</td>";
        detalleTabla += "<td>" + item.typeDocument + " - " + item.numberDocument + "</td>";
        detalleTabla += "<td>" + item.gender + "</td>";
        detalleTabla += "<td>" + item.email + "</td>";
        detalleTabla += "<td>" + item.active + "</td>";
        detalleTabla += "<td>";

        if (item.active === 'A') {
            detalleTabla += "<a href='javascript:fnEditar(" + item.personId + ");'>Editar</a>";
            detalleTabla += "<a href='javascript:fnEliminar(" + item.personId + ");'>Eliminar</a>";
        } else {
            detalleTabla += "<a href='javascript:fnRestaurar(" + item.personId + ");'>Restaurar</a>";
        }

        detalleTabla += "</td>";
        detalleTabla += "</tr>";
    });

    let divResultado = document.getElementById("divResultado");
    if (divResultado && detalleTabla) {
        divResultado.innerHTML = "<table border='1' class='table table-striped'>" +
            "<thead>" +
            "<tr>" +
            "<th>Nombre</th>" +
            "<th>Teléfono</th>" +
            "<th>Fecha de Nacimiento</th>" +
            "<th>Documento</th>" +
            "<th>Género</th>" +
            "<th>Email</th>" +
            "<th>Estado</th>" +
            "<th>Acciones</th>" +
            "</tr>" +
            "</thead>" +
            "<tbody id='detalleTabla'>" + detalleTabla + "</tbody>" +
            "</table>";
    } else {
        console.error("Elemento con ID 'divResultado' o 'detalleTabla' no encontrado.");
    }
}

function fnEditar(personId) {
    let tituloRegistro = document.getElementById("exampleModalLabel");
    if (tituloRegistro) {
        tituloRegistro.innerHTML = ACCION_EDITAR + " PERSONA";
    } else {
        console.error("Elemento con ID 'exampleModalLabel' no encontrado.");
        return;
    }

    document.getElementById("accion").value = ACCION_EDITAR;
    fnEstadoFormulario(false);

    // Lógica para cargar los datos de la persona seleccionada en el formulario
    let personaSeleccionada = arreglo.find(function (item) {
        return item.personId === personId;
    });

    if (personaSeleccionada) {
        frmPersonId.value = personaSeleccionada.personId;
        frmFirstName.value = personaSeleccionada.firstName;
        frmLastName.value = personaSeleccionada.lastName;
        frmPhoneNumber.value = personaSeleccionada.phoneNumber;
        frmDateOfBirth.value = personaSeleccionada.dateOfBirth;
        frmTypeDocument.value = personaSeleccionada.typeDocument;
        frmNumberDocument.value = personaSeleccionada.numberDocument;
        frmGender.value = personaSeleccionada.gender;
        frmEmail.value = personaSeleccionada.email;
        frmActive.value = personaSeleccionada.active;
        frmDepartmentId.value = personaSeleccionada.departmentId;
        frmProvinceId.value = personaSeleccionada.provinceId;
        frmDistrictId.value = personaSeleccionada.districtId;

        let modalEdicionElement = document.getElementById('modalEdicion');
        if (modalEdicionElement) {
            let modalEdicion = new bootstrap.Modal(modalEdicionElement);
            modalEdicion.show();
        } else {
            console.error("Elemento con ID 'modalEdicion' no encontrado.");
        }
    } else {
        console.error("No se encontró la persona con ID " + personId);
    }
}

function fnEliminar(personId) {
    // Lógica para eliminar una persona
    // Utiliza SweetAlert2 para confirmar la eliminación
    // ... tu lógica aquí ...
}

function fnRestaurar(personId) {
    // Lógica para restaurar una persona eliminada
    // Utiliza SweetAlert2 para confirmar la restauración
    // ... tu lógica aquí ...
}

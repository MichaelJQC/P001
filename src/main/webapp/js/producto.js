// Arreglo de registros
let arreglo = [];

// Constantes del CRUD
const ACCION_NUEVO = "NUEVO";
const ACCION_EDITAR = "EDITAR";
const ACCION_ELIMINAR = "ELIMINAR";

// Campos del formulario de edición
let accion = document.getElementById("accion");
let frmId = document.getElementById("frmId");
let frmNombre = document.getElementById("frmNombre");
let frmCodigo = document.getElementById("frmCodigo");
let frmCantidad = document.getElementById("frmCantidad");
let frmPrecio = document.getElementById("frmPrecio");
let frmEstado = document.getElementById("frmEstado");

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
    let urlProductos = "/crud/productos";
    let urlCategorias = "/crud/categorias"; // Ajusta la URL según tu endpoint correcto

    // Proceso con AJAX para obtener productos
    let xhttpProductos = new XMLHttpRequest();
    xhttpProductos.open("GET", urlProductos, true);
    xhttpProductos.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            arreglo = JSON.parse(this.responseText);

            // Proceso con AJAX para obtener categorías
            let xhttpCategorias = new XMLHttpRequest();
            xhttpCategorias.open("GET", urlCategorias, true);
            xhttpCategorias.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    categorias = JSON.parse(this.responseText);
                    mostrarDatosEnTabla();
                }
            };
            xhttpCategorias.send();
        }
    };
    xhttpProductos.send();
}


function fnControlBtnNuevo() {
    let tituloRegistro = document.getElementById("exampleModalLabel");
    if (tituloRegistro) {
        tituloRegistro.innerHTML = ACCION_NUEVO + " PRODUCTO";
    } else {
        console.error("Elemento con ID 'exampleModalLabel' no encontrado.");
        return;
    }

    document.getElementById("accion").value = ACCION_NUEVO;
    fnEstadoFormulario(true);

    let modalEdicionElement = document.getElementById('modalEdicion');
    if (modalEdicionElement) {
        let modalEdicion = new bootstrap.Modal(modalEdicionElement);
        modalEdicion.show();
        // Llenar las opciones del campo de categoría y establecer el valor para un nuevo producto
        llenarCategorias(null, categorias);
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
    datos += "&id=" + frmId.value;
    datos += "&nombre=" + frmNombre.value;
    datos += "&codigo=" + frmCodigo.value;
    datos += "&cantidad=" + frmCantidad.value;
    datos += "&precio=" + frmPrecio.value;
    datos += "&categoria_id=" + document.getElementById("frmCategoria").value;
    datos += "&active=" + frmEstado.value;

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/crud/procesar", true);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            alert(this.responseText);
            // Luego de procesar, actualizamos la lista de productos
            fnControlBtnConsultar();
        }
    };
    xhttp.send(datos);
}

function fnEstadoFormulario(esNuevo) {
    // Ajusta aquí según tus necesidades, por ejemplo, habilitar/deshabilitar campos, limpiar valores, etc.
    frmId.disabled = esNuevo;
    frmNombre.value = "";
    frmCodigo.value = "";
    frmCantidad.value = "";
    frmPrecio.value = "";
    frmEstado.value = "A";
}

function fnValidar() {
    // Agrega la lógica de validación según tus necesidades
    return true;
}

function mostrarDatosEnTabla() {
    let detalleTabla = "";
    arreglo.forEach(function (item) {
        detalleTabla += "<tr>";
        detalleTabla += "<td>" + item.id + "</td>";
        detalleTabla += "<td>" + item.nombre + "</td>";
        detalleTabla += "<td>" + item.codigo + "</td>";
        detalleTabla += "<td>" + item.cantidad + "</td>";
        detalleTabla += "<td>" + item.precio + "</td>";
        detalleTabla += "<td>" + item.categoria.nombre + "</td>";
        detalleTabla += "<td>" + item.active + "</td>";
        detalleTabla += "<td>";
        detalleTabla += "<a href='javascript:fnEditar(" + item.id + ");'>Editar</a> ";
        detalleTabla += "<a href='javascript:fnEliminar(" + item.id + ");'>Eliminar</a>";
        detalleTabla += "</td>";
        detalleTabla += "</tr>";
    });
    // Reemplaza el contenido de innerHTML solo si el elemento existe
    let detalleTablaElement = document.getElementById("detalleTabla");
    if (detalleTablaElement) {
        detalleTablaElement.innerHTML = detalleTabla;
    }
    // También, actualiza el estado de visibilidad de los elementos solo si existen
    let divResultado = document.getElementById("divResultado");
    let divEdicion = document.getElementById("divEdicion");
    if (divResultado && divEdicion) {
        divResultado.style.display = "block";
        divEdicion.style.display = "none";
    }
}

function fnEditar(id) {
    // Lógica para editar el registro con el ID proporcionado
    // Por ejemplo, podrías buscar el registro en el arreglo y llenar el formulario de edición.
    let registro = arreglo.find(item => item.id === id);

    if (registro) {
        let tituloRegistro = document.getElementById("exampleModalLabel");
        if (tituloRegistro) {
            tituloRegistro.innerHTML = ACCION_EDITAR + " PRODUCTO";
        } else {
            console.error("Elemento con ID 'exampleModalLabel' no encontrado.");
            return;
        }

        document.getElementById("accion").value = ACCION_EDITAR;
        frmId.value = registro.id;
        frmNombre.value = registro.nombre;
        frmCodigo.value = registro.codigo;
        frmCantidad.value = registro.cantidad;
        frmPrecio.value = registro.precio;
        frmEstado.value = registro.active;

        // Llenar las opciones del campo de categoría y establecer el valor
        llenarCategorias(registro.categoria.id, categorias);

        // Asegúrate de que el modal se haya inicializado correctamente antes de mostrarlo
        let modalEdicionElement = document.getElementById('modalEdicion');
        if (modalEdicionElement) {
            let modalEdicion = new bootstrap.Modal(modalEdicionElement);
            modalEdicion.show();
        } else {
            console.error("Elemento con ID 'modalEdicion' no encontrado.");
        }
    } else {
        console.error("Registro no encontrado con ID: " + id);
    }
}

// Nueva función para llenar las opciones del campo de categoría
function llenarCategorias(selectedCategoriaId, categorias) {
    // Obtener el elemento del campo de categoría
    let selectCategoria = document.getElementById("frmCategoria");

    // Limpiar las opciones actuales
    selectCategoria.innerHTML = "";

    // Llenar las opciones desde el arreglo de categorías
    categorias.forEach(categoria => {
        let option = document.createElement("option");
        option.value = categoria.id;
        option.text = categoria.nombre;
        selectCategoria.appendChild(option);
    });

    // Establecer el valor seleccionado
    selectCategoria.value = selectedCategoriaId;
}

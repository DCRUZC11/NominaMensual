package modelo;

// reúne los datos comunes y define los métodos
// que cada tipo de empleado debe implementar.
public abstract class Empleado {

    // los atributos solo se usan directamente en esta clase.
    private String dpi;
    private String nombre;

    // dpi y nombre son parámetros del constructor
    public Empleado(String dpi, String nombre) {
        if (dpi == null || !dpi.matches("[0-9]{13}")) {
            throw new IllegalArgumentException("El DPI debe tener 13 dígitos.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        // this identifica los atributos del objeto
        this.dpi = dpi;
        this.nombre = nombre.trim();
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract double calcularPago();

    public abstract String obtenerTipo();
}
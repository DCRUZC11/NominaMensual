package modelo;

// Herencia: EmpleadoFijo recibe los datos y métodos de Empleado.
public class EmpleadoFijo extends Empleado {

    private double sueldoMensual;
    private double bonificacion;

    public EmpleadoFijo(String dpi, String nombre,
                        double sueldoMensual, double bonificacion) {

        // Inicializa los atributos comunes mediante el constructor padre.
        super(dpi, nombre);

        if (!Double.isFinite(sueldoMensual) || sueldoMensual < 0
                || !Double.isFinite(bonificacion) || bonificacion < 0) {
            throw new IllegalArgumentException(
                    "Los valores deben ser finitos y mayores o iguales a cero.");
        }

        this.sueldoMensual = sueldoMensual;
        this.bonificacion = bonificacion;
    }

    public double getSueldoMensual() {
        return sueldoMensual;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    // Sobrescritura: esta clase define su propia fórmula.
    @Override
    public double calcularPago() {
        return sueldoMensual + bonificacion;
    }

    @Override
    public String obtenerTipo() {
        return "Fijo";
    }
}
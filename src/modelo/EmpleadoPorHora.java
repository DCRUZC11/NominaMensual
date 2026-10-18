package modelo;

public class EmpleadoPorHora extends Empleado {

    private double pagoPorHora;
    private double horasTrabajadas;

    public EmpleadoPorHora(String dpi, String nombre,
                           double pagoPorHora, double horasTrabajadas) {
        super(dpi, nombre);

        if (!Double.isFinite(pagoPorHora) || pagoPorHora < 0
                || !Double.isFinite(horasTrabajadas) || horasTrabajadas < 0) {
            throw new IllegalArgumentException(
                    "Los valores deben ser finitos y mayores o iguales a cero.");
        }

        this.pagoPorHora = pagoPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getPagoPorHora() {
        return pagoPorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    @Override
    public double calcularPago() {
        return pagoPorHora * horasTrabajadas;
    }

    @Override
    public String obtenerTipo() {
        return "Por hora";
    }
}
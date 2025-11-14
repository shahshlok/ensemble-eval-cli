public class Cuboid {
    private double l;
    private double w;
    private double h;
    private String color;

    public Cuboid(double l, double w, double h, String color) {
        this.l = l;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public Cuboid() {
        this(1, 1, 1, "white");
    }

    public double getL() {
        return l;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public String getColor() {
        return color;
    }

    public double getSurfaceArea() {
        return 2 * (l * w + l * h + w * h);
    }

    public double getVolume() {
        return l * w * h;
    }

    public void displayInfo() {
        System.out.printf("Cuboid color: %s%n", color);
        System.out.printf("Dimensions: %.1f x %.1f x %.1f%n", l, w, h);
        System.out.printf("Surface area: %.2f%n", getSurfaceArea());
        System.out.printf("Volume: %.2f%n", getVolume());
    }

    public static void main(String[] args) {
        Cuboid one = new Cuboid();
        Cuboid two = new Cuboid(8.0, 3.5, 5.9, "green");

        System.out.println("First cuboid:");
        one.displayInfo();

        System.out.println("Second cuboid:");
        two.displayInfo();
    }
}


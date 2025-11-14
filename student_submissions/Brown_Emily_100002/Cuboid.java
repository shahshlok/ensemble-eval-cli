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
        this(1.0, 1.0, 1.0, "white");
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
        return 2.0 * (l * w + l * h + w * h);
    }

    public double getVolume() {
        return l * w * h;
    }

    public void displayInfo() {
        System.out.println("Cuboid color = " + color);
        System.out.println("Dimensions (l, w, h) = " + l + ", " + w + ", " + h);
        System.out.println("Surface area = " + getSurfaceArea());
        System.out.println("Volume = " + getVolume());
    }

    public static void main(String[] args) {
        Cuboid defaultCuboid = new Cuboid();
        Cuboid greenCuboid = new Cuboid(8, 3.5, 5.9, "green");

        System.out.println("Default cuboid");
        defaultCuboid.displayInfo();

        System.out.println("Green cuboid");
        greenCuboid.displayInfo();
    }
}


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
        l = 1;
        w = 1;
        h = 1;
        color = "white";
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
        return l * w + l * h + w * h;
    }

    public double getVolume() {
        return l * w * h;
    }

    public void displayInfo() {
        System.out.println("Color " + color);
        System.out.println("Size " + l + "," + w + "," + h);
        System.out.println("SA " + getSurfaceArea());
        System.out.println("V " + getVolume());
    }

    public static void main(String[] args) {
        Cuboid c1 = new Cuboid();
        Cuboid c2 = new Cuboid(8, 3.5, 5.9, "green");

        c1.displayInfo();
        c2.displayInfo();
    }
}


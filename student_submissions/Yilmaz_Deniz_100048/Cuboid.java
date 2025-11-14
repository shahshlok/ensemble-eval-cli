public class Cuboid {
    private double l, w, h;
    private String color;

    public Cuboid(double length, double width, double height, String color) {
        l = length;
        w = width;
        h = height;
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
        System.out.println(color + " cuboid " + l + " " + w + " " + h);
        System.out.println("surface " + getSurfaceArea());
        System.out.println("volume " + getVolume());
    }

    public static void main(String[] args) {
        Cuboid first = new Cuboid();
        Cuboid second = new Cuboid(8, 3.5, 5.9, "green");

        first.displayInfo();
        second.displayInfo();
    }
}

